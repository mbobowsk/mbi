import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class View extends JFrame{
	
	private JPanel mainPanel;
	private JPanel buttonsPanel;
	private JPanel sequencesPanel;
	private JButton matrixButton;
	private JButton openButton;
	private JButton saveButton;
	private JButton runButton;
	private JTextArea inputArea;
	private JTextArea outputArea;
	private JLabel inputLabel;
	private JLabel outputLabel;
	
	View(){
		super("tekst");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		initButtons();
		initAreas();
		initLabels();
		initPanels();
		add(mainPanel, BorderLayout.CENTER);
		setSize(800, 600);
		setVisible(true);
	}
	
	private void initButtons(){
		matrixButton = new JButton("Matrix");
		openButton = new JButton("Open");
		saveButton = new JButton("Save");
		runButton = new JButton("Run");
	}
	
	private void initAreas(){
		inputArea = new JTextArea();
		outputArea = new JTextArea();
	}
	
	private void initLabels(){
		inputLabel = new JLabel("Input sequences:");
		outputLabel = new JLabel("Output sequences:");
	}
	
	private void initPanels(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setBorder(BorderFactory.createEtchedBorder());
		
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.add(matrixButton);
		buttonsPanel.add(openButton);
		buttonsPanel.add(saveButton);
		buttonsPanel.add(runButton);
		
		sequencesPanel = new JPanel();
		sequencesPanel.setLayout(new BoxLayout(sequencesPanel, BoxLayout.Y_AXIS));
		sequencesPanel.add(inputLabel);
		sequencesPanel.add(inputArea);
		sequencesPanel.add(outputLabel);
		sequencesPanel.add(outputArea);
		
		mainPanel.add(sequencesPanel);
		mainPanel.add(buttonsPanel);
	}

	public static void main(String[] args) {
		View v = new View();
		Controller c = new Controller();
		c.findSimilarity("GCC","GCAGT",0,0);
		for ( Tuple t : c.result ) {
			System.out.println(t);
		}

	}

}
