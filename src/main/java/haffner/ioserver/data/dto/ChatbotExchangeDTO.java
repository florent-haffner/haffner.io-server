package haffner.ioserver.data.dto;

public class ChatbotExchangeDTO {
    String messageRequested;
    String messageResponse;
    Boolean inError;
    String userId;

    public String getMessageRequested() {
        return messageRequested;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public Boolean getInError() {
        return inError;
    }

    public String getUserId() {
        return userId;
    }

    public void setMessageRequested(String messageRequested) {
        this.messageRequested = messageRequested;
    }

    public void setMessageResponse(String messageResponse) {
        this.messageResponse = messageResponse;
    }

    public void setInError(Boolean inError) {
        this.inError = inError;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChatbotExchangeDTO{" +
            "messageRequested='" + messageRequested + '\'' +
            ", messageResponse='" + messageResponse + '\'' +
            ", inError=" + inError +
            ", userId='" + userId + '\'' +
            '}';
    }
}
