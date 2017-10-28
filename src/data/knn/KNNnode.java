package data.knn;

public class KNNnode {
int id;
int index;
Integer distance;
public KNNnode(int id,int index,Integer distance) {
	this.id=id;
	this.index=index;
	this.distance=distance;	
}
public int getid() {
	return id;
}
public int getindex() {
	return index;
}
public Integer getdistance() {
	return distance;
}
public void setid(int id) {
	this.id=id;
}
public void setindex(int index) {
	this.index=index;
}
public void setdistance(Integer distance) {
	this.distance=distance;
}
}
