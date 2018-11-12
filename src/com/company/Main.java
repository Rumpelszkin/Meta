package com.company;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String location = "C:\\Users\\Max\\Desktop\\Study\\Metaheurystyki\\ttp_student\\";
        Loader xd = new Loader(location + "easy_0.ttp");
        Program p = new Program(xd);


        //p.runTabu2(40,20,1000);
        p.runSA(1000,10,5,40);
       //p.runGeneticAlgorithm(100,100, 0, 0);

    }
}
