package haffner.ioserver.data.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chatbot_exchange")
public class ChatbotExchange implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message_requested")
    private String messageRequested;

    @Column(name = "message_response")
    private String messageResponse;

    @Column(name = "in_error")
    private Boolean inError;

    @Column(name = "user_id")
    private String userId;

    public ChatbotExchange() { }

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

    public Boolean getInError() {
        return inError;
    }

    public void setInError(Boolean inError) {
        this.inError = inError;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
