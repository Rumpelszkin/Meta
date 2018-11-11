package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;

public class Tabu {

    ArrayList<Entity> theBestsXD;
    Entity lastBest;
    int helpListSize = 20;
    Program p;
    ArrayList<Entity> tabuList;

    public Tabu(Entity first,Program p){
        theBestsXD = new ArrayList<>();
        theBestsXD.add(first);
        this.p = p;
        p.TTP1(first);
        tabuList = new ArrayList<Entity>();
    }


    public void runTabu(){
        ArrayList<Entity> helpList= new ArrayList<>();
        Entity last = theBestsXD.get(theBestsXD.size()-1);
        for(int i =1;i<last.getCitiesArray().length;i++){
           helpList.add(last.swapCities(i));
        }

        for(int i = 0;i<helpList.size();i++){
            p.TTP1(helpList.get(i));
        }
        helpList = getRandom(helpList);

        boolean flag = true;
        while(flag) {
            Entity best = findBest(helpList);
            if (!contains1(best)){
                theBestsXD.add(best);
                flag = false;
           }
            else helpList.remove(best);
        }
    }

    public void runTabu1(int dlugoscListySasiadow, int dlugoscListyTabu){
        ArrayList<Entity> helpList= new ArrayList<>();
        Entity last = theBestsXD.get(theBestsXD.size()-1);
        Random random = new Random();
        for(int i =1;i<dlugoscListySasiadow;i++){
            helpList.add(last.swapCities(i));
        }

        for(int i = 0;i<helpList.size();i++){
            p.TTP1(helpList.get(i));
        }
        helpList = getRandom(helpList);

        boolean flag = true;
        while(flag) {
            Entity best = findBest(helpList);
            if (!contains(best)){
                theBestsXD.add(best);
                addToTabu(dlugoscListyTabu,best);
                flag = false;
            }
            else helpList.remove(best);
        }
    }


    public void addToTabu(int dlugoscListyTabu,Entity e){
        if(tabuList.size()==dlugoscListyTabu){
            tabuList.remove(0);
            tabuList.add(e);

        }else tabuList.add(e);
    }

    public boolean contains(Entity e){
        for(int i = 0; i <theBestsXD.size();i++){
            if(theBestsXD.get(i).equals(e)) return true;
        }
        return false;
    }

    public boolean contains1(Entity e){
        for(int i = 0; i <tabuList.size();i++){
            if(tabuList.get(i).equals(e)) return true;
        }
        return false;
    }
    public Entity findBest(ArrayList<Entity> helpList){

        Entity best = new Entity();

        best.setFitness(-Double.MAX_VALUE);


        for(int i=0;i<helpList.size();i++){
            if(best.getFitness()<p.TTP1(helpList.get(i)) && !contains(helpList.get(i))){
                best.setFitness(helpList.get(i).getFitness());
                best.setCitiesArray(helpList.get(i).getCitiesArray());
                best.setItemsArray(helpList.get(i).getItemsArray());
            }
        }
        return best;
    }
    public void showDaWay() throws FileNotFoundException {

        Entity bestE = new Entity();
        bestE.setFitness(-Double.MAX_VALUE);
        Random rand = new Random();
        int xdD = rand.nextInt(Integer.MAX_VALUE);
        PrintWriter pw = new PrintWriter(new File("Tabu"+ xdD+".csv"));
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< theBestsXD.size();i++){
            if(theBestsXD.get(i).getFitness()>bestE.getFitness()){
                bestE = theBestsXD.get(i);
            }

            sb.append(bestE.getFitness()+","+ theBestsXD.get(i).getFitness()+"\n");
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }

    public ArrayList<Entity> getRandom(ArrayList helpList) {
        ArrayList<Entity> lotnisko = new ArrayList<>();
        Random rand = new Random();

        for(int i = 0;i<helpList.size();i++){
            lotnisko.add((Entity) helpList.get(rand.nextInt(helpList.size())));
        }
        return lotnisko;
    }
}
