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
import interfaces.FinDePartie;
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
import java.util.Collections;
import java.util.Random;
import javax.swing.JOptionPane;

public class Jeu {
    private Map map ;
    private ArrayList<Objet> liste;
    private Joueur joueur; 
    private Connection connexion ;

    public Jeu() {
        
       this.map = new Map(1,1);
       this.liste = new ArrayList<Objet>(14);
       //       // this.joueur = new Joueur(1,false, "J1",0,32,false,false,false,false,12,0,1);
        // a enlever
        
        for(int i=1; i<8; i++){
        this.liste.add(new Objet(i+4, "malus", 32+i*187,50,false,false,false,false,12,-5,6)); 
        this.liste.add(new Objet(i+5,"bonus", 32+i*233,20,false,false,false,false,12,15,5));
        }
        this.joueur = new Joueur(1, false, "J1",1000,64,false,false,false,false,8,0,1 );
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Objet> getListe() {
        return liste;
    }

    public Joueur getJoueur() {
        return joueur;
    }
    
    public void setMap(Map map) {
        this.map = map;
    }

    public Connection getConnexion() {
        return connexion;
    }

    public void setConnexion(Connection connexion) {
        this.connexion = connexion;
    }


    public void setListe(ArrayList<Objet> liste) {
        this.liste = liste;
    }


    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
        
    public void miseAJourHorizontale(Objet objet){

        if(objet instanceof Joueur){
            if (joueur.isDroite()== true || joueur.isGauche()== true){
                
                if (this.map.getPlateforme()[(int) joueur.getY()/32-1][(int) joueur.getX()/32+1]!=0 
                        && this.map.getPlateforme()[(int) joueur.getY()/32-1][(int) joueur.getX()/32+1] !=128 
                        && joueur.isDroite()== true){
                    joueur.setX(joueur.getX()-16);
                }   
                if (this.map.getPlateforme()[(int) joueur.getY()/32-1][(int) joueur.getX()/32]!=0 
                        && this.map.getPlateforme()[(int) joueur.getY()/32-1][(int) joueur.getX()/32] !=128 
                        && joueur.isGauche()== true){
                        joueur.setX(joueur.getX()+16);
                }              
            }
        }
        objet.miseAJourCote();
    }

    public void miseAjourScore(Objet objet){
        if( this.Collision(objet)==true ){
            if(this.joueur.getScore()+objet.getScore()>120){
                this.joueur.setScore(120);
            }
            else{
            this.joueur.setScore(this.joueur.getScore()+objet.getScore());
            }
        }
    }

    public boolean Collision(Objet objet){
        if((int) (this.getListe().get(this.getListe().indexOf(objet)).getY()/32)== joueur.getY()/32
                    && (int) (this.getListe().get(this.getListe().indexOf(objet)).getX()/32)== joueur.getX()/32){
            Random X =new Random();
            Random Y =new Random();
            int newPosX = X.nextInt(1776-objet.getLargeur()-32);        //-32 pour être sur de ne pas être trop près du bord de l'écran
            int newPosY = Y.nextInt(992-objet.getHauteur()-32);
            while(this.map.getPlateforme()[newPosY/32][newPosX/32]!=0){
            newPosX = X.nextInt(1776-objet.getLargeur()-32);
            newPosY = Y.nextInt(992-objet.getHauteur()-32);
        } 
            objet.setX(newPosX);
            objet.setY(newPosY);
            return true;
        }
            else{
           return false;
        }
    }

  public void miseAJourV(Objet objet){

        if(objet instanceof Joueur){

            if (this.joueur.isHaut()== true || this.joueur.isBas()== true){
                if ((this.map.getPlateforme()[(int) this.joueur.getY()/32][(int) joueur.getX()/32]!=0 
                        && this.map.getPlateforme()[(int) this.joueur.getY()/32][(int) this.joueur.getX()/32] !=128 ) 
                        || (this.map.getPlateforme()[(int) this.joueur.getY()/32][(int) joueur.getX()/32+1]!=0 
                        && this.map.getPlateforme()[(int) this.joueur.getY()/32][(int) this.joueur.getX()/32+1] !=128)){
                    this.joueur.setBas(false);
                }
                
                if ((this.map.getPlateforme()[(int) this.joueur.getY()/32][(int) this.joueur.getX()/32]== 128 
                        && this.joueur.isBas()== true) 
                        || (this.map.getPlateforme()[(int) this.joueur.getY()/32][(int) this.joueur.getX()/32+1]== 128 
                        && this.joueur.isBas()== true)) {
                    this.joueur.setBas(true); 
                }
                
                if ((this.map.getPlateforme()[(int) this.joueur.getY()/32][(int) this.joueur.getX()/32]== 128                         
                        || (this.map.getPlateforme()[(int) this.joueur.getY()/32][(int) this.joueur.getX()/32+1]== 128))
                        && this.joueur.isHaut()== true){
                    this.joueur.setHaut(true);
                    this.joueur.setBas(false);   
                }  
            }
                
        }
            if ((this.map.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]==0) 
                    && (this.map.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32+1]==0) ){
                    this.getListe().get(this.getListe().indexOf(objet)).setBas(true);
                    this.getListe().get(this.getListe().indexOf(objet)).setHaut(false);
                    
            }
            if(!(objet instanceof Joueur)){
                if ((this.map.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]!=0) 
                        || (this.map.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32+1]!=0) ) {
                        this.getListe().get(this.getListe().indexOf(objet)).setBas(false);
                }
            }
            this.getListe().get(this.getListe().indexOf(objet)).miseAJourVertical();
        }
    
    public void miseAJourDeplacement(Objet objet){
        this.miseAJourHorizontale(objet);
        this.miseAJourV(objet);
    }
    
    public void miseAJour(){
        for(int i =0; i < this.liste.size(); i+=1){
                this.miseAJourDeplacement(this.liste.get(i));
                if(!(this.liste.get(i) instanceof Joueur)){   
                    this.miseAjourScore((this.liste.get(i)));
                }
        }
    }

    
    public int nombreDeJoueurs(){
        int nbJoueurs = 0;
        try {      
            PreparedStatement requete = this.connexion.prepareStatement("SELECT COUNT(*) AS nbJoueurs FROM joueur ;");
            ResultSet resultat = requete.executeQuery();
            resultat.next();
            nbJoueurs = resultat.getInt("nbJoueurs") ;
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nbJoueurs ;
    }
    
    public int nombreObjets(){
        int nbObjets = 0;
        try {        
            PreparedStatement requete = this.connexion.prepareStatement("SELECT COUNT(*) AS nbObjets FROM objet ;");
            ResultSet resultat = requete.executeQuery();
            resultat.next();
            nbObjets = resultat.getInt("nbobjets") ;
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nbObjets ;
    }
    
    public void creationObjet(int idObjet , String nom , int x , int y , int score , int apparence) {

        try {
            PreparedStatement requete = this.connexion.prepareStatement("INSERT INTO objet VALUES (?,?,?,?,?,?)");
            requete.setInt(1, idObjet);
            requete.setString(2,nom);
            requete.setInt(3, x);
            requete.setInt(4, y);
            requete.setInt(5, score);
            requete.setInt(6, apparence);
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void creationMonJoueur (String nom){
        joueur.setNom(nom);
        joueur.setId(this.nombreDeJoueurs()+1);
    }
    
    public void addJoueurTable() {
        try {
            PreparedStatement requete = this.connexion.prepareStatement("INSERT INTO joueur VALUES (?,?,?,?,?,?)");
            requete.setInt(1, this.getJoueur().getId() );
            requete.setString(2, this.getJoueur().getNom());
            requete.setInt(3,this.getJoueur().getX());
            requete.setInt(4, this.getJoueur().getY());
            requete.setInt(5, this.getJoueur().getId());
            requete.setInt(6, 0 );
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }  
    }
   
    public void addJoueursListe() {
        try {
            for (int id =1 ; id <= 4 ; id++){
                PreparedStatement requete = this.connexion.prepareStatement("SELECT pseudo, x, y, score FROM joueur WHERE id_joueur = ?");
                requete.setInt(1, id );
                ResultSet resultat = requete.executeQuery();
                int x = 0 ;
                int y = 0;
                int score = 0 ;
                while (resultat.next()){ 
                    String pseudo = resultat.getString("pseudo");
                     x = resultat.getInt("x");
                     y = resultat.getInt("y");
                     score = resultat.getInt("score");
                    
//                    System.out.println("id = " + this.liste.get(id-1).getId() + "  pseudo = " +  this.liste.get(id-1).getNom() + " score = " + this.liste.get(id-1).getScore() + this.liste.get(id-1).getSprite());
                }
            requete.close();
            if(this.joueur.getId()!= id){
                        Joueur joueur = new Joueur (id, false, "pseudo",x,y,false,false,false,false,12,score,id);
                        this.liste.set(id-1, joueur);
                    }
                    this.liste.set(this.getJoueur().getId()-1 , joueur);
            }
        }
          catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("taille liste = " + this.liste.size()) ;
    }
         

    public void miseAJourDataBase() {
         try {
                
            PreparedStatement requete = this.connexion.prepareStatement("UPDATE joueur SET x = ?, y = ?, score = ? WHERE id_joueur = ?");  // exportation des infos de mon joueur
            requete.setInt(1, this.joueur.getX());
            requete.setInt(2, this.joueur.getY());
            requete.setInt(3, this.joueur.getScore());
            requete.setInt(4, this.joueur.getId());
            requete.executeUpdate();
            
            requete.close();
            
            for (int id =1 ; id <= 4 ; id++){
                PreparedStatement requete2 = this.connexion.prepareStatement("SELECT pseudo, x, y, score FROM joueur WHERE id_joueur = ?");
                requete2.setInt(1, id );
                ResultSet resultat = requete2.executeQuery();
                int x = 0;
                int y = 0;
                int score = 0;
                String pseudo = "";
                while (resultat.next()){ 
                    pseudo = resultat.getString("pseudo");
                    x = resultat.getInt("x");
                    y = resultat.getInt("y");
                    score = resultat.getInt("score");
                }
                    if(this.joueur.getId()!= id){
                        this.liste.get(id-1).setNom(pseudo);
                        this.liste.get(id-1).setX(x);
                        this.liste.get(id-1).setY(y);
                        this.liste.get(id-1).setScore(score);
                    }
                    this.liste.set(this.joueur.getId()-1 , joueur);
                requete2.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    public void videTable(String nomTable){
         try {
            PreparedStatement statement = this.connexion.prepareStatement("TRUNCATE TABLE "+ nomTable);
            statement.execute() ;
         }
         catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

        
    
    public void supprimeMonJoueur(){
          try {
            PreparedStatement requete = connexion.prepareStatement("DELETE FROM joueur WHERE id_Joueur = ?");
            requete.setInt(1, this.joueur.getId());
            
            requete.executeUpdate();
            requete.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
   public void openConnexion () throws SQLException {
        Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20212022_s2_vs1_tp2_supmuriotech?serverTimezone=UTC", "etudiant","YTDTvj9TR3CDYCmP");
        this.connexion = connexion ;
   }
   
   public void closeConnexion() throws SQLException {
       this.connexion.close() ;
   }
    
    public void rendu(Graphics2D contexte){
        contexte.drawImage(this.map.getDecor(), 0, 0, null);
        for (int i = 0; i < map.getHauteur(); i++) {
            for (int j = 0; j < map.getLargeur(); j++) {
                contexte.drawImage(map.getTuiles()[map.getPlateforme()[i][j]], j * map.getTailleTuile(), i * map.getTailleTuile(), null);
            }
        }
        for(int i =0; i < this.liste.size(); i+=1){
            contexte.drawImage(this.liste.get(i).getSprite() , this.liste.get(i).getX(), this.liste.get(i).getY()-32, null);          
            if(this.liste.get(i) instanceof Joueur){
            contexte.drawString(this.liste.get(i).getNom()+ " µScore : " + this.liste.get(i).getScore() + "x10e-6", 10, 20+20*i);
            contexte.drawString(this.liste.get(i).getNom(), this.liste.get(i).getX(), this.liste.get(i).getY()-36);
            }
        }
    }
    
    public void videListe(){
        this.liste.clear();
    }
    
    public boolean finDuJeu(){
        ArrayList listescore = new ArrayList();
        Boolean reponse = false;
        try {
            for (int id =1 ; id <= this.nombreDeJoueurs(); id++){
                PreparedStatement requete = this.connexion.prepareStatement("SELECT score FROM joueur WHERE id_joueur = ?");
                requete.setInt(1, id );
                ResultSet resultat = requete.executeQuery();
                while (resultat.next()){
                    int score = resultat.getInt("score");
                    listescore.add(score);
                    }
            }  
        }
          catch (SQLException ex) {
            ex.printStackTrace();
        }     
        for (int i = 0 ; i <listescore.size(); i++){
            if ((int)listescore.get(i) >= 120){
                reponse = true;
            }
        }
        return reponse;
    }
    
    public ArrayList classementJoueurs(){
        ArrayList liste = new ArrayList();
        ArrayList classement = new ArrayList();
        try {
            for (int id =1 ; id <= this.nombreDeJoueurs(); id++){
                PreparedStatement requete = this.connexion.prepareStatement("SELECT score FROM joueur WHERE id_joueur = ?");
                requete.setInt(1, id);
                ResultSet resultat = requete.executeQuery();
                while (resultat.next()){
                    int score = resultat.getInt("score");
                    liste.add(score);
                }
            }
            Collections.sort(liste, Collections.reverseOrder());
            System.out.println(liste);
            for (int i = 0; i<liste.size(); i++){
                PreparedStatement requete = this.connexion.prepareStatement("SELECT pseudo FROM joueur WHERE score = ?");
                requete.setInt(1,(int)liste.get(i));
                ResultSet resultat = requete.executeQuery();
                while (resultat.next()){
                    String pseudo = resultat.getString("pseudo");
                    classement.add(pseudo);
                }
            }
        }
          catch (SQLException ex) {
            ex.printStackTrace();
        }
        while (classement.size()<4){
            classement.add("");
        }
        while (liste.size()<4){
            liste.add("");
        }
        for (int i = 0; i<liste.size(); i++){
            classement.add(liste.get(i));
        }
        return classement;
    }
    
    public void affichageClassement(ArrayList classement){
        FinDePartie finDePartie = new FinDePartie();
        finDePartie.setVisible(true);
        System.out.println(classement);
        finDePartie.setjLabel2("1er  : "+classement.get(0)+"    score : "+classement.get(4));
        finDePartie.setjLabel3("2ème : "+classement.get(1)+"    score : "+classement.get(5));
        finDePartie.setjLabel4("3ème : "+classement.get(2)+"    score : "+classement.get(6));
        finDePartie.setjLabel5("4ème : "+classement.get(3)+"    score : "+classement.get(7));
    }
}
    
    
    
        

    

    
