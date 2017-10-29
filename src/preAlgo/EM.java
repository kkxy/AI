package preAlgo;

import java.math.BigDecimal;
import java.util.Vector;

import calc.ProbCalc;
import data.FileData;
import data.Row;
import exception.DivZeroException;
import util.TestUtil;

public class EM implements BaseAlgo {
	
	private final int MAXITER = 10000;
	private final double EPS = 0.00001;
	
	private int iteration;
	private double exception;
	
	public EM() {
		// TODO Auto-generated constructor stub
		iteration = 0;
		exception = -5.0;
	}
	
	/**
	 * judge if the nowexception - exception > EPS, whether the literation continue?
	 * @param fd
	 * @return
	 */
	private boolean isOptimized(FileData fd, ProbCalc prob) {
		iteration += 1;
		double nowException = 0.0;
		Vector<Row> rowdata = fd.getRowDatas();
		for (int i = 0; i < rowdata.size(); i++) {
			Row r = rowdata.get(i);
			double w = r.getWeight();
			double proba = prob.getProbability(r.getDataset());
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
	/**
	 * core part of a algorithm
	 */
	public void checkData(FileData fd, Vector nodelist, ProbCalc prob) {
		// TODO Auto-generated method stub
		try {
			// set initial values
			prob.init(fd, nodelist);
			FileData oldData = fd.getCopy();
			fd = fd.fill(nodelist, prob);
			// start iteration
			while (!isOptimized(fd, prob)) {
//				System.out.println("\nIteration: " + iteration);
				fd = oldData.fill(nodelist, prob);
				prob.reCalc(fd, nodelist);
			}
			System.out.println("Total Iteration: " + iteration);
		} catch (DivZeroException e) {
			e.printStackTrace();
		}
	}
	
}
