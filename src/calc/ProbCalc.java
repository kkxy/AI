package calc;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import InferenceGraphs.InferenceGraphNode;
import data.FileData;
import data.CPT;

public class ProbCalc {
	
	private Vector<CPT> pflist = new Vector<>();
	private Map<String, Integer> map = new HashMap<>();
	private Map<String, Integer> childmap = new HashMap<>();
	
	public ProbCalc() {
		pflist.clear();
		map.clear();
		childmap.clear();
	}
	
	public ProbCalc(FileData fd, Vector nodelist) {
		pflist.clear();
		map.clear();
		childmap.clear();
		
		initMap(nodelist);
		initPFTable(fd, nodelist);
	}
	
	public void reCalc(FileData fd, Vector nodelist) {
		initMap(nodelist);
		initPFTable(fd, nodelist);
	}
	
	private void initMap(Vector nodelist) {
		// 初始化Map
		for (int i = 0; i < nodelist.size(); i++) {
			InferenceGraphNode node = (InferenceGraphNode)nodelist.get(i);
			int length = node.get_Prob().get_variables().length;
			for (int j = 0; j < length; j++) {
				String name = node.get_Prob().get_variable(j).get_name();
				int ind = node.get_Prob().get_variable(j).get_index();
				map.put(name, ind);
			}
		}
	}
	
	private void initPFTable(FileData fd, Vector nodelist) {
		// 初始化PF表
		for (int i = 0; i < nodelist.size(); i++) {
			InferenceGraphNode node = (InferenceGraphNode)nodelist.get(i);
			CPT pf = new CPT(node);
			pf.createProbTable(fd, map);
			childmap.put(node.get_name(), i);
			pflist.add(pf);
		}
	}
	
	public double getExpectation(Vector<String> values) {
		double res = 1;
		for (String key : map.keySet()) {
			CPT pf = pflist.get(childmap.get(key));
			String[] tempValue = new String[pf.getFatherSize() + 1];
			Vector<InferenceGraphNode> fathers = pf.getFathers();
			int pos = 0;
			for (InferenceGraphNode ig : fathers) {
				int index = map.get(ig.get_name());
				tempValue[pos] = values.get(index);
				pos = pos + 1;
			}
			tempValue[pos] = values.get(map.get(key));
			res = res * pf.getProbility(tempValue);
		}
		return res;
	}
}
