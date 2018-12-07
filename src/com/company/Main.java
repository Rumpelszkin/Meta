package com.company;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String location = "C:\\Users\\Max\\Desktop\\Study\\Metaheurystyki\\ttp_student\\";
        Loader xd = new Loader(location + "hard_0.ttp");
        Program p = new Program(xd);

for(int i = 0; i<4;i++) {
//    p.runTabu2(40, 20, 700);
    //  p.runSA(1200, 10, 0.1, 40);
    p.runGeneticAlgorithm(100, 200, 0.3, 0.01);
    p.runGATSreniscance(100, 200, 0.3, 0.01, 5,20,30,50);
    p.runSAinitGA(100, 200, 0.3, 0.01, 1000, 200, 0.5, 40);
}



    }
}
