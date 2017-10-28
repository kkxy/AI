package preAlgo;

import java.util.Vector;

import calc.ProbCalc;
import data.FileData;
import data.Row;

public class EM extends BaseAlgo {
	
	private final int MAXITER = 10000;
	private final double EPS = 0.001;
	
	private ProbCalc prob;
	private int iteration;
	
	public EM() {
		// TODO Auto-generated constructor stub
		iteration = 0;
	}

	private boolean isOptimized(FileData fd) {
		Vector<Row> rowdata = fd.getRowDatas();
		double exception[] = new double[100];
		exception[0] = -5;
		exception[1] = 0.0;
		double threhold = 0.001;
		int liter = 1;
		while(exception[liter] - exception[liter - 1] > threhold) {
			for (int i = 0; i < rowdata.size(); i++) {
				Row r = rowdata.get(i);
				double w = r.getWeight();
				double proba = prob.getExpectation(r.getDataset());
				exception[liter] += w * proba;
			}
			liter += 1;
		}
		if (iteration >= MAXITER)
			return false;
		return true;
	}
	
	@Override
	public void checkData(FileData fd, Vector nodelist) {
		// TODO Auto-generated method stub
		prob = new ProbCalc(fd, nodelist);
		while (!isOptimized(fd)) {
			FileData newData = fd.fill(nodelist, prob);
			
		}
	}
	
}
