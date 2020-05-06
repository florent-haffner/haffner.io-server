package haffner.io.server.data.dto;

public class ChatbotExchangeDTO {
    String messageRequest;
    String messageResponse;
    Boolean inError;
    String conversationId;
    Integer userId;

    public String getMessageRequest() {
        return messageRequest;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public Boolean getInError() {
        return inError;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setMessageRequest(String messageRequest) {
        this.messageRequest = messageRequest;
    }

    public void setMessageResponse(String messageResponse) {
        this.messageResponse = messageResponse;
    }

    public void setInError(Boolean inError) {
        this.inError = inError;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public String toString() {
        return "ChatbotExchangeDTO{" +
                "messageRequested='" + messageRequest + '\'' +
                ", messageResponse='" + messageResponse + '\'' +
                ", inError=" + inError +
                ", conversationId='" + conversationId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
