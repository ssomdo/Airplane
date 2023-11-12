package aiplane.main;
import java.io.IOException;

// main 메소드 실행 class 
public class SsangYongAir
{
	public static void main(String[] args) throws Exception
	{
		AirplaneSystem as = new AirplaneSystem();	// AirplaneSystem 인스턴스를 생성하지 않으면 사용할 수 없습니다 (static이 아니어서)
		as.create();
		as.addPlace(0);

		do
		{
			as.print();
			as.select();
		}
		while (true);								// 관리자가 끄기 전까지 무한 루프	// 예외처리 제대로 하지 않으면 오류나서 꺼질 수 있음

	}
}


