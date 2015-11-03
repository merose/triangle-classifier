package me.markrose.example.triangle;

import static org.testng.Assert.assertEquals;
import me.markrose.example.triangle.TriangleClassifier.Type;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Implements tests for the {@link TriangleClassifier} class.
 */
public class TriangleClassifierTest {

	/**
	 * Tests whether the classification of a triangle, given the
	 * three side lengths, matches the expected classification.
	 * 
	 * @param side1Str the first side length, as a string
	 * @param side2Str the second side length, as a string
	 * @param side3Str the third side length, as a string
	 * @param expectedClassification the expected classification
	 */
	@Test(dataProvider="TriangleTests")
	public void testClassification(
			String side1Str,
			String side2Str,
			String side3Str,
			Type expectedClassification
	) {
		TriangleClassifier classifier = new TriangleClassifier();
		
		assertEquals(classifier.classify(side1Str, side2Str, side3Str), expectedClassification);
	}
	
	/**
	 * Gets the test cases to send to the classifier. Each test case
	 * is an array of four values: the three side lengths, as strings,
	 * and the expected classification, as a constant from {@link TriangleClassifier.Type}.
	 * 
	 * @return an array of test cases
	 */
	@DataProvider(name="TriangleTests")
	private Object[][] getTriangleTests() {
		return new Object[][] {
				// side1, side2, side3, expected triangle type
				{ "3", "4", "5", Type.SCALENE },
		};
	}

}
