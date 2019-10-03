package dicewarsSpring.Model;

public class SocketMessage<T> {
    private String title;
    private T body;

    public SocketMessage(String title, T body) {
        this.title = title;
        this.body = body;
    }
}
