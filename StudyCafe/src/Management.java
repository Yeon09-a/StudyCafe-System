// Arrays 클래스를 import
import java.util.*; // ArrayList 클래스를 사용하기 위해 필요
import java.io.*; // 파일을 읽고 쓰기 위해 필요
public class Management {
	// 멤버변수
	private ArrayList<Room> roomList = new ArrayList<Room>(); // 방의 LinkedList를 나타낸 변수
	private int monthlyStatistics[] = new int[12]; // 월별통계를 나타낸 변수로 12달을 나타내기 위해 크기가 12인 정수형 배열이다.
	private int totalIncome[] = new int[31]; // 날짜별 수입을 나타낸 변수로 한달이 최대 31일까지 있기 때문에 크기가 31인 정수형 배열이다.
	private int bigPrice; // 8인용 방의 가격을 나타낸 변수. 이 변수를 생성한 이유는 방의 가격이 변경되었을 경우 그 변경된 값으로 방을 생성하기 위해서이다. 즉, 새롭게 업데이트한 방의 가격을 바로 새롭게 추가한 방에 반영하기 위해서이다. 만일 이 변수를 만들지 않으면, 방의 가격을 변경한 후 방을 새로 생성할 시, 생성한 방의 가격은 변경한 방의 가격으로 설정되지 않는다. 
	private int middlePrice; // 4인용 방의 가격을 나타낸 변수.
	private int smallPrice; // 2인용 방의 가격을 나타낸 변수.
	
	// 멤버함수
	public Management(DataInputStream in, ObjectInputStream objIn) throws Exception { // 생성자로 DataInputStream 객체를 매개변수로 받는다. DataInputStream 객체 파일의 데이터를 읽어 이전 상태로 복구한다.
		DataInput(in, objIn); // 데이터를 읽어오는 함수 DataInput을 호출한다.
	}
	
	public Management(int bigPrice, int middlePrice, int smallPrice) { // Management 클래스의 생성자. 매개변수 bigPrice는 8인용 방의 가격, middlePrice는 4인용 방의 가격, smallPrice는 2인용 방의 가격을 나타낸다.
		this.bigPrice = bigPrice; // Management 클래스의 bigPrice를 매개변수의 bigPrice로 초기화한다.
		this.middlePrice = middlePrice; // Management 클래스의 middlePrice를 매개변수 middlePrice로 초기화한다.
		this.smallPrice = smallPrice; // Management 클래스의 smallPrice를 매개변수 middlePrice로 초기화한다.
	}
	
