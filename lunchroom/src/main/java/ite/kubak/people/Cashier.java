package ite.kubak.people;

import ite.kubak.Lunchroom;

import java.util.Random;

public class Cashier extends Thread{

    private int id;

    public Cashier(int id){
        this.id = id;
    }

    @Override
    public void run(){
        Random random = new Random();
        while(Lunchroom.open){
            if(!Lunchroom.cashQueues.get(id).get_first_client_paid()){
                int delay = random.nextInt(5000)+5000;
                try {
                    Cashier.sleep(delay);
                    get_payment(Lunchroom.cashQueues.get(id).get_queue().get(0));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            int switched = random.nextInt(20);
            if(switched==0) Lunchroom.cashQueues.get(id).cash_switch();
        }
    }

    public void get_payment(Client client){
        client.pay();
    }

}
