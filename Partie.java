/** Titre
 *      @project Jeu déplacement de pièces - TP07 Java
 *      @class Partie
 *      @description Classe pour gérer une et une seule Partie (gère aussi l'affichage de la grille)
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
import java.math.*;


public class Partie extends JComponent
{
	//Constantes
	////{
		
		final static double DEF_WEIGHT = 1.0; // Poids par défaut
		final static int DEF_COL = 10; // NB de lignes par défaut
		final static int DEF_ROW = 10; // NB de lignes par défaut
		final static int DEF_WIDTH = 48; // Largeur des cellules en pixels
		final static int DEF_HEIGTH = ((Main.IMAGE_MOD)?(3*DEF_WIDTH/4):DEF_WIDTH); // Hauteur des cellules en pixels
		
		final static int H0 = 0; // 0x00
		final static int H4 = 96; // 0x40
		final static int H8 = 128; // 0x80
		final static int HA = 192; // 0xA0
		final static int HE = 224; // 0xE0
		final static int HF = 255; // 0xFF
		
		final static int[] COMP = {H8,H0,H0,H0,H0,H0,H8,HF,HF,HF,HF,HF}; // Compossante pour case spéciale
		final static int[] COMP_VOID = {HE,HA,HA,HA,HA,HA,HE,HF,HF,HF,HF,HF}; // c'est la plus drôle des composantes !
		final static int[] COMP_POSS = {HA,H8,H8,H4,H8,H8,HA,HF,HF,HF,HF,HF}; // composante pour les cases où le déplacement est possible
		
		final static int CE_VOID = -1;// valeur spéciale (<0)
		final static int CE_FULL = -3; // valeur spéciale (<0)
		
		final static int CE_POSS = -2; // valeur spéciale (<0)
		final static int CE_FINAL = 10; // couleur de l'arrivée
		
		final static int [] DECO_FULL = {2,8}; // couleur de la pièce (devrait avoir NB_JOUEUR comme taille)
		final static int [] DECO_VOID = {1,2}; // On refait vôtre maison
		
		final static int [] DEF_FINAL = {0,0}; // relatif au bas à droite
		final static int [] DEF_DEPART = {0,0}; // relatif au haut à gauche
		
		final static int NB_JOUEUR = 2; // Non c'est chantal !
		final static boolean DEF_IA = false; // On as des joueurs par défault
		
		final static int DEC = (Main.IMAGE_MOD?(DEF_WIDTH/DEF_HEIGTH):0); // Décalage du haut (pour les images)
		
		final static String FINAL_SYMBOL = "⚑"; // le drapeau d'arrivé choix ▒⚐⚑
		final static String DEPART_SYMBOL = "⚐"; // le drapeau du départ choix ⚐⚑
		
		final static String EXT = ".png";
		final static String FLAG_DIR = "Flag/";
		final static String FINAL_IMAGE = Main.IMAGE_DIR + FLAG_DIR + "flag" + EXT;
		final static String [] DEPART_IMAGE = 
		{
			Main.IMAGE_DIR + FLAG_DIR + "flag" + Integer.toString(DECO_FULL[0]) + EXT,
			Main.IMAGE_DIR + FLAG_DIR + "flag" + Integer.toString(DECO_FULL[1]) + EXT,
		};
		
		final static long serialVersionUID=2;
		
	////}
	
	//Attributs
	////{
		
		private int [][] map; // Pas une bonne idée de faire une classe pour ça
		private Piece [] piece;
		private Piece selected; // Pour quand il y en aura 2
		private static Partie instance;
		private int joueur;
		private boolean [] ia;
		
	////}
	
	//Méthodes
	////{
		
		//Constructeur
		////{
			
			private Partie()
			{
				map = new int [DEF_COL][DEF_ROW];
				joueur = 0;
				ia = new boolean [NB_JOUEUR];
				for(int i =0;i<ia.length;i++)
				{
					ia[i] = DEF_IA;
				}
				
				instance = this;
				genMap();
			}
			
			private Partie(int col, int row)
			{
				map = new int [col][row];
				joueur = 0;
				ia = new boolean [NB_JOUEUR];
				for(int i =0;i<ia.length;i++)
				{
					ia[i] = DEF_IA;
				}
				instance = this;
				genMap();
			}
			
			/**
			 * 
			 * name: init
			 * @param String piece
			 * @return aucun
			 */
			
			public void init(String [] piece)
			{
				selected = null;
				joueur = 0;
				this.piece = new Piece[piece.length];
				for (int i=0; i<this.piece.length ; i++)
				{
					if (piece[i] == "Aucun")
					{
						this.piece[i] = null;
						
					}
					else if (piece[i] == "Tour")
					{
						this.piece[i] = new Tour(((map.length-1)+(DEF_DEPART[0]))%(map.length),((map[0].length-1)+DEF_DEPART[1])%(map[0].length));
						
					}
					else if (piece[i] == "Roi")
					{
						this.piece[i] = new Roi(((map.length-1)+(DEF_DEPART[0]))%(map.length),((map[0].length-1)+DEF_DEPART[1])%(map[0].length));
					}
					else if (piece[i] == "Reine")
					{
						this.piece[i] = new Reine(((map.length-1)+(DEF_DEPART[0]))%(map.length),((map[0].length-1)+DEF_DEPART[1])%(map[0].length));
					}
					else if (piece[i] == "Cavalier")
					{
						this.piece[i] = new Cavalier(((map.length-1)+(DEF_DEPART[0]))%(map.length),((map[0].length-1)+DEF_DEPART[1])%(map[0].length));
					}
					else if (piece[i] == "Fou")
					{
						this.piece[i] = new Fou(((map.length-1)+(DEF_DEPART[0]))%(map.length),((map[0].length-1)+DEF_DEPART[1])%(map[0].length));
					}
					else
					{
						System.err.println("Erreur fatale : \"" + piece + "\" n'est pas une pièce valide.");
						System.exit(1);
					}
					
					if (this.piece[i] instanceof Piece)
					{
						do
						{
							int [] temp = 
							{
								(((int)(Math.random()*((map.length)-1))%((map.length)/2))+((map.length)/2))
								,
								(((int)(Math.random()*((map[0].length)-1))%((map[0].length)/2))+((map[0].length)/2))
							};
							
							if (piece[i] == "Tour")
							{
								this.piece[i] = new Tour(temp);
							}
							else if (piece[i] == "Roi")
							{
								this.piece[i] = new Roi(temp);
							}
							else if (piece[i] == "Reine")
							{
								this.piece[i] = new Reine(temp);
							}
							else if (piece[i] == "Cavalier")
							{
								this.piece[i] = new Cavalier(temp);
							}
							else if (piece[i] == "Fou")
							{
								this.piece[i] = new Fou(temp);
							}
							
						} while (this.piece[i].nextWin());
					}
				}
				
				
				
				genMap();
				repaint();
			}
			
			/**
			 * 
			 * name: init
			 * @param taille pieces
			 * @return aucun
			 */
			
			public void init(int col, int row, String [] piece)
			{
				map = new int [col][row];
				init(piece);
			}
			
			/**
			 * 
			 * name: init
			 * @param taille pieces
			 * @return aucun
			 */
			
			public void init(int col, int row, String [] piece, boolean [] ia)
			{
				this.ia = ia;
				init(col,row,piece);
			}
			
			/**
			 * 
			 * name: newsingleton
			 * @param aucun
			 * @return Partie
			 */
			
			static public Partie newSingleton ()
			{
				if (!(instance instanceof Partie))
				{
					return new Partie();
				}
				return instance;
			}
			
			
			/**
			 * 
			 * name: newsingleton
			 * @param coordonnées
			 * @return Partie
			 */
			
			static public Partie newSingleton (int col, int row)
			{
				if (!(instance instanceof Partie))
				{
					return new Partie(col, row);
				}
				return instance;
			}
			
			
			/**
			 * 
			 * name: finish
			 * @param aucun
			 * @return aucun
			 */
			
			public void finish () // oxyaction
			{
				piece = null;
				selected = null;
				genMap();
				repaint();
			}
			
		////}
		
		//Accesseur
		////{
			
			
			
			/**
			 * 
			 * name: getJoueur
			 * @param aucun
			 * @return Map
			 */
			
			public String getJoueur ()
			{
				return "Joueur " + Integer.toString(joueur + 1);
			}
			
			
		////}
		
		//Mutateur
		////{
			
			
			
		////}
		
		//Traitement
		////{
			
			/**
			 * 
			 * name: win
			 * @param aucun
			 * @return boolean
			 */
			
			public boolean win ()
			{
				int [] temp = {(DEF_FINAL[0])%(map.length),(DEF_FINAL[1])%(map[0].length)};
				if (piece instanceof Piece [])
				{
					boolean c =true;
					for (int i=0; i<piece.length;i++)
					{
						if (piece[i] instanceof Piece)
						{
							c &= piece[i].place(temp);
						}
					}
					return c;
				}
				else
				{
					return false;
				}
			}
			
			
			/**
			 * 
			 * name: genMap
			 * @param aucun
			 * @return aucun
			 */
			
			public void select (int [] coord)
			{
				if (piece instanceof Piece [])
				{
					if (ia[joueur])
					{
						for (int i=0; i<piece.length; i++)
						{
							if (piece[i] instanceof Piece)
							{
								piece[i].ia();
							}
						}
						if (!(this.win()))
						{
							joueur = (joueur+1)%NB_JOUEUR;
						}
					}
					else
					{
						if (!(selected instanceof Piece))
						{
							boolean [] temp = new boolean [piece.length];
							int j =0;
							for (int i=0; i<piece.length; i++)
							{
								temp[i] = false;
								if (piece[i] instanceof Piece)
								{
									temp[i] = piece[i].place(coord);
									if (piece[i].place(coord))
									{
										if (j>0)
										{
											int rep = JOptionPane. showConfirmDialog(this ," Un" + (selected.getGENRE()?"e ":" ") + selected.getNAME() + " est déjà séléctionné, préfèrez-vous utiliser un" + (piece[i].getGENRE()?"e ":" ") + piece[i].getNAME() + "?" , "Pièces empilées" , JOptionPane.YES_NO_OPTION);
											if (rep == JOptionPane.YES_OPTION)
											{
												selected = piece[i];
											}
											j=0;
										}
										else
										{
											j++;
											selected = piece[i];
										}
										
									}
								}
							}
						}
						else
						{
							if (selected.allowCoord(coord))
							{
								selected.setCoord(coord);
								selected = null ;
								if (!(this.win()))
								{
									joueur = (joueur+1)%NB_JOUEUR;
									/*/
									if (ia[joueur])
									{
										int [] temp = {0,0};
										select(temp);
										genMap();
										repaint();
										try
										{
											Thread.sleep(1000);
										}
										catch (Exception e)
										{
											e.printStackTrace();
											System.exit(1);
										}
									}
									//*/
								}
							}
							else
							{
								for (int i=0; i<piece.length; i++)
								{
									if (piece[i] instanceof Piece)
									{
										if (piece[i].place(coord))
										{
											selected = piece[i];
										}
									}
								}
							}
						}
					}
					genMap();
					repaint();
				}
			}
			
			
			/**
			 * 
			 * name: genMap
			 * @param aucun
			 * @return aucun
			 */
			
			public void genMap()
			{
				for (int i=0;i<map.length; i++)
				{
					for (int j=0;j<map[0].length;j++)
					{
						map[i][j] = CE_VOID;
					}
				}
				if (selected instanceof Piece)
				{
					for (int i=0;i<map.length; i++)
					{
						for (int j=0;j<map[0].length;j++)
						{
							int [] coord = {i,j};
							if(selected.allowCoord(coord))
							{
								map[i][j] = CE_POSS;
							}
						}
					}
				}
				map[(DEF_FINAL[0])%(map.length)][(DEF_FINAL[1])%(map[0].length)]=CE_FINAL;
				if (piece instanceof Piece [])
				{
					for (int i=0;i<piece.length;i++)
					{
						if (piece[i] instanceof Piece)
						{
							map[(piece[i].getCoord()[0])%(map.length)][(piece[i].getCoord()[1])%(map[0].length)]= CE_FULL;
						}
					}
				}
				else
				{
					map[((map[0].length-1)+(DEF_DEPART[0]))%(map.length)][((map[0].length-1)+DEF_DEPART[1])%(map[0].length)]= CE_FULL;
				}
				
			}
			
			
			
			
			/**
			 * 
			 * name: genColor
			 * @param dec, tableau de composante
			 * @return Color
			 */
			
			public Color genColor(int dec, int [] comp)
			{
				if (piece instanceof Piece [])
				{
					//Méthode calcul de composante
					return new Color(comp[(dec+0*(comp.length/3))%comp.length],comp[(dec+1*(comp.length/3))%comp.length],comp[(dec+2*(comp.length/3))%comp.length]);
				}
				else
				{
					// N&B
					return new Color(comp[(dec)%comp.length],comp[(dec)%comp.length],comp[(dec)%comp.length]);
					
				}
			}
			
			
		////}
		
		//Affichage
		////{
			
			/**
			 * 
			 * name: paintComponent
			 * @param Graphic (appel auto)
			 * @return aucun
			 */
			
			public void paintComponent(Graphics g)
			{
				for(int i=0;i<map.length;i++)
				{
					for(int j=0;j<map[0].length;j++)
					{
						
						switch (map[i][j])
						{
							case CE_VOID:
								g.setColor(genColor((DECO_VOID[(i+j)%DECO_VOID.length])%COMP_VOID.length,COMP_VOID));
							break;
							case CE_POSS:
								g.setColor(genColor((i+j)%COMP_POSS.length,COMP_POSS));
							break;
							case CE_FINAL:
								g.setColor(genColor((DECO_VOID[(i+j)%DECO_VOID.length])%COMP_VOID.length,COMP_VOID));
							break;
							case CE_FULL:
								if (piece instanceof Piece [])
								{
									g.setColor(genColor((DECO_VOID[(i+j)%DECO_VOID.length])%COMP_VOID.length,COMP_VOID));
									//g.setColor(new Color(HF,HF,HF));
								}
								else
								{
									g.setColor(genColor(DECO_FULL[0],COMP));
								}
							break;
							default :
								g.setColor(genColor(map[i][j],COMP));
						}
						
						g.fillRect(i*DEF_WIDTH,(j+DEC)*DEF_HEIGTH,DEF_WIDTH,DEF_HEIGTH);
						g.setColor(new Color(HF,HF,HF));
						g.drawRect(i*DEF_WIDTH,(j+DEC)*DEF_HEIGTH,DEF_WIDTH,DEF_HEIGTH);
						
						
						if (piece instanceof Piece [])
						{
							for (int k=0; k<piece.length;k++) // les [k] de krys
							{
								if ( piece[k] instanceof Piece)
								{
									if (Main.IMAGE_MOD)
									{
										Image image = new ImageIcon(piece[k].toImage((DECO_FULL[joueur%DECO_FULL.length])%12)).getImage(); // Version Image
										g.drawImage(image, 
											(DEF_WIDTH*(piece[k].getCoord()[0])), 
											((DEF_HEIGTH*(piece[k].getCoord()[1]+DEC+1))-(DEF_WIDTH*image.getHeight(null))/image.getWidth(null)),
											DEF_WIDTH,
											(DEF_WIDTH*image.getHeight(null))/image.getWidth(null),
											this
										);
									}
									else
									{
										g.setFont(new Font("Sans" , 0 , DEF_HEIGTH));// Version texte
										g.setColor(genColor(DECO_FULL[joueur%DECO_FULL.length],COMP));
										g.drawString(piece[k].toString(), ((DEF_WIDTH*piece[k].getCoord()[0])+2), ((DEF_HEIGTH*(piece[k].getCoord()[1]+DEC)))+DEF_HEIGTH-2);
									}
								}
							}
						}
						
						if (Main.IMAGE_MOD)
						{
							Image image = new ImageIcon(FINAL_IMAGE).getImage(); // Version Image
							
							g.drawImage(image, 
								(DEF_WIDTH*(DEF_FINAL[0])), 
								((DEF_HEIGTH*(DEF_FINAL[1]+DEC+1))-(DEF_WIDTH*image.getHeight(null))/image.getWidth(null)),
								DEF_WIDTH,
								(DEF_WIDTH*image.getHeight(null))/image.getWidth(null),
								this
							);
							
							/*/
							image = new ImageIcon(DEPART_IMAGE[joueur]).getImage(); // Version Image
							System.err.println(DEPART_IMAGE[joueur]);
							g.drawImage(image, 
								(DEF_WIDTH*(map.length-DEF_DEPART[0])), 
								((DEF_HEIGTH*(map[0].length-DEF_DEPART[1]+DEC))-(DEF_WIDTH*image.getHeight(null))/image.getWidth(null)),
								DEF_WIDTH,
								(DEF_WIDTH*image.getHeight(null))/image.getWidth(null),
								this
							);
							//*/
						}
						else
						{
							g.setFont(new Font("Sans" , 0 , DEF_HEIGTH));// Version texte
							g.setColor(genColor(CE_FINAL,COMP));
							g.drawString(FINAL_SYMBOL, ((DEF_WIDTH*DEF_FINAL[0])+2), ((DEF_HEIGTH*(DEF_FINAL[1]+DEC)))+DEF_HEIGTH-2);
							
							/*/
							g.setFont(new Font("Sans" , 0 , DEF_HEIGTH));// Version texte
							g.setColor(genColor(DECO_FULL[joueur],COMP));
							g.drawString(DEPART_SYMBOL, ((DEF_WIDTH*(map.length-DEF_DEPART[0])+2)), ((DEF_HEIGTH*(map[0].length-DEF_DEPART[1]+1)))+DEF_HEIGTH-2);
							//*/
						}
						
					}
				}
			}
			
			public Dimension getPreferredSize ()
			{
				//Image image = new ImageIcon(Main.IMAGE_DIR + Piece.PIECE_DIR + Piece.NAME + Integer.toString((DECO_FULL[joueur%DECO_FULL.length])%12) + Piece.EXT).getImage();
				
				return new Dimension(DEF_ROW*DEF_WIDTH,(DEF_COL+DEC)*DEF_HEIGTH);
				//+(DEF_WIDTH*image.getHeight(null))/image.getWidth(null));
			}
			
		////}
		
		
	////}
	
}

