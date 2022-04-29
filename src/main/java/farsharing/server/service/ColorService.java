package farsharing.server.service;

import farsharing.server.model.entity.ColorEntity;
import farsharing.server.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {

    private final ColorRepository colorRepository;

    @Autowired
    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public List<ColorEntity> getAllColors() {
        return this.colorRepository.findAll();
    }
}
