import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date; // Date Ŭ������ import
import java.util.GregorianCalendar; // GregorianCalendar Ŭ������ import

public class Room implements java.io.Serializable{ // �� Ŭ����
	// �������
	private String roomName; // �� �̸� ��Ÿ�� ����
	private Boolean isUsed; // ���� ��� ������ ��Ÿ�� ����
	private int headcount; // ���� �ִ� �ο� ���� ��Ÿ�� ����
	private int price; // ���� ������ ��Ÿ�� ����
	private User who; // ���� �̿��ڸ� ��Ÿ�� ����
	private Date inTime; // �Խǽð��� ��Ÿ�� ����
	private Date outTime; // ��ǽð��� ��Ÿ�� ����
	
	private GregorianCalendar calendarInTime; // �Խǽð��� ���ϱ� ���� GregorianCalendar�� ����
	private GregorianCalendar calendarOutTime; // ��ǽð��� ���ϱ� ���� GregorianCalendar�� ����
	
	// ����Լ� 
	public Room(String roomName, Boolean isUsed, int headcount, User who, int price, Date inTime) { // Room Ŭ������ ������
		this.roomName = roomName; // Room Ŭ������ roomName�� �Ű����� roomName�� �ʱ�ȭ�Ѵ�.
		this.isUsed = isUsed; // Room Ŭ������ isUsed�� �Ű����� isUsed�� �ʱ�ȭ�Ѵ�.
		this.headcount = headcount; // Room Ŭ������ headcount�� �Ű����� headcount�� �ʱ�ȭ�Ѵ�.
		this.who = who; // Room Ŭ������ who�� �Ű����� who�� �ʱ�ȭ�Ѵ�.
		this.price = price; // Room Ŭ������ price�� �Ű����� price�� �ʱ�ȭ�Ѵ�.
		this.inTime = inTime; // Room Ŭ������ inTime�� �Ű����� inTime�� �ʱ�ȭ�Ѵ�.
	}
	
	public Room(DataInputStream in) throws Exception // �����͸� �о���� ������
	{
		RoomDataInput(in);  // �� �����͸� �о���� �Լ�
	}
	
	public Room(String roomName) { // ArrayList�� indexOf�� remove�� ����Ͽ� �ش� �� �̸��� ���� ���� �ε����� ã�� ���� �˻������� �ʿ��� �� ��ü�� ����� ���� ������
		this.roomName = roomName; // Room Ŭ������ roomName�� �Ű����� roomName�� �ʱ�ȭ�Ѵ�.
	}
	
	public void checkIn(User who) { // "üũ��" ����� ������ �Լ�. ����� ��ü�� �Ű������� �޴´�.
		isUsed = true; // ���� ��� ������ isUsed�� true�� �����Ѵ�. 
		this.who = who; // Room Ŭ������ who�� �Ű����� who�� �ʱ�ȭ�Ѵ�.
		calendarInTime = new GregorianCalendar();
		inTime = calendarInTime.getTime(); // GregorianCalendarŬ������ calendar��ü�� getTime()�� ����Ͽ� ������ �ð�(checkIn�Լ��� ȣ������ ���� �ð�)�� ���Ͽ� inTime�� �����Ѵ�.
	}
	
	// 
	public void checkOut() { // "üũ�ƿ�" ����� ������ �Լ�
		isUsed = false; // ���� ��� ������ isUsed�� false�� �����Ѵ�. 
		this.who = null; // ����� �Ǳ� ������ who�� null�� �����Ѵ�.
	}
	
