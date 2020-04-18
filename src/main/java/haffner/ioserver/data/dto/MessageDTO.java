package haffner.ioserver.data.dto;

public class MessageDTO {
    String messageRequested;
    String messageResponse;
    Boolean inError;

    public String getMessageRequested() {
        return messageRequested;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public Boolean getInError() {
        return inError;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
            "messageIn='" + messageRequested + '\'' +
            ", messageOut='" + messageResponse + '\'' +
            ", inError=" + inError +
            '}';
    }
}
