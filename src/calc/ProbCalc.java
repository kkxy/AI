package calc;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import InferenceGraphs.InferenceGraphNode;
import data.FileData;
import util.TestUtil;
import data.CPT;

public class ProbCalc {
	
	private Vector<CPT> pflist = new Vector<>();
	private Map<String, Integer> map = new HashMap<>();
	
	public ProbCalc() {
		pflist.clear();
		map.clear();
	}
	
	public ProbCalc(FileData fd, Vector nodelist) {
		pflist.clear();
		map.clear();
		
		initMap(nodelist);
		initPFTable(fd, nodelist);
	}
	
	public void reCalc(FileData fd, Vector nodelist) {
		pflist.clear();
		map.clear();
		
		initMap(nodelist);
		initPFTable(fd, nodelist);
	}
	
	private void initMap(Vector nodelist) {
		// 初始化Map
		System.out.println("init Map");
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
		System.out.println("init PF Table");
		for (int i = 0; i < nodelist.size(); i++) {
			InferenceGraphNode node = (InferenceGraphNode)nodelist.get(i);
			CPT pf = new CPT(node);
			pf.createProbTable(fd, map);
			pflist.add(pf);
		}
	}
	
	public double getExpectation(Vector<String> values) {
		double res = 1;
		for (CPT pf : pflist) {
			String[] tempValue = new String[pf.getFatherSize() + 1];
			Vector<InferenceGraphNode> fathers = pf.getFathers();
			int pos = 0;
			for (InferenceGraphNode ig : fathers) {
				int index = map.get(ig.get_name());
				tempValue[pos] = values.get(index);
				pos = pos + 1;
			}
			tempValue[pos] = values.get(map.get(pf.getChild().get_name()));
			res = res * pf.getProbility(tempValue);
			if (res == 0) {
//				pf.show();
//				for (String s : values)
//					System.out.print(s + "%\t");
//				System.out.println();
//				TestUtil.testInput();
			}
		}
		return res;
	}
	
	public void showProbility() {
		for (CPT pf : pflist) {
			pf.show();
			System.out.println();
		}
	}
}
