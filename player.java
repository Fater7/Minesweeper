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
		
			MF.Start();			//��Ϸ��ʼ��
			button bu = GetRandomBlock();
			MF.SetMine(bu);	//��ȡ����鲢�������
			
			//��ʼɨ��
		
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
