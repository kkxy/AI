package preAlgo.knn;

public class NodeValue {
	String value;
	Integer count;
	public NodeValue(String value,Integer count) {
		this.value=value;
		this.count=count;
		
	}
	public String getvalue() {
		return value;
	}
	public int getcount() {
		return count;
	}
	
	public void setvalue(String value) {
		this.value=value;
	}
	public void setcount(int count) {
		this.count=count;
	}
	
}
