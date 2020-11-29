package haffner.io.server.repository;

import haffner.io.server.data.domain.ChatbotMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatbotMessageRepository extends JpaRepository<ChatbotMessage, Integer> {

    List<ChatbotMessage> findByConversationId(String conversationId);
    Integer countAllByChatbotRevision(Float revision);
    Integer countAllByChatbotRevisionAndInError(Float revision, Boolean inError);

}
