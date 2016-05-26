package ki.model.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ki.cache.ICache;
import ki.input.data.Data;
import ki.model.IKIModel;
import ki.model.impl.KIModel;
import ki.plausibility.IPlausibility;

public class ModelManager implements ICache, IPlausibility {

	private static ModelManager instance;

	private Map<String, KIModel> modelMap;

	private KIModel currentModel;

	private ModelManager() {
		setModelMap(new HashMap<>());
	}

	public static ModelManager getInstance() {
		if (instance == null) {
			instance = new ModelManager();
		}
		return instance;
	}

	public IKIModel createModel(List<Data> dataList) {
		// TODO KI erkennt z.B. für Streckenführung anhand der Neuronalen Netzen
		// ob Roboter sich in Kurve befindet

		currentModel = new KIModel();
		if (check()) {
			modelMap.put(currentModel.getName(), currentModel);
			return currentModel;
		}
		return null;
	}

	@Override
	public boolean check() {
		// Überprüft die Model ob diese Valide ist.
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

	public Map<String, KIModel> getModelMap() {
		return modelMap;
	}

	public void setModelMap(Map<String, KIModel> modelMap) {
		this.modelMap = modelMap;
	}

	public KIModel getCurrentModel() {
		return currentModel;
	}

	public void setCurrentModel(KIModel currentModel) {
		this.currentModel = currentModel;
	}
}