package haffner.io.server.data.dto;

public class ChatbotExchangeDTO {
    String messageRequest;
    String messageResponse;
    Boolean inError;
    String conversationId;
    Float chatbotRevision;

    public String getMessageRequest() {
        return messageRequest;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public Boolean getInError() {
        return inError;
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

    @Override
    public String toString() {
        return "ChatbotExchangeDTO{" +
                "messageRequest='" + messageRequest + '\'' +
                ", messageResponse='" + messageResponse + '\'' +
                ", inError=" + inError +
                ", chatbotRevision=" + chatbotRevision +
                '}';
    }
}
