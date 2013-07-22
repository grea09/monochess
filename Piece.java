/** Titre
 *      @project Jeu déplacement de pièces - TP07 Java
 *      @class Piece
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

public abstract class Piece
{
	//Constantes
	////{
		
		final static int [] DEF_COORD = {0,0};
		final static int DIM = 2;
		final static String PIECE_DIR = "Piece/";
		final static String EXT = ".png";
		final static String NAME = "Piece";
		final static String SYMBOL = "";
		final static boolean GENRE = true; // true = féminin
		
	////}
	
	//Attributs
	////{
		
		protected int [] coord;
		protected boolean [][] win;
		//protected static int turns = 1;
		
	////}
	
	//Méthodes
	////{
		
		//Constructeur
		////{
			
			public Piece()
			{
				coord = new int [DIM];
				win = null;
			}
			
			public Piece(int [] coord)
			{
				this.coord = coord;
				win = new boolean [coord[0]+1][coord[1]+1];
				genWin();
			}
			
			public Piece(int abscisse, int ordonnee)
			{
				coord = new int [DIM];
				coord[0] = abscisse;
				coord[1] = ordonnee;
				win = new boolean [coord[0]+1][coord[1]+1];
				genWin();
			}
			
		////}
		
		//Accesseur
		////{
			
			
			/**
			 * 
			 * name: getCoord
			 * @param aucun
			 * @return Coord
			 */
			
			public int [] getCoord ()
			{
				return coord;
			}
			
			/**
			 * 
			 * name: getNAME
			 * @param aucun
			 * @return NAME
			 */
			
			abstract public String getNAME ();
			
			/**
			 * 
			 * name: getGENRE
			 * @param aucun
			 * @return GENRE
			 */
			
			abstract public boolean getGENRE ();
			
		////}
		
		//Mutateur
		////{
			
			/**
			 * 
			 * name: setCoord
			 * @param aucun
			 * @return Coord
			 */
			
			public void setCoord (int [] coord)
			{
				if (allowCoord(coord))
				{
					this.coord = coord;
				}
			}
			
			
		////}
		
		//Traitement
		////{
			
			/**
			 * 
			 * name: ia
			 * @param aucun
			 * @return aucun
			 */
			
			public void ia()
			{
				//*/
				int [] temp1 = coord;
				for(int i =coord[0]; i>=0; i--)
				{
					for(int j =coord[1]; j>=0; j--)
					{
						int [] temp = {i,j};
						if (canWin(temp))
						{
							setCoord(temp);
						}
					}
				}
				if (temp1 == coord)
				{
					for(int i =coord[0]; i>=0 && (temp1 == coord); i--)
					{
						for(int j =coord[1]; j>=0 && (temp1 == coord); j--)
						{
							int [] temp = {i,j};
							setCoord(temp);
						}
					}
				}
				//*/
			}
			
			/**
			 * 
			 * name: genWin
			 * @param aucun
			 * @return aucun
			 */
			
			public void genWin()
			{
				for(int i=0; i< win.length; i++)
				{
					for (int j=0;j<win[0].length;j++)
					{
						win[i][j] = false;
					}
				}
				
				for(int i=Partie.DEF_DEPART[0];i<win.length;i++)
				{
					for(int j= Partie.DEF_DEPART[1];j<win[0].length;j++)
					{
						if (!win[i][j])
						{
							int [] temp1 = {i,j};
							for(int k=temp1[0];k<win.length;k++)
							{
								for(int l=temp1[1];l<win[k].length;l++)
								{
									int [] temp = {k,l};
									win[k][l] = allowCoord(temp1,temp) && downright(temp1,temp);
								}
							}
						}
					}
				}
				/*/
				for(int i=0; i< win.length; i++)
				{
					System.err.print("{ ");
					for (int j= 0;j< win.length; j++)
					{
						System.err.print(win[i][j] + ", ");
					}
					System.err.println("}");
				}
				System.exit(0);
				//*/
			}
			
			
			/**
			 * 
			 * name: canWin
			 * @param aucun
			 * @return aucun
			 */
			
			public boolean canWin(int [] coord)
			{
				/*/ récursif
				if (nextWin(coord))
				{
					return (turns%Partie.NB_JOUEUR == 1);
				}
				else
				{
					turns++;
					for(int i =coord[0]; i>=0; i--)
					{
						for(int j =coord[1]; j>=0; j--)
						{
							int [] temp = {i,j};
							if (canWin(temp))
							{
								return (turns%Partie.NB_JOUEUR == 1);
							}
							else
							{
								turns--;
								return false;
							}
						}
					}
				}
				return false;
				//*/
				//*/
				return !win[coord[0]][coord[1]];
				//*/
			}
			
			/**
			 * 
			 * name: nextWin
			 * @param aucun
			 * @return aucun
			 */
			
			public boolean nextWin()
			{
				int [] temp = {(Partie.DEF_FINAL[0]),(Partie.DEF_FINAL[1])};
				return allowCoord(temp);
			}
			
			/**
			 * 
			 * name: nextWin
			 * @param coord
			 * @return aucun
			 */
			
			public boolean nextWin(int [] coord)
			{
				int [] temp = {(Partie.DEF_FINAL[0]),(Partie.DEF_FINAL[1])};
				return allowCoord(coord,temp) && !upleft(coord,temp);
			}
			
			
			/**
			 * 
			 * name: allowCoord
			 * @param from to
			 * @return aucun
			 */
			
			abstract public boolean allowCoord(int [] from, int [] to);
			
			/**
			 * 
			 * name: allowCoord
			 * @param coord
			 * @return bool
			 */
			
			public boolean allowCoord ( int [] coord)
			{
				return allowCoord(this.coord,coord) && upleft(coord);
			}
			
			/**
			 * 
			 * name: downright
			 * @param coord
			 * @return bool
			 */
			
			protected boolean downright( int [] coord)
			{
				
				return downright(this.coord,coord);
			}
			
			/**
			 * 
			 * name: downright
			 * @param from to
			 * @return bool
			 */
			
			protected boolean downright(int [] from, int [] to)
			{
				
				return ((to[0] >= from[0]) && (to[1] >= from[1])) && (!place(from,to));
			}
			
			/**
			 * 
			 * name: upleft
			 * @param coord
			 * @return bool
			 */
			
			protected boolean upleft( int [] coord)
			{
				
				return upleft(this.coord,coord);
			}
			
			/**
			 * 
			 * name: upleft
			 * @param from to
			 * @return bool
			 */
			
			protected boolean upleft(int [] from, int [] to)
			{
				
				return ((to[0] <= from[0]) && (to[1] <= from[1])) && (!place(from,to));
			}
			
			/**
			 * 
			 * name: place
			 * @param from to
			 * @return bool
			 */
			
			public boolean place( int [] from, int [] to)
			{
				boolean c = true;
				for (int j=0 ; j< from.length; j++)
				{
					c &= (from[j] == to[j]);
				}
				return c;
			}
			
			/**
			 * 
			 * name: place
			 * @param coord
			 * @return bool
			 */
			
			public boolean place( int [] coord)
			{
				return place(this.coord,coord);
			}
			
		////}
		
		//Affichage
		////{
			
			/**
			 * 
			 * name: toImage
			 * @param dec copuleur
			 * @return Nom du fichier
			 */
			
			abstract public String toImage (int dec);
			
			/**
			 * 
			 * name: toString
			 * @param aucun
			 * @return output
			 */
			
			abstract public String toString();
			
		////}
		
		
	////}
	
}

