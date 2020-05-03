package haffner.ioserver.repository;

import haffner.ioserver.data.domain.ChatbotMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatbotMessageRepository extends CrudRepository<ChatbotMessage, Integer> {

    List<ChatbotMessage> findByConversationId(String conversationId);

}
