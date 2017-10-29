package preAlgo;

import java.util.Vector;

import calc.ProbCalc;
import data.FileData;
import data.Row;

public class EM implements BaseAlgo {
	
	private final int MAXITER = 10000;
	private final double EPS = 0.00001;
	
	private ProbCalc prob;
	private int iteration;
	private double exception;
	
	public EM() {
		// TODO Auto-generated constructor stub
		iteration = 0;
		exception = -5.0;
	}

	private boolean isOptimized(FileData fd) {
		iteration += 1;
		double nowException = 0.0;
		Vector<Row> rowdata = fd.getRowDatas();
		for (int i = 0; i < rowdata.size(); i++) {
			Row r = rowdata.get(i);
			double w = r.getWeight();
			double proba = prob.getExpectation(r.getDataset());
			nowException += w * proba;
		}
		if (Math.abs(nowException - exception) > EPS) {
			if (iteration >= MAXITER) {
				return true;
			}
			exception = nowException;
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
			System.out.println("\nIteration: " + iteration);
			fd = oldData.fill(nodelist, prob);
			prob.reCalc(fd, nodelist);
		}
	}
	
}
