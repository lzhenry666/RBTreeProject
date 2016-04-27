package temptest;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Random;
import java.util.Collections;

class TestRun implements Runnable {

    private int test_num;
    public boolean success = false;

    public TestRun(int test_num) {
        this.test_num = test_num;
    }

    public void run() {
        try {
        	switch (this.test_num) {
        		case 0: this.success = ExTester.emptyTreeTest();
        				break;
        		case 1: this.success = ExTester.insertAndSearchTest();
        				break;
        		case 2: this.success = ExTester.deleteAndSearchTest();
        				break;
        		case 3: this.success = ExTester.insertAndMinMaxTest();
        				break;
        		case 4: this.success = ExTester.deleteMinMaxTest();
        				break;
        		case 5: this.success = ExTester.insertAndSizeEmptyTest();
        				break;
        		case 6: this.success = ExTester.insertAndArraysTest();
        				break;
        		case 7: this.success = ExTester.deleteAndArraysTest();
        				break;
        		case 8: this.success = ExTester.doubleInsertTest();
        				break;
        		case 9: this.success = ExTester.doubleDeleteTest();
        				break;
        		case 10: this.success = ExTester.checkTreeIntegrity();
        				break;
        	}
        } catch (Exception e) {
        	System.out.println("Exception on Test " + test_num + " : " + e);
        	e.printStackTrace(System.out);
        }
    }
}


