import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;


public class MatrixDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8226321878100835070L;
	private Model model;
	private JPanel mainPanel;
	private JPanel buttonsPanel;
	private JPanel matrixPanel;
	private JButton okButton;
	private JButton cancelButton;
	private JSpinner spinners[][];
	private JSpinner gapSpinner;
	private JSpinner cSpinner;

	public MatrixDialog(Model model){
		this.model = model;
		setTitle("Define values");
		setLayout(new BorderLayout());
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL imageURL = getClass().getResource("tux.png");
		setIconImage(tk.createImage(imageURL));
		setModal(true);
		initButtons();
		initSpinners();
		initPanels();
		add(mainPanel, BorderLayout.CENTER);
		pack();
		setVisible(true);
		setResizable(false);
	}

	private void initButtons(){
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < 4; i++){
					for(int j = 0; j < 4; j++){
						model.similarityMatrix[i][j] = (Integer) spinners[i][j].getValue();
					}
				}
				
				model.gap = (Integer) gapSpinner.getValue();
				model.c = (Integer) cSpinner.getValue();
				dispose();
			}
		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	private void initSpinners(){
		spinners = new JSpinner[4][4];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				spinners[i][j] = new JSpinner(new SpinnerNumberModel(model.similarityMatrix[i][j], -100, 100, 1));
			}
		}
	}

	private void initPanels(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonsPanel.add(okButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(5,5)));
		buttonsPanel.add(cancelButton);

		matrixPanel = new JPanel();
		matrixPanel.setLayout(new GridBagLayout());
		matrixPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints c = new GridBagConstraints();
		int ins = 7;
		c.insets = new Insets(ins, ins, ins, ins);
		
		c.gridx = 0;
		c.gridy = 0;

		c.gridy++;
		matrixPanel.add(new JLabel("A"), c);
		c.gridy++;
		matrixPanel.add(new JLabel("G"), c);
		c.gridy++;
		matrixPanel.add(new JLabel("C"), c);
		c.gridy++;
		matrixPanel.add(new JLabel("T"), c);
		c.gridy++;
		matrixPanel.add(new JLabel("gap"), c);
		c.gridy++;
		matrixPanel.add(new JLabel("c"), c);
		
		c.gridx = 0;
		c.gridy = 0;
		matrixPanel.add(new JLabel(), c);
		c.gridx++;
		matrixPanel.add(new JLabel("A"), c);
		c.gridx++;
		matrixPanel.add(new JLabel("G"), c);
		c.gridx++;
		matrixPanel.add(new JLabel("C"), c);
		c.gridx++;
		matrixPanel.add(new JLabel("T"), c);
		c.gridx++;

		
		for(int i = 1; i < 5; i++){
			for(int j = 1; j < 5; j++){
				c.gridx = i;
				c.gridy = j;
					matrixPanel.add(spinners[j-1][i-1], c);
			}
		}
		
		c.gridx = 1;
		c.gridy = 5;
		gapSpinner = new JSpinner(new SpinnerNumberModel(model.gap, -100, 100, 1));
		matrixPanel.add(gapSpinner, c);
		
		c.gridx = 1;
		c.gridy = 6;
		cSpinner = new JSpinner(new SpinnerNumberModel(model.c, -100, 100, 1));
		matrixPanel.add(cSpinner, c);

		mainPanel.add(matrixPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
	}



}
