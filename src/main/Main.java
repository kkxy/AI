package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

import InferenceGraphs.InferenceGraph;
import InterchangeFormat.IFException;
import calc.ProbCalc;
import data.FileData;
import preAlgo.BaseAlgo;
import util.TestUtil;

public class Main {
	public static FileData fd;
	public static InferenceGraph G;
	public static ProbCalc prob;
	public static BaseAlgo algo;
	public static Vector nodelist;
	public static final String[] algorithm = {"KNN", "EM"};
	
	/**
	 * 初始化
	 */
	public static long init() {
		long starttime = System.currentTimeMillis();
		fd = new FileData();
		prob = new ProbCalc();
		System.out.println("Init Succeed");
		long cost = System.currentTimeMillis() - starttime;
		System.out.println("Time Cost:" + (cost / 1000.0) + "s");
		return cost;
	}
	
	/**
	 * 输入
	 */
	public static long input() {
		long starttime = System.currentTimeMillis();
		try {
			G = new InferenceGraph("data/alarm.bif");
			nodelist = G.get_nodes();
			fd.readRowDatas("data/records.dat");
			System.out.println("Input Succeed");
			long cost = System.currentTimeMillis() - starttime;
			System.out.println("Time Cost:" + (cost / 1000.0) + "s");
			return cost;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IFException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 依据传入的算法来选择不同的预处理
	 * @param algorithm
	 */
	public static long pretreatment(FileData fd, String algorithm) {
		long starttime = System.currentTimeMillis();
		try {
			algo = (BaseAlgo)Class.forName("preAlgo." + algorithm).newInstance();
			algo.checkData(fd, nodelist, prob);
			System.out.println("Pretreat Succeed");
			long cost = System.currentTimeMillis() - starttime;
			System.out.println("Time Cost:" + (cost / 1000.0) + "s");
			return cost;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static long calculate() {
		long starttime = System.currentTimeMillis();
		prob.reCalc(fd, nodelist);
//		prob.showProbility();
		System.out.println("Calculate Succeed");
		long cost = System.currentTimeMillis() - starttime;
		System.out.println("Time Cost:" + (cost / 1000.0) + "s");
		try {
			G.print_bayes_net(new PrintStream(new FileOutputStream(new File("data/alarm_result.bif"))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return cost;
	}
	
	public static void main(String[] args) {
		for (int i = 1; i < algorithm.length; i++) {
			System.out.println("\nUsing Algorithm: " + algorithm[i]);
			long total = 0;
			total += init();
			total += input();
			total += pretreatment(fd, algorithm[i]);
			total += calculate();
			System.out.println("Total Time Cost: " + (total / 1000.0) + "s" );
//			TestUtil.compareInferenceGraph("./data/alarm.bif", "./data/resort_alarm.bif");
		}
	}
}
