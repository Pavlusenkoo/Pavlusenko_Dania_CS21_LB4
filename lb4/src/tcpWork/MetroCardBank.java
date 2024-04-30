package tcpWork;

import java.util.ArrayList;

public class MetroCardBank implements java.io.Serializable {
    private ArrayList<MetroCard> store;

    public MetroCardBank() {
        store = new ArrayList<>();
    }

    public ArrayList<MetroCard> getStore() {
        return store;
    }

    public int findMetroCard(String serNum) {
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getSerNum().equals(serNum)) {
                return i;
            }
        }
        return -1;
    }

    public int numCards() {
        return store.size();
    }

    public void addCard(MetroCard newCard) {
        store.add(newCard);
    }

    public boolean removeCard(String serNum) {
        int index = findMetroCard(serNum);
        if (index >= 0) {
            store.remove(index);
            return true;
        }
        return false;
    }

    public boolean addMoney(String serNum, double money) {
        int index = findMetroCard(serNum);
        if (index >= 0) {
            store.get(index).setBalance(store.get(index).getBalance() + money);
            return true;
        }
        return false;
    }

    public boolean getMoney(String serNum, double money) {
        int index = findMetroCard(serNum);
        if (index >= 0) {
            double balance = store.get(index).getBalance();
            if (balance >= money) {
                store.get(index).setBalance(balance - money);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("List of MetroCards:");
        for (MetroCard c : store) {
            buf.append("\n\n" + c);
        }
        return buf.toString();
    }
}