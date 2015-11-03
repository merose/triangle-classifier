package me.markrose.example.triangle;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.Frame;
import java.util.regex.Pattern;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.testng.testcase.AssertJSwingTestngTestCase;
import org.testng.annotations.Test;

/**
 * Implements tests for the triangle classifier view.
 */
public class TriangleViewTest extends AssertJSwingTestngTestCase {

	private FrameFixture frame;

	/**
	 * Tests that the application starts and the main view is visible
	 * on the screen. Also test that the classification summary indicates
	 * that no classification has been made.
	 */
	@Test
	public void testStartApplication() {
		frame.requireVisible();
		JTextComponentFixture summary = frame.textBox(TriangleView.SUMMARY_NAME);
		summary.requireText(Pattern.compile(".*not yet.*", Pattern.CASE_INSENSITIVE));
	}

	/**
	 * Tests that entering side lengths and pressing the classify button
	 * results in matching classification messages.
	 */
	@Test
	public void testClassifyTriangle() {
		JTextComponentFixture side1 = frame.textBox(TriangleView.SIDE1_NAME);
		JTextComponentFixture side2 = frame.textBox(TriangleView.SIDE2_NAME);
		JTextComponentFixture side3 = frame.textBox(TriangleView.SIDE3_NAME);
		
		side1.setText("3");
		side2.setText("4");
		side3.setText("5");
		
		JButtonFixture button = frame.button(JButtonMatcher.withText("Classify"));
		button.click();
		
		JTextComponentFixture summary = frame.textBox(TriangleView.SUMMARY_NAME);
		JTextComponentFixture details = frame.textBox(TriangleView.DETAILS_NAME);
		
		summary.requireText(Pattern.compile(".*scalene.*", Pattern.CASE_INSENSITIVE));
		details.requireText(Pattern.compile(".*different.*", Pattern.CASE_INSENSITIVE));
	}
	
	@Override
	protected void onSetUp() {
		application(TriangleView.class).start();
		frame = findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
			protected boolean isMatching(Frame frame) {
				return "Triangle Classifier".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(robot());
	}

}
