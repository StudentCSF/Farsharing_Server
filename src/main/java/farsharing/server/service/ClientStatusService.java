package farsharing.server.service;

import farsharing.server.model.dto.ClientStatusDto;
import farsharing.server.model.entity.ClientStatusEntity;
import farsharing.server.repository.ClientStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientStatusService {

    private final ClientStatusRepository clientStatusRepository;

    @Autowired
    public ClientStatusService(ClientStatusRepository clientStatusRepository) {
        this.clientStatusRepository = clientStatusRepository;
    }

    private void addClientStatus(ClientStatusDto clientStatusDto) {
        if (this.clientStatusRepository.findById(clientStatusDto.getClientStatus()).isEmpty()) {
            this.clientStatusRepository.save(ClientStatusEntity.builder()
                    .status(clientStatusDto.getClientStatus())
                    .build());
        }
    }

    private void deleteClientStatus(ClientStatusDto clientStatusDto) {
        this.clientStatusRepository.deleteById(clientStatusDto.getClientStatus());
    }

    private List<ClientStatusEntity> getAllClientStatus() {
        return this.clientStatusRepository.findAll();
    }
}
