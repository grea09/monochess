/** Titre
 *      @project Jeu déplacement de pièces - TP07 Java
 *      @class Main
 *      @description Programme principal
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

public class Main
{
	//Constantes
	////{
		
		static final String IMAGE_DIR = "Ressources/";
		static final boolean IMAGE_MOD = false ; // Mode image
		
	////}
	
	//MAIN !!!
	////{
		
		public static void main(String[] args)
		{
			JFrame fen = new JFrame();
			MyPanel pan = new MyPanel(fen);
			fen.add(pan);
			//fen.setSize(256,256);
			fen.setTitle("Jeu déplacement de pièces");
			fen.setIconImage(new ImageIcon(IMAGE_DIR + "glchess.png").getImage());
			fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fen.pack();
			fen.setLocationRelativeTo(null);
			fen.setVisible(true);
			
		}
		
	////}
	
	//Methodes
	////{
		
		
		
	////}
}


