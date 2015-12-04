package main.view;




import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.core.*;



public class ThumbnailCreatorViewer extends PApplet{
	
	PFont font;
	PImage imgMan1;
	PImage imgMan2;
	PImage imgMan3;
	PImage imgWoman1;
	PImage imgWoman2;
	List<String> tracks = new ArrayList<String>();
	
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
	 * Takes a string and adds line breaks if over threshold
	 * 
	 * @param str
	 * @return str
	 */
	private String prepString(String str){
		List<String> list1 = new ArrayList<String> (Arrays.asList(str.split(" ")));
		list1.add("");
	
		if(list1.size() > 5 && str.length() > 15){
			//insert linebreak after second element
			list1.add(3, "\n");
			list1.add(6, "\n");
		} else if (list1.size() > 3 && str.length() > 15){
			list1.add(3, "\n");
		}
		
		
		StringBuilder strb = new StringBuilder();
	
		for(String s: list1){
			strb.append(s + " ");	
		}
		return strb.toString().substring(0, strb.length() -2);
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
			
			String artistName = prepString(track[0]);
			String trackName = prepString(track[1]);
			
			stroke(153);
			textAlign(CENTER,CENTER);
			background(r, g, b);
			
			image(imgMan1,0,0);
			textSize(22);
			fill(0,0,0);
			text(artistName,102,57); //artist
			fill(255,255,255);
			text(artistName,100,55); //artist
			
			textSize(18);
			fill(0,0,0);
			text(trackName,102,127); //title
			fill(255,255,255);
			text(trackName,100,125); //title
			
			
			
			save("C:/Julian/git/thumbnailCreator/output/" + artistName.replaceAll("[^a-zA-Z0-9]","") +"_"+ trackName.replaceAll("[^a-zA-Z0-9]","") + ".png");
			i++;
		}
		noLoop();
	}
	


		
	
	
}