package farsharing.server.model.entity;

import farsharing.server.model.entity.embeddable.WalletEmbeddable;
import farsharing.server.model.entity.enumerate.ClientStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "client",
        uniqueConstraints = {
                @UniqueConstraint(name = "license_un", columnNames = "driver_license"),
                @UniqueConstraint(name = "user_un", columnNames = "user_uid")
        })
public class ClientEntity {

    @Id
    private UUID uid;

    @OneToOne
    @JoinColumn(name = "user_uid", referencedColumnName = "uid", nullable = false)
    private UserEntity user;

    @Column(nullable = false, name = "driver_license")
    private String license;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "mid_name")
    private String midName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "accidents_count")
    private Integer accidents;

    @Embedded()
    private WalletEmbeddable wallet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private ClientStatus status;

}
