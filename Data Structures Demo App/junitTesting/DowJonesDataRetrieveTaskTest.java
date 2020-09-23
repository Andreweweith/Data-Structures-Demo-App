package junitTesting;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import javafx.collections.ObservableList;
import model.DowJonesData;
import model.DowJonesDataRetrieveTask;

/**
 * JUnit Test Case to determine if the data read from the 
 * DJIA.txt file is accurately being read and processed.
 * <p>
 * *Note* This test case will not be true as long as the task is 
 * updating its progress. It is intended to be run in the Application
 * thread, and will give an error unless that is the case.
 * 
 * @author Andrew
 */
public class DowJonesDataRetrieveTaskTest {

	/**
	 * The test method will assert whether the call method from the 
	 * DowJonesDataTask is returning the exact expected list of DowJonesData.
	 * <p>
	 * In order for this case to succeed, the read values much match the 
	 * predetermined date and value pair. This date (2006-12-26) was chosen
	 * specifically because the value it is paired with is the first instance
	 * FOLLOWING an empty value. The empty value is simply a "." instead of "#####.##".
	 * Any values on this day are to be skipped, and not added to the list.
	 * This test case will determine that the date and value pair being read is 
	 * formatted as it is exactly expected to be, and in order to do so will
	 * confirm that the day was skipped (2006-12-25) and not placed in the 
	 * list index 8 (dataList.get(8) - should be December 26th, not 25th)
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		DowJonesDataRetrieveTask test = new DowJonesDataRetrieveTask();
		ObservableList<DowJonesData> dataList = test.call();
		
		// ** MONTH is added as 11 because GregorianCalendar reads months as (0-11)
		GregorianCalendar calendar = new GregorianCalendar(2006, 11, 26);
		Date testDate = calendar.getTime();
		
		DowJonesData testData = new DowJonesData(testDate, 12407.63);
		
		assertEquals(testData.getDate(), dataList.get(8).getDate());
		assertTrue(testData.getValue() == dataList.get(8).getValue());
	}

}
