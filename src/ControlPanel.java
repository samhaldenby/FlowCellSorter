import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ControlPanel extends JPanel{
	private JButton loadButton;
	private JButton saveButton;
	
	//stats panel
	private JPanel statsPanel;
	private JTextField numLanesText;
	private JTextField numSamplesText;
	private JTextField freeSpaceText;
	private JLabel laneLabel;
	private JLabel sampleLabel;
	private JLabel freeLabel;
	public ControlPanel(){
		//set layout
		this.setLayout(new GridLayout(8,1));
		
		//set background colour
		setBackground(Color.gray.brighter());
		
		//create stats panel
		createStatsPanel();
		//create load button
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                System.out.println("You clicked the load button");

            }
        }); 
		
		//create load button
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                System.out.println("You clicked the save button");
          
            }
        }); 
		
		add(loadButton);
		add(saveButton);
		add(statsPanel);
	}
	
	
	public void createStatsPanel(){
		statsPanel = new JPanel();
		laneLabel = new JLabel("Lanes");
		numLanesText = new JTextField();
		sampleLabel = new JLabel("Samples");
		numSamplesText = new JTextField();
		freeLabel = new JLabel("% Free");
		freeSpaceText = new JTextField();
		
		statsPanel.setLayout(new GridLayout(3,2));
		statsPanel.add(laneLabel);
		statsPanel.add(numLanesText);
		statsPanel.add(sampleLabel);
		statsPanel.add(numSamplesText);
		statsPanel.add(freeLabel);
		statsPanel.add(freeSpaceText);
		
	}
	
	
	public void update(){
		numLanesText.setText(Integer.toString(Scores.best.NumNonEmptyLanes()));
		numSamplesText.setText(Integer.toString(Scores.best.NumSamples()));
		
		double result = Scores.best.getFreeSpace() * 10;
		result = Math.round(result);
		result = result / 10;
		freeSpaceText.setText(Double.toString(result));
	}


	public void disableLoadSave() {
		loadButton.setEnabled(false);
		saveButton.setEnabled(false);
		
	}


	public void enableLoadSave() {
		loadButton.setEnabled(true);
		saveButton.setEnabled(true);
		
	}
	
	
}
