import java.awt.Container;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class FileChooser {
	
	public static File Choose(String approveLabel, String header)
    {
		
		

        //prepare return data
        File file;

        //create file chooser
        JFileChooser chooser = new JFileChooser();
        
       
        chooser.setCurrentDirectory(new File(PreferenceStore.UserDir));
        
//        chooser.setAccessory(cBox);



        chooser.setApproveButtonText(approveLabel);
        chooser.setDialogTitle(header);

        //create frame, to contain chooser
        JFrame frame = new JFrame("Select Sample Sheet to Load");
        frame.setSize(1, 1);
        Container pane = frame.getContentPane();
        pane.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add bar to pane
        pane.add(chooser);
        chooser.setBounds(0, 0, 500, 500);

        frame.setResizable(true);
        frame.setVisible(true);

        //do eventy type stuff for file
//        chooser.setMultiSelectionEnabled(true);

        //wait for results (i.e. what's the outcome of this window)
        int result = chooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION)
        {
//            currDir = chooser.getCurrentDirectory();
            frame.setVisible(false);
            frame.dispose();
            frame.invalidate();

            return chooser.getSelectedFile();

        } else
        {
            frame.setVisible(false);
        	return null;

        }
    }

}
