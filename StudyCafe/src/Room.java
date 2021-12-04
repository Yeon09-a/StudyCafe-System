import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date; // Date 클래스를 import
import java.util.GregorianCalendar; // GregorianCalendar 클래스를 import

public class Room implements java.io.Serializable{ // 방 클래스
	// 멤버변수
	private String roomName; // 방 이름 나타낸 변수
	private Boolean isUsed; // 방의 사용 유무를 나타낸 변수
	private int headcount; // 방의 최대 인원 수를 나타낸 변수
	private int price; // 방의 가격을 나타낸 변수
	private User who; // 방의 이용자를 나타낸 변수
	private Date inTime; // 입실시간을 나타낸 변수
	private Date outTime; // 퇴실시간을 나타낸 변수
	
	private GregorianCalendar calendarInTime; // 입실시간을 구하기 위한 GregorianCalendar형 변수
	private GregorianCalendar calendarOutTime; // 퇴실시간을 구하기 위한 GregorianCalendar형 변수
	
	// 멤버함수 
	public Room(String roomName, Boolean isUsed, int headcount, User who, int price, Date inTime) { // Room 클래스의 생성자
		this.roomName = roomName; // Room 클래스의 roomName를 매개변수 roomName로 초기화한다.
		this.isUsed = isUsed; // Room 클래스의 isUsed를 매개변수 isUsed로 초기화한다.
		this.headcount = headcount; // Room 클래스의 headcount를 매개변수 headcount로 초기화한다.
		this.who = who; // Room 클래스의 who를 매개변수 who로 초기화한다.
		this.price = price; // Room 클래스의 price를 매개변수 price로 초기화한다.
		this.inTime = inTime; // Room 클래스의 inTime을 매개변수 inTime로 초기화한다.
	}
	
	public Room(DataInputStream in) throws Exception // 데이터를 읽어오는 생성자
	{
		RoomDataInput(in);  // 방 데이터를 읽어오는 함수
	}
	
	public Room(String roomName) { // ArrayList의 indexOf와 remove를 사용하여 해당 방 이름을 가진 방의 인덱스를 찾기 위해 검색용으로 필요한 방 객체를 만들기 위한 생성자
		this.roomName = roomName; // Room 클래스의 roomName를 매개변수 roomName로 초기화한다.
	}
	
	public void checkIn(User who) { // "체크인" 기능을 구현한 함수. 사용자 객체를 매개변수로 받는다.
		isUsed = true; // 방의 사용 유무인 isUsed에 true를 대입한다. 
		this.who = who; // Room 클래스의 who를 매개변수 who로 초기화한다.
		calendarInTime = new GregorianCalendar();
		inTime = calendarInTime.getTime(); // GregorianCalendar클래스의 calendar객체의 getTime()을 사용하여 현재의 시간(checkIn함수를 호출했을 때의 시간)을 구하여 inTime에 대입한다.
	}
	
	// 
	public void checkOut() { // "체크아웃" 기능을 구현한 함수
		isUsed = false; // 방의 사용 유무인 isUsed에 false를 대입한다. 
		this.who = null; // 빈방이 되기 때문에 who에 null를 대입한다.
	}
	
	// 후불
	public int pay() { // "지불하다" 기능을 구현한 함수
		calendarOutTime = new GregorianCalendar();
		outTime = calendarOutTime.getTime(); // GregorianCalendar클래스의 calendar객체의 getTime()을 사용하여 현재의 시간(checkOut함수를 호출했을 때의 시간)을 구하여 outTime에 대입한다.
		
		long useHours = (outTime.getTime() - inTime.getTime()) / 3600000; // getTime()함수를 이용하여 outTime와 inTime를 밀리세컨드로 변환한 다음 outTime.getTime() - inTime.getTime()를 하여 시간 차이 즉, 이용시간을 구한 후, 3600000으로 나누어 그 몫인 사용한 시간(hours)를 구한 후, useHours에 대입한다. 이때 getTime()은 long형을 반환하기 때믄에 useHours도 long형으로 선언한다.
		long useMinutes = ((outTime.getTime() - inTime.getTime()) % 3600000) / 60000; // getTime()함수를 이용하여 outTime와 inTime를 밀리세컨드로 변환한 다음 outTime.getTime() - inTime.getTime()를 하여 시간 차이 즉, 이용시간을 구한 후, 3600000으로 나눈 나머지를 구한 후, 그 나머지를 60000로 나누어 그 몫인 사용한 분(minute)를 구한 후 useMinutes에 대입한다. 이때 getTime()은 long형을 반환하기 때믄에 useMinutes도 long형으로 선언한다.
		
		int income = (int)useHours * price + (int)(((float)useMinutes / 60) * price); // useHours와 price의 곱, useMinutes를 60으로 나눈 몫과 price의 곱을 더하여 사용 시간에 따른 비용을 구하여 income에 대입한다. 이때 useHours와 useMinutes은 long형이고 income은 int형이기 때문에 useHours와 (((float)useMinutes / 60) * price)를 int형으로 형변환한다.
		return income;	// income을 반환한다.
	}
	
