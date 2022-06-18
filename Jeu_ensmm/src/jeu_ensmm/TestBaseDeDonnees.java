/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu_ensmm;

import java.util.ArrayList;

/**
 *
 * @author cdebremo
 */
public class TestBaseDeDonnees {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int classement[][] = new int[6][2];
        classement[1][1]=1;
        classement[1][0]=2;
        System.out.println(Sort2DArrayBasedOnColumnNumber(classement,2));
    }

    private static boolean Sort2DArrayBasedOnColumnNumber(int[][] classement, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
