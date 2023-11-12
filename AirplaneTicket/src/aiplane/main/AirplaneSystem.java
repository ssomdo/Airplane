package aiplane.main;

import java.io.*;

// 예약자 메인 화면 class
public class AirplaneSystem extends ReserveManagement
{
	int num; 
	boolean flag;

	// 예약자 메인 메뉴 출력 
	void print() throws IOException		
	{
		
		do
		{ 
			flag = false;
			System.out.println();
			System.out.println("────────────────────────────────────────────────────────────");
			System.out.println("\t\t\tSsangyong Air");
			System.out.println("────────────────────────────────────────────────────────────");
			System.out.println("\n환영합니다, 고객님.\n언제나 최상의 경험을 위해 노력하는 쌍용에어입니다.");
			System.out.println("키오스크를 통해 특가 항공권 예약 및 조회, 취소를\n간편하게 이용하실 수 있습니다.");
			System.out.print("\n\n아래의 메뉴에서 이용하실 서비스를 선택해주세요.\n");
			System.out.println("────────────────────────────────────────────────────────────");
			System.out.println(" 1. 예약");
			System.out.println(" 2. 조회");
			System.out.println(" 3. 취소");
			System.out.println("────────────────────────────────────────────────────────────");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
			System.out.print("\n서비스 선택 (번호 입력) : ");
	
			try
			{
				num = Integer.parseInt(br.readLine());			// 메인화면에서 예약, 조회, 취소 선택하면 전역변수에 입력값을 담음
			}
			catch (Exception e)
			{
				System.out.println("\n잘못된 입력입니다. 다시 입력해주세요.");
				flag = true;
			}
		}
		while (flag);
	}

	// 메뉴 선택
	void select() throws Exception
	{
		switch (num)										
		{
		case 1: reserve(); break;								// 예약		
		case 2: check(); break;									// 조회
		case 3: cancel(); break;								// 취소
		case 230817: isMgRight(0); break;						// 관리자모드
		default : System.out.println("\n잘못된 입력입니다. 다시 입력해주세요. "); break;
		}
	}

}