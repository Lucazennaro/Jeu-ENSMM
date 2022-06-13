/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu_ensmm;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author lbourger
 */
public class Jeu1 {
    
    private BufferedImage decor;
    private Personnage unPersonnage;
    private Map unePlateforme;

    public Personnage getUnPersonnage() {
        return unPersonnage;
    }

        public Jeu1() {
        try {
            this.decor = ImageIO.read(getClass().getResource("../resources/ENSMM_exterieur.png"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.unPersonnage = new Personnage();
        //this.unePlateforme = new Map();
    }
    
    public void rendu(Graphics2D contexte){
        contexte.drawImage(this.decor, 0, 0, null);
        contexte.drawImage(this.unPersonnage.sprite, 500, 500, null);
        for (int i = 0; i < unePlateforme.getHauteur(); i++) {
            for (int j = 0; j < unePlateforme.getLargeur(); j++) {
                contexte.drawImage(unePlateforme.getTuiles()[unePlateforme.getPlateforme()[i][j]], j * unePlateforme.getTailleTuile(), i * unePlateforme.getTailleTuile(), null);
            }
        }
    } 
}
