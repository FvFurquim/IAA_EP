import java.util.*;

class Path {
	
	private static Path bestPath = null;
	
	private ArrayList<Coord> pathCo;
	private ArrayList<Item> itemsCo;
	private double time;
	private int itensWeight;
	private int itensValue;

	public Path(Path clone){

		this.pathCo = new ArrayList<>(clone.pathCo);
		this.itemsCo = new ArrayList<>(clone.itemsCo);
		this.time = clone.time;
		this.itensWeight = clone.itensWeight;
		this.itensValue = clone.itensValue;
	}

	public Path(){

		this.pathCo = new ArrayList<>();
		this.itemsCo = new ArrayList<>();
		this.time = -1;
		this.itensWeight = 0;
		this.itensValue = 0;
	}

	public void clone(Path clone){

		this.pathCo = new ArrayList<>(clone.pathCo);
		this.itemsCo = new ArrayList<>(clone.itemsCo);
		this.time = clone.time;
		this.itensWeight = clone.itensWeight;
		this.itensValue = clone.itensValue;
	}

	public void addCoord(int lin, int col, Item item){

		addCoord(lin, col);

		if(item == null)
			return;
		
		this.itemsCo.add(item);
		this.itensWeight += item.getWeight();
		this.itensValue += item.getValue();
	}

	public void addCoord(int lin, int col){

		this.pathCo.add(new Coord(lin, col));

		this.time += Math.pow((1.0 + (this.itensWeight/10.0)), 2);
	}

	public static void setBestPath(Path newPath){

		Path.bestPath = new Path(newPath);
	}

	public static Path getBestPath(){

		return Path.bestPath;
	}

	public ArrayList<Coord> getPath(){

		return this.pathCo;
	}

	public double getTime(){

		return this.time;
	}

	public int getNumOfItens(){

		return this.itemsCo.size();
	}

	public int getItensWeight(){

		return this.itensWeight;
	}

	public int getItensValue(){

		return this.itensValue;
	}

	public void print(){
		
		System.out.printf("%d %.2f\n", this.pathCo.size(), this.time);

		for(Coord c : this.pathCo)
			System.out.println(c.getLin() + " " + c.getCol());

		System.out.println(this.itemsCo.size() + " " + this.itensValue + " " + this.itensWeight);
		
		for(Item i : this.itemsCo)
			System.out.println(i.getLin() + " " + i.getCol());
	}
}