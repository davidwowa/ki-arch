package ki.execution;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import ki.output.OutputManager;
import ki.solvers.solution.ISolution;

public class ExecutionManager implements IExecutionManager {

	private static ExecutionManager instance;

	private Map<String, ISolution> cache;
	private ISolution currentSolution;

	private Map<String, IExecution> excCache;
	private IExecution currentExection;

	private ExecutionManager() {
		setCache(new HashMap<>());
	}

	public static ExecutionManager getInstance() {
		if (instance == null) {
			instance = new ExecutionManager();
		}
		return instance;
	}

	@Override
	public void update(Observable o, Object arg) {
		execute((ISolution) arg);
	}

	@Override
	public void execute(ISolution arg) {
		setCurrentSolution((ISolution) arg);
		IExecution execution = createExecution(currentSolution);
		if (check()) {
			add();
			OutputManager.getInstance().go(execution);
		} else {
			stop();
		}
	}

	IExecution createExecution(ISolution currentSolution) {
		// convert to execute code
		return null;
	}

	@Override
	public boolean check() {
		// TODO check solution
		return false;
	}

	@Override
	public void stop() {
		// TODO stop this process

	}

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	public Map<String, ISolution> getCache() {
		return cache;
	}

	public void setCache(Map<String, ISolution> cache) {
		this.cache = cache;
	}

	public ISolution getCurrentSolution() {
		return currentSolution;
	}

	public void setCurrentSolution(ISolution currentSolution) {
		this.currentSolution = currentSolution;
	}
}