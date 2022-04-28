package farsharing.server.model.entity;

import farsharing.server.model.entity.enumerate.ContractStatus;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "contract")
public class ContractEntity {

    @Id
    @GeneratedValue
    private UUID uid;

    @ManyToOne
    @JoinColumn(name = "client_uid", referencedColumnName = "uid", nullable = false)
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "car_uid", referencedColumnName = "uid", nullable = false)
    private CarEntity car;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus status;

    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private ZonedDateTime endTime;
}
