package util;

import java.util.Map;
import java.util.Vector;

import data.FileData;

public class TreeNode {
	
	private String values;
	private String nodeName;
	private int count;
	private Map<String, TreeNode> childs;

	public TreeNode() {
		// TODO Auto-generated constructor stub
		count = 0;
	}
	
	public TreeNode(String a, String b, int c) {
		nodeName = a;
		values = b;
		count = c;
	}
	
	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Map<String, TreeNode> getChilds() {
		return childs;
	}

	public void setChilds(Map<String, TreeNode> childs) {
		this.childs = childs;
	}

	public void initTree(int k, Vector<String> nodelist, Vector<Integer> index, Map<String, String[]> map, FileData fd) {
		if (k == nodelist.size())
			return ;
		String name = nodelist.get(k);
		String[] domains = map.get(name);
		for (String d : domains) {
			FileData newData = fd.filter(index.get(k), d);
//			TreeNode tn = new TreeNode(name, d, newData.getDataSize());
//			tn.initTree(k + 1, nodelist, index, map, newData);
		}
	}
	
	public TreeNode getNext(String value) {
		if (childs.containsKey(value))
			return childs.get(value);
		return null;
	}
}