	public Management()
	{
		
	}
	
	
	public ArrayList<Room> findEmpty(int personCount) throws Exception{ // "빈방 찾기" 기능을 구현한 함수. 매개변수로 방 이용자의 인원을 받는다.
		int num = roomList.size(); // 방의 개수
		ArrayList<Room> emptyRoomList = new ArrayList<Room>(); // 빈방을 나타내는 리스트
		
		for (int i = 0; i < num; i++) { // 0부터 roomArr.length까지 반복
			if((roomList.get(i).getIsUsed() == false) && (roomList.get(i).getHeadcount() >= personCount)) { // 방의 사용 유무(isUsed)가 false이고, 방의 최대인원(headcount)가 매개변수로 받은 personCount 이상일 경우
				emptyRoomList.add(roomList.get(i)); // 임시 빈방의 리스트에 위의 조건을 만족하는 Room을 emptyRoomList에 넣는다.
			}
		}
		
		if(emptyRoomList.size() == 0) { // 빈방이 존재하지 않을 경우, 방 이용자의 인원이 8명을 초과할 경우 예외처리
			throw new Exception("Not Exist"); // "빈Not Exist"라는 Exception을 발생시킨다.
		}
		else { // 빈방이 존재할 경우
			return emptyRoomList; // emptyRoomList을 반환한다.
		}
	}
	
	
	public void addRoom(int headcount, String roomName) throws Exception{ // "방 추가" 기능을 구현한 함수. 최대인원 수(*인용)와 방의 이름을 매개변수로 받는다.
		int price; // 새로 생성될 방의 가격
		
		if(headcount == 8) { // headcount가 8일 때 
			price = bigPrice; // price을 bigPrice로 설정한다.
		}
		else if(headcount == 4) { // headcount가 4일 때
			price = middlePrice; // price을 middlePrice로 설정한다.
		}
		else if(headcount == 2) { // headcount가 2일 때
			price = smallPrice; // price을 smallPrice로 설정한다.
		}
		else { // headcount가 8, 4, 2가 아닌 경우 예외처리
			throw new Exception("Retry"); // "Retry"라는 Exception을 발생시킨다.
		}
		
		int roomIndex = searchRoomName(roomName);
		if (roomIndex != -1) {
			throw new Exception("Same Room");
		}
		
		int index = searchLastIndex(headcount);
		
		Room newRoom = new Room(roomName, false, headcount, null, price, null); // 조건에 따른 새 방을 생성한다.
		if(index == -1) {
			roomList.add(newRoom); // roomList에 생성한 방을 추가한다.
		}
		else {
			roomList.add(index, newRoom); // roomList에 생성한 방을 추가한다.
		}
	}
	
	
	public void removeRoom(String roomName) throws Exception{ // "방 제거" 기능을 구현한 함수. 방의 이름을 매개변수로 받는다.
		if (!(roomList.remove(new Room(roomName)))){
			throw new Exception("Not Exist"); // "Not Exist"라는 Exception을 발생시킨다.
		}
	}
	
	
	public Room checkIn(String roomName, User user) throws Exception{ // "체크인" 기능을 구현한 함수. 방의 이름과 사용자 객체를 매개변수로 받는다.
		int roomIndex = searchRoomName(roomName);// searchRoomName 함수를 사용하여 방의 이름으로 방을 찾아 해당 인덱스를 반환한다.
		
		if (roomIndex == -1) { // 해당하는 방의 이름을 가진 방이 없을 때 예외처리
			throw new Exception("Not Exist"); // "Not Exist"라는 Exception을 발생시킨다.
		}
		else { // 해당하는 번호를 가진 방이 있을 때
			try { // 예외처리
				roomList.get(roomIndex).checkIn(user); // 방 객체의 checkIn함수를 호출한다.(방을 체크인 한다.)
				return roomList.get(roomIndex); // checkIn한 방(Room 객체)을 반환한다.
			}
			catch (IndexOutOfBoundsException IOOBE) { // 인덱스가 범위에서 벗어났을 때
				throw new Exception("Ioobe"); // "Ioobe"라는 Exception을 발생시킨다.
			}
		}
	}
	
	
	public Room checkOut(String roomName) throws Exception{ // "체크아웃" 기능을 구현한 함수. 방 이름을 매개변수로 받는다.
		int roomIndex = searchRoomName(roomName);// searchUserNum 함수를 사용하여 방의 이름으로 방을 찾아 해당 인덱스를 반환한다.
	
		if (roomIndex == -1) { // 해당하는 방 이름을 가진 방이 없을 때 예외처리
			throw new Exception("Not Exist"); // "Not Exist"라는 Exception을 발생시킨다.
		}
		else { // 해당하는 번호를 가진 방이 있을 때
			try {
				roomList.get(roomIndex).checkOut(); // 방 객체의 checkOut함수를 호출한다.(방을 체크아웃 한다.)
				return roomList.get(roomIndex); // checkOut한 방(Room 객체)을 반환한다.
			}
			catch (IndexOutOfBoundsException IOOBE) { // 인덱스가 범위에서 벗어났을 때
				throw new Exception("Ioobe"); // "Ioobe"라는 Exception을 발생시킨다.
			}
		}
		
	}
	
	
	public int pay(String roomName) throws Exception{ // "지불하다" 기능을 구현한 함수. 방 이름을 매개변수로 받는다.
		int roomIndex = searchRoomName(roomName); // searchRoomName 함수를 사용하여 방의 이름으로 방을 찾아 해당 인덱스를 반환한다.
		
		if (roomIndex == -1) { // 해당하는 방 이름을 가진 방이 없을 때 예외처리
			throw new Exception("Not Exist"); // "Not Exist"라는 Exception을 발생시킨다.
		}
		else { // 해당하는 방 이름을 가진 방이 있을 때
			try { // 예외처리
				return roomList.get(roomIndex).pay(); // 방 객체의 pay함수를 호출한 후, 비용을 반환한다.(Room객채의 pay함수는 비용을 반환한다.)
			}
			catch (IndexOutOfBoundsException IOOBE) { // 인덱스가 범위에서 벗어났을 때
				throw new Exception("Ioobe"); // "Ioobe"라는 Exception을 발생시킨다.
			}
		}
	}
	
