import java.util.ArrayList;
import java.util.Scanner; // Scanner�� ����ϱ� ���� import�Ѵ�.
import java.text.SimpleDateFormat;
import java.io.*; // ������ �а� ���� ���� �ʿ�
public class UI { // �������̽� Ŭ����
	public static void main(String args[]) throws Exception{
		
		DataInputStream in = null; // DataInputStream ��ü�� ���� ���� in�� �����Ѵ�
		ObjectInputStream objIn = null; // ObjectInputStream ��ü�� ���� ���� objIn�� �����Ѵ�.
		
		
		Management cms = null; // Management ��ü�� ���� ���� cms�� �����Ѵ�.
		
		try {
			in = new DataInputStream(new FileInputStream("StudyCafeData.dat")); // "StudyCafeData.dat" ������ ����. FileInputStream ��ü�� �����ؼ� DataInputStream �������� �Ķ���ͷ� ����Ѵ�.
			objIn = new ObjectInputStream(new FileInputStream("StudyCafeObjectSerialization.dat")); // "StudyCafeObjectSerialization.dat" ������ ����. ObjectInputStream ��ü�� �����Ѵ�.
			//cms = new Management(in, objIn); // Management ��ü�� �����Ѵ�.
			cms = new Management();
		}
		catch (FileNotFoundException fnfe) { // ���� ������ �������� ���� ��쿡 �߻��ϴ� ���ܸ� ó��
			System.out.println("������ ã�� �� �����ϴ�."); // "������ ã�� �� �����ϴ�." �� ����Ѵ�.
		}
		catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� �� catch�� �´�.
			String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
			if (msg.equals("ERROR")) // �߻��� exception�� "ERROR"�� ���
			{
				System.out.println("������ �߻��Ͽ����ϴ�."); // "������ �߻��Ͽ����ϴ�." �� ����Ѵ�.
			}
			else if (msg.equals("Can't Read")){
				System.out.println("�����͸� �ҷ��� �� �����ϴ�."); // "�����͸� �ҷ��� �� �����ϴ�." �� ����Ѵ�.
			}
			System.out.println();
		}
		finally { // �ݵ�� �����ؾ� �ϴ� finally
			try { // ����ó���� ���� try-catch�� ���
				objIn.close(); // ������ �ݴ´�.
				in.close(); // ������ �ݴ´�.
			}
			catch (Exception e) { // ������ ���� �� �߻��ϴ� ���ܸ� ó��
			}
		}
				
		int mode = 0; // ��带 �����ϴ� ����(0 : ����, 1 : �����, 2 : �Ŵ���)
		
		Scanner scan = new Scanner(System.in); // ����ڷκ��� �Է��� �ޱ����� Scanner ��ü�� �����.
		int menuNumber = 0; // ������� �Է��� ���� ����
		
