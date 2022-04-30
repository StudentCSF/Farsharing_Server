package farsharing.server.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BriefCarInfoResponse {

    private UUID uid;

    private String brand;

    private String model;

    private Float pricePerHour;
}
