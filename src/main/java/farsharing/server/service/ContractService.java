package farsharing.server.service;

import farsharing.server.component.AddContractRequestValidationComponent;
import farsharing.server.component.PayRequestValidationComponent;
import farsharing.server.exception.*;
import farsharing.server.model.dto.request.AddContractRequest;
import farsharing.server.model.dto.request.PayRequest;
import farsharing.server.model.dto.response.CarResponse;
import farsharing.server.model.dto.response.RequestInfoResponse;
import farsharing.server.model.entity.CarEntity;
import farsharing.server.model.entity.ClientEntity;
import farsharing.server.model.entity.ContractEntity;
import farsharing.server.model.entity.embeddable.WalletEmbeddable;
import farsharing.server.model.entity.enumerate.ClientStatus;
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

    private final PayRequestValidationComponent payRequestValidationComponent;

    private final AddContractRequestValidationComponent addContractRequestValidationComponent;

    @Autowired
    public ContractService(ContractRepository contractRepository,
                           CarRepository carRepository,
                           ClientRepository clientRepository,
                           PayRequestValidationComponent payRequestValidationComponent,
                           AddContractRequestValidationComponent addContractRequestValidationComponent) {
        this.contractRepository = contractRepository;
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.payRequestValidationComponent = payRequestValidationComponent;
        this.addContractRequestValidationComponent = addContractRequestValidationComponent;
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
        UUID contractUid = null;

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
            contractUid = contract.getUid();
        }
        return CarResponse.builder()
                .isFree(isFree)
                .thisClient(thisClient)
                .status(status)
                .contractUid(contractUid)
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

    public void pay(UUID uid, PayRequest request) {
        if (!this.payRequestValidationComponent.isValid(request) || uid == null) {
            throw new RequestNotValidException();
        }

        ContractEntity contract = this.contractRepository.findById(uid)
                .orElseThrow(ContractNotFoundException::new);

        if (contract.getStatus() != ContractStatus.APPROVED) {
            throw new ContractNotApprovedException();
        }

        ClientEntity client = this.clientRepository.findById(contract.getClient().getUid())
                .orElseThrow(ClientNotFoundException::new);

        Integer cvv = Integer.parseInt(request.getCvv());
        if (client.getWallet() != null) {
            if (!client.getWallet().getCard().equals(request.getCardNumber())
                    || !client.getWallet().getCvv().equals(cvv)
                    || !client.getWallet().getValidThru().equals(request.getValidThru())
            ) {
                throw new IncorrectPaymentDataException();
            }
        } else if (request.getSavePaymentData()) {
            client.setWallet(WalletEmbeddable.builder()
                    .validThru(request.getValidThru())
                    .cvv(cvv)
                    .card(request.getCardNumber())
                    .build());

            this.clientRepository.save(client);
        }

        // Вместо реальной ветки оплаты тут ничего не происходит

        CarEntity car = this.carRepository.findById(contract.getCar().getUid())
                .orElseThrow(CarNotFoundException::new);

        car.setIsAvailable(false);
        this.carRepository.save(car);

        contract.setStatus(ContractStatus.ACTIVE);
        this.contractRepository.save(contract);
    }

    public void cancel(UUID uid) {
        ContractEntity contract = this.contractRepository.findById(uid)
                .orElseThrow(ContractNotFoundException::new);

        if (contract.getStatus() == ContractStatus.CONSIDERED
                || contract.getStatus() == ContractStatus.APPROVED
        ) {
            contract.setStatus(ContractStatus.CLOSED);

            this.contractRepository.save(contract);
        }
    }

    public UUID addContract(AddContractRequest addContractRequest) {
        if (!this.addContractRequestValidationComponent.isValid(addContractRequest)) {
            throw new RequestNotValidException();
        }

        CarEntity car = this.carRepository.findById(addContractRequest.getCarUid())
                .orElseThrow(CarNotFoundException::new);

        if (!car.getIsAvailable()) {
            throw new CarIsNotAvailableException();
        }

        UUID res = UUID.randomUUID();
        this.contractRepository.save(ContractEntity.builder()
                .uid(res)
                .car(car)
                .status(ContractStatus.CONSIDERED)
                .startTime(addContractRequest.getStartTime())
                .endTime(addContractRequest.getEndTime())
                .client(this.clientRepository.findById(
                                addContractRequest.getClientUid())
                        .orElseThrow(ClientNotFoundException::new))
                .build());

        return res;
    }

    public RequestInfoResponse getRequestInfo(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }

        ContractEntity contract = this.contractRepository.findById(uid)
                .orElseThrow(ContractNotFoundException::new);
        if (contract.getStatus() != ContractStatus.CONSIDERED) {
            throw new ContractAlreadyActiveException();
        }
        ClientEntity client = this.clientRepository.findById(contract.getClient().getUid())
                .orElseThrow(ClientNotFoundException::new);

        if (client.getStatus() == ClientStatus.BANNED) {
            throw new ClientBannedException();
        }

        return RequestInfoResponse.builder()
                .contract(contract)
                .client(client)
                .build();
    }

    public void approve(UUID uid, Boolean approve) {
        if (uid == null) {
            throw new RequestNotValidException();
        }

         ContractEntity contract = this.contractRepository.findById(uid)
                 .orElseThrow(ContractNotFoundException::new);

        if (contract.getStatus() != ContractStatus.CONSIDERED) {
            throw new ContractHaveNotConsideredStatusException();
        }

        contract.setStatus(approve ? ContractStatus.APPROVED : ContractStatus.REJECTED);

        this.contractRepository.save(contract);
    }
}
