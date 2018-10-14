import javax.swing.*;  
import java.awt.event.*;  
import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MainFrame extends JFrame implements MouseListener, ActionListener
{
	public button[][] BA = new button[20][20];		//��������
	private boolean IsFirst;		//����ǲ��ǵ�һ�ε��
	private boolean IsOver;			//�����Ϸ�Ƿ��ѽ���
	private JLabel Dog;				//��Ϸ���
	private look SO;				//��Ϸ��ʼ��ť
	private timer Ti;				//ʱ����ʾ
	private Timer tme;				//��ʱ��
	private int MineCount;			//��������
	private Random ra;				//�����
	
	public MainFrame()
	{
		setResizable(false);		//���ô���Ϊ�̶���С
		getContentPane().setLayout(null);	//�ղ���
		getContentPane().setBackground(new Color(22, 24, 21));
		setSize(700,880);
		setLocationRelativeTo(null);		//��Ļ�м���ʾ
		IsOver = false;
		IsFirst = true;
		MineCount = 0;
		ra = new Random();
		
		SO = new look();
		SO.addMouseListener(this);
		add(SO);
		SO.setBounds(50, 50, 100, 50);
		
		Dog = new JLabel();
		add(Dog);
		Dog.setBounds(300, 50, 61, 60);
		
		Ti = new timer();
		add(Ti);
		Ti.setBounds(500, 60, 200, 50);
		
		tme = new Timer(1000, this);

		Start();
	}
	
	//��ʼ����Ϸ
	public void Start()
	{
		Dog.setIcon(new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/dog.png"));
		Ti.setZero();
		//�ڴ���������
		int ylen = 190;
		for(int i = 0; i < 20; i++)
		{
			int xlen = 50;
			for(int j = 0; j < 20; j++)
			{
				BA[i][j] = new button(i, j);
				button bu = BA[i][j];
				bu.addMouseListener(this);
				this.add(bu);
				bu.setBounds(xlen, ylen, 30, 30);
				xlen = xlen + 30;
			}
			ylen = ylen + 30;
		}
	}
	
	//���ݵ�һ�������λ�����ɵ��ײ������ʱ��
	public void SetMine(button bu)
	{
		tme.start();
		//������ɵ���λ��
		MineCount = 60;				//��������
		int a[] = new int[MineCount];	//��¼�����
		boolean Same;				//��¼�Ƿ����ظ��������
		//���������
		for(int i = 0; i < MineCount; i++)
		{		
			do
			{
				Same = false;
				a[i] = ra.nextInt(400);
				
				if(((a[i] / 20) == bu.x) && ((a[i] % 20) == bu.y))
					Same = true;
				
				for(int j = 0; j < i; j++)
				{
					if(a[j] == a[i])
						Same = true;
				}
			}while(Same);		
		}	
		//������������ɵ�������
		for(int i = 0; i < MineCount; i++)
		{
			int x = a[i] / 20;
			int y = a[i] - x * 20;
			BA[x][y].IsMine = true;
		}
		
		//���ݵ���λ�����������ո�����
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				if(!BA[i][j].IsMine)
				{
					int num = 0;
					//������Χ���׵�����
					for(int ix = i - 1; ix < i + 2; ix++)
					{
						if((ix >= 0) && (ix <= 19))
						{
							for(int jy = j - 1; jy < j+ 2; jy++)
							{
								if((jy >= 0) && (jy <= 19))
								{
									if(BA[ix][jy].IsMine)
									{
										num += 1;
									}
								}
							}
						}
					}
							
					BA[i][j].number = num;
				}
			}
		}
		
		bu.change_picture();
	}
	
	//����¼�����
	public void mouseClicked(MouseEvent e) 
	{          
		Object obj = e.getSource();
		
		//�����������ʱ
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			//��һ�ε��ʱ���ɵ��ײ���
			if((obj instanceof button) && IsFirst)
			{
				button bu = (button)obj;
				if(bu.IsClicked == 0)
				{
					SetMine(bu);
					
					if(bu.number == 0)
					{
						Dozero(bu);
					}
					IsFirst = false;
				}				
			}
			//����Ϸδ�����ҵ�����ǿհ�ʱ
			else if((obj instanceof button) && (!IsOver) && (!IsFirst))
	        {  
	        	button bu = (button)obj;
	        	if(bu.IsClicked == 0)
	        	{
	        		bu.IsClicked = 2;
		            bu.change_picture();
		            if(bu.number == 0)
		            {
		            	Dozero(bu);
		            }
		            if(bu.IsMine)
		            {
		            	Gameover();
		            	return;
		            }
	        	}	
	        }
	        //��������¿�ʼ��ťʱ����IsOverΪfalse�������岢�½�
	        else if(obj instanceof look)
	        {
	        	IsOver = false;
	        	IsFirst = true;
	        	for(int i = 0; i < 20; i++)
	        	{
	        		for(int j = 0; j < 20; j++)
	        		{
	        			remove(BA[i][j]);
	        		}
	        	}
	        	Start();
	        	return;
	        }
			
			//�����Ϸ�Ƿ�ʤ��
			Gamewin();
		}
		
		//������Ҽ����ʱ
		if((e.getButton() == MouseEvent.BUTTON3) && (obj instanceof button) && (!IsOver))
		{
			button bu = (button)obj;
			bu.change_picture_right();
		}	
	}
	
	//Ϊ��ʱ��������
	private void Dozero(button bu)
	{
		for(int i = bu.x - 1; i < bu.x + 2; i++)
		{
			if((i >= 0) && (i <= 19))
			{
				for(int j = bu.y - 1; j < bu.y+ 2; j++)
				{
					if((j >= 0) && (j <= 19))
					{
						if(((i != bu.x) || (j != bu.y)) && (BA[i][j].IsClicked == 0))
						{
							BA[i][j].IsClicked = 2;
							BA[i][j].change_picture();
							if(BA[i][j].number == 0)
							{
								Dozero(BA[i][j]);
							}
						}	
					}
				}
			}
		}
	}
	
	//�ȵ���ʱ������Ϸ
	private void Gameover()
	{
		tme.stop();
		IsOver = true;
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				if(BA[i][j].IsMine)
				{
					BA[i][j].change_picture();
				}
			}
		}
		Dog.setIcon(new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/dog_sleep.png"));
	}

	//��Ϸʤ�����
	private void Gamewin()
	{
		int count =0;		//��¼�Ѳ�����
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				if((!BA[i][j].IsMine) && (BA[i][j].IsClicked == 2))
				{
					count++;
				}
			}
		}
		//�����������ȫ���ѷ���������Ϸʤ��
		if(count == (400 - MineCount))
		{
			IsOver = true;
			Dog.setIcon(new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/dog_stand.jpg"));
			tme.stop();
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		Ti.addtime();
	}
	
	
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO �Զ����ɵķ������
		
	}

	
	
}
















