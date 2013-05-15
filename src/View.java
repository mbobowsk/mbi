import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.SortedSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;


public class View extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6775398136175415140L;
	private static int SPACE_SIZE = 5;
	private ControllerClean controller;
	private JPanel mainPanel;
	private JPanel buttonsPanel;
	private JPanel sequencesPanel;
	private JPanel statusPanel;
	private JButton matrixButton;
	private JButton openButton;
	private JButton saveButton;
	private JButton runButton;
	private JTextArea inputArea;
	private JTextArea outputArea;
	private JScrollPane inputScrollPane;
	private JScrollPane outputScrollPane;
	private JLabel inputLabel;
	private JLabel outputLabel;
	private JLabel timeLabel;
	private JLabel memoryLabel;
	private Dimension spaces;
	private JFileChooser fileChooser;
	private String inputSequence1;
	private String inputSequence2;
	private String inputSequence3;
	private String outputSequence1;
	private String outputSequence2;
	private String outputSequence3;

	View(ControllerClean controller){
		super("DNA sequences");
		this.controller = controller;
		spaces = new Dimension(SPACE_SIZE, SPACE_SIZE);
		fileChooser = new JFileChooser();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL imageURL = getClass().getResource("tux.png");
		setIconImage(tk.createImage(imageURL));
		setLayout(new BorderLayout());
		initButtons();
		initAreas();
		initLabels();
		initPanels();
		add(mainPanel, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.SOUTH);
		pack();		
		setVisible(true);
	}

	private void initButtons(){
		matrixButton = new JButton("Matrix");
		matrixButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MatrixDialog(controller.getModel());
			}
		});

		openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fileChooser.showOpenDialog(View.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					inputArea.setText("");
					outputArea.setText("");
					saveButton.setEnabled(false);
					File file = fileChooser.getSelectedFile();
					try {
						BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
						String s1 = br.readLine();
						String s2 = br.readLine();
						String s3 = br.readLine();
						if (validateSequences(s1, s2, s3)){
							runButton.setEnabled(true);
							inputArea.setText(inputSequence1 + '\n' + inputSequence2 + '\n' + inputSequence3);
						} else {
							runButton.setEnabled(false);
							inputArea.setText("Data in file \"" + file.getName() + "\" is not valid");
						}
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					} 


				}		        
			}
		});

		saveButton = new JButton("Save");
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					File file = new File("result.txt");

					// if file doesn't exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}

					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(outputSequence1 + '\n' + outputSequence2 + '\n' + outputSequence3);
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		runButton = new JButton("Run");
		runButton.setEnabled(false);
		runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveButton.setEnabled(true);
				controller.reset();
				Long startTime = System.currentTimeMillis();
				controller.findSimilarity(inputSequence1, inputSequence2, inputSequence3, 0, 0, 0);
				Long stopTime = System.currentTimeMillis();
				double time = (double) (stopTime - startTime);
				timeLabel.setText("Computing time: " + (double)Math.round(time * 100d) / 100000d + " sec");
				memoryLabel.setText("Memory usage: " + controller.getMaxMemory() + "MB");

				outputSequence1 = "";
				outputSequence2 = "";
				outputSequence3 = "";
				int x = 0;
				int y = 0;
				int z = 0;
				int difX, difY, difZ;
				SortedSet<Tuple> result = controller.getResult();
				String gap = "-";
				for ( Tuple t : result ) {
					//					System.out.println(t);
					difX = t.x - x;
					difY = t.y - y;
					difZ = t.z - z;

					if (difX == 1 && difY == 1 && difZ == 1){
						outputSequence1 = outputSequence1 + inputSequence1.charAt(t.x-1);
						outputSequence2 = outputSequence2 + inputSequence2.charAt(t.y-1);
						outputSequence3 = outputSequence3 + inputSequence3.charAt(t.z-1);
					} else if(difX == 0 && difY == 1 && difZ == 1){
						outputSequence1 = outputSequence1 + gap;
						outputSequence2 = outputSequence2 + inputSequence2.charAt(t.y-1);
						outputSequence3 = outputSequence3 + inputSequence3.charAt(t.z-1);
					} else if(difX == 0 && difY == 0 && difZ == 1){
						outputSequence1 = outputSequence1 + gap;
						outputSequence2 = outputSequence2 + gap;
						outputSequence3 = outputSequence3 + inputSequence3.charAt(t.z-1);
					} else if(difX == 0 && difY == 1 && difZ == 0){
						outputSequence1 = outputSequence1 + gap;
						outputSequence2 = outputSequence2 + inputSequence2.charAt(t.y-1);
						outputSequence3 = outputSequence3 + gap;
					} else if(difX == 1 && difY == 0 && difZ == 1){
						outputSequence1 = outputSequence1 + inputSequence1.charAt(t.x-1);
						outputSequence2 = outputSequence2 + gap;
						outputSequence3 = outputSequence3 + inputSequence3.charAt(t.z-1);
					} else if(difX == 1 && difY == 1 && difZ == 0){
						outputSequence1 = outputSequence1 + inputSequence1.charAt(t.x-1);
						outputSequence2 = outputSequence2 + inputSequence2.charAt(t.y-1);
						outputSequence3 = outputSequence3 + gap;
					} else if(difX == 1 && difY == 0 && difZ == 0){
						outputSequence1 = outputSequence1 + inputSequence1.charAt(t.x-1);
						outputSequence2 = outputSequence2 + gap;
						outputSequence3 = outputSequence3 + gap;
					}

					x = t.x;
					y = t.y;
					z = t.z;
				}
				outputArea.setText(outputSequence1 + '\n' + outputSequence2 + '\n' + outputSequence3);
			}
		});
	}

	private void initAreas(){
		inputArea = new JTextArea();
		inputArea.setRows(3);
		inputArea.setEditable(false);
		inputArea.setFont(new Font("Monospaced",Font.PLAIN,12));
		inputScrollPane = new JScrollPane(inputArea);
		outputArea = new JTextArea();
		outputArea.setRows(3);
		outputArea.setEditable(false);
		outputArea.setFont(new Font("Monospaced",Font.PLAIN,12));
		outputScrollPane = new JScrollPane(outputArea);
	}

	private void initLabels(){
		inputLabel = new JLabel("Input sequences:");
		outputLabel = new JLabel("Output sequences:");
		timeLabel = new JLabel("");
		memoryLabel = new JLabel("");
	}

	private void initPanels(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(SPACE_SIZE, SPACE_SIZE, SPACE_SIZE, SPACE_SIZE));

		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(SPACE_SIZE, SPACE_SIZE, SPACE_SIZE, SPACE_SIZE));
		buttonsPanel.add(matrixButton);
		buttonsPanel.add(Box.createRigidArea(spaces));
		buttonsPanel.add(openButton);
		buttonsPanel.add(Box.createRigidArea(spaces));
		buttonsPanel.add(saveButton);
		buttonsPanel.add(Box.createRigidArea(spaces));
		buttonsPanel.add(runButton);

		sequencesPanel = new JPanel();
		sequencesPanel.setLayout(new BoxLayout(sequencesPanel, BoxLayout.Y_AXIS));
		sequencesPanel.setBorder(BorderFactory.createEmptyBorder(SPACE_SIZE, SPACE_SIZE, SPACE_SIZE, SPACE_SIZE));
		sequencesPanel.add(inputLabel);
		sequencesPanel.add(inputScrollPane);
		sequencesPanel.add(outputLabel);
		sequencesPanel.add(outputScrollPane);

		mainPanel.add(sequencesPanel);
		mainPanel.add(buttonsPanel);

		statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setPreferredSize(new Dimension(this.getWidth(), 32));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
		statusPanel.add(timeLabel);
		statusPanel.add(memoryLabel);
	}

	private boolean validateSequences(String s1, String s2, String s3){
		String pattern = "[AGCT]+";
		if ( s1 != null && s2 != null && s3 != null ){
			if (s1.length() >= s2.length() && s1.length() >= s3.length()){
				inputSequence1 = s1.toUpperCase();
				inputSequence2 = s2.toUpperCase();
				inputSequence3 = s3.toUpperCase();
			} else if (s2.length() >= s1.length() && s2.length() >= s3.length())
			{
				inputSequence1 = s2.toUpperCase();
				inputSequence2 = s1.toUpperCase();
				inputSequence3 = s3.toUpperCase();
			} else if (s3.length() >= s1.length() && s3.length() >= s2.length())
			{
				inputSequence1 = s3.toUpperCase();
				inputSequence2 = s2.toUpperCase();
				inputSequence3 = s1.toUpperCase();
			}
				return (inputSequence1.matches(pattern) && inputSequence2.matches(pattern) && inputSequence3.matches(pattern));
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		ControllerClean c = new ControllerClean();
		new View(c);
	}

}
