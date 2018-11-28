package com.company;

import java.util.ArrayList;

public class Population {

    private ArrayList<Entity> populacja;
    private double best;
    private double avg;
    private double worst;
    private Entity bestEntity;

    public Population(ArrayList<Entity> populacja) {
        this.populacja = populacja;
    }

    public Population(Population p){
        populacja = new ArrayList<>();
        for(int i = 0; i< p.getPopulacja().size();i++){
            this.populacja.add(new Entity(p.populacja.get(i)));
        }
    }
    public ArrayList<Entity> getPopulacja() {
        return populacja;
    }

    public void ocenPopulacje(Program program){

        best = -Double.MAX_VALUE;
        avg = 0;
        worst = Double.MAX_VALUE;
        bestEntity = new Entity();
        bestEntity.setFitness(best);

        for(int i = 0;i<populacja.size();i++){
            program.TTP1(populacja.get(i));
            if(best< populacja.get(i).getFitness())
            {
                best = populacja.get(i).getFitness();
                bestEntity = populacja.get(i);

            }
            if(worst> populacja.get(i).getFitness()){
                worst = populacja.get(i).getFitness();

            }
            avg += populacja.get(i).getFitness();
        }
        avg /=populacja.size();
    }

    public void setPopulacja(ArrayList<Entity> populacja) {
        this.populacja = populacja;
    }

    public double getBest() {
        return best;
    }

    public void setBest(double best) {
        this.best = best;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getWorst() {
        return worst;
    }

    public void setWorst(double worst) {
        this.worst = worst;
    }

    public Entity getBestEntity() {
        return bestEntity;
    }

    public void setBestEntity(Entity bestEntity) {
        this.bestEntity = bestEntity;
    }

    public String toString(){
        return best + "," + avg + ","+ worst+'\n';
    }
}
