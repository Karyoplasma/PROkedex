package core;

public class DistanceResult {
	private String word;
	private int distance;

	public DistanceResult(String word, int distance) {
		this.word = word;
		this.distance = distance;
	}

	public String getWord() {
		return word;
	}

	public int getDistance() {
		return distance;
	}
}
