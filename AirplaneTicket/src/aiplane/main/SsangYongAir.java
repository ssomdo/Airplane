package aiplane.main;
import java.io.IOException;

// main �޼ҵ� ���� class 
public class SsangYongAir
{
	public static void main(String[] args) throws Exception
	{
		AirplaneSystem as = new AirplaneSystem();	// AirplaneSystem �ν��Ͻ��� �������� ������ ����� �� �����ϴ� (static�� �ƴϾ)
		as.create();
		as.addPlace(0);

		do
		{
			as.print();
			as.select();
		}
		while (true);								// �����ڰ� ���� ������ ���� ����	// ����ó�� ����� ���� ������ �������� ���� �� ����

	}
}


