
public class program 
{
	public static void main(String[] args)
	{
		MainFrame mm = new MainFrame();
		mm.setVisible(true);
		player p = new player(mm);
		p.StartPlay();
	}
}
