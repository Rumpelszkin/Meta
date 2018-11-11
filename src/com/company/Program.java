package com.company;


import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Program {

    double Px = 0.5;
    double Pm = 0.05;

    Loader xd;
    double[][] distanceMatrix;
    String PROBLEM_NAME;
    String KNAPSACK_DATA_TYPE;
    int DIMENSION;
    int NUMBER_OF_ITEMS;
    double CAPACITY_OF_KNAPSACK;
    double MIN_SPEED;
    double MAX_SPEED;
    double RENTING_RATIO;
    String EDGE_WEIGHT_TYPE;

    double ACTUAL_SPEED;
    double ACTUAL_CAPACITY_OF_KNAPSACK;
    double ACTUAL_VALUE;
    double KNAPSACK_COST;

    public Program(Loader xd) throws FileNotFoundException {

        this.xd = xd;
        fillDistanceMatrix();
        PROBLEM_NAME = xd.infoList.get(0);
        KNAPSACK_DATA_TYPE = xd.infoList.get(1);
        DIMENSION = Integer.parseInt(xd.infoList.get(2).split("\t+")[1]);
        NUMBER_OF_ITEMS = Integer.parseInt(xd.infoList.get(3).split("\t+")[1]);
        CAPACITY_OF_KNAPSACK = Integer.parseInt(xd.infoList.get(4).split("\t+")[1]);
        MIN_SPEED = Double.parseDouble(xd.infoList.get(5).split("\t+")[1]);
        MAX_SPEED = Double.parseDouble(xd.infoList.get(6).split("\t+")[1]);
        RENTING_RATIO = Double.parseDouble(xd.infoList.get(7).split("\t+")[1]);
        EDGE_WEIGHT_TYPE = xd.infoList.get(8);
        ACTUAL_SPEED = MAX_SPEED;
        ACTUAL_CAPACITY_OF_KNAPSACK = CAPACITY_OF_KNAPSACK;
        ACTUAL_VALUE = 0;
        KNAPSACK_COST = 0;
    }

    public void runGeneticAlgorithm(int populationSize, int populationsNumbes, double Px, double Pm) throws FileNotFoundException {
        PopulationCreator populationCreator = new PopulationCreator(xd);
        Population zero = populationCreator.createPopulation(populationSize);
        GA ga = new GA(Px, Pm, xd.itemsList, CAPACITY_OF_KNAPSACK);

        PrintWriter pw = new PrintWriter(new File("aPx" + Px + "_Pm_" + Pm + ".csv"));
        StringBuilder sb = new StringBuilder();

        double best = 0;
        double worst = Double.MAX_VALUE;
        double avg = 0;
        int bestIndex = 0;

        for (int rounds = 0; rounds < populationsNumbes; rounds++) {

            best = 0;
            worst = Double.MAX_VALUE;
            avg = 0;

            for (int i = 0; i < populationSize; i++) {
                double temp = TTP1(zero.getPopulacja().get(i));
                if (temp > best) {
                    zero.setBest(temp);
                    zero.setBestEntity(zero.getPopulacja().get(i));
                    best = temp;
                }
                if (temp < worst) {
                    zero.setWorst(temp);
                    worst = temp;
                }
                avg += temp;
            }
            avg /= populationSize;
            zero.setAvg(avg);
            sb.append(zero.toString());
            //----- 1. populacja oceniona dodajemy wyniki do csv!!!!!

            zero = new Population(ga.runGA(zero));
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }

    public void runTabuAlgorithm(int rounds) throws FileNotFoundException {
        EntityCreator entityCreator = new EntityCreator(xd);
        Tabu tabu = new Tabu(entityCreator.generateEntity(), this);
        for (int i = 0; i < rounds; i++) {
            tabu.runTabu();
        }

        tabu.showDaWay();
    }

    public void runTabuAlgorithm1(int rounds, int parametrListaSasiadow, int parametrDlugoscTabu) throws FileNotFoundException {
        EntityCreator entityCreator = new EntityCreator(xd);
        Tabu tabu = new Tabu(entityCreator.generateEntity(), this);
        for (int i = 0; i < rounds; i++) {
            tabu.runTabu1(parametrListaSasiadow,parametrDlugoscTabu);
        }

        tabu.showDaWay();
    }

    public void runTabu2(int rozmiarListyTabu,int liczbaGenerowanychSasiadow,int liczbaCykli) throws FileNotFoundException {
        Tabu2 tabu = new Tabu2(rozmiarListyTabu,liczbaGenerowanychSasiadow,liczbaCykli,this);
        EntityCreator entityCreator = new EntityCreator(xd);
        tabu.runTabu(entityCreator.generateEntity());
    }

    public void runSimulatedAnnealing() throws FileNotFoundException {
        EntityCreator entityCreator = new EntityCreator(xd);
        SA sa = new SA(500, 20, entityCreator.generateEntity2(), this);

        sa.runSA();
        sa.showDaWay();
    }

    public void refreshActualSpeed() {
        if (ACTUAL_CAPACITY_OF_KNAPSACK != 0)
            ACTUAL_SPEED = (ACTUAL_CAPACITY_OF_KNAPSACK / CAPACITY_OF_KNAPSACK) * (MAX_SPEED - MIN_SPEED) + MIN_SPEED;

        ACTUAL_SPEED = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * (ACTUAL_CAPACITY_OF_KNAPSACK / CAPACITY_OF_KNAPSACK);
    }

    public double TTP1(Entity entity) {
        /*  G(x,z) = g(z) - R * f(x,z)

            Fitness finesse data:
            G - value of all picked items
            R - rent per time unit price
            f - time of the tour
        */
        int[] listaMiast = entity.getCitiesArray();
        int[] listaPrzedmiotow = entity.getItemsArray();
        ACTUAL_SPEED = MAX_SPEED;
        ACTUAL_VALUE = 0;
        KNAPSACK_COST = 0;
        ACTUAL_CAPACITY_OF_KNAPSACK = CAPACITY_OF_KNAPSACK;
        double fitness = 0;
        refreshActualSpeed();
        for (int j = 0; j < listaPrzedmiotow.length; j++) {
            if (listaPrzedmiotow[j] == 0) {//indeks miasta == indeksowi miasta w ktorym jest przedmiot
                // ACTUAL_CAPACITY_OF_KNAPSACK -= xd.itemsList.get(j).getWeight();
                ACTUAL_VALUE += xd.itemsList.get(j).getProfit();
            }
        }

        KNAPSACK_COST += RENTING_RATIO * ACTUAL_SPEED * xd.cityLocationMatrix[0][1];

        for (int i = 1; i < listaMiast.length; i++) {
            for (int jj = 0; jj < listaPrzedmiotow.length; jj++) {
                if (listaPrzedmiotow[jj] == i) {//indeks miasta == indeksowi miasta w ktorym jest przedmiot
                    ACTUAL_CAPACITY_OF_KNAPSACK -= xd.itemsList.get(jj).getWeight();
                    ACTUAL_VALUE += xd.itemsList.get(jj).getProfit();
                }
            }
            refreshActualSpeed();
            KNAPSACK_COST += RENTING_RATIO * ACTUAL_SPEED * distanceMatrix[listaMiast[i - 1]][listaMiast[i]];
        }

        fitness = ACTUAL_VALUE - KNAPSACK_COST;
        entity.setFitness(fitness);
        return fitness;
    }

    public void fillDistanceMatrix() {
        distanceMatrix = new double[xd.cityLocationMatrix.length][xd.cityLocationMatrix.length];
        for (int i = 0; i < xd.cityLocationMatrix.length; i++) {
            for (int i2 = i + 1; i2 < xd.cityLocationMatrix.length; i2++) {
                double x = xd.cityLocationMatrix[i][0] - xd.cityLocationMatrix[i2][0];
                double y = xd.cityLocationMatrix[i][1] - xd.cityLocationMatrix[i2][1];
                double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                distanceMatrix[i][i2] = distance;
                distanceMatrix[i2][i] = distance;
            }
        }
    }

    public void showDistanceMatrix() {
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int ii = 0; ii < distanceMatrix.length; ii++) {
                //  System.out.print(distanceMatrix[i][ii]+ "  ");
            }
            //  System.out.println();
        }
    }
}
