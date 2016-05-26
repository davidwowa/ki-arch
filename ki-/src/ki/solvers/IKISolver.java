package ki.solvers;

import ki.model.KIModel;
import ki.plausibility.IPlausibility;

public interface IKISolver extends IPlausibility {
	Solution solve(KIModel model);
}