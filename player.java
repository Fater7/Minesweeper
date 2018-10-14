import java.util.Random;

public class player 
{
	private MainFrame MF;
	private Random rm;
	private boolean IsSuccess;
	
	public player(MainFrame M)
	{
		MF = M;
		rm = new Random();
		IsSuccess = false;
	}
	
	public void StartPlay()
	{
		
			MF.Start();			//游戏初始化
			button bu = GetRandomBlock();
			MF.SetMine(bu);	//获取随机块并铺设地雷
			
			//开始扫雷
		
	}
	
	private button GetRandomBlock()
	{
		int seedX, seedY;
		do
		{
			int blockseed = rm.nextInt(400);
			seedX = blockseed / 20;
			seedY = blockseed % 20;
		}while(MF.BA[seedX][seedY].IsClicked != 0);
		return MF.BA[seedX][seedY];
	}

}
