class Coord {
	
	private int lin;
	private int col;

	public Coord(int lin, int col){

		this.lin = lin;
		this.col = col;
	}

	public int getLin(){

		return this.lin;
	}

	public int getCol(){

		return this.col;
	}

	public String toString(){

		return lin + "-" + col;
	}
}