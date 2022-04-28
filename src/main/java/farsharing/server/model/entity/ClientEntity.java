package farsharing.server.model.entity;

import farsharing.server.model.entity.embeddable.WalletEmbeddable;
import farsharing.server.model.entity.enumerate.ClientStatus;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @GeneratedValue
    private UUID uid;

    @OneToOne
    @JoinColumn(name = "user_uid", referencedColumnName = "uid")
    private UserEntity user;

    @Column(nullable = false, name = "driver_license")
    private String license;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "mid_name")
    private String midName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "accidents_count", nullable = false)
    private Integer accidents;

    @Embedded()
    private WalletEmbeddable wallet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private ClientStatus status;

}
