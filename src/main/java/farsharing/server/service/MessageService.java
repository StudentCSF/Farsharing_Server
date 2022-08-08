package farsharing.server.service;

import farsharing.server.exception.MessageNotFoundException;
import farsharing.server.exception.RequestNotValidException;
import farsharing.server.model.dto.request.MessageRequest;
import farsharing.server.model.entity.MessageEntity;
import farsharing.server.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void insertMessage(MessageRequest request) {
        if (request == null) {
            throw new RequestNotValidException();
        }

        String title = request.getTitle(), content = request.getContent();
        if (title == null || title.length() == 0) {
            title = "<Тема не указана>";
        }

        if (content == null) {
            content = "";
        }

        UUID uid = UUID.randomUUID();
        MessageEntity messageEntity = MessageEntity.builder()
                .uid(uid)
                .title(title)
                .content(content)
                .build();

        this.messageRepository.save(messageEntity);
    }

    public void deleteMessage(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }
        if (this.messageRepository.findById(uid).isPresent()) {
            this.messageRepository.deleteById(uid);
        }
    }

    public List<MessageEntity> getAllMessages() {
        return this.messageRepository.findAll();
    }

    public MessageEntity getMessage(UUID uid) {
        if (uid == null) {
            throw new RequestNotValidException();
        }
        return this.messageRepository.findById(uid)
                .orElseThrow(MessageNotFoundException::new);
    }
}
