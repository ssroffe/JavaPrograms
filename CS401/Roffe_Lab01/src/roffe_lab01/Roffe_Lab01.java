/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roffe_lab01;

/**
 * Simonseth Roffe - Lab 1
 * @author sdr45
 */
public class Roffe_Lab01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String name = "Simonseth Roffe";
        double sideA = 9;
        double sideB = 4;
        double sideC = 10;
        
        double semiperimeter1 = 0.5 * (sideA + sideB + sideC);
        System.out.println("The semiperimeter of the triangle is "+semiperimeter1+".");
        
        double semiperimeter2 = (sideA+sideB+sideC)/2;
        System.out.println("The semiperimeter of the triangle is "+semiperimeter2+".");
        
        System.out.println(name+", the program is now ending.");
    }
    
}
