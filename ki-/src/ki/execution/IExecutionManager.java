package ki.execution;

import java.util.Observer;

import ki.cache.ICache;
import ki.plausibility.IPlausibility;
import ki.singleton.ISingleton;
import ki.solvers.solution.ISolution;

public interface IExecutionManager extends ICache, IPlausibility, ISingleton, Observer {

	void execute(ISolution solution);

}
