package ki.input.manager;

import java.util.ArrayList;
import java.util.List;

import ki.cache.ICache;
import ki.input.data.Data;

public class InputDataManager implements ICache {

	private static InputDataManager instance;

	private List<Data> dataList;
	private List<Data> cache;

	private InputDataManager() {
		setDataList(new ArrayList<>());
		cache = new ArrayList<>();
	}

	public static InputDataManager getInstance() {
		if (instance == null) {
			instance = new InputDataManager();
		}
		return instance;
	}

	@Override
	public void add(Data data) {
		cache.add(data);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	public List<Data> getDataList() {
		return dataList;
	}

	public void setDataList(List<Data> dataList) {
		this.dataList = dataList;
	}
}