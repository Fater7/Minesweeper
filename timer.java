import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class timer extends JLabel
{
	int time = 0;	//¼ÆÊ±
	
	public timer()
	{
		super();
		setFont(new Font("Dialog", 1, 30)); 
		setForeground(Color.white);
		setZero();
	}
	
	public void setZero()
	{
		time = 0;
		setText("TIME:" + Integer.toString(time));
	}
	
	public void addtime()
	{
		time++;
		setText("TIME:" + Integer.toString(time));
	}

}
