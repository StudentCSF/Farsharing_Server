package farsharing.server.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "client_status")
public class ClientStatusEntity {

    @Id
    private String status;
}
