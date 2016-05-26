package ki.solvers;

import java.util.Map;

public class Solution {

	private String id;
	private Map<String, String> solutionMap;

	public Solution() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getSolutionMap() {
		return solutionMap;
	}

	public void setSolutionMap(Map<String, String> solutionMap) {
		this.solutionMap = solutionMap;
	}
}