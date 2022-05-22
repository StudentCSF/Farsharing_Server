package farsharing.server.service;

import farsharing.server.component.AddClientValidationComponent;

import farsharing.server.component.MailSenderComponent;
import farsharing.server.component.StringHandlerComponent;
import farsharing.server.exception.ClientAlreadyExistsException;
import farsharing.server.exception.RequestNotValidException;
import farsharing.server.model.dto.request.AddClientRequest;
import farsharing.server.model.entity.ClientEntity;
import farsharing.server.model.entity.embeddable.WalletEmbeddable;
import farsharing.server.model.entity.enumerate.ClientStatus;
import farsharing.server.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final UserService userService;

    private final AddClientValidationComponent addClientValidationComponent;

    private final StringHandlerComponent stringHandlerComponent;

    private final MailSenderComponent mailSenderComponent;

    @Autowired
    public ClientService(ClientRepository clientRepository,
                         UserService userService, AddClientValidationComponent addClientValidationComponent, StringHandlerComponent stringHandlerComponent, MailSenderComponent mailSenderComponent) {
        this.clientRepository = clientRepository;
        this.userService = userService;
        this.addClientValidationComponent = addClientValidationComponent;
        this.stringHandlerComponent = stringHandlerComponent;
        this.mailSenderComponent = mailSenderComponent;
    }

    public void addClient(AddClientRequest addClientRequest) {
        if (!this.addClientValidationComponent.isValid(addClientRequest)) {
            throw new RequestNotValidException();
        }

        if (this.clientRepository.findByLicense(addClientRequest.getLicense()).isPresent()) {
            throw new ClientAlreadyExistsException();
        }

        UUID userUid = this.userService.addUser(
                addClientRequest.getEmail(),
                addClientRequest.getPassword()
        );

        String cvv = this.stringHandlerComponent.emptyLikeNull(addClientRequest.getCvv());

        WalletEmbeddable wallet = WalletEmbeddable.builder()
                .card(this.stringHandlerComponent.emptyLikeNull(addClientRequest.getCardNumber()))
                .cvv(cvv == null ? null : Integer.valueOf(cvv))
                .validThru(this.stringHandlerComponent.emptyLikeNull(addClientRequest.getValidThru()))
                .build();

        this.clientRepository.save(ClientEntity.builder()
                .user(this.userService.getUser(userUid))
                .wallet(wallet)
                .uid(UUID.randomUUID())
                .status(ClientStatus.DEFAULT)
                .phoneNumber(addClientRequest.getPhoneNumber())
                .midName(this.stringHandlerComponent.emptyLikeNull(addClientRequest.getMidName()))
                .license(addClientRequest.getLicense())
                .accidents(0)
                .address(this.stringHandlerComponent.emptyLikeNull(addClientRequest.getAddress()))
                .firstName(addClientRequest.getFirstName())
                .lastName(addClientRequest.getLastName())
                .build());

        int code = this.mailSenderComponent.sendActivationCode(addClientRequest.getEmail());

        this.userService.setActivationCode(userUid, code);
    }
}
