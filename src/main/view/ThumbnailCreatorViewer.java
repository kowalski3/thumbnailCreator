package main.view;




import java.io.BufferedReader;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.*;



public class ThumbnailCreatorViewer extends PApplet{
	Map<Genre, PImage> icons;
	Map<Genre, PImage> backgrounds;
	PFont font;
	List<String> tracks = new ArrayList<String>();

	PImage transparency;
	
	File iconDir;
	File bgDir;
	File bgTransDir;
	File outputDir;
	File fontDir;
	
	
	
	/*-----------------------------------------------------------------------------------------
	 * Constructor and initialiser
	 *----------------------------------------------------------------------------------------*/	
	 public ThumbnailCreatorViewer(){
		 icons = new HashMap<>();
		 backgrounds = new HashMap<>();
	
		 loadTracks("./data/tracks_tabDelimited.txt");
		 iconDir = new File("./icons");
		 bgDir = new File("./bg");
		 bgTransDir = new File("./bgTrans");
		 outputDir = new File("./output/");
		 fontDir = new File("./fonts");
	 }
	
	
	
	 private void loadTracks(String filename) {	
		 try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while((line = reader.readLine()) != null){
				tracks.add(line);
			}
				
		} catch (IOException e) {
			printExceptionToLogFile(e);
		}
		
	}
	 
	/*-----------------------------------------------------------------------------------------
	 * Processing setup and draw methods
	 *----------------------------------------------------------------------------------------*/	
	 /**
	  * Processing setup method run immediately after constructor.
	  * Initialises key screen elements such as size and background.
	  * Run once
	  */
	 
	@Override
	public void setup() {  
		PFont font = createFont(fontDir + "/" + "Helvetica.otf",20);
		textFont(font);
	}
	
	
	@Override
	public void settings(){
		size(200,200);	
		initaliseImages();
	}
	//must be called from settings
	private void initaliseImages(){		
		try{
			 //init background images
			 for(File next: bgDir.listFiles()) {
				 String absFilePath = next.getAbsolutePath();
				 String fileNameSplit = next.getName().substring(0, next.getName().length()-4);
				 PImage x =  backgrounds.put(Genre.valueOf(fileNameSplit), loadImage(absFilePath));
			 }
			//init icon images
			 for(File next: iconDir.listFiles()) {
				 String absFilePath = next.getAbsolutePath();
				 String fileNameSplit = next.getName().substring(0, next.getName().length()-4);
				 icons.put(Genre.valueOf(fileNameSplit), loadImage(absFilePath));	
			 }
			 
			
			 
		 } catch (IllegalArgumentException e){
			 System.out.println(e);
			 printExceptionToLogFile(e);
		 }
		 

	 }
	
	
	/*-----------------------------------------------------------------------------------------
	 * Main process
	 *----------------------------------------------------------------------------------------*/			
	
	//Main process starts here
	@Override
	public void draw(){	
		stroke(153);
		textAlign(CENTER,CENTER);
		
		//PImage transparent = backgrounds.get(Genre.valueOf("transparent"));
		for(String next: tracks){
			clear();
			String[] track = next.split("\t");	
			String artistName = prepString(track[0]);
			String trackName = prepString(track[1]);
			Genre genre = Genre.valueOf(track[2]);
				
			//bg image
			image(backgrounds.get(genre),0,0);
			tint(180,180);
			//add transparent layer
			//image(transparent ,0,0);
			//icon
			image(icons.get(genre),0,0);
			
			textSize(22);
			fill(0,0,0);
			text(artistName,102,47); //artist
			fill(255,255,255);
			text(artistName,100,45); //artist
			
			textSize(22);
			fill(0,0,0);
			text(trackName,102,137); //title
			fill(255,255,255);
			text(trackName,100,135); //title
			
			
			save(outputDir.getAbsolutePath().toString() + "/" + artistName.replaceAll("[^a-zA-Z0-9]","") +"_"+ trackName.replaceAll("[^a-zA-Z0-9]","") + ".png");
	
		}
		
		
		noLoop();
		
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
	 
	 
	 
	
	
	
	
	
	
	private void printExceptionToLogFile(Exception e){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("error.log"));
			e.printStackTrace(pw);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		pw.close();
		
	}
	
	
//Old draw method using colours on a loop to generate backgrounds
//	/**
//	   * Processing draw method runs in a loop immediately after setup()
//	   */
//	@Override
//	public void draw() {
//		Colour[] colours = Colour.values();
//		int r;
//		int g;
//		int b;
//		
//		//System.out.println(colours[0].toString());
//		
//		int i = 0;
//		for(String next: tracks){
//			String[] track = next.split(",");
//			
//			
//			r = colours[i % colours.length].getR();
//			g = colours[i % colours.length].getG();
//			b = colours[i % colours.length].getB();
//			
//			String artistName = prepString(track[0]);
//			String trackName = prepString(track[1]);
//			
//			stroke(153);
//			textAlign(CENTER,CENTER);
//			background(r, g, b);
//			
//			if(track[2].equals("MALE")) {
//				image(men.get(i % men.size()),0,0);	
//			} else{
//				image(women.get(i % women.size()),0,0);	
//			}
//			
//			
//			textSize(22);
//			fill(0,0,0);
//			text(artistName,102,57); //artist
//			fill(255,255,255);
//			text(artistName,100,55); //artist
//			
//			textSize(18);
//			fill(0,0,0);
//			text(trackName,102,127); //title
//			fill(255,255,255);
//			text(trackName,100,125); //title
//			
//			
//			
//			save("C:/Julian/git/thumbnailCreator/output/" + artistName.replaceAll("[^a-zA-Z0-9]","") +"_"+ trackName.replaceAll("[^a-zA-Z0-9]","") + ".png");
//			i++;
//		}
//		noLoop();
//	}
	


		
	
	
}