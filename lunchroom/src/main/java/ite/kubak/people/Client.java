package ite.kubak.people;

import ite.kubak.Lunchroom;
import ite.kubak.common.CashQueue;
import ite.kubak.common.FoodQueue;
import ite.kubak.common.Queue;
import ite.kubak.common.Table;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println("client "+name+group);
        queue_up(Lunchroom.foodQueues);
        while(!gotMeal){
            try {
                Client.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(name+" got his meal!");
        leave_queue(Lunchroom.foodQueues);
        queue_up(Lunchroom.cashQueues);
        while(!paid){
            try {
                Client.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(name+" paid!");
        leave_queue(Lunchroom.cashQueues);
        take_seat(Lunchroom.tables);
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

    public void find_seat(){}

    public void take_seat(List<Table> tables){
        List<Integer> scores = new ArrayList<>();
        for(int i=0; i<tables.size(); i++) scores.add(tables.get(i).calculate_seat(this));
        int best=-1;
        int id=0;
        for(int i=0; i<scores.size(); i++){
            if(scores.get(i)>best) id=i;
        }
        tables.get(id).take_seat(this,id);
    }

    public void leave(){}

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
