package farsharing.server.service;

import farsharing.server.component.ClientRequestValidationComponent;
import farsharing.server.component.MailSenderComponent;
import farsharing.server.component.StringHandlerComponent;
import farsharing.server.exception.ClientAlreadyExistsException;
import farsharing.server.exception.ClientNotFoundException;
import farsharing.server.exception.RequestNotValidException;
import farsharing.server.model.dto.request.ClientRequest;
import farsharing.server.model.dto.request.UserRequest;
import farsharing.server.model.dto.response.ClientDataResponse;
import farsharing.server.model.entity.ClientEntity;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.model.entity.embeddable.WalletEmbeddable;
import farsharing.server.model.entity.enumerate.ClientStatus;
import farsharing.server.model.entity.enumerate.ContractStatus;
import farsharing.server.repository.ClientRepository;
import farsharing.server.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final UserService userService;

    private final ClientRequestValidationComponent clientRequestValidationComponent;

    private final StringHandlerComponent stringHandlerComponent;

    private final MailSenderComponent mailSenderComponent;

    private final ContractRepository contractRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository,
                         UserService userService,
                         ClientRequestValidationComponent clientRequestValidationComponent,
                         StringHandlerComponent stringHandlerComponent,
                         MailSenderComponent mailSenderComponent,
                         ContractRepository contractRepository) {
        this.clientRepository = clientRepository;
        this.userService = userService;
        this.clientRequestValidationComponent = clientRequestValidationComponent;
        this.stringHandlerComponent = stringHandlerComponent;
        this.mailSenderComponent = mailSenderComponent;
        this.contractRepository = contractRepository;
    }

    public void addClient(ClientRequest clientRequest) {
        if (clientRequest == null
                ||!this.clientRequestValidationComponent.isValid(clientRequest)
        ) {
            throw new RequestNotValidException();
        }

        if (this.clientRepository.findByLicense(clientRequest.getLicense()).isPresent()) {
            throw new ClientAlreadyExistsException();
        }

        UUID userUid = this.userService.addUser(
                clientRequest.getEmail(),
                clientRequest.getPassword()
        );

        String cvv = this.stringHandlerComponent.emptyLikeNull(clientRequest.getCvv());

        WalletEmbeddable wallet = WalletEmbeddable.builder()
                .card(this.stringHandlerComponent.emptyLikeNull(clientRequest.getCardNumber()))
                .cvv(cvv == null ? null : Integer.valueOf(cvv))
                .validThru(this.stringHandlerComponent.emptyLikeNull(clientRequest.getValidThru()))
                .build();

        this.clientRepository.save(ClientEntity.builder()
                .user(this.userService.getUser(userUid))
                .wallet(wallet)
                .uid(UUID.randomUUID())
                .status(ClientStatus.DEFAULT)
                .phoneNumber(clientRequest.getPhoneNumber())
                .midName(this.stringHandlerComponent.emptyLikeNull(clientRequest.getMidName()))
                .license(clientRequest.getLicense())
                .accidents(0)
                .address(this.stringHandlerComponent.emptyLikeNull(clientRequest.getAddress()))
                .firstName(clientRequest.getFirstName())
                .lastName(clientRequest.getLastName())
                .build());

        int code = this.mailSenderComponent.sendActivationCode(clientRequest.getEmail());

        this.userService.setActivationCode(userUid, code);
    }

    public ClientDataResponse getData(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }
        ClientEntity client = this.clientRepository.findById(uid)
                .orElseThrow(ClientNotFoundException::new);

        UserEntity user = this.userService.getUser(client.getUser().getUid());

        WalletEmbeddable wallet = client.getWallet();

        Boolean existsContract = !this.contractRepository.findAllByClientUidAndStatus(uid, ContractStatus.ACTIVE)
                .isEmpty();

        ClientDataResponse res = ClientDataResponse.builder()
                .accidents(client.getAccidents())
                .address(client.getAddress())
                .status(client.getStatus())
                .email(user.getEmail())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .license(client.getLicense())
                .midName(client.getMidName())
                .password(user.getPassword())
                .phoneNumber(client.getPhoneNumber())
                .existsActiveContract(existsContract)
                .build();

        if (wallet != null) {
            res.setCardNumber(wallet.getCard());
            res.setCvv(wallet.getCvv() + "");
            res.setValidThru(wallet.getValidThru());
        }

        return res;
    }

    public void delete(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }
        this.userService.deleteUser(this.clientRepository.findById(uid)
                .orElseThrow(ClientNotFoundException::new)
                .getUser().getUid());
    }

    public void update(UUID uid, ClientRequest clientRequest) {
        if (uid == null
                || clientRequest == null
                || !this.clientRequestValidationComponent.isValid(clientRequest)
        ) {
            throw new RequestNotValidException();
        }

        ClientEntity client = this.clientRepository.findById(uid)
                .orElseThrow(ClientNotFoundException::new);

        Optional<ClientEntity> otherClient = this.clientRepository.findByLicense(clientRequest.getLicense());
        if (otherClient.isPresent() && !otherClient.get().getUid().equals(uid)) {
            throw new ClientAlreadyExistsException();
        }

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(clientRequest.getEmail());
        userRequest.setPassword(clientRequest.getPassword());

        this.userService.updateUser(userRequest, client.getUser().getUid());

        String cvv = this.stringHandlerComponent.emptyLikeNull(clientRequest.getCvv());
        WalletEmbeddable wallet = WalletEmbeddable.builder()
                .card(this.stringHandlerComponent.emptyLikeNull(clientRequest.getCardNumber()))
                .cvv(cvv == null ? null : Integer.valueOf(cvv))
                .validThru(this.stringHandlerComponent.emptyLikeNull(clientRequest.getValidThru()))
                .build();

        client.setAddress(this.stringHandlerComponent.emptyLikeNull(clientRequest.getAddress()));
        client.setFirstName(clientRequest.getFirstName());
        client.setLastName(clientRequest.getLastName());
        client.setLicense(clientRequest.getLicense());
        client.setPhoneNumber(clientRequest.getPhoneNumber());
        client.setMidName(this.stringHandlerComponent.emptyLikeNull(clientRequest.getMidName()));
        client.setWallet(wallet);

        this.clientRepository.save(client);
    }

    public void changeClientStatus(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }

        ClientEntity client = this.clientRepository.findById(uid)
                .orElseThrow(ClientNotFoundException::new);

        boolean bnd = client.getStatus() == ClientStatus.BANNED;
        client.setStatus(bnd ? ClientStatus.DEFAULT : ClientStatus.BANNED);

        this.clientRepository.save(client);
    }

//    public UUID getClientUid(UUID userUid) {
//        return this.clientRepository.findByUserUid(userUid)
//                .orElseThrow(ClientNotFoundException::new).getUid();
//    }


    public List<ClientEntity> getAll() {
        return this.clientRepository.findAll();
    }
}
