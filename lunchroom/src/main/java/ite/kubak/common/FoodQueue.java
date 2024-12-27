package ite.kubak.common;

import ite.kubak.people.Client;

import java.util.ArrayList;
import java.util.List;

public class FoodQueue extends Queue{

    public FoodQueue(int id){
        super(id);
    }

    public synchronized boolean get_first_client_meal(){
        if(!clients.isEmpty()) return clients.get(0).got_meal();
        else return true;
    }



}
