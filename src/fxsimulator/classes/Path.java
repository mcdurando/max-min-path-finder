/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxsimulator.classes;

import java.util.ArrayList;

/**
 *
 * @author Mac Carthy
 */
public class Path {
    
    private ArrayList path ;
    private double cost ;

    public Path(ArrayList path, double cost) {
        this.path = path;
        this.cost = cost;
    }

    
    public ArrayList getPath() {
        return path;
    }

    public void setPath(ArrayList path) {
        this.path = path;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

   
}
