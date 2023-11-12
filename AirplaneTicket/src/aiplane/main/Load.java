package aiplane.main;

public class Load {
	// 티켓 출력 시 loading 메소드
    public static void loadingTicket() 
	{
        String[] loadingStr = {"|", "/", "-", "\\"};
        for (int i = 0; i < 100; i++) 
		{	
            System.out.print("\r티켓 출력 중 " + loadingStr[i % loadingStr.length] + " " + (i + 1) + "%");	//\r 캐리지 리턴 문자 현재줄의 맨앞으로 가서 덮어쓰기
            try 
			{
                Thread.sleep(25); //25밀리초 
            } 
			catch (InterruptedException e) 
			{
                e.printStackTrace();
            }
        }
        System.out.println("\n\n티켓 출력 완료~!~!");
    }
	// 결제 시 loading 메소드
	public static void loadingPay() 
	{
        String[] loadingStr = {"|", "/", "-", "\\"};
        for (int i = 0; i < 100; i++) 
		{	
            System.out.print("\r결제 중 " + loadingStr[i % loadingStr.length] + " " + (i + 1) + "%");	//\r 캐리지 리턴 문자 현재줄의 맨앞으로 가서 덮어쓰기
            try 
			{
                Thread.sleep(10); //25밀리초 
            } 
			catch (InterruptedException e) 
			{
                e.printStackTrace();
            }
        }
        System.out.println("\n\n결제 완료~!~!");
    }
}









