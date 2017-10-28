package main;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import InferenceGraphs.InferenceGraph;
import InferenceGraphs.InferenceGraphNode;
import InterchangeFormat.IFException;
import calc.ProbCalc;
import data.FileData;
import data.CPT;
import preAlgo.BaseAlgo;

public class Main {
	public static FileData fd;
	public static ProbCalc bsp;
	public static InferenceGraph G;
	public static BaseAlgo algo;
	public static Vector nodelist;
	public static final String[] algorithm = {"KNN", "EM"};
	
	/**
	 * 初始化
	 */
	public static void init() {
		fd = new FileData();
		bsp = new ProbCalc();
		System.out.println("Init Succeed");
	}
	
	/**
	 * 输入
	 */
	public static void input() {
		try {
			G = new InferenceGraph("data/alarm.bif");
			nodelist = G.get_nodes();
			fd.readRowDatas("data/records.dat");
			System.out.println("Input Succeed");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IFException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 依据传入的算法来选择不同的预处理
	 * @param algorithm
	 */
	public static void pretreatment(FileData fd, String algorithm) {
		try {
			algo = (BaseAlgo)Class.forName("preAlgo." + algorithm).newInstance();
			algo.checkData(fd, nodelist);
			System.out.println("Pretreat Succeed");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	public static void output() {
		ProbCalc bp = new ProbCalc(fd, nodelist);
		bp.showProbility();
		// write into file
//			Vector<Double> plist = bsp.getProb();
//			double[] values = new double[plist.size()];
//			for (int k = 0; k < plist.size(); k++)
//				values[k] = new BigDecimal(plist.get(k)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
//			node.get_Prob().set_values(values);
//			node.get_Prob().print();
		System.out.println("Output Succeed");
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < algorithm.length; i++) {
			System.out.println("Now Using Algorithm: " + algorithm[i]);
			init();
			input();
			pretreatment(fd, algorithm[i]);
			output();
		}
	}
}
