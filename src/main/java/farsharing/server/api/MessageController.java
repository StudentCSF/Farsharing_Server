package farsharing.server.api;

import farsharing.server.model.dto.request.MessageRequest;
import farsharing.server.model.entity.MessageEntity;
import farsharing.server.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Контроллер сообщений в техподдержку", description = "Позволяет добавлять и удалять текстовые обращения в техподдержку")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/api/message")
    @Operation(summary = "Добавление сообщения",
            description = "Позволяет добавить сообщение в БД")
    public void addMessage(
            @RequestBody MessageRequest messageRequest) {
        this.messageService.insertMessage(messageRequest);
    }

    @GetMapping("/api/message")
    @Operation(summary = "Получение всех сообщений",
            description = "Позволяет получить список сообщений")
    public List<MessageEntity> getAllMessages() {
        return this.messageService.getAllMessages();
    }

    @GetMapping("/api/message/{uid}")
    @Operation(summary = "Получение конкретного сообщения",
            description = "Позволяет получить конкретное сообщение из БД")
    public MessageEntity getMessage(
            @PathVariable("uid") @Parameter(description = "идентификатор сообщения") UUID uid
    ) {
        return this.messageService.getMessage(uid);
    }

    @DeleteMapping("/api/message/{uid}")
    @Operation(summary = "Удаление сообщения",
            description = "Позволяет удалить сообщение из БД")
    public void deleteMessage(
            @PathVariable("uid") @Parameter(description = "идентификатор сообщения") UUID uid
    ) {
        this.messageService.deleteMessage(uid);
    }
}
