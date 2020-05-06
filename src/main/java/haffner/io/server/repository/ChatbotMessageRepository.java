package haffner.io.server.repository;

import haffner.io.server.data.domain.ChatbotMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatbotMessageRepository extends CrudRepository<ChatbotMessage, Integer> {

    List<ChatbotMessage> findByConversationId(String conversationId);

}