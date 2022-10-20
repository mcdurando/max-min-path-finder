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
public class InputOuputNode {
    
    DataMatrix source , target ;
    ArrayList input , output ;

    public InputOuputNode(DataMatrix source, DataMatrix target, ArrayList input, ArrayList output) {
        this.source = source;
        this.target = target;
        this.input = input;
        this.output = output;
    }

    public DataMatrix getSource() {
        return source;
    }

    public void setSource(DataMatrix source) {
        this.source = source;
    }

    public DataMatrix getTarget() {
        return target;
    }

    public void setTarget(DataMatrix target) {
        this.target = target;
    }

    public ArrayList getInput() {
        return input;
    }

    public void setInput(ArrayList input) {
        this.input = input;
    }

    public ArrayList getOutput() {
        return output;
    }

    public void setOutput(ArrayList output) {
        this.output = output;
    }
   
}
