import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
	private MessagePanel messagePanel = null;

	Display(){
		super(String.format("Flow Cell Sorter v%s",Consts.VERSION));
		
		setLayout(new BorderLayout());
		
		//create panels
		controlPanel = new ControlPanel(this);
		
		
		flowCellPanel = new FlowCellPanel();
		flowCellPanel.setBackground(Color.yellow.darker());
		
		messagePanel = new MessagePanel();
		
		

		
		
		//add panels to frame
		add(controlPanel, BorderLayout.EAST);
		add(flowCellPanel, BorderLayout.CENTER);
		add(messagePanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
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
	
	
	
}
