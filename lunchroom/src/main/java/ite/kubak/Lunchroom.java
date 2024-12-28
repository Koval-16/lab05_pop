package ite.kubak;

import ite.kubak.common.CashQueue;
import ite.kubak.common.FoodQueue;
import ite.kubak.common.Queue;
import ite.kubak.common.Table;
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

    public static List<Table> tables = new ArrayList<>();

    public static boolean open = true;

    public static void progress() throws InterruptedException {
        for(int i=0; i<2; i++){
            foodQueues.add(new FoodQueue(i));
            kitchenWorkers.add(new KitchenWorker(i));
            kitchenWorkers.get(i).start();
            tables.add(new Table(7));
        }
        for(int i=0; i<4; i++){
            cashQueues.add(new CashQueue(i));
            cashiers.add(new Cashier(i));
            cashiers.get(i).start();
        }
        Random random = new Random();
        for(int i=0; i<100000;){
            boolean y = random.nextBoolean();
            if(y) {
                spawn_client();
            }
            Lunchroom.sleep(1000);
            i += 1000;
            for(int j=0; j<2; j++){
                System.out.print(j+" === ");
                for(Client client : foodQueues.get(j).get_queue()){
                    System.out.print(client.get_name()+" : ");
                }
                System.out.println();
            }
            for(int j=0; j<4; j++){
                System.out.print(j+" --- ");
                for(Client client : cashQueues.get(j).get_queue()){
                    System.out.print(client.get_name()+" : ");
                }
                System.out.println();
            }
            for(int j=0; j<2; j++){
                tables.get(j).print_tables();
            }
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

}
