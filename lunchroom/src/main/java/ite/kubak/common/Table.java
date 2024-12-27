package ite.kubak.common;

import ite.kubak.people.Client;

import java.util.ArrayList;
import java.util.List;

public class Table {

    int seats;
    private List<Client> clients;

    public Table(int seats){
        this.seats = 2*seats;
        clients = new ArrayList<>(this.seats);
    }

    public synchronized int calculate_seat(Client client) {
        int id = -1;
        int best = Integer.MIN_VALUE;
        int group = client.getGroup();
        for (int i = 0; i < clients.size(); i++) {
            int result = 0;
            for (int j = 1; j < 4; j++) {
                if (i - j >= 0) {
                    if (clients.get(i - j).getGroup() == group) result += 4 - j;
                    else result -= 4 - j;
                }
                if (i + j < clients.size()) {
                    if (clients.get(i + j).getGroup() == group) result += 4 - j;
                    else result -= 4 - j;
                }
            }
            if (result > best) {
                best = result;
                id = i;
            }
        }
        return id;
    }

    public synchronized void take_seat(Client client, int index){
        clients.set(index,client);
    }


}
