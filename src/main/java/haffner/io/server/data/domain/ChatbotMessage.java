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
@Table(name = "chatbot_message")
public class ChatbotMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "text")
    private String text;

    @NotNull
    @Column(name = "date_of_creation")
    private Timestamp dateOfCreation;

    @NotNull
    @Column(name = "conversation_id")
    private String conversationId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "chatbot_revision")
    private Float chatbotRevision;

    @Column(name = "is_in_error")
    private Boolean in_error;

    public ChatbotMessage() {
        this.dateOfCreation = new Timestamp(System.currentTimeMillis());
        this.userId = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String messageIN) {
        this.text = messageIN;
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

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Float getChatbotRevision() {
        return chatbotRevision;
    }

    public void setChatbotRevision(Float chatbotRevision) {
        this.chatbotRevision = chatbotRevision;
    }

    public Boolean getIn_error() {
        return in_error;
    }

    public void setIn_error(Boolean in_error) {
        this.in_error = in_error;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChatbotMessage{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", conversationId='" + conversationId + '\'' +
                ", userId=" + userId +
                ", chatbotRevision=" + chatbotRevision +
                ", in_error=" + in_error +
                '}';
    }

}
