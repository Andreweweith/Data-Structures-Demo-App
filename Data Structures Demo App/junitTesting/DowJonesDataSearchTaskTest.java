package junitTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import model.BST;
import model.DowJonesData;
import model.DowJonesDataSearchTask;

/**
 * Test case to ensure that the binary search tree is retrieving the
 * data properly.
 * 
 * @author Andrew
 */
public class DowJonesDataSearchTaskTest {

	/** Initialize dummy data, arrange in ordered manner. Then shuffe, and
	 * place in a Binary Search Tree. Assert that the assumed date value
	 * matches the retrieved date value. */
	@Test
	public void test() throws Exception {
		ArrayList<DowJonesData> dataList = new ArrayList<>();
		
		GregorianCalendar calendar1 = new GregorianCalendar(2016, 9, 26);
		GregorianCalendar calendar2 = new GregorianCalendar(2006, 11, 26);
		GregorianCalendar calendar3 = new GregorianCalendar(2011, 2, 4);
		GregorianCalendar calendar4 = new GregorianCalendar(2009, 10, 29);
		GregorianCalendar calendar5 = new GregorianCalendar(2013, 4, 17);
		
		Date date1 = calendar1.getTime();
		Date date2 = calendar2.getTime();
		Date date3 = calendar3.getTime();
		Date date4 = calendar4.getTime();
		Date date5 = calendar5.getTime();
		
		DowJonesData data1 = new DowJonesData(date1, 18094.83);
		DowJonesData data2 = new DowJonesData(date2, 12407.63);
		DowJonesData data3 = new DowJonesData(date3, 12092.15);
		DowJonesData data4 = new DowJonesData(date4, 9962.58);
		DowJonesData data5 = new DowJonesData(date5, 14618.59);
		
		// Ordered
		dataList.add(data2);
		dataList.add(data4);
		dataList.add(data3);
		dataList.add(data5);
		dataList.add(data1);
		
		// Shuffle
		Collections.shuffle(dataList);
		
		BST binarySearchTree = new BST();
		
		for(DowJonesData items : dataList) {
			binarySearchTree.put(items.getDate(), items.getValue());
		}
		
		// Initialize the task, and set testResult as the value from call
		DowJonesDataSearchTask test = new DowJonesDataSearchTask(binarySearchTree, data2.getDate());
		Double testResult = test.call();
		
		assertTrue(testResult == 12407.63);
	}

}
