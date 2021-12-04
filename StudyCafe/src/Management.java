// Arrays Ŭ������ import
import java.util.*; // ArrayList Ŭ������ ����ϱ� ���� �ʿ�
import java.io.*; // ������ �а� ���� ���� �ʿ�
public class Management {
	// �������
	private ArrayList<Room> roomList = new ArrayList<Room>(); // ���� LinkedList�� ��Ÿ�� ����
	private int monthlyStatistics[] = new int[12]; // ������踦 ��Ÿ�� ������ 12���� ��Ÿ���� ���� ũ�Ⱑ 12�� ������ �迭�̴�.
	private int totalIncome[] = new int[31]; // ��¥�� ������ ��Ÿ�� ������ �Ѵ��� �ִ� 31�ϱ��� �ֱ� ������ ũ�Ⱑ 31�� ������ �迭�̴�.
	private int bigPrice; // 8�ο� ���� ������ ��Ÿ�� ����. �� ������ ������ ������ ���� ������ ����Ǿ��� ��� �� ����� ������ ���� �����ϱ� ���ؼ��̴�. ��, ���Ӱ� ������Ʈ�� ���� ������ �ٷ� ���Ӱ� �߰��� �濡 �ݿ��ϱ� ���ؼ��̴�. ���� �� ������ ������ ������, ���� ������ ������ �� ���� ���� ������ ��, ������ ���� ������ ������ ���� �������� �������� �ʴ´�. 
	private int middlePrice; // 4�ο� ���� ������ ��Ÿ�� ����.
	private int smallPrice; // 2�ο� ���� ������ ��Ÿ�� ����.
	
	// ����Լ�
	public Management(DataInputStream in, ObjectInputStream objIn) throws Exception { // �����ڷ� DataInputStream ��ü�� �Ű������� �޴´�. DataInputStream ��ü ������ �����͸� �о� ���� ���·� �����Ѵ�.
		DataInput(in, objIn); // �����͸� �о���� �Լ� DataInput�� ȣ���Ѵ�.
	}
	
	public Management(int bigPrice, int middlePrice, int smallPrice) { // Management Ŭ������ ������. �Ű����� bigPrice�� 8�ο� ���� ����, middlePrice�� 4�ο� ���� ����, smallPrice�� 2�ο� ���� ������ ��Ÿ����.
		this.bigPrice = bigPrice; // Management Ŭ������ bigPrice�� �Ű������� bigPrice�� �ʱ�ȭ�Ѵ�.
		this.middlePrice = middlePrice; // Management Ŭ������ middlePrice�� �Ű����� middlePrice�� �ʱ�ȭ�Ѵ�.
		this.smallPrice = smallPrice; // Management Ŭ������ smallPrice�� �Ű����� middlePrice�� �ʱ�ȭ�Ѵ�.
	}
	
