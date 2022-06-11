package jeu_ensmm;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.abs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Random;

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
    private int id;
    private String nom;
    private int x, y;

    private boolean gauche, droite, haut, bas;
    private int vitesse;
    private int score;
    private BufferedImage sprite;

    public Objet(int id,String nom, int x, int y, boolean gauche, boolean droite, boolean haut, boolean bas, int vitesse, int score, int numSprite){//, BufferedImage sprite) {
        this.id =id;
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
        if((this.x-objet.getX()<=objet.getSprite().getHeight()/2+this.getLargeur()/2) 
           && (this.y-objet.getY()<=objet.getSprite().getWidth()/2+this.getHauteur()/2)){
            System.out.println("yo");
            Random newPosX =new Random();
            Random newPosY =new Random();
            this.setX(newPosX.nextInt(1776-objet.getLargeur()-3*32));
            this.setY(newPosY.nextInt(992-objet.getHauteur()-32));
            System.out.println(newPosX.nextInt(1776-objet.getLargeur()-3*32)/32);
            System.out.println(newPosY.nextInt(992-objet.getHauteur()-32)/32);
            
            return true;
        } else{
            return false;
        }
                    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public void miseAJourCote() {
        if (this.gauche) {
            x -= vitesse;
        }
        if (this.droite) {
            x += vitesse;
        }
        if (x > 1776-this.getLargeur()) {
            x = 1776-this.getLargeur();
        }
        if (x < 0) {
            x = 0;
        }
    }
    public void miseAJourVertical() {
        if (this.bas) {
            y += vitesse;
        }
        if (this.haut) {
            y -= vitesse;
        }
        if (y > 992-this.getHauteur()) {
            y = 992-this.getHauteur();
        }
        if (y < 0) {
            y = vitesse;
        }
    }
    
    
    
    public void miseAJourDeplacement(){
        this.miseAJourCote();
        this.miseAJourVertical();
    }
    public int score(){
        int score=0;
        
        try {

            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/tp_jdbc_vs1tp1?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");

            PreparedStatement requete = connexion.prepareStatement("SELECT score FROM joueur WHERE id = ?");
            requete.setInt(1,this.getId());
            ResultSet resultat = requete.executeQuery();
            score = resultat.getInt("score");
            

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return score;
    }
    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }
}


