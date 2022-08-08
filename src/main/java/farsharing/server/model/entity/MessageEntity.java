package farsharing.server.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "message")
public class MessageEntity {

    @Id
    private UUID uid;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;
}
