package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class SA2 {

    private double MAX_TEMP;
    private double ACT_TEMP;
    private double MIN_TEMP;
    private double TEMP_STEP;

    Entity actBest;
    Entity everBest;

    Program program;

    int czas;
    int iloscGenerowanychSasiadow;
    public SA2(double MAX_TEMP,double MIN_TEMP,double TEMP_STEP,int iloscGenerowanychSasiadow,Program program){
        this.MAX_TEMP = MAX_TEMP;
        this.MIN_TEMP = MIN_TEMP;
        this.ACT_TEMP = MAX_TEMP;
        this.TEMP_STEP = TEMP_STEP;
        this.iloscGenerowanychSasiadow = iloscGenerowanychSasiadow;
        this.program = program;
        czas = 0;
        everBest = new Entity();
        everBest.setFitness(-Double.MAX_VALUE);
    }
    public void runSA(Entity startEntity) throws FileNotFoundException {

        actBest = startEntity;
        Random random = new Random();
        PrintWriter pw = new PrintWriter(new File("SA\\SA"+random.nextInt() +".csv"));
        StringBuilder sb = new StringBuilder();

        int liczbaMiast = startEntity.getCitiesArray().length;


        while(ACT_TEMP>MIN_TEMP){

            Entity tempBest = actBest;
            for(int i =0; i<iloscGenerowanychSasiadow;i++){
                Entity temp = actBest.swapCities(random.nextInt(liczbaMiast),random.nextInt(liczbaMiast));
                program.TTP1(temp);
            if(temp.getFitness()>actBest.getFitness()){
                tempBest = temp;
                if(tempBest.getFitness()>everBest.getFitness()){
                    everBest = temp;
                }

            }else if(random.nextDouble()< Math.exp((tempBest.getFitness()-temp.getFitness())/ACT_TEMP)){
                tempBest=temp;

            }
            }
            actBest = tempBest;

            sb.append(everBest.getFitness()+","+ actBest.getFitness()+"\n");
            funkcjaZmianyTemperatury();
            czas++;
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("SA done!");
    }

    public void funkcjaZmianyTemperatury(){
        ACT_TEMP = MAX_TEMP -czas*TEMP_STEP;
    }
}
