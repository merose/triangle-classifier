package me.markrose.example.triangle;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.awt.Frame;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JLabelFixture;
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
	@Test(groups="ui")
	public void testStartApplication() {
		frame.requireVisible();
		JTextComponentFixture summary = frame.textBox(TriangleView.SUMMARY_NAME);
		summary.requireText(Pattern.compile(".*not yet.*", Pattern.CASE_INSENSITIVE));
	}

	/**
	 * Tests that entering side lengths and pressing the classify button
	 * results in matching classification messages.
	 * 
	 * @throws Throwable if there is an error finding the components
	 */
	@Test(groups="ui")
	public void testClassifyTriangle() throws Throwable {
		JTextComponentFixture side1 = findTextComponentWithLabelText(".*side 1.*");
		JTextComponentFixture side2 = findTextComponentWithLabelText(".*side 2.*");
		JTextComponentFixture side3 = findTextComponentWithLabelText(".*side 3.*");
		
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
	
	/**
	 * Set up the frame fixture for testing.
	 */
	@Override
	protected void onSetUp() {
		application(TriangleView.class).start();
		frame = findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
			protected boolean isMatching(Frame frame) {
				return "Triangle Classifier".equals(frame.getTitle()) && frame.isShowing();
			}
		}).using(robot());
	}
	
	/**
	 * Finds a text component labeled by a {@link JLabel} with given text. The match
	 * against the label text is case-insensitive. The label must be associated with
	 * the component using {@link JLabel#setLabelFor(java.awt.Component)}.
	 * 
	 * @param pattern a pattern for matching against the label text
	 * @return the text component found
	 * @throws Throwable if no such component can be found
	 */
	private JTextComponentFixture findTextComponentWithLabelText(String pattern) throws Throwable {
		JLabelMatcher matcher = JLabelMatcher.withText(Pattern.compile(pattern, Pattern.CASE_INSENSITIVE));
		final JLabelFixture label = frame.label(matcher);

		// Now query the label to find the component. We need to use
		// {@link GuiQuery} so that the access to the label happens on
		// the Swing event dispatch thread.
		JTextComponent component = (new GuiQuery<JTextComponent>() {
			@Override
			protected JTextComponent executeInEDT() throws Throwable {
				return (JTextComponent) label.target().getLabelFor();
			}
			
		}).executeInEDT();
		
		return new JTextComponentFixture(robot(), component);
	}

}
