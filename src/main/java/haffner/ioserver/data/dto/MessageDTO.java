package haffner.ioserver.data.dto;

public class MessageDTO {
    String message;
    Boolean inError;

    public String getMessage() {
        return message;
    }

    public Boolean getInError() {
        return inError;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
            "message='" + message + '\'' +
            ", inError=" + inError +
            '}';
    }
}
