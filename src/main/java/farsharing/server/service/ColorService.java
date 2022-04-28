package farsharing.server.service;

import farsharing.server.model.dto.AddColorDto;
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

    public void addColor(AddColorDto addColorDto) {
        if (this.colorRepository.findById(addColorDto.getName()).isEmpty()) {
            this.colorRepository.save(ColorEntity.builder()
                    .name(addColorDto.getName())
                    .hex(addColorDto.getHex())
                    .build());
        }
    }

    public void removeColor(String colorName) {
        this.colorRepository.deleteById(colorName);
    }

    public List<ColorEntity> getAllColors() {
        return this.colorRepository.findAll();
    }
}
