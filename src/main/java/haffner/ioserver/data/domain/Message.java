package haffner.ioserver.data.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MESSAGE")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String message;

    @Column(name = "in_error")
    private Boolean inError;

    public Message() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getInError() {
        return inError;
    }

    public void setInError(Boolean inError) {
        this.inError = inError;
    }

}
