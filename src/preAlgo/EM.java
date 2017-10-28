package preAlgo;

import java.util.Vector;

import calc.ProbCalc;
import data.FileData;

public class EM extends BaseAlgo {
	
	private final int MAXITER = 10000;
	private final double EPS = 0.001;
	
	private ProbCalc prob;
	private int iteration;
	
	public EM() {
		// TODO Auto-generated constructor stub
		iteration = 0;
	}

	private boolean isOptimized() {
		if (iteration >= MAXITER)
			return false;
		return true;
	}
	
	@Override
	public void checkData(FileData fd, Vector nodelist) {
		// TODO Auto-generated method stub
		prob = new ProbCalc(fd, nodelist);
		while (!isOptimized()) {
			FileData newData = fd.fill(prob);
			
		}
	}
	
}
