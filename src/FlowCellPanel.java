import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;


public class FlowCellPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Display display_;
	
	public FlowCellPanel(Display display){
		super();
		display_ = display;
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
				
	
			//paint flowcell
			if(Scores.best!=null){
				
				//switch!
				switch (display_.ColourMode()){
					case Consts.COLOUR_BY_BARCODE:{
						ArrayList<Lane> nonEmptyLanes = Scores.best.NonEmptyLanes();
						int numLanes = nonEmptyLanes.size();
						boolean drawBorders = (this.getHeight()/numLanes >=2);
						for(int l=0; l<numLanes; ++l){
							
							//for each sample, draw a wee box
							
							Iterator<Sample> iSample = nonEmptyLanes.get(l).getSamples().iterator();
							
							double currPos = 0;
							while(iSample.hasNext()){
								Sample sample = iSample.next();
			//					System.out.printf("%s = %d\n",sample.Barcode(), sample.Barcode().hashCode());
								g.setColor(new Color(sample.Barcode().hashCode()+ Integer.MAX_VALUE/2));
			//					g.setColor(new Color((int)Math.pow(sample.Pool(),2.5)+ Integer.MAX_VALUE/2));
			//					g.setColor(new Color((int)sample.Pool()*5000+ Integer.MAX_VALUE/2));
								double len = ((sample.Reads() / nonEmptyLanes.get(l).Capacity()) * this.getWidth()*0.99d);
								g.fillRect((int)currPos, (this.getHeight()/numLanes)*l + this.getY()+30, (int)len, this.getHeight()/numLanes);
								if(drawBorders){
									g.setColor(Color.BLACK);
									g.drawRect((int)currPos, (this.getHeight()/numLanes)*l + this.getY()+30, (int)len, this.getHeight()/numLanes);
								}
								currPos+=len;
							}
							
							
			//				g.setColor(Color.GREEN);
			//				g.fillRect(0, (400/numLanes)*l + 450, (int)(Scores.best.Lane(l).currentFillLevel() * 400), 400/numLanes);
			//				g.setColor(Color.BLACK);
			//				g.drawRect(0, (400/numLanes)*l + 450, (int)(Scores.best.Lane(l).currentFillLevel() * 400), 400/numLanes);
							
						}
						break;}
						
						
					
					case Consts.COLOUR_BY_POOL:{
						ArrayList<Lane> nonEmptyLanes = Scores.best.NonEmptyLanes();
						int numLanes = nonEmptyLanes.size();
						boolean drawBorders = (this.getHeight()/numLanes >=2);
						for(int l=0; l<numLanes; ++l){
							
							//for each sample, draw a wee box
							
							Iterator<Sample> iSample = nonEmptyLanes.get(l).getSamples().iterator();
							
							double currPos = 0;
							while(iSample.hasNext()){
								Sample sample = iSample.next();
			//					System.out.printf("%s = %d\n",sample.Barcode(), sample.Barcode().hashCode());
//								g.setColor(new Color(sample.Barcode().hashCode()+ Integer.MAX_VALUE/2));
								g.setColor(new Color((int)Math.pow(sample.Pool(),2.5)+ Integer.MAX_VALUE/2));
//								g.setColor(new Color((int)sample.Pool()*5000+ Integer.MAX_VALUE/2));
								double len = ((sample.Reads() / nonEmptyLanes.get(l).Capacity()) * this.getWidth()*0.99d);
								g.fillRect((int)currPos, (this.getHeight()/numLanes)*l + this.getY()+30, (int)len, this.getHeight()/numLanes);
								
								if(drawBorders){
									g.setColor(Color.BLACK);
									g.drawRect((int)currPos, (this.getHeight()/numLanes)*l + this.getY()+30, (int)len, this.getHeight()/numLanes);
								}
								currPos+=len;
							}
							
							
			//				g.setColor(Color.GREEN);
			//				g.fillRect(0, (400/numLanes)*l + 450, (int)(Scores.best.Lane(l).currentFillLevel() * 400), 400/numLanes);
			//				g.setColor(Color.BLACK);
			//				g.drawRect(0, (400/numLanes)*l + 450, (int)(Scores.best.Lane(l).currentFillLevel() * 400), 400/numLanes);
							
						}
						break;}
					
					
					
					case Consts.COLOUR_BY_SPACE:{
						ArrayList<Lane> nonEmptyLanes = Scores.best.NonEmptyLanes();
						int numLanes = nonEmptyLanes.size();
						
						boolean drawBorders = (this.getHeight()/numLanes >=2);
						for(int l=0; l<numLanes; ++l){
							
		
		
							double currPos = 0;
							
							//empty bit first
							double len = 1.0d * this.getWidth() * 0.99d;
							g.setColor(Color.yellow.darker());
							g.fillRect((int)currPos, (this.getHeight()/numLanes)*l + this.getY()+30, (int)len, this.getHeight()/numLanes);
							
							if(drawBorders){
								g.setColor(Color.BLACK);
								g.drawRect((int)currPos, (this.getHeight()/numLanes)*l + this.getY()+30, (int)len, this.getHeight()/numLanes);
							}
							g.setColor(Color.blue.darker());
							double fullness = nonEmptyLanes.get(l).currentFillLevel() / nonEmptyLanes.get(l).Capacity();
							 len = fullness * this.getWidth() * 0.99d;
							g.fillRect((int)currPos, (this.getHeight()/numLanes)*l + this.getY()+30, (int)len, this.getHeight()/numLanes);
							
							if(drawBorders){
								g.setColor(Color.BLACK);
								g.drawRect((int)currPos, (this.getHeight()/numLanes)*l + this.getY()+30, (int)len, this.getHeight()/numLanes);
							}

							
						}
						break;}
						
				
				}
				
			}
			
	//		g.drawRect(0,0,10,10);
	//		g.setColor(Color.RED);
	//		g.fillRect(50,50,Results.x,100);
			validate();
	//		System.out.printf("%s on EDT? %s\n",this.toString(),javax.swing.SwingUtilities.isEventDispatchThread());
		}
	

}
