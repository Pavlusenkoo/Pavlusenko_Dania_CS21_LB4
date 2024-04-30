package tcpWork;

public class MetroCard implements java.io.Serializable {
    private String serNum;
    private User usr;
    private String colledge;
    private double balance;

    public MetroCard() {
    }

    public MetroCard(String serNum, User usr, String colledge, double balance) {
        this.serNum = serNum;
        this.usr = usr;
        this.colledge = colledge;
        this.balance = balance;
    }

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public User getUsr() {
        return usr;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public String getColledge() {
        return colledge;
    }

    public void setColledge(String colledge) {
        this.colledge = colledge;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "No: " + serNum + "\nUser: " + usr + "\nColledge: " + colledge + "\nBalance: " + balance;
    }

    public String getFullInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Інформація про картку:\n");
        sb.append("Серійний номер: ").append(serNum).append("\n");
        sb.append("Колледж: ").append(colledge).append("\n");
        sb.append("Поточний баланс: ").append(balance).append("\n\n");
        sb.append("Інформація про власника картки:\n");
        sb.append(usr.toString());
        return sb.toString();
    }
}