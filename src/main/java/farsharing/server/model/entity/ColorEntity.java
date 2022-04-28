package farsharing.server.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "color")
public class ColorEntity {

    @Id
    private String name;

    @Column(name = "hex")
    private String hex;
}
