package com.company;

import java.util.Random;

public class Entity{

    private int[] citiesArray;
    private int[] itemsArray;
    private double fitness;

    public Entity(){}
    public Entity(int[] citiesArray, int[] itemsArray) {
        this.citiesArray = citiesArray;
        this.itemsArray = itemsArray;
    }

    public Entity(Entity e){
        citiesArray = new int[e.citiesArray.length];
        for(int i=0; i<e.citiesArray.length;i++){
            this.citiesArray[i] = new Integer(e.citiesArray[i]);
        }

        itemsArray = new int[e.itemsArray.length];
        for(int i=0; i<e.itemsArray.length;i++){
            this.itemsArray[i] = new Integer(e.itemsArray[i]);
        }
        fitness = e.getFitness();
    }
    public int[] getCitiesArray() {
        return citiesArray;
    }

    public void setCitiesArray(int[] citiesArray) {
        this.citiesArray = citiesArray;
    }

    public int[] getItemsArray() {
        return itemsArray;
    }

    public void setItemsArray(int[] itemsArray) {
        this.itemsArray = itemsArray;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }



    @Override
    public boolean equals(Object obj){
        Entity second =(Entity) obj;
        for(int i = 0;i<citiesArray.length;i++){
            if(citiesArray[i]!=second.citiesArray[i]) return false;
        }
        for(int i = 0;i<itemsArray.length;i++){
            if(itemsArray[i]!=second.itemsArray[i]) return false;
        }

        return true;
    }

    public void mutuj(double Pm){
        Random rand = new Random();
        for(int i =0; i<citiesArray.length;i++){
            if(Pm<=rand.nextDouble()){
                int temp = rand.nextInt(citiesArray.length);
                if(temp!=i){
                    int help = citiesArray[i];
                    citiesArray[i] = citiesArray[temp];
                    citiesArray[temp]= help;
                }
            }
        }
    }

    public Entity swapCities(int i){
        Entity tempa = new Entity(this);

        int temp = tempa.getCitiesArray()[i];
        tempa.getCitiesArray()[i]=tempa.getCitiesArray()[i-1];
        tempa.getCitiesArray()[i-1]=temp;

        return tempa;
    }


    public Entity swapCities(int i, int j){
        Entity tempa = new Entity(this);

        int temp = tempa.getCitiesArray()[i];
        tempa.getCitiesArray()[i]=tempa.getCitiesArray()[j];
        tempa.getCitiesArray()[j]=temp;
        return tempa;
    }
}
