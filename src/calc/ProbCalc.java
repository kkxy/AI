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
	
	public ProbCalc() {
	}
	
	public ProbCalc(FileData fd, Vector nodelist) {
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
			pflist.add(pf);
		}
	}
	
	public double getExpectation(Vector<String> values) {
		double res = 0;
		return res;
	}
}
