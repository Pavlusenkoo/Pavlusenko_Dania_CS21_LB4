package tcpWork;

public class ShowCardInfoOperation extends CardOperation {
    private String cardNumber;

    public ShowCardInfoOperation(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}