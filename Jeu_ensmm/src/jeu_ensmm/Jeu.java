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
    private Map map ;
    private  ArrayList<Objet> liste;
    private BufferedImage decor;
    private Joueur joueur; 


    public Jeu() {
         try {
            this.decor = ImageIO.read(getClass().getResource("../resources/Chatelet_map.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu1.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.map = new Map();

        this.liste = new ArrayList();
        //this.joueur = new Joueur(1,false, "J1",0,0,false,false,false,false,12,0,1); 
       //this.liste.add(joueur);
        //this.liste.add(new Joueur(1, false, "J1",0,0,false,false,false,false,12,0,2));

       // this.joueur = new Joueur(1,false, "J1",0,32,false,false,false,false,12,0,1);
        //this.liste.add(new Objet(2, "J1",150,575,false,false,false,false,12,0,2));    // a enlever
//        this.liste.add(new Joueur(1, false, "J1",0,0,false,false,false,false,12,0,1));
        //Joueur J1 = new Joueur(false, "J1",20,20,40,40,false,false,false,false,10,0);
        //liste.add(J1);
    }

    public Map getMap() {
        return map;
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
    
    public void setMap(Map map) {
        this.map = map;
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

    @Override
    public String toString() {
        return "Jeu{" + "liste=" + liste + '}';
    }

    
    public void miseAJourV(Objet objet){
//        System.out.println((int) (objet.getX()/32));

        if(objet instanceof Joueur){
            if (this.getListe().get(this.getListe().indexOf(objet)).isHaut()== true || this.getListe().get(this.getListe().indexOf(objet)).isBas()== true){
//                if (this.map.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]==0){
//                    this.getListe().get(this.getListe().indexOf(objet)).setBas(true);
//                }
                if (this.map.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]!=0 && this.map.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32] !=128){
                    this.getListe().get(this.getListe().indexOf(objet)).setBas(false);
        }
                if (this.map.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]== 128 && objet.isHaut()== true){
                    this.getListe().get(this.getListe().indexOf(objet)).setHaut(true);
//                    System.out.println(this.getListe().get(this.getListe().indexOf(objet)).isHaut());
                    
                }
                if (this.map.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]== 128 && objet.isBas()== true){
                    this.getListe().get(this.getListe().indexOf(objet)).setBas(true);
                    
                }
                
        }
            if (this.map.getPlateforme()[(int) objet.getY()/32][(int) objet.getX()/32]==0){
                    this.getListe().get(this.getListe().indexOf(objet)).setBas(true);
                    this.getListe().get(this.getListe().indexOf(objet)).setHaut(false);
                }
            this.getListe().get(this.getListe().indexOf(objet)).miseAJourVertical();
        }
////        if(this.map.getPlateforme()[objet.getX()%31][objet.getY()%31]!=0 && objet.isHaut()== true){//     
////            objet.setBas(false);
////            objet.setHaut(true);
////            objet.miseAJourVertical();
////        }
//            
//        if(this.map.getPlateforme()[objet.getX()%31][objet.getY()%31]==0){
//            objet.setBas(true);
//            objet.setHaut(false);
//            objet.miseAJourVertical();
//        }
//        if(this.map.getPlateforme()[objet.getX()%31][objet.getY()%31]>0){
//            objet.setBas(false);
//            objet.setHaut(false);
//        }
//        }   
    }
    
    public void miseAJour(){
        for(int i =0; i < this.liste.size(); i+=1){
                this.miseAJourV(this.liste.get(i));
                this.miseAJourHorizontale(this.liste.get(i));
                if(!(this.liste.get(i) instanceof Joueur)){
                    this.liste.get(i).collision(joueur);
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
            
           // System.out.println(requete);
            requete.executeUpdate();

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
   }
    public void creationMonJoueur (String nom){
        joueur = new Joueur(1, false, nom,0,0,false,false,false,false,12,0,1) ;
        joueur.setId(this.nombreDeJoueurs()+1);
        this.setJoueur(joueur);
        
    }
    public void addJoueurTable() {
        try {

            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20212022_s2_vs1_tp2_supmuriotech?serverTimezone=UTC", "etudiant","YTDTvj9TR3CDYCmP" );

            PreparedStatement requete = connexion.prepareStatement("INSERT INTO joueur VALUES (?,?,?,?,?,?)");
            requete.setInt(1, this.getJoueur().getId() );
            requete.setString(2,this.getJoueur().getNom());
            requete.setInt(3, 0);
            requete.setInt(4, 0);
            requete.setInt(5, this.getJoueur().getId());
            requete.setInt(6, 0 );
            
            //System.out.println(requete);
            requete.executeUpdate();

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
    }
    public void addJoueursListe() {
        try {

            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20212022_s2_vs1_tp2_supmuriotech?serverTimezone=UTC", "etudiant","YTDTvj9TR3CDYCmP" );
            
            for (int id =1 ; id <= 2 ; id++){
                PreparedStatement requete = connexion.prepareStatement("SELECT pseudo, x, y, score FROM joueur WHERE id_joueur = ?");
                requete.setInt(1, id );
                ResultSet resultat = requete.executeQuery();
                while (resultat.next()){ 
                    String pseudo = resultat.getString("pseudo");
                    int x = resultat.getInt("x");
                    int y = resultat.getInt("y");
                    int score = resultat.getInt("score");
                    joueur = new Joueur(id, false, pseudo , x, y, false, false, false, false,0,score,id) ;
                    this.liste.add( joueur );
                    //System.out.println("id = " + this.liste.get(id-1).getId() + "  pseudo = " +  this.liste.get(id-1).getNom() + " score = " + this.liste.get(id-1).getScore() + this.liste.get(id-1).getSprite());
                }
            }     
        }
          catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("taille liste = " + this.liste.size()) ;
    }
         
    public void miseAJourDataBase() {
         try {

            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20212022_s2_vs1_tp2_supmuriotech?serverTimezone=UTC", "etudiant","YTDTvj9TR3CDYCmP" );

            PreparedStatement requete = connexion.prepareStatement("UPDATE joueur SET x = ?, y = ?, score = ? WHERE id_joueur = ?");
            requete.setInt(1, this.joueur.getX());
            requete.setInt(2, this.joueur.getY());
            requete.setInt(3, this.joueur.getScore());
            System.out.println(requete);
            requete.executeUpdate();

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    public void videTable(String nomTable){
         try {

            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20212022_s2_vs1_tp2_supmuriotech?serverTimezone=UTC", "etudiant","YTDTvj9TR3CDYCmP" );

            PreparedStatement statement = connexion.prepareStatement("TRUNCATE TABLE "+ nomTable);
            statement.execute() ;
            connexion.close();
         }
         catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void supprimeMonJoueur(){
          try {

            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20212022_s2_vs1_tp2_supmuriotech?serverTimezone=UTC", "etudiant","YTDTvj9TR3CDYCmP");
            
            PreparedStatement requete = connexion.prepareStatement("DELETE FROM joueur WHERE id_Joueur = ?");
            requete.setInt(1, this.joueur.getId());
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
        for (int i = 0; i < map.getHauteur(); i++) {
            for (int j = 0; j < map.getLargeur(); j++) {
                contexte.drawImage(map.getTuiles()[map.getPlateforme()[i][j]], j * map.getTailleTuile(), i * map.getTailleTuile(), null);
            }
        }
        for(int i =0; i < this.liste.size(); i+=1){
            contexte.drawImage(this.liste.get(i).getSprite() , this.liste.get(i).getX(), this.liste.get(i).getY()+32, null);
            if(this.liste.get(i).getId()==1){
//                System.out.println("coucou" + this.liste.get(i).getY()  );
            contexte.drawString("Joueur"+this.getJoueur().getId()+ " Score : " + this.liste.get(i).getScore(), 10, 20);
            
        }
        }
    }

//    void Afficher(Graphics2D contexteBuffer) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
   
}  
    
    
        

    
   
