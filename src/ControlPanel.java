import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;


public class ControlPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8523461652875295421L;

	private Display display_;
	
	
	
	//run panel
	private JPanel runPanel;
	private JLabel runPanelLabel;
	private JButton loadButton;
	private JButton runButton;
	private JButton saveButton;
	
	//stats panel
	private JPanel statsPanel;
	private JLabel statsPanelLabelL, statsPanelLabelR;
	private JTextField numLanesText;
	private JTextField numSamplesText;
	private JTextField freeSpaceText;
	private JLabel laneLabel;
	private JLabel sampleLabel;
	private JLabel freeLabel;

	//check panel
	private JPanel colourPanel;
	private JLabel colourPanelLabel;
	private ButtonGroup colourButtonGroup;
	private JRadioButton colourByBarcodeButton;
	private JRadioButton colourByPoolButton;
	private JRadioButton colourBySpaceButton;

	

	
	public ControlPanel(Display display){
		
		display_ = display;
		//set layout
		GridLayout grid = new GridLayout(5,1);
		grid.setVgap(5);
		grid.setHgap(5);
		this.setLayout(grid);
		
		//set background colour
		setBackground(Color.black.darker());
		
		//create all panels
		createRunPanel();
		createStatsPanel();
		createColourPanel();
		
		
		add(runPanel);
		add(statsPanel);
		add(colourPanel);
	}
	
	public void createRunPanel(){
		runPanel = new JPanel();
		
		runPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.red.brighter(), Color.red.darker()));

		
		GridLayout grid = new GridLayout(4,1);
		grid.setVgap(3);
		runPanel.setLayout(grid);
		
		runPanelLabel = new JLabel("Run");
		
		//create load button
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                System.out.println("You clicked the load button");
                File input = FileChooser.Choose("Open","Select samplesheet to open");
                if(input!=null){
                	 try {
     					Storage.samples = SheetReader.read(input.toString());
     					
     					//reset variable
     					numLanesText.setText("NA");
     					numSamplesText.setText(Integer.toString(Storage.samples.size()));
     					freeSpaceText.setText("NA");
     					Scores.best = null;
     				} catch (IOException e1) {
     					// TODO Auto-generated catch block
     					e1.printStackTrace();
     				}
                }
               

            }
        }); 
		
		//create save button
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                System.out.println("You clicked the save button");
                
                File output = FileChooser.Choose("Save","Select output file");
                if (output!=null){
	                try {
						StrategyWriter.write(output.toString());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
          
            }
        }); 
		
		
		//create run button
		runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				System.out.println("You clicked the run button");
				ShuffleRunner runner = new ShuffleRunner(display_);
				new Thread(runner).start();

			}
		});
		
		runPanel.add(runPanelLabel);
		runPanel.add(loadButton);
		runPanel.add(runButton);
		runPanel.add(saveButton);
		
	}
	
	
	public void createStatsPanel(){
		statsPanel = new JPanel();
		
//		statsPanel.setBackground(Color.magenta.brighter());
		statsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.magenta.brighter(), Color.magenta.darker()));
		
		statsPanelLabelL = new JLabel("Stats");
		statsPanelLabelR = new JLabel();


		laneLabel = new JLabel("Lanes");
		numLanesText = new JTextField();
		sampleLabel = new JLabel("Samples");
		numSamplesText = new JTextField();
		freeLabel = new JLabel("% Free");
		freeSpaceText = new JTextField();
//		laneLabel.setBackground(Color.gray);
		
		statsPanel.setLayout(new GridLayout(4,2));
		statsPanel.add(statsPanelLabelL);
		statsPanel.add(statsPanelLabelR);
		statsPanel.add(laneLabel);
		statsPanel.add(numLanesText);
		statsPanel.add(sampleLabel);
		statsPanel.add(numSamplesText);
		statsPanel.add(freeLabel);
		statsPanel.add(freeSpaceText);
		
//		statsPanelLabelL.setBackground(Color.magenta.darker());
//		statsPanelLabelR.setBackground(Color.magenta.darker());
		
	}
	
	public void createColourPanel(){
		colourPanel = new JPanel();
//		colourPanel.setBackground(Color.cyan.darker());
		colourPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.cyan.brighter(), Color.cyan.darker()));

		
		
		colourPanelLabel = new JLabel("Colour by:");
		
		
		colourByBarcodeButton = new JRadioButton("Barcode");
		colourByBarcodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				System.out.println("You clicked the colour by barcode button");
				display_.setColourMode(Consts.COLOUR_BY_BARCODE);
			}
		});
		
		
		colourByPoolButton = new JRadioButton("Pool");
		colourByPoolButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				System.out.println("You clicked the colour by pool button");
				display_.setColourMode(Consts.COLOUR_BY_POOL);
			}
		});
		
		colourBySpaceButton = new JRadioButton("Space");
		colourBySpaceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				System.out.println("You clicked the colour by space button");
				display_.setColourMode(Consts.COLOUR_BY_SPACE);
			}
		});
		
		colourButtonGroup = new ButtonGroup();
		colourButtonGroup.add(colourByBarcodeButton);
		colourButtonGroup.add(colourByPoolButton);
		colourButtonGroup.add(colourBySpaceButton);
		
		
		colourPanel.setLayout(new GridLayout(4,1));
		colourPanel.add(colourPanelLabel);
		colourPanel.add(colourByBarcodeButton);
		colourPanel.add(colourByPoolButton);
		colourPanel.add(colourBySpaceButton);
		
		colourByBarcodeButton.setSelected(true);
		display_.setColourMode(Consts.COLOUR_BY_BARCODE);
		
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
		runButton.setEnabled(false);
	}


	public void enableLoadSave() {
		loadButton.setEnabled(true);
		saveButton.setEnabled(true);
		runButton.setEnabled(true);
	}
	
	
}