	public void setPrice(int headcount, int price) throws Exception{ // "가격 설정하기" 기능을 구현한 함수.
		if(headcount == 8) { // headcount가 8인 경우
			bigPrice = price; // bigPrice에 price를 대입한다.(8인용 방의 가격을 바꾼다.)
		}
		else if(headcount == 4) { // headcount가 4인 경우
			middlePrice = price; // middlePrice에 price를 대입한다.(8인용 방의 가격을 바꾼다.)
		}
		else if(headcount == 2) { // headcount가 2인 경우
			smallPrice = price; // smallPrice에 price를 대입한다.(8인용 방의 가격을 바꾼다.)
		}
		else { // headcount가 8, 4, 2가 아닌 경우 예외처리
			throw new Exception("Retry"); // "Retry"라는 Exception을 발생시킨다.
		}
		
		int num = roomList.size(); // 방의 개수
		
		for (int i = 0; i < num; i++) { // 최대인원이 headcount인 방을 찾는다. // 0부터 방의 개수만큼 반복
			if(roomList.get(i).getHeadcount() == headcount) { // 방의 최대 인원이 headcount일 경우
				roomList.get(i).setPrice(price); // 방 객체의 setPrice함수를 호출하여 방의 가격을 price로 설정한다.
			}
		}
	}
	
	public void setMonthlyStatistics(int month, int income) { // 월별통계에서 원하는 달의 월수입을 설정하는 함수. 원하는 달과 수입을 매개변수로 받는다.
		monthlyStatistics[month - 1] += income; // monthlyStatistics[month - 1]에 income을 더하고 다시 monthlyStatistics[month - 1]에 대입한다. 이때 배열은 인덱스가 0부터 있으므로 0번째 인덱스의 요소가 1월, 1번째 인덱스의 요소가 2월 ... 11번째 인덱스가 12월이므로 [month - 1]을 한다.
	} // += 을 사용하여 수입이 날때마다 수입을 누적하여 실시간으로 이번 달의 수입을 나타내도록 하였다.
	
	public void setTotalIncome(int day, int income) { // 날짜별 수입에서 원하는 날짜의 총 수입을 설정하는 함수. 원하는 날짜과 수입을 매개변수로 받는다.
		totalIncome[day - 1] += income; // totalIncome[day - 1]에 income을 더하고 다시 totalIncome[day - 1]에 대입한다. 이때 배열은 인덱스가 0부터 있으므로 0번째 인덱스의 요소가 1일, 1번째 인덱스의 요소가 2일 ... 30번째 인덱스가 31이므로 [day - 1]을 한다.
	} // += 을 사용하여 수입이 날때마다 수입을 누적하여 실시간으로 오늘의 수입을 나타내도록 하였다.
	
	public ArrayList<Room> getRoomArr() { // roomList를 반환하는 getter 메소드
		return roomList; // roomList을 반환한다.
	}
	
	public int getMonthlyStatistics(int month) { // 원하는 달의 월 수입을 반환하는 함수. 원하는 달을 매개변수로 받는다.
		return monthlyStatistics[month - 1]; // monthlyStatistics[month - 1]의 값을 반환한다.(원하는 달의 월 수입을 반환한다)
	}
	
	public int getTotalIncome(int day) { // 원하는 날짜의 수입을 반환하는 함수. 원하는 날짜를 매개변수로 받는다.
		return totalIncome[day - 1]; // totalIncome[day - 1]의 값을 반환한다.(원하는 날짜의 수입을 반환한다)
	} 
	
	public int getBigPrice() // bigPrice를 반환하는 getter 메소드
	{
		return bigPrice; // bigPrice을 반환한다.
	}
	
	public int getMiddlePrice() // middlePrice를 반환하는 getter 메소드
	{
		return middlePrice; // middlePrice을 반환한다.
	}
	
	public int getSmallPrice() // smallPrice를 반환하는 getter 메소드
	{
		return smallPrice; // smallPrice을 반환한다.
	}
	
	public int searchRoomName(String roomName) { // 방의 이름으로 방을 찾는 함수. 해당 방의 인덱스를 반환한다.
		return roomList.indexOf(new Room(roomName)); // 방 배열의 인덱스를 반환한다.
	}
	
