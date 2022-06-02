package jeu_ensmm;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nbouvere
 */
public class Objet {
    private String nom;
    private int x, y;

    private boolean gauche, droite, haut, bas;
    private int vitesse;
    private int score;
    private BufferedImage sprite;

    public Objet(String nom, int x, int y, boolean gauche, boolean droite, boolean haut, boolean bas, int vitesse, int score, int numSprite){//, BufferedImage sprite) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.gauche = gauche;
        this.droite = droite;
        this.haut = haut;
        this.bas = bas;
        this.vitesse = vitesse;
        
        this.score = score;
        try {
            switch (numSprite) {
                case 1 : this.setSprite(ImageIO.read(getClass().getResource("../resources/zombie.png"))); break;
                case 2 : this.setSprite(ImageIO.read(getClass().getResource("../resources/banane.png"))); break;
                default : this.setSprite(ImageIO.read(getClass().getResource("../resources/jungle.png"))); break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Personnage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //this.sprite = null;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
    public BufferedImage getSprite() {
        return sprite;
    }
    public int getScore() {
        return score;
    }
    public String getNom() {
        return nom;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getVitesse() {
        return vitesse;
    }
    public boolean isGauche() {
        return gauche;
    }
    public boolean isDroite() {
        return droite;
    }
    public boolean isHaut() {
        return haut;
    }
    public boolean isBas() {
        return bas;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void setGauche(boolean gauche) {
        this.gauche = gauche;
    }
    public void setDroite(boolean droite) {
        this.droite = droite;
    }
    public void setHaut(boolean haut) {
        this.haut = haut;
    }
    public void setBas(boolean bas) {
        this.bas = bas;
    }
    public void setVitesse(int vitesse) {
        this.vitesse = 8;
    }
    public void setScore(int score) {
        this.score = score;
    }
    @Override
    public String toString() {
        return "Objet{" + "nom=" + nom + ", x=" + x + ", y=" + y + '}';
    }
 
    
    public double distance(Objet p){
        return Math.sqrt((p.x-this.x)*(p.x-this.x)+(p.y-this.y)*(p.y-this.y));
    }
    
    public int getLargeur(){
        return (int) sprite.getHeight();
    }
     public int getHauteur(){
        return (int) sprite.getWidth();
    }
    public boolean collision(Objet objet){
        if((x>=objet.getSprite().getHeight()+objet.getX()) 
           || (x+sprite.getHeight()<= objet.getX())
           || (y>=objet.getY()+objet.getSprite().getWidth())
           || (y+sprite.getWidth()<= objet.getY())){
            return false;
        } else{
            return true;
        }
                    
    }
    
    public void miseAJourCote() {
        if (this.gauche) {
            x -= vitesse;
        }
        if (this.droite) {
            x += vitesse;
        }
        if (x > 1776-112) {
            x = 1776-112;
        }
        if (x < 0) {
            x = 0;
        }
    }
    public void miseAJourVertical() {
        if (this.bas) {
            y += 5;
        }
        if (this.haut) {
            y -= vitesse;
        }
        if (y > 970-this.getHauteur()) {
            y = 970-this.getHauteur();
        }
        if (y < 0) {
            y = vitesse;
        }
    }
    
    public void miseAJourDeplacement(){
        this.miseAJourCote();
        this.miseAJourVertical();
    }
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }
}