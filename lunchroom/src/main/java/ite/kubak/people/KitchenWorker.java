package ite.kubak.people;

import ite.kubak.communication.Lunchroom;

import java.util.Random;

public class KitchenWorker extends Thread{

    private int id;

    public KitchenWorker(int id){
        this.id = id;
    }

    @Override
    public void run(){
        while(true){
            if(!Lunchroom.foodQueues.get(id).get_first_client_meal()){
                Random random = new Random();
                int delay = random.nextInt(5000)+5000;
                try {
                    KitchenWorker.sleep(delay);
                    give_food(Lunchroom.foodQueues.get(id).get_queue().get(0));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void give_food(Client client){
        client.setGotMeal();
    }

}
