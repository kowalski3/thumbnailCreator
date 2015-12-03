package main.view;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import processing.core.*;



public class ThumbnailCreatorViewer extends PApplet{
	
	PFont font;
	PImage imgMan1;
	PImage imgMan2;
	PImage imgMan3;
	PImage imgWoman1;
	PImage imgWoman2;
	LinkedList<String> tracks = new LinkedList<String>();
	
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
		size(200,200);	
	}
	
	@Override
	public void setup() {  
		
		loadTracks("C:/Julian/git/thumbnailCreator/data/tracks.csv");
		
		PFont font = createFont("C:/Julian/Helvetica.otf",20);
		textFont(font);
		imgMan1 = loadImage("C:/Julian/git/thumbnailCreator/img/man1.png");
		imgMan2 = loadImage("C:/Julian/git/thumbnailCreator/img/man2.png");;
		imgMan3 = loadImage("C:/Julian/git/thumbnailCreator/img/man3.png");;
		imgWoman1 = loadImage("C:/Julian/git/thumbnailCreator/img/girl1.png");;
		imgWoman2 = loadImage("C:/Julian/git/thumbnailCreator/img/girl2.png");;
	
	}
		
	 private void loadTracks(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while((line = reader.readLine()) != null){
				tracks.add(line);
			}
				
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		int i = 0;
		for(String next: tracks){
			String[] track = next.split(",");
			
			
			r = colours[i % colours.length].getR();
			g = colours[i % colours.length].getG();
			b = colours[i % colours.length].getB();
			
			textSize(20);
			stroke(153);
			textAlign(CENTER,CENTER);
			background(r, g, b);
			
			image(imgMan1,0,0);
			
			fill(0,0,0);
			text(track[0],102,67); //artist
			fill(255,255,255);
			text(track[0],100,65); //artist
			
			fill(0,0,0);
			text(track[1],102,117); //title
			fill(255,255,255);
			text(track[1],100,115); //title
			
			
			
			save("C:/Julian/git/thumbnailCreator/output/test" + i + ".png");
			i++;
		}
		noLoop();
	}
	


		
	
	
}