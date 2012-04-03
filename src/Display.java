import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;


public class Display extends JFrame{

	/**
	 * 
	 */
	
	private static int FC_WIDTH=1000;
	private static int FC_LENGTH=1000;
	private static final long serialVersionUID = 1L;

	Display(){
		super("Main view");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,1000);
		setVisible(true);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
//		//for each round of first polishing
//		if (Scores.counts>0){
//			int numOfP1= Scores.p.size();
//			int numOfS1 = Scores.s.size();
//			for (int a=0; a<numOfP1; ++a){
//				double d = (Scores.p.get(a));
//				if(d>Double.MIN_VALUE){
//					g.setColor(Color.RED);
//					g.fillRect(0, (200/numOfP1)*a + 50, (int)d/100/Scores.counts, 200/numOfP1);
//					g.setColor(Color.BLACK);
//					g.drawRect(0, (200/numOfP1)*a + 50, (int)d/100/Scores.counts, 200/numOfP1);
//				}
//				
//			}
//			
//			for (int a=0; a<numOfS1; ++a){
//				double d = (Scores.s.get(a));
//				if(d>Double.MIN_VALUE){
//					g.setColor(Color.BLUE);
//					g.fillRect(0, (200/numOfS1)*a + 0, (int)d/Scores.counts, 200/numOfS1);
//					g.setColor(Color.BLACK);
//					g.drawRect(0, (200/numOfS1)*a + 0, (int)d/Scores.counts, 200/numOfS1);
//				}
//		
//			}
//		}
		
		//paint flowcell
		if(Scores.best!=null){
			ArrayList<Lane> nonEmptyLanes = Scores.best.NonEmptyLanes();
			int numLanes = nonEmptyLanes.size();
			for(int l=0; l<numLanes; ++l){
				
				//for each sample, draw a wee box
				int currPos =0;
				Iterator<Sample> iSample = nonEmptyLanes.get(l).getSamples().iterator();
				
//				g.setColor(Color.GREEN);
//				g.fillRect(0, (FC_WIDTH/numLanes)*l + 350, (int)(Scores.best.Lane(l).currentFillLevel() * FC_LENGTH), FC_WIDTH/numLanes);
				while(iSample.hasNext()){
					Sample sample = iSample.next();
//					System.out.printf("%s = %d\n",sample.Barcode(), sample.Barcode().hashCode());
					g.setColor(new Color(sample.Barcode().hashCode()+ Integer.MAX_VALUE/2));
					int len = (int)(sample.Reads()*FC_LENGTH);
					g.fillRect(currPos, (FC_WIDTH/numLanes)*l + 35, len, FC_WIDTH/numLanes);
					g.setColor(Color.BLACK);
					g.drawRect(currPos, (FC_WIDTH/numLanes)*l + 35, len, FC_WIDTH/numLanes);
					currPos+=len;
				}
				
				
//				g.setColor(Color.GREEN);
//				g.fillRect(0, (400/numLanes)*l + 450, (int)(Scores.best.Lane(l).currentFillLevel() * 400), 400/numLanes);
//				g.setColor(Color.BLACK);
//				g.drawRect(0, (400/numLanes)*l + 450, (int)(Scores.best.Lane(l).currentFillLevel() * 400), 400/numLanes);
				
			}
		}
		
//		g.drawRect(0,0,10,10);
//		g.setColor(Color.RED);
//		g.fillRect(50,50,Results.x,100);
		validate();
//		System.out.printf("%s on EDT? %s\n",this.toString(),javax.swing.SwingUtilities.isEventDispatchThread());
	}
	
	
	
}
