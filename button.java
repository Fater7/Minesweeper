import javax.swing.*;  

public class button extends JLabel 
{
	public int IsClicked;		    //��¼��ť״̬��0Ϊ��ʼ��1Ϊ����죬2Ϊ�ȿ���3Ϊ�ʺ�
	public boolean IsMine;			//��¼�ð�ť�Ƿ�Ϊ����
	public int number;				//��¼�ð�ť��Χ�˸��׵�����
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
	
	//����ı䰴ťͼ��
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

	//�Ҽ��ı䰴ťͼ��
	public void change_picture_right()
	{
		//��δ�㿪ʱ����Ϊ��
		if(IsClicked == 0)
		{
			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/mine_check.png");
			setIcon(m);
			IsClicked = 1;
		}
		
		//��Ϊ��ʱ����Ϊ�ʺ�
		else if(IsClicked == 1)
		{
			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/mine_undefined.png");
			setIcon(m);
			IsClicked = 3;
		}
		
		//��Ϊ�ʺ�ʱ����Ϊ�հ�
		else if(IsClicked == 3)
		{
			m = new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/mine_close.png");
			setIcon(m);
			IsClicked = 0;
		}
	}
}