	public Management()
	{
		
	}
	
	
	public ArrayList<Room> findEmpty(int personCount) throws Exception{ // "��� ã��" ����� ������ �Լ�. �Ű������� �� �̿����� �ο��� �޴´�.
		int num = roomList.size(); // ���� ����
		ArrayList<Room> emptyRoomList = new ArrayList<Room>(); // ����� ��Ÿ���� ����Ʈ
		
		for (int i = 0; i < num; i++) { // 0���� roomArr.length���� �ݺ�
			if((roomList.get(i).getIsUsed() == false) && (roomList.get(i).getHeadcount() >= personCount)) { // ���� ��� ����(isUsed)�� false�̰�, ���� �ִ��ο�(headcount)�� �Ű������� ���� personCount �̻��� ���
				emptyRoomList.add(roomList.get(i)); // �ӽ� ����� ����Ʈ�� ���� ������ �����ϴ� Room�� emptyRoomList�� �ִ´�.
			}
		}
		
		if(emptyRoomList.size() == 0) { // ����� �������� ���� ���, �� �̿����� �ο��� 8���� �ʰ��� ��� ����ó��
			throw new Exception("Not Exist"); // "��Not Exist"��� Exception�� �߻���Ų��.
		}
		else { // ����� ������ ���
			return emptyRoomList; // emptyRoomList�� ��ȯ�Ѵ�.
		}
	}
	
	
	public void addRoom(int headcount, String roomName) throws Exception{ // "�� �߰�" ����� ������ �Լ�. �ִ��ο� ��(*�ο�)�� ���� �̸��� �Ű������� �޴´�.
		int price; // ���� ������ ���� ����
		
		if(headcount == 8) { // headcount�� 8�� �� 
			price = bigPrice; // price�� bigPrice�� �����Ѵ�.
		}
		else if(headcount == 4) { // headcount�� 4�� ��
			price = middlePrice; // price�� middlePrice�� �����Ѵ�.
		}
		else if(headcount == 2) { // headcount�� 2�� ��
			price = smallPrice; // price�� smallPrice�� �����Ѵ�.
		}
		else { // headcount�� 8, 4, 2�� �ƴ� ��� ����ó��
			throw new Exception("Retry"); // "Retry"��� Exception�� �߻���Ų��.
		}
		
		int roomIndex = searchRoomName(roomName);
		if (roomIndex != -1) {
			throw new Exception("Same Room");
		}
		
		int index = searchLastIndex(headcount);
		
		Room newRoom = new Room(roomName, false, headcount, null, price, null); // ���ǿ� ���� �� ���� �����Ѵ�.
		if(index == -1) {
			roomList.add(newRoom); // roomList�� ������ ���� �߰��Ѵ�.
		}
		else {
			roomList.add(index, newRoom); // roomList�� ������ ���� �߰��Ѵ�.
		}
	}
	
	
	public void removeRoom(String roomName) throws Exception{ // "�� ����" ����� ������ �Լ�. ���� �̸��� �Ű������� �޴´�.
		if (!(roomList.remove(new Room(roomName)))){
			throw new Exception("Not Exist"); // "Not Exist"��� Exception�� �߻���Ų��.
		}
	}
	
	
	public Room checkIn(String roomName, User user) throws Exception{ // "üũ��" ����� ������ �Լ�. ���� �̸��� ����� ��ü�� �Ű������� �޴´�.
		int roomIndex = searchRoomName(roomName);// searchRoomName �Լ��� ����Ͽ� ���� �̸����� ���� ã�� �ش� �ε����� ��ȯ�Ѵ�.
		
		if (roomIndex == -1) { // �ش��ϴ� ���� �̸��� ���� ���� ���� �� ����ó��
			throw new Exception("Not Exist"); // "Not Exist"��� Exception�� �߻���Ų��.
		}
		else { // �ش��ϴ� ��ȣ�� ���� ���� ���� ��
			try { // ����ó��
				roomList.get(roomIndex).checkIn(user); // �� ��ü�� checkIn�Լ��� ȣ���Ѵ�.(���� üũ�� �Ѵ�.)
				return roomList.get(roomIndex); // checkIn�� ��(Room ��ü)�� ��ȯ�Ѵ�.
			}
			catch (IndexOutOfBoundsException IOOBE) { // �ε����� �������� ����� ��
				throw new Exception("Ioobe"); // "Ioobe"��� Exception�� �߻���Ų��.
			}
		}
	}
	
	
	public Room checkOut(String roomName) throws Exception{ // "üũ�ƿ�" ����� ������ �Լ�. �� �̸��� �Ű������� �޴´�.
		int roomIndex = searchRoomName(roomName);// searchUserNum �Լ��� ����Ͽ� ���� �̸����� ���� ã�� �ش� �ε����� ��ȯ�Ѵ�.
	
		if (roomIndex == -1) { // �ش��ϴ� �� �̸��� ���� ���� ���� �� ����ó��
			throw new Exception("Not Exist"); // "Not Exist"��� Exception�� �߻���Ų��.
		}
		else { // �ش��ϴ� ��ȣ�� ���� ���� ���� ��
			try {
				roomList.get(roomIndex).checkOut(); // �� ��ü�� checkOut�Լ��� ȣ���Ѵ�.(���� üũ�ƿ� �Ѵ�.)
				return roomList.get(roomIndex); // checkOut�� ��(Room ��ü)�� ��ȯ�Ѵ�.
			}
			catch (IndexOutOfBoundsException IOOBE) { // �ε����� �������� ����� ��
				throw new Exception("Ioobe"); // "Ioobe"��� Exception�� �߻���Ų��.
			}
		}
		
	}
	
	
	public int pay(String roomName) throws Exception{ // "�����ϴ�" ����� ������ �Լ�. �� �̸��� �Ű������� �޴´�.
		int roomIndex = searchRoomName(roomName); // searchRoomName �Լ��� ����Ͽ� ���� �̸����� ���� ã�� �ش� �ε����� ��ȯ�Ѵ�.
		
		if (roomIndex == -1) { // �ش��ϴ� �� �̸��� ���� ���� ���� �� ����ó��
			throw new Exception("Not Exist"); // "Not Exist"��� Exception�� �߻���Ų��.
		}
		else { // �ش��ϴ� �� �̸��� ���� ���� ���� ��
			try { // ����ó��
				return roomList.get(roomIndex).pay(); // �� ��ü�� pay�Լ��� ȣ���� ��, ����� ��ȯ�Ѵ�.(Room��ä�� pay�Լ��� ����� ��ȯ�Ѵ�.)
			}
			catch (IndexOutOfBoundsException IOOBE) { // �ε����� �������� ����� ��
				throw new Exception("Ioobe"); // "Ioobe"��� Exception�� �߻���Ų��.
			}
		}
	}
	
