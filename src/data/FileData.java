package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import InferenceGraphs.InferenceGraphNode;
import calc.ProbCalc;
import exception.DivZeroException;

public class FileData {
	// all data
	private Vector<Row> rowDatas = new Vector<>();
	private double dataSize;
	
	public FileData() {
		// TODO Auto-generated constructor stub
		rowDatas.clear();
		dataSize = 0;
	}
	
	public FileData(String[][] data) {
		rowDatas.clear();
		dataSize = 0;
		for (int i = 0; i < data.length; i++) {
			Row row = new Row();
			row.setLine(i);
			for (int j = 0; j < data[i].length; j++) {
				row.addData(data[i][j]);
			}
			dataSize = dataSize + row.getWeight();
			rowDatas.add(row);
		}
	}

	public void resetData(String[][] data) {
		rowDatas.clear();
		for (int i = 0; i < data.length; i++) {
			Row row = new Row();
			row.setLine(i);
			for (int j = 0; j < data[i].length; j++) {
				row.addData(data[i][j]);
			}
			rowDatas.add(row);
		}
	}
	
	public Vector<Row> getRowDatas() {
		return rowDatas;
	}

	public void setRowDatas(Vector<Row> rowDatas) {
		this.rowDatas = rowDatas;
	}
	
	public double getDataSize() {
		return dataSize;
	}

	public void setDataSize(double dataSize) {
		this.dataSize = dataSize;
	}

	/**
	 * parse the String
	 * @param s
	 * @return
	 */
	private String[] parseString(String s) {
		String[] strs = s.split(" ");
		String para[] = new String[strs.length];
		int co = 0;
		Pattern pattern = Pattern.compile("\"");
		
		for(String str : strs) {
			Matcher matcher = pattern.matcher(str);
			str = matcher.replaceAll("");
			para[co++] = str;
		}
		return para;
	}


	/**
	 * read data
	 * @param filename
	 */
	public void readRowDatas(String filename) {
		File file = new File(filename);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String lineString = null;
			int line = 1;
			dataSize = 0;
			while ((lineString= reader.readLine()) != null) {
				String[] t = parseString(lineString);
				Row row = new Row();
				row.setData(line, t);
				dataSize = dataSize + row.getWeight();
				rowDatas.add(row);	
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch(IOException e1) {
				}
			}
		}
	}
	
	public void addRowData(Row row) {
		rowDatas.add(row.getCopy());
		dataSize = dataSize + row.getWeight();
	}

	/**
	 * copy the data
	 * @return
	 */
	public FileData getCopy() {
		FileData res = new FileData();
		res.setDataSize(dataSize);
		Vector<Row> resRows = new Vector<>();
		for (Row r : rowDatas) {
			Row newRow = r.getCopy();
			resRows.add(newRow);
		}
		res.setRowDatas(resRows);
		return res;
	}
	
	/**
	 * filter the data in the condition of var-th with the value
	 * @param var
	 * @param value
	 * @return
	 */
	public FileData filter(int var, String value) {
		FileData fd = new FileData();
		for (Row row : rowDatas) {
			if (row.match(var, value)) {
				fd.addRowData(row);
			}
		}
		return fd;
	}
	
	/**
	 * get the all row data without the rows with "?"
	 * @param index
	 * @return
	 */
	public FileData cleanAbsent(int[] index) {
		FileData res = new FileData();
		for (Row row : rowDatas) {
			if (row.check(index)) {
				res.addRowData(row);
			}
		}
		return res;
	}
	
	/**
	 * expand the table
	 * @param nodelist
	 * @param prob
	 * @return
	 */
	public FileData fill(Vector<InferenceGraphNode> nodelist, ProbCalc prob) throws DivZeroException {
		FileData fd = new FileData();
		for (Row row : rowDatas) {
			int qPos = row.getIncomplete(); 
			// if the row data is incomplete
			if (qPos != -1) {
				String[] domains = nodelist.get(qPos).get_values();
				Vector<Row> rowlist = new Vector<>();
				double sumWeight = 0;
				for (int i = 0; i < domains.length; i++) {
					String value = domains[i];
					Row newRow = row.getCopy();
					newRow.replaceIncompleteData(value);
					double weight = prob.getProbability(newRow.getDataset()); // calculate the probability of insert row 
					sumWeight = sumWeight + weight;
					newRow.setWeight(weight); // set the temporary weight
					rowlist.add(newRow);
				}
				if (sumWeight == 0)
					throw new DivZeroException("Divide sumWeight zero");
				for (int i = 0; i < rowlist.size(); i++) {
					Row newRow = rowlist.get(i);
					newRow.setWeight(newRow.getWeight() / sumWeight); // set the real weight
					fd.addRowData(newRow);
				}
			}
			else {
				fd.addRowData(row);
			}
		}
		return fd;
	}
	
	public String[][] toStringArray() {
		if (rowDatas.isEmpty()) {
			return null;
		}
		int column = rowDatas.get(0).getSize();
		String[][] res = new String[rowDatas.size()][column];
		for (int i = 0; i < rowDatas.size(); i++) {
			Row row = rowDatas.get(i);
			String[] rowString = row.toStringArray();
			for (int j = 0; j < rowString.length; j++)
				res[i][j] = rowString[j];
		}
		return res;
	}

}
