package com.enlight.demo.tmp;

public class App {

    public static void main(String[] args) {
        Auto auto1 = new Auto(1L, "BMW");
        Auto auto2 = new Auto(2L, "Audi");
        Auto auto3 = new Auto(1L, "Mercedes"); // isti ID kao i auto1

        // poredjenje po vrednosti
        boolean daLiSuIdenticni = auto1.equals(auto2);
        System.out.println("### Identicni?: " + daLiSuIdenticni);

        // poredjenje po vrednosti
        boolean daLiSuIdenticniAuto1IAuto3 = auto1.equals(auto3);
        System.out.println("### Identicni Auto1 i Auto3?: " + daLiSuIdenticniAuto1IAuto3); // jesu, posto se porede samo vrednosti id polja

        // poredjenje po referenci
        boolean daLiSuReferenciIdenti = auto1 == auto3;
        System.out.println("### Da li je ista instanca u memoriji?: " + daLiSuReferenciIdenti);
    }
}