	// �ĺ�
	public int pay() { // "�����ϴ�" ����� ������ �Լ�
		calendarOutTime = new GregorianCalendar();
		outTime = calendarOutTime.getTime(); // GregorianCalendarŬ������ calendar��ü�� getTime()�� ����Ͽ� ������ �ð�(checkOut�Լ��� ȣ������ ���� �ð�)�� ���Ͽ� outTime�� �����Ѵ�.
		
		long useHours = (outTime.getTime() - inTime.getTime()) / 3600000; // getTime()�Լ��� �̿��Ͽ� outTime�� inTime�� �и�������� ��ȯ�� ���� outTime.getTime() - inTime.getTime()�� �Ͽ� �ð� ���� ��, �̿�ð��� ���� ��, 3600000���� ������ �� ���� ����� �ð�(hours)�� ���� ��, useHours�� �����Ѵ�. �̶� getTime()�� long���� ��ȯ�ϱ� ���ȿ� useHours�� long������ �����Ѵ�.
		long useMinutes = ((outTime.getTime() - inTime.getTime()) % 3600000) / 60000; // getTime()�Լ��� �̿��Ͽ� outTime�� inTime�� �и�������� ��ȯ�� ���� outTime.getTime() - inTime.getTime()�� �Ͽ� �ð� ���� ��, �̿�ð��� ���� ��, 3600000���� ���� �������� ���� ��, �� �������� 60000�� ������ �� ���� ����� ��(minute)�� ���� �� useMinutes�� �����Ѵ�. �̶� getTime()�� long���� ��ȯ�ϱ� ���ȿ� useMinutes�� long������ �����Ѵ�.
		
		int income = (int)useHours * price + (int)(((float)useMinutes / 60) * price); // useHours�� price�� ��, useMinutes�� 60���� ���� ��� price�� ���� ���Ͽ� ��� �ð��� ���� ����� ���Ͽ� income�� �����Ѵ�. �̶� useHours�� useMinutes�� long���̰� income�� int���̱� ������ useHours�� (((float)useMinutes / 60) * price)�� int������ ����ȯ�Ѵ�.
		return income;	// income�� ��ȯ�Ѵ�.
	}
	
	public String getRoomName() { // roomName�� ��ȯ�ϴ� getter �޼ҵ�
		return roomName; // roomName�� ��ȯ�Ѵ�.
	}
	
	public Boolean getIsUsed() { // isUsed�� ��ȯ�ϴ� getter �޼ҵ�
		return isUsed; // isUsed�� ��ȯ�Ѵ�.
	}
	
	public int getHeadcount() { // headcount�� ��ȯ�ϴ� getter �޼ҵ�
		return headcount; // headcount�� ��ȯ�Ѵ�.
	}
	
	public int getPrice() { // price�� ��ȯ�ϴ� getter �޼ҵ�
		return price; // price�� ��ȯ�Ѵ�.
	}
	
	public User getWho() { // who�� ��ȯ�ϴ� getter �޼ҵ�
		return who; // who�� ��ȯ�Ѵ�.
	}
	
	public Date getInTime() { // inTime�� ��ȯ�ϴ� getter �޼ҵ�
		return inTime; // inTime�� ��ȯ�Ѵ�.
	}
	
	public Date getOutTime() { // outTime�� ��ȯ�ϴ� getter �޼ҵ�
		return outTime; // outTime�� ��ȯ�Ѵ�.
	}
	
	public void setPrice(int price) { // price�� �����ϴ� setter �޼ҵ�
		this.price = price; // Room Ŭ������ price�� �Ű����� price�� �ʱ�ȭ�Ѵ�. 
	}
	
	public void setRoomName(String roomName) { // roomName�� �����ϴ� setter �޼ҵ�
		this.roomName = roomName; // Room Ŭ������ roomName�� �Ű����� roomName�� �ʱ�ȭ�Ѵ�.
	}
	
