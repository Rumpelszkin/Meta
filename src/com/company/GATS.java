package com.company;

import java.util.ArrayList;
import java.util.Random;

public class GATS {

    double crossProbability;
    double mutationProbability;
    int tournamentSize;
    int populationSize;
    int numberOfGARepetitons;

    int tabuListSize;
    int neighboursListSize;
    int numberOfTabuRepetitons;
    Random random;

    EntityCreator entityCreator;

    public GATS(double crossProbability, double mutationProbability, int tournamentSize, int populationSize, int numberOfGARepetitons, int tabuListSize, int neighboursListSize, int numberOfTabuRepetitons) {
        this.crossProbability = crossProbability;
        this.mutationProbability = mutationProbability;
        this.tournamentSize = tournamentSize;
        this.populationSize = populationSize;
        this.numberOfGARepetitons = numberOfGARepetitons;
        this.tabuListSize = tabuListSize;
        this.neighboursListSize = neighboursListSize;
        this.numberOfTabuRepetitons = numberOfTabuRepetitons;
        random = new Random();
    }

    private void runGATS(Program program){

        entityCreator = new EntityCreator(program.xd);
        PopulationCreator  pc = new PopulationCreator(program.xd);
        Population beginingPopulation = pc.createPopulation(populationSize);
        beginingPopulation.ocenPopulacje(program);
        ArrayList<Entity> newPopulation = new ArrayList<Entity>();

        for(int i = 0; i < numberOfGARepetitons;i++)
        {


            for(int j = 0; j<populationSize;j++)
            {
                Entity firstNew,secondNew;
                Entity one = tournament(beginingPopulation);
                Entity two = tournament(beginingPopulation);
                if(random.nextDouble() > crossProbability)
                {
                   firstNew = cross(one,two);
                   secondNew = cross(two,one);
                }else {
                    firstNew = one;
                    secondNew = two;
                }

                if(random.nextDouble()>mutationProbability)
                {
                    mutate(firstNew);
                }
                if(random.nextDouble()>mutationProbability)
                {
                    mutate(secondNew);
                }
                tabu(firstNew);
                tabu(secondNew);

                newPopulation.add(firstNew);
                newPopulation.add(secondNew);
            }
        }
        beginingPopulation = new Population(newPopulation);
    }

    public Entity tournament(Population population){
        ArrayList<Entity> touranemntArray = new ArrayList<>();
        Entity entity = new Entity();
        entity.setFitness(-Double.MAX_VALUE);

        for(int i = 0; i<tournamentSize;i++){
            int x = random.nextInt(populationSize);
            if(entity.getFitness()<population.getPopulacja().get(x).getFitness()){
                entity=population.getPopulacja().get(x);
            }
        }
        return entity;
    }

    private Entity cross(Entity entity1, Entity entity2){
        double px= random.nextDouble();
        if(crossProbability<px) return entity1;

        int sliceIndex = random.nextInt(entity1.getCitiesArray().length);
        int[] newCitiesArray = new int[entity1.getCitiesArray().length];

        for(int i = 0; i<sliceIndex;i++){
            newCitiesArray[i] = entity1.getCitiesArray()[i];
        }

        for(int i = sliceIndex; i<entity1.getCitiesArray().length;i++){
            newCitiesArray[i] = entity2.getCitiesArray()[i];
        }

        sliceIndex = random.nextInt(entity1.getItemsArray().length);
        int[] newItemsArray = new int[entity1.getItemsArray().length];
        for(int i = 0; i<entity1.getItemsArray().length;i++){
            newItemsArray[i] = entity1.getItemsArray()[i];
        }
        for(int i = sliceIndex; i<entity1.getItemsArray().length;i++){
            newItemsArray[i] = -1;
        }

        Entity newEntity = new Entity(newCitiesArray,newItemsArray);

        entityCreator.greedyKNP(newEntity);

        return newEntity;
    }

    private void mutate(Entity entity){
        entity.mutuj(mutationProbability);
    }

    private void tabu(Entity entity){

    }

}
