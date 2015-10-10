package module1;

/*	
 * Created by: 	Jonathan Young
 * Date: 		May 14, 2015
 */

public class MyClassHere {
	private int myInteger;
	private boolean myBoolean;
	private double myDouble;
	private long myLong;
	private String myString;
	
	
	MyClassHere(int someParameter){
		this.setMyInteger(someParameter);
		this.setMyBoolean(false);
		this.setMyDouble(452.2);
		this.setMyLong(10000l);
		this.setMyString("This is awesome!!!");
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
	
	public void addOne() {
		this.myInteger += 1;
	}

	public void changeBoolean() {
		if(this.myBoolean == true){
			this.myBoolean = false;
		}
		else {
			this.myBoolean = true;
		}
	}
	
	public void changeDouble() {
		this.myDouble += 50.0;
	}
	
	public void changeLong() {
		this.myLong += 50l;
	}
	
	public void changeString() {
		this.myString = this.myString.concat("After Calculation.");
	}
	
	public void printObject() {
		System.out.println("===========================================");
		System.out.println("myInteger = " + this.myInteger);
		System.out.println("myBoolean = " + this.myBoolean);
		System.out.println("myDouble = " + this.myDouble);
		System.out.println("myLong = " + this.myLong);
		System.out.println("myString = " + this.myString);
		System.out.println("===========================================");
	}


}
