package si.model;

public enum AlienType {
	A(45, 45, 50), B(35, 35,100), C(25, 25, 200), D(60, 45, 500), E(16, 18,1000);
	//A(0.5), B(0.3), C(0.2);
	private double size;
	private int width;
	private int height;
	private int score;
	public static double largeSize = 0.8;
	public static double middleSize = 0.6;
	public static double littleSize = 0.4;

	private AlienType(int w, int h, int s) {
		width = w;
		height = h;
		score = s;
	}

	private AlienType(double s){
		size = s;
	}
	public double getSize(){return size;}
	public int getWidth() {
		return width;
	}

	public int getScore() {
		return score;
	}

	public int getHeight() {return height; }
}
