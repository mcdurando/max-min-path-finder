/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxsimulator.controllers;

// JAVA program to print all  
// paths from a source to 
// destination. 
import fxsimulator.ProjetROMain;
import java.util.ArrayList; 
import java.util.List; 
  
// A directed graph using 
// adjacency list representation 
public class All_path { 
    
  
    // No. of vertices in graph 
    private int v;  
      
    // adjacency list  
    public ArrayList<Integer>[] adjList;  
    
    
      
    //Constructor 
    public All_path(int vertices){ 
          
        //initialise vertex count 
        this.v = vertices + 1 ; 
          
        // initialise adjacency list 
        initAdjList();  
    } 
      
    // utility method to initialize 
    // adjacency list 
    @SuppressWarnings("unchecked") 
    private void initAdjList() 
    {
        
        adjList = new ArrayList[v]; 
          
        for(int i = 0; i < v ; i++) 
        { 
            adjList[i] = new ArrayList<>(); 
        } 
    } 
      
    // add edge from u to v 
    public void addEdge(int u, int v) 
    { 
        test() ;
        // Add v to u's list. 
        adjList[u].add(v);  
        
    } 
    
    public void test(){
        
       System.out.println("ZONE DE TEST :");
       System.out.println("adjList.length = " + adjList.length);
       
       for(int i=0 ; i < adjList.length ; i++){
           
           System.out.println("adjList[" + i + "] = " + adjList[i]);
           
//           for(int j=0 ; j < adjList[i].size() ; j++){
//               
//                System.out.println("adjList[" + i + "][" + j + "] = " + adjList[i].get(j));
//               
//           }
           
       }
        
    }
      
    // Prints all paths from 
    // 's' to 'd' 
    public void printAllPaths(int s, int d)  
    { 
        boolean[] isVisited = new boolean[v]; 
        ArrayList<Integer> pathList = new ArrayList<>(); 
          
        //add source to path[] 
        pathList.add(s); 
          
        //Call recursive utility 
        printAllPathsUtil(s, d, isVisited, pathList); 
        
        
    } 
  
    // A recursive function to print 
    // all paths from 'u' to 'd'. 
    // isVisited[] keeps track of 
    // vertices in current path. 
    // localPathList<> stores actual 
    // vertices in the current path 
    private void printAllPathsUtil(Integer u, Integer d, 
                                    boolean[] isVisited, 
                            List<Integer> localPathList) { 
          
        // Mark the current node 
        isVisited[u] = true; 
          
        if (u.equals(d))  
        { 
            
            System.out.println(localPathList); 
            // if match found then no need to traverse more till depth 
            
            // TEST
            int t ;
            ArrayList path = new ArrayList<>() ;
            
            for(int z=0 ; z < localPathList.size() ; z++){
                
                t = (int) localPathList.get(z);
                path.add(t);
            }
            
            System.out.println("Contenu = " + path); 
            HomeController.cref.tousLesChemins.add(path);
            
            // FIN TEST
            
            isVisited[u]= false; 
            return ; 
        } 
          
        // Recur for all the vertices 
        // adjacent to current vertex 
        for (Integer i : adjList[u])  
        { 
            if (!isVisited[i]) 
            { 
                // store current node  
                // in path[] 
                localPathList.add(i); 
                printAllPathsUtil(i, d, isVisited, localPathList); 
                  
                // remove current node 
                // in path[] 
                localPathList.remove(i); 
            } 
        } 
          
        // Mark the current node 
        isVisited[u] = false; 
      
    } 
  
    // Driver program 
    
    /*
    public static void main(String[] args)  
    { 
        // Create a sample graph 
        All_path g = new All_path(5); 
        g.addEdge(0,1); 
        g.addEdge(0,2); 
        g.addEdge(0,3); 
        g.addEdge(1,4); 
        g.addEdge(2,4); 
        g.addEdge(3,4); 
      
        // arbitrary source 
        int s = 0; 
      
        // arbitrary destination 
        int d = 4; 
      
        System.out.println("Following are all different paths from "+s+" to "+d); 
        g.printAllPaths(s, d); 
  
    } 
    */
} 
  
// This code is contributed by Himanshu Shekhar.