	public int searchUserNum(String userNum) { // 사용자의 전화번호로 방을 찾는 함수. 해당 방의 인덱스를 반환한다.
		int num = -1;
		int size = roomList.size();
		
		for (int i = 0; i < size; i++) {
			if (roomList.get(i).getIsUsed() == true && roomList.get(i).getWho().getPhoneNum().equals(userNum)) {
				num = i;
				break;
			}
		}
		return num;
	}
	
	public int searchLastIndex(int headCount) {
		int index = -1;
		int size = roomList.size();
		for (int i = size - 1; i >= 0; i--) {
			if (roomList.get(i).getHeadcount() == headCount) {
				index = i + 1;
				break;
			}
		}
		return index;
	}
	
	public ArrayList<Room> countBigRoom () { // 방 리스트에서 8인용 방 리스트를 반환하는 함수 
		int num = roomList.size(); // 방의 개수
		ArrayList<Room> temRoomList = new ArrayList<Room>(); // 해당 인원수의 방을 나타내는 리스트
		
		for (int i = 0; i < num; i++) { // 0부터 num번 반복
			if((roomList.get(i).getHeadcount() == 8)) { 
				temRoomList.add(roomList.get(i)); // temRoomList에 추가한다.
			}
		}
		return temRoomList; // temRoomList을 반환한다.
	}
	
	public ArrayList<Room> countMiddleRoom () { // 방 리스트에서 4인용 방 리스트를 반환하는 함수 
		int num = roomList.size(); // 방의 개수
		ArrayList<Room> temRoomList = new ArrayList<Room>(); // 해당 인원수의 방을 나타내는 리스트
		
		for (int i = 0; i < num; i++) { // 0부터 num번 반복
			if((roomList.get(i).getHeadcount() == 4)) { 
				temRoomList.add(roomList.get(i)); // temRoomList에 추가한다.
			}
		}
		return temRoomList; // temRoomList을 반환한다.
	}
	
	public ArrayList<Room> countSmallRoom () { // 방 리스트에서 2인용 방 리스트를 반환하는 함수 
		int num = roomList.size(); // 방의 개수
		ArrayList<Room> temRoomList = new ArrayList<Room>(); // 해당 인원수의 방을 나타내는 리스트
		
		for (int i = 0; i < num; i++) { // 0부터 num번 반복
			if((roomList.get(i).getHeadcount() == 2)) { 
				temRoomList.add(roomList.get(i)); // temRoomList에 추가한다.
			}
		}
		return temRoomList; // temRoomList을 반환한다.
	}
	
	// 데이터를 직렬화하는 함수
	public void ObjectOutput(int size, ObjectOutputStream objOut) throws Exception // 매개변수로 리스트의 사이즈를 받는다.
	{
		try { 
			for (int i = 0; i < size; i++) {
				objOut.writeObject(roomList.get(i)); // 방 객체를 출력한다.
			}
		}
		catch (IOException ioe) // 오류가 발생할 경우 예외처리
		{
			throw new Exception("Can't Write"); // "Can't Write"라는 Exception을 발생시킨다.
		}
	}
	
	// 데이터를 저장하는 함수
	public void Dataoutput(DataOutputStream out , ObjectOutputStream objOut) throws Exception
	{
		try { 
			// 데이터 저장하기
			for(int i = 0; i < 31; i++) { // 날짜별 수입 데이터를 출력하기(저장하기) 위한 반복문
				out.writeInt(getTotalIncome(i+1)); // 날짜별 수입을 저장한다. (파일에 int타입 데이터를 출력한다.) 
			}
			for(int i = 0; i < 12; i++) { // 월별수입 데이터를 출력하기(저장하기) 위한 반복문
				out.writeInt(getMonthlyStatistics(i+1)); // 월별수입 수입을 저장한다. (파일에 int타입 데이터를 출력한다.) 
			}
			out.writeInt(roomList.size()); // 방의 배열의 개수를 저장한다. (파일에 int타입 데이터를 출력한다.) 
			out.writeInt(getBigPrice()); // 8인용 방의 가격을 저장한다. (파일에 int타입 데이터를 출력한다.) 
			out.writeInt(getMiddlePrice()); // 4인용 방의 가격을 저장한다. (파일에 int타입 데이터를 출력한다.) 
			out.writeInt(getSmallPrice()); // 2인용 방의 가격을 저장한다. (파일에 int타입 데이터를 출력한다.) 
			
			int num = roomList.size(); // roomList의 크기
			
			/*for (int i = 0; i < num; i++) // 방의 정보들을 출력하기(저장하기) 위한 반복문
			{
				roomList.get(i).RoomDataOutput(out);
			}*/
			
			ObjectOutput(num, objOut); // 데이터를 직렬화하는 함수 호출
		}
		catch (IOException ioe) { // 오류가 발생할 경우에 발생하는 예외를 처리
			throw new Exception("Can't Write"); // "Can't Write"라는 Exception을 발생시킨다.
		}
		catch (Exception e) {
			throw new Exception("Can't Write"); // "Can't Write"라는 Exception을 발생시킨다.
		}	
	}
	
