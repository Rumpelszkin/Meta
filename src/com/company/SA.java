package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class SA{
    int t = 0;
    double TEMP_START;
    double TEMP;
    double TEMP_MIN = 1;//TEMP_START*0.00001;


    int Lk;
    Entity ACTUAL;
    Program program;
    ArrayList<Entity> solutions;
    ArrayList<Entity> generatedEntitiesSource;

    public SA(double TEMP_START,int Lk,Entity entity,Program program){
        this.TEMP_START=TEMP_START;
        refreshTemp();
        this.ACTUAL = entity;

        this.program=program;
        this.Lk = Lk;
        program.TTP1(ACTUAL);
        solutions = new ArrayList<>();
        generatedEntitiesSource = new ArrayList<>();
    }

    public void runSA(){
        ArrayList<Entity> helpList= new ArrayList<>();

        t = 0;
        Random random = new Random();
        Entity actBEST;


        while(TEMP>TEMP_MIN){
            for(int i =0;i<Lk;i++){
                int x1 = random.nextInt(ACTUAL.getCitiesArray().length);
                int x2 = random.nextInt(ACTUAL.getCitiesArray().length);
                helpList.add(ACTUAL.swapCities(x1,x2));
            }

            for(int i = 0;i<helpList.size();i++){
                program.TTP1(helpList.get(i));
            }

          //  helpList = getRandom(helpList);

            for(int i =0; i<Lk;i++){//edit needed
                actBEST = helpList.get(i);
                double xx =random.nextDouble();
                double xxx =Math.exp((actBEST.getFitness()-ACTUAL.getFitness())/TEMP);


                if(ACTUAL.getFitness()< actBEST.getFitness()){
                 ACTUAL =actBEST;

                }else if(xx <xxx){
                    ACTUAL =actBEST;
                   }
            }

        solutions.add(ACTUAL);
        t++;
        refreshTemp();
        helpList= new ArrayList<>();

        }}



    public void showDaWay() throws FileNotFoundException {
        Random rand = new Random();

        Entity bestE = new Entity();
        bestE.setFitness(-Double.MAX_VALUE);

        int xdD = rand.nextInt(Integer.MAX_VALUE);
        PrintWriter pw = new PrintWriter(new File("SA"+ xdD+".csv"));
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< solutions.size();i++){

            if(solutions.get(i).getFitness()>bestE.getFitness()){
                bestE = solutions.get(i);
            }

            sb.append(bestE.getFitness()+","+solutions.get(i).getFitness()+"\n");
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("done!a");
    }

    public ArrayList<Entity> getRandom(ArrayList helpList) {
        ArrayList<Entity> lotnisko = new ArrayList<>();

        for(int i = 0;i<Lk;i++){
            lotnisko.add((Entity) helpList.get(i));
        }
        return lotnisko;
    }

    public Entity findBest(ArrayList<Entity> helpList) {

        Entity best = new Entity();

        best.setFitness(-Double.MAX_VALUE);


        for (int i = 0; i < helpList.size(); i++) {
            if (best.getFitness() < program.TTP1(helpList.get(i))) {
                best.setFitness(helpList.get(i).getFitness());
                best.setCitiesArray(helpList.get(i).getCitiesArray());
                best.setItemsArray(helpList.get(i).getItemsArray());
            }
        }
        return best;
    }

    private void refreshTemp(){
        TEMP = TEMP_START - t*2 ;
    }

}
