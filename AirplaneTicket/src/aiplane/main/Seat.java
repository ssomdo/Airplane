package aiplane.main;

import java.util.ArrayList;
import java.io.Serializable;

// 비행기 정보가 담긴 class
public class Seat implements Serializable
{

	boolean[][] seatB = new boolean[10][4];						// 좌석 현황(false: 예약 안 된 좌석, true: 예약된 좌석)
	PassengerInfo[][] seatP = new PassengerInfo[10][4];			// 탑승자 정보


	static ArrayList<int[]> tmpseat = new ArrayList<int[]>();	// 선택한 좌석배열을 담은 ArrayList
	int seatLeft = seatB.length*seatB[0].length;				// 잔여 좌석 수
	

	String[][] strArr = { { "06:00", "07:00", "20:00" }, { "13:00", "18:00", "21:00" }, { "14:00", "15:00", "19:00" } };  // 목적지별로 출발 시간대 담은 배열


	ArrayList<String[]> placeAndTime = new ArrayList<String[]>(); // 목적지별 시간대 담은 ArrayList
	ArrayList<String> whereToGo = new ArrayList<String>();		  // 목적지명(한글) 담은 ArrayList
	ArrayList<String> englishName = new ArrayList<String>();	  // 목적지명(영문) 담은 ArrayList
	ArrayList<Integer> placeCost = new ArrayList<Integer>();	  // 목적지별 가격 담은 ArrayList

	// 각 Arraylist 초기값 설정
	{									
		placeAndTime.add(strArr[0]);
		placeAndTime.add(strArr[1]);
		placeAndTime.add(strArr[2]);
		

		whereToGo.add("도쿄");
		whereToGo.add("런던");
		whereToGo.add("방콕");

		placeCost.add(100000);
		placeCost.add(350000);
		placeCost.add(200000);

		englishName.add("Tokyo");
		englishName.add("London");
		englishName.add("Bangkok");
	}


}