		while(true) { 
			if(mode == 0) // ���� ���
			{
				System.out.println();
				System.out.println("���"); // "���" �� ����Ѵ�.
				System.out.println();
				System.out.println("1. �����"); // "1. �����" �� ����Ѵ�.
				System.out.println("2. ������"); // "2. ������" �� ����Ѵ�.
				System.out.println("3. ����"); // "3. ����" �� ����Ѵ�.
				
				try { // ����ó���� ���� try-catch�� ���
					menuNumber = scan.nextInt(); // ��ȣ�� �Է¹޴´�.
					if((menuNumber != 1) && (menuNumber != 2) && (menuNumber != 3)) { // 1, 2, 3 �̿��� ���ڸ� �Է¹޾��� ��
						throw new Exception("�߸� �����̽��ϴ�."); // "�߸� �����̽��ϴ�."��� Exception�� �߻���Ų��.
					}
				}
				catch (java.util.InputMismatchException e) { // nextInt()�� Integer�� �ƴ� �ٸ� ���� �Է¹޾��� ��
					scan.nextLine(); // ���ѷ����� �����ϱ� ���� scan.nextLine()�� �߰��Ͽ� nextInt() �޼����� ����� �Է��� ������ ���๮�ڸ� �����Ѵ�.
					System.out.println("�߸� �����̽��ϴ�."); // "�߸� �����̽��ϴ�."��� Exception�� �߻���Ų��.
					System.out.println();
				}
				catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� �� catch�� �´�.
					String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
					System.out.println(msg); // msg�� ����Ѵ�.
					System.out.println();
				}
				
				if(menuNumber == 1) // menuNumber(�Է�)�� 1�� ��
				{
					mode = 1; // ��带 ����ڷ� �ٲ۴�.
				}
				else if (menuNumber == 2) // menuNumber(�Է�)�� 2�� ��
				{
					mode = 2; // ��带 �����ڷ� �ٲ۴�.
				}
				else if (menuNumber == 3) // menuNumber(�Է�)�� 3�� ��
				{
					Save(cms);
					break; // while���� �������� ������.
				}
			}
			else if (mode == 1) // ����� ���
			{
				System.out.println();
				System.out.println("�޴�"); // "�޴�" �� ����Ѵ�.
				System.out.println();
				System.out.println("1. check-in"); // "1. check-in" �� ����Ѵ�.
				System.out.println("2. check-out"); // "2. check-out" �� ����Ѵ�.
				System.out.println("3. �ڷΰ���"); // "3. �ڷΰ���" �� ����Ѵ�.
				
				try { // ����ó���� ���� try-catch�� ���
					menuNumber = scan.nextInt(); // ��ȣ�� �Է¹޴´�.
					if((menuNumber != 1) && (menuNumber != 2) && (menuNumber != 3)) { // 1, 2, 3 �̿��� ���ڸ� �Է¹޾��� ��
						throw new Exception("�߸� �����̽��ϴ�."); // "�߸� �����̽��ϴ�."��� Exception�� �߻���Ų��.
					}
				}
				catch (java.util.InputMismatchException e) { // nextInt()�� Integer�� �ƴ� �ٸ� ���� �Է¹޾��� ��
					scan.nextLine(); // ���ѷ����� �����ϱ� ���� scan.nextLine()�� �߰��Ͽ� nextInt() �޼����� ����� �Է��� ������ ���๮�ڸ� �����Ѵ�.
					System.out.println("�߸� �����̽��ϴ�."); // "�߸� �����̽��ϴ�."��� Exception�� �߻���Ų��.
					System.out.println();
				}
				catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� �� catch�� �´�.
					String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
					System.out.println(msg); // msg�� ����Ѵ�.
					System.out.println();
				}
				
