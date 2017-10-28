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

	public Vector<InferenceGraphNode> getFathers() {
		return fathers;
	}

	public void setFathers(Vector<InferenceGraphNode> fathers) {
		this.fathers = fathers;
	}

	public InferenceGraphNode getChild() {
		return child;
	}

	public void setChild(InferenceGraphNode child) {
		this.child = child;
	}

	public String[][] getDomains() {
		return domains;
	}

	public void setDomains(String[][] domains) {
		this.domains = domains;
	}

	public double[] getProb() {
		return prob;
	}

	public void setProb(double[] prob) {
		this.prob = prob;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getFatherSize() {
		return fathers.size();
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
		
		for (int i = 0; i < domains.length; i++) {
			for (int j = 0; j < domains[i].length; j++) {
				System.out.print(domains[i][j] + "\t");
			}
			System.out.println();
		}
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
	
	public void show() {
		System.out.print(child.get_name() + "\t");
		for (InferenceGraphNode ig : fathers) {
			System.out.print(ig.get_name() + "\t");
		}
		System.out.println();
		for (int i = 0; i < domains.length; i++) {
			for (int j = 0; j < domains[i].length; j++) {
				System.out.print(domains[i][j] + "\t");
			}
			System.out.println(prob[i]);
		}
	}
}
