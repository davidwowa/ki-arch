package ki.solvers;

import ki.model.IKIModel;
import ki.plausibility.IPlausibility;
import ki.solvers.solution.ISolution;

public interface IKISolver extends IPlausibility {
	ISolution solve(IKIModel model);
}