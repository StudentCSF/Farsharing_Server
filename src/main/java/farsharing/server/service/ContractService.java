package farsharing.server.service;

import farsharing.server.exception.CarNotFoundException;
import farsharing.server.exception.ClientNotFoundException;
import farsharing.server.exception.NotFreeCarNotExistsInContractException;
import farsharing.server.exception.RequestNotValidException;
import farsharing.server.model.dto.response.CarResponse;
import farsharing.server.model.entity.ContractEntity;
import farsharing.server.model.entity.embeddable.WalletEmbeddable;
import farsharing.server.model.entity.enumerate.ContractStatus;
import farsharing.server.repository.CarRepository;
import farsharing.server.repository.ClientRepository;
import farsharing.server.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    private final CarRepository carRepository;

    private final ClientRepository clientRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository,
                           CarRepository carRepository,
                           ClientRepository clientRepository) {
        this.contractRepository = contractRepository;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
    }

    public CarResponse checkCar(UUID clUid, UUID carUid) {
        if (clUid == null || carUid == null) {
            throw new RequestNotValidException();
        }

        boolean isFree = this.carRepository.findById(carUid)
                .orElseThrow(CarNotFoundException::new)
                .getIsAvailable();

        boolean thisClient;
        ContractStatus status;

        List<ContractEntity> contractEntityList = this.contractRepository.findByCar(carUid);
        if (contractEntityList.isEmpty()) {
            if (!isFree) {
                throw new NotFreeCarNotExistsInContractException();
            }
            thisClient = false;
            status = null;
        } else {
            ContractEntity contract = contractEntityList.get(0);
            thisClient = contract.getClient().getUid().equals(clUid);

            status = thisClient && contract.getStatus() != ContractStatus.CLOSED ? contract.getStatus() : null;
        }
        return CarResponse.builder()
                .isFree(isFree)
                .thisClient(thisClient)
                .status(status)
                .build();
    }



    public WalletEmbeddable getPayData(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }

        return this.clientRepository.findById(uid)
                .orElseThrow(ClientNotFoundException::new)
                .getWallet();
    }
}
