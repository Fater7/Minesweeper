import javax.swing.*;  

public class button extends JLabel 
{
	public int IsClicked;		    //记录按钮状态，0为初始，1为标记旗，2为踩开，3为问号
	public boolean IsMine;			//记录该按钮是否为地雷
	public int number;				//记录该按钮周围八格雷的数量
	public int x;
	public int y;
	
	private ImageIcon m;
	
	public button(int x1, int y1)
	{
		super();
		x = x1;
		y = y1;
		IsClicked = 0;
		IsMine = false;
		number = 9;
		m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/mine_close.png");
		setIcon(m);		
	}
	
	//左键改变按钮图标
	public void change_picture()
	{
       	if(IsMine)
       	{
       		m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/mine_boom.png");
       		setIcon(m);
       	}
       	else
       	{
       		switch(number)
        	{
        	case 0:
        		m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/0.png");
       			setIcon(m);
               	break;
       		case 1:
       			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/1.png");
       			setIcon(m);
               	break;
       		case 2:
       			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/2.png");
        		setIcon(m);
               	break;
        	case 3:
        		m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/3.png");
       			setIcon(m);
               	break;
       		case 4:
       			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/4.png");
       			setIcon(m);
               	break;
       		case 5:
       			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/5.png");
        		setIcon(m);
               	break;
        	case 6:
       			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/6.png");
       			setIcon(m);
               	break;
       		case 7:
       			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/7.png");
       			setIcon(m);
               	break;
       		case 8:
        		m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/8.png");
        		setIcon(m);
                break;
        	}
        }
	}

	//右键改变按钮图标
	public void change_picture_right()
	{
		//当未点开时，变为旗
		if(IsClicked == 0)
		{
			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/mine_check.png");
			setIcon(m);
			IsClicked = 1;
		}
		
		//当为旗时，变为问号
		else if(IsClicked == 1)
		{
			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/mine_undefined.png");
			setIcon(m);
			IsClicked = 3;
		}
		
		//当为问号时，变为空板
		else if(IsClicked == 3)
		{
			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/mine_close.png");
			setIcon(m);
			IsClicked = 0;
		}
	}
}