	public void setPrice(int headcount, int price) throws Exception{ // "���� �����ϱ�" ����� ������ �Լ�.
		if(headcount == 8) { // headcount�� 8�� ���
			bigPrice = price; // bigPrice�� price�� �����Ѵ�.(8�ο� ���� ������ �ٲ۴�.)
		}
		else if(headcount == 4) { // headcount�� 4�� ���
			middlePrice = price; // middlePrice�� price�� �����Ѵ�.(8�ο� ���� ������ �ٲ۴�.)
		}
		else if(headcount == 2) { // headcount�� 2�� ���
			smallPrice = price; // smallPrice�� price�� �����Ѵ�.(8�ο� ���� ������ �ٲ۴�.)
		}
		else { // headcount�� 8, 4, 2�� �ƴ� ��� ����ó��
			throw new Exception("Retry"); // "Retry"��� Exception�� �߻���Ų��.
		}
		
		int num = roomList.size(); // ���� ����
		
		for (int i = 0; i < num; i++) { // �ִ��ο��� headcount�� ���� ã�´�. // 0���� ���� ������ŭ �ݺ�
			if(roomList.get(i).getHeadcount() == headcount) { // ���� �ִ� �ο��� headcount�� ���
				roomList.get(i).setPrice(price); // �� ��ü�� setPrice�Լ��� ȣ���Ͽ� ���� ������ price�� �����Ѵ�.
			}
		}
	}
	
	public void setMonthlyStatistics(int month, int income) { // ������迡�� ���ϴ� ���� �������� �����ϴ� �Լ�. ���ϴ� �ް� ������ �Ű������� �޴´�.
		monthlyStatistics[month - 1] += income; // monthlyStatistics[month - 1]�� income�� ���ϰ� �ٽ� monthlyStatistics[month - 1]�� �����Ѵ�. �̶� �迭�� �ε����� 0���� �����Ƿ� 0��° �ε����� ��Ұ� 1��, 1��° �ε����� ��Ұ� 2�� ... 11��° �ε����� 12���̹Ƿ� [month - 1]�� �Ѵ�.
	} // += �� ����Ͽ� ������ �������� ������ �����Ͽ� �ǽð����� �̹� ���� ������ ��Ÿ������ �Ͽ���.
	
