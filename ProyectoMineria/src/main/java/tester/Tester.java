/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import controller.MineroControler;
/**
 *
 * @author Isaac
 */
public class Tester {
    
    
    public static void main(String[] args) {
        MineroControler minero = new MineroControler();
        minero.setARFF("C:\\arff\\ALUMNO_CALIFS.arff", "26,1,10");  // \\Users\\Isaac\\Desktop\\ProyectoMineria
        System.out.println( minero.regresionLineal() );
        System.out.println("Valor obtenido: " + minero.getValorCalculado());
//        minero.setARFF("C:\\Users\\Isaac\\Desktop\\ProyectoMineria\\ALUMNO_GEN_ED_CAL.arff");
//        System.out.println( minero.clasificarSimpleKmeans() );
//        minero.setARFF("C:\\Users\\Isaac\\Desktop\\ProyectoMineria\\ALUMNO_GEN_ED_CAL.arff");
//        System.out.println( minero.clasificardorArbolAleat("genero") );
        
    }
    
}
