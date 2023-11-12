package aiplane.main;

/*=====================================
	< SSANGYONG AIR > ������ Ư�� �����
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


// ������ �� ������ ��� class
class ReserveManagement
{

	HashMap<String, Seat[][]> h = new HashMap<String, Seat[][]>();		//-- key : ��¥ / value : Seat[][]
	DecimalFormat decimalFormat = new DecimalFormat("#,###");			//-- �ݾ� ��½� 000,000

	ArrayList<String[]> placeAndTime = new ArrayList<String[]>();		// ���������� �ð��� ���� ArrayList 
	ArrayList<String> whereToGo = new ArrayList<String>();				// ��������(�ѱ�) ���� ArrayList   
	ArrayList<Integer> placeCost = new ArrayList<Integer>();			// ��������(����) ���� ArrayList   
	ArrayList<String> englishName = new ArrayList<String>();			// ������ �� ���� ���� ArrayList   

	boolean flag;
	boolean engFlag;
	int sel;

	// ������ ��й�ȣ ��ġ ���� Ȯ�� �޼ҵ�
	void isMgRight(int n) 
	{
		int mgPW;														// ������ ��й�ȣ
		int tmp=3;														// �Է� Ƚ�� (�� 3ȸ)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		if (n==0)
		{
			System.out.println(">> ������ �޴� �̵� ��...");
			do
			{
				try
				{
					System.out.printf("\n������ ��й�ȣ�� �Է����ּ���.(���� �Է� Ƚ��: %dȸ) : ",tmp--);
					mgPW = Integer.parseInt(br.readLine());

					if (mgPW == 230125)
					{
						mg_main();
						tmp=-1;
					}
					else
						System.out.println(">> ��й�ȣ�� ��ġ���� �ʽ��ϴ�. �ٽ� �Է����ּ���.");
				}
				catch (Exception e)
				{
					if (tmp==0)
						break;

					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");	
				}
				
			}
			while (tmp>0);
			if (tmp==0)
			{
				System.out.println(">> �������� ���ư��ϴ�.");
				try 
				{
					Thread.sleep(750); //25�и��� 
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


	// ������ �޴� ��� �޼ҵ�								
	void mg_main() throws IOException, ClassNotFoundException					
	{																

		do
		{ 
			System.out.println();
			System.out.println("������������������������������������������������������������������������������������������������������������������������");
			System.out.println("\t\t\tSsangyong Air");
			System.out.println("������������������������������������������������������������������������������������������������������������������������");
			System.out.println();
			System.out.println("1. ���� ����");
			System.out.println("2. ������ �߰�");
			System.out.println("3. ������ ����");
			System.out.println("4. ������ ����");
			System.out.println("5. ���α׷� ����");
			System.out.println("������������������������������������������������������������������������������������������������������������������������");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("\n�̿��Ͻ� ���񽺸� �������ּ���. (��-1�� �Է� �� ������ ����ȭ��) : ");
	
			try
			{
				sel = Integer.parseInt(br.readLine());					// ������ �޴����� �Ѿ ���� ����
				if ((sel<1 || sel>5) && sel!=-1)
				{
					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				sel=-5;
			}
		}
		while ((sel<1 || sel>5) && sel!=-1);
		
		if (sel==-1)													//-- -1 �Է½� ������ main(AirplaneSystem)���� ���ư�
		{
			isMgRight(-1);
		}
		else
			mg_select(sel);
	}

	// ������ �޴� ���� �޼ҵ�
	void mg_select(int sel) throws IOException, ClassNotFoundException
	{
		switch (sel)											
		{
		case 1: summaryPass(); break;									// ���� ����
		case 2: addPlace(1); break;										// ������ �߰�
		case 3: change(); break;										// ������ ����
		case 4: save(); break;											// ������ ����
		case 5: out(); break;											// ���α׷� ����
		}
	}


	// hashmap ���� �� ��ü ������ȭ �޼ҵ�  
	protected void create() throws IOException, ClassNotFoundException
	{

		String appDir = System.getProperty("user.dir");
		File f1 = new File(appDir + "\\DataBase\\");
		String[] fileNames = f1.list();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
		// ������ ���� ��(ó�� ����) -> ��¥(���� ~ 7�� ��)�� key������ �� hashmap ����
		if (fileNames==null)
		{
			Date date = new Date();
			String[] data_str = new String[7];							// ������ġ ��¥�� ��Ƶ� String �迭 (���߿� ����� �� �ֵ��� ����� ��Ƶ�)
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);									// ������ �������̸� ȭ���Ϻ��� �迭�� ����
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
		// ������ ���� ��(ó�� ������ �ƴ�)-> ���� �ҷ�����(��ü ������ȭ)
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
			String whereDate = formatter.format(date);					// ������ �� ��¥

			Calendar calendar2 = Calendar.getInstance();
			Date date2 = calendar2.getTime();							
			String whereDate2 = formatter.format(date2);				// ���� ��¥

			
			// ������ �� ��¥�� key ������ �ϴ� hashmap �� ���� �� -> ���� ��¥
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

	// ������ �߰� �޼ҵ�
	void addPlace(int n) throws IOException, ClassNotFoundException
	{
		// ���α׷� ó�� ����� �Ű����� 0 �Ѱܹ޾� ����
		
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

		// hashmap �� ����� ������ �� ������ ����
		placeAndTime = h.get(data_str[0])[0][0].placeAndTime;				
		whereToGo = h.get(data_str[0])[0][0].whereToGo;
		placeCost = h.get(data_str[0])[0][0].placeCost;
		englishName = h.get(data_str[0])[0][0].englishName;
		
		
		// ������ �߰� �� �Ű����� 1 �Ѱܹ���
		if (n == 1)
		{	
			String tmpPlace="";											// ������ ��������               
			String placeEng="";											// ���ο� ��������(����)                
			String[] tmpTime = {};										// ���ο� �ð��� ���� �迭         
			int tmpCost=0;												// ���ο� ������ ���    						   
			String addPlaceYesNo = "n";									// ž���ڷκ��� �Է¹��� �� (Y/N)
			boolean flag;

			do															       
			{
			
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
				//List<String> goToDesti = whereToGo;						// whereToGo(������ �迭) list �� ��ȯ(�迭�� ���̸� �ø� �� ���� ����)

				System.out.println("\n\t\t <<������ ����Ʈ>>");
				System.out.println("������������������������������������������������������������������������������������������������������");
				for (int j=0; j<whereToGo.size(); j++)
				{
					System.out.printf("%4s", whereToGo.get(j));
				}
				System.out.println();
				System.out.println("������������������������������������������������������������������������������������������������������");

				do
				{
					flag=true;
					System.out.print("�߰��� �������� �ѱ۸����� �Է����ּ���. (��ctrl+z���Է� �� ������ ����ȭ��) : ");
					try
					{
						tmpPlace = br.readLine();
						flag = Pattern.matches("^[��-����-�R]*$", tmpPlace);

						if (!flag)
							System.out.println(">> �ѱ۸����� �ٽ� �Է����ּ���.\n");
						else if ("".equals(tmpPlace))
						{
							System.out.println(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
							flag=false;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> ������ �������� ���ư��ϴ�.");
						try 
						{
							Thread.sleep(750); //25�и��� 
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
				
				if (whereToGo.contains(tmpPlace))						// �Է� ���� �������� ������ �迭�� �ִ��� Ȯ��
				{
					System.out.println(">> �̹� �����ϴ� ������ �Դϴ�. ");
					System.out.print(">> ���ο� �������� �ٽ� �Է� �Ͻðڽ��ϱ�?(Y/N)(��Y�� �̿� �Է� �� ������ ���� ȭ��) : ");
					String c = br.readLine();	
					if (c.equalsIgnoreCase("Y"))
					{
						addPlace(1);
						return;
					}
					else
					{
						System.out.println(">> ������ �������� ���ư��ϴ�.");
						try 
						{
							Thread.sleep(750); //25�и��� 
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
					System.out.print("\n�߰��� �������� ���������� �Է����ּ���. (��ctrl+z���Է� �� ������ ����ȭ��) : ");
					try
					{
						placeEng = br.readLine();
						flag = Pattern.matches("^[a-zA-Z]*$", placeEng);

						if (!flag)
							System.out.println(">> ���������� �ٽ� �Է����ּ���.");
						else if ("".equals(placeEng))
						{
							System.out.println(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
							flag=false;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> ������ �������� ���ư��ϴ�.");
						try 
						{
							Thread.sleep(750); //25�и��� 
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
					System.out.print("\n�߰��� �������� �װ��� �ݾ��� �Է����ּ��� : ");						
					try
					{
						tmpCost = Integer.parseInt(br.readLine());
						if (tmpCost < 35000 || tmpCost > 50000000)
						{
							System.out.print(">> �ݾ��� ������ �������� �ʽ��ϴ�. �ٽ� �Է����ּ���.");
							System.out.println();
							flag=true;
						}
					}
					catch (NumberFormatException e)
					{
						System.out.print(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
						System.out.println();
						flag=true;
					}
				}
				while (flag);						
					
				do
				{
					flag=false;
					System.out.print("\n�߰��� �������� ��� �ð��� �Է����ּ���.(���� ����)(ex. 06:00 12:00 18:00) : ");
					try
					{
						tmpTime = br.readLine().split(" ");

						if (tmpTime.length!=3)
						{
							System.out.println(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
							flag=true;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
						flag=true;
					}
					
				} 
				while (flag);

				System.out.println("\n\n\t<<�߰��� ������ ���� Ȯ��>>");
				System.out.println("- ������(�ѱ۸�) : " + tmpPlace);
				System.out.println("- ������(������) : " + placeEng);
				System.out.println("- �װ��� �ݾ� : " + tmpCost);
				System.out.print("- ��� �ð� : ");
				for (int i=0 ; i<tmpTime.length ;i++)
				{
				   System.out.print(tmpTime[i]);
				   if (i!=(tmpTime.length-1))
				   {
					  System.out.print(", ");
				   }
				}
				System.out.println();

				System.out.print("\nȮ���Ͻðڽ��ϱ�?\n");
				System.out.print(">> Ȯ�� �� ��Y�� �Է����ּ���. (��-1���Է¡������ ����ȭ��) : ");
				addPlaceYesNo = br.readLine();

				if (addPlaceYesNo.equals("-1"))
				{
					System.out.println(">> ������ �������� ���ư��ϴ�.");
					try 
					{
						Thread.sleep(750); //25�и��� 
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
				   System.out.println(">> ����մϴ�. �߰��� �������� �ٽ� �Է����ּ���.");
				   continue;
				}
				else
				{
					// ArrayList �� �Է� ���� ������ �߰�
					placeAndTime.add(tmpTime);
					whereToGo.add(tmpPlace);
					placeCost.add(tmpCost);
					englishName.add(placeEng);
					
					

					// ���� �߰��� �������濡 new Seat Ŭ���� ����
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


					// hashmap �� �Է� ���� ������ �߰�
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

					System.out.printf(">> %s(%s)�� ����� ������ �����������ϴ�.\n", tmpPlace, placeEng);
				}

				
			}
			while (!addPlaceYesNo.equalsIgnoreCase("Y"));

			System.out.println(">> ������ �������� ���ư��ϴ�.");
			try 
			{
				Thread.sleep(750); //25�и��� 
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



	// ������ ���� �޼ҵ�
	public void change() throws IOException, ClassNotFoundException
	{	
		String oldPlace="";												// ������ ��������
		String newPlace="";												// ���ο� ��������
		String placeEng="";												// ���ο� ��������(����)
		int tmpCost=0;													// ���ο� ������ ���
		int n=0;														// ������ �迭 index
		String con = "Y";												// ž���ڷκ��� �Է� ���� �� (Y/N)
		String[] tmpTime = {};											// ���ο� �ð��� ���� �迭
		boolean flag;	

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//List<String> goToDesti = whereToGo;								

		System.out.println("\n\t\t <<������ ����Ʈ>>");
		System.out.println("������������������������������������������������������������������������������������������������������");
		for (int j=0; j<whereToGo.size(); j++)
		{
			System.out.printf("%4s", whereToGo.get(j));
		}
		System.out.println();
		System.out.println("������������������������������������������������������������������������������������������������������");

		do
		{
			flag=false;
			System.out.print("\n������ �������� �ѱ۸����� �Է����ּ���. (��ctrl+z���Է� �� ������ ����ȭ��) : ");
			try
			{
				oldPlace = br.readLine();
				flag = Pattern.matches("^[��-����-�R]*$", oldPlace);

				if (!flag)
					System.out.println(">> �ѱ۸����� �ٽ� �Է����ּ���.");
				else if ("".equals(oldPlace))
				{
					System.out.println(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
					flag=false;
				}
			}
			catch (Exception e)
			{
				System.out.println(">> ������ �������� ���ư��ϴ�.");
				try 
				{
					Thread.sleep(750); //25�и��� 
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

		
		if (!whereToGo.contains(oldPlace))								// �Է� ���� �������� ������ �迭�� �ִ��� Ȯ��
		{
			System.out.println(">> ������ �������� �������� �ʽ��ϴ�.");
			System.out.println(">> ������ �������� ���ư��ϴ�.");
			try 
			{
				Thread.sleep(750); //25�и��� 
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
								System.out.println(">> ������ �Ұ����� ������ �Դϴ�.");
								System.out.println(">> ������ �������� ���ư��ϴ�.");
								try 
								{
									Thread.sleep(750); //25�и��� 
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
				System.out.print("\n���ο� �������� �ѱ۸����� �Է����ּ���. (��ctrl+z���Է� �� ������ ����ȭ��) : ");
				try
				{
					newPlace = br.readLine();
					flag = Pattern.matches("^[��-����-�R]*$", newPlace);

					if (!flag)
						System.out.println(">> �ѱ۸����� �ٽ� �Է����ּ���.");
					else if ("".equals(newPlace))
					{
						System.out.println(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
						flag=false;
					}
				}
				catch (Exception e)
				{
					System.out.println(">> ������ �������� ���ư��ϴ�.");
					try 
					{
						Thread.sleep(750); //25�и��� 
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
			

			if (whereToGo.contains(newPlace))							// ���ο� �������� �迭�� �����ϴ� ���������� Ȯ��
			{
					System.out.println(">> �̹� �����ϴ� ������ �Դϴ�.");
					
					System.out.println(">> ������ �������� ���ư��ϴ�.");
					try 
					{
						Thread.sleep(750); //25�и��� 
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
				System.out.print("\n���ο� �������� ���������� �Է����ּ���. (��ctrl+z���Է� �� ������ ����ȭ��) : ");
				try
				{
					placeEng = br.readLine();
					flag = Pattern.matches("^[a-zA-Z]*$", placeEng);

					if (!flag)
						System.out.println(">> ���������� �ٽ� �Է����ּ���.");
					else if ("".equals(placeEng))
					{
						System.out.println(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
						flag=false;
					}
				}
				catch (Exception e)
				{
					System.out.println(">> ������ �������� ���ư��ϴ�.");
					try 
					{
						Thread.sleep(750); //25�и��� 
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
				System.out.print("\n���ο� �������� ��� �ð��븦 �Է����ּ���.(���� ����)(ex. 06:00 08:00 11:00P) : ");
				try
				{
					tmpTime = br.readLine().split(" ");

					if (tmpTime.length!=3)
					{
						System.out.print(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
						flag=true;
					}
				}
				catch (Exception e)
				{
					System.out.print(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
					flag=true;
				}
				
			} 
			while (flag);

			

			do																	
			{																	
				flag=false;															
				System.out.print("\n���ο� �������� �װ��� �ݾ��� �Է����ּ���. : ");						
				try
				{
					tmpCost = Integer.parseInt(br.readLine());
					if (tmpCost < 35000 || tmpCost > 50000000)
					{
						System.out.print(">> �ݾ��� ������ �������� �ʽ��ϴ�. �ٽ� �Է����ּ���.");
						System.out.println();
						flag=true;
					}
				}
				catch (NumberFormatException e)
				{
					System.out.print(">> �߸��� �Է��Դϴ�.�ٽ� �Է����ּ���.");
					System.out.println();
					flag=true;
				}
			}
			while (flag);						


					
			do
			{
				try
				{									
					System.out.printf("\n%s�� %s(��)�� �������� �����Ͻðڽ��ϱ�?(Y/N) : ", oldPlace, newPlace);
					con = br.readLine();
					if (con.equals("y") || con.equals("Y"))
					{
						
						whereToGo.set(n,newPlace);
						englishName.set(n, placeEng);
						placeAndTime.set(n, tmpTime);
						placeCost.set(n, tmpCost);

						
						System.out.print(">> ������ �Ϸ�Ǿ����ϴ�.\n");
						mg_main();
					}
					else
					{
						System.out.print(">> ������ ��ҵǾ����ϴ�.\n");
						System.out.println(">> ������ �������� ���ư��ϴ�.");
						try 
						{
							Thread.sleep(750); //25�и��� 
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
					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");

				}
			}
			
			while (true);
		}	
	}	

	
	// ����Ȯ�� �޴� ���� �޼ҵ�
	protected void summaryPass() throws IOException, ClassNotFoundException
	{	
		int choose;														// �޴� ���� ���� ����
		boolean flag;

		do
		{ 
			System.out.println();
			System.out.println("������������������������������������������������������������������������������������������������������������������������");
			System.out.println("\t\t\tSsangyong Air");
			System.out.println("������������������������������������������������������������������������������������������������������������������������");
			System.out.println();
			System.out.println(" 1. ���ں� ����");
			System.out.println(" 2. �������� ����");
			System.out.println("������������������������������������������������������������������������������������������������������������������������");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("\n�̿��Ͻ� ���񽺸� �������ּ���. (��-1�� �Է� �� ������ ����ȭ��) : ");
	
			try
			{
				choose = Integer.parseInt(br.readLine());			
				if ((choose<1 || choose>2)&& choose !=-1)				// �޴��� �ִ� ���ڿ� -1 �� �Է� �����ϵ��� �� (-1: ������ �޴��� �̵�)
				{
					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				choose=-5;
			}
		}
		while ((choose<1 || choose>2) && choose != -1);
		
		if (choose==-1)
		{
			System.out.println(">> ������ �������� ���ư��ϴ�.");
			try 
			{
				Thread.sleep(750); //25�и��� 
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

	// ���� Ȯ�� �޼ҵ�(�޴� ���� �� �Է� ���� ���ڸ� �Ű������� �̿�)
	void summary(int ch) throws IOException, ClassNotFoundException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		String[] fileNamesarr = new String[h.size()];					// ��¥�� ���� �迭
		ArrayList<int[]> sumCost = new ArrayList<int[]>();				// ���ں� ������ ���� ArrayList

		int i=0;
		int tmpcost;													// ���ں� ����
		int tmpcharge;													// ���ں� ��� ������
		Seat[][] st;													// �ش� ��¥�� ����� ��ü ����

		for (String key : h.keySet())
		{
			fileNamesarr[i] = key;										// fileNamesarr �迭�� ��¥(hashmap key��) ����
			i++;
		}
		
		Arrays.sort(fileNamesarr);										// ��¥ ������������ ���� (��¥ ���� ������� ��µǱ� ����)
		

		// ���ں� ���� ����
		if (ch == 1)
		{
			sumCost.clear();											// sumCost �� �ʱ�ȭ										
			for (String day : fileNamesarr)							
			{
				tmpcost=0;												// ��¥ �ٲ� �� ���� �ʱ�ȭ
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
										// �� ���� = ����� + ��Ź���Ϲ� �߰� �ݾ� + ��� ������ �ݾ�
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

				int[] tmpCost = {tmpcost, tmpcharge};					// ���ں� {����, ��Ҽ�����} ���� �迭
				sumCost.add(tmpCost);									// {����, ��Ҽ�����} ���� �迭�� Arraylist�� �߰�
			}

			System.out.println("\n\t<< ���ں� ���� >>");
			for (int n=0; n<sumCost.size(); n++)
			{
				System.out.printf("%s  : ", fileNamesarr[n]);

				int[] sc = sumCost.get(n);							
				for (int mn=0; mn<sc.length; mn++)
				{
					if (mn!=0)
					{
						System.out.printf("(��� ������ : %,d�� ����)\n", sc[mn]);
						continue;
					}
					System.out.printf("%,d��",sc[mn]);
				}
				
			}
			
			System.out.println("\n����Ϸ��� Press button...");
			try
			{
				String pause = br.readLine();							// ���� �Է� �� ������ ���� �޴��� �̵�
				mg_main();
				return;
				
			}
			catch (IOException e)
			{
				mg_main();
				return;
			}

		}

		// �������� ���� ����
		else if (ch == 2)
		{
			sumCost.clear();											// sumCost �� �ʱ�ȭ
			for (int j=0; j<whereToGo.size(); j++)
			{
				tmpcost=0;												// ��¥ �ٲ� �� ���� �ʱ�ȭ
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
										// �� ���� = ����� + ��Ź���Ϲ� �߰� �ݾ� + ��� ������ �ݾ�
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
			
				int[] tmpCost = {tmpcost, tmpcharge};					// ���ں� {����, ��Ҽ�����} ���� �迭            
				sumCost.add(tmpCost);									// {����, ��Ҽ�����} ���� �迭�� Arraylist�� �߰�
			}


			System.out.println("\n\t<< �������� ���� >> ");
			for (int n=0; n<sumCost.size(); n++)
			{
				System.out.printf("%s : ",  whereToGo.get(n));

				int[] sc = sumCost.get(n);
				for (int mn=0; mn<sc.length; mn++)
				{
					if (mn!=0)
					{
						System.out.printf("(��� ������ : %,d�� ����)\n", sc[mn]);
						continue;
					}
					System.out.printf("%,d��",sc[mn]);
				}
				
			}
			System.out.println("\n����Ϸ��� Press button...");
			try
			{
				String pause = br.readLine();							// ���� �Է� �� ������ ���� �޴��� �̵�
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
	

	// ��ü ����ȭ �޼ҵ� 
	public void save() throws IOException, ClassNotFoundException
	{
		Calendar cal1 = Calendar.getInstance();					

		Integer d = cal1.get(Calendar.DATE);
		Integer m = cal1.get(Calendar.MONTH) + 1;
		Integer y = cal1.get(Calendar.YEAR);

		String t = String.format("%04d", y) + String.format("%02d", m) + String.format("%02d", d);	
		String time = t + ".ser";										

		String appDir = System.getProperty("user.dir");					
		File f1 = new File(appDir + "\\DataBase\\");					// "DataBase" ���� ����
		
		File f0 = new File(appDir +  "\\DataBase\\", time);				// ���� ��¥�� ���ϸ����� �ϴ� ���� ����

		if (!f0.getParentFile().exists())
		{
			f0.getParentFile().mkdirs();
		}

		FileOutputStream fos = new FileOutputStream(f0);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(h);												// ��ü ����ȭ
		oos.close();
		fos.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("\n���α׷��� �����Ͻðڽ��ϱ�? (��Y�� �̿� �Է� �� ������ ���� ȭ��) : ");
		String outcheck = br.readLine();

		if (outcheck.equalsIgnoreCase("Y"))
			out();
		else
			mg_main();
	}
	

	// ���α׷� ���� �޼ҵ�
	public static void out()
	{
		System.out.println("\n>> ���α׷� ����~!!!");
		System.exit(-1);
	}
	
// ===================== ������ ���� �޼ҵ� �� =================================


// ===================== ������ ���� �޼ҵ� ���� ================================


	int num;														// ����ȭ�鿡�� ����, ��ȸ, ��� ���� ������ �� ����� ����
	int idxPlace, idxTime;											// ������, �ð��� ���� �迭�� �ش��ϴ� index
	int selDay;														// �����ڰ� ������ ��¥(����) 
	String startDay;												// �����ڰ� ������ ��¥(��¥ ������ ���ڿ�)	
	String place;													// �����ڰ� �Է��� ������ 
	Queue<PassengerInfo> qu = new LinkedList<PassengerInfo>();		// �������� ����� �� ����� ����. ���� ������ �͸� Queue�� ��Ƽ� ��� (�ջ�� ���������� ���� ������ �ʵ���)
	String tmpcard;													// �����ڰ� �Է��� ī���ȣ
	String tmpcardpw;												// �����ڰ� �Է��� ī�� ��й�ȣ
	char[] tmpcd ;													// ī���ȣ ���� ���ڹ迭
	String con="";													// �����ڰ� �Է��� ����(Y/N)
	


	void reserve() throws Exception
	{
		System.out.println("\n\n���� ���񽺸� �����ϼ̽��ϴ�.");
		System.out.println("\n���Ϻ��� �����ֱ���, �������� Ư�� �װ����� �����帳�ϴ�.");
		System.out.println("���Ͻô� �ⱹ��¥�� ���� �� �ش����� �װ����� Ȯ���Ͻ� �� �ֽ��ϴ�.");


		Calendar now = Calendar.getInstance();						
		System.out.printf("\n���� ��¥ : %tF\n", now);				

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// ��¥ ���(����~ ������ ��)

		System.out.println("����������������������������������������������������������");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String[] data_str = new String[7];							// ������ġ ��¥�� ��Ƶ� String �迭 (���߿� ����� �� �ֵ��� ����� ��Ƶ�)
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);									// ������ �������̸� ȭ���Ϻ��� ����ϵ��� ��
		date = cal.getTime();
		
        for (int i = 0; i < 7; i++)
		{
            System.out.println((i+1)+". "+ formatter.format(date));
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            date = cal.getTime();
			data_str[i] = formatter.format(date);
		}
		System.out.println("����������������������������������������������������������");
		
		// ��¥ ����
		do
		{

			System.out.printf("\n��¥ ���� : ");
			try
			{
				selDay = Integer.parseInt(br.readLine()) ;				

				if (selDay < 1 || selDay > 7)
				{
					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");	
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���."); 
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
		
		System.out.printf("\n\n>> ������ ��¥ : %tF\n", selectCal);	

		// �������� ���� ���
		do
		{
			System.out.println("\n\t\t �������� ����");
			System.out.println("\t\t(Economy class)");
			System.out.println("������������������������������������������������������������������������������������������������������");
			for (int j=0; j<whereToGo.size(); j++)
			{
				System.out.printf("%4s : %,7d", whereToGo.get(j), placeCost.get(j));
				if (j!=0 && j%2==0 && j!=(whereToGo.size()-1))
				{
					System.out.println();
				}
			}
			System.out.println();
			System.out.println("������������������������������������������������������������������������������������������������������");

			// ������ ����
			System.out.printf("\n������ ���� (");
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
				System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");				
			}

		}
		while (idxPlace==-1);
		
		// ��� �ð��� ���� 
		System.out.printf("\n%tF %s�� �װ��� ���\n", selectCal, place);

		System.out.println("����������������������������������������������������������");
		for (int j=0; j<placeAndTime.get(idxPlace).length; j++)	
		{
			System.out.printf(" %d. %s\n", j+1, placeAndTime.get(idxPlace)[j]);			
		}
		System.out.println("����������������������������������������������������������");
		System.out.println();
		do
		{
			System.out.print("\n���ϴ� �ð��� �����ϼ��� (��ȣ �Է�) : ");
			try
			{
				idxTime = Integer.parseInt(br.readLine()) - 1;			

				if (idxTime < 0 || idxTime > placeAndTime.size() - 1)
				{
					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				idxTime = -1;
			}

		}
		while (idxTime<0 || idxTime>=placeAndTime.get(idxPlace).length);
		

		
		System.out.printf(">> %tF %s�� %s ����⸦ �����ϼ̽��ϴ�.\n\n", selectCal, place, placeAndTime.get(idxPlace)[idxTime]);

		// �¼� ���� �޼ҵ� ȣ��
		seatSelect();

		// ���� �޼ҵ� ȣ�� (���� ���� �� 1 ��ȯ)
		//					(ī���ȣ �� ī�� ��й�ȣ �Է� ���� �� 0 ��ȯ)
		int b = payMent();												
	
		// ���� ���� �� ����Ȯ�� ���� Ȯ��
		if (b == 1)
		{
			do
			{
				flag=false;
				try
				{
					System.out.print("\n������ Ȯ���ϰڽ��ϱ�?(Y/N) : ");
					con = br.readLine();
					if (!con.equalsIgnoreCase("Y") && !con.equalsIgnoreCase("N"))
					{
						flag=true;
					}
				}
				catch (Exception e)
				{
					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
					flag=true;
				}
			}
			while (flag);
			
			// ���� Ȯ���ϰڴٰ� �� ��
			if (con.equals("Y") || con.equals("y"))
			{

				for (int[] s : Seat.tmpseat)		// passInfor()������ �¼� ��ȣ�� �Ű������� �޾� �¼� ������ �ٷ� hashmap�� ������ �� �־�����
													// payMent() ������ �Ű������� ���� �ʱ� ������ �ӽ� �¼� �迭(tmpseat)�� ����� �¼��� �̿��Ѵ�.
				{
					// hashmap �� cardNumber ����(PassengerInfo)�� ����� ī���ȣ �ֱ�
					h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardNumber(tmpcard);
					// hashmap �� cardPW ����(PassengerInfo)�� ����� ī�� ��й�ȣ �ֱ�
					h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardPW(tmpcardpw);
				}

				// print() ȣ���Ͽ� ������ ���
				print(place, placeAndTime.get(idxPlace)[idxTime]);
			}
			// ���� Ȯ�� ���ϰڴٰ� �� ��
			else
			{
				// qu(Queue) �� ��� ž���� ���� ���� 
				qu.clear();			
				
				for (int[] s : Seat.tmpseat)
				{
					// tmpseat �� ��� ��� �¼��� boolean �� false ����
					h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
					// tmpseat �� ��� ��� �¼��� ������ �̸� null ����
					h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
					// �ش� ����� �ܿ��¼� �� ����
					++h.get(startDay)[idxPlace][idxTime].seatLeft;
					
				}
				
				do
				{
					flag=false;
					try
					{
						System.out.print("������ �ٽ� �Ͻðڽ��ϱ�?(Y/N) : ");
						con = br.readLine();
						if (!con.equalsIgnoreCase("Y") && !con.equalsIgnoreCase("N"))
						{
							flag=true;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
						flag=true;
					}
				}
				while (flag);

				// ���� �ٽ� �ϰڴٰ� �� �� �¼� ���ú��� �ٽ� ����
				if (con.equalsIgnoreCase("Y"))
				{
					// �¼����� �޼ҵ� ȣ��
					 seatSelect();
					// ���� �޼ҵ� ȣ��
					 b = payMent();

					 // �ι�° ���� ���� ���� �� ���� Ȯ�� ���� Ȯ��
					if (b == 1)
					{
						do
						{
							flag=false;
							try
							{
								System.out.print("������ Ȯ���ϰڽ��ϱ�?(Y/N : N(n) �Է½� �������� ���ư��ϴ�.) : ");
								con = br.readLine();
								if (!con.equalsIgnoreCase("Y") && !con.equalsIgnoreCase("N"))
								{
									flag=true;
								}
							}
							catch (Exception e)
							{
								System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
								flag=true;
							}
						}
						while (flag);
						

						if (con.equals("Y") || con.equals("y"))
						{
							for (int[] s : Seat.tmpseat)
							{
								// hashmap �� cardNumber ����(PassengerInfo)�� ����� ī���ȣ �ֱ�
								h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardNumber(tmpcard);
								// hashmap �� cardPW ����(PassengerInfo)�� ����� ī�� ��й�ȣ �ֱ�
								h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardPW(tmpcardpw);
							}
							// print() ȣ���Ͽ� ������ ���
							print(place, placeAndTime.get(idxPlace)[idxTime]);
						}
						// �ι�° ���� �� ���� Ȯ�� ���ϰڴٰ� �� ��
						else
						{
							// qu(Queue) �� ��� ž���� ���� ����
							qu.clear();
							for (int[] s : Seat.tmpseat)
							{
								// tmpseat �� ��� ��� �¼��� boolean �� false ����
								h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
								// tmpseat �� ��� ��� �¼��� ������ �̸� null ����
								h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
								// �ش� ����� �ܿ��¼� �� ����
								++h.get(startDay)[idxPlace][idxTime].seatLeft;

								System.out.println(">> ���� ȭ������ ���ư��ϴ�.");
								try 
								{
									Thread.sleep(750); //25�и��� 
								} 
								catch (InterruptedException e) 
								{
									e.printStackTrace();
								}
								return;
							}
						}
					}
					// �ι�° ���� �� ���� ���� ���� ��
					
					// ���� �ٽ� ���ϰڴٰ� �� �� ������ ���� �̵�
					else
					{
					
						// qu(Queue) �� ��� ž���� ���� ����
						qu.clear();
						for (int[] s : Seat.tmpseat)
						{
							// tmpseat �� ��� ��� �¼��� boolean �� false ����
							h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
							// tmpseat �� ��� ��� �¼��� ������ �̸� null ����
							h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
							// �ش� ����� �ܿ��¼� �� ����
							++h.get(startDay)[idxPlace][idxTime].seatLeft;
						}
						System.out.println(">> ���� ȭ������ ���ư��ϴ�.");
						try 
						{
							Thread.sleep(750); //25�и��� 
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
					// qu(Queue) �� ��� ž���� ���� ����
					qu.clear();
					for (int[] s : Seat.tmpseat)
					{
						// tmpseat �� ��� ��� �¼��� boolean �� false ����
						h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
						// tmpseat �� ��� ��� �¼��� ������ �̸� null ����
						h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
						// �ش� ����� �ܿ��¼� �� ����
						++h.get(startDay)[idxPlace][idxTime].seatLeft;
					}
					System.out.println(">> ���� ȭ������ ���ư��ϴ�.");
					try 
					{
						Thread.sleep(750); //25�и��� 
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					return;
				}
			}
		}
		else // ù��° ���� ���� ���� �� 
		{
			qu.clear();
			for (int[] s : Seat.tmpseat)
			{
				// tmpseat �� ��� ��� �¼��� boolean �� false ����
				h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
				// tmpseat �� ��� ��� �¼��� ������ �̸� null ����
				h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
				// �ش� ����� �ܿ��¼� �� ����
				++h.get(startDay)[idxPlace][idxTime].seatLeft;
			}
			System.out.println(">> ���� ȭ������ ���ư��ϴ�.");
			try 
			{
				Thread.sleep(750); //25�и��� 
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			return;														// ������ ���� �̵�
		}
	}
	
	// �¼� ���� �޼ҵ� (hashmap �� �ִ� ����� ����(Seat[][]) �̿�)
	void seatSelect() throws IOException 		
	{

		String seat;												// �����ڰ� ������ �¼� ��ȣ
		int row, col;												// ������ �¼��� ��, �� 
		int inwon;													// ���� �ο�  ��


		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		Seat.tmpseat.clear();										// ArrayList tmpseat�� �� �� �ʱ�ȭ �ؾ� ��ü ������ ��ҵ��� ����

		
		// ���� �ο� �� �Է� (�ܿ� �¼��� ���� ������ �ִ� 4����� ���� ����)
		System.out.printf("�ο� ���� �Է��ϼ���. �� ���� 4����� ������ �����մϴ�.(�ܿ� �¼� ��: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);

		
		do
		{
			try
			{
				inwon = Integer.parseInt(br.readLine());

				if (inwon> h.get(startDay)[idxPlace][idxTime].seatLeft || inwon>4)
				{
					System.out.printf(">> �̿� ������ �¼� ���� �ʰ��� �ο� �� �Դϴ�. �ٽ� �Է����ּ���.");
					System.out.printf("\n\n�ο� ���� �Է��ϼ���. �� ���� 4����� ������ �����մϴ�.(�ܿ� �¼� ��: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);

					inwon=-1;
				}
				else if (inwon <1)
				{
					System.out.printf(">> 1 �̻��� ���ڷ� �ٽ� �Է����ּ���. ");
					System.out.printf("\n\n�ο� ���� �Է��ϼ���. �� ���� 4����� ������ �����մϴ�.(�ܿ� �¼� ��: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);
				}
				else 
					break;
			}
			catch (NumberFormatException e)
			{
				System.out.printf(">> �ùٸ��� ���� �Է��Դϴ�.�ٽ� �Է����ּ���.");
				System.out.printf("\n\n�ο� ���� �Է��ϼ���. �� ���� 4����� ������ �����մϴ�.(�ܿ� �¼� ��: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);

				inwon = -1;
			}
			
		}
		while (inwon <=-1);

		// �¼� ��Ȳ ���
		for (int i=0; i<inwon ; i++)															// �ο��� ��ŭ �¼� ���� �ݺ�
		{
			do
			{
				System.out.print("\n�¼��� �����ϼ���(ex. A10)\n");
				System.out.print("(�� : �� �¼�, �� : ���õ� �¼�)\n");
				System.out.printf("%6c  %3c%11c %3c\n",'A','B','C','D');
				System.out.println("  �� <-���Թ�");
				for (int j=0; j<h.get(startDay)[idxPlace][idxTime].seatB.length; j++)			// �ش� ����� �¼��� �� �¼� ��(row) ��
				{																		
					if (j%2==1)
					{
						System.out.print("��");													
						System.out.printf("%2d",j+1);
					}

					else
						System.out.printf("%3d",j+1);
					
					for (int k=0; k<h.get(startDay)[idxPlace][idxTime].seatB[j].length; k++)	// �ش� ����� �¼��� �� �¼� ��(col) ��
					{
						if (!h.get(startDay)[idxPlace][idxTime].seatB[j][k])					// �ش� ����� �¼��� �¼� ��Ȳ(true: ����� �¼�, false: ����� �� �¼�)			
						{
							System.out.printf("%3s","��");
							if (k==1)
							{
								System.out.print("        ");
							}
						}
						else if (h.get(startDay)[idxPlace][idxTime].seatB[j][k])	
						{
							System.out.printf("%3s","��");										// ����� �¼��� "��"�� ǥ��
							if (k==1)
							{
								System.out.print("        ");
							}
						}
													
					}

					if (j%2==1)
					{
						System.out.print(" ��");
					}
					System.out.println();
				}
				System.out.println();

				// ��(col) �� �¼� ��� �� �¼� ���� �ȳ�
				System.out.printf("1~2 �� : First class (%,d��)\n",(int)(placeCost.get(idxPlace) * 1.5));			// first class : �������� ���� * 1.5						  
				System.out.printf("3~5 �� : Business class (%,d��)\n",(int)(placeCost.get(idxPlace) * 1.3));		// Business class : �������� ���� * 1.3
				System.out.printf("6~10 ��  : Economy class(%,d��)\n",placeCost.get(idxPlace));						// Economy class : �������� ���� * 1
				System.out.println();
				

				// �¼� ��ȣ �Է�
				System.out.printf("%d��° �¼� ����(ex. A10) : ", i+1);						
				
				do
				{
					try
					{
						seat = br.readLine();													
					
						switch (seat.substring(0,1))																// �¼���ȣ �� ����(A,B,C,D)�� ����
						{
						case "A": case "a": col = 0; break;
						case "B": case "b": col = 1; break;
						case "C": case "c": col = 2; break;
						case "D": case "d": col = 3; break;
						default : col = -1; break;

						}
						row = Integer.parseInt(seat.substring(1)) - 1;												// �¼���ȣ �� ���ڸ� ����(�ε����� �̿�)

						if (col==-1 || (row<0 || row>=h.get(startDay)[idxPlace][idxTime].seatB.length))						
						{
							System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
							System.out.printf("%d��° �¼� ���� : ", i+1);				
						}
					}
					catch (Exception e)
					{
						System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
						System.out.printf("%d��° �¼� ���� : ", i+1);
						
						row = col = -1;
					}
	
				}
				while(col == -1 || (row<0 || row>=h.get(startDay)[idxPlace][idxTime].seatB.length)); // �߸��� �Է��� ��� - ��, �¼� ���� a~d�� ����ų� ���� 1~10�� ��� ���
								
			}
			while (seatCheck(row,col));		// seatCheck(row,col)�� true���(���õ� �¼��̸� �ݺ�)
											// seatCheck(row,col)�� false���(����ִ� �¼��̶��) 
			passInfor(row,col);				// ������ ������ �Է� ����

		}  
	}

	// �¼� ��Ȳ Ȯ�� �޼ҵ�
	boolean seatCheck(int row, int col)		
	{
		if (!h.get(startDay)[idxPlace][idxTime].seatB[row][col])						// �ش� ����� �¼��� ���� �ȵǾ� ������ (-> false ��ȯ)
		{
			try
			{
				if (h.get(startDay)[idxPlace][idxTime].seatP[row][col].getName()==null)
				{
					h.get(startDay)[idxPlace][idxTime].seatB[row][col] = true;					// �ش� �¼� true�� �ٲ���.
					h.get(startDay)[idxPlace][idxTime].seatLeft--;
					System.out.println(">> ���� ������ �¼��Դϴ�.\n");
					int[] tmp = {row, col};														
					Seat.tmpseat.add(tmp);														// tmpseat�� �����ص�.
					return false;
				}
			}
			catch (NullPointerException e)
			{
				h.get(startDay)[idxPlace][idxTime].seatP[row][col] = new PassengerInfo();	// �ش� �¼��� PassengerInfo �ν��Ͻ� �����Ͽ� ������ ���� ����.
				h.get(startDay)[idxPlace][idxTime].seatB[row][col] = true;					// �ش� �¼� true�� �ٲ���.
				h.get(startDay)[idxPlace][idxTime].seatLeft--;
				System.out.println(">> ���� ������ �¼��Դϴ�.\n");
				int[] tmp = {row, col};														
				Seat.tmpseat.add(tmp);														// tmpseat�� �����ص�.
				return false;
			}
			
		}
		else if (h.get(startDay)[idxPlace][idxTime].seatB[row][col])					// �ش� ����� �¼��� ����Ǿ� ������ (-> true ��ȯ)
		{
			System.out.println(">> ���õ� �¼��Դϴ�.\n");
			return true;
		}
		return true;
	}


	// ������ ���� �Է� �޼ҵ�(PassengerInfo)
	void passInfor(int row, int col) throws IOException
	{
		String tmpAns = "n";															// �����ڰ� �Է��� ž������ ��ġ ����(Y/N)
		String tmpName="";																// �����ڰ� �Է��� �̸� 
		String tmpEngLastName="";														// �����ڰ� �Է��� �����̸�
		String tmpEngFirstName="";														// �����ڰ� �Է��� �����̸�(��)
		String tmpTel="";																// �����ڰ� �Է��� ��ȭ��ȣ
		String tmpBag;																	// ž���ڰ� �Է��� ���Ϲ� ����(Y/N)
		int tmpBagNum1=-1;																// 24~28 kg ���Ϲ� ����
		int tmpBagNum2=-1;																// 28~32 kg ���Ϲ� ����
		int tmpBagNum3=0;																// �� ���Ϲ� ����
		int tmpBagSum=0;																// �� ���Ϲ� �߰� �ݾ�
		boolean flag=false;																
									
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// ������ ���� �Է�(�̸�(�ѱ۸�), �̸�(������), ��ȭ��ȣ)
		System.out.println("<������ ���� �Է�>");
		do
		{
			do
			{
				try
				{
					System.out.print("\n������ �Է��ϼ��� : ");
					tmpName = br.readLine();
	
					if ("".equals(tmpName))
					{
						System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");	
					}
					else
					{
						flag = Pattern.matches("^[��-����-�R]*$", tmpName);					// �ѱ۸� �Է� ����
						if (!flag)
								System.out.println(">> �ѱ۸����� �ٽ� �Է����ּ���.");
					}
					
				}
				catch (Exception e)
				{
					
					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				}
			}
			while (!flag);
			
			
			
			System.out.println("\n���� �̸��� �Է��ϼ���.");
			
			do
			{
				engFlag=true;
				try
				{
					System.out.print("Last Name(��): ");
					tmpEngLastName= br.readLine().toUpperCase();	

					if ("".equals(tmpEngLastName))
					{
						System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
					}
					else
						// ����θ� �ԷµǾ����� Ȯ���ϴ� �޼ҵ�(checkEngName()) ȣ�� -> ������ �Է��� ���� ��ȯ, engFlag = false
						tmpEngLastName = checkEngName(tmpEngLastName);						
				
				}	
				catch (Exception e)
				{
					
					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
				}
			}while (engFlag);

			do
			{
				engFlag=true;
				try
				{
					System.out.print("First Name(�̸�): ");
					tmpEngFirstName= br.readLine().toUpperCase();	

					if ("".equals(tmpEngFirstName))
					{
						System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
					}
					else
						// ����θ� �ԷµǾ����� Ȯ���ϴ� �޼ҵ�(checkEngName()) ȣ�� -> ������ �Է��� ���� ��ȯ, engFlag = false
						tmpEngFirstName = checkEngName(tmpEngFirstName);					
				
				}	
				catch (Exception e)
				{
					
					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
				}
			}while (engFlag);
			
		do
		{
			try
			{
				System.out.print("\n��ȭ��ȣ�� �Է��ϼ��� (XXX-XXXX-XXXX): ");
				tmpTel = br.readLine();
				flag = tmpTel.matches("\\d{3}-\\d{4}-\\d{4}");						// ��ȭ��ȣ ����(xxx-xxxx-xxxx)�� �Է� ����
				if (!flag)
				{
					System.out.println(">> ��ȭ��ȣ ���Ŀ� �°� �Է����ּ���");
					flag = false;
				}
			}
			catch (Exception e)
			{
				System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
			}
		}while (!flag);

			// ��Ź���Ϲ� �߰� Ȯ�� 
			do
			{
				flag=false;

				try
				{
					do
					{
						tmpBagSum = 0;
						System.out.print("\n�߰��� ��Ź���Ϲ��� �ֽ��ϱ�?(Y/N) : ");	
						tmpBag = br.readLine();
					
						if (tmpBag.equals("y")||tmpBag.equals("Y"))
						{
							// ��Ź���Ϲ� ���Ժ� �ݾ� ���
							System.out.println("\n[��Ź���Ϲ� ���� : ���Ժ� �߰��ݾ�]");
							System.out.println("=============================================");
							System.out.println("24 ~ 28kg : 40,000��");
							System.out.println("28 ~ 32kg : 60,000��");
							System.out.println("�� 32kg �ʰ��� ���� �Ұ�, �ִ���� 5�� ���� ��");
							System.out.println("==============================================\n");
							do	
							{
								System.out.print("24kg �̻��� ��Ź���Ϲ��� �����Ű���?(Y/N) : ");
								tmpBag = br.readLine();

								if (tmpBag.equals("y")||tmpBag.equals("Y"))				// 24kg �̻��� ��Ź���Ϲ��� ���� ��
								{
									do
									{
										System.out.print("24 ~ 28kg ��Ź���Ϲ� ������ �Է����ּ��� : ");
										try
										{
											tmpBagNum1 = Integer.parseInt(br.readLine());
											if (tmpBagNum1>5)
												System.out.println("�� ������ ������ �ִ� 5�� �Դϴ� ��\n");	
										}
										catch (NumberFormatException e)
										{
											System.out.println(">> ���ڸ� �Է����ּ���.\n");
										}
									}
									while (tmpBagNum1>5 || tmpBagNum1<0);	
									
									tmpBagSum += 40000 * tmpBagNum1;					// 24 ~28 kg ���Ϲ� 1�� �߰���� : 40000
									
									if (tmpBagNum1!=5)									// tmpBagNum1�� �̹� �ִ� ���� 5���� ä��� 28~32kg �� ���Ϲ��� 
																						// ���� �ʰ� �ٷ� ���� Ȯ������ �Ѿ�� ��
									{
										do
										{
											flag=true;
											System.out.print("28 ~ 32kg ��Ź���Ϲ� ������ �Է����ּ��� : ");
											try
											{
												tmpBagNum2 = Integer.parseInt(br.readLine());
												
											}
											catch (NumberFormatException e)
											{
												System.out.println(">> ���ڸ� �Է����ּ���.\n");
												flag = false;
											}
											if (flag)
											{
												tmpBagNum3 = tmpBagNum1 + tmpBagNum2;
												if (tmpBagNum2>5 || tmpBagNum3>5)
													System.out.printf("�� ������ ������ �ִ� 5�� �Դϴ�(���� ����:%d) ��\n\n",tmpBagNum1);
											}
										}
										while (tmpBagNum2<0 || tmpBagNum2>5|| tmpBagNum3>5); // �� ��Ź���Ϲ� ���� �ִ� 5������ �ް���

										tmpBagSum += 60000 * tmpBagNum2;					 // 28 ~32 kg ���Ϲ� 1�� �߰���� : 40000

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
								else if (tmpBag.equals("n")||tmpBag.equals("N"))			// �߰��� ��Ź ���Ϲ� ���ٰ� �Է����� ��
								{
									tmpBagNum1 = 0;
									tmpBagNum2 = 0;
								}
							}
							while (!(tmpBag.equals("n")||tmpBag.equals("N")||tmpBag.equals("y")||tmpBag.equals("Y")));	//  n,N,y,Y �� �Է� ����
						
						}
						else if (tmpBag.equals("n")||tmpBag.equals("N"))												// �߰��� ��Ź���Ϲ��� ���� ��
						{
							tmpBagNum1 = 0;
							tmpBagNum2 = 0;
						}
						
					}
					while (!(tmpBag.equals("n")||tmpBag.equals("N")||tmpBag.equals("y")||tmpBag.equals("Y")));			//  n,N,y,Y �� �Է� ����
				}
				catch (Exception e)
				{
					flag=true;
				}
			}
			while (flag);
				
			// ��Ź���Ϲ� �� �߰� �ݾ� ���
			if (tmpBagSum!=0)
			{
				System.out.printf("\n��Ź���Ϲ� �߰� ����� �� %s���Դϴ�.\n",decimalFormat.format(tmpBagSum));	
			}

			// ������ �Է� ���� Ȯ��
			System.out.println("\n<< ������ ���� Ȯ�� >>");
			System.out.println("�̸�(�ѱ۸�) : " + tmpName +"\n�̸�(������) : "+tmpEngFirstName+" "+tmpEngLastName+"\n��ȭ��ȣ : " + tmpTel);
			System.out.println("24 ~ 28kg ��Ź���Ϲ� ���� : " + tmpBagNum1);	
			System.out.println("28 ~ 32kg ��Ź���Ϲ� ���� : " + tmpBagNum2);
			System.out.print("\n�Է��Ͻ� ������ �½��ϱ�?(Y/N) : ");
			tmpAns = br.readLine();
			if (tmpAns.equalsIgnoreCase("Y"))
			{
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setName(tmpName);									// hashmap �� name ����(PassengerInfo)�� ����� �̸� �ֱ�
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setEngFirstName(tmpEngFirstName);					// hashmap �� engFirstName ����(PassengerInfo)�� ����� �����̸�(�̸�)  �ֱ�
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setEngLastName(tmpEngLastName);						// hashmap �� engLastName ����(PassengerInfo)�� ����� �����̸�(��) �ֱ�
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setTel(tmpTel);										// hashmap �� IndexA ����(PassengerInfo)�� ����� ���� ��ȭ��ȣ �ֱ�
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setBagCost(tmpBagSum);								// hashmap �� IndexA ����(PassengerInfo)�� ����� �߰� ��Ź���Ϲ� �ݾ� �ֱ�
			}
			else
				System.out.println(">> ž�������� �ٽ� �Է����ּ���");

		} while (!(tmpAns.equalsIgnoreCase("Y")));


		// hashmap �� indexA ����(PassengerInfo)�� �ش� �¼���ȣ �ֱ�
		h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexA(row);

		//  hashmap �� grade ����(PassengerInfo)�� �ش� �¼� ��� �ֱ�
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

		// hashmap �� serial ����(PassengerInfo)�� �ش� �¼� ������ȣ �ֱ�(�¼���ȣ+������+��߽ð�+��¥)  
		switch (col)
		{
		case 0: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("A") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("A"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		case 1: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("B") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("B"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		case 2: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("C") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("C"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		case 3: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("D") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("D"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		default : h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("row") ; break;
		}

		// hashmap �� cost ����(PassengerInfo)�� �ش� �¼� �ݾ� �ֱ� 
		switch(row)
		{
			case 0: case 1: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setCost((int)(placeCost.get(idxPlace) * 1.5));break;							
			case 2:case 3:case 4:  h.get(startDay)[idxPlace][idxTime].seatP[row][col].setCost((int)(placeCost.get(idxPlace) * 1.3));break;					
			case 5:case 6:case 7:case 8:case 9:  h.get(startDay)[idxPlace][idxTime].seatP[row][col].setCost(placeCost.get(idxPlace));break;	
		}

		// hashmap �� depTime ����(PassengerInfo)�� �ش� �¼� ��� �ð� �ֱ�
		h.get(startDay)[idxPlace][idxTime].seatP[row][col].setDepTime(placeAndTime.get(idxPlace)[idxTime]);	

		// hashmap �� desti ����(PassengerInfo)�� �ش� �¼� ������(����) �ֱ�
		h.get(startDay)[idxPlace][idxTime].seatP[row][col].setDesti(englishName.get(idxPlace));				
		
		// hashmap �� ����� ���� qu(Queue)�� �߰��ϱ� (������ ��� ����)
		qu.offer(h.get(startDay)[idxPlace][idxTime].seatP[row][col]);
		
	}

	// �Է� ���� ���� �������� Ȯ���ϴ� �޼ҵ�
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
			System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
			this.engFlag = true;
			return "";
		}
	}

	// ���� �޼ҵ�
	private int payMent() throws IOException
	{
		boolean flag;
		int checkCard=0;												//  ī��
		int cdn;														//  �Է¹��� ī���ȣ ���ڸ� ����
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// ī���ȣ �Է¹޾� ī�� ��ȿ�� �˻� ����
		do
		{
			flag=false;
			try
			{
				System.out.print("\n�����Ͻ� ī�� ��ȣ 16�ڸ��� �Է����ּ��� : ");
				tmpcard = br.readLine();
				if ("".equals(tmpcard))
				{
					System.out.print(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
					flag=true;
				}
				else
				{
					tmpcd = tmpcard.toCharArray();						// ī���ȣ ���ڹ迭�� ���


					// 16�ڸ� ���ڿ��� �� ���� ������ ���� Ȧ�� ��° ���� �״�� �ΰ�, ¦�� ��° ���� 2��� �����.
					// 2��� ���� ¦�� ��° ���� 10 �̻��� ���, �� �ڸ��� ���ڸ� ���ϰ� �� ���� ��ü�Ѵ�.
					// �̿� ���� ���� ��� �ڸ��� ���� ���Ѵ�.
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
				System.out.print(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
				flag = true;

			}
		}
		while (flag);

		
		// 10���� �� ��(checkCard)�� ���� �������� ���ϰ�, �װ��� 10���� ����.
		// �� ���� ī���ȣ�� ������ ���ڿ� ������ Ȥ�� �������� 10 �̰� ī���ȣ�� ������ ���ڰ� 0�̶��
		// �������� ��ȣ��(��ȿ)�̰�, �׷��� ������ ���δ��� ��ȣ��(��ȿ���� ����)�� �����ȴ�.

		// ī���ȣ�� 16�ڸ��̰�, ��ȿ�� ������ �Ǿ��ٸ� �� ī�� ��й�ȣ �Է�
		if (tmpcd.length==16 && (((10-(checkCard%10)) == Character.getNumericValue(tmpcd[tmpcd.length-1])) || ((10-(checkCard%10))==10 && Character.getNumericValue(tmpcd[tmpcd.length-1])==0)))
		{
			int breakn=0;												// ���� �Է� Ƚ�� ����(�ִ� 3ȸ)
			do
			{
				try
				{
					if (breakn>=3)
					{
						return 0;
					}
					
					System.out.printf("ī�� ��й�ȣ ���� 4�ڸ��� �Է����ּ���.(���� �Է� Ƚ�� : %d) : ",3-breakn);
					tmpcardpw = br.readLine();
			
					char[] tmpcdpw = tmpcardpw.toCharArray();
					
					if (tmpcdpw.length == 4)							// �Է��� ��й�ȣ ���ڰ� 4�ڸ� �� �� 
					{
						try
						{
							for (char cdpw : tmpcdpw)
							{
								Character.getNumericValue(cdpw);		// ���ڸ� �Է� �����ϵ��� ��
							}
							return 1;
						}
						catch (NumberFormatException e)
						{
							System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
						}
					}
					else
						System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
				}
				catch (Exception e)
				{
					System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.\n");
				}
				
				breakn++;												
			}
			while (breakn<4);
			return 1;													// ī���ȣ �� ī�� ��й�ȣ �Է� ���� �� 1����
		}
		// �߸��� ī���ȣ �Է� ��
		else
		{
			System.out.println(">> ī���ȣ�� �ùٸ��� �ʽ��ϴ�.");
			System.out.print("�ٽ� �Է��Ͻðڽ��ϱ�?(Y/N)(��Y�� �̿� �Է� ����� ȭ��) : ");
			String re = br.readLine();

			if (re.equals("Y") || re.equals("y"))
			{
				return payMent();										// ���� ó������ �ٽ� ����
			}
			else
				return 0;												// ī���ȣ �ٽ� �Է� �ź� �� �� 0 ����
		}
			
	}

	// ����� Ƽ�� �߱� ���� ��� �޼ҵ�
	void print(String str, String time)
	{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Load.loadingPay();
		System.out.println("\n<<"+str + " �� " + time + " ����� Ƽ�� �߱� ����>>");
		//qu(Queue)�� ����ִ� ž�� ������ �� �� ���� ž�� ���� ���
		while (!qu.isEmpty())
		{
			System.out.println("--------------------------------------------------------");
			System.out.printf("ž�°�: %s\nž�°�(����): %s-%s\n\n��ȭ��ȣ: %s\n�¼����: %s\n�¼���ȣ: %s%d\nƼ�ϰ���: %s�� + ��Ź���Ϲ� �ʰ����� ���: %s��\n������ȣ: %s\n",
				qu.peek().getName(), qu.peek().getEngFirstName(), qu.peek().getEngLastName(), qu.peek().getTel(),qu.peek().getGrade(), qu.peek().getIndexB(), (qu.peek().getIndexA()+1), decimalFormat.format(qu.peek().getCost()),decimalFormat.format(qu.peek().getBagCost()),qu.peek().getSerial());
			System.out.print("ī�� ��ȣ: ");
			char[] tmpcdpw = qu.peek().getCardNumber().toCharArray();
			for (int i=0; i<tmpcdpw.length; i++)
			{
				if ((i>=0 && i<4) || i>11)								// ī���ȣ ������ ����ŷ
				{
					System.out.print(tmpcdpw[i]);
				}
				else
					System.out.print("*");
			}
			System.out.println();
			System.out.println("--------------------------------------------------------\n\n");

			// �� ���� ž�� ���� ���� 
			qu.poll();
			System.out.println("\n����Ϸ��� Press button...");			// ���� �Է� �� ���� �޴��� �̵�
			
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
	// ���� ��ȸ �޼ҵ�
	void check() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String findRes="";													// �����ڰ� �Է��� ������ȣ
		int tmp=0;														// ������ȣ �Է� Ƚ��(�ִ� 3ȸ)

		do
		{
			try
			{
				// ������ȣ �Է�
				System.out.printf("\n������ȣ�� �Է����ּ���.(���� �Է� Ƚ��: %dȸ) : ", 3-tmp);
				findRes = br.readLine();

				// ������ȣ ����(���� 1�ڸ�+ ���� 11�ڸ�) �� �´��� Ȯ��
				if (findRes.matches("[A-Za-z][0-9]{11}"))				 
				{
					// �����ϴ� ������ȣ���� Ȯ���ϴ� �޼ҵ� ȣ��										
					break;
				}
				else
				{
					System.out.println(">> ������ȣ ���Ŀ� �°� �ٽ� �Է����ּ���.");
					tmp++;
				}
			
			}
			catch (Exception e)
			{
				System.out.println(">> ������ȣ ���Ŀ� �°� �ٽ� �Է����ּ���.");
				tmp++;
			}
		}
			while (tmp < 3);

		if (tmp==3)
		{
			System.out.println(">> �Է� Ƚ���� �ʰ��߽��ϴ�.");
			System.out.println(">> ���� ȭ������ ���ư��ϴ�.");
			try 
			{
                Thread.sleep(750); //25�и��� 
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

	// ���� ��ȸ(�����ϴ� ������ȣ���� Ȯ��) �޼ҵ�
	void check(String findRes) throws IOException	
	{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));										

		PassengerInfo passValue;										// �Է��� ������ȣ�� ����ִ� ������ ����									
		
		int col=-1;
		switch (findRes.substring(0,1))									// ������ȣ�� �ִ� �¼���ȣ(����)�� ���ڷ� ��ȯ
		{
		case "A" : col = 0; break;									
		case "B" : col = 1; break;
		case "C" : col = 2; break;
		case "D" : col = 3; break;
		}
		
		// �ش� ������ȣ�� ������ �����ִ��� Ȯ��
		// �Է��� ������ȣ�� �����Ͽ� ������ ���� ����(h.get(��¥)[������][�ð���][�¼��� ��][�¼��� ��]
		try
		{
			passValue = h.get(findRes.substring(4,8)+"-"+findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatP[Integer.parseInt(findRes.substring(1,2))][col];
			if (passValue.getName() == null)								// ž�������� ����� �̸��� ���� �� (���� �ߴٰ� ������� ���)
			{
				System.out.println(">> ������ �������� �ʽ��ϴ�.");
				System.out.println(">> ���� ȭ������ ���ư��ϴ�.");
				try 
				{
					Thread.sleep(750); //25�и��� 
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				return;
			}
			// ž������ ������ ��� ����� Ƽ�� ���
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
				System.out.println("\n����Ϸ��� Press button...");

				 try
				 {
					String pause = br.readLine();							// ���� �Է� �� ���� �޴��� �̵�
				 }
				 catch (IOException e)
				 {
					return;
				 }
				
			}
		}
		catch (NullPointerException e)
		{
			System.out.println(">> ������ �������� �ʽ��ϴ�.");			// �������� �ʴ� ������ȣ�� �Է��� ��� NullPointerException�� �Ͼ
			System.out.println(">> ���� ȭ������ ���ư��ϴ�.");
			try 
			{
                Thread.sleep(750); //25�и��� 
            } 
			catch (InterruptedException c) 
			{
                e.printStackTrace();
            }
			return;
		}					
	}
	// ���� ����ϴ� �޼ҵ�
	void cancel() throws IOException
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = false;

		String findRes="Z";												// �����ڰ� �Է��� ������ȣ
		double charge=0;												// ��� ������
		
		int tmp=3;														// ������ȣ �Է� Ƚ��(�ִ� 3ȸ)
		PassengerInfo passValue;										// �Է��� ������ȣ�� ����ִ� ������ ����
		
		// ������ȣ �Է�
		do
		{
			try
			{
				System.out.printf("\n������ȣ�� �Է����ּ���.(���� �Է� Ƚ��: %dȸ) : ",tmp);
				
				findRes = br.readLine();
				// ������ȣ ����(���� 1�ڸ�+����11�ڸ�)�� �´��� Ȯ��
				if (findRes.matches("[A-Za-z][0-9]{11}"))				
				{
					break;
				}

				System.out.println(">> ������ȣ ���Ŀ� �°� �ٽ� �Է����ּ���.");
				tmp--;
			}
			catch (Exception e)
			{

				System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
				tmp--;
			}
			
		}
		while (tmp>0);

		if (tmp==0)
		{
			System.out.println(">> �Է� Ƚ���� �ʰ��Ͽ����ϴ�. ���� ȭ������ ���ư��ϴ�.");
			System.out.println(">> ���� ȭ������ ���ư��ϴ�.");
			try 
			{
                Thread.sleep(750); //25�и��� 
            } 
			catch (InterruptedException c) 
			{
                c.printStackTrace();
            }
			return;
		}
		

		int col=-1;
		switch (findRes.substring(0,1))									// ������ȣ�� �ִ� �¼���ȣ(����)�� ���ڷ� ��ȯ
		{
		case "A" : col = 0; break;
		case "B" : col = 1; break;
		case "C" : col = 2; break;
		case "D" : col = 3; break;
		}

		// �ش� ������ȣ�� ������ �����ִ��� Ȯ��
		// �Է��� ������ȣ�� �����Ͽ� ������ ���� ����(h.get(��¥)[������][�ð���][�¼��� ��][�¼��� ��]
		try						
		{
			passValue = h.get(findRes.substring(4,8)+"-"+ findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatP[Integer.parseInt(findRes.substring(1,2))][col];
			
			if (passValue.getName() == null)								// ž�������� ����� �̸��� ���� �� (���� �ߴٰ� ������� ���)
			{
			System.out.println(">> ������ �������� �ʽ��ϴ�.");
			System.out.println(">> ���� ȭ������ ���ư��ϴ�.");
			try 
			{
				Thread.sleep(750); //25�и��� 
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
			System.out.println(">> ������ �������� �ʽ��ϴ�.");		// �������� �ʴ� ������ȣ�� �Է��� ��� NullPointerException�� �Ͼ
			System.out.println(">> ���� ȭ������ ���ư��ϴ�.");
			try 
			{
                Thread.sleep(750); //25�и��� 
            } 
			catch (InterruptedException c) 
			{
                c.printStackTrace();
            }
			return;
		}

		
		try
		{
			System.out.printf(">> %s���� �����ʴϱ�?(Y/N) : ", passValue.getName()); // ž�������� ����� �̸��� ���� ��� (���� �ߴٰ� ������� ���) NullPointerException�� �Ͼ
		}
		catch (Exception e)
		{
			System.out.println(">> ������ȣ�� ��ġ���� �ʰų� �������� �ʽ��ϴ�.");
			System.out.println("������ȣ�� �ٽ� �Է��Ͻðڽ��ϱ�?(Y/N)(��Y�� �̿� �Է� ����� ȭ��) : ");

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
					System.out.print(">> ���� ȭ������ ���ư��ϴ�.");
					try 
					{
						Thread.sleep(750); //25�и��� 
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
				System.out.print(">> ���� ȭ������ ���ư��ϴ�.");
				try 
				{
					Thread.sleep(750); //25�и��� 
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
			// �ش� ������ȣ�� ������ ������ ������ �� �� ��� ������ �ȳ�
			if (con.equals("Y") || con.equals("y"))						
			{
				System.out.println("\n�� ��ҽ� ����ݿ��� ������(������� 10%)�� ������ �ݾ��� ȯ�ҵ˴ϴ�. ��");

				charge = (passValue.getCost()+passValue.getBagCost()) * 0.1;			// ������ = (�����+���Ϲ� �߰��ݾ�) *0.1
				
				System.out.printf("\n>> %s���� ���� ��� �� ȯ�� ���� �ݾ��� %.0f��(������: %.0f��)�Դϴ�."
									, passValue.getName(),((passValue.getCost()+passValue.getBagCost())-charge), charge);
				
				do
				{
					flag = true;
					try
					{
						System.out.print("\n\n>> ������ ������ ����Ͻðڽ��ϱ�?(Y/N) : ");
						con = br.readLine();
						if (con.equalsIgnoreCase("y") || con.equalsIgnoreCase("n"))
						{
							flag = false;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
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
							// ���� ī�� ��й�ȣ �Է� (��ȸ �ִ� 3��)
							System.out.print("���� ī��(");
							char[] tmpcdpw = passValue.getCardNumber().toCharArray();
							for (int i=0; i<tmpcdpw.length; i++)
							{
								if ((i>=0 && i<4) || i>11)
								{
									System.out.print(tmpcdpw[i]);
								}
								else
									System.out.print("*");				// ī�� ������ ����ŷ
							}
							System.out.print(")�� ��й�ȣ�� �Է����ּ���: ");
	
							String passwd = br.readLine();

							if (passwd.equals(passValue.getCardPW()))
							{
								System.out.println(">> ��й�ȣ�� ��ġ�մϴ�.");
								l=3;
							}
							else
								System.out.printf(">> ��й�ȣ�� ��ġ���� �ʽ��ϴ�. (���� ��ȸ: %d��)", (3-(++l)));
						}
						catch (Exception e)
						{
							System.out.printf(">> ��й�ȣ�� ��ġ���� �ʽ��ϴ�. (���� ��ȸ: %d��)", (3-(++l)));
						}
						
					
					}
					while (l<3);
				}
				else
				{
					System.out.println(">> �������� ���ư��ϴ�.");
					try 
					{
						Thread.sleep(750); //25�и��� 
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
			System.out.println(">> �������� ���ư��ϴ�.");
			try 
			{
				Thread.sleep(750); //25�и��� 
			} 
			catch (InterruptedException c) 
			{
				c.printStackTrace();
			}
			return;
		}
		System.out.printf(">> %s������ %s�� %s ������Ұ� �Ϸ�Ǿ����ϴ�.", passValue.getName(), passValue.getDesti(), passValue.getDepTime());
		System.out.println("�ȳ��� ���ʽÿ�.");
		System.out.println();

		// ������ ������ ������ �̸��� null ����
		passValue.setName(null); 
		// ������ ������ ����� 0���� �ʱ�ȭ
		passValue.setCost(0);
		// ������ ������ ���� ��� �����ῡ�� ���� �߻��� ��� ������ ����
		passValue.setCharge(passValue.getCharge() + charge); 
		// hashmap ���� �ش� ����� �¼� ��Ȳ false�� ����
		h.get(findRes.substring(4,8)+"-"+ 
		findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatB[Integer.parseInt(findRes.substring(1,2))][col] = false;
		// hashmap ���� �ش� ����� �ܿ��¼� �� 1 �߰�
		h.get(findRes.substring(4,8)+"-"+ findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatLeft++;
		try 
		{
			Thread.sleep(750); //25�и��� 
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		return;
	}

}