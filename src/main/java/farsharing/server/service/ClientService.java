package farsharing.server.service;

import farsharing.server.component.AddClientValidationComponent;

import farsharing.server.exception.RequestNotValidException;
import farsharing.server.exception.UserNotFoundException;
import farsharing.server.model.dto.request.AddClientRequest;
import farsharing.server.model.entity.ClientEntity;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.model.entity.embeddable.WalletEmbeddable;
import farsharing.server.model.entity.enumerate.ClientStatus;
import farsharing.server.repository.ClientRepository;
import farsharing.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;

    private final AddClientValidationComponent addClientValidationComponent;

    @Autowired
    public ClientService(ClientRepository clientRepository,
                         UserRepository userRepository,
                         AddClientValidationComponent addClientValidationComponent) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.addClientValidationComponent = addClientValidationComponent;
    }

    public void addClient(AddClientRequest clientDto) {
        if (!this.addClientValidationComponent.isValid(clientDto)) {
            throw new RequestNotValidException();
        }
        UserEntity userEntity = this.userRepository.findById(
                        clientDto.getUserUid())
                .orElseThrow(UserNotFoundException::new);
        this.clientRepository.save(ClientEntity.builder()
                .lastName(clientDto.getLastName())
                .firstName(clientDto.getFirstName())
                .address(clientDto.getAddress())
                .accidents(0)
                .license(clientDto.getLicense())
                .midName(clientDto.getMidName())
                .phoneNumber(clientDto.getPhoneNumber())
                .status(ClientStatus.DEFAULT)
                .uid(UUID.randomUUID())
                .wallet(WalletEmbeddable.builder()
                        .card(clientDto.getCardNumber())
                        .cvv(clientDto.getCvv())
                        .validThru(clientDto.getValidThru())
                        .build())
                .user(userEntity)
                .build());
    }
}
