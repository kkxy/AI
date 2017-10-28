package preAlgo;

import java.util.Scanner;
import java.util.Vector;

import calc.ProbCalc;
import data.FileData;
import data.Row;
import util.TestUtil;

public class EM extends BaseAlgo {
	
	private final int MAXITER = 10000;
	private final double EPS = 0.001;
	private double exception[] = new double[100];
	
	private ProbCalc prob;
	private int iteration;
	
	public EM() {
		// TODO Auto-generated constructor stub
		iteration = 0;
		this.exception[0] = -5.0;
	}

	private boolean isOptimized(FileData fd) {
		iteration += 1;
		exception[iteration] = 0.0;
		Vector<Row> rowdata = fd.getRowDatas();
		if (Math.abs(exception[iteration] - exception[iteration - 1]) > EPS) {
			for (int i = 0; i < rowdata.size(); i++) {
				Row r = rowdata.get(i);
				double w = r.getWeight();
				double proba = prob.getExpectation(r.getDataset());
				exception[iteration] += w * proba;
			}
			if (iteration >= MAXITER) {
				return true;
			}
			return false;
		}	
		return true;
	}
	
	@Override
	public void checkData(FileData fd, Vector nodelist) {
		// TODO Auto-generated method stub
		// set initial values
		prob = new ProbCalc(fd, nodelist);
		FileData oldData = fd.getCopy();
		fd = fd.fill(nodelist, prob);
		// start iteration
		while (!isOptimized(fd)) {
			System.out.println("Iteration: " + iteration);
			fd = oldData.fill(nodelist, prob);
			prob.reCalc(fd, nodelist);
		}
	}
	
}
