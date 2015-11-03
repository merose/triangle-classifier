package me.markrose.example.triangle;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import me.markrose.example.triangle.TriangleClassifier.Type;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Implements a Swing main view for the triangle classifier application.
 */
public class TriangleView {
	
	// Names of the interactive components. Default scope so they
	// can be used for unit testing.
	static final String DETAILS_NAME = "DETAILS_NAME";
	static final String SUMMARY_NAME = "SUMMARY_NAME";
	static final String CLASSIFY_BUTTON_NAME = "CLASSIFY_BUTTON_NAME";
	static final String SIDE1_NAME = "side1";
	static final String SIDE2_NAME = "side2";
	static final String SIDE3_NAME = "side3";

	private TriangleClassifier classifier;
	
	private JFrame frame;
	private JTextField side1;
	private JTextField side2;
	private JTextField side3;
	private JTextField summary;
	private JTextArea details;
	
	/**
	 * A map between the triangle type and a summary of the classification.
	 */
	private static final Map<Type, String> CLASSIFICATION_SUMMARY =
			new HashMap<Type, String>();
	static {
		CLASSIFICATION_SUMMARY.put(Type.EQUILATERAL, "Equilateral");
		CLASSIFICATION_SUMMARY.put(Type.ISOSCELES, "Isosceles");
		CLASSIFICATION_SUMMARY.put(Type.SCALENE, "Scalene");
		CLASSIFICATION_SUMMARY.put(Type.NOT_A_TRIANGLE, "Not a triangle");
	}
	
	/**
	 * A map between the triangle type and a detail message about the classification.
	 */
	private static final Map<Type, String> CLASSIFICATION_DETAILS =
			new HashMap<Type, String>();
	static {
		CLASSIFICATION_DETAILS.put(Type.EQUILATERAL, "All sides are equal.");
		CLASSIFICATION_DETAILS.put(Type.ISOSCELES, "Two sides are equal.");
		CLASSIFICATION_DETAILS.put(Type.SCALENE, "All sides are different.");
		CLASSIFICATION_DETAILS.put(Type.NOT_A_TRIANGLE, "Sides cannot form a triangle.");
	}
	
	/**
	 * Starts the application by creating a view and running it.
	 * 
	 * @param args the command line arguments, if any
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				(new TriangleView()).run();
			}
		});
	}

	/**
	 * Runs the view by creating the main frame and adding the
	 * widgets.
	 */
	private void run() {
		classifier = new TriangleClassifier();
		
		JTextArea instructions = new JTextArea();
		instructions.setText(
				"This program will determine the type of triangle given the "
				+ "lengths of the three sides of the triangle. Please enter the "
				+ "the integer lengths of the sides and press Classify to display the "
				+ "type of triangle.");
		instructions.setLineWrap(true);
		instructions.setWrapStyleWord(true);
		instructions.setRows(4);
		
		JLabel side1Label = new JLabel("Side 1 length:");
		side1 = new JTextField();
		side1.setName(SIDE1_NAME);
		
		JLabel side2Label = new JLabel("Side 2 length:");
		side2 = new JTextField();
		side2.setName(SIDE2_NAME);
		
		JLabel side3Label = new JLabel("Side 3 length:");
		side3 = new JTextField();
		side3.setName(SIDE3_NAME);
		
		JButton classifyButton = new JButton("Classify");
		classifyButton.setName(CLASSIFY_BUTTON_NAME);
		classifyButton.setFocusable(false);
		classifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					setClassification(classifier.classify(side1.getText(), side2.getText(), side3.getText()));
				} catch (Throwable ex) {
					showException(ex);
				}
			}
		});
		
		JLabel summaryLabel = new JLabel("Classification:");
		summary = new JTextField("Not yet classified");
		summary.setName(SUMMARY_NAME);
		summary.setEditable(false);
		summary.setBorder(Borders.EMPTY);
		summary.setFont(summary.getFont().deriveFont(Font.BOLD));

		details = new JTextArea("");
		details.setName(DETAILS_NAME);
		details.setEditable(false);
		details.setLineWrap(true);
		details.setWrapStyleWord(true);
		
		FormLayout layout = new FormLayout(
				"left:max(25dlu;pref), 4dlu, 80dlu, 80dlu",
				""
		);
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.border(Borders.DIALOG);
		
		builder.append(instructions, 4);
		builder.nextLine();

		builder.append(side1Label, side1);
		builder.nextLine();
		builder.append(side2Label, side2);
		builder.nextLine();
		builder.append(side3Label, side3);
		builder.nextLine();
		
		builder.append(classifyButton);
		builder.nextLine();
		
		builder.append(summaryLabel, summary);
		builder.nextLine();
		
		builder.append(details, 4);
		builder.nextLine();
		
		JPanel panel = builder.build();
		panel.setOpaque(false);
		
		frame = new JFrame("Triangle Classifier");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setContentPane(panel);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Changes the classification messages shown in the view.
	 * 
	 * @param classification the triangle classification based on the three side lengths
	 */
	private void setClassification(Type classification) {
		summary.setText(CLASSIFICATION_SUMMARY.get(classification));
		details.setText(CLASSIFICATION_DETAILS.get(classification));
	}

	/**
	 * Displays an alert box showing an exception message, and
	 * also displays to the standard error output.
	 * 
	 * @param ex the exception that occurred
	 */
	private void showException(Throwable ex) {
		JOptionPane.showMessageDialog(
				frame,
				"An unexpected exception occurred.\n"
				+ ex.getClass().getName() + "\n"
				+ ex.getMessage(),
				"Unexpected Exception",
				JOptionPane.ERROR_MESSAGE
		);
	}

}
