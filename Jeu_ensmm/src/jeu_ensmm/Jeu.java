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
import java.sql.Connection;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import outils.OutilsJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jeu {
    private Plateforme plateforme ;
    private  ArrayList<Objet> liste;
    private BufferedImage decor;
    private Joueur joueur; 
    private int id;

    public Jeu() {
         try {
            this.decor = ImageIO.read(getClass().getResource("../resources/ENSMM_exterieur.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu1.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.plateforme = new Plateforme();
        this.liste = new ArrayList();
        this.joueur = new Joueur(false, "J1",0,0,false,false,false,false,12,0,1);
        this.liste.add(joueur);
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

    
    public void miseAJourV(Objet objet){
//        System.out.println((int) (objet.getX()/32));

        if(objet instanceof Joueur){
            if (this.getListe().get(this.getListe().indexOf(objet)).isHaut()== true || this.getListe().get(this.getListe().indexOf(objet)).isBas()== true){
//                if (this.plateforme.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]==0){
//                    this.getListe().get(this.getListe().indexOf(objet)).setBas(true);
//                }
                if (this.plateforme.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]!=0 && this.plateforme.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32] !=128){
                    this.getListe().get(this.getListe().indexOf(objet)).setBas(false);
        }
                if (this.plateforme.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]== 128 && objet.isHaut()== true){
                    this.getListe().get(this.getListe().indexOf(objet)).setHaut(true);
                    
                }
                if (this.plateforme.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]== 128 && objet.isBas()== true){
                    this.getListe().get(this.getListe().indexOf(objet)).setBas(true);
                    
                }
                
        }
            if (this.plateforme.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]==0){
                    this.getListe().get(this.getListe().indexOf(objet)).setBas(true);
                    this.getListe().get(this.getListe().indexOf(objet)).setHaut(false);
                }
            this.getListe().get(this.getListe().indexOf(objet)).miseAJourVertical();
        }
////        if(this.plateforme.getPlateforme()[objet.getX()%31][objet.getY()%31]!=0 && objet.isHaut()== true){//     
////            objet.setBas(false);
////            objet.setHaut(true);
////            objet.miseAJourVertical();
////        }
//            
//        if(this.plateforme.getPlateforme()[objet.getX()%31][objet.getY()%31]==0){
//            objet.setBas(true);
//            objet.setHaut(false);
//            objet.miseAJourVertical();
//        }
//        if(this.plateforme.getPlateforme()[objet.getX()%31][objet.getY()%31]>0){
//            objet.setBas(false);
//            objet.setHaut(false);
//        }
//        }   
    }
    
    public void miseAJour(){
        for(int i =0; i < this.liste.size(); i+=1){
                this.miseAJourV(this.liste.get(i));
                this.miseAJourHorizontale(this.liste.get(i));
                if(this.liste.get(i) instanceof Joueur){
                    
                }
            }
        }
    public int nombreDeJoueurs(){
        int nbJoueurs = 0;
        try {
            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20212022_s2_vs1_tp2_supmuriotech?serverTimezone=UTC", "etudiant","YTDTvj9TR3CDYCmP");
                    
            PreparedStatement requete = connexion.prepareStatement("SELECT COUNT(*) AS nbJoueurs FROM joueur ;");
            ResultSet resultat = requete.executeQuery();
            resultat.next();
            nbJoueurs = resultat.getInt("nbJoueurs") ;
            requete.close();
            connexion.close();
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nbJoueurs ;
    }
    public int nombreObjets(){
        int nbObjets = 0;
        try {
            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20212022_s2_vs1_tp2_supmuriotech?serverTimezone=UTC", "etudiant","YTDTvj9TR3CDYCmP");
                    
            PreparedStatement requete = connexion.prepareStatement("SELECT COUNT(*) AS nbObjets FROM objet ;");
            ResultSet resultat = requete.executeQuery();
            resultat.next();
            nbObjets = resultat.getInt("nbobjets") ;
            requete.close();
            connexion.close();
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nbObjets ;
    }
    
    public void creationObjet(int idObjet , String nom , int x , int y , int score , int apparence) {

        try {

            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20212022_s2_vs1_tp2_supmuriotech?serverTimezone=UTC", "etudiant","YTDTvj9TR3CDYCmP" );

            PreparedStatement requete = connexion.prepareStatement("INSERT INTO objet VALUES (?,?,?,?,?,?)");
            requete.setInt(1, idObjet);
            requete.setString(2,nom);
            requete.setInt(3, x);
            requete.setInt(4, y);
            requete.setInt(5, score);
            requete.setInt(6, apparence);
            
            System.out.println(requete);
            requete.executeUpdate();

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
   }
    
    
    public void rendu(Graphics2D contexte){
        contexte.drawImage(this.decor, 0, 0, null);
        for (int i = 0; i < plateforme.getHauteur(); i++) {
            for (int j = 0; j < plateforme.getLargeur(); j++) {
                contexte.drawImage(plateforme.getTuiles()[plateforme.getPlateforme()[i][j]], j * plateforme.getTailleTuile(), i * plateforme.getTailleTuile(), null);
            }
        }
        contexte.drawImage(this.joueur.getSprite(), this.joueur.getX(), this.joueur.getY()-32, null);
    } 

    void Afficher(Graphics2D contexteBuffer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}  
    
    
        

    
   
