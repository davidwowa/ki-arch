package ki.flow;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ki.cache.ICache;
import ki.input.data.Data;
import ki.model.IKIModel;
import ki.model.manager.ModelManager;
import ki.solvers.IKISolver;
import ki.solvers.impl.CSPSolver;
import ki.solvers.impl.PredictionSolver;
import ki.solvers.impl.TreeSolver;

public class Decider implements ICache, Observer {

	private static Decider instance;

	private ModelManager modelManager;

	private Decider() {
		modelManager = ModelManager.getInstance();
	}

	public static Decider getInstance() {
		if (instance == null) {
			instance = new Decider();
		}
		return instance;
	}

	@Override
	public void update(Observable o, Object arg) {
		solve((List<Data>) arg);
	}

	private void solve(List<Data> dataList) {
		if (dataList instanceof List) {
			// Model für KI Solver erstellen
			IKIModel model = modelManager.createModel(dataList);
			// Logik für Entscheidung welchen Solver die Aufgabe bekommt

			IKISolver csp_solver = new CSPSolver();
			IKISolver prediction_solver = new PredictionSolver();
			IKISolver tree_solver = new TreeSolver();

			csp_solver.solve(model);
			prediction_solver.solve(model);
			tree_solver.solve(model);
		}
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	public ModelManager getModelManager() {
		return modelManager;
	}

	public void setModelManager(ModelManager modelManager) {
		this.modelManager = modelManager;
	}
}