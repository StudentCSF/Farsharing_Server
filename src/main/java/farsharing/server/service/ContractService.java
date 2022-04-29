package farsharing.server.service;

import farsharing.server.component.AddContractValidationComponent;
import farsharing.server.exception.CarNotFoundException;
import farsharing.server.exception.ClientNotFoundException;
import farsharing.server.exception.RequestNotValidException;
import farsharing.server.model.dto.AddContractDto;
import farsharing.server.model.entity.CarEntity;
import farsharing.server.model.entity.ClientEntity;
import farsharing.server.model.entity.ContractEntity;
import farsharing.server.model.entity.enumerate.ContractStatus;
import farsharing.server.repository.CarRepository;
import farsharing.server.repository.ClientRepository;
import farsharing.server.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    private final CarRepository carRepository;

    private final ClientRepository clientRepository;

    private final AddContractValidationComponent addContractValidationComponent;

    @Autowired
    public ContractService(ContractRepository contractRepository,
                           CarRepository carRepository,
                           ClientRepository clientRepository,
                           AddContractValidationComponent addContractValidationComponent) {
        this.contractRepository = contractRepository;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.addContractValidationComponent = addContractValidationComponent;
    }

    public void addContract(AddContractDto addContractDto) {
        if (!this.addContractValidationComponent.isValid(addContractDto)) {
            throw new RequestNotValidException();
        }

        CarEntity carEntity = this.carRepository.findById(addContractDto.getCarUid())
                .orElseThrow(CarNotFoundException::new);

        ClientEntity clientEntity = this.clientRepository.findById(addContractDto.getClientUid())
                .orElseThrow(ClientNotFoundException::new);

        this.contractRepository.save(ContractEntity.builder()
                .car(carEntity)
                .client(clientEntity)
                .endTime(addContractDto.getEndTime())
                .startTime(addContractDto.getStartTime())
                .status(ContractStatus.CONSIDERED)
                .uid(UUID.randomUUID())
                .build());
    }
}