	public String getRoomName() { // roomName를 반환하는 getter 메소드
		return roomName; // roomName을 반환한다.
	}
	
	public Boolean getIsUsed() { // isUsed를 반환하는 getter 메소드
		return isUsed; // isUsed을 반환한다.
	}
	
	public int getHeadcount() { // headcount를 반환하는 getter 메소드
		return headcount; // headcount을 반환한다.
	}
	
	public int getPrice() { // price를 반환하는 getter 메소드
		return price; // price을 반환한다.
	}
	
	public User getWho() { // who를 반환하는 getter 메소드
		return who; // who을 반환한다.
	}
	
	public Date getInTime() { // inTime를 반환하는 getter 메소드
		return inTime; // inTime을 반환한다.
	}
	
	public Date getOutTime() { // outTime를 반환하는 getter 메소드
		return outTime; // outTime을 반환한다.
	}
	
	public void setPrice(int price) { // price를 설정하는 setter 메소드
		this.price = price; // Room 클래스의 price를 매개변수 price로 초기화한다. 
	}
	
	public void setRoomName(String roomName) { // roomName을 설정하는 setter 메소드
		this.roomName = roomName; // Room 클래스의 roomName를 매개변수 roomName로 초기화한다.
	}
	
	private void RoomDataInput(DataInputStream in) throws Exception
	{
		try {
			roomName = in.readUTF(); // 방의 이름 데이터. 문자열을 읽어 roomName을 설정한다. (파일로부터 문자열을 읽는다.)
			headcount = in.readInt(); // 방의 인원수 데이터. 4바이트를 읽어 int타입으로 반환한 값으로 headcount을 설정한다. (파일로부터 문자열을 읽는다.)
			price = in.readInt(); // 방의 가격 데이터. 4바이트를 읽어 int타입으로 반환한 값으로 roomPrice을 설정한다. (파일로부터 int형을 읽는다.)
			isUsed = in.readBoolean(); // 방 사용 유무 데이터. 1바이트를 읽어 Boolean타입으로 반환한 값으로 isUsed을 설정한다. (파일로부터 Boolean형을 읽는다.)
			if (isUsed == true) { // isUsed == true 일 때는 현재 방을 사용하고 있다는 의미로 사용자의 정보와 입실시간의 데이터를 읽는다.
				String userName = in.readUTF(); // 사용자의 이름 데이터. 문자열을 읽어 userName을 설정한다. (파일로부터 문자열을 읽는다.)
				String userPhoneNum = in.readUTF(); // 사용자의 전화번호 데이터. 문자열을 읽어 userPhoneNum을 설정한다. (파일로부터 문자열을 읽는다.)
				inTime = new Date(in.readLong()); // 사용자의 입실 시간 데이터. 8바이트를 읽어 long타입으로 반환한 값으로 inTime을 설정한다. (파일로부터 long형을 읽는다.)
				who = new User(userName, userPhoneNum);
			}
		}
		catch (IOException ioe) { // 오류가 발생하였을 때 발생하는 예외를 처리
			throw new Exception("ERROR"); // "오류가 발생하였습니다." 를 출력한다.
		}
	}
	
	public void RoomDataOutput(DataOutputStream out) throws Exception
	{
		try {
			out.writeUTF(roomName); // 방의 이름을 저장한다. (파일에 문자열을 출력한다.)
			out.writeInt(headcount); // 방의 인원수를 저장한다. (파일에 int타입 데이터를 출력한다.) 
			out.writeInt(price); // 방의 가격을 저장한다. (파일에 int타입 데이터를 출력한다.) 
			out.writeBoolean(isUsed); // 방의 사용 유무를 저장한다. (파일에 Boolean타입 데이터를 출력한다.)
			if (isUsed == true) // 현재 방을 사용하고 있다는 의미로 사용자의 정보와 입실시간도 출력한다(저장한다).
			{
				out.writeUTF(who.getName()); // 사용자의 이름을 저장한다. (파일에 문자열을 출력한다.)
				out.writeUTF(who.getPhoneNum()); // 사용자의 전화번호를 저장한다. (파일에 문자열을 출력한다.)
				out.writeLong(inTime.getTime()); // 방의 입실 시간을 저장한다. (파일에 long타입 데이터를 출력한다.)
			}
		}
		catch (IOException ioe) { // 오류가 발생할 경우에 발생하는 예외를 처리
			throw new Exception("Can't Write"); // "Can't Write"라는 Exception을 발생시킨다.
		}
	}
	
	public boolean equals(Object obj) { // equals 중복 정의
		if (!(obj instanceof Room)) { // 파라미터 객체가 Room 타입인지 검사한다.
			return false;
		}
		
		Room room = (Room) obj; // 파라미터 객체를 Room 타입으로 캐스트한다.
		if (this.roomName.equals(room.roomName)) { // 파라미터 객체와 자신 객체의 방 이름을 비교한다.
			return true;
		}
		else
		{
			return false;
		}
	}

}
