package ki.flow;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ki.cache.ICache;
import ki.input.data.Data;
import ki.model.KIModel;
import ki.model.manager.ModelManager;

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
		if (arg instanceof List) {
			List<Data> currentData = (List<Data>) arg;
			KIModel model = modelManager.createModel(currentData);
			// Logik für Entscheidung welchen Solver die Aufgabe bekommt
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