	// 객체를 역직렬화 하는 함수
	public void ObjectInput(ObjectInputStream objIn) throws Exception // 매개변수로 ObjectInputStream 객체를 받는다.
	{
		try {
			while (true) {
				Room room = (Room) objIn.readObject(); // 방 객체를 읽어와 room에 넣는다.
				roomList.add(room); // 방 리스트에 방 객체를 추가한다.
			}
		}
		catch (FileNotFoundException fnfe){ // 파일이 존재하지 않을 경우
			throw new Exception("FileNotFound"); // "NotFound"라는 Exception을 발생시킨다.
		}
		catch (EOFException eofe) { // 파일을 다 읽었을 경우
		}
		catch (IOException ioe) // 오류가 발생할 경우 예외처리
		{
			throw new Exception("Can't Read"); // "Can't Read"라는 Exception을 발생시킨다.
		}
		catch (ClassNotFoundException cnfe)
		{
			throw new Exception("ClassNotFound"); // "ClassNotFound"라는 Exception을 발생시킨다.
		}
	}
		
	// 데이터를 읽어오는 함수
	public void DataInput(DataInputStream in, ObjectInputStream objIn) throws Exception
	{
		try { // 예외처리를 위해 try-catch문 사용
			// 데이터 읽어오기
			for(int i = 0; i < 31; i++) { // 날짜별 수입 데이터를 읽기 위한 반복문
				setTotalIncome((i + 1), in.readInt()); // 4바이트를 읽어 int타입으로 반환한 값으로 날짜별 수입을 설정한다.(파일로부터 int형을 읽는다.)
			}
			
			for(int i = 0; i < 12; i++) { // 월별 통계 데이터를 읽기 위한 반복문
				setMonthlyStatistics((i + 1), in.readInt()); // 4바이트를 읽어 int타입으로 반환한 값으로 월별 통계를 설정한다.(파일로부터 int형을 읽는다.)
			}
			
			int roomNum = in.readInt(); // 4바이트를 읽어 int타입으로 반환한 값을 roomNum(방 배열의 개수)에 넣는다.(파일로부터 int형을 읽는다.)
			
			roomList = new ArrayList<Room>(); // 방 리스트를 생성한다.
			bigPrice = in.readInt(); // 8인용 방의 가격 데이터. 4바이트를 읽어 int타입으로 반환한 값으로 8인용 방의 가격을 설정한다.(파일로부터 int형을 읽는다.)
			middlePrice = in.readInt(); // 4인용 방의 가격 데이터. 4바이트를 읽어 int타입으로 반환한 값으로 4인용 방의 가격을 설정한다.(파일로부터 int형을 읽는다.)
			smallPrice = in.readInt(); // 2인용 방의 가격 데이터. 4바이트를 읽어 int타입으로 반환한 값으로 2인용 방의 가격을 설정한다.(파일로부터 int형을 읽는다.)
			
			/*for (int i = 0; i < roomNum; i++) { // 방의 정보 데이터들을 읽기 위한 반복문
				Room room = new Room(in);
				roomList.add(room);
			}*/
			ObjectInput(objIn);
		}
		catch (IOException ioe) { // 오류가 발생하였을 때 발생하는 예외를 처리
			throw new Exception("ERROR"); // "ERROR"라는 Exception을 발생시킨다.
		}
		catch (Exception e) {
			String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
			if (msg.equals("FileNotFound") || msg.equals("Can't Read")) // 발생한 exception이 "FileNotFound" 또는 "Can't Read"인 경우
			{
				throw new Exception("Can't Read"); // "NotFound"라는 Exception을 발생시킨다.
			}
			else if(msg.equals("ClassNotFound")){
				throw new Exception("ERROR"); // "ERROR"라는 Exception을 발생시킨다.
			}
		}
				
	}	
}
