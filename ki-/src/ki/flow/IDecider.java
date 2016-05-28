package ki.flow;

import java.util.List;
import java.util.Observer;

import ki.cache.ICache;
import ki.input.data.IData;
import ki.singleton.ISingleton;

public interface IDecider extends ICache, ISingleton, Observer {
	void decide(List<IData> currentData);
}
