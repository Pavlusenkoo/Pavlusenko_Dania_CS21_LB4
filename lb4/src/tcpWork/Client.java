package tcpWork;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private int port = -1;
    private String server = null;
    private Socket socket = null;
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private Object response;

    public Client(String server, int port) {
        this.port = port;
        this.server = server;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(server, port), 1000);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (InterruptedIOException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void finish() {
        try {
            os.writeObject(new StopOperation());
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void applyOperation(CardOperation op) {
        try {
            os.writeObject(op);
            os.flush();
            response = is.readObject();
            System.out.println(response);
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public Object getResponse() {
        return response;
    }

    private static boolean isCardExists(Client client, String cardNumber) {
        ShowBalanceOperation op = new ShowBalanceOperation(cardNumber);
        client.applyOperation(op);
        String response = (String) client.getResponse();
        return response.startsWith("Card :");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Client client = new Client("localhost", 7891);

        boolean exit = false;
        while (!exit) {
            System.out.println("Виберіть операцію:");
            System.out.println("1. Створити нову картку");
            System.out.println("2. Поповнити баланс картки");
            System.out.println("3. Оплатити поїздку");
            System.out.println("4. Видалити картку");
            System.out.println("5. Показати баланс картки");
            System.out.println("6. Показати інформацію про картку та її власника");
            System.out.println("7. Вийти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистити буфер

            switch (choice) {
                case 1:
                    System.out.println("Введіть ім'я користувача:");
                    String name = scanner.nextLine();
                    System.out.println("Введіть прізвище користувача:");
                    String surname = scanner.nextLine();
                    System.out.println("Введіть стать користувача (M/F):");
                    String sex = scanner.nextLine();
                    System.out.println("Введіть дату народження користувача (дд.мм.рррр):");
                    String birthday = scanner.nextLine();
                    System.out.println("Введіть серійний номер картки:");
                    String serialNumber = scanner.nextLine();
                    System.out.println("Введіть назву навчального закладу:");
                    String college = scanner.nextLine();
                    System.out.println("Введіть початковий баланс картки:");
                    double balance = scanner.nextDouble();
                    scanner.nextLine(); // Очистити буфер

                    User user = new User(name, surname, sex, birthday);
                    MetroCard card = new MetroCard(serialNumber, user, college, balance);
                    AddMetroCardOperation op = new AddMetroCardOperation();
                    op.getCrd().setUsr(user);
                    op.getCrd().setSerNum(serialNumber);
                    op.getCrd().setColledge(college);
                    op.getCrd().setBalance(balance);
                    client.applyOperation(op);
                    break;
                case 2:
                    System.out.println("Введіть серійний номер картки:");
                    String cardNumber = scanner.nextLine();
                    if (isCardExists(client, cardNumber)) {
                        System.out.println("Введіть суму для поповнення:");
                        double amount = scanner.nextDouble();
                        scanner.nextLine(); // Очистити буфер
                        client.applyOperation(new AddMoneyOperation(cardNumber, amount));
                    } else {
                        System.out.println("Картка з таким серійним номером не знайдена.");
                    }
                    break;
                case 3:
                    System.out.println("Введіть серійний номер картки:");
                    cardNumber = scanner.nextLine();
                    if (isCardExists(client, cardNumber)) {
                        System.out.println("Введіть суму для оплати поїздки:");
                        double amount = scanner.nextDouble();
                        scanner.nextLine(); // Очистити буфер
                        client.applyOperation(new PayMoneyOperation(cardNumber, amount));
                    } else {
                        System.out.println("Картка з таким серійним номером не знайдена.");
                    }
                    break;
                case 4:
                    System.out.println("Введіть серійний номер картки для видалення:");
                    cardNumber = scanner.nextLine();
                    if (isCardExists(client, cardNumber)) {
                        client.applyOperation(new RemoveCardOperation(cardNumber));
                    } else {
                        System.out.println("Картка з таким серійним номером не знайдена.");
                    }
                    break;
                case 5:
                    System.out.println("Введіть серійний номер картки для перегляду балансу:");
                    cardNumber = scanner.nextLine();
                    if (isCardExists(client, cardNumber)) {
                        client.applyOperation(new ShowBalanceOperation(cardNumber));
                    } else {
                        System.out.println("Картка з таким серійним номером не знайдена.");
                    }
                    break;
                case 6:
                    System.out.println("Введіть серійний номер картки для перегляду інформації:");
                    cardNumber = scanner.nextLine();
                    if (isCardExists(client, cardNumber)) {
                        client.applyOperation(new ShowCardInfoOperation(cardNumber));
                    } else {
                        System.out.println("Картка з таким серійним номером не знайдена.");
                    }
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }

        client.finish();
        scanner.close();
    }
}