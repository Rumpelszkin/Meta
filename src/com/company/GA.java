package com.company;

import java.util.ArrayList;
import java.util.Random;

public class GA {

    double Px;
    double Pm;
    ArrayList<Item> itemArrayList;
    double CAPACITY_OF_KNAPSACK;
    Random rand;

    public GA(double px, double pm, ArrayList<Item> itemArrayList, double CAPACITY_OF_KNAPSACK) {
        Px = px;
        Pm = pm;
        this.itemArrayList = itemArrayList;
        this.CAPACITY_OF_KNAPSACK = CAPACITY_OF_KNAPSACK;
        rand = new Random();
    }

    public Population runGA(Population population){

        ArrayList<Entity> startPop = population.getPopulacja();

        int secondToMutationIndex;
        ArrayList<Entity> newPop = new ArrayList<>();

        for(int i= 0; i<startPop.size();i++) {

           /*
            secondToMutationIndex = rand.nextInt(startPop.size());
            while(secondToMutationIndex == i){
                secondToMutationIndex = rand.nextInt(startPop.size());
            }*/
           int x=0;
            int y=0;

            boolean flag = true;
            while (flag){
                x = tournament(20, population);
             y = tournament(20, population);
            if(x!=y) flag = false;
        }
            newPop.add(mutate(fix(cross(startPop.get(x),startPop.get(y)))));
        }

        Population newPopulation = new Population(newPop);
        return newPopulation;
    }

    public Entity cross(Entity entity1,Entity entity2){
        double pxprobability = rand.nextDouble();
        if(Px<pxprobability) return entity1;

        int sliceIndex = rand.nextInt(entity1.getCitiesArray().length);
        int[] newCitiesArray = new int[entity1.getCitiesArray().length];

        for(int i = 0; i<sliceIndex;i++){
            newCitiesArray[i] = entity1.getCitiesArray()[i];
        }

        for(int i = sliceIndex; i<entity1.getCitiesArray().length;i++){
            newCitiesArray[i] = entity2.getCitiesArray()[i];
        }

        sliceIndex = rand.nextInt(entity1.getItemsArray().length);
        int[] newItemsArray = new int[entity1.getItemsArray().length];
        for(int i = 0; i<entity1.getItemsArray().length;i++){
            newItemsArray[i] = entity1.getItemsArray()[i];
        }
        for(int i = sliceIndex; i<entity1.getItemsArray().length;i++){
            newItemsArray[i] = -1;
        }

        Entity newEntity = new Entity(newCitiesArray,newItemsArray);

            return newEntity;
    }

    public Entity fix(Entity entity){
        int actualKnapsackWeight = 0;
        for(int i = 0; i<entity.getItemsArray().length;i++){
            if(entity.getItemsArray()[i] != -1){
                actualKnapsackWeight += itemArrayList.get(i).getWeight();
            }
        }

        int fixCounter = 100;
        int fixItemIndex;
        while(fixCounter>0&& actualKnapsackWeight<CAPACITY_OF_KNAPSACK){
            fixItemIndex = rand.nextInt(entity.getItemsArray().length);
            if(entity.getItemsArray()[fixItemIndex] == -1 &&((actualKnapsackWeight+itemArrayList.get(fixItemIndex).getWeight())<=CAPACITY_OF_KNAPSACK)){
                entity.getItemsArray()[fixItemIndex] = itemArrayList.get(fixItemIndex).getAssignedNode();
                actualKnapsackWeight += itemArrayList.get(fixItemIndex).getWeight();
            }
        fixCounter--;
        }
        return entity;
    }

    public Entity mutate(Entity entity){
        entity.mutuj(Pm);
        return entity;
    }

    public int tournament(int size,Population population){
        int temp = rand.nextInt(population.getPopulacja().size());
        int best = temp;
        for(int i =0; i <size-1;i++){

            temp = rand.nextInt(population.getPopulacja().size());
            if(population.getPopulacja().get(best).getFitness()<population.getPopulacja().get(temp).getFitness()) best = temp;
        }

        return best;
    }
}
