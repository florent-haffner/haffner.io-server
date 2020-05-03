package haffner.ioserver.data.domain;

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
@Table(name = "chatbot_message_in_error")
public class ChatbotMessageInError implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "message_requested")
    private String messageRequested;

    @NotNull
    @Column(name = "message_response")
    private String messageResponse;

    @NotNull
    @Column(name = "date_of_creation")
    private Timestamp dateOfCreation;

    @NotNull
    @Column(name = "conversation_id")
    private String conversationId;

    public ChatbotMessageInError() {
        this.dateOfCreation = new Timestamp(System.currentTimeMillis());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageRequested() {
        return messageRequested;
    }

    public void setMessageRequested(String messageIN) {
        this.messageRequested = messageIN;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(String message) {
        this.messageResponse = message;
    }

    public Timestamp getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Timestamp timestamp) {
        this.dateOfCreation = timestamp;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String userId) {
        this.conversationId = userId;
    }
}
