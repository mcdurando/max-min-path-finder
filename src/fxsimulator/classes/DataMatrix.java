/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxsimulator.classes;


/**
 *
 * @author Mac Carthy
 */
public class DataMatrix {
    
    private String matrixName = "" ;
    private int ligne , colonne ;
    private String valeur = "" ;

    public DataMatrix(String matrixName,int ligne, int colonne, String valeur) {
        this.matrixName = matrixName ;
        this.ligne = ligne;
        this.colonne = colonne;
        this.valeur = valeur;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getMatrixName() {
        return matrixName;
    }

    public void setMatrixName(String matrixName) {
        this.matrixName = matrixName;
    }
  
    
}
