/** Utilised for updating GUI at regular intervals
*
* @author Sam Haldenby
*/
public class DisplayUpdater implements Runnable{

	private Display d = null;
	public DisplayUpdater(Display d){
		this.d = d;
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.printf("DisplayUpdater: Loop on EDT? %s\n",javax.swing.SwingUtilities.isEventDispatchThread());
			if(Scores.best!=null){
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
				      public void run() {
				    	d.repaint();
				        d.update();  // access result and update JList
				      }
				    } );
			}

//				d.repaint();
//				d.update();
			
		}
		
	}

}
