package data;

import java.util.Vector;

public class Row {

	private int line;
	private Vector<String> data = new Vector<>();
	private double weight;
	
	public Row() {
		// TODO Auto-generated constructor stub
		data.clear();
		weight = 1;
	}
	
	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public Vector<String> getData() {
		return data;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setData(Vector<String> data) {
		this.data = data;
	}

	public void setData(int l, String[] values) {
		line = l;
		for (String v : values)
			data.add(v);
		weight = 1;
	}
	
	public boolean match(int loc, String value) {
		return data.get(loc).equals(value);
	}
	
	public void addData(String d) {
		data.add(d);
	}
	
	public int getSize() {
		return data.size();
	}
	
	public String[] toStringArray() {
		String[] res = new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			res[i] = data.get(i);
		}
		return res;
	}

}
