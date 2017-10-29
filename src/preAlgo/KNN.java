package preAlgo;
import BayesianNetworks.*;
import InferenceGraphs.*;
import InterchangeFormat.*;
import data.FileData;
import data.Row;
import data.knn.KNNnode;
import data.knn.NodeValue;

import java.io.*;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class KNN implements BaseAlgo {
	private final int K = 9;
	public Integer distance(String[] s1,String[] s2) {
		int distence=0;
		int colnum=s1.length;
		for(int i=0;i<colnum;i++) {
			if(s1[i].equals(s2[i])) {distence+=0;}
			else {distence+=1;}
		}
		return distence;
	}
	
	@SuppressWarnings("unchecked")
	public List<KNNnode> knn(String[] s1,String[][]s2,int j,int m) {
		List<KNNnode> rank=new ArrayList<KNNnode>();
		List<KNNnode> result=new ArrayList<KNNnode>();
		Integer dis;
		
		for(int i=0;i<s2.length;i++) {
			if(i==m) {continue;}
			
			else if (s1[j].equals(s2[i][j])) {dis=38;}
			else dis=distance(s1,s2[i]);
			rank.add(new KNNnode(m,i,dis));
			
		}
		//ComparatorKNNnode comparator=new ComparatorKNNnode();
		rank.sort(new Comparator<KNNnode>() {
			public int compare(KNNnode a, KNNnode b) {
				if (a.getdistance() == b.getdistance())
					return 0;
				return a.getdistance()>b.getdistance()?1:-1;
			}
		});
		//Collections.sort(rank,comparator);
		for(int n=0;n<K;n++) {
			result.add(rank.get(n));
	//		System.out.print(result.get(n).getid()+"  ");
	//		System.out.print(result.get(n).getindex()+"  ");
	//		System.out.println(result.get(n).getdistance());
		}
		
		return result;
	} 

	public String[][] useknn(String[][] s) throws IOException, IFException{
		int rownum=s.length;
		int colnum=0;
		if(rownum>0) {colnum=s[0].length;}
		List<KNNnode> knode=new ArrayList<KNNnode>();
		String str;
		String[][] v=new String[rownum][colnum];
		for(int i=0;i<colnum;i++) {
			for(int j=0;j<rownum;j++) {
				if(s[j][i].equals("?")) {
					knode=knn(s[j],s,i,j);
					str=maxcount(knode,s,i);
					v[j][i]=str;
				}
				else {
					v[j][i]=s[j][i];
				}
			}
			
		}
		return v;
			
	}
	@SuppressWarnings("unchecked")
	public String maxcount(List<KNNnode> knode,String[][] s,int i) throws IOException, IFException {
		String str;
		int m=0;
		int len=knode.size();
		int type;
		int c=-1;
		List<NodeValue> valuescount=new ArrayList<NodeValue>();
		InferenceGraph G = new InferenceGraph("./data/alarm.bif");
		Vector nodes = G.get_nodes();
		InferenceGraphNode n = ((InferenceGraphNode)nodes.elementAt(i));
		type=n.get_number_values();
		int[] count = new int[type];
		String[] values=new String[type];
		for(int k=0;k<type;k++) {count[k]=0;}
		values=n.get_values();
		for(int a=0;a<type;a++){
		    for(int j=0;j<len;j++)
		      {m=knode.get(j).getindex();
		       if(values[a].equals(s[m][i])) {
		    //	   System.out.print(values[a]+" "+s[m][i]+" ");
		    	   count[a]=count[a]+1;
		   // 	   System.out.println(count[a]);
		    	   }
				}}
		for(int b=0;b<type;b++) {
			valuescount.add(new NodeValue(values[b],count[b]));
		}
	//	ComparatorNodeValue comparator=new ComparatorNodeValue();
		valuescount.sort(new Comparator<NodeValue>() {
			public int compare(NodeValue a, NodeValue b) {
				if (a.getcount() == b.getcount())
					return 0;
				return a.getcount()>b.getcount()?-1:1;
			}
		});
		
	//	System.out.println(i);
	//	Collections.sort(valuescount,comparator);
	//	for(int cc=0;cc<type;cc++) {
	//		
	//	System.out.print(valuescount.get(cc).getvalue()+" ");
	//	System.out.print(valuescount.get(cc).getcount()+" ");
	//	System.out.println();
	//	}
		str=valuescount.get(0).getvalue();
		return str;
		
	}
	
	@Override
	public void checkData(FileData fd, Vector nodelist) {
		String[][] filedatas = fd.toStringArray();
		try {
			fd.resetData(useknn(filedatas));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IFException e) {
			e.printStackTrace();
		}
	}

}
