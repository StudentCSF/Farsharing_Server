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
                @UniqueConstraint(name = "phone_un", columnNames = "phone_number"),
                @UniqueConstraint(name = "user_un", columnNames = "user_uid")
        })
public class ClientEntity {

    @Id
    @GeneratedValue
    private UUID uid;

    @OneToOne
    @JoinColumn(name = "user_uid", referencedColumnName = "uid")
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "accidents_count")
    private Integer accidents;

    @Embedded()
    private WalletEmbeddable wallet;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

}
