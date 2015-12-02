package main.view;

public enum Colour {

	RED(255,0,0),
	YELLOW(230,230,0),
	GREEN(0,206,0),
	LBLUE(0,128,255),
	DBLUE(0,128,192),
	ORANGE(255,128,0);
	
	private int r;
	private int g;
	private int b;
	
	private Colour(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public int getR(){
		return this.r;
	}
	
	public int getG(){
		return this.g;
	}
	
	public int getB(){
		return this.b;
	}
}

