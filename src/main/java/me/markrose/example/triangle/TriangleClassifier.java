package me.markrose.example.triangle;

/**
 * Implements a classifier for triangles. The classifier can determine the
 * type of triangle - scalene, isosceles, or equilateral - given the lengths
 * of the three sides.
 */
public class TriangleClassifier {

	/**
	 * Defines the types of triangles that can be detected by the triangle
	 * classifier.
	 */
	public enum Type {

		/** The triangle is scalene. */
		SCALENE,
		
		/** The triangle is isosceles. */
		ISOSCELES,
		
		/** The triangle is equilateral. */
		EQUILATERAL,
		
		/** The side lengths cannot form a triangle. */
		NOT_A_TRIANGLE;
		
	}
	
	/**
	 * Determines the type of triangle given the lengths of the three sides.
	 * 
	 * @param side1Str the first side, as a string
	 * @param side2Str the second side, as a string
	 * @param side3Str the third side, as a string
	 * @return the type of triangle
	 */
	public Type classify(String side1Str, String side2Str, String side3Str) {
		int side1 = Integer.parseInt(side1Str);
		int side2 = Integer.parseInt(side2Str);
		int side3 = Integer.parseInt(side3Str);
		
		return classify(side1, side2, side3);
	}
	
	/**
	 * Determines the type of triangle given the lengths of the three sides.
	 * A triangle is equilateral if all sides are equal in length. A triangle
	 * is isosceles if two sides are equal in length. The sides cannot form
	 * a triangle if one side is longer or equal two the sum of the other
	 * two sides. Otherwise, a triangle has three different side lengths, and
	 * is scalene.
	 * 
	 * @param side1Str the length of the first side
	 * @param side2Str the length of the second side
	 * @param side3Str the length of the third side
	 * @return the type of triangle
	 */
	private Type classify(int side1, int side2, int side3) {
		if (side1 > side2+side3) {
			return Type.NOT_A_TRIANGLE;
		} else if (side1==side2 && side2==side3) {
			return Type.EQUILATERAL;
		} else if (side1==side2) {
			return Type.ISOSCELES;
		} else {
			return Type.SCALENE;
		}
	}
	
}
