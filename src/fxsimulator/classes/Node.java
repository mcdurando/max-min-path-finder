/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxsimulator.classes;


import java.util.ArrayList;
import java.util.List;
import fxsimulator.controllers.CanvasController ;

/**
 *
 * @author sowme
 */
public class Node implements Comparable<Node> {
    
    public String name;
    public List<Edge> adjacents = new ArrayList<Edge>();
    public List<Edge> revAdjacents = new ArrayList<Edge>();
    public Node previous;
    public CanvasController.NodeFX circle;
    public double minDistance = Double.POSITIVE_INFINITY;
    public boolean visited, isArticulationPoint;
    public int visitTime = 0, lowTime = 0;
    public int DAGColor;
    
    // DATA ADDITIONNELLE
    private double distanceMax = 0 ; 
    private Node sommetPrecedent ;
    
    // Nb total de noeud
    private static int compteur = 0 ;
    
    private int nodeId ;

    public Node(String argName) {
        name = argName;
        visited = false;
        
        
        // FORD
        compteur++ ;
        nodeId = compteur ;
        
    }

    public Node(String argName, CanvasController.NodeFX c) {
        name = argName;
        circle = c;
        visited = false;
        
        // FORD
        compteur++ ;
        nodeId = compteur ;
        
        System.out.println( nodeId + "/ Noeud : " + name);
        System.out.println("Compteur = " + compteur);
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(minDistance, o.minDistance);
    }

    // FORD
    
    public double getDistanceMax() {
        return distanceMax;
    }

    public void setDistanceMax(double distanceMax) {
        this.distanceMax = distanceMax;
    }

    public Node getSommetPrecedent() {
        return sommetPrecedent;
    }

    public void setSommetPrecedent(Node sommetPrecedent) {
        this.sommetPrecedent = sommetPrecedent;
    }

    public static int getCompteur() {
        return compteur;
    }

    public static void setCompteur(int compteur) {
        Node.compteur = compteur;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    
    
    
    
}
