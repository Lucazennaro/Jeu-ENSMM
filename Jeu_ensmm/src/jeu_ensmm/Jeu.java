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
    private Plateforme plateforme ;
    private  ArrayList<Objet> liste;
    private BufferedImage decor;
    private Joueur joueur; 

    public Jeu() {
         try {
            this.decor = ImageIO.read(getClass().getResource("../resources/ENSMM_exterieur.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu1.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.liste = new ArrayList();
        this.plateforme = new Plateforme();
        this.joueur = new Joueur(false, "J1",500,500,100,100,false,false,false,false,10,0);
        //Joueur J1 = new Joueur(false, "J1",20,20,40,40,false,false,false,false,10,0);
        //liste.add(J1);
    }

    public Plateforme getPlateforme() {
        return plateforme;
    }

    public ArrayList<Objet> getListe() {
        return liste;
    }

    public BufferedImage getDecor() {
        return decor;
    }

    public Joueur getJoueur() {
        return joueur;
    }
    
    public void setPlateforme(Plateforme plateforme) {
        this.plateforme = plateforme;
    }

    public void setListe(ArrayList<Objet> liste) {
        this.liste = liste;
    }

    public void setDecor(BufferedImage decor) {
        this.decor = decor;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
    
   
        
    public void miseAJourHorizontale(Objet objet){
        if (this.getListe().get(this.getListe().indexOf(objet)).isDroite()== true || this.getListe().get(this.getListe().indexOf(objet)).isGauche()== true){
                this.getListe().get(this.getListe().indexOf(objet)).miseAJourCote();
        }
    }
    
    public void miseAJourVertical(Objet objet){
        if(objet instanceof Joueur)
        if(this.plateforme.getPlateforme()[objet.getX()][objet.getY()-objet.getHauteur()/2]==1 && (objet.isHaut()== true)){
            objet.setBas(false);
            objet.setHaut(true);
            for(int i=0; i<3;i+=1){
                objet.miseAJourVertical();
            }
            while(this.plateforme.getPlateforme()[objet.getX()][objet.getY()-objet.getHauteur()/2]!=1){
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
    
    
        
    public void rendu(Graphics2D contexte){
        contexte.drawImage(this.decor, 0, 0, null);
        contexte.drawImage(this.joueur.getSprite(), 500, 500, null);
        for (int i = 0; i < plateforme.getHauteur(); i++) {
            for (int j = 0; j < plateforme.getLargeur(); j++) {
                contexte.drawImage(plateforme.getTuiles()[plateforme.getPlateforme()[i][j]], j * plateforme.getTailleTuile(), i * plateforme.getTailleTuile(), null);
            }
        }
    } 
    
}
        

    
    
    
    
    

