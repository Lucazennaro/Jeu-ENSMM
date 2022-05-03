package jeu_ensmm;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;

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
    private int largeur,hauteur;
    private boolean gauche, droite, haut, bas;
    private int vitesse;
    private int score;
    private BufferedImage sprite;

    public Objet(String nom, int x, int y, int largeur, int hauteur, boolean gauche, boolean droite, boolean haut, boolean bas, int vitesse, int score, BufferedImage sprite) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.gauche = gauche;
        this.droite = droite;
        this.haut = haut;
        this.bas = bas;
        this.vitesse = vitesse;
        
        this.score = score;
        this.sprite = sprite;
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

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
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

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
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
        this.vitesse = vitesse;
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
    
    public boolean collision(Objet objet){
        boolean b = true;
        if( abs(this.x-objet.getX())<=(this.largeur/2+objet.getLargeur()/2) ){
            if( abs(this.y-objet.getY() ) > ( this.hauteur/2 + objet.getHauteur()/2 ) ){
                b=false;
            }
            else {
                b=true;
            }    
        }
        return b;
    }
    
    public void miseAJourCote() {
        if (this.gauche) {
            x -= vitesse;
        }
        if (this.droite) {
            x += vitesse;
        }
        if (x > 607-52) {
            x = 607-52;
        }
        if (x < 0) {
            x = 0;
        }
    }
    public void miseAJourVertical() {
        if (this.bas) {
            y -= vitesse;
        }
        if (this.haut) {
            y += vitesse;
        }
        if (y > 607-52) {
            y = 607-52;
        }
        if (y < 0) {
            y = 0;
        }
    }
    
    public void miseAJourDeplacement(){
        this.miseAJourCote();
        this.miseAJourVertical();
    }
    
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) this.getX(), (int) this.getY(), null);
    }
}