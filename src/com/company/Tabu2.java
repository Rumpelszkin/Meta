package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Tabu2 {



int rozmiarListyTabu;
int liczbaGenerowanychSasiadow;
int liczbaCykli;


ArrayList<Entity> listaTabu;
ArrayList<Entity> helpList;

Entity everBest;
Entity actBest;
Program program;


    public Tabu2(int rozmiarListyTabu, int liczbaGenerowanychSasiadow, int liczbaCykli, Program program) {
        this.rozmiarListyTabu = rozmiarListyTabu;
        this.liczbaGenerowanychSasiadow = liczbaGenerowanychSasiadow;
        this.liczbaCykli = liczbaCykli;
        listaTabu = new ArrayList<>();
        helpList = new ArrayList<>();
        this.program = program;
        everBest = new Entity();
        everBest.setFitness(-Double.MAX_VALUE);
    }

    public void runTabu(Entity startEntity) throws FileNotFoundException {
        actBest = startEntity;
        Random random = new Random();
        PrintWriter pw = new PrintWriter(new File("Tabu\\Tabu"+random.nextInt() +".csv"));
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i<liczbaCykli;i++){
            generujJednegoZHelpList(actBest);
            dodajDoTabuList(actBest);
            sb.append(everBest.getFitness()+";"+ actBest.getFitness()+"\n");
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("Tabu done!");
    }

    public void generujJednegoZHelpList(Entity entity){

        Random random = new Random();
        int liczbaMiast = entity.getCitiesArray().length;
        Entity tempEntity= new Entity();
        double bestFittnes = - Double.MAX_VALUE;

        for(int i = 0; i<liczbaGenerowanychSasiadow;i++){

            tempEntity = entity.swapCities(random.nextInt(liczbaMiast),random.nextInt(liczbaMiast));
            double tempFitnes = program.TTP1(tempEntity);
            if(bestFittnes <tempFitnes&&!tabuContains(tempEntity)){
                actBest = tempEntity;
                bestFittnes = tempFitnes;
            }
            if(everBest.getFitness()<tempFitnes&&!tabuContains(tempEntity)){
                everBest = tempEntity;
            }
        }
    }

    public boolean tabuContains(Entity entity){
        for(int i = 0; i<listaTabu.size();i++){
            if(listaTabu.get(i).equals(entity)) return true;

        }
        return false;
    }

    public void dodajDoTabuList(Entity entity){
        if(listaTabu.size()==rozmiarListyTabu){
            listaTabu.remove(0);
            listaTabu.add(entity);
        }else listaTabu.add(entity);
    }

}
