/** Titre
 *      @project Jeu déplacement de pièces - TP07 Java
 *      @class MyPanel
 *      @description Classe pour gérer un(e) Classe
 *      
 *      @author Copyright 2009 Antoine GREA <grea09@gmail.com>
 *      
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *      
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *      
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;


public class MyPanel extends JPanel implements ActionListener, MouseListener
{
	//Constantes
	////{
		
		final static double DEF_WEIGHT = 1.0;
		final static String [] S_LABEL = {"Colonnes","10","Lignes","10","Type de pièce", "Joueur1","Joueur2","Cliquer sur le bouton pour commancer"};
		final static String [] S_BUTTON = {"-","+","-","+","Lancer partie"};
		final static String [] S_COMBO1 = {"Tour","Roi","Reine"};
		final static String [] S_COMBO15 = {"Aucun","Tour","Roi","Reine"};
		final static String [] S_COMBO2 = {"Humain","Ordinateur"};
		final static String [][] S_COMBO = { S_COMBO1, S_COMBO15, S_COMBO2, S_COMBO2};
		final static int [][] MAP_LABEL = 
		{
			{0,0,3,1}, 
			{1,1,1,1}, 
			{2,0,3,1},  
			{3,1,1,1}, 
			{0,4,1,1}, 
			{3,4,1,1}, 
			{5,4,1,1}, 
			{8,0,5,1}
		};
		final static int [][] MAP_BUTTON = 
		{
			{1,0,1,1}, 
			{1,2,1,1}, 
			{3,0,1,1},  
			{3,2,1,1}, 
			{7,0,5,1}
		};
		final static int [][] MAP_COMBO = 
		{
			{1,4,1,1}, 
			{2,4,1,1}, 
			{4,4,1,1}, 
			{6,4,1,1}
		};
		final static int [] MAP_PART = {9,0,5,1,(int)(Partie.DEF_ROW * DEF_WEIGHT),(int)(DEF_WEIGHT)};
		final static long serialVersionUID=1;
		
	////}
	
	//Attributs
	////{
		
		private JLabel [] labels;
		private JButton [] buttons;
		private JComboBox [] combos;
		private Partie partie;
		private JFrame fen;
		
	////}
	
	//Méthodes
	////{
		
		//Constructeur
		////{
			
			public MyPanel(JFrame fen)
			{
				this.fen = fen;
				labels = new JLabel [S_LABEL.length];
				buttons = new JButton [S_BUTTON.length];
				combos = new JComboBox [S_COMBO.length];
				partie = Partie.newSingleton();
				build();
			}
			
			
		////}
		
		//Accesseur
		////{
			
			
			
		////}
		
		//Mutateur
		////{
			
			
			
		////}
		
		//Traitement
		////{
			
			/**
			 * 
			 * name: build
			 * @param aucun
			 * @return aucun
			 */
			
			private void build()
			{
				this.setLayout(new GridBagLayout());
				
				GridBagConstraints contraintes = new GridBagConstraints();
				contraintes.fill=GridBagConstraints.CENTER;
				contraintes.weighty = DEF_WEIGHT;
				contraintes.weightx = DEF_WEIGHT;
				
				for (int i =0;i<labels.length;i++)
				{
					labels[i] = new JLabel (S_LABEL[i]);
					mapConstraint(contraintes,MAP_LABEL[i]);
					this.add(labels[i],contraintes);
				}
				
				for (int i =0;i<buttons.length;i++)
				{
					buttons[i] = new JButton (S_BUTTON[i]);
					mapConstraint(contraintes,MAP_BUTTON[i]);
					this.add(buttons[i],contraintes);
					buttons[i].addActionListener(this);
				}
				
				for (int i =0;i<combos.length;i++)
				{
					combos[i] = new JComboBox ();
					for (int j=0;j<S_COMBO[i].length;j++)
					{
						combos[i].addItem(S_COMBO[i][j]);
					}
					mapConstraint(contraintes,MAP_COMBO[i]);
					this.add(combos[i],contraintes);
					combos[i].addActionListener(this);
				}
				
				mapConstraint(contraintes,MAP_PART);
				this.add(partie,contraintes);
				partie.addMouseListener(this);
			}
			
			/**
			 * 
			 * name: mapConstraint
			 * @param Contraintes, Map
			 * @return aucun
			 */
			
			private void mapConstraint ( GridBagConstraints contraintes, int [] map)
			{
				switch (map.length)
				{
					case 7 :
						contraintes.fill=map[6];
					case 6 : // Fallthrough volontaire
						contraintes.weighty = (double)map[4];
						contraintes.weightx = (double)map[5];
					case 4 : // Fallthrough volontaire
						contraintes . gridheight = map[3];
						contraintes . gridwidth = map[2];
					case 2 : // Fallthrough volontaire
						contraintes . gridy =map[0];
						contraintes . gridx =map[1];
					break;
					default :
						contraintes.fill=GridBagConstraints.BOTH;
						contraintes.weighty = DEF_WEIGHT;
						contraintes.weightx = DEF_WEIGHT;
						contraintes . gridx =0;
						contraintes . gridy =0;
						contraintes . gridwidth = 1;
						contraintes . gridheight = 1;
				}
			}
			
			/**
			 * 
			 * name: actionPerformed
			 * @param ActionEvent
			 * @return aucun
			 */
			
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == buttons[0])
				{
					labels[1].setText(Integer.toString((((Integer.parseInt(labels[1].getText())-1)>0)?Integer.parseInt(labels[1].getText())-1 : 1) ));
				}
				if (e.getSource() == buttons[1])
				{
					labels[1].setText(Integer.toString((Integer.parseInt(labels[1].getText())+1) ));
				}
				if (e.getSource() == buttons[2])
				{
					labels[3].setText(Integer.toString((((Integer.parseInt(labels[3].getText())-1)>0)?Integer.parseInt(labels[3].getText())-1 : 1) ));
				}
				if (e.getSource() == buttons[3])
				{
					labels[3].setText(Integer.toString((Integer.parseInt(labels[3].getText())+1) ));
				}
				if (e.getSource() == buttons[4])
				{
					String [] temp = {(String)combos[0].getSelectedItem(),(String)combos[1].getSelectedItem()};
					boolean [] ia = {combos[2].getSelectedItem() == S_COMBO2[1] , combos[3].getSelectedItem() == S_COMBO2[1]};
					partie.init(Integer.parseInt(labels[1].getText()),Integer.parseInt(labels[3].getText()),
					temp,
					ia);
					labels[S_LABEL.length - 1].setText(partie.getJoueur());
					fen.pack();
				}
			}
			
			
			/**
			 * 
			 * name: mouseMoved
			 * @param MouseEvent
			 * @return aucun
			 */
			
			public void mouseMoved(MouseEvent e)
			{
				
			}
			
			public void mouseClicked(MouseEvent e)
			{
				int [] temp = {e.getX()/Partie.DEF_WIDTH,(e.getY()/Partie.DEF_HEIGTH)-Partie.DEC};
				partie.select(temp);
				partie.repaint();
				labels[S_LABEL.length - 1].setText(partie.getJoueur());
				if (partie.win())
				{
					JOptionPane.showMessageDialog(this , partie.getJoueur() + " as gagné le match !" , "Félicitations !" , JOptionPane.INFORMATION_MESSAGE);
					partie.finish();
					
				}
			}
			
			public void mousePressed(MouseEvent e)
			{
				
			}
			
			public void mouseReleased(MouseEvent e)
			{
				
			}
			
			public void mouseEntered(MouseEvent e)
			{
				
			}
			
			public void mouseExited(MouseEvent e)
			{
				
			}
			
		////}
		
		//Affichage
		////{
			
			
			
		////}
		
		
	////}
	
}

