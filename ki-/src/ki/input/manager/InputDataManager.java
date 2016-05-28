package ki.input.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import ki.flow.Decider;
import ki.input.data.Data;

public class InputDataManager extends Observable implements IInputDataManager {

	private static InputDataManager instance;

	private List<Data> dataList;
	private Map<Long, List<Data>> cache;

	private InputDataManager() {
		setDataList(new ArrayList<>());
		cache = new HashMap<>();
		this.addObserver(Decider.getInstance());
	}

	public static InputDataManager getInstance() {
		if (instance == null) {
			instance = new InputDataManager();
		}
		return instance;
	}

	@Override
	public void add() {
		if (check()) {
			if (countObservers() > 0) {
				cache.put(new Date().getTime(), dataList);
				notifyObservers(dataList);
			}
		} else {
			stop();
		}
	}

	@Override
	public void clear() {
		cache.clear();
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

	public List<Data> getDataList() {
		return dataList;
	}

	public void setDataList(List<Data> dataList) {
		this.dataList = dataList;
	}

	public Map<Long, List<Data>> getCache() {
		return cache;
	}

	public void setCache(Map<Long, List<Data>> cache) {
		this.cache = cache;
	}
}