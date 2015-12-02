package main.view;


import java.io.File;

import processing.core.*;



public class ThumbnailCreatorViewer extends PApplet{
	
	PFont font;
	
	
	/*-----------------------------------------------------------------------------------------
	 * Processing setup and draw methods
	 *----------------------------------------------------------------------------------------*/	
	 /**
	  * Processing setup method run immediately after constructor.
	  * Initialises key screen elements such as size and background.
	  * Run once
	  */
	@Override
	public void settings(){
		size(300,300);	
	}
	
	@Override
	public void setup() {  
		PFont font = createFont("helvetica",20);
		textFont(font);
		
	
	}
		
	 /**
	   * Processing draw method runs in a loop immediately after setup()
	   */
	@Override
	public void draw() {
		Colour[] colours = Colour.values();
		int r;
		int g;
		int b;
		for(int i = 0; i <= 5; i++){
			
			
			
			r = colours[i % colours.length].getR();
			g = colours[i % colours.length].getG();
			b = colours[i % colours.length].getB();
			
			textSize(35);
			stroke(153);
			textAlign(CENTER,CENTER);
			background(r, g, b);
			
			fill(0,0,0);
			text("Artist name",151,116); //artist
			fill(255,255,255);
			text("Artist name",150,115); //artist
			
			fill(0,0,0);
			text("Song title",151,166); //title
			fill(255,255,255);
			text("Song title",150,165); //title
			
			
			
			save("C:/Julian/git/thumbnailCreator/output/test" + i + ".png");
		}
		noLoop();
	}
	


		
	
	
}