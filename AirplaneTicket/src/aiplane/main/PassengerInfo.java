package aiplane.main;

import java.io.Serializable;

public class PassengerInfo implements Serializable
{
	private String name;				// 이름
	private String engFirstName;		// 영문 이름
	private String engLastName;			// 영문 이름(성)
	private String tel;					// 전화번호
	private double bagCost;				// 추가 위탁수하물 금액
	private int indexA;					// 좌석번호(숫자)
	private String indexB;				// 좌석번호(문자)
	private String grade;				// 좌석등급
	private String serial;				// 고유번호
	private double cost;				// 좌석 금액(예약금)
	private double charge=0;			// 취소 수수료
	private String desti;				// 목적지
	private String depTime;				// 출발시간

	private String cardNumber;			// 카드 번호
	private String cardPW;				// 카드 비밀번호


	public String getName()						// getter의 반환자료형은 속성의 자료형 그대로
	{		
		return name;							// 속성을 그대로 반환
	}
	public void setName(String name)			// setter의 매개변수는 속성 그대로, 반환자료형은 void
	{
		this.name = name;						// 'this' 주의!
	}


	public String getTel()
	{		
		return tel;	
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}


	public String getSerial()
	{		
		return serial;	
	}
	public void setSerial(String serial)
	{
		this.serial = serial;
	}


	public int getIndexA()
	{		
		return indexA;	
	}
	public void setIndexA(int indexA)
	{
		this.indexA = indexA;
	}


	public String getIndexB()
	{		
		return indexB;	
	}
	public void setIndexB(String indexB)
	{
		this.indexB = indexB;
	}


	public double getCost()
	{		
		return cost;	
	}
	public void setCost(double cost)
	{
		this.cost = cost;
	}


	public String getDesti()
	{		
		return desti;	
	}
	public void setDesti(String desti)
	{
		this.desti = desti;
	}


	public String getGrade()
	{		
		return grade;	
	}
	public void setGrade(String grade)
	{
		this.grade = grade;
	}


	public String getDepTime()
	{		
		return depTime;	
	}
	public void setDepTime(String depTime)
	{
		this.depTime = depTime;
	}


	public double getCharge()
	{		
		return charge;	
	}
	public void setCharge(double charge)
	{
		this.charge = charge;
	}


	public double getBagCost()
	{		
		return bagCost;	
	}
	public void setBagCost(double bagCost)
	{
		this.bagCost = bagCost;
	}


	public String getCardNumber()
	{		
		return cardNumber;	
	}
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}


	public String getCardPW()
	{		
		return cardPW;	
	}
	public void setCardPW(String cardPW)
	{
		this.cardPW = cardPW;
	}

	public String getEngFirstName()
	{		
		return engFirstName;	
	}
	public void setEngFirstName(String engFirstName)
	{
		this.engFirstName = engFirstName;
	}

	public String getEngLastName()
	{		
		return engLastName;	
	}
	public void setEngLastName(String engLastName)
	{
		this.engLastName = engLastName;
	}
}