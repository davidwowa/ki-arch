package ki.solvers.impl;

import java.util.Observable;

import ki.execution.ExecutionManager;
import ki.model.IKIModel;
import ki.solvers.IKISolver;
import ki.solvers.solution.ISolution;
import ki.solvers.solution.Solution;

public class PredictionSolver extends Observable implements IKISolver {

	private ISolution currentSolution;

	public PredictionSolver() {
		this.addObserver(ExecutionManager.getInstance());
	}

	@Override
	public ISolution solve(IKIModel model) {
		// calculate solution
		ISolution solution = new Solution();
		if (check()) {
			setCurrentSolution(solution);
			notifyObservers(solution);
		}
		return null;
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	public ISolution getCurrentSolution() {
		return currentSolution;
	}

	public void setCurrentSolution(ISolution currentSolution) {
		this.currentSolution = currentSolution;
	}
}
