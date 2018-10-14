import javax.swing.*;  
import java.awt.event.*;  
import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MainFrame extends JFrame implements MouseListener, ActionListener
{
	public button[][] BA = new button[20][20];		//地雷阵列
	private boolean IsFirst;		//检测是不是第一次点击
	private boolean IsOver;			//检查游戏是否已结束
	private JLabel Dog;				//游戏结果
	private look SO;				//游戏开始按钮
	private timer Ti;				//时间显示
	private Timer tme;				//计时器
	private int MineCount;			//地雷数量
	private Random ra;				//随机器
	
	public MainFrame()
	{
		setResizable(false);		//设置窗口为固定大小
		getContentPane().setLayout(null);	//空布局
		getContentPane().setBackground(new Color(22, 24, 21));
		setSize(700,880);
		setLocationRelativeTo(null);		//屏幕中间显示
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
	
	//初始化游戏
	public void Start()
	{
		Dog.setIcon(new ImageIcon("F:/Programs/Java/Minesweeper/Picture resources/dog.png"));
		Ti.setZero();
		//在窗口中铺满
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
	
	//根据第一步点击的位置生成地雷布局与计时器
	public void SetMine(button bu)
	{
		tme.start();
		//随机生成地雷位置
		MineCount = 60;				//地雷数量
		int a[] = new int[MineCount];	//记录随机数
		boolean Same;				//记录是否有重复的随机数
		//生成随机数
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
		//根据随机数生成地雷坐标
		for(int i = 0; i < MineCount; i++)
		{
			int x = a[i] / 20;
			int y = a[i] - x * 20;
			BA[x][y].IsMine = true;
		}
		
		//根据地雷位置设置其他空格数字
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				if(!BA[i][j].IsMine)
				{
					int num = 0;
					//计算周围地雷的数量
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
	
	//鼠标事件处理
	public void mouseClicked(MouseEvent e) 
	{          
		Object obj = e.getSource();
		
		//当鼠标左键点击时
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			//第一次点击时生成地雷布局
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
			//当游戏未结束且点击的是空板时
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
	        //当点击重新开始按钮时，置IsOver为false，清除面板并新建
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
			
			//检查游戏是否胜利
			Gamewin();
		}
		
		//当鼠标右键点击时
		if((e.getButton() == MouseEvent.BUTTON3) && (obj instanceof button) && (!IsOver))
		{
			button bu = (button)obj;
			bu.change_picture_right();
		}	
	}
	
	//为零时连续翻开
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
	
	//踩到雷时结束游戏
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

	//游戏胜利检测
	private void Gamewin()
	{
		int count =0;		//记录已踩数量
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
		//如果除地雷外全部已翻开，则游戏胜利
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
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		
	}

	
	
}
















