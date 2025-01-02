package ite.kubak.people;

import ite.kubak.common.Tables;
import ite.kubak.communication.Lunchroom;
import ite.kubak.common.Queue;
import ite.kubak.common.Table;

import java.util.List;
import java.util.Random;

public class Client extends Thread{

    private String name;
    private int group;
    private int meal;
    private boolean gotMeal;
    private boolean paid;
    private boolean finished;

    public Client(String name, int group){
        this.name = name;
        this.group = group;
        finished = false;
        gotMeal = false;
    }

    @Override
    public void run(){
        queue_up(Lunchroom.foodQueues);
        while(!gotMeal){
            try {
                Client.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        leave_queue(Lunchroom.foodQueues);
        queue_up(Lunchroom.cashQueues);
        while(!paid){
            try {
                Client.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        leave_queue(Lunchroom.cashQueues);
        queue_up(Lunchroom.bufors);
        while(Lunchroom.bufors.get(0).get_first_client()!=this){
            try {
                Client.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        take_seat(Lunchroom.tables);
        leave_queue(Lunchroom.bufors);
        leave(Lunchroom.tables);
        while(!finished){}
    }

    public void queue_up(List<? extends Queue> queues){
        synchronized (queues){
            Queue shortest = queues.get(0);
            for(int i=1; i<queues.size(); i++){
                if(shortest.get_queue().size()>queues.get(i).get_queue().size() && queues.get(i).is_open()){
                    shortest = queues.get(i);
                }
            }
            shortest.add_client(this);
        }
    }


    public void leave_queue(List<? extends Queue> queues){
        for(int i=0; i<queues.size(); i++){
            if(queues.get(i).get_first_client()==this){
                synchronized (queues.get(i)){
                    queues.get(i).remove_client();
                }
            }
        }
    }

    public void pay(){
        paid = true;
    }

    public boolean got_paid(){
        return paid;
    }

    public void take_seat(Tables tables) {
        try {
            tables.choose_seat(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void leave(Tables tables){
        Random random = new Random();
        int delay = random.nextInt(20000)+10000;
        try {
            Client.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(Table table : tables.getTables()){
            table.leave_seat(this,tables);
        }
    }

    public void setGotMeal(){
        gotMeal = true;
    }

    public boolean got_meal(){
        return gotMeal;
    }

    public String get_name(){
        return name;
    }

    public int getGroup(){
        return group;
    }

}