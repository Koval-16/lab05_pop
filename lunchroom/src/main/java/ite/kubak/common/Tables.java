package ite.kubak.common;

import ite.kubak.people.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tables {

    private List<Table> tables = new ArrayList<>();


    public synchronized boolean is_any_seat_available(){
        boolean isAny = false;
        for(int i=0; i<tables.size(); i++){
            for(int j=0; j<tables.get(i).getClients().size(); j++){
                if(tables.get(i).getClients().get(j)==null) isAny = true;
            }
        }
        System.out.println(isAny);
        return isAny;
    }

    public synchronized void choose_seat(Client client) throws InterruptedException {
        Random random = new Random();
        while(true){
            if(is_any_seat_available()){
                int[] bestSeat = {-1, Integer.MIN_VALUE};
                int bestTable = -1;
                for(int i=0; i<tables.size();i++){
                    int[] temp = tables.get(i).calculate_seat(client);
                    if(temp[1]>bestSeat[1]){
                        bestSeat = temp;
                        bestTable = i;
                    }
                    else if(temp[1]==bestSeat[1]) if(random.nextBoolean()){
                        bestSeat = temp;
                        bestTable = i;
                    }
                }
                tables.get(bestTable).take_seat(client,bestSeat[0]);
                break;
            }
            else wait();
        }
    }

    public List<Table> getTables() {
        return tables;
    }
}
