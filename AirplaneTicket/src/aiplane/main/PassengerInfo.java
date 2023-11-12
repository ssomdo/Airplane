package aiplane.main;

import java.io.Serializable;

public class PassengerInfo implements Serializable
{
	private String name;				// �̸�
	private String engFirstName;		// ���� �̸�
	private String engLastName;			// ���� �̸�(��)
	private String tel;					// ��ȭ��ȣ
	private double bagCost;				// �߰� ��Ź���Ϲ� �ݾ�
	private int indexA;					// �¼���ȣ(����)
	private String indexB;				// �¼���ȣ(����)
	private String grade;				// �¼����
	private String serial;				// ������ȣ
	private double cost;				// �¼� �ݾ�(�����)
	private double charge=0;			// ��� ������
	private String desti;				// ������
	private String depTime;				// ��߽ð�

	private String cardNumber;			// ī�� ��ȣ
	private String cardPW;				// ī�� ��й�ȣ


	public String getName()						// getter�� ��ȯ�ڷ����� �Ӽ��� �ڷ��� �״��
	{		
		return name;							// �Ӽ��� �״�� ��ȯ
	}
	public void setName(String name)			// setter�� �Ű������� �Ӽ� �״��, ��ȯ�ڷ����� void
	{
		this.name = name;						// 'this' ����!
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