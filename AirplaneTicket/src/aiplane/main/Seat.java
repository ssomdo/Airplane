package aiplane.main;

import java.util.ArrayList;
import java.io.Serializable;

// ����� ������ ��� class
public class Seat implements Serializable
{

	boolean[][] seatB = new boolean[10][4];						// �¼� ��Ȳ(false: ���� �� �� �¼�, true: ����� �¼�)
	PassengerInfo[][] seatP = new PassengerInfo[10][4];			// ž���� ����


	static ArrayList<int[]> tmpseat = new ArrayList<int[]>();	// ������ �¼��迭�� ���� ArrayList
	int seatLeft = seatB.length*seatB[0].length;				// �ܿ� �¼� ��
	

	String[][] strArr = { { "06:00", "07:00", "20:00" }, { "13:00", "18:00", "21:00" }, { "14:00", "15:00", "19:00" } };  // ���������� ��� �ð��� ���� �迭


	ArrayList<String[]> placeAndTime = new ArrayList<String[]>(); // �������� �ð��� ���� ArrayList
	ArrayList<String> whereToGo = new ArrayList<String>();		  // ��������(�ѱ�) ���� ArrayList
	ArrayList<String> englishName = new ArrayList<String>();	  // ��������(����) ���� ArrayList
	ArrayList<Integer> placeCost = new ArrayList<Integer>();	  // �������� ���� ���� ArrayList

	// �� Arraylist �ʱⰪ ����
	{									
		placeAndTime.add(strArr[0]);
		placeAndTime.add(strArr[1]);
		placeAndTime.add(strArr[2]);
		

		whereToGo.add("����");
		whereToGo.add("����");
		whereToGo.add("����");

		placeCost.add(100000);
		placeCost.add(350000);
		placeCost.add(200000);

		englishName.add("Tokyo");
		englishName.add("London");
		englishName.add("Bangkok");
	}


}