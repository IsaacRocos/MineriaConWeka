/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.trees.RandomTree;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;

/**
 *
 * @author Isaac
 */
public class MineroControler {

    FileReader fuente_arff;
    String valorCalculado;

    public void setARFF(String nArchivo, String nuevoRegistro) {
        
        if(nuevoRegistro != null){
            try {
                System.out.println("Agregando nueva evaluación");
                FileWriter fwriter = new FileWriter(nArchivo,true);
                fwriter.write("\n"+nuevoRegistro+",?");
                fwriter.close();
            } catch (IOException ex) {
                System.out.println("Problemas al agregar nuevo registro");
            }
        }
        
        try {
            fuente_arff = new FileReader(nArchivo);
            BufferedReader breader = null;
            breader = new BufferedReader(fuente_arff); 
        } catch (FileNotFoundException ex) {
            System.out.println("Ocurrio un problema durante la carga de la fuente de datos");
        }
    }

// ------------------------------------------------------------------------------
// Obtiene un modelo matematic para calcular el valor de una variable desconocida
// -------------------------------------------------------------------------------
// Recibe tablas con valores numericos unicamente
// Retorna: String con resultado del calculo
// -------------------------------------------------------------------
    public String regresionLineal() {
        BufferedReader breader = null;
        Instances datos = null;
        breader = new BufferedReader(fuente_arff); 
        try {
            datos = new Instances(breader);
            datos.setClassIndex(datos.numAttributes() - 1); // clase principal, última en atributos
        } catch (IOException ex) {
            System.err.println("Problemas al intentar cargar los datos");
        }

        LinearRegression regresionL = new LinearRegression();
        try {
            
            regresionL.buildClassifier(datos);
            
            Instance nuevaCal = datos.lastInstance();
            double calif = regresionL.classifyInstance(nuevaCal);
             
            setValorCalculado(new Double(calif));
            
        } catch (Exception ex) {
            System.err.println("Problemas al clasificar instancia");
        }

        return regresionL.toString();
    }

    
    //
    public void setValorCalculado(Double valor){
        valorCalculado = valor.toString();
    }
    
    public String getValorCalculado(){
        return valorCalculado;
    }
    
    
// ------------------------------------------------------------------------------
// Realiza una clasificación con los datos obtenidos
// -------------------------------------------------------------------------------
// Recibe tablas con valores numericos y clases
// Retorna: String con resultado de clasificación
// -------------------------------------------------------------------
    public String clasificarSimpleKmeans(int numClusters) {
        BufferedReader breader = null;
        Instances datos = null;
        breader = new BufferedReader(fuente_arff); 
        try {
            datos = new Instances(breader);
        } catch (IOException ex) {
            System.err.println("Problemas al intentar cargar los datos");
        }

        SimpleKMeans skmeans = new SimpleKMeans();

        try {
            skmeans.setSeed(10);
            skmeans.setPreserveInstancesOrder(true);
            skmeans.setNumClusters(numClusters);
            skmeans.buildClusterer(datos);
        } catch (Exception ex) {
            System.err.println("Problemas al ejecutar algorimo de clasificacion");
        }
        return skmeans.toString();
    }

    
    
// ------------------------------------------------------------------------------
// Realiza una clasificación con los datos obtenidos - RANDOM TREE
// -------------------------------------------------------------------------------
// Recibe: Tablas con valores numericos y clases. La clase por analizae debe 
// ser la última en la tabla.
// Retorna: String con resultado de clasificación
// ------------------------------------------------------------------- 

    public String clasificardorArbolAleat(String atributo) {
        BufferedReader breader = null;
        Instances datos = null;
        breader = new BufferedReader(fuente_arff); 
        try {
            datos = new Instances(breader);
            Attribute atr =  datos.attribute(atributo);
            datos.setClass(atr);
            //datos.setClassIndex(0);
        } catch (IOException ex) {
            System.err.println("Problemas al intentar cargar los datos");
            return null;
        }

        RandomTree arbol = new RandomTree();  // Class for constructing a tree that considers K randomly chosen attributes at each node. 

        try {
            
            arbol.setNumFolds(100);
            arbol.setKValue(0);
            arbol.setMinNum(1);
            arbol.setMaxDepth(0);
            arbol.setSeed(1);
            arbol.buildClassifier(datos);
            
        } catch (Exception ex) {
            System.err.println("Problemas al ejecutar algorimo de clasificacion" + ex.getLocalizedMessage());
        }
        return arbol.toString();
    }
    
    
    
}//class

