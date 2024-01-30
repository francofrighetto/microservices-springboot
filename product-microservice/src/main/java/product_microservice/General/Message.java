package product_microservice.General;

public class Message {
    private int errortype;
    private String message;

    public Message(int errortype, String message) {
        this.errortype = errortype;
        this.message = message;
    }

    public int getErrortype() {
        return errortype;
    }

    public String getMessage() {
        return message;
    }

    public void setErrortype(int errortype) {
        this.errortype = errortype;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
