package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Tabu2 {



int rozmiarListyTabu;
int liczbaGenerowanychSasiadow;
int liczbaCykli;

ArrayList<Entity> bestList;
ArrayList<Entity> listaTabu;
ArrayList<Entity> helpList;

Entity actBest;
Program program;


    public Tabu2(int rozmiarListyTabu, int liczbaGenerowanychSasiadow, int liczbaCykli, Program program) {
        this.rozmiarListyTabu = rozmiarListyTabu;
        this.liczbaGenerowanychSasiadow = liczbaGenerowanychSasiadow;
        this.liczbaCykli = liczbaCykli;
        bestList = new ArrayList<>();
        listaTabu = new ArrayList<>();
        helpList = new ArrayList<>();
        this.program = program;
    }

    public void runTabu(Entity startEntity){
        actBest = startEntity;
        for (int i = 0; i<liczbaCykli;i++){
            actBest = generujJednegoZHelpList(actBest);
            dodajDoTabuList(actBest);
            bestList.add(actBest);
        }

    }

    public Entity generujJednegoZHelpList(Entity entity){
        ArrayList<Entity> tempHelpList = new ArrayList<>();
        Random random = new Random();
        int liczbaMiast = entity.getCitiesArray().length;
        Entity tempEntity= new Entity();
        double bestFittnes = - Double.MAX_VALUE;


        for(int i = 0; i<liczbaGenerowanychSasiadow;i++){

            tempEntity = entity.swapCities(random.nextInt(liczbaMiast),random.nextInt(liczbaMiast));
            double tempFitnes = program.TTP1(tempEntity);
            if(bestFittnes <tempFitnes){
                actBest = tempEntity;
                bestFittnes = tempFitnes;
            }
        }
        return tempEntity;
    }

    public void dodajDoTabuList(Entity entity){
        if(listaTabu.size()==rozmiarListyTabu){
            listaTabu.remove(0);
            listaTabu.add(entity);
        }else listaTabu.add(entity);
    }

}
