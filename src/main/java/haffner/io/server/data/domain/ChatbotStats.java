package haffner.io.server.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "chatbot_stats")
public class ChatbotStats implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "date_of_creation")
    private Timestamp dateOfCreation;

    @Column(name = "chatbot_revision")
    private Float chatbotRevision;

    @NotNull
    @Column(name = "nbr_messages")
    private Integer nbrMessages;

    @NotNull
    @Column(name = "nbr_error")
    private Integer nbrerror;

    @Column(name = "success_in_percent")
    private Integer successPercent;

    public ChatbotStats() {
        this.dateOfCreation = new Timestamp(System.currentTimeMillis());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Timestamp dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Integer getNbrMessages() {
        return nbrMessages;
    }

    public void setNbrMessages(Integer nbrMessages) {
        this.nbrMessages = nbrMessages;
    }

    public Integer getNbrerror() {
        return nbrerror;
    }

    public void setNbrerror(Integer nbrerror) {
        this.nbrerror = nbrerror;
    }

    public Integer getSuccessPercent() {
        return successPercent;
    }

    public void setSuccessPercent(Integer successPercent) {
        this.successPercent = successPercent;
    }

    public Float getChatbotRevision() {
        return chatbotRevision;
    }

    public void setChatbotRevision(Float chatbotRevision) {
        this.chatbotRevision = chatbotRevision;
    }

    @Override
    public String toString() {
        return "ChatbotStats{" +
                "id=" + id +
                ", dateOfCreation=" + dateOfCreation +
                ", chatbotRevision=" + chatbotRevision +
                ", nbrMessages=" + nbrMessages +
                ", nbrerror=" + nbrerror +
                ", successPercent=" + successPercent +
                '}';
    }

}
