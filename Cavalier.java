/** Titre
 *      @project Jeu déplacement de pièces - TP07 Java
 *      @class Cavalier
 *      @description Classe pour gérer un Cavalier
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
import java.math.*;

public class Cavalier extends Piece
{
	//Constantes
	////{
		
		final static String NAME = "Cavalier";
		final static String SYMBOL = "♞";
		final static boolean GENRE = false; // true = féminin
		
	////}
	
	//Attributs
	////{
		
		
		
	////}
	
	//Méthodes
	////{
		
		//Constructeur
		////{
			
			public Cavalier()
			{
				super();
			}
			
			public Cavalier(int [] coord)
			{
				super(coord);
			}
			
			public Cavalier(int abscisse, int ordonnee)
			{
				super(abscisse,ordonnee);
			}
			
			
		////}
		
		//Accesseur
		////{
			
			/**
			 * 
			 * name: getNAME
			 * @param aucun
			 * @return NAME
			 */
			
			public String getNAME ()
			{
				return NAME;
			}
			
			/**
			 * 
			 * name: getGENRE
			 * @param aucun
			 * @return GENRE
			 */
			
			public boolean getGENRE ()
			{
				return GENRE;
			}
			
			
		////}
		
		//Mutateur
		////{
			
			
			
		////}
		
		//Traitement
		////{
			
			/**
			 * 
			 * name: allowCoord
			 * @param coord
			 * @return aucun
			 */
			
			public boolean allowCoord(int [] from, int [] to)
			{
				return (
				(
					(
							(Math.abs(to[0]-from[0])==1)
						&&
							(Math.abs(to[1]-from[1])==2)
					)
				||
					(
							(Math.abs(to[0]-from[0])==2)
						&&
							(Math.abs(to[1]-from[1])==1)
					)
				)); // On teste toutes les combinaison de l'ensemble {-2,-1,1,2}
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
			
			public String toImage (int dec)
			{
				return Main.IMAGE_DIR + PIECE_DIR + NAME + Integer.toString(dec) + EXT;
			}
			
			/**
			 * 
			 * name: toString
			 * @param aucun
			 * @return output
			 */
			
			public String toString()
			{
				/*String temp = NAME + " : ( ";
				for (int i=0; i<coord.length-1; i++)
				{
					temp += Integer.toString(coord[i]) + ", ";
				}
				temp += Integer.toString(coord[coord.length-1]) + " )";
				return temp;*/
				return SYMBOL;
			}
			
		////}
		
		
	////}
	
}

