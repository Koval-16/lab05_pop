package ite.kubak.common;

import ite.kubak.people.Client;

import java.util.ArrayList;
import java.util.List;

public class CashQueue extends Queue{

    public CashQueue(int id){
        super(id);
    }

    public synchronized boolean get_first_client_paid(){
        if(!clients.isEmpty()) return clients.get(0).got_paid();
        else return true;
    }

    public synchronized void cash_switch(){
        if(isOpen) isOpen=false;
        else isOpen=true;
    }


}
