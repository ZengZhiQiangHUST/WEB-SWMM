package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;
public class HDTopo {
    private String riverID;//河段的ID
    private String downriverID;//河段对应的下游河段的ID
    private String[] upRiverID=new String[2];//河段所对应上游河段的ID
    public HDTopo( String riverID,String downriverID){
        this.riverID =riverID;
        this.downriverID = downriverID;
    }
    public HDTopo(String riverID, String[] upRiverID,String downriverID){
    	this.riverID =riverID;
        this.upRiverID =upRiverID;
        this.downriverID = downriverID;
    }
    public String getRiverID() {
		return riverID;
	}
	public void setRiverID(String riverID) {
		this.riverID = riverID;
	}
	public String getDownriverID() {
        return downriverID;
    }
	public String[] getUpRiverID() {
		return upRiverID;
	}
	public void setUpRiverID(String[] upRiverID) {
		this.upRiverID = upRiverID;
	}
}
