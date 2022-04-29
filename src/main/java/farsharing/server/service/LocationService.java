package farsharing.server.service;

import farsharing.server.model.entity.LocationEntity;
import farsharing.server.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationEntity> getAllLocations() {
        return this.locationRepository.findAll();
    }
}
