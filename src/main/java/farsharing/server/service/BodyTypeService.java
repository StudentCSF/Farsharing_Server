package farsharing.server.service;

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

    private List<BodyTypeEntity> getAllBodyTypes() {
        return this.bodyTypeRepository.findAll();
    }
}
