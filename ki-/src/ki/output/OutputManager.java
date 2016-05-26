package ki.output;

import java.util.ArrayList;
import java.util.List;

import ki.cache.ICache;
import ki.plausibility.IPlausibility;
import ki.solvers.solution.ISolution;

public class OutputManager implements ICache, IPlausibility {

	private static OutputManager instance;

	private List<IOutput> cache;

	private IOutput output;

	private OutputManager() {
		setCache(new ArrayList<>());
	}

	public static OutputManager getInstance() {
		if (instance == null) {
			instance = new OutputManager();
		}
		return instance;
	}

	public void go(ISolution solution) {
		// convert to output
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

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	public List<IOutput> getCache() {
		return cache;
	}

	public void setCache(List<IOutput> cache) {
		this.cache = cache;
	}

	public IOutput getOutput() {
		return output;
	}

	public void setOutput(IOutput output) {
		this.output = output;
	}
}