	public void setTotalIncome(int day, int income) { // ��¥�� ���Կ��� ���ϴ� ��¥�� �� ������ �����ϴ� �Լ�. ���ϴ� ��¥�� ������ �Ű������� �޴´�.
		totalIncome[day - 1] += income; // totalIncome[day - 1]�� income�� ���ϰ� �ٽ� totalIncome[day - 1]�� �����Ѵ�. �̶� �迭�� �ε����� 0���� �����Ƿ� 0��° �ε����� ��Ұ� 1��, 1��° �ε����� ��Ұ� 2�� ... 30��° �ε����� 31�̹Ƿ� [day - 1]�� �Ѵ�.
	} // += �� ����Ͽ� ������ �������� ������ �����Ͽ� �ǽð����� ������ ������ ��Ÿ������ �Ͽ���.
	
	public ArrayList<Room> getRoomArr() { // roomList�� ��ȯ�ϴ� getter �޼ҵ�
		return roomList; // roomList�� ��ȯ�Ѵ�.
	}
	
	public int getMonthlyStatistics(int month) { // ���ϴ� ���� �� ������ ��ȯ�ϴ� �Լ�. ���ϴ� ���� �Ű������� �޴´�.
		return monthlyStatistics[month - 1]; // monthlyStatistics[month - 1]�� ���� ��ȯ�Ѵ�.(���ϴ� ���� �� ������ ��ȯ�Ѵ�)
	}
	
	public int getTotalIncome(int day) { // ���ϴ� ��¥�� ������ ��ȯ�ϴ� �Լ�. ���ϴ� ��¥�� �Ű������� �޴´�.
		return totalIncome[day - 1]; // totalIncome[day - 1]�� ���� ��ȯ�Ѵ�.(���ϴ� ��¥�� ������ ��ȯ�Ѵ�)
	} 
	
	public int getBigPrice() // bigPrice�� ��ȯ�ϴ� getter �޼ҵ�
	{
		return bigPrice; // bigPrice�� ��ȯ�Ѵ�.
	}
	
	public int getMiddlePrice() // middlePrice�� ��ȯ�ϴ� getter �޼ҵ�
	{
		return middlePrice; // middlePrice�� ��ȯ�Ѵ�.
	}
	
	public int getSmallPrice() // smallPrice�� ��ȯ�ϴ� getter �޼ҵ�
	{
		return smallPrice; // smallPrice�� ��ȯ�Ѵ�.
	}
	
	public int searchRoomName(String roomName) { // ���� �̸����� ���� ã�� �Լ�. �ش� ���� �ε����� ��ȯ�Ѵ�.
		return roomList.indexOf(new Room(roomName)); // �� �迭�� �ε����� ��ȯ�Ѵ�.
	}
	
