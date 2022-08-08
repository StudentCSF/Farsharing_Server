package farsharing.server.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность для добавления сообщений")
public class MessageRequest {

    @Schema(description = "заголовок/тема сообщения")
    private String title;

    @Schema(description = "текст сообщения")
    private String content;
}
