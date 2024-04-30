package tcpWork;

public class AddMetroCardOperation extends CardOperation {
    private MetroCard crd = null;

    public AddMetroCardOperation() {
        crd = new MetroCard();
    }

    public MetroCard getCrd() {
        return crd;
    }
}