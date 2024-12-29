package ite.kubak.common;

import ite.kubak.people.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Table {

    int seats;
    private List<Client> clients;

    public Table(int seats){
        this.seats = 2*seats;
        clients = new ArrayList<>(this.seats);
        for(int i=0; i<this.seats; i++) clients.add(null);
    }

    public synchronized int[] calculate_seat(Client client) {
        Random random = new Random();
        List<Integer> bestSeats = new ArrayList<>();
        int best = Integer.MIN_VALUE;
        int group = client.getGroup();

        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i) == null) {
                int result = 0;
                for (int j = 1; j < 4; j++) {
                    if (i - j >= 0) {
                        Client neighbour = clients.get(i - j);
                        if (neighbour != null) {
                            if (neighbour.getGroup() == group) result += 4 - j;
                            else result -= 4 - j;
                        }
                    }
                    if (i + j < clients.size()){
                        Client neighbour = clients.get(i + j);
                        if (neighbour != null){
                            if (neighbour.getGroup() == group) result += 4 - j;
                            else result -= 4 - j;
                        }
                    }
                }
                if (result > best) {
                    best = result;
                    bestSeats.clear();
                    bestSeats.add(i);
                } else if (result == best) {
                    bestSeats.add(i);
                }
            }
        }
        if (!bestSeats.isEmpty()) {
            int seatIndex = bestSeats.get(random.nextInt(bestSeats.size()));
            return new int[]{seatIndex, best};
        } else {
            return new int[]{-1, Integer.MIN_VALUE};
        }
    }

    public synchronized void take_seat(Client client, int index){
        clients.set(index,client);
    }

    public synchronized void leave_seat(Client client){
        for(int i=0; i<clients.size(); i++) if(clients.get(i)==client) clients.set(i,null);
    }

    public synchronized StringBuilder print_table(){
        StringBuilder table = new StringBuilder();
        table.append("  ");
        for(int i=0; i<clients.size(); i+=2){
            if(clients.get(i)!=null) table.append("|").append(clients.get(i).get_name()).append(clients.get(i).getGroup()).append("|");
            else table.append("     ");
        }
        table.append("\n# ");
        for(int i=0; i<(clients.size()/2); i++) table.append("=====");
        table.append("\n  ");
        for(int i=1; i<clients.size(); i+=2){
            if(clients.get(i)!=null) table.append("|").append(clients.get(i).get_name()).append(clients.get(i).getGroup()).append("|");
            else table.append("     ");
        }
        table.append("\n");
        return table;
    }

    public synchronized List<Client> getClients(){
        return clients;
    }


}
