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

    public int getId(){
        return id;
    }

}
