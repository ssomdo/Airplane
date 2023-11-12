package aiplane.main;

import java.io.*;

// ������ ���� ȭ�� class
public class AirplaneSystem extends ReserveManagement
{
	int num; 
	boolean flag;

	// ������ ���� �޴� ��� 
	void print() throws IOException		
	{
		
		do
		{ 
			flag = false;
			System.out.println();
			System.out.println("������������������������������������������������������������������������������������������������������������������������");
			System.out.println("\t\t\tSsangyong Air");
			System.out.println("������������������������������������������������������������������������������������������������������������������������");
			System.out.println("\nȯ���մϴ�, ����.\n������ �ֻ��� ������ ���� ����ϴ� �ֿ뿡���Դϴ�.");
			System.out.println("Ű����ũ�� ���� Ư�� �װ��� ���� �� ��ȸ, ��Ҹ�\n�����ϰ� �̿��Ͻ� �� �ֽ��ϴ�.");
			System.out.print("\n\n�Ʒ��� �޴����� �̿��Ͻ� ���񽺸� �������ּ���.\n");
			System.out.println("������������������������������������������������������������������������������������������������������������������������");
			System.out.println(" 1. ����");
			System.out.println(" 2. ��ȸ");
			System.out.println(" 3. ���");
			System.out.println("������������������������������������������������������������������������������������������������������������������������");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
			System.out.print("\n���� ���� (��ȣ �Է�) : ");
	
			try
			{
				num = Integer.parseInt(br.readLine());			// ����ȭ�鿡�� ����, ��ȸ, ��� �����ϸ� ���������� �Է°��� ����
			}
			catch (Exception e)
			{
				System.out.println("\n�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				flag = true;
			}
		}
		while (flag);
	}

	// �޴� ����
	void select() throws Exception
	{
		switch (num)										
		{
		case 1: reserve(); break;								// ����		
		case 2: check(); break;									// ��ȸ
		case 3: cancel(); break;								// ���
		case 230817: isMgRight(0); break;						// �����ڸ��
		default : System.out.println("\n�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���. "); break;
		}
	}

}