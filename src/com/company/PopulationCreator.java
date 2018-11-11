package com.company;

import java.util.ArrayList;

public class PopulationCreator {
    EntityCreator entityCreator;
    int populationSize;

    public PopulationCreator(Loader loader){
        entityCreator = new EntityCreator(loader);
    }

    public Population createPopulation(int size){

        ArrayList<Entity> arrayList = new ArrayList();
        for(int i = 0; i< size; i++){
            arrayList.add(new Entity(entityCreator.generateEntity()));
        }

        Population newPopulation = new Population(arrayList);
        return newPopulation;
    }


}
