package ite.kubak.communication;

import ite.kubak.common.*;
import ite.kubak.people.Cashier;
import ite.kubak.people.Client;
import ite.kubak.people.KitchenWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lunchroom extends Thread{

    public static List<FoodQueue> foodQueues = new ArrayList<>();
    public static List<KitchenWorker> kitchenWorkers = new ArrayList<>();

    public static List<CashQueue> cashQueues = new ArrayList<>();
    public static List<Cashier> cashiers = new ArrayList<>();

    //public static List<Table> tables = new ArrayList<>();
    public static Tables tables = new Tables();

    public static List<BuforQueue> bufors = new ArrayList<>();

    public static boolean open = true;
    private int seats;

    private final GuiUpdater guiUpdater;

    public Lunchroom(GuiUpdater guiUpdater, int seats) {
        this.guiUpdater = guiUpdater;
        this.seats = seats;
    }

    @Override
    public void run(){
        for(int i=0; i<2; i++){
            foodQueues.add(new FoodQueue(i));
            kitchenWorkers.add(new KitchenWorker(i));
            kitchenWorkers.get(i).start();
            tables.getTables().add(new Table(seats));
        }
        for(int i=0; i<4; i++){
            cashQueues.add(new CashQueue(i));
            cashiers.add(new Cashier(i));
            cashiers.get(i).start();
        }
        bufors.add(new BuforQueue(0));
        Random random = new Random();
        while(true){
            if(open){
                boolean y = random.nextBoolean();
                if(y) {
                    spawn_client();
                }
                try {
                    Lunchroom.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                try {
                    Lunchroom.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            updateGUI();
        }
    }



    private static void spawn_client(){
        Random random1 = new Random();
        char c1 = (char) (random1.nextInt(25)+65);
        char c2 = (char) (random1.nextInt(25)+65);
        StringBuilder name = new StringBuilder();
        name.append(c1);
        name.append(c2);
        Client client = new Client(name.toString(),random1.nextInt(8));
        client.start();
    }

    public static void switch_status(){
        if(open) open=false;
        else open=true;
    }

    private void updateGUI() {
        StringBuilder foodQueuesBuilder = new StringBuilder();
        for(int i=0; i<foodQueues.size(); i++) foodQueuesBuilder.append(foodQueues.get(i).print("Kuchnia",i));

        StringBuilder cashQueuesBuilder = new StringBuilder();
        for(int i=0; i<cashQueues.size(); i++) cashQueuesBuilder.append(cashQueues.get(i).print("Kasa",i));

        StringBuilder tablesBuilder = new StringBuilder();
        for(int i=0; i<tables.getTables().size(); i++) tablesBuilder.append(tables.getTables().get(i).print_table());

        guiUpdater.update(foodQueuesBuilder.toString(), cashQueuesBuilder.toString(), tablesBuilder.toString());
    }


}