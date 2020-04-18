package haffner.ioserver.repository;

import haffner.ioserver.data.domain.ChatbotExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatbotExchangeRepository extends JpaRepository<ChatbotExchange, String> {
}
