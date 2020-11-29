package haffner.io.server.repository;

import haffner.io.server.data.domain.ChatbotStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatbotStatsRepository extends JpaRepository<ChatbotStats, Integer> {
}