	public int searchUserNum(String userNum) { // ������� ��ȭ��ȣ�� ���� ã�� �Լ�. �ش� ���� �ε����� ��ȯ�Ѵ�.
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
	
	public ArrayList<Room> countBigRoom () { // �� ����Ʈ���� 8�ο� �� ����Ʈ�� ��ȯ�ϴ� �Լ� 
		int num = roomList.size(); // ���� ����
		ArrayList<Room> temRoomList = new ArrayList<Room>(); // �ش� �ο����� ���� ��Ÿ���� ����Ʈ
		
		for (int i = 0; i < num; i++) { // 0���� num�� �ݺ�
			if((roomList.get(i).getHeadcount() == 8)) { 
				temRoomList.add(roomList.get(i)); // temRoomList�� �߰��Ѵ�.
			}
		}
		return temRoomList; // temRoomList�� ��ȯ�Ѵ�.
	}
	
	public ArrayList<Room> countMiddleRoom () { // �� ����Ʈ���� 4�ο� �� ����Ʈ�� ��ȯ�ϴ� �Լ� 
		int num = roomList.size(); // ���� ����
		ArrayList<Room> temRoomList = new ArrayList<Room>(); // �ش� �ο����� ���� ��Ÿ���� ����Ʈ
		
		for (int i = 0; i < num; i++) { // 0���� num�� �ݺ�
			if((roomList.get(i).getHeadcount() == 4)) { 
				temRoomList.add(roomList.get(i)); // temRoomList�� �߰��Ѵ�.
			}
		}
		return temRoomList; // temRoomList�� ��ȯ�Ѵ�.
	}
	
	public ArrayList<Room> countSmallRoom () { // �� ����Ʈ���� 2�ο� �� ����Ʈ�� ��ȯ�ϴ� �Լ� 
		int num = roomList.size(); // ���� ����
		ArrayList<Room> temRoomList = new ArrayList<Room>(); // �ش� �ο����� ���� ��Ÿ���� ����Ʈ
		
		for (int i = 0; i < num; i++) { // 0���� num�� �ݺ�
			if((roomList.get(i).getHeadcount() == 2)) { 
				temRoomList.add(roomList.get(i)); // temRoomList�� �߰��Ѵ�.
			}
		}
		return temRoomList; // temRoomList�� ��ȯ�Ѵ�.
	}
	
	// �����͸� ����ȭ�ϴ� �Լ�
	public void ObjectOutput(int size, ObjectOutputStream objOut) throws Exception // �Ű������� ����Ʈ�� ����� �޴´�.
	{
		try { 
			for (int i = 0; i < size; i++) {
				objOut.writeObject(roomList.get(i)); // �� ��ü�� ����Ѵ�.
			}
		}
		catch (IOException ioe) // ������ �߻��� ��� ����ó��
		{
			throw new Exception("Can't Write"); // "Can't Write"��� Exception�� �߻���Ų��.
		}
	}
	
	// �����͸� �����ϴ� �Լ�
	public void Dataoutput(DataOutputStream out , ObjectOutputStream objOut) throws Exception
	{
		try { 
			// ������ �����ϱ�
			for(int i = 0; i < 31; i++) { // ��¥�� ���� �����͸� ����ϱ�(�����ϱ�) ���� �ݺ���
				out.writeInt(getTotalIncome(i+1)); // ��¥�� ������ �����Ѵ�. (���Ͽ� intŸ�� �����͸� ����Ѵ�.) 
			}
			for(int i = 0; i < 12; i++) { // �������� �����͸� ����ϱ�(�����ϱ�) ���� �ݺ���
				out.writeInt(getMonthlyStatistics(i+1)); // �������� ������ �����Ѵ�. (���Ͽ� intŸ�� �����͸� ����Ѵ�.) 
			}
			out.writeInt(roomList.size()); // ���� �迭�� ������ �����Ѵ�. (���Ͽ� intŸ�� �����͸� ����Ѵ�.) 
			out.writeInt(getBigPrice()); // 8�ο� ���� ������ �����Ѵ�. (���Ͽ� intŸ�� �����͸� ����Ѵ�.) 
			out.writeInt(getMiddlePrice()); // 4�ο� ���� ������ �����Ѵ�. (���Ͽ� intŸ�� �����͸� ����Ѵ�.) 
			out.writeInt(getSmallPrice()); // 2�ο� ���� ������ �����Ѵ�. (���Ͽ� intŸ�� �����͸� ����Ѵ�.) 
			
			int num = roomList.size(); // roomList�� ũ��
			
			/*for (int i = 0; i < num; i++) // ���� �������� ����ϱ�(�����ϱ�) ���� �ݺ���
			{
				roomList.get(i).RoomDataOutput(out);
			}*/
			
			ObjectOutput(num, objOut); // �����͸� ����ȭ�ϴ� �Լ� ȣ��
		}
		catch (IOException ioe) { // ������ �߻��� ��쿡 �߻��ϴ� ���ܸ� ó��
			throw new Exception("Can't Write"); // "Can't Write"��� Exception�� �߻���Ų��.
		}
		catch (Exception e) {
			throw new Exception("Can't Write"); // "Can't Write"��� Exception�� �߻���Ų��.
		}	
	}
	
	// ��ü�� ������ȭ �ϴ� �Լ�
	public void ObjectInput(ObjectInputStream objIn) throws Exception // �Ű������� ObjectInputStream ��ü�� �޴´�.
	{
		try {
			while (true) {
				Room room = (Room) objIn.readObject(); // �� ��ü�� �о�� room�� �ִ´�.
				roomList.add(room); // �� ����Ʈ�� �� ��ü�� �߰��Ѵ�.
			}
		}
		catch (FileNotFoundException fnfe){ // ������ �������� ���� ���
			throw new Exception("FileNotFound"); // "NotFound"��� Exception�� �߻���Ų��.
		}
		catch (EOFException eofe) { // ������ �� �о��� ���
		}
		catch (IOException ioe) // ������ �߻��� ��� ����ó��
		{
			throw new Exception("Can't Read"); // "Can't Read"��� Exception�� �߻���Ų��.
		}
		catch (ClassNotFoundException cnfe)
		{
			throw new Exception("ClassNotFound"); // "ClassNotFound"��� Exception�� �߻���Ų��.
		}
	}
		
	// �����͸� �о���� �Լ�
	public void DataInput(DataInputStream in, ObjectInputStream objIn) throws Exception
	{
		try { // ����ó���� ���� try-catch�� ���
			// ������ �о����
			for(int i = 0; i < 31; i++) { // ��¥�� ���� �����͸� �б� ���� �ݺ���
				setTotalIncome((i + 1), in.readInt()); // 4����Ʈ�� �о� intŸ������ ��ȯ�� ������ ��¥�� ������ �����Ѵ�.(���Ϸκ��� int���� �д´�.)
			}
			
			for(int i = 0; i < 12; i++) { // ���� ��� �����͸� �б� ���� �ݺ���
				setMonthlyStatistics((i + 1), in.readInt()); // 4����Ʈ�� �о� intŸ������ ��ȯ�� ������ ���� ��踦 �����Ѵ�.(���Ϸκ��� int���� �д´�.)
			}
			
			int roomNum = in.readInt(); // 4����Ʈ�� �о� intŸ������ ��ȯ�� ���� roomNum(�� �迭�� ����)�� �ִ´�.(���Ϸκ��� int���� �д´�.)
			
			roomList = new ArrayList<Room>(); // �� ����Ʈ�� �����Ѵ�.
			bigPrice = in.readInt(); // 8�ο� ���� ���� ������. 4����Ʈ�� �о� intŸ������ ��ȯ�� ������ 8�ο� ���� ������ �����Ѵ�.(���Ϸκ��� int���� �д´�.)
			middlePrice = in.readInt(); // 4�ο� ���� ���� ������. 4����Ʈ�� �о� intŸ������ ��ȯ�� ������ 4�ο� ���� ������ �����Ѵ�.(���Ϸκ��� int���� �д´�.)
			smallPrice = in.readInt(); // 2�ο� ���� ���� ������. 4����Ʈ�� �о� intŸ������ ��ȯ�� ������ 2�ο� ���� ������ �����Ѵ�.(���Ϸκ��� int���� �д´�.)
			
			/*for (int i = 0; i < roomNum; i++) { // ���� ���� �����͵��� �б� ���� �ݺ���
				Room room = new Room(in);
				roomList.add(room);
			}*/
			ObjectInput(objIn);
		}
		catch (IOException ioe) { // ������ �߻��Ͽ��� �� �߻��ϴ� ���ܸ� ó��
			throw new Exception("ERROR"); // "ERROR"��� Exception�� �߻���Ų��.
		}
		catch (Exception e) {
			String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
			if (msg.equals("FileNotFound") || msg.equals("Can't Read")) // �߻��� exception�� "FileNotFound" �Ǵ� "Can't Read"�� ���
			{
				throw new Exception("Can't Read"); // "NotFound"��� Exception�� �߻���Ų��.
			}
			else if(msg.equals("ClassNotFound")){
				throw new Exception("ERROR"); // "ERROR"��� Exception�� �߻���Ų��.
			}
		}
				
	}	
}
