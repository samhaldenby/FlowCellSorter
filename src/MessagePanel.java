import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;


/** GUI component used for displaying progress information
*
* @author Sam Haldenby
*/
public class MessagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField textBox_;
	public MessagePanel(){
		
		Font font = new Font("Arial", Font.BOLD,12);
		
		setBackground(Color.green.darker());
		textBox_= new JTextField();
		
		textBox_.setFont(font);
		textBox_.setEditable(false);
//		textBox_.set
		textBox_.setAlignmentX(CENTER_ALIGNMENT);
		textBox_.setBackground(this.getBackground());
		textBox_.setBorder(null);

//		scrollPane_ = new JScrollPane();
//		scrollPane_.setBorder(null);
//		scrollPane_.add(textBox_);
//		scrollPane_.setVisible(true);
//		scrollPane_.setViewportView(textBox_);
//		add(scrollPane_);
//		scrollPane_.setSize(new Dimension(this.getWidth(), this.getHeight()));
		
		add(textBox_);

//		textBox_.setPreferredSize(this.getPreferredSize());
		textBox_.setText(String.format("Welcome to Flow Cell Sorter v%s",Consts.VERSION));
		
//		textBox_.setText("THIS IS THE TEXT AREA!!\nSam\nTHIS IS A BIT LONGER\nTHIS IS HUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUGE\n\nTESSTF\n");
		
	}
	
	public void updateMessage(String message){
		textBox_.setColumns((int)(message.length()));
	
		textBox_.setText(message);	
//		textBox_.setPreferredSize(new Dimension((int)textBox_.getFont().getSize2D(),(int)textBox_.getFont().getSize2D()));
		textBox_.validate();
		this.validate();

	}

}