				if(menuNumber == 1) // menuNumber(�Է�)�� 1�� ��
				{
					System.out.println(); 
					System.out.print("����� ���� �̸��� �Է��Ͻʽÿ�. "); // "����� ���� �̸��� �Է����ֽʽÿ�. " �� ����Ѵ�.
					String roomName; // ����ڰ� �Է��� ���� �̸��� �޾Ƴ� ����
					String userName ; // ����ڰ� �Է��� ������� �̸��� �޾Ƴ� ����
					String userNum; // ����ڰ� �Է��� ������� ��ȭ��ȣ�� �޾Ƴ� ����
					try {
						scan.nextLine(); // scan.nextLine()�� �߰��Ͽ� nextInt() �޼����� ����� �Է��� ������ ���๮�ڸ� �����Ѵ�.
						roomName = scan.nextLine(); // ���� �̸��� �Է¹޾� roomName�� �����Ѵ�.
						System.out.print("������� �̸��� �Է��Ͻʽÿ�. "); // "������� �̸��� �Է��Ͻʽÿ�. " �� ����Ѵ�.
						userName = scan.nextLine(); // ������� ��ȣ�� �Է¹޾� userNum�� �����Ѵ�.
						System.out.print("������� ��ȣ�� �Է��Ͻʽÿ�. "); // "������� ��ȣ�� �Է����ֽʽÿ�. " �� ����Ѵ�.
						userNum = scan.nextLine(); // ������� ��ȣ�� �Է¹޾� userNum�� �����Ѵ�.
						User user = new User(userName, userNum); // new�� ����Ͽ� ����� ��ü�� �����ϰ� ������ ��ü�� �ּҰ��� user�� �����Ѵ�.
						Room useRoom = cms.checkIn(roomName, user); // roomName�� user�� �Ű������� �Ͽ� cms�� checkIn�Լ��� ȣ���ϰ�, checkIn�Լ��� ��ȯ�� Room ��ü�� useRoom�� �޴´�.
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� aa hh�� mm�� ss��"); // SimpleDateFormat ������ �Ķ���ͷ� ��¥�� �ð��� ������ �ѱ��, new�� ��ü�� �����Ѵ�.
						System.out.println("�� �̸� : " + useRoom.getRoomName() + "\n�ִ� �̿��ο� : " + useRoom.getHeadcount() + "\n�̿��� : " + useRoom.getWho().getName() + "\n�ð��� ���� : " + useRoom.getPrice() + "\n�Խ� �ð� : " + dateFormat.format(useRoom.getInTime())); // check-in�� ���� �̸�, �ִ� �̿� �ο�, �̿���, �ð��� ����, �Խ� �ð��� ����Ѵ�.
						System.out.println();
						//Save(cms);
					}
					catch (java.util.NoSuchElementException nsee) { // nextLine()�� �Է��� ���� ������ ����� ����ó��
						System.out.println("�Է��� �ֽʽÿ�."); // "�� �̸��� �Է��� �ֽʽÿ�." �� ����Ѵ�.
						System.out.println();
					}
					catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� ��
						String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
						if (msg.equals("Not Exist")) // �߻��� exception�� "Not Exist"�� ���
						{
							System.out.println("üũ�� �� ���� �����ϴ�.\n�� �̸��� �ٽ� Ȯ���Ͻʽÿ�."); // "üũ�� �� ���� �����ϴ�.\n�� �̸��� �ٽ� Ȯ���Ͻʽÿ�."�� ����Ѵ�.
						}
						else if (msg.equals("Ioobe")) // �߻��� exception�� "Ioobe"�� ���
						{
							System.out.println("�� ����Ʈ�� ������ �Ѿ���ϴ�."); // "�� ����Ʈ�� ������ �Ѿ���ϴ�."�� ����Ѵ�.
						}
						System.out.println();
					}
				}
				else if (menuNumber == 2) // menuNumber(�Է�)�� 2�� ��
				{
					System.out.println(); 
					System.out.print("���̸��� �Է����ֽʽÿ�. "); // "��ȭ��ȣ�� �Է����ֽʽÿ�. " �� ����Ѵ�.
					String userNum; // ����ڰ� �Է��� ������� ��ȭ��ȣ�� �޾Ƴ� ����
					try { 
						scan.nextLine(); // scan.nextLine()�� �߰��Ͽ� nextInt() �޼����� ����� �Է��� ������ ���๮�ڸ� �����Ѵ�.
						userNum = scan.nextLine(); // ���� �̸��� �Է¹޾� roomName�� �����Ѵ�.
						Room usedRoom = cms.checkOut(userNum); // roomName�� �Ű������� �Ͽ� cms�� checkOut�Լ��� ȣ���ϰ�, checkOut�Լ��� ��ȯ�� Room ��ü�� usedRoom�� �޴´�.
						int cost = cms.pay(usedRoom.getRoomName()); // usedRoom.getRoomName()�� �Ű������� �޾� cms�� pay�Լ��� ȣ���ϰ�, pay�Լ��� ��ȯ�� ����� cost�� �ִ´�.
						cms.setMonthlyStatistics(usedRoom.getOutTime().getMonth() + 1, cost); // usedRoom�� ��� ��¥�� ��(usedRoom.getOutTime().getMonth() + 1)�� cost�� �Ű������� �޾� cms�� setMonthlyStatistics�� ȣ���Ͽ� �� ���Կ� �����Ѵ�.
						cms.setTotalIncome(usedRoom.getOutTime().getDate(), cost); // usedRoom�� ��� ��¥�� ��(usedRoom.getOutTime().getDate())�� cost�� �Ű������� �޾� cms�� setTotalIncome�� ȣ���Ͽ� �Ϸ� ���Կ� �����Ѵ�.
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� aa hh�� mm�� ss��"); // SimpleDateFormat ������ �Ķ���ͷ� ��¥�� �ð��� ������ �ѱ��, new�� ��ü�� �����Ѵ�.
						System.out.println(usedRoom.getRoomName() + " ��\n��� �ð� : " + dateFormat.format(usedRoom.getOutTime()) + "\n��� : " + cost + "��"); // check-out�� ���� �̸�, ��ǽð�, ����� ����Ѵ�.
						System.out.println();
						//Save(cms);
					}
					catch (java.util.NoSuchElementException nsee) { // nextLine()�� �Է��� ���� ������ ����� ����ó��
						System.out.println("��ȭ��ȣ�� �Է����ֽʽÿ�. "); // "��ȭ��ȣ�� �Է����ֽʽÿ�. " �� ����Ѵ�.
						System.out.println();
					}
					catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� ��
						String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
						if (msg.equals("Not Exist")) // �߻��� exception�� "Not Exist"�� ���
						{
							System.out.println("üũ�ƿ� �� ���� �����ϴ�.\n��ȭ��ȣ�� �ٽ� Ȯ���Ͻʽÿ�."); // "üũ�ƿ� �� ���� �����ϴ�.\n��ȭ��ȣ�� �ٽ� Ȯ���Ͻʽÿ�."�� ����Ѵ�.
						}
						else if (msg.equals("Ioobe")) // �߻��� exception�� "Ioobe"�� ���
						{
							System.out.println("�� ����Ʈ�� ������ �Ѿ���ϴ�."); // "�� ����Ʈ�� ������ �Ѿ���ϴ�."�� ����Ѵ�.
						}
						else
						{
							System.out.println(msg);
						}
						System.out.println();
					}
				}
				else if (menuNumber == 3) // menuNumber(�Է�)�� 3�� ��
				{
					mode = 0; // ��带 �������� �ٲ۴�.
				}
			}
			else if (mode == 2)
			{
				System.out.println("�޴�"); // "�޴�" �� ����Ѵ�.
				System.out.println();
				System.out.println("1. �� ��ȸ"); // "1. �� ��ȸ" �� ����Ѵ�.
				System.out.println("2. �� �߰�"); // "2. �� �߰�" �� ����Ѵ�.
				System.out.println("3. �� ����"); // "3. �� ����" �� ����Ѵ�.
				System.out.println("4. ���� ����"); // "4. ���� ����" �� ����Ѵ�.
				System.out.println("5. ���� ��ȸ"); // "5. ���� ��ȸ" �� ����Ѵ�.
				System.out.println("6. �ڷΰ���"); // "6. �ڷΰ���" �� ����Ѵ�.
				
				try { // ����ó���� ���� try-catch�� ���
					menuNumber = scan.nextInt(); // ��ȣ�� �Է¹޴´�.
					if((menuNumber != 1) && (menuNumber != 2) && (menuNumber != 3) && (menuNumber != 4) && (menuNumber != 5) && (menuNumber != 6)) { // 1, 2, 3, 4, 6 �̿��� ���ڸ� �Է¹޾��� ��
						throw new Exception("�߸� �����̽��ϴ�."); // "�߸� �����̽��ϴ�."��� Exception�� �߻���Ų��.
					}
				}
				catch (java.util.InputMismatchException e) { // nextInt()�� Integer�� �ƴ� �ٸ� ���� �Է¹޾��� ��
					scan.nextLine(); // ���ѷ����� �����ϱ� ���� scan.nextLine()�� �߰��Ͽ� nextInt() �޼����� ����� �Է��� ������ ���๮�ڸ� �����Ѵ�.
					System.out.println("�߸� �����̽��ϴ�."); // "�߸� �����̽��ϴ�."��� Exception�� �߻���Ų��.
					System.out.println();
				}
				catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� �� catch�� �´�.
					String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
					System.out.println(msg); // msg�� ����Ѵ�.
					System.out.println();
				}
				
				if(menuNumber == 1) { // menuNumber(�Է�)�� 1�� ��
					System.out.println("���� ������ ���� �����մϴ�."); // "�� ���� ������ ���� �����մϴ�." �� ����Ѵ�.
					int num = cms.getRoomArr().size();
					for (int i = 0; i < num; i++) { // 0���� emptyRoomArr.length��ŭ �ݺ� 
						System.out.println("�� �̸� : " + cms.getRoomArr().get(i).getRoomName() + ", �ִ� �̿��ο� : " + cms.getRoomArr().get(i).getHeadcount() + "��, �ð� �� ���� : " + cms.getRoomArr().get(i).getPrice() + "��"); // ���� ��ȣ, �ִ� �̿��ο�, ������ ����Ѵ�.
					}
					System.out.println();
				}
				else if(menuNumber == 2) { // menuNumber(�Է�)�� 2�� ��
					System.out.println(); 
					System.out.print("�� �ο� ���� �����Ͻðڽ��ϱ�?(8, 4, 2 �� �� 1) "); // "�� �ο� ���� �����Ͻðڽ��ϱ�?(8, 4, 2 �� �� 1) " �� ����Ѵ�.
					int headcount; // ����ڰ� �Է��� �� �ο��� �޾Ƴ� ����
					String roomName; // ����ڰ� �Է��� ���� �̸��� �޾Ƴ� ����
					try{ // ����ó���� ���� try-catch�� ���
						headcount = scan.nextInt(); // �� �ο��� �Է¹޾� headcount�� �����Ѵ�.
						scan.nextLine();
						System.out.print("�� �̸��� �Է��Ͻʽÿ�. "); // "�� �̸��� �Է��Ͻʽÿ�. "��� Exception�� �߻���Ų��.
						roomName = scan.nextLine(); // ���� �̸��� �Է¹޾� roomName�� �����Ѵ�.
						cms.addRoom(headcount, roomName); // headcount�� roomName�� �Ű������� �Ͽ� cms�� addRoom�Լ��� ȣ���Ѵ�.
						System.out.println("���� �� ����\n8�ο� �� " + cms.countBigRoom().size() + "��\n4�ο� �� " + cms.countMiddleRoom().size() + "��\n2�ο� �� " + cms.countSmallRoom().size() + "��"); // �� �ִ��ο����� �ٸ� ���� ������ ����Ѵ�.
						System.out.println();
						//Save(cms);
					}
					catch (java.util.InputMismatchException ime) { // nextInt()�� Integer�� �ƴ� �ٸ� ���� �Է¹޾��� ���� ����ó��
						scan.nextLine(); // scan.nextLine()�� �߰��Ͽ� nextInt() �޼����� ����� �Է��� ������ ���๮�ڸ� �����Ѵ�.
						System.out.println("���ڸ� �Է��� �ֽʽÿ�."); // "���ڸ� �Է��� �ֽʽÿ�." �� ����Ѵ�.
						System.out.println();
					}
					catch (java.util.NoSuchElementException nsee) { // nextLine()�� �Է��� ���� ������ ����� ����ó��
						System.out.println("���� �̸��� �Է��� �ֽʽÿ�."); // "���� �̸��� �Է��� �ֽʽÿ�." �� ����Ѵ�.
						System.out.println();
					}
					catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� ��
						String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
						if (msg.equals("Retry")) // �߻��� exception�� "Retry"�� ���
						{
							System.out.println("�ο� ���� �ٽ� �Է��� �ֽʽÿ�."); // "�ο� ���� �ٽ� �Է��� �ֽʽÿ�."�� ����Ѵ�.
						}
						System.out.println();
					}
				}
				else if(menuNumber == 3) { // menuNumber(�Է�)�� 3�� ��
					System.out.println(); 
					System.out.print("� ���� �����Ͻðڽ��ϱ�?(���� �̸��� �Է��Ͻÿ�.) "); // "� ���� �����Ͻðڽ��ϱ�?(���� �̸��� �Է��Ͻÿ�.) " �� ����Ѵ�.
					String roomName; // ����ڰ� �Է��� ���� �̸��� �޾Ƴ� ����
					try{ // ����ó���� ���� try-catch�� ���
						scan.nextLine();
						roomName = scan.nextLine(); // ���� �̸��� �Է¹޾� roomName�� �����Ѵ�.
						cms.removeRoom(roomName); // roomNum�� �Ű������� �Ͽ� cms�� removeRoom�Լ��� ȣ���Ѵ�.
						
						System.out.println("���� �� ����\n8�ο� �� " + cms.countBigRoom().size() + "��\n4�ο� �� " + cms.countMiddleRoom().size() + "��\n2�ο� �� " + cms.countSmallRoom().size() + "��"); // �� �ִ��ο����� �ٸ� ���� ������ ����Ѵ�.
						System.out.println();
						//Save(cms);
					}
					catch (java.util.NoSuchElementException nsee) { // nextLine()�� �Է��� ���� ������ ����� ����ó��
						System.out.println("�� �̸��� �Է��� �ֽʽÿ�."); // "�� �̸��� �Է��� �ֽʽÿ�." �� ����Ѵ�.
						System.out.println();
					}
					catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� ��
						String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
						if (msg.equals("Not Exist")) // �߻��� exception�� "Not Exist"�� ���
						{
							System.out.println("������ ���� �����ϴ�."); // "������ ���� �����ϴ�."�� ����Ѵ�.
						}
						System.out.println();
					}
				}
				else if(menuNumber == 4) { // menuNumber(�Է�)�� 4�� ��
					System.out.println(); 
					System.out.print("�� �ο� ���� ������ �����Ͻðڽ��ϱ�?(8, 4, 2 �� �� 1) "); // "�� �ο� ���� ������ �����Ͻðڽ��ϱ�?(8, 4, 2 �� �� 1) " �� ����Ѵ�.
					int headcount; // ����ڰ� �Է��� �� �ο��� �޾Ƴ� ����
					int newPrice; // ����ڰ� �Է��� ���� ������ �̸��� �޾Ƴ� ����
					try{ // ����ó���� ���� try-catch�� ���
						headcount = scan.nextInt(); // �� �ο��� �Է¹޾� headcount�� �����Ѵ�.
						System.out.print("���� ������ �Է��Ͻʽÿ�. "); // "���� ������ �Է��Ͻʽÿ�. "�� ����Ѵ�.
						newPrice = scan.nextInt(); // ���� �̸��� �Է¹޾� roomName�� �����Ѵ�.
						cms.setPrice(headcount, newPrice); // headcount�� newPrice�� �Ű������� �Ͽ� cms�� setPrice�Լ��� ȣ���Ѵ�.
						System.out.println("���� �� ����\n8�ο� �� " + cms.getBigPrice() + "��\n4�ο� �� " + cms.getMiddlePrice() + "��\n2�ο� �� " + cms.getSmallPrice() + "��"); // �� ���� ���� ����� ����Ѵ�.
						System.out.println();
						//Save(cms);
					}
					catch (java.util.InputMismatchException ime) { // nextInt()�� Integer�� �ƴ� �ٸ� ���� �Է¹޾��� ���� ����ó��
						scan.nextLine(); // scan.nextLine()�� �߰��Ͽ� nextInt() �޼����� ����� �Է��� ������ ���๮�ڸ� �����Ѵ�.
						System.out.println("���ڸ� �Է��� �ֽʽÿ�."); // "���ڸ� �Է��� �ֽʽÿ�." �� ����Ѵ�.
						System.out.println();
					}
					catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� ��
						String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
						if (msg.equals("Retry")) // �߻��� exception�� "Retry"�� ���
						{
							System.out.println("�ο� ���� �ٽ� �Է��� �ֽʽÿ�."); // "�ο� ���� �ٽ� �Է��� �ֽʽÿ�."�� ����Ѵ�.
						}
						System.out.println();
					}
				}
				else if(menuNumber == 5) { // menuNumber(�Է�)�� 5�� ��
					System.out.println(); 
					System.out.print("���� ��ȸ"); // "���� ��ȸ" �� ����Ѵ�.
					for (int i = 1; i <= 31; i++) {
						System.out.println(i + "�� : " + cms.getTotalIncome(i));
					}
					System.out.println();
					for (int i = 1; i <= 12; i++) {
						System.out.println(i + "�� : " + cms.getMonthlyStatistics(i));
					}
					System.out.println();
				}
				else if(menuNumber == 6) { // menuNumber(�Է�)�� 6�� ��
					mode = 0; // ��带 �������� �ٲ۴�.
				}
			}
		}
	}
	
	// ���� �ڵ带 �� ���ǹ����� �ۼ��ϱ⿡�� ����ó������ ������ �ڵ尡 �ʹ� ����� ���� �Լ��� ����� ȣ���ϵ��� �Ͽ����ϴ�.
	static void Save(Management cms)
	{
		DataOutputStream out = null; // DataOutputStream ��ü�� ���� ���� out�� �����Ѵ�.
		ObjectOutputStream objOut = null; // ObjectOutputStream ��ü�� ���� ���� objOut�� �����Ѵ�.
		
		try {
			out = new DataOutputStream(new FileOutputStream("StudyCafeData.dat")); // "StudyCafeData.dat" ������ ����. FileOutputStream ��ü�� �����ؼ� DataOutputStream �������� �Ķ���ͷ� ����Ѵ�.
			objOut = new ObjectOutputStream(new FileOutputStream("StudyCafeObjectSerialization.dat")); // "StudyCafeObjectSerialization.dat" ������ ����. ObjectOutputStream ��ü�� �����Ѵ�.
			cms.Dataoutput(out, objOut); // cms�� Dataoutput �Լ��� ȣ���Ͽ� �����͸� ����.
		}
		catch (IOException ioe) // ������ �߻��� ��� ����ó��
		{
			System.out.println("�����͸� ������ �� �����ϴ�."); // "�����͸� ������ �� �����ϴ�."�� ����Ѵ�.
		}
		catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� �� catch�� �´�.
			String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
			if (msg.equals("Can't Write")) // �߻��� exception�� "Can't Write"�� ���
			{
				System.out.println("�����͸� ������ �� �����ϴ�."); // "�����͸� ������ �� �����ϴ�."�� ����Ѵ�.
			}
			System.out.println();
		}
		finally { // �ݵ�� �����ؾ� �ϴ� finally
			try {
				objOut.close(); // ������ �ݴ´�.
				out.close(); // ������ �ݴ´�.
			}
			catch (Exception e) { // ������ ���� �� �߻��ϴ� ���ܸ� ó��
			}
		}
	}
}