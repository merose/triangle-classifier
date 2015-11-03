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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import me.markrose.example.triangle.TriangleClassifier.Type;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Implements a Swing main view for the triangle classifier application.
 */
public class TriangleView {

	private TriangleClassifier classifier;
	
	private JFrame frame;
	private JTextField side1;
	private JTextField side2;
	private JTextField side3;
	private JTextField summary;
	private JTextArea details;
	
	private static final Map<Type, String> CLASSIFICATION_SUMMARY =
			new HashMap<Type, String>();
	static {
		CLASSIFICATION_SUMMARY.put(Type.EQUILATERAL, "Equilateral");
		CLASSIFICATION_SUMMARY.put(Type.ISOSCELES, "Isosceles");
		CLASSIFICATION_SUMMARY.put(Type.SCALENE, "Scalene");
		CLASSIFICATION_SUMMARY.put(Type.NOT_A_TRIANGLE, "Not a triangle");
	}
	
	private static final Map<Type, String> CLASSIFICATION_DETAILS =
			new HashMap<Type, String>();
	static {
		CLASSIFICATION_DETAILS.put(Type.EQUILATERAL, "All sides are equal.");
		CLASSIFICATION_DETAILS.put(Type.ISOSCELES, "Two sides are equal.");
		CLASSIFICATION_DETAILS.put(Type.SCALENE, "All sides are different.");
		CLASSIFICATION_DETAILS.put(Type.NOT_A_TRIANGLE, "Sides cannot form a triangle.");
	}
	
	public static void main(String[] args) {
		(new TriangleView()).run();
	}

	private void run() {
		classifier = new TriangleClassifier();
		
		JTextArea instructions = new JTextArea();
		instructions.setText(
				"This program will determine the type of triangle given the "
				+ "lengths of the three sides of the triangle. Please enter the "
				+ "the lengths of the sides and press Analyze to display the "
				+ "type of triangle.");
		instructions.setLineWrap(true);
		instructions.setWrapStyleWord(true);
		instructions.setRows(2);
		
		JLabel side1Label = new JLabel("Side 1 length:");
		side1 = new JTextField();
		
		JLabel side2Label = new JLabel("Side 2 length:");
		side2 = new JTextField();
		
		JLabel side3Label = new JLabel("Side 3 length:");
		side3 = new JTextField();
		
		JButton analyzeButton = new JButton("Analyze");
		analyzeButton.setFocusable(false);
		analyzeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setClassification();
			}
		});
		
		JLabel summaryLabel = new JLabel("Classification:");
		summary = new JTextField("Not yet classified");
		summary.setBorder(Borders.EMPTY);
		Font summaryFont = summary.getFont();
		summary.setFont(summaryFont.deriveFont(Font.BOLD));

		details = new JTextArea("");
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
		
		builder.append(analyzeButton);
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
		
		frame.setSize(500, 300);
		frame.setVisible(true);
	}


	private void setClassification() {
		Type result = classifier.classify(side1.getText(), side2.getText(), side3.getText());
		summary.setText(CLASSIFICATION_SUMMARY.get(result));
		details.setText(CLASSIFICATION_DETAILS.get(result));
	}

}
