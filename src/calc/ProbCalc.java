package calc;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import InferenceGraphs.InferenceGraphNode;
import data.FileData;
import exception.DivZeroException;
import util.TestUtil;
import data.CPT;

public class ProbCalc {
	
	private Vector<CPT> cptlist = new Vector<>();
	private Map<String, Integer> map = new HashMap<>();
	
	public ProbCalc() {
		cptlist.clear();
		map.clear();
	}
	
	public void init(FileData fd, Vector nodelist) {
		cptlist.clear();
		map.clear();
		
		initMap(nodelist);
		initCPT(fd, nodelist);
	}
	
	public void reCalc(FileData fd, Vector nodelist) {
		for (int i = 0; i < cptlist.size(); i++)
			cptlist.get(i).reCalcCPT(fd, map);
	}
	
	/**
	 * init map
	 * @param nodelist
	 */
	private void initMap(Vector nodelist) {
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
	
	/**
	 * initual the CPT 
	 * @param fd
	 * @param nodelist
	 */
	private void initCPT(FileData fd, Vector nodelist) {
		System.out.println("init PF Table");
		for (int i = 0; i < nodelist.size(); i++) {
			InferenceGraphNode node = (InferenceGraphNode)nodelist.get(i);
			CPT cpt = new CPT(node);
			cpt.createCPT(fd, map);
			cptlist.add(cpt);
		}
	}
	
	/**
	 * put the 37 values, and with chain theorem to calculate the probability
	 * @param values
	 * @return
	 */
	public double getProbability(Vector<String> values) {
		double res = 1;
		for (CPT cpt : cptlist) {
			String[] tempValue = new String[cpt.getFatherSize() + 1];
			Vector<InferenceGraphNode> fathers = cpt.getFathers();
			int pos = 0;
			for (InferenceGraphNode ig : fathers) {
				int index = map.get(ig.get_name());
				tempValue[pos] = values.get(index);
				pos = pos + 1;
			}
			tempValue[pos] = values.get(map.get(cpt.getChild().get_name()));
			res = res * cpt.getProbility(tempValue);
		}
		return res;
	}
	
	public void showProbility() {
		for (CPT pf : cptlist) {
			pf.show();
			System.out.println();
		}
	}
}
