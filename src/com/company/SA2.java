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
    public Entity runSA(Entity startEntity) throws FileNotFoundException {

     //   actBest = startEntity;
     //   program.TTP1(actBest);
        EntityCreator entityCreator= new EntityCreator(program.xd);
        Random random = new Random();
      //  PrintWriter pw = new PrintWriter(new File("SA\\hard02\\SA"+random.nextInt() +".csv"));
       // StringBuilder sb = new StringBuilder();

        int liczbaMiast = startEntity.getCitiesArray().length;


        program.TTP1(startEntity);
        Entity roundBest = startEntity;
        Entity everBest = new Entity(startEntity);
        Entity temp;



        while(ACT_TEMP>MIN_TEMP){


            for(int i =0; i<iloscGenerowanychSasiadow;i++){

                Entity tempp = roundBest.swapCities(random.nextInt(liczbaMiast),random.nextInt(liczbaMiast));
                entityCreator.greedyKNP(tempp);
                program.TTP1(tempp);

                if(tempp.getFitness()>roundBest.getFitness()){
                    roundBest = tempp;


                }else if(random.nextDouble()< Math.exp((tempp.getFitness()-everBest.getFitness())/ACT_TEMP)){
                    roundBest=tempp;

                }
                if(roundBest.getFitness()>everBest.getFitness()){
                    everBest = new Entity(tempp);
                }
            }




     /*   while(ACT_TEMP>MIN_TEMP){

            Entity tempBest = new Entity(actBest);

            for(int i =0; i<iloscGenerowanychSasiadow;i++){
                Entity temp = tempBest.swapCities(random.nextInt(liczbaMiast),random.nextInt(liczbaMiast));
                program.TTP1(temp);
            if(temp.getFitness()>actBest.getFitness()){
                tempBest = new Entity(temp);
                if(tempBest.getFitness()>everBest.getFitness()){
                    everBest = new Entity(temp);
                }

            }else if(random.nextDouble()< Math.exp((tempBest.getFitness()-temp.getFitness())/ACT_TEMP)){
                tempBest=new Entity(temp);

            }
            }
            actBest = new Entity(tempBest);
*/
         //   sb.append(everBest.getFitness()+","+ roundBest.getFitness()+"\n");
            czas++;
            funkcjaZmianyTemperatury();

        }

      //  pw.write(sb.toString());
       // pw.close();
        System.out.println("SA done!");

        return everBest;
    }

    public void funkcjaZmianyTemperatury(){
        ACT_TEMP = MAX_TEMP -czas*TEMP_STEP;
    }
}
