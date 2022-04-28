package farsharing.server.service;

import farsharing.server.model.dto.BodyTypeDto;
import farsharing.server.model.entity.BodyTypeEntity;
import farsharing.server.repository.BodyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyTypeService {

    private final BodyTypeRepository bodyTypeRepository;

    @Autowired
    public BodyTypeService(BodyTypeRepository bodyTypeRepository) {
        this.bodyTypeRepository = bodyTypeRepository;
    }

    public void addBodyType(BodyTypeDto bodyTypeDto) {
        if (this.bodyTypeRepository.findById(bodyTypeDto.getBodyType()).isEmpty()) {
            this.bodyTypeRepository.save(BodyTypeEntity.builder()
                    .name(bodyTypeDto.getBodyType())
                    .build());
        }
    }

    private void removeBodyType(BodyTypeDto bodyTypeDto) {
        this.bodyTypeRepository.deleteById(bodyTypeDto.getBodyType());
    }

    private List<BodyTypeEntity> getAllBodyTypes() {
        return this.bodyTypeRepository.findAll();
    }
}
