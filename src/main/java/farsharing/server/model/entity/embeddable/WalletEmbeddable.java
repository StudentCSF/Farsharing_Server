package farsharing.server.model.entity.embeddable;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletEmbeddable {

    @Column(name = "card_number")
    private String card;

    @Column(name = "valid_thru")
    private String validThru;

    @Column(name = "cvv")
    private Integer cvv;
}
