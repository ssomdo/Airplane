package aiplane.main;

/*=====================================
	< SSANGYONG AIR > 내일의 특가 비행기
=====================================*/



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Calendar;			
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Queue;
import java.util.LinkedList;

import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.util.Set;

import java.text.DecimalFormat;

import java.util.Collections;
import java.util.regex.Pattern;


// 예약자 및 관리자 기능 class
class ReserveManagement
{

	HashMap<String, Seat[][]> h = new HashMap<String, Seat[][]>();		//-- key : 날짜 / value : Seat[][]
	DecimalFormat decimalFormat = new DecimalFormat("#,###");			//-- 금액 출력시 000,000

	ArrayList<String[]> placeAndTime = new ArrayList<String[]>();		// 목적지별로 시간대 담을 ArrayList 
	ArrayList<String> whereToGo = new ArrayList<String>();				// 목적지명(한글) 담을 ArrayList   
	ArrayList<Integer> placeCost = new ArrayList<Integer>();			// 목적지명(영문) 담을 ArrayList   
	ArrayList<String> englishName = new ArrayList<String>();			// 목적지 별 가격 담을 ArrayList   

	boolean flag;
	boolean engFlag;
	int sel;

	// 관리자 비밀번호 일치 여부 확인 메소드
	void isMgRight(int n) 
	{
		int mgPW;														// 관리자 비밀번호
		int tmp=3;														// 입력 횟수 (총 3회)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		if (n==0)
		{
			System.out.println(">> 관리자 메뉴 이동 중...");
			do
			{
				try
				{
					System.out.printf("\n관리자 비밀번호를 입력해주세요.(제한 입력 횟수: %d회) : ",tmp--);
					mgPW = Integer.parseInt(br.readLine());

					if (mgPW == 230125)
					{
						mg_main();
						tmp=-1;
					}
					else
						System.out.println(">> 비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
				}
				catch (Exception e)
				{
					if (tmp==0)
						break;

					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");	
				}
				
			}
			while (tmp>0);
			if (tmp==0)
			{
				System.out.println(">> 메인으로 돌아갑니다.");
				try 
				{
					Thread.sleep(750); //25밀리초 
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
			return;
		}
		else if (n==-1)
		{
			return;
		}
	}


	// 관리자 메뉴 출력 메소드								
	void mg_main() throws IOException, ClassNotFoundException					
	{																

		do
		{ 
			System.out.println();
			System.out.println("────────────────────────────────────────────────────────────");
			System.out.println("\t\t\tSsangyong Air");
			System.out.println("────────────────────────────────────────────────────────────");
			System.out.println();
			System.out.println("1. 매출 관리");
			System.out.println("2. 목적지 추가");
			System.out.println("3. 목적지 수정");
			System.out.println("4. 데이터 저장");
			System.out.println("5. 프로그램 종료");
			System.out.println("────────────────────────────────────────────────────────────");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("\n이용하실 서비스를 선택해주세요. (『-1』 입력 → 예약자 메인화면) : ");
	
			try
			{
				sel = Integer.parseInt(br.readLine());					// 관리자 메뉴에서 넘어갈 서비스 선택
				if ((sel<1 || sel>5) && sel!=-1)
				{
					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
				sel=-5;
			}
		}
		while ((sel<1 || sel>5) && sel!=-1);
		
		if (sel==-1)													//-- -1 입력시 예약자 main(AirplaneSystem)으로 돌아감
		{
			isMgRight(-1);
		}
		else
			mg_select(sel);
	}

	// 관리자 메뉴 선택 메소드
	void mg_select(int sel) throws IOException, ClassNotFoundException
	{
		switch (sel)											
		{
		case 1: summaryPass(); break;									// 매출 관리
		case 2: addPlace(1); break;										// 목적지 추가
		case 3: change(); break;										// 목적지 수정
		case 4: save(); break;											// 데이터 저장
		case 5: out(); break;											// 프로그램 종료
		}
	}


	// hashmap 생성 및 객체 역직렬화 메소드  
	protected void create() throws IOException, ClassNotFoundException
	{

		String appDir = System.getProperty("user.dir");
		File f1 = new File(appDir + "\\DataBase\\");
		String[] fileNames = f1.list();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
		// 파일이 없을 시(처음 실행) -> 날짜(내일 ~ 7일 후)를 key값으로 한 hashmap 생성
		if (fileNames==null)
		{
			Date date = new Date();
			String[] data_str = new String[7];							// 일주일치 날짜를 담아둘 String 배열 (나중에 사용할 수 있도록 예비로 담아둠)
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);									// 오늘이 월요일이면 화요일부터 배열에 담음
			date = cal.getTime();

			for (int i = 0; i < 7; i++)
			{
				cal.setTime(date);
				cal.add(Calendar.DATE, 1);
				data_str[i] = formatter.format(date);
				date = cal.getTime();
			}

			
			for (int i=0; i < 7; i++)
			{
				Seat[][] st = new Seat[3][3];

				for (int k=0; k<3; k++)
				{
					for (int j=0; j<3; j++)
					{
						st[k][j] = new Seat();
					}
				}

				h.put(data_str[i], st);
			}
		}
		// 파일이 있을 시(처음 실행이 아님)-> 파일 불러오기(객체 역직렬화)
		else
		{
			Arrays.sort(fileNames);
			
			File f2 = new File(appDir + "\\DataBase\\" + fileNames[fileNames.length-1]);

			FileInputStream fis = new FileInputStream(f2);
			ObjectInputStream ois = new ObjectInputStream(fis);

			h = (HashMap)ois.readObject();
			

			ois.close();
			fis.close();

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 7);								
			Date date = calendar.getTime();
			String whereDate = formatter.format(date);					// 일주일 뒤 날짜

			Calendar calendar2 = Calendar.getInstance();
			Date date2 = calendar2.getTime();							
			String whereDate2 = formatter.format(date2);				// 오늘 날짜

			
			// 일주일 뒤 날짜를 key 값으로 하는 hashmap 이 없을 때 -> 오늘 날짜
			if (!h.containsKey(whereDate))
			{
				Seat[][] st = new Seat[h.get(whereDate2).length][h.get(whereDate2)[0].length];
				for (int k=0; k<h.get(whereDate2).length; k++)
				{
					for (int j=0; j<h.get(whereDate2)[k].length; j++)
					{
						st[k][j] = new Seat();
					}
				}
				h.put(whereDate,st);
			}
			else
				return;

			placeAndTime = h.get(whereDate2)[0][0].placeAndTime;
			whereToGo = h.get(whereDate2)[0][0].whereToGo;
			placeCost = h.get(whereDate2)[0][0].placeCost;
			englishName = h.get(whereDate2)[0][0].englishName;
				
		}
	}

	// 목적지 추가 메소드
	void addPlace(int n) throws IOException, ClassNotFoundException
	{
		// 프로그램 처음 실행시 매개변수 0 넘겨받아 실행
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String[] data_str = new String[7];
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		date = cal.getTime();

		for (int i = 0; i < 7; i++)
		{
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			data_str[i] = formatter.format(date);
			date = cal.getTime();
		}

		// hashmap 에 저장된 데이터 각 변수에 대입
		placeAndTime = h.get(data_str[0])[0][0].placeAndTime;				
		whereToGo = h.get(data_str[0])[0][0].whereToGo;
		placeCost = h.get(data_str[0])[0][0].placeCost;
		englishName = h.get(data_str[0])[0][0].englishName;
		
		
		// 목적지 추가 시 매개변수 1 넘겨받음
		if (n == 1)
		{	
			String tmpPlace="";											// 변경할 목적지명               
			String placeEng="";											// 새로운 목적지명(영어)                
			String[] tmpTime = {};										// 새로운 시간대 담을 배열         
			int tmpCost=0;												// 새로운 목적지 요금    						   
			String addPlaceYesNo = "n";									// 탑승자로부터 입력받을 값 (Y/N)
			boolean flag;

			do															       
			{
			
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
				//List<String> goToDesti = whereToGo;						// whereToGo(목적지 배열) list 로 변환(배열은 길이를 늘릴 수 없기 때문)

				System.out.println("\n\t\t <<목적지 리스트>>");
				System.out.println("───────────────────────────────────────────────────");
				for (int j=0; j<whereToGo.size(); j++)
				{
					System.out.printf("%4s", whereToGo.get(j));
				}
				System.out.println();
				System.out.println("───────────────────────────────────────────────────");

				do
				{
					flag=true;
					System.out.print("추가할 목적지를 한글명으로 입력해주세요. (『ctrl+z』입력 → 관리자 메인화면) : ");
					try
					{
						tmpPlace = br.readLine();
						flag = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", tmpPlace);

						if (!flag)
							System.out.println(">> 한글명으로 다시 입력해주세요.\n");
						else if ("".equals(tmpPlace))
						{
							System.out.println(">> 잘못된 입력입니다.다시 입력해주세요.");
							flag=false;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> 관리자 메인으로 돌아갑니다.");
						try 
						{
							Thread.sleep(750); //25밀리초 
						} 
						catch (InterruptedException c) 
						{
							e.printStackTrace();
						}
						mg_main();
						return;
					}
				}
				while (!flag);
				
				if (whereToGo.contains(tmpPlace))						// 입력 받은 목적지가 목적지 배열에 있는지 확인
				{
					System.out.println(">> 이미 존재하는 목적지 입니다. ");
					System.out.print(">> 새로운 목적지를 다시 입력 하시겠습니까?(Y/N)(『Y』 이외 입력 → 관리자 메인 화면) : ");
					String c = br.readLine();	
					if (c.equalsIgnoreCase("Y"))
					{
						addPlace(1);
						return;
					}
					else
					{
						System.out.println(">> 관리자 메인으로 돌아갑니다.");
						try 
						{
							Thread.sleep(750); //25밀리초 
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
						mg_main();
						return;
					}
					
				}

				do
				{
					flag=true;
					System.out.print("\n추가할 목적지를 영문명으로 입력해주세요. (『ctrl+z』입력 → 관리자 메인화면) : ");
					try
					{
						placeEng = br.readLine();
						flag = Pattern.matches("^[a-zA-Z]*$", placeEng);

						if (!flag)
							System.out.println(">> 영문명으로 다시 입력해주세요.");
						else if ("".equals(placeEng))
						{
							System.out.println(">> 잘못된 입력입니다.다시 입력해주세요.");
							flag=false;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> 관리자 메인으로 돌아갑니다.");
						try 
						{
							Thread.sleep(750); //25밀리초 
						} 
						catch (InterruptedException c) 
						{
							e.printStackTrace();
						}
						mg_main();
						return;
					}
				}
				while (!flag);
				
				
				do																	
				{		
					flag=false;																													
					System.out.print("\n추가할 목적지의 항공권 금액을 입력해주세요 : ");						
					try
					{
						tmpCost = Integer.parseInt(br.readLine());
						if (tmpCost < 35000 || tmpCost > 50000000)
						{
							System.out.print(">> 금액이 범위에 적합하지 않습니다. 다시 입력해주세요.");
							System.out.println();
							flag=true;
						}
					}
					catch (NumberFormatException e)
					{
						System.out.print(">> 잘못된 입력입니다.다시 입력해주세요.");
						System.out.println();
						flag=true;
					}
				}
				while (flag);						
					
				do
				{
					flag=false;
					System.out.print("\n추가할 목적지의 출발 시간을 입력해주세요.(공백 구분)(ex. 06:00 12:00 18:00) : ");
					try
					{
						tmpTime = br.readLine().split(" ");

						if (tmpTime.length!=3)
						{
							System.out.println(">> 잘못된 입력입니다.다시 입력해주세요.");
							flag=true;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> 잘못된 입력입니다.다시 입력해주세요.");
						flag=true;
					}
					
				} 
				while (flag);

				System.out.println("\n\n\t<<추가한 목적지 내용 확인>>");
				System.out.println("- 목적지(한글명) : " + tmpPlace);
				System.out.println("- 목적지(영문명) : " + placeEng);
				System.out.println("- 항공권 금액 : " + tmpCost);
				System.out.print("- 출발 시간 : ");
				for (int i=0 ; i<tmpTime.length ;i++)
				{
				   System.out.print(tmpTime[i]);
				   if (i!=(tmpTime.length-1))
				   {
					  System.out.print(", ");
				   }
				}
				System.out.println();

				System.out.print("\n확정하시겠습니까?\n");
				System.out.print(">> 확정 시 『Y』 입력해주세요. (『-1』입력→관리자 메인화면) : ");
				addPlaceYesNo = br.readLine();

				if (addPlaceYesNo.equals("-1"))
				{
					System.out.println(">> 관리자 메인으로 돌아갑니다.");
					try 
					{
						Thread.sleep(750); //25밀리초 
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					mg_main();
					return;
				}
				else if (!addPlaceYesNo.equalsIgnoreCase("Y"))
				{
				   System.out.println(">> 취소합니다. 추가할 목적지를 다시 입력해주세요.");
				   continue;
				}
				else
				{
					// ArrayList 에 입력 받은 데이터 추가
					placeAndTime.add(tmpTime);
					whereToGo.add(tmpPlace);
					placeCost.add(tmpCost);
					englishName.add(placeEng);
					
					

					// 새로 추가된 목적지방에 new Seat 클래스 대입
					//Seat[][] st= new Seat[whereToGo.size()][3];
					for (String day : data_str)
					{
						Seat[][] st= new Seat[whereToGo.size()][3];
						for (int i=0; i<h.get(day).length ; i++)
						{
							
							for (int k=0; k<h.get(day)[i].length; k++)
							{
								st[i][k] = h.get(day)[i][k];
								st[whereToGo.size()-1][k] = new Seat();
							}
						}
						h.put(day, st);
					}


					// hashmap 에 입력 받은 데이터 추가
					for (String day : data_str)
					{
						for (int i=0; i<h.get(day).length ; i++)
						{
							for (int k=0; k<h.get(day)[i].length; k++)
							{
								h.get(day)[i][k].placeAndTime = placeAndTime;
								h.get(day)[i][k].whereToGo = whereToGo;
								h.get(day)[i][k].placeCost = placeCost;
								h.get(day)[i][k].englishName = englishName;
							}
						}
					}

					System.out.printf(">> %s(%s)행 비행기 예약이 가능해졌습니다.\n", tmpPlace, placeEng);
				}

				
			}
			while (!addPlaceYesNo.equalsIgnoreCase("Y"));

			System.out.println(">> 관리자 메인으로 돌아갑니다.");
			try 
			{
				Thread.sleep(750); //25밀리초 
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			mg_main();
			return;

		}  
		else
			return;
		}



	// 목적지 변경 메소드
	public void change() throws IOException, ClassNotFoundException
	{	
		String oldPlace="";												// 변경할 목적지명
		String newPlace="";												// 새로운 목적지명
		String placeEng="";												// 새로운 목적지명(영어)
		int tmpCost=0;													// 새로운 목적지 요금
		int n=0;														// 목적지 배열 index
		String con = "Y";												// 탑승자로부터 입력 받을 값 (Y/N)
		String[] tmpTime = {};											// 새로운 시간대 담을 배열
		boolean flag;	

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//List<String> goToDesti = whereToGo;								

		System.out.println("\n\t\t <<목적지 리스트>>");
		System.out.println("───────────────────────────────────────────────────");
		for (int j=0; j<whereToGo.size(); j++)
		{
			System.out.printf("%4s", whereToGo.get(j));
		}
		System.out.println();
		System.out.println("───────────────────────────────────────────────────");

		do
		{
			flag=false;
			System.out.print("\n변경할 목적지를 한글명으로 입력해주세요. (『ctrl+z』입력 → 관리자 메인화면) : ");
			try
			{
				oldPlace = br.readLine();
				flag = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", oldPlace);

				if (!flag)
					System.out.println(">> 한글명으로 다시 입력해주세요.");
				else if ("".equals(oldPlace))
				{
					System.out.println(">> 잘못된 입력입니다.다시 입력해주세요.");
					flag=false;
				}
			}
			catch (Exception e)
			{
				System.out.println(">> 관리자 메인으로 돌아갑니다.");
				try 
				{
					Thread.sleep(750); //25밀리초 
				} 
				catch (InterruptedException c) 
				{
					e.printStackTrace();
				}
				mg_main();
				return;
			}
		}
		while (!flag);

		
		if (!whereToGo.contains(oldPlace))								// 입력 받은 목적지가 목적지 배열에 있는지 확인
		{
			System.out.println(">> 변경할 목적지가 존재하지 않습니다.");
			System.out.println(">> 관리자 메인으로 돌아갑니다.");
			try 
			{
				Thread.sleep(750); //25밀리초 
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			mg_main();
		}
		else
		{
			n = whereToGo.indexOf(oldPlace);
			int c=0;
			
			for (String day : h.keySet())
			{
				Seat[][] st = h.get(day);

				for (int k=0; k<st[n].length; k++)
				{
					boolean[][] b = st[n][k].seatB;

					for (int l=0; l<b.length; l++)
					{
						for (int m=0; m<b[l].length; m++)
						{
							if (b[l][m])
							{
								System.out.println(">> 변경이 불가능한 목적지 입니다.");
								System.out.println(">> 관리자 메인으로 돌아갑니다.");
								try 
								{
									Thread.sleep(750); //25밀리초 
								} 
								catch (InterruptedException e) 
								{
									e.printStackTrace();
								}
								mg_main();
								return;
							}
							else
								continue;
						}
					}
				}
			}

			do
			{
				flag=false;
				System.out.print("\n새로운 목적지를 한글명으로 입력해주세요. (『ctrl+z』입력 → 관리자 메인화면) : ");
				try
				{
					newPlace = br.readLine();
					flag = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", newPlace);

					if (!flag)
						System.out.println(">> 한글명으로 다시 입력해주세요.");
					else if ("".equals(newPlace))
					{
						System.out.println(">> 잘못된 입력입니다.다시 입력해주세요.");
						flag=false;
					}
				}
				catch (Exception e)
				{
					System.out.println(">> 관리자 메인으로 돌아갑니다.");
					try 
					{
						Thread.sleep(750); //25밀리초 
					} 
					catch (InterruptedException d) 
					{
						e.printStackTrace();
					}
					mg_main();
					return;
				}
			}
			while (!flag);
			

			if (whereToGo.contains(newPlace))							// 새로운 목적지가 배열에 존재하는 목적지인지 확인
			{
					System.out.println(">> 이미 존재하는 목적지 입니다.");
					
					System.out.println(">> 관리자 메인으로 돌아갑니다.");
					try 
					{
						Thread.sleep(750); //25밀리초 
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					mg_main();
					return;
			}

			do
			{
				System.out.print("\n새로운 목적지를 영문명으로 입력해주세요. (『ctrl+z』입력 → 관리자 메인화면) : ");
				try
				{
					placeEng = br.readLine();
					flag = Pattern.matches("^[a-zA-Z]*$", placeEng);

					if (!flag)
						System.out.println(">> 영문명으로 다시 입력해주세요.");
					else if ("".equals(placeEng))
					{
						System.out.println(">> 잘못된 입력입니다.다시 입력해주세요.");
						flag=false;
					}
				}
				catch (Exception e)
				{
					System.out.println(">> 관리자 메인으로 돌아갑니다.");
					try 
					{
						Thread.sleep(750); //25밀리초 
					} 
					catch (InterruptedException f) 
					{
						e.printStackTrace();
					}
					mg_main();
					return;
				}
			}
			while (!flag);


			do
			{
				flag=false;
				System.out.print("\n새로운 목적지의 출발 시간대를 입력해주세요.(공백 구분)(ex. 06:00 08:00 11:00P) : ");
				try
				{
					tmpTime = br.readLine().split(" ");

					if (tmpTime.length!=3)
					{
						System.out.print(">> 잘못된 입력입니다.다시 입력해주세요.");
						flag=true;
					}
				}
				catch (Exception e)
				{
					System.out.print(">> 잘못된 입력입니다.다시 입력해주세요.");
					flag=true;
				}
				
			} 
			while (flag);

			

			do																	
			{																	
				flag=false;															
				System.out.print("\n새로운 목적지의 항공권 금액을 입력해주세요. : ");						
				try
				{
					tmpCost = Integer.parseInt(br.readLine());
					if (tmpCost < 35000 || tmpCost > 50000000)
					{
						System.out.print(">> 금액이 범위에 적합하지 않습니다. 다시 입력해주세요.");
						System.out.println();
						flag=true;
					}
				}
				catch (NumberFormatException e)
				{
					System.out.print(">> 잘못된 입력입니다.다시 입력해주세요.");
					System.out.println();
					flag=true;
				}
			}
			while (flag);						


					
			do
			{
				try
				{									
					System.out.printf("\n%s를 %s(으)로 목적지를 변경하시겠습니까?(Y/N) : ", oldPlace, newPlace);
					con = br.readLine();
					if (con.equals("y") || con.equals("Y"))
					{
						
						whereToGo.set(n,newPlace);
						englishName.set(n, placeEng);
						placeAndTime.set(n, tmpTime);
						placeCost.set(n, tmpCost);

						
						System.out.print(">> 변경이 완료되었습니다.\n");
						mg_main();
					}
					else
					{
						System.out.print(">> 변경이 취소되었습니다.\n");
						System.out.println(">> 관리자 메인으로 돌아갑니다.");
						try 
						{
							Thread.sleep(750); //25밀리초 
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
						mg_main();
					}
				}
				catch (Exception e)
				{
					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");

				}
			}
			
			while (true);
		}	
	}	

	
	// 매출확인 메뉴 선택 메소드
	protected void summaryPass() throws IOException, ClassNotFoundException
	{	
		int choose;														// 메뉴 선택 숫자 변수
		boolean flag;

		do
		{ 
			System.out.println();
			System.out.println("────────────────────────────────────────────────────────────");
			System.out.println("\t\t\tSsangyong Air");
			System.out.println("────────────────────────────────────────────────────────────");
			System.out.println();
			System.out.println(" 1. 일자별 매출");
			System.out.println(" 2. 목적지별 매출");
			System.out.println("────────────────────────────────────────────────────────────");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("\n이용하실 서비스를 선택해주세요. (『-1』 입력 → 관리자 메인화면) : ");
	
			try
			{
				choose = Integer.parseInt(br.readLine());			
				if ((choose<1 || choose>2)&& choose !=-1)				// 메뉴에 있는 숫자와 -1 만 입력 가능하도록 함 (-1: 예약자 메뉴로 이동)
				{
					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
				choose=-5;
			}
		}
		while ((choose<1 || choose>2) && choose != -1);
		
		if (choose==-1)
		{
			System.out.println(">> 관리자 메인으로 돌아갑니다.");
			try 
			{
				Thread.sleep(750); //25밀리초 
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			mg_main();

			return;
		}
		else
			summary(choose);
		
	}

	// 매출 확인 메소드(메뉴 선택 시 입력 받은 숫자를 매개변수로 이용)
	void summary(int ch) throws IOException, ClassNotFoundException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		String[] fileNamesarr = new String[h.size()];					// 날짜를 담을 배열
		ArrayList<int[]> sumCost = new ArrayList<int[]>();				// 일자별 매출을 담을 ArrayList

		int i=0;
		int tmpcost;													// 일자별 매출
		int tmpcharge;													// 일자별 취소 수수료
		Seat[][] st;													// 해당 날짜의 비행기 전체 정보

		for (String key : h.keySet())
		{
			fileNamesarr[i] = key;										// fileNamesarr 배열에 날짜(hashmap key값) 담음
			i++;
		}
		
		Arrays.sort(fileNamesarr);										// 날짜 오름차순으로 정렬 (날짜 별로 순서대로 출력되기 위함)
		

		// 일자별 매출 관리
		if (ch == 1)
		{
			sumCost.clear();											// sumCost 값 초기화										
			for (String day : fileNamesarr)							
			{
				tmpcost=0;												// 날짜 바뀔 때 마다 초기화
				tmpcharge=0;
				st = h.get(day);

				for (int j=0; j<st.length; j++)
				{
					for (int k=0; k<st[j].length; k++)
					{
						PassengerInfo[][] p = st[j][k].seatP;
						boolean[][] b = st[j][k].seatB;

						for (int l=0; l<p.length; l++)
						{
							for (int m=0; m<p[l].length; m++)
							{
								try
								{
									if (b[l][m])
									{
										// 총 매출 = 예약금 + 위탁수하물 추가 금액 + 취소 수수료 금액
										tmpcost += p[l][m].getCost();			
										tmpcost += p[l][m].getBagCost();		
										tmpcost += p[l][m].getCharge();	
										tmpcharge += p[l][m].getCharge();		
									}
									else
										tmpcharge += p[l][m].getCharge();		
								}
								catch (NullPointerException e)
								{
									continue;
								}
							}
						}
					}
				}

				int[] tmpCost = {tmpcost, tmpcharge};					// 일자별 {매출, 취소수수료} 담은 배열
				sumCost.add(tmpCost);									// {매출, 취소수수료} 담은 배열을 Arraylist에 추가
			}

			System.out.println("\n\t<< 일자별 매출 >>");
			for (int n=0; n<sumCost.size(); n++)
			{
				System.out.printf("%s  : ", fileNamesarr[n]);

				int[] sc = sumCost.get(n);							
				for (int mn=0; mn<sc.length; mn++)
				{
					if (mn!=0)
					{
						System.out.printf("(취소 수수료 : %,d원 포함)\n", sc[mn]);
						continue;
					}
					System.out.printf("%,d원",sc[mn]);
				}
				
			}
			
			System.out.println("\n계속하려면 Press button...");
			try
			{
				String pause = br.readLine();							// 엔터 입력 시 관리자 메인 메뉴로 이동
				mg_main();
				return;
				
			}
			catch (IOException e)
			{
				mg_main();
				return;
			}

		}

		// 목적지별 매출 관리
		else if (ch == 2)
		{
			sumCost.clear();											// sumCost 값 초기화
			for (int j=0; j<whereToGo.size(); j++)
			{
				tmpcost=0;												// 날짜 바뀔 때 마다 초기화
				tmpcharge=0;

				for (String day : fileNamesarr)
				{
					st = h.get(day);
					
					for (int k=0; k<st[j].length; k++)
					{
						PassengerInfo[][] p = st[j][k].seatP;
						boolean[][] b = st[j][k].seatB;

						for (int l=0; l<p.length; l++)
						{
							for (int m=0; m<p[l].length; m++)
							{
								try
								{
									if (b[l][m])
									{
										// 총 매출 = 예약금 + 위탁수하물 추가 금액 + 취소 수수료 금액
										tmpcost += p[l][m].getCost();				
										tmpcost += p[l][m].getBagCost();			
										tmpcost += p[l][m].getCharge();
										tmpcharge += p[l][m].getCharge();		
									}
									else
										tmpcharge += p[l][m].getCharge();			
								}
								catch (NullPointerException e)
								{
									continue;
								}
							}
						}
					}
				}
			
				int[] tmpCost = {tmpcost, tmpcharge};					// 일자별 {매출, 취소수수료} 담은 배열            
				sumCost.add(tmpCost);									// {매출, 취소수수료} 담은 배열을 Arraylist에 추가
			}


			System.out.println("\n\t<< 목적지별 매출 >> ");
			for (int n=0; n<sumCost.size(); n++)
			{
				System.out.printf("%s : ",  whereToGo.get(n));

				int[] sc = sumCost.get(n);
				for (int mn=0; mn<sc.length; mn++)
				{
					if (mn!=0)
					{
						System.out.printf("(취소 수수료 : %,d원 포함)\n", sc[mn]);
						continue;
					}
					System.out.printf("%,d원",sc[mn]);
				}
				
			}
			System.out.println("\n계속하려면 Press button...");
			try
			{
				String pause = br.readLine();							// 엔터 입력 시 관리자 메인 메뉴로 이동
				mg_main();
				return;
				
			}
			catch (IOException e)
			{
				mg_main();
				return;
			}
		}
	}
	

	// 객체 직렬화 메소드 
	public void save() throws IOException, ClassNotFoundException
	{
		Calendar cal1 = Calendar.getInstance();					

		Integer d = cal1.get(Calendar.DATE);
		Integer m = cal1.get(Calendar.MONTH) + 1;
		Integer y = cal1.get(Calendar.YEAR);

		String t = String.format("%04d", y) + String.format("%02d", m) + String.format("%02d", d);	
		String time = t + ".ser";										

		String appDir = System.getProperty("user.dir");					
		File f1 = new File(appDir + "\\DataBase\\");					// "DataBase" 폴더 생성
		
		File f0 = new File(appDir +  "\\DataBase\\", time);				// 오늘 날짜를 파일명으로 하는 파일 생성

		if (!f0.getParentFile().exists())
		{
			f0.getParentFile().mkdirs();
		}

		FileOutputStream fos = new FileOutputStream(f0);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(h);												// 객체 직렬화
		oos.close();
		fos.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("\n프로그램을 종료하시겠습니까? (『Y』 이외 입력 → 관리자 메인 화면) : ");
		String outcheck = br.readLine();

		if (outcheck.equalsIgnoreCase("Y"))
			out();
		else
			mg_main();
	}
	

	// 프로그램 종료 메소드
	public static void out()
	{
		System.out.println("\n>> 프로그램 종료~!!!");
		System.exit(-1);
	}
	
// ===================== 관리자 관련 메소드 끝 =================================


// ===================== 예약자 관련 메소드 시작 ================================


	int num;														// 메인화면에서 예약, 조회, 취소 숫자 선택할 때 사용할 변수
	int idxPlace, idxTime;											// 목적지, 시간대 관련 배열에 해당하는 index
	int selDay;														// 예약자가 선택할 날짜(숫자) 
	String startDay;												// 예약자가 선택할 날짜(날짜 형식의 문자열)	
	String place;													// 예약자가 입력할 목적지 
	Queue<PassengerInfo> qu = new LinkedList<PassengerInfo>();		// 영수증을 출력할 때 사용할 변수. 지금 예약한 것만 Queue에 담아서 출력 (앞사람 영수증까지 같이 나오지 않도록)
	String tmpcard;													// 예약자가 입력할 카드번호
	String tmpcardpw;												// 예약자가 입력할 카드 비밀번호
	char[] tmpcd ;													// 카드번호 담을 문자배열
	String con="";													// 예약자가 입력할 문자(Y/N)
	


	void reserve() throws Exception
	{
		System.out.println("\n\n예약 서비스를 선택하셨습니다.");
		System.out.println("\n내일부터 다음주까지, 일주일의 특가 항공편을 보여드립니다.");
		System.out.println("원하시는 출국날짜를 선택 후 해당일의 항공편을 확인하실 수 있습니다.");


		Calendar now = Calendar.getInstance();						
		System.out.printf("\n현재 날짜 : %tF\n", now);				

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 날짜 출력(내일~ 일주일 후)

		System.out.println("─────────────────────────────");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String[] data_str = new String[7];							// 일주일치 날짜를 담아둘 String 배열 (나중에 사용할 수 있도록 예비로 담아둠)
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);									// 오늘이 월요일이면 화요일부터 출력하도록 함
		date = cal.getTime();
		
        for (int i = 0; i < 7; i++)
		{
            System.out.println((i+1)+". "+ formatter.format(date));
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            date = cal.getTime();
			data_str[i] = formatter.format(date);
		}
		System.out.println("─────────────────────────────");
		
		// 날짜 선택
		do
		{

			System.out.printf("\n날짜 선택 : ");
			try
			{
				selDay = Integer.parseInt(br.readLine()) ;				

				if (selDay < 1 || selDay > 7)
				{
					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");	
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요."); 
				selDay = -1;
			}
			
		}
		while (selDay<1 || selDay>7);							

		Calendar selectCal = Calendar.getInstance();					
		selectCal.add(Calendar.DATE, selDay);							

		Integer ddd = selectCal.get(Calendar.DATE);
		Integer mmm = selectCal.get(Calendar.MONTH) + 1;
		Integer yyy = selectCal.get(Calendar.YEAR);

		startDay = String.format("%04d", yyy) + "-" + String.format("%02d", mmm) + "-" + String.format("%02d", ddd);
		
		System.out.printf("\n\n>> 선택한 날짜 : %tF\n", selectCal);	

		// 목적지별 가격 출력
		do
		{
			System.out.println("\n\t\t 목적지별 가격");
			System.out.println("\t\t(Economy class)");
			System.out.println("───────────────────────────────────────────────────");
			for (int j=0; j<whereToGo.size(); j++)
			{
				System.out.printf("%4s : %,7d", whereToGo.get(j), placeCost.get(j));
				if (j!=0 && j%2==0 && j!=(whereToGo.size()-1))
				{
					System.out.println();
				}
			}
			System.out.println();
			System.out.println("───────────────────────────────────────────────────");

			// 목적지 선택
			System.out.printf("\n목적지 선택 (");
			for (int j=0; j<whereToGo.size(); j++)
			{
				System.out.printf("%s", whereToGo.get(j));
				if (j!=(whereToGo.size()-1))
				{
					System.out.print(", ");
				}
			}
			System.out.printf(") : ");
	
			place = br.readLine();										
			
			idxPlace = whereToGo.indexOf(place);
			
			if (idxPlace==-1)
			{
				System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");				
			}

		}
		while (idxPlace==-1);
		
		// 출발 시간대 선택 
		System.out.printf("\n%tF %s행 항공편 목록\n", selectCal, place);

		System.out.println("─────────────────────────────");
		for (int j=0; j<placeAndTime.get(idxPlace).length; j++)	
		{
			System.out.printf(" %d. %s\n", j+1, placeAndTime.get(idxPlace)[j]);			
		}
		System.out.println("─────────────────────────────");
		System.out.println();
		do
		{
			System.out.print("\n원하는 시간을 선택하세요 (번호 입력) : ");
			try
			{
				idxTime = Integer.parseInt(br.readLine()) - 1;			

				if (idxTime < 0 || idxTime > placeAndTime.size() - 1)
				{
					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
				idxTime = -1;
			}

		}
		while (idxTime<0 || idxTime>=placeAndTime.get(idxPlace).length);
		

		
		System.out.printf(">> %tF %s행 %s 비행기를 선택하셨습니다.\n\n", selectCal, place, placeAndTime.get(idxPlace)[idxTime]);

		// 좌석 선택 메소드 호출
		seatSelect();

		// 결제 메소드 호출 (결제 성공 → 1 반환)
		//					(카드번호 및 카드 비밀번호 입력 실패 → 0 반환)
		int b = payMent();												
	
		// 결제 성공 시 예약확정 여부 확인
		if (b == 1)
		{
			do
			{
				flag=false;
				try
				{
					System.out.print("\n예약을 확정하겠습니까?(Y/N) : ");
					con = br.readLine();
					if (!con.equalsIgnoreCase("Y") && !con.equalsIgnoreCase("N"))
					{
						flag=true;
					}
				}
				catch (Exception e)
				{
					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
					flag=true;
				}
			}
			while (flag);
			
			// 예약 확정하겠다고 할 때
			if (con.equals("Y") || con.equals("y"))
			{

				for (int[] s : Seat.tmpseat)		// passInfor()에서는 좌석 번호를 매개변수로 받아 좌석 정보를 바로 hashmap에 저장할 수 있었지만
													// payMent() 에서는 매개변수를 받지 않기 때문에 임시 좌석 배열(tmpseat)에 저장된 좌석을 이용한다.
				{
					// hashmap 의 cardNumber 변수(PassengerInfo)에 사용자 카드번호 넣기
					h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardNumber(tmpcard);
					// hashmap 의 cardPW 변수(PassengerInfo)에 사용자 카드 비밀번호 넣기
					h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardPW(tmpcardpw);
				}

				// print() 호출하여 영수증 출력
				print(place, placeAndTime.get(idxPlace)[idxTime]);
			}
			// 예약 확정 안하겠다고 할 때
			else
			{
				// qu(Queue) 에 담긴 탑승자 정보 비우기 
				qu.clear();			
				
				for (int[] s : Seat.tmpseat)
				{
					// tmpseat 에 담긴 모든 좌석의 boolean 값 false 대입
					h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
					// tmpseat 에 담긴 모든 좌석의 예약자 이름 null 대입
					h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
					// 해당 비행기 잔여좌석 수 증가
					++h.get(startDay)[idxPlace][idxTime].seatLeft;
					
				}
				
				do
				{
					flag=false;
					try
					{
						System.out.print("예약을 다시 하시겠습니까?(Y/N) : ");
						con = br.readLine();
						if (!con.equalsIgnoreCase("Y") && !con.equalsIgnoreCase("N"))
						{
							flag=true;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
						flag=true;
					}
				}
				while (flag);

				// 예약 다시 하겠다고 할 때 좌석 선택부터 다시 시행
				if (con.equalsIgnoreCase("Y"))
				{
					// 좌석선택 메소드 호출
					 seatSelect();
					// 결제 메소드 호출
					 b = payMent();

					 // 두번째 예약 결제 성공 시 예약 확정 여부 확인
					if (b == 1)
					{
						do
						{
							flag=false;
							try
							{
								System.out.print("예약을 확정하겠습니까?(Y/N : N(n) 입력시 메인으로 돌아갑니다.) : ");
								con = br.readLine();
								if (!con.equalsIgnoreCase("Y") && !con.equalsIgnoreCase("N"))
								{
									flag=true;
								}
							}
							catch (Exception e)
							{
								System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
								flag=true;
							}
						}
						while (flag);
						

						if (con.equals("Y") || con.equals("y"))
						{
							for (int[] s : Seat.tmpseat)
							{
								// hashmap 의 cardNumber 변수(PassengerInfo)에 사용자 카드번호 넣기
								h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardNumber(tmpcard);
								// hashmap 의 cardPW 변수(PassengerInfo)에 사용자 카드 비밀번호 넣기
								h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardPW(tmpcardpw);
							}
							// print() 호출하여 영수증 출력
							print(place, placeAndTime.get(idxPlace)[idxTime]);
						}
						// 두번째 예약 후 예약 확정 안하겠다고 할 때
						else
						{
							// qu(Queue) 에 담긴 탑승자 정보 비우기
							qu.clear();
							for (int[] s : Seat.tmpseat)
							{
								// tmpseat 에 담긴 모든 좌석의 boolean 값 false 대입
								h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
								// tmpseat 에 담긴 모든 좌석의 예약자 이름 null 대입
								h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
								// 해당 비행기 잔여좌석 수 증가
								++h.get(startDay)[idxPlace][idxTime].seatLeft;

								System.out.println(">> 메인 화면으로 돌아갑니다.");
								try 
								{
									Thread.sleep(750); //25밀리초 
								} 
								catch (InterruptedException e) 
								{
									e.printStackTrace();
								}
								return;
							}
						}
					}
					// 두번째 예약 후 결재 실패 했을 때
					
					// 예약 다시 안하겠다고 할 때 예약자 메인 이동
					else
					{
					
						// qu(Queue) 에 담긴 탑승자 정보 비우기
						qu.clear();
						for (int[] s : Seat.tmpseat)
						{
							// tmpseat 에 담긴 모든 좌석의 boolean 값 false 대입
							h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
							// tmpseat 에 담긴 모든 좌석의 예약자 이름 null 대입
							h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
							// 해당 비행기 잔여좌석 수 증가
							++h.get(startDay)[idxPlace][idxTime].seatLeft;
						}
						System.out.println(">> 메인 화면으로 돌아갑니다.");
						try 
						{
							Thread.sleep(750); //25밀리초 
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
						return;
					}
				}
				else
				{
					// qu(Queue) 에 담긴 탑승자 정보 비우기
					qu.clear();
					for (int[] s : Seat.tmpseat)
					{
						// tmpseat 에 담긴 모든 좌석의 boolean 값 false 대입
						h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
						// tmpseat 에 담긴 모든 좌석의 예약자 이름 null 대입
						h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
						// 해당 비행기 잔여좌석 수 증가
						++h.get(startDay)[idxPlace][idxTime].seatLeft;
					}
					System.out.println(">> 메인 화면으로 돌아갑니다.");
					try 
					{
						Thread.sleep(750); //25밀리초 
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					return;
				}
			}
		}
		else // 첫번째 예약 결제 실패 시 
		{
			qu.clear();
			for (int[] s : Seat.tmpseat)
			{
				// tmpseat 에 담긴 모든 좌석의 boolean 값 false 대입
				h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
				// tmpseat 에 담긴 모든 좌석의 예약자 이름 null 대입
				h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
				// 해당 비행기 잔여좌석 수 증가
				++h.get(startDay)[idxPlace][idxTime].seatLeft;
			}
			System.out.println(">> 메인 화면으로 돌아갑니다.");
			try 
			{
				Thread.sleep(750); //25밀리초 
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			return;														// 에약자 메인 이동
		}
	}
	
	// 좌석 선택 메소드 (hashmap 에 있는 비행기 정보(Seat[][]) 이용)
	void seatSelect() throws IOException 		
	{

		String seat;												// 예약자가 선택한 좌석 번호
		int row, col;												// 선택한 좌석의 열, 행 
		int inwon;													// 예약 인원  수


		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		Seat.tmpseat.clear();										// ArrayList tmpseat을 한 번 초기화 해야 전체 예약이 취소되지 않음

		
		// 예약 인원 수 입력 (잔여 좌석수 보다 적으며 최대 4명까지 예약 가능)
		System.out.printf("인원 수를 입력하세요. 한 번에 4명까지 예약이 가능합니다.(잔여 좌석 수: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);

		
		do
		{
			try
			{
				inwon = Integer.parseInt(br.readLine());

				if (inwon> h.get(startDay)[idxPlace][idxTime].seatLeft || inwon>4)
				{
					System.out.printf(">> 이용 가능한 좌석 수를 초과한 인원 수 입니다. 다시 입력해주세요.");
					System.out.printf("\n\n인원 수를 입력하세요. 한 번에 4명까지 예약이 가능합니다.(잔여 좌석 수: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);

					inwon=-1;
				}
				else if (inwon <1)
				{
					System.out.printf(">> 1 이상의 숫자로 다시 입력해주세요. ");
					System.out.printf("\n\n인원 수를 입력하세요. 한 번에 4명까지 예약이 가능합니다.(잔여 좌석 수: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);
				}
				else 
					break;
			}
			catch (NumberFormatException e)
			{
				System.out.printf(">> 올바르지 않은 입력입니다.다시 입력해주세요.");
				System.out.printf("\n\n인원 수를 입력하세요. 한 번에 4명까지 예약이 가능합니다.(잔여 좌석 수: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);

				inwon = -1;
			}
			
		}
		while (inwon <=-1);

		// 좌석 현황 출력
		for (int i=0; i<inwon ; i++)															// 인원수 만큼 좌석 선택 반복
		{
			do
			{
				System.out.print("\n좌석을 선택하세요(ex. A10)\n");
				System.out.print("(□ : 빈 좌석, ■ : 선택된 좌석)\n");
				System.out.printf("%6c  %3c%11c %3c\n",'A','B','C','D');
				System.out.println("  ▣ <-출입문");
				for (int j=0; j<h.get(startDay)[idxPlace][idxTime].seatB.length; j++)			// 해당 비행기 좌석의 총 좌석 열(row) 수
				{																		
					if (j%2==1)
					{
						System.out.print("│");													
						System.out.printf("%2d",j+1);
					}

					else
						System.out.printf("%3d",j+1);
					
					for (int k=0; k<h.get(startDay)[idxPlace][idxTime].seatB[j].length; k++)	// 해당 비행기 좌석의 총 좌석 행(col) 수
					{
						if (!h.get(startDay)[idxPlace][idxTime].seatB[j][k])					// 해당 비행기 좌석의 좌석 현황(true: 예약된 좌석, false: 예약안 된 좌석)			
						{
							System.out.printf("%3s","□");
							if (k==1)
							{
								System.out.print("        ");
							}
						}
						else if (h.get(startDay)[idxPlace][idxTime].seatB[j][k])	
						{
							System.out.printf("%3s","■");										// 예약된 좌석은 "■"로 표시
							if (k==1)
							{
								System.out.print("        ");
							}
						}
													
					}

					if (j%2==1)
					{
						System.out.print(" │");
					}
					System.out.println();
				}
				System.out.println();

				// 행(col) 별 좌석 등급 및 좌석 가격 안내
				System.out.printf("1~2 행 : First class (%,d원)\n",(int)(placeCost.get(idxPlace) * 1.5));			// first class : 목적지별 가격 * 1.5						  
				System.out.printf("3~5 행 : Business class (%,d원)\n",(int)(placeCost.get(idxPlace) * 1.3));		// Business class : 목적지별 가격 * 1.3
				System.out.printf("6~10 행  : Economy class(%,d원)\n",placeCost.get(idxPlace));						// Economy class : 목적지별 가격 * 1
				System.out.println();
				

				// 좌석 번호 입력
				System.out.printf("%d번째 좌석 선택(ex. A10) : ", i+1);						
				
				do
				{
					try
					{
						seat = br.readLine();													
					
						switch (seat.substring(0,1))																// 좌석번호 중 문자(A,B,C,D)만 빼냄
						{
						case "A": case "a": col = 0; break;
						case "B": case "b": col = 1; break;
						case "C": case "c": col = 2; break;
						case "D": case "d": col = 3; break;
						default : col = -1; break;

						}
						row = Integer.parseInt(seat.substring(1)) - 1;												// 좌석번호 중 숫자만 빼냄(인덱스로 이용)

						if (col==-1 || (row<0 || row>=h.get(startDay)[idxPlace][idxTime].seatB.length))						
						{
							System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
							System.out.printf("%d번째 좌석 선택 : ", i+1);				
						}
					}
					catch (Exception e)
					{
						System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
						System.out.printf("%d번째 좌석 선택 : ", i+1);
						
						row = col = -1;
					}
	
				}
				while(col == -1 || (row<0 || row>=h.get(startDay)[idxPlace][idxTime].seatB.length)); // 잘못된 입력일 경우 - 즉, 좌석 열이 a~d를 벗어나거나 행이 1~10을 벗어날 경우
								
			}
			while (seatCheck(row,col));		// seatCheck(row,col)가 true라면(선택된 좌석이면 반복)
											// seatCheck(row,col)가 false라면(비어있는 좌석이라면) 
			passInfor(row,col);				// 예약자 정보를 입력 받음

		}  
	}

	// 좌석 현황 확인 메소드
	boolean seatCheck(int row, int col)		
	{
		if (!h.get(startDay)[idxPlace][idxTime].seatB[row][col])						// 해당 비행기 좌석이 예약 안되어 있으면 (-> false 반환)
		{
			try
			{
				if (h.get(startDay)[idxPlace][idxTime].seatP[row][col].getName()==null)
				{
					h.get(startDay)[idxPlace][idxTime].seatB[row][col] = true;					// 해당 좌석 true로 바꿔줌.
					h.get(startDay)[idxPlace][idxTime].seatLeft--;
					System.out.println(">> 선택 가능한 좌석입니다.\n");
					int[] tmp = {row, col};														
					Seat.tmpseat.add(tmp);														// tmpseat에 저장해둠.
					return false;
				}
			}
			catch (NullPointerException e)
			{
				h.get(startDay)[idxPlace][idxTime].seatP[row][col] = new PassengerInfo();	// 해당 좌석에 PassengerInfo 인스턴스 생성하여 예약자 정보 담음.
				h.get(startDay)[idxPlace][idxTime].seatB[row][col] = true;					// 해당 좌석 true로 바꿔줌.
				h.get(startDay)[idxPlace][idxTime].seatLeft--;
				System.out.println(">> 선택 가능한 좌석입니다.\n");
				int[] tmp = {row, col};														
				Seat.tmpseat.add(tmp);														// tmpseat에 저장해둠.
				return false;
			}
			
		}
		else if (h.get(startDay)[idxPlace][idxTime].seatB[row][col])					// 해당 비행기 좌석이 예약되어 있으면 (-> true 반환)
		{
			System.out.println(">> 선택된 좌석입니다.\n");
			return true;
		}
		return true;
	}


	// 예약자 정보 입력 메소드(PassengerInfo)
	void passInfor(int row, int col) throws IOException
	{
		String tmpAns = "n";															// 예약자가 입력할 탑승정보 일치 여부(Y/N)
		String tmpName="";																// 예약자가 입력할 이름 
		String tmpEngLastName="";														// 예약자가 입력할 영문이름
		String tmpEngFirstName="";														// 예약자가 입력할 영문이름(성)
		String tmpTel="";																// 예약자가 입력할 전화번호
		String tmpBag;																	// 탑승자가 입력할 수하물 여부(Y/N)
		int tmpBagNum1=-1;																// 24~28 kg 수하물 수량
		int tmpBagNum2=-1;																// 28~32 kg 수하물 수량
		int tmpBagNum3=0;																// 총 수하물 수량
		int tmpBagSum=0;																// 총 수하물 추가 금액
		boolean flag=false;																
									
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 예약자 정보 입력(이름(한글명), 이름(영문명), 전화번호)
		System.out.println("<예약자 정보 입력>");
		do
		{
			do
			{
				try
				{
					System.out.print("\n성함을 입력하세요 : ");
					tmpName = br.readLine();
	
					if ("".equals(tmpName))
					{
						System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");	
					}
					else
					{
						flag = Pattern.matches("^[ㄱ-ㅎ가-힣]*$", tmpName);					// 한글만 입력 가능
						if (!flag)
								System.out.println(">> 한글명으로 다시 입력해주세요.");
					}
					
				}
				catch (Exception e)
				{
					
					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
				}
			}
			while (!flag);
			
			
			
			System.out.println("\n영문 이름을 입력하세요.");
			
			do
			{
				engFlag=true;
				try
				{
					System.out.print("Last Name(성): ");
					tmpEngLastName= br.readLine().toUpperCase();	

					if ("".equals(tmpEngLastName))
					{
						System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
					}
					else
						// 영어로만 입력되었는지 확인하는 메소드(checkEngName()) 호출 -> 맞으면 입력한 문자 반환, engFlag = false
						tmpEngLastName = checkEngName(tmpEngLastName);						
				
				}	
				catch (Exception e)
				{
					
					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
				}
			}while (engFlag);

			do
			{
				engFlag=true;
				try
				{
					System.out.print("First Name(이름): ");
					tmpEngFirstName= br.readLine().toUpperCase();	

					if ("".equals(tmpEngFirstName))
					{
						System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
					}
					else
						// 영어로만 입력되었는지 확인하는 메소드(checkEngName()) 호출 -> 맞으면 입력한 문자 반환, engFlag = false
						tmpEngFirstName = checkEngName(tmpEngFirstName);					
				
				}	
				catch (Exception e)
				{
					
					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
				}
			}while (engFlag);
			
		do
		{
			try
			{
				System.out.print("\n전화번호를 입력하세요 (XXX-XXXX-XXXX): ");
				tmpTel = br.readLine();
				flag = tmpTel.matches("\\d{3}-\\d{4}-\\d{4}");						// 전화번호 형식(xxx-xxxx-xxxx)만 입력 받음
				if (!flag)
				{
					System.out.println(">> 전화번호 형식에 맞게 입력해주세요");
					flag = false;
				}
			}
			catch (Exception e)
			{
				System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
			}
		}while (!flag);

			// 위탁수하물 추가 확인 
			do
			{
				flag=false;

				try
				{
					do
					{
						tmpBagSum = 0;
						System.out.print("\n추가할 위탁수하물이 있습니까?(Y/N) : ");	
						tmpBag = br.readLine();
					
						if (tmpBag.equals("y")||tmpBag.equals("Y"))
						{
							// 위탁수하물 무게별 금액 출력
							System.out.println("\n[위탁수하물 규정 : 무게별 추가금액]");
							System.out.println("=============================================");
							System.out.println("24 ~ 28kg : 40,000원");
							System.out.println("28 ~ 32kg : 60,000원");
							System.out.println("※ 32kg 초과시 수하 불가, 최대수량 5개 제한 ※");
							System.out.println("==============================================\n");
							do	
							{
								System.out.print("24kg 이상의 위탁수하물이 있으신가요?(Y/N) : ");
								tmpBag = br.readLine();

								if (tmpBag.equals("y")||tmpBag.equals("Y"))				// 24kg 이상의 위탁수하물이 있을 때
								{
									do
									{
										System.out.print("24 ~ 28kg 위탁수하물 수량을 입력해주세요 : ");
										try
										{
											tmpBagNum1 = Integer.parseInt(br.readLine());
											if (tmpBagNum1>5)
												System.out.println("※ 가능한 수량은 최대 5개 입니다 ※\n");	
										}
										catch (NumberFormatException e)
										{
											System.out.println(">> 숫자를 입력해주세요.\n");
										}
									}
									while (tmpBagNum1>5 || tmpBagNum1<0);	
									
									tmpBagSum += 40000 * tmpBagNum1;					// 24 ~28 kg 수하물 1개 추가요금 : 40000
									
									if (tmpBagNum1!=5)									// tmpBagNum1이 이미 최대 수량 5개를 채우면 28~32kg 의 수하물을 
																						// 받지 않고 바로 정보 확인으로 넘어가게 함
									{
										do
										{
											flag=true;
											System.out.print("28 ~ 32kg 위탁수하물 수량을 입력해주세요 : ");
											try
											{
												tmpBagNum2 = Integer.parseInt(br.readLine());
												
											}
											catch (NumberFormatException e)
											{
												System.out.println(">> 숫자를 입력해주세요.\n");
												flag = false;
											}
											if (flag)
											{
												tmpBagNum3 = tmpBagNum1 + tmpBagNum2;
												if (tmpBagNum2>5 || tmpBagNum3>5)
													System.out.printf("※ 가능한 수량은 최대 5개 입니다(현재 수량:%d) ※\n\n",tmpBagNum1);
											}
										}
										while (tmpBagNum2<0 || tmpBagNum2>5|| tmpBagNum3>5); // 총 위탁수하물 개수 최대 5개까지 받게함

										tmpBagSum += 60000 * tmpBagNum2;					 // 28 ~32 kg 수하물 1개 추가요금 : 40000

										flag = false;

										break;
									}
									else
									{
										tmpBagNum2 = 0;
										flag = false;
										break;
									}
								} 
								else if (tmpBag.equals("n")||tmpBag.equals("N"))			// 추가할 위탁 수하물 없다고 입력했을 때
								{
									tmpBagNum1 = 0;
									tmpBagNum2 = 0;
								}
							}
							while (!(tmpBag.equals("n")||tmpBag.equals("N")||tmpBag.equals("y")||tmpBag.equals("Y")));	//  n,N,y,Y 만 입력 가능
						
						}
						else if (tmpBag.equals("n")||tmpBag.equals("N"))												// 추가할 위탁수하물이 있을 때
						{
							tmpBagNum1 = 0;
							tmpBagNum2 = 0;
						}
						
					}
					while (!(tmpBag.equals("n")||tmpBag.equals("N")||tmpBag.equals("y")||tmpBag.equals("Y")));			//  n,N,y,Y 만 입력 가능
				}
				catch (Exception e)
				{
					flag=true;
				}
			}
			while (flag);
				
			// 위탁수하물 총 추가 금액 출력
			if (tmpBagSum!=0)
			{
				System.out.printf("\n위탁수하물 추가 요금은 총 %s원입니다.\n",decimalFormat.format(tmpBagSum));	
			}

			// 예약자 입력 내용 확인
			System.out.println("\n<< 예약자 정보 확인 >>");
			System.out.println("이름(한글명) : " + tmpName +"\n이름(영문명) : "+tmpEngFirstName+" "+tmpEngLastName+"\n전화번호 : " + tmpTel);
			System.out.println("24 ~ 28kg 위탁수하물 수량 : " + tmpBagNum1);	
			System.out.println("28 ~ 32kg 위탁수하물 수량 : " + tmpBagNum2);
			System.out.print("\n입력하신 정보가 맞습니까?(Y/N) : ");
			tmpAns = br.readLine();
			if (tmpAns.equalsIgnoreCase("Y"))
			{
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setName(tmpName);									// hashmap 의 name 변수(PassengerInfo)에 사용자 이름 넣기
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setEngFirstName(tmpEngFirstName);					// hashmap 의 engFirstName 변수(PassengerInfo)에 사용자 영문이름(이름)  넣기
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setEngLastName(tmpEngLastName);						// hashmap 의 engLastName 변수(PassengerInfo)에 사용자 영문이름(성) 넣기
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setTel(tmpTel);										// hashmap 의 IndexA 변수(PassengerInfo)에 사용자 영문 전화번호 넣기
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setBagCost(tmpBagSum);								// hashmap 의 IndexA 변수(PassengerInfo)에 사용자 추가 위탁수하물 금액 넣기
			}
			else
				System.out.println(">> 탑승정보를 다시 입력해주세요");

		} while (!(tmpAns.equalsIgnoreCase("Y")));


		// hashmap 의 indexA 변수(PassengerInfo)에 해당 좌석번호 넣기
		h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexA(row);

		//  hashmap 의 grade 변수(PassengerInfo)에 해당 좌석 등급 넣기
		int seatGrade = -1; 

		if (row >= 0 && row < 2) 
			{
			seatGrade = 0;
			} 
		else if (row >1  && row < 5) 
			{
			seatGrade = 1;
			} 
		else if (row > 4 && row < 10) 
			{
			seatGrade = 2;
			}
		
		switch (seatGrade)
		{
		case 0:h.get(startDay)[idxPlace][idxTime].seatP[row][col].setGrade("First");break;				
		case 1:h.get(startDay)[idxPlace][idxTime].seatP[row][col].setGrade("Business");break;			
		case 2:h.get(startDay)[idxPlace][idxTime].seatP[row][col].setGrade("Economy");break;			
		}

		// hashmap 의 serial 변수(PassengerInfo)에 해당 좌석 고유번호 넣기(좌석번호+목적지+출발시간+날짜)  
		switch (col)
		{
		case 0: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("A") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("A"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		case 1: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("B") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("B"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		case 2: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("C") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("C"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		case 3: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("D") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("D"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		default : h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("row") ; break;
		}

		// hashmap 의 cost 변수(PassengerInfo)에 해당 좌석 금액 넣기 
		switch(row)
		{
			case 0: case 1: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setCost((int)(placeCost.get(idxPlace) * 1.5));break;							
			case 2:case 3:case 4:  h.get(startDay)[idxPlace][idxTime].seatP[row][col].setCost((int)(placeCost.get(idxPlace) * 1.3));break;					
			case 5:case 6:case 7:case 8:case 9:  h.get(startDay)[idxPlace][idxTime].seatP[row][col].setCost(placeCost.get(idxPlace));break;	
		}

		// hashmap 의 depTime 변수(PassengerInfo)에 해당 좌석 출발 시간 넣기
		h.get(startDay)[idxPlace][idxTime].seatP[row][col].setDepTime(placeAndTime.get(idxPlace)[idxTime]);	

		// hashmap 의 desti 변수(PassengerInfo)에 해당 좌석 목적지(영문) 넣기
		h.get(startDay)[idxPlace][idxTime].seatP[row][col].setDesti(englishName.get(idxPlace));				
		
		// hashmap 에 저장된 정보 qu(Queue)에 추가하기 (영수증 출력 위함)
		qu.offer(h.get(startDay)[idxPlace][idxTime].seatP[row][col]);
		
	}

	// 입력 받은 값이 영문인지 확인하는 메소드
	public String checkEngName(String engName) 
	{
		if (engName.matches("^[a-zA-Z]*$")) 
		{
			String checkEng = engName.replaceAll("[^a-zA-Z]", "");
			this.engFlag = false;
			return checkEng;
		} 
		else
		{
			System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
			this.engFlag = true;
			return "";
		}
	}

	// 결제 메소드
	private int payMent() throws IOException
	{
		boolean flag;
		int checkCard=0;												//  카드
		int cdn;														//  입력받은 카드번호 한자리 숫자
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 카드번호 입력받아 카드 유효성 검사 시행
		do
		{
			flag=false;
			try
			{
				System.out.print("\n결제하실 카드 번호 16자리를 입력해주세요 : ");
				tmpcard = br.readLine();
				if ("".equals(tmpcard))
				{
					System.out.print(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
					flag=true;
				}
				else
				{
					tmpcd = tmpcard.toCharArray();						// 카드번호 문자배열에 담기


					// 16자리 숫자에서 맨 우측 수부터 세어 홀수 번째 수는 그대로 두고, 짝수 번째 수를 2배로 만든다.
					// 2배로 만든 짝수 번째 수가 10 이상인 경우, 각 자리의 숫자를 더하고 그 수로 대체한다.
					// 이와 같이 얻은 모든 자리의 수를 더한다.
					for (int i=tmpcd.length-2; i>=0; i--)
					{
						cdn = Character.getNumericValue(tmpcd[i]);
						if (i%2==0)
						{
							if (cdn*2>=10)
							{ 
								checkCard += ((cdn*2)%10)+1;
							}
							else
								checkCard += cdn*2;
						}
						else
							checkCard += cdn;
					}
				}
			}
			catch (Exception e)
			{
				System.out.print(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
				flag = true;

			}
		}
		while (flag);

		
		// 10에서 그 합(checkCard)을 나눈 나머지를 구하고, 그것을 10에서 뺀다.
		// 뺀 값이 카드번호의 마지막 숫자와 같으면 혹은 나머지가 10 이고 카드번호의 마지막 숫자가 0이라면
		// “정당한 번호”(유효)이고, 그렇지 않으면 “부당한 번호”(유효하지 않음)로 판정된다.

		// 카드번호가 16자리이고, 유효성 판정이 되었다면 → 카드 비밀번호 입력
		if (tmpcd.length==16 && (((10-(checkCard%10)) == Character.getNumericValue(tmpcd[tmpcd.length-1])) || ((10-(checkCard%10))==10 && Character.getNumericValue(tmpcd[tmpcd.length-1])==0)))
		{
			int breakn=0;												// 제한 입력 횟수 변수(최대 3회)
			do
			{
				try
				{
					if (breakn>=3)
					{
						return 0;
					}
					
					System.out.printf("카드 비밀번호 숫자 4자리를 입력해주세요.(제한 입력 횟수 : %d) : ",3-breakn);
					tmpcardpw = br.readLine();
			
					char[] tmpcdpw = tmpcardpw.toCharArray();
					
					if (tmpcdpw.length == 4)							// 입력한 비밀번호 숫자가 4자리 일 때 
					{
						try
						{
							for (char cdpw : tmpcdpw)
							{
								Character.getNumericValue(cdpw);		// 숫자만 입력 가능하도록 함
							}
							return 1;
						}
						catch (NumberFormatException e)
						{
							System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
						}
					}
					else
						System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
				}
				catch (Exception e)
				{
					System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.\n");
				}
				
				breakn++;												
			}
			while (breakn<4);
			return 1;													// 카드번호 및 카드 비밀번호 입력 성공 → 1리턴
		}
		// 잘못된 카드번호 입력 시
		else
		{
			System.out.println(">> 카드번호가 올바르지 않습니다.");
			System.out.print("다시 입력하시겠습니까?(Y/N)(『Y』 이외 입력 →메인 화면) : ");
			String re = br.readLine();

			if (re.equals("Y") || re.equals("y"))
			{
				return payMent();										// 결제 처음부터 다시 시행
			}
			else
				return 0;												// 카드번호 다시 입력 거부 시 → 0 리턴
		}
			
	}

	// 비행기 티켓 발권 정보 출력 메소드
	void print(String str, String time)
	{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Load.loadingPay();
		System.out.println("\n<<"+str + " 행 " + time + " 비행기 티켓 발권 정보>>");
		//qu(Queue)에 담겨있는 탑승 정보가 빌 때 까지 탑승 정보 출력
		while (!qu.isEmpty())
		{
			System.out.println("--------------------------------------------------------");
			System.out.printf("탑승객: %s\n탑승객(영문): %s-%s\n\n전화번호: %s\n좌석등급: %s\n좌석번호: %s%d\n티켓가격: %s원 + 위탁수하물 초과무게 비용: %s원\n고유번호: %s\n",
				qu.peek().getName(), qu.peek().getEngFirstName(), qu.peek().getEngLastName(), qu.peek().getTel(),qu.peek().getGrade(), qu.peek().getIndexB(), (qu.peek().getIndexA()+1), decimalFormat.format(qu.peek().getCost()),decimalFormat.format(qu.peek().getBagCost()),qu.peek().getSerial());
			System.out.print("카드 번호: ");
			char[] tmpcdpw = qu.peek().getCardNumber().toCharArray();
			for (int i=0; i<tmpcdpw.length; i++)
			{
				if ((i>=0 && i<4) || i>11)								// 카드번호 데이터 마스킹
				{
					System.out.print(tmpcdpw[i]);
				}
				else
					System.out.print("*");
			}
			System.out.println();
			System.out.println("--------------------------------------------------------\n\n");

			// 한 명의 탑승 정보 삭제 
			qu.poll();
			System.out.println("\n계속하려면 Press button...");			// 엔터 입력 시 메인 메뉴로 이동
			
			 try
			 {
				String pause = br.readLine();
			 }
			 catch (IOException e)
			 {
				return;													
			 }
		}

	}
	// 예약 조회 메소드
	void check() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String findRes="";													// 예약자가 입력한 고유번호
		int tmp=0;														// 고유번호 입력 횟수(최대 3회)

		do
		{
			try
			{
				// 고유번호 입력
				System.out.printf("\n고유번호를 입력해주세요.(제한 입력 횟수: %d회) : ", 3-tmp);
				findRes = br.readLine();

				// 고유번호 형식(문자 1자리+ 숫자 11자리) 에 맞는지 확인
				if (findRes.matches("[A-Za-z][0-9]{11}"))				 
				{
					// 존재하는 고유번호인지 확인하는 메소드 호출										
					break;
				}
				else
				{
					System.out.println(">> 고유번호 형식에 맞게 다시 입력해주세요.");
					tmp++;
				}
			
			}
			catch (Exception e)
			{
				System.out.println(">> 고유번호 형식에 맞게 다시 입력해주세요.");
				tmp++;
			}
		}
			while (tmp < 3);

		if (tmp==3)
		{
			System.out.println(">> 입력 횟수를 초과했습니다.");
			System.out.println(">> 메인 화면으로 돌아갑니다.");
			try 
			{
                Thread.sleep(750); //25밀리초 
            } 
			catch (InterruptedException e) 
			{
                e.printStackTrace();
            }
			return;
		}
		else
			check(findRes);	
	}

	// 예약 조회(존재하는 고유번호인지 확인) 메소드
	void check(String findRes) throws IOException	
	{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));										

		PassengerInfo passValue;										// 입력한 고유번호에 담겨있는 예약자 정보									
		
		int col=-1;
		switch (findRes.substring(0,1))									// 고유번호에 있는 좌석번호(문자)를 숫자로 변환
		{
		case "A" : col = 0; break;									
		case "B" : col = 1; break;
		case "C" : col = 2; break;
		case "D" : col = 3; break;
		}
		
		// 해당 고유번호에 예약자 정보있는지 확인
		// 입력한 고유번호를 추출하여 예약자 정보 얻어내기(h.get(날짜)[목적지][시간대][좌석의 열][좌석의 행]
		try
		{
			passValue = h.get(findRes.substring(4,8)+"-"+findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatP[Integer.parseInt(findRes.substring(1,2))][col];
			if (passValue.getName() == null)								// 탑승정보에 사용자 이름만 없을 때 (예약 했다가 취소했을 경우)
			{
				System.out.println(">> 정보가 존재하지 않습니다.");
				System.out.println(">> 메인 화면으로 돌아갑니다.");
				try 
				{
					Thread.sleep(750); //25밀리초 
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				return;
			}
			// 탑승정보 존재할 경우 비행기 티켓 출력
			else															
			{
				Load.loadingTicket();
				System.out.println();
				System.out.println("-----------------------------------------------------------------------------");
				System.out.print("\t\t\t\t\t\t\t\t eTicket  \n");
				System.out.println("-----------------------------------------------------------------------------");
				System.out.print(" SSANGYONG AIR,INC\n");
				System.out.println("\t\t\t\t\t"+findRes.substring(8,10)+"-"+findRes.substring(10));
				System.out.println(" Ticket Number: "+passValue.getSerial());	
				System.out.println();
				System.out.println(" Passenger: "+passValue.getName()+" / "+passValue.getEngFirstName()+" "+passValue.getEngLastName()+"\n");			
				System.out.print(" Departure: SEOUL INCHEON INT,KOREA REPUBLIC");
				System.out.println("\tClass:	"+passValue.getGrade());		
				System.out.print("            "+passValue.getDepTime());
				System.out.println("\t\t\t\tStatus: Confirmed");
				System.out.print("            TERMINAL 1");
				System.out.println("\t\t\t\tSeat: "+passValue.getIndexB()+(passValue.getIndexA()+1)+"\n\n");	
				System.out.println(" Arrival:   "+passValue.getDesti());		
				System.out.println("-----------------------------------------------------------------------------");
				System.out.println("\t\t\t\t\tCost + (excess Baggage fee): "+decimalFormat.format((passValue.getCost()+passValue.getBagCost()))); 
				System.out.println("-----------------------------------------------------------------------------\n");
				System.out.println("\n계속하려면 Press button...");

				 try
				 {
					String pause = br.readLine();							// 엔터 입력 시 메인 메뉴로 이동
				 }
				 catch (IOException e)
				 {
					return;
				 }
				
			}
		}
		catch (NullPointerException e)
		{
			System.out.println(">> 정보가 존재하지 않습니다.");			// 존재하지 않는 고유번호를 입력할 경우 NullPointerException이 일어남
			System.out.println(">> 메인 화면으로 돌아갑니다.");
			try 
			{
                Thread.sleep(750); //25밀리초 
            } 
			catch (InterruptedException c) 
			{
                e.printStackTrace();
            }
			return;
		}					
	}
	// 예약 취소하는 메소드
	void cancel() throws IOException
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = false;

		String findRes="Z";												// 예약자가 입력한 고유번호
		double charge=0;												// 취소 수수료
		
		int tmp=3;														// 고유번호 입력 횟수(최대 3회)
		PassengerInfo passValue;										// 입력한 고유번호에 담겨있는 예약자 정보
		
		// 고유번호 입력
		do
		{
			try
			{
				System.out.printf("\n고유번호를 입력해주세요.(제한 입력 횟수: %d회) : ",tmp);
				
				findRes = br.readLine();
				// 고유번호 형식(문자 1자리+숫자11자리)에 맞는지 확인
				if (findRes.matches("[A-Za-z][0-9]{11}"))				
				{
					break;
				}

				System.out.println(">> 고유번호 형식에 맞게 다시 입력해주세요.");
				tmp--;
			}
			catch (Exception e)
			{

				System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
				tmp--;
			}
			
		}
		while (tmp>0);

		if (tmp==0)
		{
			System.out.println(">> 입력 횟수를 초과하였습니다. 메인 화면으로 돌아갑니다.");
			System.out.println(">> 메인 화면으로 돌아갑니다.");
			try 
			{
                Thread.sleep(750); //25밀리초 
            } 
			catch (InterruptedException c) 
			{
                c.printStackTrace();
            }
			return;
		}
		

		int col=-1;
		switch (findRes.substring(0,1))									// 고유번호에 있는 좌석번호(문자)를 숫자로 변환
		{
		case "A" : col = 0; break;
		case "B" : col = 1; break;
		case "C" : col = 2; break;
		case "D" : col = 3; break;
		}

		// 해당 고유번호에 예약자 정보있는지 확인
		// 입력한 고유번호를 추출하여 예약자 정보 얻어내기(h.get(날짜)[목적지][시간대][좌석의 열][좌석의 행]
		try						
		{
			passValue = h.get(findRes.substring(4,8)+"-"+ findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatP[Integer.parseInt(findRes.substring(1,2))][col];
			
			if (passValue.getName() == null)								// 탑승정보에 사용자 이름만 없을 때 (예약 했다가 취소했을 경우)
			{
			System.out.println(">> 정보가 존재하지 않습니다.");
			System.out.println(">> 메인 화면으로 돌아갑니다.");
			try 
			{
				Thread.sleep(750); //25밀리초 
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			return;
			}
		}
		catch (Exception e )
		{
			System.out.println(">> 정보가 존재하지 않습니다.");		// 존재하지 않는 고유번호를 입력할 경우 NullPointerException이 일어남
			System.out.println(">> 메인 화면으로 돌아갑니다.");
			try 
			{
                Thread.sleep(750); //25밀리초 
            } 
			catch (InterruptedException c) 
			{
                c.printStackTrace();
            }
			return;
		}

		
		try
		{
			System.out.printf(">> %s님이 맞으십니까?(Y/N) : ", passValue.getName()); // 탑승정보에 사용자 이름만 없을 경우 (예약 했다가 취소했을 경우) NullPointerException이 일어남
		}
		catch (Exception e)
		{
			System.out.println(">> 고유번호가 일치하지 않거나 존재하지 않습니다.");
			System.out.println("고유번호를 다시 입력하시겠습니까?(Y/N)(『Y』 이외 입력 →메인 화면) : ");

			try
			{
				String con = br.readLine();

				if (con.equals("Y") || con.equals("y"))					
				{
					cancel();											
					return;
				}
				else													
				{
					System.out.print(">> 메인 화면으로 돌아갑니다.");
					try 
					{
						Thread.sleep(750); //25밀리초 
					} 
					catch (InterruptedException c) 
					{
						c.printStackTrace();
					}
					return;
				}	
			}
			catch (Exception row)
			{
				System.out.print(">> 메인 화면으로 돌아갑니다.");
				try 
				{
					Thread.sleep(750); //25밀리초 
				} 
				catch (InterruptedException c) 
				{
					c.printStackTrace();
				}
				return;
			}
		}
		
		try
		{
			
			String con = br.readLine();		
			// 해당 고유번호에 예약자 정보가 존재할 때 → 취소 수수료 안내
			if (con.equals("Y") || con.equals("y"))						
			{
				System.out.println("\n※ 취소시 예약금에서 수수료(예약금의 10%)를 제외한 금액이 환불됩니다. ※");

				charge = (passValue.getCost()+passValue.getBagCost()) * 0.1;			// 수수료 = (예약금+수하물 추가금액) *0.1
				
				System.out.printf("\n>> %s님이 예약 취소 시 환불 가능 금액은 %.0f원(수수료: %.0f원)입니다."
									, passValue.getName(),((passValue.getCost()+passValue.getBagCost())-charge), charge);
				
				do
				{
					flag = true;
					try
					{
						System.out.print("\n\n>> 정말로 예약을 취소하시겠습니까?(Y/N) : ");
						con = br.readLine();
						if (con.equalsIgnoreCase("y") || con.equalsIgnoreCase("n"))
						{
							flag = false;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
					}
				}
				while (flag);

				if (con.equals("Y") || con.equals("y"))
				{
					int l = 0;
					do
					{
						try
						{
							// 결제 카드 비밀번호 입력 (기회 최대 3번)
							System.out.print("결제 카드(");
							char[] tmpcdpw = passValue.getCardNumber().toCharArray();
							for (int i=0; i<tmpcdpw.length; i++)
							{
								if ((i>=0 && i<4) || i>11)
								{
									System.out.print(tmpcdpw[i]);
								}
								else
									System.out.print("*");				// 카드 데이터 마스킹
							}
							System.out.print(")의 비밀번호를 입력해주세요: ");
	
							String passwd = br.readLine();

							if (passwd.equals(passValue.getCardPW()))
							{
								System.out.println(">> 비밀번호가 일치합니다.");
								l=3;
							}
							else
								System.out.printf(">> 비밀번호가 일치하지 않습니다. (남은 기회: %d번)", (3-(++l)));
						}
						catch (Exception e)
						{
							System.out.printf(">> 비밀번호가 일치하지 않습니다. (남은 기회: %d번)", (3-(++l)));
						}
						
					
					}
					while (l<3);
				}
				else
				{
					System.out.println(">> 메인으로 돌아갑니다.");
					try 
					{
						Thread.sleep(750); //25밀리초 
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					return;
				}
			}
			else
				cancel();
		
		}
		catch (Exception e)
		{
			System.out.println(">> 메인으로 돌아갑니다.");
			try 
			{
				Thread.sleep(750); //25밀리초 
			} 
			catch (InterruptedException c) 
			{
				c.printStackTrace();
			}
			return;
		}
		System.out.printf(">> %s고객님의 %s행 %s 예약취소가 완료되었습니다.", passValue.getName(), passValue.getDesti(), passValue.getDepTime());
		System.out.println("안녕히 가십시오.");
		System.out.println();

		// 예약자 정보의 예약자 이름에 null 대입
		passValue.setName(null); 
		// 예약자 정보의 예약금 0으로 초기화
		passValue.setCost(0);
		// 예약자 정보의 기존 취소 수수료에서 현재 발생한 취소 수수료 더함
		passValue.setCharge(passValue.getCharge() + charge); 
		// hashmap 에서 해당 비행기 좌석 현황 false로 변경
		h.get(findRes.substring(4,8)+"-"+ 
		findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatB[Integer.parseInt(findRes.substring(1,2))][col] = false;
		// hashmap 에서 해당 비행기 잔여좌석 수 1 추가
		h.get(findRes.substring(4,8)+"-"+ findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatLeft++;
		try 
		{
			Thread.sleep(750); //25밀리초 
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		return;
	}

}