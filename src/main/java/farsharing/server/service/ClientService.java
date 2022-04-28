package farsharing.server.service;

//import farsharing.server.component.AddClientValidationComponent;

import farsharing.server.exception.UserNotFoundException;
import farsharing.server.model.dto.AddClientDto;
import farsharing.server.model.entity.UserEntity;
import farsharing.server.repository.ClientRepository;
import farsharing.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;

//    private final AddClientValidationComponent addClientValidationComponent;

    @Autowired
    public ClientService(ClientRepository clientRepository, UserRepository userRepository){//, AddClientValidationComponent addClientValidationComponent) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
//        this.addClientValidationComponent = addClientValidationComponent;
    }

    public void addClient(AddClientDto clientDto) {
//        if (!addClientValidationComponent.isValid(clientDto)) {
//            throw new RequestNotValidException();
//        }
        UserEntity userEntity = userRepository.findById(
                clientDto.getUserUid())
                .orElseThrow(UserNotFoundException::new);
//        clientRepository.save(ClientEntity.builder()
//                        .accidents(0)
//                        .address(clientDto.getAddress())
//                        .firstName(clientDto.getFirstName())
//                        .lastName(clientDto.getLastName())
//                        .status()
//                .build())
    }
}
