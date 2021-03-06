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
				 System.out.println(absFilePath);
				 String fileNameSplit = next.getName().substring(0, next.getName().length()-4);
				 if (validFile(absFilePath)){
					 backgrounds.put(Genre.valueOf(fileNameSplit), loadImage(absFilePath));
				 }
			 }
			//init icon images
			 for(File next: iconDir.listFiles()) {
				 String absFilePath = next.getAbsolutePath();
				 
				 String fileNameSplit = next.getName().substring(0, next.getName().length()-4);
				 if (validFile(absFilePath)){
					 icons.put(Genre.valueOf(fileNameSplit), loadImage(absFilePath));
				 }	
			 }
		 } catch (IllegalArgumentException e){
			 System.out.println(e);
			 printExceptionToLogFile(e);
		 }
	 }
	
	private boolean validFile(String fileName){
		return (fileName.endsWith(".png") || fileName.endsWith(".jpg"));
	}
	
	
	/*-----------------------------------------------------------------------------------------
	 * Main process
	 *----------------------------------------------------------------------------------------*/			
	
	//Main process starts here
	@Override
	public void draw() throws IllegalArgumentException{	
		stroke(153);
		textAlign(CENTER,CENTER);
		
		//PImage transparent = backgrounds.get(Genre.valueOf("transparent"));
		for(String next: tracks){
			clear();
			if(! next.isEmpty()){ //protects against empty lines in data file
				String[] track = next.split("\t");	
				String artistName = prepString(track[1]);
				String trackName = prepString(track[2]);
				Genre genre = null;
				
				try{
					genre = getGenre(track[3]);
				} catch (IllegalArgumentException e){
					printExceptionToLogFile(e);
				}
					
				//bg image
				image(backgrounds.get(genre),0,0);
				tint(200,200);
				//image(transparent ,0,0);
				//icon
				PImage icon =icons.get(genre);
				//icon.resize(150, 150);
				image(icons.get(genre),0,0);
				
				textSize(20);
				fill(0,0,0);
				text(artistName,102,52); //artist
				fill(255,255,255);
				text(artistName,100,50); //artist
				
				textSize(24);
				fill(0,0,0);
				text(trackName,102,137); //title
				fill(255,255,255);
				text(trackName,100,135); //title
				
				
				save(outputDir.getAbsolutePath().toString() + "/" + track[0] + 
						" - " + artistName.replaceAll("[^a-zA-Z0-9]","") + " - " + 
						trackName.replaceAll("[^a-zA-Z0-9]","") + "_" + 
						track[3] + ".png");
			}
	
		}
		
		
		noLoop();
		
	}
	 
	private Genre getGenre(String str) throws IllegalArgumentException{
		return Genre.valueOf(str);	
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
	
		if(list1.size() > 5 && str.length() > 10){
			//insert linebreak after second element
			list1.add(2, "\n");
			list1.add(5, "\n");
			list1.add(8, "\n");
		} else if (list1.size() > 2 && str.length() > 10){
			list1.add(2, "\n");
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