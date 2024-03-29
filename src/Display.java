import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/** Main GUI component
*
* @author Sam Haldenby
*/
public class Display extends JFrame{

	
	private static final long serialVersionUID = 1L;

	private ControlPanel controlPanel= null;
	private FlowCellPanel flowCellPanel = null;
	private MessagePanel messagePanel = null;
	private int colourMode_ = Consts.COLOUR_BY_BARCODE;

	Display(){
		super(String.format("Flow Cell Sorter v%s",Consts.VERSION));
		
		setLayout(new BorderLayout());
		

		
		//create panels
		controlPanel = new ControlPanel(this);
		
		
		flowCellPanel = new FlowCellPanel(this);
		flowCellPanel.setBackground(Color.gray.brighter());
		
		messagePanel = new MessagePanel();
		
		

		
		//add panels to frame
		add(controlPanel, BorderLayout.EAST);
		add(flowCellPanel, BorderLayout.CENTER);
		add(messagePanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.out.println("EXITING NOW!");
				Scores.reset();
			}
		});
		
		
//		this.setResizable(false);
		
		//get screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width/2,(int)(screenSize.height*0.9f));
		setVisible(true);
	}
	
	public void update(){
		controlPanel.update();
	}
	
	public void disableLoadSave(){
		controlPanel.disableLoadSave();
	}
	
	public void enableLoadSave(){
		controlPanel.enableLoadSave();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		flowCellPanel.paint(g);
	}
	
	public void updateMessage(String message){
		messagePanel.updateMessage(message);
	}

	public void setColourMode(int colourMode) {
		colourMode_ = colourMode;
		
	}

	public int ColourMode() {
		return colourMode_;
	}
	
	
	
}
