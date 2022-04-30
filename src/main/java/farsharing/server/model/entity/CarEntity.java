package farsharing.server.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car", uniqueConstraints = @UniqueConstraint(
        name = "state_number_un",
        columnNames = "state_number"
))
public class CarEntity {

    @Id
    private UUID uid;

    @Column(nullable = false, name = "brand")
    private String brand;

    @Column(nullable = false, name = "model")
    private String model;

    @ManyToOne
    @JoinColumn(name = "color", referencedColumnName = "name", nullable = false)
    private ColorEntity color;

    @ManyToOne
    @JoinColumn(name = "body_type", referencedColumnName = "name", nullable = false)
    private BodyTypeEntity bodyType;

    @ManyToOne
    @JoinColumn(name = "location", referencedColumnName = "uid", nullable = false)
    private LocationEntity location;

    @Column(nullable = false, name = "state_number")
    private String stateNumber;

    @Column(nullable = false, name = "is_available")
    private Boolean isAvailable;

    @Column(name = "price_per_hour", nullable = false)
    private Float pricePerHour;

    @Column(nullable = false, name = "mileage")
    private Float mileage;
}
