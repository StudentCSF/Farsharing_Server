package farsharing.server.model.entity;

import farsharing.server.model.entity.enumerate.UserRole;
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
@Table(name = "user_", uniqueConstraints = @UniqueConstraint(
        name = "email_un",
        columnNames = "email"
))
public class UserEntity {

    @Id
    private UUID uid;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
}
