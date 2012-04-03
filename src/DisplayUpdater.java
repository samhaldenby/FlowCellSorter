
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
			d.repaint();
		}
		
	}

}
