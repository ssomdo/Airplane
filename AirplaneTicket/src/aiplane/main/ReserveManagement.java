package aiplane.main;

/*=====================================
	< SSANGYONG AIR > ³»ÀÏÀÇ Æ¯°¡ ºñÇà±â
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


// ¿¹¾àÀÚ ¹× °ü¸®ÀÚ ±â´É class
class ReserveManagement
{

	HashMap<String, Seat[][]> h = new HashMap<String, Seat[][]>();		//-- key : ³¯Â¥ / value : Seat[][]
	DecimalFormat decimalFormat = new DecimalFormat("#,###");			//-- ±İ¾× Ãâ·Â½Ã 000,000

	ArrayList<String[]> placeAndTime = new ArrayList<String[]>();		// ¸ñÀûÁöº°·Î ½Ã°£´ë ´ãÀ» ArrayList 
	ArrayList<String> whereToGo = new ArrayList<String>();				// ¸ñÀûÁö¸í(ÇÑ±Û) ´ãÀ» ArrayList   
	ArrayList<Integer> placeCost = new ArrayList<Integer>();			// ¸ñÀûÁö¸í(¿µ¹®) ´ãÀ» ArrayList   
	ArrayList<String> englishName = new ArrayList<String>();			// ¸ñÀûÁö º° °¡°İ ´ãÀ» ArrayList   

	boolean flag;
	boolean engFlag;
	int sel;

	// °ü¸®ÀÚ ºñ¹Ğ¹øÈ£ ÀÏÄ¡ ¿©ºÎ È®ÀÎ ¸Ş¼Òµå
	void isMgRight(int n) 
	{
		int mgPW;														// °ü¸®ÀÚ ºñ¹Ğ¹øÈ£
		int tmp=3;														// ÀÔ·Â È½¼ö (ÃÑ 3È¸)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		if (n==0)
		{
			System.out.println(">> °ü¸®ÀÚ ¸Ş´º ÀÌµ¿ Áß...");
			do
			{
				try
				{
					System.out.printf("\n°ü¸®ÀÚ ºñ¹Ğ¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.(Á¦ÇÑ ÀÔ·Â È½¼ö: %dÈ¸) : ",tmp--);
					mgPW = Integer.parseInt(br.readLine());

					if (mgPW == 230125)
					{
						mg_main();
						tmp=-1;
					}
					else
						System.out.println(">> ºñ¹Ğ¹øÈ£°¡ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				}
				catch (Exception e)
				{
					if (tmp==0)
						break;

					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");	
				}
				
			}
			while (tmp>0);
			if (tmp==0)
			{
				System.out.println(">> ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
				try 
				{
					Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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


	// °ü¸®ÀÚ ¸Ş´º Ãâ·Â ¸Ş¼Òµå								
	void mg_main() throws IOException, ClassNotFoundException					
	{																

		do
		{ 
			System.out.println();
			System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
			System.out.println("\t\t\tSsangyong Air");
			System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
			System.out.println();
			System.out.println("1. ¸ÅÃâ °ü¸®");
			System.out.println("2. ¸ñÀûÁö Ãß°¡");
			System.out.println("3. ¸ñÀûÁö ¼öÁ¤");
			System.out.println("4. µ¥ÀÌÅÍ ÀúÀå");
			System.out.println("5. ÇÁ·Î±×·¥ Á¾·á");
			System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("\nÀÌ¿ëÇÏ½Ç ¼­ºñ½º¸¦ ¼±ÅÃÇØÁÖ¼¼¿ä. (¡º-1¡» ÀÔ·Â ¡æ ¿¹¾àÀÚ ¸ŞÀÎÈ­¸é) : ");
	
			try
			{
				sel = Integer.parseInt(br.readLine());					// °ü¸®ÀÚ ¸Ş´º¿¡¼­ ³Ñ¾î°¥ ¼­ºñ½º ¼±ÅÃ
				if ((sel<1 || sel>5) && sel!=-1)
				{
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				sel=-5;
			}
		}
		while ((sel<1 || sel>5) && sel!=-1);
		
		if (sel==-1)													//-- -1 ÀÔ·Â½Ã ¿¹¾àÀÚ main(AirplaneSystem)À¸·Î µ¹¾Æ°¨
		{
			isMgRight(-1);
		}
		else
			mg_select(sel);
	}

	// °ü¸®ÀÚ ¸Ş´º ¼±ÅÃ ¸Ş¼Òµå
	void mg_select(int sel) throws IOException, ClassNotFoundException
	{
		switch (sel)											
		{
		case 1: summaryPass(); break;									// ¸ÅÃâ °ü¸®
		case 2: addPlace(1); break;										// ¸ñÀûÁö Ãß°¡
		case 3: change(); break;										// ¸ñÀûÁö ¼öÁ¤
		case 4: save(); break;											// µ¥ÀÌÅÍ ÀúÀå
		case 5: out(); break;											// ÇÁ·Î±×·¥ Á¾·á
		}
	}


	// hashmap »ı¼º ¹× °´Ã¼ ¿ªÁ÷·ÄÈ­ ¸Ş¼Òµå  
	protected void create() throws IOException, ClassNotFoundException
	{

		String appDir = System.getProperty("user.dir");
		File f1 = new File(appDir + "\\DataBase\\");
		String[] fileNames = f1.list();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
		// ÆÄÀÏÀÌ ¾øÀ» ½Ã(Ã³À½ ½ÇÇà) -> ³¯Â¥(³»ÀÏ ~ 7ÀÏ ÈÄ)¸¦ key°ªÀ¸·Î ÇÑ hashmap »ı¼º
		if (fileNames==null)
		{
			Date date = new Date();
			String[] data_str = new String[7];							// ÀÏÁÖÀÏÄ¡ ³¯Â¥¸¦ ´ã¾ÆµÑ String ¹è¿­ (³ªÁß¿¡ »ç¿ëÇÒ ¼ö ÀÖµµ·Ï ¿¹ºñ·Î ´ã¾ÆµÒ)
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);									// ¿À´ÃÀÌ ¿ù¿äÀÏÀÌ¸é È­¿äÀÏºÎÅÍ ¹è¿­¿¡ ´ãÀ½
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
		// ÆÄÀÏÀÌ ÀÖÀ» ½Ã(Ã³À½ ½ÇÇàÀÌ ¾Æ´Ô)-> ÆÄÀÏ ºÒ·¯¿À±â(°´Ã¼ ¿ªÁ÷·ÄÈ­)
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
			String whereDate = formatter.format(date);					// ÀÏÁÖÀÏ µÚ ³¯Â¥

			Calendar calendar2 = Calendar.getInstance();
			Date date2 = calendar2.getTime();							
			String whereDate2 = formatter.format(date2);				// ¿À´Ã ³¯Â¥

			
			// ÀÏÁÖÀÏ µÚ ³¯Â¥¸¦ key °ªÀ¸·Î ÇÏ´Â hashmap ÀÌ ¾øÀ» ¶§ -> ¿À´Ã ³¯Â¥
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

	// ¸ñÀûÁö Ãß°¡ ¸Ş¼Òµå
	void addPlace(int n) throws IOException, ClassNotFoundException
	{
		// ÇÁ·Î±×·¥ Ã³À½ ½ÇÇà½Ã ¸Å°³º¯¼ö 0 ³Ñ°Ü¹Ş¾Æ ½ÇÇà
		
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

		// hashmap ¿¡ ÀúÀåµÈ µ¥ÀÌÅÍ °¢ º¯¼ö¿¡ ´ëÀÔ
		placeAndTime = h.get(data_str[0])[0][0].placeAndTime;				
		whereToGo = h.get(data_str[0])[0][0].whereToGo;
		placeCost = h.get(data_str[0])[0][0].placeCost;
		englishName = h.get(data_str[0])[0][0].englishName;
		
		
		// ¸ñÀûÁö Ãß°¡ ½Ã ¸Å°³º¯¼ö 1 ³Ñ°Ü¹ŞÀ½
		if (n == 1)
		{	
			String tmpPlace="";											// º¯°æÇÒ ¸ñÀûÁö¸í               
			String placeEng="";											// »õ·Î¿î ¸ñÀûÁö¸í(¿µ¾î)                
			String[] tmpTime = {};										// »õ·Î¿î ½Ã°£´ë ´ãÀ» ¹è¿­         
			int tmpCost=0;												// »õ·Î¿î ¸ñÀûÁö ¿ä±İ    						   
			String addPlaceYesNo = "n";									// Å¾½ÂÀÚ·ÎºÎÅÍ ÀÔ·Â¹ŞÀ» °ª (Y/N)
			boolean flag;

			do															       
			{
			
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
				//List<String> goToDesti = whereToGo;						// whereToGo(¸ñÀûÁö ¹è¿­) list ·Î º¯È¯(¹è¿­Àº ±æÀÌ¸¦ ´Ã¸± ¼ö ¾ø±â ¶§¹®)

				System.out.println("\n\t\t <<¸ñÀûÁö ¸®½ºÆ®>>");
				System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
				for (int j=0; j<whereToGo.size(); j++)
				{
					System.out.printf("%4s", whereToGo.get(j));
				}
				System.out.println();
				System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");

				do
				{
					flag=true;
					System.out.print("Ãß°¡ÇÒ ¸ñÀûÁö¸¦ ÇÑ±Û¸íÀ¸·Î ÀÔ·ÂÇØÁÖ¼¼¿ä. (¡ºctrl+z¡»ÀÔ·Â ¡æ °ü¸®ÀÚ ¸ŞÀÎÈ­¸é) : ");
					try
					{
						tmpPlace = br.readLine();
						flag = Pattern.matches("^[¤¡-¤¾°¡-ÆR]*$", tmpPlace);

						if (!flag)
							System.out.println(">> ÇÑ±Û¸íÀ¸·Î ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
						else if ("".equals(tmpPlace))
						{
							System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
							flag=false;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
						try 
						{
							Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
				
				if (whereToGo.contains(tmpPlace))						// ÀÔ·Â ¹ŞÀº ¸ñÀûÁö°¡ ¸ñÀûÁö ¹è¿­¿¡ ÀÖ´ÂÁö È®ÀÎ
				{
					System.out.println(">> ÀÌ¹Ì Á¸ÀçÇÏ´Â ¸ñÀûÁö ÀÔ´Ï´Ù. ");
					System.out.print(">> »õ·Î¿î ¸ñÀûÁö¸¦ ´Ù½Ã ÀÔ·Â ÇÏ½Ã°Ú½À´Ï±î?(Y/N)(¡ºY¡» ÀÌ¿Ü ÀÔ·Â ¡æ °ü¸®ÀÚ ¸ŞÀÎ È­¸é) : ");
					String c = br.readLine();	
					if (c.equalsIgnoreCase("Y"))
					{
						addPlace(1);
						return;
					}
					else
					{
						System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
						try 
						{
							Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
					System.out.print("\nÃß°¡ÇÒ ¸ñÀûÁö¸¦ ¿µ¹®¸íÀ¸·Î ÀÔ·ÂÇØÁÖ¼¼¿ä. (¡ºctrl+z¡»ÀÔ·Â ¡æ °ü¸®ÀÚ ¸ŞÀÎÈ­¸é) : ");
					try
					{
						placeEng = br.readLine();
						flag = Pattern.matches("^[a-zA-Z]*$", placeEng);

						if (!flag)
							System.out.println(">> ¿µ¹®¸íÀ¸·Î ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
						else if ("".equals(placeEng))
						{
							System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
							flag=false;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
						try 
						{
							Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
					System.out.print("\nÃß°¡ÇÒ ¸ñÀûÁöÀÇ Ç×°ø±Ç ±İ¾×À» ÀÔ·ÂÇØÁÖ¼¼¿ä : ");						
					try
					{
						tmpCost = Integer.parseInt(br.readLine());
						if (tmpCost < 35000 || tmpCost > 50000000)
						{
							System.out.print(">> ±İ¾×ÀÌ ¹üÀ§¿¡ ÀûÇÕÇÏÁö ¾Ê½À´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
							System.out.println();
							flag=true;
						}
					}
					catch (NumberFormatException e)
					{
						System.out.print(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
						System.out.println();
						flag=true;
					}
				}
				while (flag);						
					
				do
				{
					flag=false;
					System.out.print("\nÃß°¡ÇÒ ¸ñÀûÁöÀÇ Ãâ¹ß ½Ã°£À» ÀÔ·ÂÇØÁÖ¼¼¿ä.(°ø¹é ±¸ºĞ)(ex. 06:00 12:00 18:00) : ");
					try
					{
						tmpTime = br.readLine().split(" ");

						if (tmpTime.length!=3)
						{
							System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
							flag=true;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
						flag=true;
					}
					
				} 
				while (flag);

				System.out.println("\n\n\t<<Ãß°¡ÇÑ ¸ñÀûÁö ³»¿ë È®ÀÎ>>");
				System.out.println("- ¸ñÀûÁö(ÇÑ±Û¸í) : " + tmpPlace);
				System.out.println("- ¸ñÀûÁö(¿µ¹®¸í) : " + placeEng);
				System.out.println("- Ç×°ø±Ç ±İ¾× : " + tmpCost);
				System.out.print("- Ãâ¹ß ½Ã°£ : ");
				for (int i=0 ; i<tmpTime.length ;i++)
				{
				   System.out.print(tmpTime[i]);
				   if (i!=(tmpTime.length-1))
				   {
					  System.out.print(", ");
				   }
				}
				System.out.println();

				System.out.print("\nÈ®Á¤ÇÏ½Ã°Ú½À´Ï±î?\n");
				System.out.print(">> È®Á¤ ½Ã ¡ºY¡» ÀÔ·ÂÇØÁÖ¼¼¿ä. (¡º-1¡»ÀÔ·Â¡æ°ü¸®ÀÚ ¸ŞÀÎÈ­¸é) : ");
				addPlaceYesNo = br.readLine();

				if (addPlaceYesNo.equals("-1"))
				{
					System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
					try 
					{
						Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
				   System.out.println(">> Ãë¼ÒÇÕ´Ï´Ù. Ãß°¡ÇÒ ¸ñÀûÁö¸¦ ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				   continue;
				}
				else
				{
					// ArrayList ¿¡ ÀÔ·Â ¹ŞÀº µ¥ÀÌÅÍ Ãß°¡
					placeAndTime.add(tmpTime);
					whereToGo.add(tmpPlace);
					placeCost.add(tmpCost);
					englishName.add(placeEng);
					
					

					// »õ·Î Ãß°¡µÈ ¸ñÀûÁö¹æ¿¡ new Seat Å¬·¡½º ´ëÀÔ
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


					// hashmap ¿¡ ÀÔ·Â ¹ŞÀº µ¥ÀÌÅÍ Ãß°¡
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

					System.out.printf(">> %s(%s)Çà ºñÇà±â ¿¹¾àÀÌ °¡´ÉÇØÁ³½À´Ï´Ù.\n", tmpPlace, placeEng);
				}

				
			}
			while (!addPlaceYesNo.equalsIgnoreCase("Y"));

			System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			try 
			{
				Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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



	// ¸ñÀûÁö º¯°æ ¸Ş¼Òµå
	public void change() throws IOException, ClassNotFoundException
	{	
		String oldPlace="";												// º¯°æÇÒ ¸ñÀûÁö¸í
		String newPlace="";												// »õ·Î¿î ¸ñÀûÁö¸í
		String placeEng="";												// »õ·Î¿î ¸ñÀûÁö¸í(¿µ¾î)
		int tmpCost=0;													// »õ·Î¿î ¸ñÀûÁö ¿ä±İ
		int n=0;														// ¸ñÀûÁö ¹è¿­ index
		String con = "Y";												// Å¾½ÂÀÚ·ÎºÎÅÍ ÀÔ·Â ¹ŞÀ» °ª (Y/N)
		String[] tmpTime = {};											// »õ·Î¿î ½Ã°£´ë ´ãÀ» ¹è¿­
		boolean flag;	

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//List<String> goToDesti = whereToGo;								

		System.out.println("\n\t\t <<¸ñÀûÁö ¸®½ºÆ®>>");
		System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
		for (int j=0; j<whereToGo.size(); j++)
		{
			System.out.printf("%4s", whereToGo.get(j));
		}
		System.out.println();
		System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");

		do
		{
			flag=false;
			System.out.print("\nº¯°æÇÒ ¸ñÀûÁö¸¦ ÇÑ±Û¸íÀ¸·Î ÀÔ·ÂÇØÁÖ¼¼¿ä. (¡ºctrl+z¡»ÀÔ·Â ¡æ °ü¸®ÀÚ ¸ŞÀÎÈ­¸é) : ");
			try
			{
				oldPlace = br.readLine();
				flag = Pattern.matches("^[¤¡-¤¾°¡-ÆR]*$", oldPlace);

				if (!flag)
					System.out.println(">> ÇÑ±Û¸íÀ¸·Î ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				else if ("".equals(oldPlace))
				{
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					flag=false;
				}
			}
			catch (Exception e)
			{
				System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
				try 
				{
					Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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

		
		if (!whereToGo.contains(oldPlace))								// ÀÔ·Â ¹ŞÀº ¸ñÀûÁö°¡ ¸ñÀûÁö ¹è¿­¿¡ ÀÖ´ÂÁö È®ÀÎ
		{
			System.out.println(">> º¯°æÇÒ ¸ñÀûÁö°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
			System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			try 
			{
				Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
								System.out.println(">> º¯°æÀÌ ºÒ°¡´ÉÇÑ ¸ñÀûÁö ÀÔ´Ï´Ù.");
								System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
								try 
								{
									Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
				System.out.print("\n»õ·Î¿î ¸ñÀûÁö¸¦ ÇÑ±Û¸íÀ¸·Î ÀÔ·ÂÇØÁÖ¼¼¿ä. (¡ºctrl+z¡»ÀÔ·Â ¡æ °ü¸®ÀÚ ¸ŞÀÎÈ­¸é) : ");
				try
				{
					newPlace = br.readLine();
					flag = Pattern.matches("^[¤¡-¤¾°¡-ÆR]*$", newPlace);

					if (!flag)
						System.out.println(">> ÇÑ±Û¸íÀ¸·Î ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					else if ("".equals(newPlace))
					{
						System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
						flag=false;
					}
				}
				catch (Exception e)
				{
					System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
					try 
					{
						Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
			

			if (whereToGo.contains(newPlace))							// »õ·Î¿î ¸ñÀûÁö°¡ ¹è¿­¿¡ Á¸ÀçÇÏ´Â ¸ñÀûÁöÀÎÁö È®ÀÎ
			{
					System.out.println(">> ÀÌ¹Ì Á¸ÀçÇÏ´Â ¸ñÀûÁö ÀÔ´Ï´Ù.");
					
					System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
					try 
					{
						Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
				System.out.print("\n»õ·Î¿î ¸ñÀûÁö¸¦ ¿µ¹®¸íÀ¸·Î ÀÔ·ÂÇØÁÖ¼¼¿ä. (¡ºctrl+z¡»ÀÔ·Â ¡æ °ü¸®ÀÚ ¸ŞÀÎÈ­¸é) : ");
				try
				{
					placeEng = br.readLine();
					flag = Pattern.matches("^[a-zA-Z]*$", placeEng);

					if (!flag)
						System.out.println(">> ¿µ¹®¸íÀ¸·Î ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					else if ("".equals(placeEng))
					{
						System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
						flag=false;
					}
				}
				catch (Exception e)
				{
					System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
					try 
					{
						Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
				System.out.print("\n»õ·Î¿î ¸ñÀûÁöÀÇ Ãâ¹ß ½Ã°£´ë¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.(°ø¹é ±¸ºĞ)(ex. 06:00 08:00 11:00P) : ");
				try
				{
					tmpTime = br.readLine().split(" ");

					if (tmpTime.length!=3)
					{
						System.out.print(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
						flag=true;
					}
				}
				catch (Exception e)
				{
					System.out.print(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					flag=true;
				}
				
			} 
			while (flag);

			

			do																	
			{																	
				flag=false;															
				System.out.print("\n»õ·Î¿î ¸ñÀûÁöÀÇ Ç×°ø±Ç ±İ¾×À» ÀÔ·ÂÇØÁÖ¼¼¿ä. : ");						
				try
				{
					tmpCost = Integer.parseInt(br.readLine());
					if (tmpCost < 35000 || tmpCost > 50000000)
					{
						System.out.print(">> ±İ¾×ÀÌ ¹üÀ§¿¡ ÀûÇÕÇÏÁö ¾Ê½À´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
						System.out.println();
						flag=true;
					}
				}
				catch (NumberFormatException e)
				{
					System.out.print(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					System.out.println();
					flag=true;
				}
			}
			while (flag);						


					
			do
			{
				try
				{									
					System.out.printf("\n%s¸¦ %s(À¸)·Î ¸ñÀûÁö¸¦ º¯°æÇÏ½Ã°Ú½À´Ï±î?(Y/N) : ", oldPlace, newPlace);
					con = br.readLine();
					if (con.equals("y") || con.equals("Y"))
					{
						
						whereToGo.set(n,newPlace);
						englishName.set(n, placeEng);
						placeAndTime.set(n, tmpTime);
						placeCost.set(n, tmpCost);

						
						System.out.print(">> º¯°æÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù.\n");
						mg_main();
					}
					else
					{
						System.out.print(">> º¯°æÀÌ Ãë¼ÒµÇ¾ú½À´Ï´Ù.\n");
						System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
						try 
						{
							Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");

				}
			}
			
			while (true);
		}	
	}	

	
	// ¸ÅÃâÈ®ÀÎ ¸Ş´º ¼±ÅÃ ¸Ş¼Òµå
	protected void summaryPass() throws IOException, ClassNotFoundException
	{	
		int choose;														// ¸Ş´º ¼±ÅÃ ¼ıÀÚ º¯¼ö
		boolean flag;

		do
		{ 
			System.out.println();
			System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
			System.out.println("\t\t\tSsangyong Air");
			System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
			System.out.println();
			System.out.println(" 1. ÀÏÀÚº° ¸ÅÃâ");
			System.out.println(" 2. ¸ñÀûÁöº° ¸ÅÃâ");
			System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("\nÀÌ¿ëÇÏ½Ç ¼­ºñ½º¸¦ ¼±ÅÃÇØÁÖ¼¼¿ä. (¡º-1¡» ÀÔ·Â ¡æ °ü¸®ÀÚ ¸ŞÀÎÈ­¸é) : ");
	
			try
			{
				choose = Integer.parseInt(br.readLine());			
				if ((choose<1 || choose>2)&& choose !=-1)				// ¸Ş´º¿¡ ÀÖ´Â ¼ıÀÚ¿Í -1 ¸¸ ÀÔ·Â °¡´ÉÇÏµµ·Ï ÇÔ (-1: ¿¹¾àÀÚ ¸Ş´º·Î ÀÌµ¿)
				{
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				choose=-5;
			}
		}
		while ((choose<1 || choose>2) && choose != -1);
		
		if (choose==-1)
		{
			System.out.println(">> °ü¸®ÀÚ ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			try 
			{
				Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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

	// ¸ÅÃâ È®ÀÎ ¸Ş¼Òµå(¸Ş´º ¼±ÅÃ ½Ã ÀÔ·Â ¹ŞÀº ¼ıÀÚ¸¦ ¸Å°³º¯¼ö·Î ÀÌ¿ë)
	void summary(int ch) throws IOException, ClassNotFoundException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		String[] fileNamesarr = new String[h.size()];					// ³¯Â¥¸¦ ´ãÀ» ¹è¿­
		ArrayList<int[]> sumCost = new ArrayList<int[]>();				// ÀÏÀÚº° ¸ÅÃâÀ» ´ãÀ» ArrayList

		int i=0;
		int tmpcost;													// ÀÏÀÚº° ¸ÅÃâ
		int tmpcharge;													// ÀÏÀÚº° Ãë¼Ò ¼ö¼ö·á
		Seat[][] st;													// ÇØ´ç ³¯Â¥ÀÇ ºñÇà±â ÀüÃ¼ Á¤º¸

		for (String key : h.keySet())
		{
			fileNamesarr[i] = key;										// fileNamesarr ¹è¿­¿¡ ³¯Â¥(hashmap key°ª) ´ãÀ½
			i++;
		}
		
		Arrays.sort(fileNamesarr);										// ³¯Â¥ ¿À¸§Â÷¼øÀ¸·Î Á¤·Ä (³¯Â¥ º°·Î ¼ø¼­´ë·Î Ãâ·ÂµÇ±â À§ÇÔ)
		

		// ÀÏÀÚº° ¸ÅÃâ °ü¸®
		if (ch == 1)
		{
			sumCost.clear();											// sumCost °ª ÃÊ±âÈ­										
			for (String day : fileNamesarr)							
			{
				tmpcost=0;												// ³¯Â¥ ¹Ù²ğ ¶§ ¸¶´Ù ÃÊ±âÈ­
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
										// ÃÑ ¸ÅÃâ = ¿¹¾à±İ + À§Å¹¼öÇÏ¹° Ãß°¡ ±İ¾× + Ãë¼Ò ¼ö¼ö·á ±İ¾×
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

				int[] tmpCost = {tmpcost, tmpcharge};					// ÀÏÀÚº° {¸ÅÃâ, Ãë¼Ò¼ö¼ö·á} ´ãÀº ¹è¿­
				sumCost.add(tmpCost);									// {¸ÅÃâ, Ãë¼Ò¼ö¼ö·á} ´ãÀº ¹è¿­À» Arraylist¿¡ Ãß°¡
			}

			System.out.println("\n\t<< ÀÏÀÚº° ¸ÅÃâ >>");
			for (int n=0; n<sumCost.size(); n++)
			{
				System.out.printf("%s  : ", fileNamesarr[n]);

				int[] sc = sumCost.get(n);							
				for (int mn=0; mn<sc.length; mn++)
				{
					if (mn!=0)
					{
						System.out.printf("(Ãë¼Ò ¼ö¼ö·á : %,d¿ø Æ÷ÇÔ)\n", sc[mn]);
						continue;
					}
					System.out.printf("%,d¿ø",sc[mn]);
				}
				
			}
			
			System.out.println("\n°è¼ÓÇÏ·Á¸é Press button...");
			try
			{
				String pause = br.readLine();							// ¿£ÅÍ ÀÔ·Â ½Ã °ü¸®ÀÚ ¸ŞÀÎ ¸Ş´º·Î ÀÌµ¿
				mg_main();
				return;
				
			}
			catch (IOException e)
			{
				mg_main();
				return;
			}

		}

		// ¸ñÀûÁöº° ¸ÅÃâ °ü¸®
		else if (ch == 2)
		{
			sumCost.clear();											// sumCost °ª ÃÊ±âÈ­
			for (int j=0; j<whereToGo.size(); j++)
			{
				tmpcost=0;												// ³¯Â¥ ¹Ù²ğ ¶§ ¸¶´Ù ÃÊ±âÈ­
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
										// ÃÑ ¸ÅÃâ = ¿¹¾à±İ + À§Å¹¼öÇÏ¹° Ãß°¡ ±İ¾× + Ãë¼Ò ¼ö¼ö·á ±İ¾×
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
			
				int[] tmpCost = {tmpcost, tmpcharge};					// ÀÏÀÚº° {¸ÅÃâ, Ãë¼Ò¼ö¼ö·á} ´ãÀº ¹è¿­            
				sumCost.add(tmpCost);									// {¸ÅÃâ, Ãë¼Ò¼ö¼ö·á} ´ãÀº ¹è¿­À» Arraylist¿¡ Ãß°¡
			}


			System.out.println("\n\t<< ¸ñÀûÁöº° ¸ÅÃâ >> ");
			for (int n=0; n<sumCost.size(); n++)
			{
				System.out.printf("%s : ",  whereToGo.get(n));

				int[] sc = sumCost.get(n);
				for (int mn=0; mn<sc.length; mn++)
				{
					if (mn!=0)
					{
						System.out.printf("(Ãë¼Ò ¼ö¼ö·á : %,d¿ø Æ÷ÇÔ)\n", sc[mn]);
						continue;
					}
					System.out.printf("%,d¿ø",sc[mn]);
				}
				
			}
			System.out.println("\n°è¼ÓÇÏ·Á¸é Press button...");
			try
			{
				String pause = br.readLine();							// ¿£ÅÍ ÀÔ·Â ½Ã °ü¸®ÀÚ ¸ŞÀÎ ¸Ş´º·Î ÀÌµ¿
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
	

	// °´Ã¼ Á÷·ÄÈ­ ¸Ş¼Òµå 
	public void save() throws IOException, ClassNotFoundException
	{
		Calendar cal1 = Calendar.getInstance();					

		Integer d = cal1.get(Calendar.DATE);
		Integer m = cal1.get(Calendar.MONTH) + 1;
		Integer y = cal1.get(Calendar.YEAR);

		String t = String.format("%04d", y) + String.format("%02d", m) + String.format("%02d", d);	
		String time = t + ".ser";										

		String appDir = System.getProperty("user.dir");					
		File f1 = new File(appDir + "\\DataBase\\");					// "DataBase" Æú´õ »ı¼º
		
		File f0 = new File(appDir +  "\\DataBase\\", time);				// ¿À´Ã ³¯Â¥¸¦ ÆÄÀÏ¸íÀ¸·Î ÇÏ´Â ÆÄÀÏ »ı¼º

		if (!f0.getParentFile().exists())
		{
			f0.getParentFile().mkdirs();
		}

		FileOutputStream fos = new FileOutputStream(f0);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(h);												// °´Ã¼ Á÷·ÄÈ­
		oos.close();
		fos.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("\nÇÁ·Î±×·¥À» Á¾·áÇÏ½Ã°Ú½À´Ï±î? (¡ºY¡» ÀÌ¿Ü ÀÔ·Â ¡æ °ü¸®ÀÚ ¸ŞÀÎ È­¸é) : ");
		String outcheck = br.readLine();

		if (outcheck.equalsIgnoreCase("Y"))
			out();
		else
			mg_main();
	}
	

	// ÇÁ·Î±×·¥ Á¾·á ¸Ş¼Òµå
	public static void out()
	{
		System.out.println("\n>> ÇÁ·Î±×·¥ Á¾·á~!!!");
		System.exit(-1);
	}
	
// ===================== °ü¸®ÀÚ °ü·Ã ¸Ş¼Òµå ³¡ =================================


// ===================== ¿¹¾àÀÚ °ü·Ã ¸Ş¼Òµå ½ÃÀÛ ================================


	int num;														// ¸ŞÀÎÈ­¸é¿¡¼­ ¿¹¾à, Á¶È¸, Ãë¼Ò ¼ıÀÚ ¼±ÅÃÇÒ ¶§ »ç¿ëÇÒ º¯¼ö
	int idxPlace, idxTime;											// ¸ñÀûÁö, ½Ã°£´ë °ü·Ã ¹è¿­¿¡ ÇØ´çÇÏ´Â index
	int selDay;														// ¿¹¾àÀÚ°¡ ¼±ÅÃÇÒ ³¯Â¥(¼ıÀÚ) 
	String startDay;												// ¿¹¾àÀÚ°¡ ¼±ÅÃÇÒ ³¯Â¥(³¯Â¥ Çü½ÄÀÇ ¹®ÀÚ¿­)	
	String place;													// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÒ ¸ñÀûÁö 
	Queue<PassengerInfo> qu = new LinkedList<PassengerInfo>();		// ¿µ¼öÁõÀ» Ãâ·ÂÇÒ ¶§ »ç¿ëÇÒ º¯¼ö. Áö±İ ¿¹¾àÇÑ °Í¸¸ Queue¿¡ ´ã¾Æ¼­ Ãâ·Â (¾Õ»ç¶÷ ¿µ¼öÁõ±îÁö °°ÀÌ ³ª¿ÀÁö ¾Êµµ·Ï)
	String tmpcard;													// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÒ Ä«µå¹øÈ£
	String tmpcardpw;												// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÒ Ä«µå ºñ¹Ğ¹øÈ£
	char[] tmpcd ;													// Ä«µå¹øÈ£ ´ãÀ» ¹®ÀÚ¹è¿­
	String con="";													// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÒ ¹®ÀÚ(Y/N)
	


	void reserve() throws Exception
	{
		System.out.println("\n\n¿¹¾à ¼­ºñ½º¸¦ ¼±ÅÃÇÏ¼Ì½À´Ï´Ù.");
		System.out.println("\n³»ÀÏºÎÅÍ ´ÙÀ½ÁÖ±îÁö, ÀÏÁÖÀÏÀÇ Æ¯°¡ Ç×°øÆíÀ» º¸¿©µå¸³´Ï´Ù.");
		System.out.println("¿øÇÏ½Ã´Â Ãâ±¹³¯Â¥¸¦ ¼±ÅÃ ÈÄ ÇØ´çÀÏÀÇ Ç×°øÆíÀ» È®ÀÎÇÏ½Ç ¼ö ÀÖ½À´Ï´Ù.");


		Calendar now = Calendar.getInstance();						
		System.out.printf("\nÇöÀç ³¯Â¥ : %tF\n", now);				

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// ³¯Â¥ Ãâ·Â(³»ÀÏ~ ÀÏÁÖÀÏ ÈÄ)

		System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String[] data_str = new String[7];							// ÀÏÁÖÀÏÄ¡ ³¯Â¥¸¦ ´ã¾ÆµÑ String ¹è¿­ (³ªÁß¿¡ »ç¿ëÇÒ ¼ö ÀÖµµ·Ï ¿¹ºñ·Î ´ã¾ÆµÒ)
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);									// ¿À´ÃÀÌ ¿ù¿äÀÏÀÌ¸é È­¿äÀÏºÎÅÍ Ãâ·ÂÇÏµµ·Ï ÇÔ
		date = cal.getTime();
		
        for (int i = 0; i < 7; i++)
		{
            System.out.println((i+1)+". "+ formatter.format(date));
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            date = cal.getTime();
			data_str[i] = formatter.format(date);
		}
		System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
		
		// ³¯Â¥ ¼±ÅÃ
		do
		{

			System.out.printf("\n³¯Â¥ ¼±ÅÃ : ");
			try
			{
				selDay = Integer.parseInt(br.readLine()) ;				

				if (selDay < 1 || selDay > 7)
				{
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");	
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä."); 
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
		
		System.out.printf("\n\n>> ¼±ÅÃÇÑ ³¯Â¥ : %tF\n", selectCal);	

		// ¸ñÀûÁöº° °¡°İ Ãâ·Â
		do
		{
			System.out.println("\n\t\t ¸ñÀûÁöº° °¡°İ");
			System.out.println("\t\t(Economy class)");
			System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
			for (int j=0; j<whereToGo.size(); j++)
			{
				System.out.printf("%4s : %,7d", whereToGo.get(j), placeCost.get(j));
				if (j!=0 && j%2==0 && j!=(whereToGo.size()-1))
				{
					System.out.println();
				}
			}
			System.out.println();
			System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");

			// ¸ñÀûÁö ¼±ÅÃ
			System.out.printf("\n¸ñÀûÁö ¼±ÅÃ (");
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
				System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");				
			}

		}
		while (idxPlace==-1);
		
		// Ãâ¹ß ½Ã°£´ë ¼±ÅÃ 
		System.out.printf("\n%tF %sÇà Ç×°øÆí ¸ñ·Ï\n", selectCal, place);

		System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
		for (int j=0; j<placeAndTime.get(idxPlace).length; j++)	
		{
			System.out.printf(" %d. %s\n", j+1, placeAndTime.get(idxPlace)[j]);			
		}
		System.out.println("¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡");
		System.out.println();
		do
		{
			System.out.print("\n¿øÇÏ´Â ½Ã°£À» ¼±ÅÃÇÏ¼¼¿ä (¹øÈ£ ÀÔ·Â) : ");
			try
			{
				idxTime = Integer.parseInt(br.readLine()) - 1;			

				if (idxTime < 0 || idxTime > placeAndTime.size() - 1)
				{
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				idxTime = -1;
			}

		}
		while (idxTime<0 || idxTime>=placeAndTime.get(idxPlace).length);
		

		
		System.out.printf(">> %tF %sÇà %s ºñÇà±â¸¦ ¼±ÅÃÇÏ¼Ì½À´Ï´Ù.\n\n", selectCal, place, placeAndTime.get(idxPlace)[idxTime]);

		// ÁÂ¼® ¼±ÅÃ ¸Ş¼Òµå È£Ãâ
		seatSelect();

		// °áÁ¦ ¸Ş¼Òµå È£Ãâ (°áÁ¦ ¼º°ø ¡æ 1 ¹İÈ¯)
		//					(Ä«µå¹øÈ£ ¹× Ä«µå ºñ¹Ğ¹øÈ£ ÀÔ·Â ½ÇÆĞ ¡æ 0 ¹İÈ¯)
		int b = payMent();												
	
		// °áÁ¦ ¼º°ø ½Ã ¿¹¾àÈ®Á¤ ¿©ºÎ È®ÀÎ
		if (b == 1)
		{
			do
			{
				flag=false;
				try
				{
					System.out.print("\n¿¹¾àÀ» È®Á¤ÇÏ°Ú½À´Ï±î?(Y/N) : ");
					con = br.readLine();
					if (!con.equalsIgnoreCase("Y") && !con.equalsIgnoreCase("N"))
					{
						flag=true;
					}
				}
				catch (Exception e)
				{
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					flag=true;
				}
			}
			while (flag);
			
			// ¿¹¾à È®Á¤ÇÏ°Ú´Ù°í ÇÒ ¶§
			if (con.equals("Y") || con.equals("y"))
			{

				for (int[] s : Seat.tmpseat)		// passInfor()¿¡¼­´Â ÁÂ¼® ¹øÈ£¸¦ ¸Å°³º¯¼ö·Î ¹Ş¾Æ ÁÂ¼® Á¤º¸¸¦ ¹Ù·Î hashmap¿¡ ÀúÀåÇÒ ¼ö ÀÖ¾úÁö¸¸
													// payMent() ¿¡¼­´Â ¸Å°³º¯¼ö¸¦ ¹ŞÁö ¾Ê±â ¶§¹®¿¡ ÀÓ½Ã ÁÂ¼® ¹è¿­(tmpseat)¿¡ ÀúÀåµÈ ÁÂ¼®À» ÀÌ¿ëÇÑ´Ù.
				{
					// hashmap ÀÇ cardNumber º¯¼ö(PassengerInfo)¿¡ »ç¿ëÀÚ Ä«µå¹øÈ£ ³Ö±â
					h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardNumber(tmpcard);
					// hashmap ÀÇ cardPW º¯¼ö(PassengerInfo)¿¡ »ç¿ëÀÚ Ä«µå ºñ¹Ğ¹øÈ£ ³Ö±â
					h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardPW(tmpcardpw);
				}

				// print() È£ÃâÇÏ¿© ¿µ¼öÁõ Ãâ·Â
				print(place, placeAndTime.get(idxPlace)[idxTime]);
			}
			// ¿¹¾à È®Á¤ ¾ÈÇÏ°Ú´Ù°í ÇÒ ¶§
			else
			{
				// qu(Queue) ¿¡ ´ã±ä Å¾½ÂÀÚ Á¤º¸ ºñ¿ì±â 
				qu.clear();			
				
				for (int[] s : Seat.tmpseat)
				{
					// tmpseat ¿¡ ´ã±ä ¸ğµç ÁÂ¼®ÀÇ boolean °ª false ´ëÀÔ
					h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
					// tmpseat ¿¡ ´ã±ä ¸ğµç ÁÂ¼®ÀÇ ¿¹¾àÀÚ ÀÌ¸§ null ´ëÀÔ
					h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
					// ÇØ´ç ºñÇà±â ÀÜ¿©ÁÂ¼® ¼ö Áõ°¡
					++h.get(startDay)[idxPlace][idxTime].seatLeft;
					
				}
				
				do
				{
					flag=false;
					try
					{
						System.out.print("¿¹¾àÀ» ´Ù½Ã ÇÏ½Ã°Ú½À´Ï±î?(Y/N) : ");
						con = br.readLine();
						if (!con.equalsIgnoreCase("Y") && !con.equalsIgnoreCase("N"))
						{
							flag=true;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
						flag=true;
					}
				}
				while (flag);

				// ¿¹¾à ´Ù½Ã ÇÏ°Ú´Ù°í ÇÒ ¶§ ÁÂ¼® ¼±ÅÃºÎÅÍ ´Ù½Ã ½ÃÇà
				if (con.equalsIgnoreCase("Y"))
				{
					// ÁÂ¼®¼±ÅÃ ¸Ş¼Òµå È£Ãâ
					 seatSelect();
					// °áÁ¦ ¸Ş¼Òµå È£Ãâ
					 b = payMent();

					 // µÎ¹øÂ° ¿¹¾à °áÁ¦ ¼º°ø ½Ã ¿¹¾à È®Á¤ ¿©ºÎ È®ÀÎ
					if (b == 1)
					{
						do
						{
							flag=false;
							try
							{
								System.out.print("¿¹¾àÀ» È®Á¤ÇÏ°Ú½À´Ï±î?(Y/N : N(n) ÀÔ·Â½Ã ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.) : ");
								con = br.readLine();
								if (!con.equalsIgnoreCase("Y") && !con.equalsIgnoreCase("N"))
								{
									flag=true;
								}
							}
							catch (Exception e)
							{
								System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
								flag=true;
							}
						}
						while (flag);
						

						if (con.equals("Y") || con.equals("y"))
						{
							for (int[] s : Seat.tmpseat)
							{
								// hashmap ÀÇ cardNumber º¯¼ö(PassengerInfo)¿¡ »ç¿ëÀÚ Ä«µå¹øÈ£ ³Ö±â
								h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardNumber(tmpcard);
								// hashmap ÀÇ cardPW º¯¼ö(PassengerInfo)¿¡ »ç¿ëÀÚ Ä«µå ºñ¹Ğ¹øÈ£ ³Ö±â
								h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setCardPW(tmpcardpw);
							}
							// print() È£ÃâÇÏ¿© ¿µ¼öÁõ Ãâ·Â
							print(place, placeAndTime.get(idxPlace)[idxTime]);
						}
						// µÎ¹øÂ° ¿¹¾à ÈÄ ¿¹¾à È®Á¤ ¾ÈÇÏ°Ú´Ù°í ÇÒ ¶§
						else
						{
							// qu(Queue) ¿¡ ´ã±ä Å¾½ÂÀÚ Á¤º¸ ºñ¿ì±â
							qu.clear();
							for (int[] s : Seat.tmpseat)
							{
								// tmpseat ¿¡ ´ã±ä ¸ğµç ÁÂ¼®ÀÇ boolean °ª false ´ëÀÔ
								h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
								// tmpseat ¿¡ ´ã±ä ¸ğµç ÁÂ¼®ÀÇ ¿¹¾àÀÚ ÀÌ¸§ null ´ëÀÔ
								h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
								// ÇØ´ç ºñÇà±â ÀÜ¿©ÁÂ¼® ¼ö Áõ°¡
								++h.get(startDay)[idxPlace][idxTime].seatLeft;

								System.out.println(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
								try 
								{
									Thread.sleep(750); //25¹Ğ¸®ÃÊ 
								} 
								catch (InterruptedException e) 
								{
									e.printStackTrace();
								}
								return;
							}
						}
					}
					// µÎ¹øÂ° ¿¹¾à ÈÄ °áÀç ½ÇÆĞ ÇßÀ» ¶§
					
					// ¿¹¾à ´Ù½Ã ¾ÈÇÏ°Ú´Ù°í ÇÒ ¶§ ¿¹¾àÀÚ ¸ŞÀÎ ÀÌµ¿
					else
					{
					
						// qu(Queue) ¿¡ ´ã±ä Å¾½ÂÀÚ Á¤º¸ ºñ¿ì±â
						qu.clear();
						for (int[] s : Seat.tmpseat)
						{
							// tmpseat ¿¡ ´ã±ä ¸ğµç ÁÂ¼®ÀÇ boolean °ª false ´ëÀÔ
							h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
							// tmpseat ¿¡ ´ã±ä ¸ğµç ÁÂ¼®ÀÇ ¿¹¾àÀÚ ÀÌ¸§ null ´ëÀÔ
							h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
							// ÇØ´ç ºñÇà±â ÀÜ¿©ÁÂ¼® ¼ö Áõ°¡
							++h.get(startDay)[idxPlace][idxTime].seatLeft;
						}
						System.out.println(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
						try 
						{
							Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
					// qu(Queue) ¿¡ ´ã±ä Å¾½ÂÀÚ Á¤º¸ ºñ¿ì±â
					qu.clear();
					for (int[] s : Seat.tmpseat)
					{
						// tmpseat ¿¡ ´ã±ä ¸ğµç ÁÂ¼®ÀÇ boolean °ª false ´ëÀÔ
						h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
						// tmpseat ¿¡ ´ã±ä ¸ğµç ÁÂ¼®ÀÇ ¿¹¾àÀÚ ÀÌ¸§ null ´ëÀÔ
						h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
						// ÇØ´ç ºñÇà±â ÀÜ¿©ÁÂ¼® ¼ö Áõ°¡
						++h.get(startDay)[idxPlace][idxTime].seatLeft;
					}
					System.out.println(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
					try 
					{
						Thread.sleep(750); //25¹Ğ¸®ÃÊ 
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					return;
				}
			}
		}
		else // Ã¹¹øÂ° ¿¹¾à °áÁ¦ ½ÇÆĞ ½Ã 
		{
			qu.clear();
			for (int[] s : Seat.tmpseat)
			{
				// tmpseat ¿¡ ´ã±ä ¸ğµç ÁÂ¼®ÀÇ boolean °ª false ´ëÀÔ
				h.get(startDay)[idxPlace][idxTime].seatB[s[0]][s[1]] = false;
				// tmpseat ¿¡ ´ã±ä ¸ğµç ÁÂ¼®ÀÇ ¿¹¾àÀÚ ÀÌ¸§ null ´ëÀÔ
				h.get(startDay)[idxPlace][idxTime].seatP[s[0]][s[1]].setName(null);
				// ÇØ´ç ºñÇà±â ÀÜ¿©ÁÂ¼® ¼ö Áõ°¡
				++h.get(startDay)[idxPlace][idxTime].seatLeft;
			}
			System.out.println(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			try 
			{
				Thread.sleep(750); //25¹Ğ¸®ÃÊ 
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			return;														// ¿¡¾àÀÚ ¸ŞÀÎ ÀÌµ¿
		}
	}
	
	// ÁÂ¼® ¼±ÅÃ ¸Ş¼Òµå (hashmap ¿¡ ÀÖ´Â ºñÇà±â Á¤º¸(Seat[][]) ÀÌ¿ë)
	void seatSelect() throws IOException 		
	{

		String seat;												// ¿¹¾àÀÚ°¡ ¼±ÅÃÇÑ ÁÂ¼® ¹øÈ£
		int row, col;												// ¼±ÅÃÇÑ ÁÂ¼®ÀÇ ¿­, Çà 
		int inwon;													// ¿¹¾à ÀÎ¿ø  ¼ö


		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		Seat.tmpseat.clear();										// ArrayList tmpseatÀ» ÇÑ ¹ø ÃÊ±âÈ­ ÇØ¾ß ÀüÃ¼ ¿¹¾àÀÌ Ãë¼ÒµÇÁö ¾ÊÀ½

		
		// ¿¹¾à ÀÎ¿ø ¼ö ÀÔ·Â (ÀÜ¿© ÁÂ¼®¼ö º¸´Ù ÀûÀ¸¸ç ÃÖ´ë 4¸í±îÁö ¿¹¾à °¡´É)
		System.out.printf("ÀÎ¿ø ¼ö¸¦ ÀÔ·ÂÇÏ¼¼¿ä. ÇÑ ¹ø¿¡ 4¸í±îÁö ¿¹¾àÀÌ °¡´ÉÇÕ´Ï´Ù.(ÀÜ¿© ÁÂ¼® ¼ö: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);

		
		do
		{
			try
			{
				inwon = Integer.parseInt(br.readLine());

				if (inwon> h.get(startDay)[idxPlace][idxTime].seatLeft || inwon>4)
				{
					System.out.printf(">> ÀÌ¿ë °¡´ÉÇÑ ÁÂ¼® ¼ö¸¦ ÃÊ°úÇÑ ÀÎ¿ø ¼ö ÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					System.out.printf("\n\nÀÎ¿ø ¼ö¸¦ ÀÔ·ÂÇÏ¼¼¿ä. ÇÑ ¹ø¿¡ 4¸í±îÁö ¿¹¾àÀÌ °¡´ÉÇÕ´Ï´Ù.(ÀÜ¿© ÁÂ¼® ¼ö: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);

					inwon=-1;
				}
				else if (inwon <1)
				{
					System.out.printf(">> 1 ÀÌ»óÀÇ ¼ıÀÚ·Î ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä. ");
					System.out.printf("\n\nÀÎ¿ø ¼ö¸¦ ÀÔ·ÂÇÏ¼¼¿ä. ÇÑ ¹ø¿¡ 4¸í±îÁö ¿¹¾àÀÌ °¡´ÉÇÕ´Ï´Ù.(ÀÜ¿© ÁÂ¼® ¼ö: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);
				}
				else 
					break;
			}
			catch (NumberFormatException e)
			{
				System.out.printf(">> ¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·ÂÀÔ´Ï´Ù.´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				System.out.printf("\n\nÀÎ¿ø ¼ö¸¦ ÀÔ·ÂÇÏ¼¼¿ä. ÇÑ ¹ø¿¡ 4¸í±îÁö ¿¹¾àÀÌ °¡´ÉÇÕ´Ï´Ù.(ÀÜ¿© ÁÂ¼® ¼ö: %d) : ",  h.get(startDay)[idxPlace][idxTime].seatLeft);

				inwon = -1;
			}
			
		}
		while (inwon <=-1);

		// ÁÂ¼® ÇöÈ² Ãâ·Â
		for (int i=0; i<inwon ; i++)															// ÀÎ¿ø¼ö ¸¸Å­ ÁÂ¼® ¼±ÅÃ ¹İº¹
		{
			do
			{
				System.out.print("\nÁÂ¼®À» ¼±ÅÃÇÏ¼¼¿ä(ex. A10)\n");
				System.out.print("(¡à : ºó ÁÂ¼®, ¡á : ¼±ÅÃµÈ ÁÂ¼®)\n");
				System.out.printf("%6c  %3c%11c %3c\n",'A','B','C','D');
				System.out.println("  ¢Ã <-ÃâÀÔ¹®");
				for (int j=0; j<h.get(startDay)[idxPlace][idxTime].seatB.length; j++)			// ÇØ´ç ºñÇà±â ÁÂ¼®ÀÇ ÃÑ ÁÂ¼® ¿­(row) ¼ö
				{																		
					if (j%2==1)
					{
						System.out.print("¦¢");													
						System.out.printf("%2d",j+1);
					}

					else
						System.out.printf("%3d",j+1);
					
					for (int k=0; k<h.get(startDay)[idxPlace][idxTime].seatB[j].length; k++)	// ÇØ´ç ºñÇà±â ÁÂ¼®ÀÇ ÃÑ ÁÂ¼® Çà(col) ¼ö
					{
						if (!h.get(startDay)[idxPlace][idxTime].seatB[j][k])					// ÇØ´ç ºñÇà±â ÁÂ¼®ÀÇ ÁÂ¼® ÇöÈ²(true: ¿¹¾àµÈ ÁÂ¼®, false: ¿¹¾à¾È µÈ ÁÂ¼®)			
						{
							System.out.printf("%3s","¡à");
							if (k==1)
							{
								System.out.print("        ");
							}
						}
						else if (h.get(startDay)[idxPlace][idxTime].seatB[j][k])	
						{
							System.out.printf("%3s","¡á");										// ¿¹¾àµÈ ÁÂ¼®Àº "¡á"·Î Ç¥½Ã
							if (k==1)
							{
								System.out.print("        ");
							}
						}
													
					}

					if (j%2==1)
					{
						System.out.print(" ¦¢");
					}
					System.out.println();
				}
				System.out.println();

				// Çà(col) º° ÁÂ¼® µî±Ş ¹× ÁÂ¼® °¡°İ ¾È³»
				System.out.printf("1~2 Çà : First class (%,d¿ø)\n",(int)(placeCost.get(idxPlace) * 1.5));			// first class : ¸ñÀûÁöº° °¡°İ * 1.5						  
				System.out.printf("3~5 Çà : Business class (%,d¿ø)\n",(int)(placeCost.get(idxPlace) * 1.3));		// Business class : ¸ñÀûÁöº° °¡°İ * 1.3
				System.out.printf("6~10 Çà  : Economy class(%,d¿ø)\n",placeCost.get(idxPlace));						// Economy class : ¸ñÀûÁöº° °¡°İ * 1
				System.out.println();
				

				// ÁÂ¼® ¹øÈ£ ÀÔ·Â
				System.out.printf("%d¹øÂ° ÁÂ¼® ¼±ÅÃ(ex. A10) : ", i+1);						
				
				do
				{
					try
					{
						seat = br.readLine();													
					
						switch (seat.substring(0,1))																// ÁÂ¼®¹øÈ£ Áß ¹®ÀÚ(A,B,C,D)¸¸ »©³¿
						{
						case "A": case "a": col = 0; break;
						case "B": case "b": col = 1; break;
						case "C": case "c": col = 2; break;
						case "D": case "d": col = 3; break;
						default : col = -1; break;

						}
						row = Integer.parseInt(seat.substring(1)) - 1;												// ÁÂ¼®¹øÈ£ Áß ¼ıÀÚ¸¸ »©³¿(ÀÎµ¦½º·Î ÀÌ¿ë)

						if (col==-1 || (row<0 || row>=h.get(startDay)[idxPlace][idxTime].seatB.length))						
						{
							System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
							System.out.printf("%d¹øÂ° ÁÂ¼® ¼±ÅÃ : ", i+1);				
						}
					}
					catch (Exception e)
					{
						System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
						System.out.printf("%d¹øÂ° ÁÂ¼® ¼±ÅÃ : ", i+1);
						
						row = col = -1;
					}
	
				}
				while(col == -1 || (row<0 || row>=h.get(startDay)[idxPlace][idxTime].seatB.length)); // Àß¸øµÈ ÀÔ·ÂÀÏ °æ¿ì - Áï, ÁÂ¼® ¿­ÀÌ a~d¸¦ ¹ş¾î³ª°Å³ª ÇàÀÌ 1~10À» ¹ş¾î³¯ °æ¿ì
								
			}
			while (seatCheck(row,col));		// seatCheck(row,col)°¡ true¶ó¸é(¼±ÅÃµÈ ÁÂ¼®ÀÌ¸é ¹İº¹)
											// seatCheck(row,col)°¡ false¶ó¸é(ºñ¾îÀÖ´Â ÁÂ¼®ÀÌ¶ó¸é) 
			passInfor(row,col);				// ¿¹¾àÀÚ Á¤º¸¸¦ ÀÔ·Â ¹ŞÀ½

		}  
	}

	// ÁÂ¼® ÇöÈ² È®ÀÎ ¸Ş¼Òµå
	boolean seatCheck(int row, int col)		
	{
		if (!h.get(startDay)[idxPlace][idxTime].seatB[row][col])						// ÇØ´ç ºñÇà±â ÁÂ¼®ÀÌ ¿¹¾à ¾ÈµÇ¾î ÀÖÀ¸¸é (-> false ¹İÈ¯)
		{
			try
			{
				if (h.get(startDay)[idxPlace][idxTime].seatP[row][col].getName()==null)
				{
					h.get(startDay)[idxPlace][idxTime].seatB[row][col] = true;					// ÇØ´ç ÁÂ¼® true·Î ¹Ù²ãÁÜ.
					h.get(startDay)[idxPlace][idxTime].seatLeft--;
					System.out.println(">> ¼±ÅÃ °¡´ÉÇÑ ÁÂ¼®ÀÔ´Ï´Ù.\n");
					int[] tmp = {row, col};														
					Seat.tmpseat.add(tmp);														// tmpseat¿¡ ÀúÀåÇØµÒ.
					return false;
				}
			}
			catch (NullPointerException e)
			{
				h.get(startDay)[idxPlace][idxTime].seatP[row][col] = new PassengerInfo();	// ÇØ´ç ÁÂ¼®¿¡ PassengerInfo ÀÎ½ºÅÏ½º »ı¼ºÇÏ¿© ¿¹¾àÀÚ Á¤º¸ ´ãÀ½.
				h.get(startDay)[idxPlace][idxTime].seatB[row][col] = true;					// ÇØ´ç ÁÂ¼® true·Î ¹Ù²ãÁÜ.
				h.get(startDay)[idxPlace][idxTime].seatLeft--;
				System.out.println(">> ¼±ÅÃ °¡´ÉÇÑ ÁÂ¼®ÀÔ´Ï´Ù.\n");
				int[] tmp = {row, col};														
				Seat.tmpseat.add(tmp);														// tmpseat¿¡ ÀúÀåÇØµÒ.
				return false;
			}
			
		}
		else if (h.get(startDay)[idxPlace][idxTime].seatB[row][col])					// ÇØ´ç ºñÇà±â ÁÂ¼®ÀÌ ¿¹¾àµÇ¾î ÀÖÀ¸¸é (-> true ¹İÈ¯)
		{
			System.out.println(">> ¼±ÅÃµÈ ÁÂ¼®ÀÔ´Ï´Ù.\n");
			return true;
		}
		return true;
	}


	// ¿¹¾àÀÚ Á¤º¸ ÀÔ·Â ¸Ş¼Òµå(PassengerInfo)
	void passInfor(int row, int col) throws IOException
	{
		String tmpAns = "n";															// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÒ Å¾½ÂÁ¤º¸ ÀÏÄ¡ ¿©ºÎ(Y/N)
		String tmpName="";																// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÒ ÀÌ¸§ 
		String tmpEngLastName="";														// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÒ ¿µ¹®ÀÌ¸§
		String tmpEngFirstName="";														// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÒ ¿µ¹®ÀÌ¸§(¼º)
		String tmpTel="";																// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÒ ÀüÈ­¹øÈ£
		String tmpBag;																	// Å¾½ÂÀÚ°¡ ÀÔ·ÂÇÒ ¼öÇÏ¹° ¿©ºÎ(Y/N)
		int tmpBagNum1=-1;																// 24~28 kg ¼öÇÏ¹° ¼ö·®
		int tmpBagNum2=-1;																// 28~32 kg ¼öÇÏ¹° ¼ö·®
		int tmpBagNum3=0;																// ÃÑ ¼öÇÏ¹° ¼ö·®
		int tmpBagSum=0;																// ÃÑ ¼öÇÏ¹° Ãß°¡ ±İ¾×
		boolean flag=false;																
									
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// ¿¹¾àÀÚ Á¤º¸ ÀÔ·Â(ÀÌ¸§(ÇÑ±Û¸í), ÀÌ¸§(¿µ¹®¸í), ÀüÈ­¹øÈ£)
		System.out.println("<¿¹¾àÀÚ Á¤º¸ ÀÔ·Â>");
		do
		{
			do
			{
				try
				{
					System.out.print("\n¼ºÇÔÀ» ÀÔ·ÂÇÏ¼¼¿ä : ");
					tmpName = br.readLine();
	
					if ("".equals(tmpName))
					{
						System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");	
					}
					else
					{
						flag = Pattern.matches("^[¤¡-¤¾°¡-ÆR]*$", tmpName);					// ÇÑ±Û¸¸ ÀÔ·Â °¡´É
						if (!flag)
								System.out.println(">> ÇÑ±Û¸íÀ¸·Î ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					}
					
				}
				catch (Exception e)
				{
					
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				}
			}
			while (!flag);
			
			
			
			System.out.println("\n¿µ¹® ÀÌ¸§À» ÀÔ·ÂÇÏ¼¼¿ä.");
			
			do
			{
				engFlag=true;
				try
				{
					System.out.print("Last Name(¼º): ");
					tmpEngLastName= br.readLine().toUpperCase();	

					if ("".equals(tmpEngLastName))
					{
						System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
					}
					else
						// ¿µ¾î·Î¸¸ ÀÔ·ÂµÇ¾ú´ÂÁö È®ÀÎÇÏ´Â ¸Ş¼Òµå(checkEngName()) È£Ãâ -> ¸ÂÀ¸¸é ÀÔ·ÂÇÑ ¹®ÀÚ ¹İÈ¯, engFlag = false
						tmpEngLastName = checkEngName(tmpEngLastName);						
				
				}	
				catch (Exception e)
				{
					
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
				}
			}while (engFlag);

			do
			{
				engFlag=true;
				try
				{
					System.out.print("First Name(ÀÌ¸§): ");
					tmpEngFirstName= br.readLine().toUpperCase();	

					if ("".equals(tmpEngFirstName))
					{
						System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
					}
					else
						// ¿µ¾î·Î¸¸ ÀÔ·ÂµÇ¾ú´ÂÁö È®ÀÎÇÏ´Â ¸Ş¼Òµå(checkEngName()) È£Ãâ -> ¸ÂÀ¸¸é ÀÔ·ÂÇÑ ¹®ÀÚ ¹İÈ¯, engFlag = false
						tmpEngFirstName = checkEngName(tmpEngFirstName);					
				
				}	
				catch (Exception e)
				{
					
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
				}
			}while (engFlag);
			
		do
		{
			try
			{
				System.out.print("\nÀüÈ­¹øÈ£¸¦ ÀÔ·ÂÇÏ¼¼¿ä (XXX-XXXX-XXXX): ");
				tmpTel = br.readLine();
				flag = tmpTel.matches("\\d{3}-\\d{4}-\\d{4}");						// ÀüÈ­¹øÈ£ Çü½Ä(xxx-xxxx-xxxx)¸¸ ÀÔ·Â ¹ŞÀ½
				if (!flag)
				{
					System.out.println(">> ÀüÈ­¹øÈ£ Çü½Ä¿¡ ¸Â°Ô ÀÔ·ÂÇØÁÖ¼¼¿ä");
					flag = false;
				}
			}
			catch (Exception e)
			{
				System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
			}
		}while (!flag);

			// À§Å¹¼öÇÏ¹° Ãß°¡ È®ÀÎ 
			do
			{
				flag=false;

				try
				{
					do
					{
						tmpBagSum = 0;
						System.out.print("\nÃß°¡ÇÒ À§Å¹¼öÇÏ¹°ÀÌ ÀÖ½À´Ï±î?(Y/N) : ");	
						tmpBag = br.readLine();
					
						if (tmpBag.equals("y")||tmpBag.equals("Y"))
						{
							// À§Å¹¼öÇÏ¹° ¹«°Ôº° ±İ¾× Ãâ·Â
							System.out.println("\n[À§Å¹¼öÇÏ¹° ±ÔÁ¤ : ¹«°Ôº° Ãß°¡±İ¾×]");
							System.out.println("=============================================");
							System.out.println("24 ~ 28kg : 40,000¿ø");
							System.out.println("28 ~ 32kg : 60,000¿ø");
							System.out.println("¡Ø 32kg ÃÊ°ú½Ã ¼öÇÏ ºÒ°¡, ÃÖ´ë¼ö·® 5°³ Á¦ÇÑ ¡Ø");
							System.out.println("==============================================\n");
							do	
							{
								System.out.print("24kg ÀÌ»óÀÇ À§Å¹¼öÇÏ¹°ÀÌ ÀÖÀ¸½Å°¡¿ä?(Y/N) : ");
								tmpBag = br.readLine();

								if (tmpBag.equals("y")||tmpBag.equals("Y"))				// 24kg ÀÌ»óÀÇ À§Å¹¼öÇÏ¹°ÀÌ ÀÖÀ» ¶§
								{
									do
									{
										System.out.print("24 ~ 28kg À§Å¹¼öÇÏ¹° ¼ö·®À» ÀÔ·ÂÇØÁÖ¼¼¿ä : ");
										try
										{
											tmpBagNum1 = Integer.parseInt(br.readLine());
											if (tmpBagNum1>5)
												System.out.println("¡Ø °¡´ÉÇÑ ¼ö·®Àº ÃÖ´ë 5°³ ÀÔ´Ï´Ù ¡Ø\n");	
										}
										catch (NumberFormatException e)
										{
											System.out.println(">> ¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
										}
									}
									while (tmpBagNum1>5 || tmpBagNum1<0);	
									
									tmpBagSum += 40000 * tmpBagNum1;					// 24 ~28 kg ¼öÇÏ¹° 1°³ Ãß°¡¿ä±İ : 40000
									
									if (tmpBagNum1!=5)									// tmpBagNum1ÀÌ ÀÌ¹Ì ÃÖ´ë ¼ö·® 5°³¸¦ Ã¤¿ì¸é 28~32kg ÀÇ ¼öÇÏ¹°À» 
																						// ¹ŞÁö ¾Ê°í ¹Ù·Î Á¤º¸ È®ÀÎÀ¸·Î ³Ñ¾î°¡°Ô ÇÔ
									{
										do
										{
											flag=true;
											System.out.print("28 ~ 32kg À§Å¹¼öÇÏ¹° ¼ö·®À» ÀÔ·ÂÇØÁÖ¼¼¿ä : ");
											try
											{
												tmpBagNum2 = Integer.parseInt(br.readLine());
												
											}
											catch (NumberFormatException e)
											{
												System.out.println(">> ¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
												flag = false;
											}
											if (flag)
											{
												tmpBagNum3 = tmpBagNum1 + tmpBagNum2;
												if (tmpBagNum2>5 || tmpBagNum3>5)
													System.out.printf("¡Ø °¡´ÉÇÑ ¼ö·®Àº ÃÖ´ë 5°³ ÀÔ´Ï´Ù(ÇöÀç ¼ö·®:%d) ¡Ø\n\n",tmpBagNum1);
											}
										}
										while (tmpBagNum2<0 || tmpBagNum2>5|| tmpBagNum3>5); // ÃÑ À§Å¹¼öÇÏ¹° °³¼ö ÃÖ´ë 5°³±îÁö ¹Ş°ÔÇÔ

										tmpBagSum += 60000 * tmpBagNum2;					 // 28 ~32 kg ¼öÇÏ¹° 1°³ Ãß°¡¿ä±İ : 40000

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
								else if (tmpBag.equals("n")||tmpBag.equals("N"))			// Ãß°¡ÇÒ À§Å¹ ¼öÇÏ¹° ¾ø´Ù°í ÀÔ·ÂÇßÀ» ¶§
								{
									tmpBagNum1 = 0;
									tmpBagNum2 = 0;
								}
							}
							while (!(tmpBag.equals("n")||tmpBag.equals("N")||tmpBag.equals("y")||tmpBag.equals("Y")));	//  n,N,y,Y ¸¸ ÀÔ·Â °¡´É
						
						}
						else if (tmpBag.equals("n")||tmpBag.equals("N"))												// Ãß°¡ÇÒ À§Å¹¼öÇÏ¹°ÀÌ ÀÖÀ» ¶§
						{
							tmpBagNum1 = 0;
							tmpBagNum2 = 0;
						}
						
					}
					while (!(tmpBag.equals("n")||tmpBag.equals("N")||tmpBag.equals("y")||tmpBag.equals("Y")));			//  n,N,y,Y ¸¸ ÀÔ·Â °¡´É
				}
				catch (Exception e)
				{
					flag=true;
				}
			}
			while (flag);
				
			// À§Å¹¼öÇÏ¹° ÃÑ Ãß°¡ ±İ¾× Ãâ·Â
			if (tmpBagSum!=0)
			{
				System.out.printf("\nÀ§Å¹¼öÇÏ¹° Ãß°¡ ¿ä±İÀº ÃÑ %s¿øÀÔ´Ï´Ù.\n",decimalFormat.format(tmpBagSum));	
			}

			// ¿¹¾àÀÚ ÀÔ·Â ³»¿ë È®ÀÎ
			System.out.println("\n<< ¿¹¾àÀÚ Á¤º¸ È®ÀÎ >>");
			System.out.println("ÀÌ¸§(ÇÑ±Û¸í) : " + tmpName +"\nÀÌ¸§(¿µ¹®¸í) : "+tmpEngFirstName+" "+tmpEngLastName+"\nÀüÈ­¹øÈ£ : " + tmpTel);
			System.out.println("24 ~ 28kg À§Å¹¼öÇÏ¹° ¼ö·® : " + tmpBagNum1);	
			System.out.println("28 ~ 32kg À§Å¹¼öÇÏ¹° ¼ö·® : " + tmpBagNum2);
			System.out.print("\nÀÔ·ÂÇÏ½Å Á¤º¸°¡ ¸Â½À´Ï±î?(Y/N) : ");
			tmpAns = br.readLine();
			if (tmpAns.equalsIgnoreCase("Y"))
			{
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setName(tmpName);									// hashmap ÀÇ name º¯¼ö(PassengerInfo)¿¡ »ç¿ëÀÚ ÀÌ¸§ ³Ö±â
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setEngFirstName(tmpEngFirstName);					// hashmap ÀÇ engFirstName º¯¼ö(PassengerInfo)¿¡ »ç¿ëÀÚ ¿µ¹®ÀÌ¸§(ÀÌ¸§)  ³Ö±â
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setEngLastName(tmpEngLastName);						// hashmap ÀÇ engLastName º¯¼ö(PassengerInfo)¿¡ »ç¿ëÀÚ ¿µ¹®ÀÌ¸§(¼º) ³Ö±â
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setTel(tmpTel);										// hashmap ÀÇ IndexA º¯¼ö(PassengerInfo)¿¡ »ç¿ëÀÚ ¿µ¹® ÀüÈ­¹øÈ£ ³Ö±â
				h.get(startDay)[idxPlace][idxTime].seatP[row][col].setBagCost(tmpBagSum);								// hashmap ÀÇ IndexA º¯¼ö(PassengerInfo)¿¡ »ç¿ëÀÚ Ãß°¡ À§Å¹¼öÇÏ¹° ±İ¾× ³Ö±â
			}
			else
				System.out.println(">> Å¾½ÂÁ¤º¸¸¦ ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");

		} while (!(tmpAns.equalsIgnoreCase("Y")));


		// hashmap ÀÇ indexA º¯¼ö(PassengerInfo)¿¡ ÇØ´ç ÁÂ¼®¹øÈ£ ³Ö±â
		h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexA(row);

		//  hashmap ÀÇ grade º¯¼ö(PassengerInfo)¿¡ ÇØ´ç ÁÂ¼® µî±Ş ³Ö±â
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

		// hashmap ÀÇ serial º¯¼ö(PassengerInfo)¿¡ ÇØ´ç ÁÂ¼® °íÀ¯¹øÈ£ ³Ö±â(ÁÂ¼®¹øÈ£+¸ñÀûÁö+Ãâ¹ß½Ã°£+³¯Â¥)  
		switch (col)
		{
		case 0: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("A") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("A"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		case 1: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("B") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("B"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		case 2: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("C") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("C"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		case 3: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("D") ; h.get(startDay)[idxPlace][idxTime].seatP[row][col].setSerial("D"+row+idxPlace+idxTime+startDay.substring(0,4)+startDay.substring(5,7)+startDay.substring(8,10));break;		
		default : h.get(startDay)[idxPlace][idxTime].seatP[row][col].setIndexB("row") ; break;
		}

		// hashmap ÀÇ cost º¯¼ö(PassengerInfo)¿¡ ÇØ´ç ÁÂ¼® ±İ¾× ³Ö±â 
		switch(row)
		{
			case 0: case 1: h.get(startDay)[idxPlace][idxTime].seatP[row][col].setCost((int)(placeCost.get(idxPlace) * 1.5));break;							
			case 2:case 3:case 4:  h.get(startDay)[idxPlace][idxTime].seatP[row][col].setCost((int)(placeCost.get(idxPlace) * 1.3));break;					
			case 5:case 6:case 7:case 8:case 9:  h.get(startDay)[idxPlace][idxTime].seatP[row][col].setCost(placeCost.get(idxPlace));break;	
		}

		// hashmap ÀÇ depTime º¯¼ö(PassengerInfo)¿¡ ÇØ´ç ÁÂ¼® Ãâ¹ß ½Ã°£ ³Ö±â
		h.get(startDay)[idxPlace][idxTime].seatP[row][col].setDepTime(placeAndTime.get(idxPlace)[idxTime]);	

		// hashmap ÀÇ desti º¯¼ö(PassengerInfo)¿¡ ÇØ´ç ÁÂ¼® ¸ñÀûÁö(¿µ¹®) ³Ö±â
		h.get(startDay)[idxPlace][idxTime].seatP[row][col].setDesti(englishName.get(idxPlace));				
		
		// hashmap ¿¡ ÀúÀåµÈ Á¤º¸ qu(Queue)¿¡ Ãß°¡ÇÏ±â (¿µ¼öÁõ Ãâ·Â À§ÇÔ)
		qu.offer(h.get(startDay)[idxPlace][idxTime].seatP[row][col]);
		
	}

	// ÀÔ·Â ¹ŞÀº °ªÀÌ ¿µ¹®ÀÎÁö È®ÀÎÇÏ´Â ¸Ş¼Òµå
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
			System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
			this.engFlag = true;
			return "";
		}
	}

	// °áÁ¦ ¸Ş¼Òµå
	private int payMent() throws IOException
	{
		boolean flag;
		int checkCard=0;												//  Ä«µå
		int cdn;														//  ÀÔ·Â¹ŞÀº Ä«µå¹øÈ£ ÇÑÀÚ¸® ¼ıÀÚ
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// Ä«µå¹øÈ£ ÀÔ·Â¹Ş¾Æ Ä«µå À¯È¿¼º °Ë»ç ½ÃÇà
		do
		{
			flag=false;
			try
			{
				System.out.print("\n°áÁ¦ÇÏ½Ç Ä«µå ¹øÈ£ 16ÀÚ¸®¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä : ");
				tmpcard = br.readLine();
				if ("".equals(tmpcard))
				{
					System.out.print(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
					flag=true;
				}
				else
				{
					tmpcd = tmpcard.toCharArray();						// Ä«µå¹øÈ£ ¹®ÀÚ¹è¿­¿¡ ´ã±â


					// 16ÀÚ¸® ¼ıÀÚ¿¡¼­ ¸Ç ¿ìÃø ¼öºÎÅÍ ¼¼¾î È¦¼ö ¹øÂ° ¼ö´Â ±×´ë·Î µÎ°í, Â¦¼ö ¹øÂ° ¼ö¸¦ 2¹è·Î ¸¸µç´Ù.
					// 2¹è·Î ¸¸µç Â¦¼ö ¹øÂ° ¼ö°¡ 10 ÀÌ»óÀÎ °æ¿ì, °¢ ÀÚ¸®ÀÇ ¼ıÀÚ¸¦ ´õÇÏ°í ±× ¼ö·Î ´ëÃ¼ÇÑ´Ù.
					// ÀÌ¿Í °°ÀÌ ¾òÀº ¸ğµç ÀÚ¸®ÀÇ ¼ö¸¦ ´õÇÑ´Ù.
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
				System.out.print(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
				flag = true;

			}
		}
		while (flag);

		
		// 10¿¡¼­ ±× ÇÕ(checkCard)À» ³ª´« ³ª¸ÓÁö¸¦ ±¸ÇÏ°í, ±×°ÍÀ» 10¿¡¼­ »«´Ù.
		// »« °ªÀÌ Ä«µå¹øÈ£ÀÇ ¸¶Áö¸· ¼ıÀÚ¿Í °°À¸¸é È¤Àº ³ª¸ÓÁö°¡ 10 ÀÌ°í Ä«µå¹øÈ£ÀÇ ¸¶Áö¸· ¼ıÀÚ°¡ 0ÀÌ¶ó¸é
		// ¡°Á¤´çÇÑ ¹øÈ£¡±(À¯È¿)ÀÌ°í, ±×·¸Áö ¾ÊÀ¸¸é ¡°ºÎ´çÇÑ ¹øÈ£¡±(À¯È¿ÇÏÁö ¾ÊÀ½)·Î ÆÇÁ¤µÈ´Ù.

		// Ä«µå¹øÈ£°¡ 16ÀÚ¸®ÀÌ°í, À¯È¿¼º ÆÇÁ¤ÀÌ µÇ¾ú´Ù¸é ¡æ Ä«µå ºñ¹Ğ¹øÈ£ ÀÔ·Â
		if (tmpcd.length==16 && (((10-(checkCard%10)) == Character.getNumericValue(tmpcd[tmpcd.length-1])) || ((10-(checkCard%10))==10 && Character.getNumericValue(tmpcd[tmpcd.length-1])==0)))
		{
			int breakn=0;												// Á¦ÇÑ ÀÔ·Â È½¼ö º¯¼ö(ÃÖ´ë 3È¸)
			do
			{
				try
				{
					if (breakn>=3)
					{
						return 0;
					}
					
					System.out.printf("Ä«µå ºñ¹Ğ¹øÈ£ ¼ıÀÚ 4ÀÚ¸®¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.(Á¦ÇÑ ÀÔ·Â È½¼ö : %d) : ",3-breakn);
					tmpcardpw = br.readLine();
			
					char[] tmpcdpw = tmpcardpw.toCharArray();
					
					if (tmpcdpw.length == 4)							// ÀÔ·ÂÇÑ ºñ¹Ğ¹øÈ£ ¼ıÀÚ°¡ 4ÀÚ¸® ÀÏ ¶§ 
					{
						try
						{
							for (char cdpw : tmpcdpw)
							{
								Character.getNumericValue(cdpw);		// ¼ıÀÚ¸¸ ÀÔ·Â °¡´ÉÇÏµµ·Ï ÇÔ
							}
							return 1;
						}
						catch (NumberFormatException e)
						{
							System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
						}
					}
					else
						System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
				}
				catch (Exception e)
				{
					System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.\n");
				}
				
				breakn++;												
			}
			while (breakn<4);
			return 1;													// Ä«µå¹øÈ£ ¹× Ä«µå ºñ¹Ğ¹øÈ£ ÀÔ·Â ¼º°ø ¡æ 1¸®ÅÏ
		}
		// Àß¸øµÈ Ä«µå¹øÈ£ ÀÔ·Â ½Ã
		else
		{
			System.out.println(">> Ä«µå¹øÈ£°¡ ¿Ã¹Ù¸£Áö ¾Ê½À´Ï´Ù.");
			System.out.print("´Ù½Ã ÀÔ·ÂÇÏ½Ã°Ú½À´Ï±î?(Y/N)(¡ºY¡» ÀÌ¿Ü ÀÔ·Â ¡æ¸ŞÀÎ È­¸é) : ");
			String re = br.readLine();

			if (re.equals("Y") || re.equals("y"))
			{
				return payMent();										// °áÁ¦ Ã³À½ºÎÅÍ ´Ù½Ã ½ÃÇà
			}
			else
				return 0;												// Ä«µå¹øÈ£ ´Ù½Ã ÀÔ·Â °ÅºÎ ½Ã ¡æ 0 ¸®ÅÏ
		}
			
	}

	// ºñÇà±â Æ¼ÄÏ ¹ß±Ç Á¤º¸ Ãâ·Â ¸Ş¼Òµå
	void print(String str, String time)
	{	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Load.loadingPay();
		System.out.println("\n<<"+str + " Çà " + time + " ºñÇà±â Æ¼ÄÏ ¹ß±Ç Á¤º¸>>");
		//qu(Queue)¿¡ ´ã°ÜÀÖ´Â Å¾½Â Á¤º¸°¡ ºô ¶§ ±îÁö Å¾½Â Á¤º¸ Ãâ·Â
		while (!qu.isEmpty())
		{
			System.out.println("--------------------------------------------------------");
			System.out.printf("Å¾½Â°´: %s\nÅ¾½Â°´(¿µ¹®): %s-%s\n\nÀüÈ­¹øÈ£: %s\nÁÂ¼®µî±Ş: %s\nÁÂ¼®¹øÈ£: %s%d\nÆ¼ÄÏ°¡°İ: %s¿ø + À§Å¹¼öÇÏ¹° ÃÊ°ú¹«°Ô ºñ¿ë: %s¿ø\n°íÀ¯¹øÈ£: %s\n",
				qu.peek().getName(), qu.peek().getEngFirstName(), qu.peek().getEngLastName(), qu.peek().getTel(),qu.peek().getGrade(), qu.peek().getIndexB(), (qu.peek().getIndexA()+1), decimalFormat.format(qu.peek().getCost()),decimalFormat.format(qu.peek().getBagCost()),qu.peek().getSerial());
			System.out.print("Ä«µå ¹øÈ£: ");
			char[] tmpcdpw = qu.peek().getCardNumber().toCharArray();
			for (int i=0; i<tmpcdpw.length; i++)
			{
				if ((i>=0 && i<4) || i>11)								// Ä«µå¹øÈ£ µ¥ÀÌÅÍ ¸¶½ºÅ·
				{
					System.out.print(tmpcdpw[i]);
				}
				else
					System.out.print("*");
			}
			System.out.println();
			System.out.println("--------------------------------------------------------\n\n");

			// ÇÑ ¸íÀÇ Å¾½Â Á¤º¸ »èÁ¦ 
			qu.poll();
			System.out.println("\n°è¼ÓÇÏ·Á¸é Press button...");			// ¿£ÅÍ ÀÔ·Â ½Ã ¸ŞÀÎ ¸Ş´º·Î ÀÌµ¿
			
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
	// ¿¹¾à Á¶È¸ ¸Ş¼Òµå
	void check() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String findRes="";													// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÑ °íÀ¯¹øÈ£
		int tmp=0;														// °íÀ¯¹øÈ£ ÀÔ·Â È½¼ö(ÃÖ´ë 3È¸)

		do
		{
			try
			{
				// °íÀ¯¹øÈ£ ÀÔ·Â
				System.out.printf("\n°íÀ¯¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.(Á¦ÇÑ ÀÔ·Â È½¼ö: %dÈ¸) : ", 3-tmp);
				findRes = br.readLine();

				// °íÀ¯¹øÈ£ Çü½Ä(¹®ÀÚ 1ÀÚ¸®+ ¼ıÀÚ 11ÀÚ¸®) ¿¡ ¸Â´ÂÁö È®ÀÎ
				if (findRes.matches("[A-Za-z][0-9]{11}"))				 
				{
					// Á¸ÀçÇÏ´Â °íÀ¯¹øÈ£ÀÎÁö È®ÀÎÇÏ´Â ¸Ş¼Òµå È£Ãâ										
					break;
				}
				else
				{
					System.out.println(">> °íÀ¯¹øÈ£ Çü½Ä¿¡ ¸Â°Ô ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					tmp++;
				}
			
			}
			catch (Exception e)
			{
				System.out.println(">> °íÀ¯¹øÈ£ Çü½Ä¿¡ ¸Â°Ô ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				tmp++;
			}
		}
			while (tmp < 3);

		if (tmp==3)
		{
			System.out.println(">> ÀÔ·Â È½¼ö¸¦ ÃÊ°úÇß½À´Ï´Ù.");
			System.out.println(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			try 
			{
                Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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

	// ¿¹¾à Á¶È¸(Á¸ÀçÇÏ´Â °íÀ¯¹øÈ£ÀÎÁö È®ÀÎ) ¸Ş¼Òµå
	void check(String findRes) throws IOException	
	{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));										

		PassengerInfo passValue;										// ÀÔ·ÂÇÑ °íÀ¯¹øÈ£¿¡ ´ã°ÜÀÖ´Â ¿¹¾àÀÚ Á¤º¸									
		
		int col=-1;
		switch (findRes.substring(0,1))									// °íÀ¯¹øÈ£¿¡ ÀÖ´Â ÁÂ¼®¹øÈ£(¹®ÀÚ)¸¦ ¼ıÀÚ·Î º¯È¯
		{
		case "A" : col = 0; break;									
		case "B" : col = 1; break;
		case "C" : col = 2; break;
		case "D" : col = 3; break;
		}
		
		// ÇØ´ç °íÀ¯¹øÈ£¿¡ ¿¹¾àÀÚ Á¤º¸ÀÖ´ÂÁö È®ÀÎ
		// ÀÔ·ÂÇÑ °íÀ¯¹øÈ£¸¦ ÃßÃâÇÏ¿© ¿¹¾àÀÚ Á¤º¸ ¾ò¾î³»±â(h.get(³¯Â¥)[¸ñÀûÁö][½Ã°£´ë][ÁÂ¼®ÀÇ ¿­][ÁÂ¼®ÀÇ Çà]
		try
		{
			passValue = h.get(findRes.substring(4,8)+"-"+findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatP[Integer.parseInt(findRes.substring(1,2))][col];
			if (passValue.getName() == null)								// Å¾½ÂÁ¤º¸¿¡ »ç¿ëÀÚ ÀÌ¸§¸¸ ¾øÀ» ¶§ (¿¹¾à Çß´Ù°¡ Ãë¼ÒÇßÀ» °æ¿ì)
			{
				System.out.println(">> Á¤º¸°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
				System.out.println(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
				try 
				{
					Thread.sleep(750); //25¹Ğ¸®ÃÊ 
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				return;
			}
			// Å¾½ÂÁ¤º¸ Á¸ÀçÇÒ °æ¿ì ºñÇà±â Æ¼ÄÏ Ãâ·Â
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
				System.out.println("\n°è¼ÓÇÏ·Á¸é Press button...");

				 try
				 {
					String pause = br.readLine();							// ¿£ÅÍ ÀÔ·Â ½Ã ¸ŞÀÎ ¸Ş´º·Î ÀÌµ¿
				 }
				 catch (IOException e)
				 {
					return;
				 }
				
			}
		}
		catch (NullPointerException e)
		{
			System.out.println(">> Á¤º¸°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");			// Á¸ÀçÇÏÁö ¾Ê´Â °íÀ¯¹øÈ£¸¦ ÀÔ·ÂÇÒ °æ¿ì NullPointerExceptionÀÌ ÀÏ¾î³²
			System.out.println(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			try 
			{
                Thread.sleep(750); //25¹Ğ¸®ÃÊ 
            } 
			catch (InterruptedException c) 
			{
                e.printStackTrace();
            }
			return;
		}					
	}
	// ¿¹¾à Ãë¼ÒÇÏ´Â ¸Ş¼Òµå
	void cancel() throws IOException
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = false;

		String findRes="Z";												// ¿¹¾àÀÚ°¡ ÀÔ·ÂÇÑ °íÀ¯¹øÈ£
		double charge=0;												// Ãë¼Ò ¼ö¼ö·á
		
		int tmp=3;														// °íÀ¯¹øÈ£ ÀÔ·Â È½¼ö(ÃÖ´ë 3È¸)
		PassengerInfo passValue;										// ÀÔ·ÂÇÑ °íÀ¯¹øÈ£¿¡ ´ã°ÜÀÖ´Â ¿¹¾àÀÚ Á¤º¸
		
		// °íÀ¯¹øÈ£ ÀÔ·Â
		do
		{
			try
			{
				System.out.printf("\n°íÀ¯¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.(Á¦ÇÑ ÀÔ·Â È½¼ö: %dÈ¸) : ",tmp);
				
				findRes = br.readLine();
				// °íÀ¯¹øÈ£ Çü½Ä(¹®ÀÚ 1ÀÚ¸®+¼ıÀÚ11ÀÚ¸®)¿¡ ¸Â´ÂÁö È®ÀÎ
				if (findRes.matches("[A-Za-z][0-9]{11}"))				
				{
					break;
				}

				System.out.println(">> °íÀ¯¹øÈ£ Çü½Ä¿¡ ¸Â°Ô ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				tmp--;
			}
			catch (Exception e)
			{

				System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				tmp--;
			}
			
		}
		while (tmp>0);

		if (tmp==0)
		{
			System.out.println(">> ÀÔ·Â È½¼ö¸¦ ÃÊ°úÇÏ¿´½À´Ï´Ù. ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			System.out.println(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			try 
			{
                Thread.sleep(750); //25¹Ğ¸®ÃÊ 
            } 
			catch (InterruptedException c) 
			{
                c.printStackTrace();
            }
			return;
		}
		

		int col=-1;
		switch (findRes.substring(0,1))									// °íÀ¯¹øÈ£¿¡ ÀÖ´Â ÁÂ¼®¹øÈ£(¹®ÀÚ)¸¦ ¼ıÀÚ·Î º¯È¯
		{
		case "A" : col = 0; break;
		case "B" : col = 1; break;
		case "C" : col = 2; break;
		case "D" : col = 3; break;
		}

		// ÇØ´ç °íÀ¯¹øÈ£¿¡ ¿¹¾àÀÚ Á¤º¸ÀÖ´ÂÁö È®ÀÎ
		// ÀÔ·ÂÇÑ °íÀ¯¹øÈ£¸¦ ÃßÃâÇÏ¿© ¿¹¾àÀÚ Á¤º¸ ¾ò¾î³»±â(h.get(³¯Â¥)[¸ñÀûÁö][½Ã°£´ë][ÁÂ¼®ÀÇ ¿­][ÁÂ¼®ÀÇ Çà]
		try						
		{
			passValue = h.get(findRes.substring(4,8)+"-"+ findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatP[Integer.parseInt(findRes.substring(1,2))][col];
			
			if (passValue.getName() == null)								// Å¾½ÂÁ¤º¸¿¡ »ç¿ëÀÚ ÀÌ¸§¸¸ ¾øÀ» ¶§ (¿¹¾à Çß´Ù°¡ Ãë¼ÒÇßÀ» °æ¿ì)
			{
			System.out.println(">> Á¤º¸°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
			System.out.println(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			try 
			{
				Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
			System.out.println(">> Á¤º¸°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");		// Á¸ÀçÇÏÁö ¾Ê´Â °íÀ¯¹øÈ£¸¦ ÀÔ·ÂÇÒ °æ¿ì NullPointerExceptionÀÌ ÀÏ¾î³²
			System.out.println(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			try 
			{
                Thread.sleep(750); //25¹Ğ¸®ÃÊ 
            } 
			catch (InterruptedException c) 
			{
                c.printStackTrace();
            }
			return;
		}

		
		try
		{
			System.out.printf(">> %s´ÔÀÌ ¸ÂÀ¸½Ê´Ï±î?(Y/N) : ", passValue.getName()); // Å¾½ÂÁ¤º¸¿¡ »ç¿ëÀÚ ÀÌ¸§¸¸ ¾øÀ» °æ¿ì (¿¹¾à Çß´Ù°¡ Ãë¼ÒÇßÀ» °æ¿ì) NullPointerExceptionÀÌ ÀÏ¾î³²
		}
		catch (Exception e)
		{
			System.out.println(">> °íÀ¯¹øÈ£°¡ ÀÏÄ¡ÇÏÁö ¾Ê°Å³ª Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
			System.out.println("°íÀ¯¹øÈ£¸¦ ´Ù½Ã ÀÔ·ÂÇÏ½Ã°Ú½À´Ï±î?(Y/N)(¡ºY¡» ÀÌ¿Ü ÀÔ·Â ¡æ¸ŞÀÎ È­¸é) : ");

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
					System.out.print(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
					try 
					{
						Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
				System.out.print(">> ¸ŞÀÎ È­¸éÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
				try 
				{
					Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
			// ÇØ´ç °íÀ¯¹øÈ£¿¡ ¿¹¾àÀÚ Á¤º¸°¡ Á¸ÀçÇÒ ¶§ ¡æ Ãë¼Ò ¼ö¼ö·á ¾È³»
			if (con.equals("Y") || con.equals("y"))						
			{
				System.out.println("\n¡Ø Ãë¼Ò½Ã ¿¹¾à±İ¿¡¼­ ¼ö¼ö·á(¿¹¾à±İÀÇ 10%)¸¦ Á¦¿ÜÇÑ ±İ¾×ÀÌ È¯ºÒµË´Ï´Ù. ¡Ø");

				charge = (passValue.getCost()+passValue.getBagCost()) * 0.1;			// ¼ö¼ö·á = (¿¹¾à±İ+¼öÇÏ¹° Ãß°¡±İ¾×) *0.1
				
				System.out.printf("\n>> %s´ÔÀÌ ¿¹¾à Ãë¼Ò ½Ã È¯ºÒ °¡´É ±İ¾×Àº %.0f¿ø(¼ö¼ö·á: %.0f¿ø)ÀÔ´Ï´Ù."
									, passValue.getName(),((passValue.getCost()+passValue.getBagCost())-charge), charge);
				
				do
				{
					flag = true;
					try
					{
						System.out.print("\n\n>> Á¤¸»·Î ¿¹¾àÀ» Ãë¼ÒÇÏ½Ã°Ú½À´Ï±î?(Y/N) : ");
						con = br.readLine();
						if (con.equalsIgnoreCase("y") || con.equalsIgnoreCase("n"))
						{
							flag = false;
						}
					}
					catch (Exception e)
					{
						System.out.println(">> Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
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
							// °áÁ¦ Ä«µå ºñ¹Ğ¹øÈ£ ÀÔ·Â (±âÈ¸ ÃÖ´ë 3¹ø)
							System.out.print("°áÁ¦ Ä«µå(");
							char[] tmpcdpw = passValue.getCardNumber().toCharArray();
							for (int i=0; i<tmpcdpw.length; i++)
							{
								if ((i>=0 && i<4) || i>11)
								{
									System.out.print(tmpcdpw[i]);
								}
								else
									System.out.print("*");				// Ä«µå µ¥ÀÌÅÍ ¸¶½ºÅ·
							}
							System.out.print(")ÀÇ ºñ¹Ğ¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä: ");
	
							String passwd = br.readLine();

							if (passwd.equals(passValue.getCardPW()))
							{
								System.out.println(">> ºñ¹Ğ¹øÈ£°¡ ÀÏÄ¡ÇÕ´Ï´Ù.");
								l=3;
							}
							else
								System.out.printf(">> ºñ¹Ğ¹øÈ£°¡ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù. (³²Àº ±âÈ¸: %d¹ø)", (3-(++l)));
						}
						catch (Exception e)
						{
							System.out.printf(">> ºñ¹Ğ¹øÈ£°¡ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù. (³²Àº ±âÈ¸: %d¹ø)", (3-(++l)));
						}
						
					
					}
					while (l<3);
				}
				else
				{
					System.out.println(">> ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
					try 
					{
						Thread.sleep(750); //25¹Ğ¸®ÃÊ 
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
			System.out.println(">> ¸ŞÀÎÀ¸·Î µ¹¾Æ°©´Ï´Ù.");
			try 
			{
				Thread.sleep(750); //25¹Ğ¸®ÃÊ 
			} 
			catch (InterruptedException c) 
			{
				c.printStackTrace();
			}
			return;
		}
		System.out.printf(">> %s°í°´´ÔÀÇ %sÇà %s ¿¹¾àÃë¼Ò°¡ ¿Ï·áµÇ¾ú½À´Ï´Ù.", passValue.getName(), passValue.getDesti(), passValue.getDepTime());
		System.out.println("¾È³çÈ÷ °¡½Ê½Ã¿À.");
		System.out.println();

		// ¿¹¾àÀÚ Á¤º¸ÀÇ ¿¹¾àÀÚ ÀÌ¸§¿¡ null ´ëÀÔ
		passValue.setName(null); 
		// ¿¹¾àÀÚ Á¤º¸ÀÇ ¿¹¾à±İ 0À¸·Î ÃÊ±âÈ­
		passValue.setCost(0);
		// ¿¹¾àÀÚ Á¤º¸ÀÇ ±âÁ¸ Ãë¼Ò ¼ö¼ö·á¿¡¼­ ÇöÀç ¹ß»ıÇÑ Ãë¼Ò ¼ö¼ö·á ´õÇÔ
		passValue.setCharge(passValue.getCharge() + charge); 
		// hashmap ¿¡¼­ ÇØ´ç ºñÇà±â ÁÂ¼® ÇöÈ² false·Î º¯°æ
		h.get(findRes.substring(4,8)+"-"+ 
		findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatB[Integer.parseInt(findRes.substring(1,2))][col] = false;
		// hashmap ¿¡¼­ ÇØ´ç ºñÇà±â ÀÜ¿©ÁÂ¼® ¼ö 1 Ãß°¡
		h.get(findRes.substring(4,8)+"-"+ findRes.substring(8,10)+"-"+findRes.substring(10))[Integer.parseInt(findRes.substring(2,3))][Integer.parseInt(findRes.substring(3,4))].seatLeft++;
		try 
		{
			Thread.sleep(750); //25¹Ğ¸®ÃÊ 
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		return;
	}

}