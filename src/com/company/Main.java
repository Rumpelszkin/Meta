package com.company;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String location = "C:\\Users\\Max\\Desktop\\Study\\Metaheurystyki\\ttp_student\\";
        Loader xd = new Loader(location + "hard_2.ttp");
        Program p = new Program(xd);

for(int i = 0; i<10;i++) {
    p.runTabu2(40, 20, 700);
    p.runSA(1200, 10, 0.1, 40);
    p.runGeneticAlgorithm(100, 300, 0.3, 0.1);
}
    }
}
