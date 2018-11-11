package com.company;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EntityCreator {


    int numberOfCities;
    int captacityOfKnapsack;
    int ActualCaptacityOfKnapsack;
    ArrayList<Integer> pobranePrzedmioty;
    ArrayList<ArrayList<Integer>> listaList;
    Entity entity;
    int numberOfItems;
    Loader loader;
    int ROUNDS_VALUE = 10;

    public EntityCreator(Loader loader) {


        this.loader = loader;
        this.numberOfCities = Integer.parseInt(loader.infoList.get(2).split("\t+")[1]);
        this.numberOfItems = Integer.parseInt(loader.infoList.get(3).split("\t+")[1]);
        this.captacityOfKnapsack = Integer.parseInt(loader.infoList.get(4).split("\t+")[1]);
    }

    public Entity generateEntity(){

        ActualCaptacityOfKnapsack=0;

        int[] tablica = new int[numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            tablica[i] = i;
        }
        shuffleArray(tablica);
//---------------gotowa Lista Miast----------

        int[] itemsArray = new int[numberOfItems];
        for (int i = 0; i < numberOfItems; i++) {
            itemsArray[i] = -1;
        }
/*
        int temp = 0;
        Random rand = new Random();
        int random = 0;
*/

        for(int i = numberOfCities-1;i!=0;i--){
            for(int j = 0;j<numberOfItems;j++){
                if(tablica[i]==loader.itemsList.get(j).getAssignedNode()&&(ActualCaptacityOfKnapsack+loader.itemsList.get(j).getWeight())<= captacityOfKnapsack ){
                    itemsArray[j]=tablica[i];
                    ActualCaptacityOfKnapsack += loader.itemsList.get(j).getWeight();
                }
            }
        }

        /*
        while(!isKnapsackFull()&&temp< ROUNDS_VALUE){
            random = rand.nextInt(numberOfItems);
            if(((ActualCaptacityOfKnapsack+loader.itemsList.get(random).getWeight())<= captacityOfKnapsack)&&itemsArray[random]==-1) {
                itemsArray[random] = loader.itemsList.get(random).getAssignedNode();
                ActualCaptacityOfKnapsack += loader.itemsList.get(random).getWeight();
            }
            temp++;}*/


        Entity titty = new Entity(tablica,itemsArray);

        return titty;
    }

    public Entity generateEntity2(){

        ActualCaptacityOfKnapsack=0;

        int[] tablica = new int[numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            tablica[i] = i;
        }
        shuffleArray(tablica);
//---------------gotowa Lista Miast----------

        int[] itemsArray = new int[numberOfItems];
        for (int i = 0; i < numberOfItems; i++) {
            itemsArray[i] = -1;
        }

        int temp = 0;
        Random rand = new Random();
        int random = 0;
/*

        for(int i = numberOfCities-1;i!=0;i--){
            for(int j = 0;j<numberOfItems;j++){
                if(tablica[i]==loader.itemsList.get(j).getAssignedNode()&&(ActualCaptacityOfKnapsack+loader.itemsList.get(j).getWeight())<= captacityOfKnapsack ){
                    itemsArray[j]=tablica[i];
                    ActualCaptacityOfKnapsack += loader.itemsList.get(j).getWeight();
                }
            }
        }

        */
        while(!isKnapsackFull()&&temp< ROUNDS_VALUE){
            random = rand.nextInt(numberOfItems);
            if(((ActualCaptacityOfKnapsack+loader.itemsList.get(random).getWeight())<= captacityOfKnapsack)&&itemsArray[random]==-1) {
                itemsArray[random] = loader.itemsList.get(random).getAssignedNode();
                ActualCaptacityOfKnapsack += loader.itemsList.get(random).getWeight();
            }
            temp++;}


        Entity titty = new Entity(tablica,itemsArray);

        return titty;
    }
    boolean isKnapsackFull() {
        return captacityOfKnapsack <= ActualCaptacityOfKnapsack;
    }

    void shuffleArray(int[] ar) {

        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
      /*  for (int i = 0; i < ar.length; i++) {
            ArrayList<Integer> araj = new ArrayList<>();
            araj.add(ar[i]);
            listaList.add(araj);
            //   System.out.println((araj.toString()));
        }
*/

    }

}
