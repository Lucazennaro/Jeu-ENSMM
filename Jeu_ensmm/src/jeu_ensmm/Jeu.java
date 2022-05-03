package jeu_ensmm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nbouvere
 */

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class Jeu {
    private int largeur ;
    private int hauteur ;
    private int tailleTuile ;
    private int[][] decor ;
    private  ArrayList<Objet> liste;
     private BufferedImage nyancat, fond;

    public Jeu() {
        try {
            this.fond = ImageIO.read(new File("fond.jpg"));
            this.nyancat = ImageIO.read(new File("nyancat.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.liste = new ArrayList();
        //Joueur J1 = new Joueur(false, "J1",20,20,40,40,false,false,false,false,10,0);
        //liste.add(J1);
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getTailleTuile() {
        return tailleTuile;
    }

    public int[][] getDecor() {
        return decor;
    }

    public ArrayList<Objet> getListe() {
        return liste;
    }
        
    public void miseAJourHorizontale(Objet objet){
        if (this.getListe().get(this.getListe().indexOf(objet)).isDroite()== true || this.getListe().get(this.getListe().indexOf(objet)).isGauche()== true){
                this.getListe().get(this.getListe().indexOf(objet)).miseAJourCote();
        }
    }
    
    public void miseAJourVertical(Objet objet){
        if(objet instanceof Joueur)
        if(this.decor[objet.getX()][objet.getY()-objet.getHauteur()/2]==1 && (objet.isHaut()== true)){
            objet.setBas(false);
            objet.setHaut(true);
            for(int i=0; i<3;i+=1){
                objet.miseAJourVertical();
            }
            while(this.decor[objet.getX()][objet.getY()-objet.getHauteur()/2]!=1){
                objet.setBas(true);
                objet.setHaut(false);
                objet.miseAJourVertical();     
            }
        }
    }
    
    public void miseAJour(){
        for(int i =0; i <= this.liste.size(); i++){
                this.miseAJourVertical(this.liste.get(i));
                this.miseAJourHorizontale(this.liste.get(i));
                if(this.liste.get(i) instanceof Joueur){
                    
                }
            }
        }
    
    public void Afficher(Graphics2D contexte) {
        contexte.drawImage(this.fond, 0, 0, null);
        contexte.drawImage(this.nyancat, this.liste.get(1).getX(), this.liste.get(1).getY(), null);
    }
    
}
        

    
    
    
    
    

