package data;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import InferenceGraphs.InferenceGraphNode;

public class CPT {
	
	private Vector<InferenceGraphNode> fathers = new Vector<>();
	private InferenceGraphNode child;
	private String[][] domains;
	private double[] prob;
	private int rows;

	public CPT() {
		// TODO Auto-generated constructor stub
	}

	public CPT(InferenceGraphNode c) {
		child = c;
		Vector parents = child.get_parents();
		for (Enumeration e = parents.elements(); e.hasMoreElements(); ) {
            fathers.add((InferenceGraphNode)(e.nextElement()));
        }
		rows = 0;
	}
	
	private void arrange(int c, FileData fd, double lastCount, Map<String, Integer> map) {
		if (c == fathers.size() + 1) {
			prob[rows] = (1.0 * fd.getDataSize()) / lastCount;
			rows = rows + 1;
			return ;
		}
		InferenceGraphNode nowNode = (c < fathers.size() ? fathers.get(c) : child);
		String[] nowDomain = nowNode.get_values();
		for (int i = 0; i < nowDomain.length; i++) {
			String value = nowDomain[i];
			domains[rows][c] = value;
			int index = map.get(nowNode.get_name());
			FileData newData = fd.filter(index, value);
			arrange(c + 1, newData, lastCount + newData.getDataSize(), map);
		}
		
	}
	
	public void createProbTable(FileData fd, Map<String, Integer> map) {
		rows = child.get_number_values();
		for (int i = 0; i < fathers.size(); i++) {
			rows = rows * fathers.get(i).get_number_values();
		}
		domains = new String[rows][fathers.size() + 1];
		prob = new double[rows];
		
		rows = 0;
		arrange(0, fd, 0.0, map);
	}
	
	public double getProbility(String[] values) {
		double res = 0.0;
		for (int i = 0; i < domains.length; i++) {
			boolean find = true;
			for (int j = 0; j < domains[i].length; j++) {
				if (!domains[i][j].equals(values[j])) {
					find = false;
					break;
				}
			}
			if (find) {
				res = prob[i];
				break;
			}
		}
		return res;
	}
	
}
