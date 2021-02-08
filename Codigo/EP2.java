import java.io.*;
import java.util.*;

public class EP2 {

	public static final boolean DEBUG = false;

	public static void main(String[] args) throws IOException {
		
		Map map = new Map(args[0]);

		if(DEBUG){ 
			map.print(); 
			System.out.println("---------------------------------------------------------------");
		}

		int criteria = Integer.parseInt(args[1]);
		Path path = findSolution(map, criteria);
		path.print();
	}

	public static boolean checkCo(Map map, int lin, int col){

		try {
			if(map.free(lin, col))
				return true;
		}
		catch(Exception e){
		}

		return false;
	}

	public static Path findSolution(Map map, int criteria){

		Criteria crit = null;

		switch(criteria){

			case 1:
				crit = new ShorterPath();
				break;

			case 2:
				crit = new LongerPath();
				break;

			case 3:
				crit = new MostValuePath();
				break;

			case 4:
				crit = new FastestPath();
				break;

			default:
				System.out.println("ERROR: Invalid criteria");
		}

		findPath(map, map.getStartLin(), map.getStartCol(), new Path(), crit);

		return Path.getBestPath();
	}

	public static void findPath(Map map, int lin, int col, Path p, Criteria crit){

		if(checkCo(map, lin, col)){

			Item item = map.getItem(lin, col);
			Path prevPath = new Path(p);

			p.addCoord(lin, col, item);
			map.step(lin, col);

			if(map.finished(lin, col)){

				if(Path.getBestPath() == null || crit.isBetter(p, Path.getBestPath()))
					Path.setBestPath(p);

				printMap(map);
				p.clone(prevPath);
				map.undoStep(lin, col);

				return;
			}
			
			printMap(map);

			findPath(map, lin, col+1, p, crit);
			findPath(map, lin-1, col, p, crit);
			findPath(map, lin, col-1, p, crit);
			findPath(map, lin+1, col, p, crit);

			p.clone(prevPath);

			map.undoStep(lin, col);
			printMap(map);
		}

		return;
	}

	public static void printMap(Map map){

		if(DEBUG && false){
			map.print();
			new Scanner(System.in).nextLine();
		}
	}
}