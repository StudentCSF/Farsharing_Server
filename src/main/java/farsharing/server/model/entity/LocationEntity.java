package farsharing.server.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "location")
public class LocationEntity {

    @Id
    @GeneratedValue
    private UUID uid;

    @Column(nullable = false, name = "country")
    private String country;

    @Column(name = "city", nullable = false)
    private String city;
}
