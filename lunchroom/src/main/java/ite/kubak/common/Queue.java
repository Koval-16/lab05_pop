package ite.kubak.common;

import ite.kubak.people.Client;

import java.util.ArrayList;
import java.util.List;

public class Queue {

    protected List<Client> clients;
    private int id;
    protected boolean isOpen;

    public Queue(int id){
        clients = new ArrayList<>();
        isOpen = true;
        this.id = id;
    }

    public synchronized Client get_first_client(){
        if(!clients.isEmpty()) return clients.get(0);
        else return null;
    }


    public synchronized List<Client> get_queue(){
        return clients;
    }

    public synchronized void add_client(Client client){
        clients.add(client);
    }

    public synchronized void remove_client(){
        clients.remove(0);
    }

    public synchronized boolean is_open(){
        return isOpen;
    }

    public synchronized StringBuilder print(String string, int i){
        StringBuilder builder = new StringBuilder();
        builder.append(string).append(" ").append(i+1);
        if(isOpen) builder.append(" [").append("O").append("]").append(" : ");
        else builder.append(" [").append("X").append("]").append(" : ");
        for(Client client : clients) builder.append(client.get_name()).append(" ");
        builder.append("\n");
        return builder;
    }

    public int getId(){
        return id;
    }

}
