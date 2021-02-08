import java.io.*;
import java.util.*;

class Item {

	private int lin, col, value, weight;

	public Item(String s){

		String [] parts = s.split(" +");		
		lin = Integer.parseInt(parts[0]);
		col = Integer.parseInt(parts[1]);
		value = Integer.parseInt(parts[2]);
		weight = Integer.parseInt(parts[3]);
	}

	public Item(int lin, int col, int value, int weight){

		this.lin = lin;
		this.col = col;
		this.value = value;
		this.weight = weight;
	}

	public int getLin(){

		return lin;
	}

	public int getCol(){

		return col;
	}

	public int [] getCoordinates(){

		return new int [] { getLin(), getCol() } ;
	}

	public int getValue(){

		return value;
	}

	public int getWeight(){

		return weight;
	}

	public String toString(){

		return "Item: coordinates = (" + getLin() + ", " + getCol() + "), value = " + getValue() + " weight = " + getWeight();
	}
}

class Map {

	public static final char FREE = '.';

	private char [][] map;
	private Item [] items;
	private int nLin, nCol, nItems, startLin, startCol, endLin, endCol, size;

	public Map(String fileName){

		try{

			BufferedReader in = new BufferedReader(new FileReader(fileName));

			Scanner scanner = new Scanner(new File(fileName));

			
			nLin = scanner.nextInt();
			nCol = scanner.nextInt();

			map = new char[nLin][nCol];
			size = 0;

			for(int i = 0; i < nLin; i++){

				String line = scanner.next();
			
				for(int j = 0; j < nCol; j++){

					map[i][j] = line.charAt(j);

					if(free(i, j)) size++;
				}
			}

			nItems = scanner.nextInt();
			items = new Item[nItems];

			for(int i = 0; i < nItems; i++){

				items[i] = new Item(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
			}

			startLin = scanner.nextInt();
			startCol = scanner.nextInt();
			endLin = scanner.nextInt();
			endCol = scanner.nextInt();
		}
		catch(IOException e){

			System.out.println("Error loading map... :(");
			e.printStackTrace();
		}
	}

	public void print(){

		System.out.println("Map size (lines x columns): " + nLin + " x " + nCol);

		for(int i = 0; i < nLin; i++){

			for(int j = 0; j < nCol; j++){

				System.out.print(map[i][j]);
			}

			System.out.println();
		}

		System.out.println("Number of items: " + nItems);

		for(int i = 0; i < nItems; i++){

			System.out.println(items[i]);
		}
	}

	public boolean blocked(int lin, int col){

		return !free(lin, col);
	}

	public boolean free(int lin, int col){

		return map[lin][col] == FREE; 
	}

	public void step(int lin, int col){

		map[lin][col] = '*';
	}

	public boolean finished(int lin, int col){

		return (lin == endLin && col == endCol); 
	}

	public int getStartLin(){

		return startLin;
	}

	public int getStartCol(){

		return startCol;
	}

	public int getSize(){

		return size;
	}

	public int nLines(){

		return nLin;
	}

	public int nColumns(){

		return nCol;
	}

	public Item getItem(int lin, int col){

		for(int i = 0; i < items.length; i++){

			Item item = items[i];

			if(item.getLin() == lin && item.getCol() == col) return item;
		}

		return null;
	}
}

public class EP2 {

	public static final boolean DEBUG = false;

	public static int [] findPath(Map map, int criteria){

		int lin, col; // coordenadas (lin, col) da posição atual
		
		// path é um vetor de inteiro usado para guardar as coordenadas do caminho conforme vai sendo calculado.
		// path_index é usado para gerenciar a ocupação deste vetor. O vetor é usado da seguinte forma:
		//
		// path[0] = quantidade de valores efetivamente armazenados no vetor (não necessáriamente coincide com o tamanho real do vetor)
		// path[1] = linha da 1.a coordenada que faz parte do caminho
		// path[2] = coluna da 1.a coordenada que faz parte do caminho
		// path[3] = linha da 2.a coordenada que faz parte do caminho
		// path[4] = coluna da 2.a coordenada que faz parte do caminho
		// ... etc

		int [] path;  
		int path_index;

		path = new int[2 * map.getSize()];
		path_index = 1;

		lin = map.getStartLin();
		col = map.getStartCol();
		
		// efetivação de um passo
		map.step(lin, col);		// marcamos no mapa que a posição está sendo ocupada.
		path[path_index] = lin;		// adicionamos as coordenadas da posição (lin, col) no path 
		path[path_index + 1] = col;
		path_index += 2;

		if(DEBUG){

			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}
		
		while(!map.finished(lin, col)){

			if(lin - 1 >= 0 && map.free(lin - 1, col)){			// cima
				
				//System.out.println("UP");	
				lin = lin - 1;
			}
			else if(col + 1 < map.nColumns() && map.free(lin, col + 1)){	// direita

				//System.out.println("RIGHT");	
				col = col + 1;
			}
			else if(lin + 1 < map.nLines() && map.free(lin + 1, col)){	// baixo

				//System.out.println("DOWN");	
				lin = lin + 1;
			}
			else if(col - 1 >= 0 && map.free(lin, col - 1)){		// esquerda

				//System.out.println("LEFT");	
				col = col - 1;
			}
			else{
				//System.out.println("BREAK!");	
				break; // não existe passo a ser dado a partir da posição atual... 
			}

			map.step(lin, col);
			path[path_index] = lin;
			path[path_index + 1] = col;
			path_index += 2;

			if(DEBUG){ 
				map.print(); 
				System.out.println("---------------------------------------------------------------");
			}
		}
		
		path[0] = path_index;
		return path;
	}

	public static void printSolution(Map map, int [] path){

		// A partir do mapa e do path contendo a solução, imprime a saída conforme especificações do EP.

		int totalItems = 0;
		int totalValue = 0;
		int totalWeight = 0;

		int path_size = path[0];

		System.out.println((path_size - 1)/2 + " " + 0.0);

		for(int i = 1; i < path_size; i += 2){

			int lin = path[i];
			int col = path[i + 1];
			Item item = map.getItem(lin, col);

			System.out.println(lin + " " + col);

			if(item != null){

				totalItems++;
				totalValue += item.getValue();
				totalWeight += item.getWeight();
			}
		}

		// Estamos ignorando os itens que são coletados no caminho. Isso precisa ser modificado para a versão final.
		System.out.println("0 0 0");
	}

	public static void main(String [] args) throws IOException {
	
		Map map = new Map(args[0]);

		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}

		int criteria = Integer.parseInt(args[1]);
		int [] path = findPath(map, criteria);
		printSolution(map, path);
	}
}