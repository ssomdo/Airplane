package aiplane.main;

public class Load {
	// Ƽ�� ��� �� loading �޼ҵ�
    public static void loadingTicket() 
	{
        String[] loadingStr = {"|", "/", "-", "\\"};
        for (int i = 0; i < 100; i++) 
		{	
            System.out.print("\rƼ�� ��� �� " + loadingStr[i % loadingStr.length] + " " + (i + 1) + "%");	//\r ĳ���� ���� ���� �������� �Ǿ����� ���� �����
            try 
			{
                Thread.sleep(25); //25�и��� 
            } 
			catch (InterruptedException e) 
			{
                e.printStackTrace();
            }
        }
        System.out.println("\n\nƼ�� ��� �Ϸ�~!~!");
    }
	// ���� �� loading �޼ҵ�
	public static void loadingPay() 
	{
        String[] loadingStr = {"|", "/", "-", "\\"};
        for (int i = 0; i < 100; i++) 
		{	
            System.out.print("\r���� �� " + loadingStr[i % loadingStr.length] + " " + (i + 1) + "%");	//\r ĳ���� ���� ���� �������� �Ǿ����� ���� �����
            try 
			{
                Thread.sleep(10); //25�и��� 
            } 
			catch (InterruptedException e) 
			{
                e.printStackTrace();
            }
        }
        System.out.println("\n\n���� �Ϸ�~!~!");
    }
}









