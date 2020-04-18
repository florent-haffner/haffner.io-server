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
