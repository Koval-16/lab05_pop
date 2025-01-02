package ite.kubak.people;

import ite.kubak.communication.Lunchroom;

import java.util.Random;

public class Cashier extends Thread{

    private int id;

    public Cashier(int id){
        this.id = id;
    }

    @Override
    public void run(){
        Random random = new Random();
        long lastUpdateTime = System.currentTimeMillis();
        while(true){
            if(!Lunchroom.cashQueues.get(id).get_first_client_paid()){
                int delay = random.nextInt(5000)+10000;
                try {
                    Cashier.sleep(delay);
                    get_payment(Lunchroom.cashQueues.get(id).get_queue().get(0));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(System.currentTimeMillis()-lastUpdateTime>=20000){
                if(random.nextInt(10)==0) Lunchroom.cashQueues.get(id).cash_switch();
                lastUpdateTime = System.currentTimeMillis();
            }
        }
    }

    public void get_payment(Client client){
        client.pay();
    }

}
