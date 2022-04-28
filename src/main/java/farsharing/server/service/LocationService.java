package farsharing.server.service;

import farsharing.server.model.dto.AddLocationDto;
import farsharing.server.model.entity.LocationEntity;
import farsharing.server.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void addLocation(AddLocationDto addLocationDto) {
        this.locationRepository.save(LocationEntity.builder()
                .country(addLocationDto.getCountry())
                .city(addLocationDto.getCity())
                .build());
    }

    public void removeLocation(UUID locationUid) {
        this.locationRepository.deleteById(locationUid);
    }

    public List<LocationEntity> getAllLocations() {
        return this.locationRepository.findAll();
    }
}
