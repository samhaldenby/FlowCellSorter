import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Display extends JFrame{

	/**
	 * 
	 */
	
	private static int FC_WIDTH=1000;
	private static int FC_LENGTH=1000;
	private static final long serialVersionUID = 1L;

	private ControlPanel controlPanel= null;
	private FlowCellPanel flowCellPanel = null;

	Display(){
		super("Main view");
		
		setLayout(new BorderLayout());
		
		//create panels
		controlPanel = new ControlPanel();
		
		
		flowCellPanel = new FlowCellPanel();
		flowCellPanel.setBackground(Color.yellow.darker());
		

		
		
		//add panels to frame
		add(controlPanel, BorderLayout.EAST);
		add(flowCellPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,1000);
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
	
	
	
}
