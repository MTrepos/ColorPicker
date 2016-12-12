package colorpicker;

import java.awt.AWTException;
import java.awt.Robot;

public class DataStore{

	private static DataStore ds;
	private Robot r = new Robot();
	static{
		try {
			DataStore.ds = new DataStore();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	}
	
	private DataStore() throws AWTException{
	}
	
	public static DataStore getInstance(){
		return ds;
	}
	
	public Robot getRobot(){
		return this.r;
	}
}
