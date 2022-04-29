package farsharing.server.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class AddContractDto {

    @NotNull
    private UUID clientUid;

    @NotNull
    private UUID carUid;

    @NotNull
    private ZonedDateTime startTime;

    @NotNull
    private ZonedDateTime endTime;
}
