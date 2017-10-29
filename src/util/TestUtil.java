package util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;

import InferenceGraphs.InferenceGraph;
import InferenceGraphs.InferenceGraphNode;
import InterchangeFormat.IFException;

public class TestUtil {
	
	public TestUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static void testInput() {
		Scanner sc = new Scanner(System.in);
		sc.next();
	}
	
	public static void compareInferenceGraph(String filename1, String filename2) {
		try {
			InferenceGraph g1 = new InferenceGraph(filename1);
			InferenceGraph g2 = new InferenceGraph(filename2);
			TestUtil.compareInferenceGraph(g1, g2);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IFException e) {
			e.printStackTrace();
		}
	}

	/**
	 * compare g1 with g2
	 * @param g1
	 * @param g2
	 */
	public static void compareInferenceGraph(InferenceGraph g1, InferenceGraph g2) {
		Vector nodelist1 = g1.get_nodes();
		Vector nodelist2 = g2.get_nodes();
		if (nodelist1.size() != nodelist2.size()) {
			System.out.println("Not The Same Inference Graph");
			return;
		}
		if (nodelist1.size() == 0) {
			System.out.println("Inference Graph is Empty");
			return;
		}
		int size = nodelist1.size();
		String[] namelist = new String[size];
		int[] total = new int[size];
		int[] matched = new int[size];
		for (int i = 0; i < nodelist1.size(); i++) {
			InferenceGraphNode node1 = (InferenceGraphNode)nodelist1.get(i);
			InferenceGraphNode node2 = null;
			for (int j = 0; j < nodelist2.size(); j++) {
				InferenceGraphNode n = (InferenceGraphNode)nodelist2.get(j);
				if (n.get_name().equals(node1.get_name())) {
					node2 = n;
					break;
				}
			}
			if (node2 == null) {
				System.out.println("Not Find " + node1.get_name() + " in Graph 2");
				return;
			}
			namelist[i] = node1.get_name();
			total[i] = node1.get_Prob().get_values().length;
			matched[i] = 0;
			double[] values1 = node1.get_Prob().get_values();
			double[] values2 = node2.get_Prob().get_values();
			for (int j = 0; j < values1.length && j < values2.length; j++) {
				double value1 = new BigDecimal(values1[j]).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				double value2 = new BigDecimal(values2[j]).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (value1 == value2) {
					matched[i] = matched[i] + 1;
				}
			}
		}
		int sum = 0, summatched = 0;
		System.out.println();
		for (int i = 0; i < size; i++) {
			System.out.println(namelist[i] + ": " + matched[i] + "/" + total[i]);
			sum = sum + total[i];
			summatched = summatched + matched[i];
		}
		System.out.println(summatched + "/" + sum + "\n");
	}
	
}
