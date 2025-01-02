package ite.kubak.common;

public class FoodQueue extends Queue{

    public FoodQueue(int id){
        super(id);
    }

    public synchronized boolean get_first_client_meal(){
        if(!clients.isEmpty()) return clients.get(0).got_meal();
        else return true;
    }



}
