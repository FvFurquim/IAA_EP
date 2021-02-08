public interface Criteria {

	public boolean isBetter(Path path1, Path path2);
}

class ShorterPath implements Criteria {

	public boolean isBetter(Path path1, Path path2){

		return path1.getPath().size() < path2.getPath().size();
	}
}

class LongerPath implements Criteria {

	public boolean isBetter(Path path1, Path path2){

		return path1.getPath().size() >= path2.getPath().size();
	}
}

class MostValuePath implements Criteria {

	public boolean isBetter(Path path1, Path path2){

		return path1.getItensValue() > path2.getItensValue();
	}
}

class FastestPath implements Criteria {

	public boolean isBetter(Path path1, Path path2){

		return path1.getTime() < path2.getTime();
	}
}