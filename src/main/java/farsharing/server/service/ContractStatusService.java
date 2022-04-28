package farsharing.server.service;

import farsharing.server.model.dto.ContractStatusDto;
import farsharing.server.model.entity.ContractStatusEntity;
import farsharing.server.repository.ContractStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractStatusService {

    private final ContractStatusRepository contractStatusRepository;

    @Autowired
    public ContractStatusService(ContractStatusRepository contractStatusRepository) {
        this.contractStatusRepository = contractStatusRepository;
    }

    public void addContractStatus(ContractStatusDto contractStatusDto) {
        if (this.contractStatusRepository.findById(contractStatusDto.getContractStatus()).isEmpty()) {
            this.contractStatusRepository.save(ContractStatusEntity.builder()
                    .status(contractStatusDto.getContractStatus())
                    .build());
        }
    }

    public void removeContractStatus(ContractStatusDto contractStatusDto) {
        this.contractStatusRepository.deleteById(contractStatusDto.getContractStatus());
    }
}
