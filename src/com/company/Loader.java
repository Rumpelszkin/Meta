package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loader {

    List<String> infoList;
    double[][] cityLocationMatrix;
    ArrayList<Item> itemsList;


    public Loader(String fileLocation) throws FileNotFoundException {
        load(fileLocation);
    }


    public void load(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner input = new Scanner(file);
        List<String> list = new ArrayList<String>();


        while (input.hasNextLine()) {//Lista stringow z tekstu
            list.add(input.nextLine());
        }
        infoList= new ArrayList<String>();
        for(int i = 0;i<list.size();i++)//od 0 do 9  wyswietla dane
        {
            //  System.out.println(list.get(i));
            infoList.add(list.get(i));
        }
        int numberOfCities = Integer.parseInt(list.get(2).split("\t+")[1]);//z 3. lini liczba miast
        int numberOfItems = Integer.parseInt(list.get(3).split("\t+")[1]);//z 4. lini liczba itemow
        //System.out.println(numberOfCities +" " +numberOfItems);

        cityLocationMatrix = new double[numberOfCities][2];
        for(int i = 10; i<10+numberOfCities;i++){
            String[] temp = list.get(i).split("\t+");
            int index = Integer.parseInt(temp[0]);
            double[] temp2= {Double.parseDouble(temp[1]),Double.parseDouble(temp[2])};
            cityLocationMatrix[index-1] = temp2;
        }
        for(int i = 0; i<52; i++){
            for(int ii = 0; ii <2;ii++){
               //  System.out.print(cityLocationMatrix[i][ii] +" ");
            }
           //  System.out.println(i);
        }
        itemsList= new ArrayList<Item>();
        for(int i = 11+numberOfCities; i<11+numberOfCities+numberOfItems;i++) {
            String[] temp = list.get(i).split("\t+");

            int index = Integer.parseInt(temp[0]);
            int profit = Integer.parseInt(temp[1]);
            int weight = Integer.parseInt(temp[2]);
            int assignedNode = Integer.parseInt(temp[3]);
            Item tempItem = new Item(index,profit,weight,assignedNode);
            itemsList.add(tempItem);
        }
       /* for(int y = 0; y<numberOfItems;y++){
            System.out.println(itemsList.get(y).getIndex()+" "+itemsList.get(y).getProfit()+" "+itemsList.get(y).getWeight()+" "+itemsList.get(y).getAssignedNode());
        }*/

    }
}
