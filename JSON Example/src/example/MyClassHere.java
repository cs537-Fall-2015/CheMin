package example;


public class MyClassHere {
	private int myInteger;
	private boolean myBoolean;
	private double myDouble;
	private long myLong;
	private String myString;
	private double[] myList;
	
	
	MyClassHere(int someParameter){
		this.setMyInteger(someParameter);
		this.setMyBoolean(false);
		this.setMyDouble(452.2);
		this.setMyLong(10000l);
		this.setMyString("This is awesome!");
		double[] myList = {1.9, 2.9, 3.4, 3.5};
		this.setMyList(myList);
	}

	public int getMyInteger() {
		return myInteger;
	}

	public void setMyInteger(int myInteger) {
		this.myInteger = myInteger;
	}

	public boolean isMyBoolean() {
		return myBoolean;
	}

	public void setMyBoolean(boolean myBoolean) {
		this.myBoolean = myBoolean;
	}

	public double getMyDouble() {
		return myDouble;
	}

	public void setMyDouble(double myDouble) {
		this.myDouble = myDouble;
	}

	public long getMyLong() {
		return myLong;
	}

	public void setMyLong(long myLong) {
		this.myLong = myLong;
	}

	public String getMyString() {
		return myString;
	}

	public void setMyString(String myString) {
		this.myString = myString;
	}

	public double[] getMyList() {
		return myList;
	}

	public void setMyList(double[] myList) {
		this.myList = myList;
	}


}