	private void RoomDataInput(DataInputStream in) throws Exception
	{
		try {
			roomName = in.readUTF(); // ���� �̸� ������. ���ڿ��� �о� roomName�� �����Ѵ�. (���Ϸκ��� ���ڿ��� �д´�.)
			headcount = in.readInt(); // ���� �ο��� ������. 4����Ʈ�� �о� intŸ������ ��ȯ�� ������ headcount�� �����Ѵ�. (���Ϸκ��� ���ڿ��� �д´�.)
			price = in.readInt(); // ���� ���� ������. 4����Ʈ�� �о� intŸ������ ��ȯ�� ������ roomPrice�� �����Ѵ�. (���Ϸκ��� int���� �д´�.)
			isUsed = in.readBoolean(); // �� ��� ���� ������. 1����Ʈ�� �о� BooleanŸ������ ��ȯ�� ������ isUsed�� �����Ѵ�. (���Ϸκ��� Boolean���� �д´�.)
			if (isUsed == true) { // isUsed == true �� ���� ���� ���� ����ϰ� �ִٴ� �ǹ̷� ������� ������ �Խǽð��� �����͸� �д´�.
				String userName = in.readUTF(); // ������� �̸� ������. ���ڿ��� �о� userName�� �����Ѵ�. (���Ϸκ��� ���ڿ��� �д´�.)
				String userPhoneNum = in.readUTF(); // ������� ��ȭ��ȣ ������. ���ڿ��� �о� userPhoneNum�� �����Ѵ�. (���Ϸκ��� ���ڿ��� �д´�.)
				inTime = new Date(in.readLong()); // ������� �Խ� �ð� ������. 8����Ʈ�� �о� longŸ������ ��ȯ�� ������ inTime�� �����Ѵ�. (���Ϸκ��� long���� �д´�.)
				who = new User(userName, userPhoneNum);
			}
		}
		catch (IOException ioe) { // ������ �߻��Ͽ��� �� �߻��ϴ� ���ܸ� ó��
			throw new Exception("ERROR"); // "������ �߻��Ͽ����ϴ�." �� ����Ѵ�.
		}
	}
	
	public void RoomDataOutput(DataOutputStream out) throws Exception
	{
		try {
			out.writeUTF(roomName); // ���� �̸��� �����Ѵ�. (���Ͽ� ���ڿ��� ����Ѵ�.)
			out.writeInt(headcount); // ���� �ο����� �����Ѵ�. (���Ͽ� intŸ�� �����͸� ����Ѵ�.) 
			out.writeInt(price); // ���� ������ �����Ѵ�. (���Ͽ� intŸ�� �����͸� ����Ѵ�.) 
			out.writeBoolean(isUsed); // ���� ��� ������ �����Ѵ�. (���Ͽ� BooleanŸ�� �����͸� ����Ѵ�.)
			if (isUsed == true) // ���� ���� ����ϰ� �ִٴ� �ǹ̷� ������� ������ �Խǽð��� ����Ѵ�(�����Ѵ�).
			{
				out.writeUTF(who.getName()); // ������� �̸��� �����Ѵ�. (���Ͽ� ���ڿ��� ����Ѵ�.)
				out.writeUTF(who.getPhoneNum()); // ������� ��ȭ��ȣ�� �����Ѵ�. (���Ͽ� ���ڿ��� ����Ѵ�.)
				out.writeLong(inTime.getTime()); // ���� �Խ� �ð��� �����Ѵ�. (���Ͽ� longŸ�� �����͸� ����Ѵ�.)
			}
		}
		catch (IOException ioe) { // ������ �߻��� ��쿡 �߻��ϴ� ���ܸ� ó��
			throw new Exception("Can't Write"); // "Can't Write"��� Exception�� �߻���Ų��.
		}
	}
	
	public boolean equals(Object obj) { // equals �ߺ� ����
		if (!(obj instanceof Room)) { // �Ķ���� ��ü�� Room Ÿ������ �˻��Ѵ�.
			return false;
		}
		
		Room room = (Room) obj; // �Ķ���� ��ü�� Room Ÿ������ ĳ��Ʈ�Ѵ�.
		if (this.roomName.equals(room.roomName)) { // �Ķ���� ��ü�� �ڽ� ��ü�� �� �̸��� ���Ѵ�.
			return true;
		}
		else
		{
			return false;
		}
	}

}
