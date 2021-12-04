import java.util.ArrayList;
import java.util.Scanner; // Scanner을 사용하기 위해 import한다.
import java.text.SimpleDateFormat;
import java.io.*; // 파일을 읽고 쓰기 위해 필요
public class UI { // 인터페이스 클래스
	public static void main(String args[]) throws Exception{
		
		DataInputStream in = null; // DataInputStream 객체를 담을 변수 in을 생성한다
		ObjectInputStream objIn = null; // ObjectInputStream 객체를 담을 변수 objIn을 생성한다.
		
		
		Management cms = null; // Management 객체를 담을 변수 cms을 생성한다.
		
		try {
			in = new DataInputStream(new FileInputStream("StudyCafeData.dat")); // "StudyCafeData.dat" 파일을 연다. FileInputStream 객체를 생성해서 DataInputStream 생성자의 파라미터로 사용한다.
			objIn = new ObjectInputStream(new FileInputStream("StudyCafeObjectSerialization.dat")); // "StudyCafeObjectSerialization.dat" 파일을 연다. ObjectInputStream 객체를 생성한다.
			//cms = new Management(in, objIn); // Management 객체를 생성한다.
			cms = new Management();
		}
		catch (FileNotFoundException fnfe) { // 읽을 파일이 존재하지 않을 경우에 발생하는 예외를 처리
			System.out.println("파일을 찾을 수 없습니다."); // "파일을 찾을 수 없습니다." 를 출력한다.
		}
		catch (Exception e) { // try에서 인위적 exception이 발생하였을 때 catch로 온다.
			String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
			if (msg.equals("ERROR")) // 발생한 exception이 "ERROR"인 경우
			{
				System.out.println("오류가 발생하였습니다."); // "오류가 발생하였습니다." 를 출력한다.
			}
			else if (msg.equals("Can't Read")){
				System.out.println("데이터를 불러올 수 없습니다."); // "데이터를 불러올 수 없습니다." 를 출력한다.
			}
			System.out.println();
		}
		finally { // 반드시 실행해야 하는 finally
			try { // 예외처리를 위해 try-catch문 사용
				objIn.close(); // 파일을 닫는다.
				in.close(); // 파일을 닫는다.
			}
			catch (Exception e) { // 파일을 닫을 때 발생하는 예외를 처리
			}
		}
				
		int mode = 0; // 모드를 결정하는 변수(0 : 메인, 1 : 사용자, 2 : 매니저)
		
		Scanner scan = new Scanner(System.in); // 사용자로부터 입력을 받기위해 Scanner 객체를 만든다.
		int menuNumber = 0; // 사용자의 입력을 받을 변수
		
		while(true) { 
			if(mode == 0) // 메인 모드
			{
				System.out.println();
				System.out.println("모드"); // "모드" 를 출력한다.
				System.out.println();
				System.out.println("1. 사용자"); // "1. 사용자" 를 출력한다.
				System.out.println("2. 관리자"); // "2. 관리자" 를 출력한다.
				System.out.println("3. 종료"); // "3. 종료" 를 출력한다.
				
				try { // 예외처리를 위해 try-catch문 사용
					menuNumber = scan.nextInt(); // 번호를 입력받는다.
					if((menuNumber != 1) && (menuNumber != 2) && (menuNumber != 3)) { // 1, 2, 3 이외의 숫자를 입력받았을 때
						throw new Exception("잘못 누르셨습니다."); // "잘못 누르셨습니다."라는 Exception을 발생시킨다.
					}
				}
				catch (java.util.InputMismatchException e) { // nextInt()에 Integer가 아닌 다른 것을 입력받았을 때
					scan.nextLine(); // 무한루프를 방지하기 위해 scan.nextLine()를 추가하여 nextInt() 메서드의 사용자 입력의 남겨진 개행문자를 제거한다.
					System.out.println("잘못 누르셨습니다."); // "잘못 누르셨습니다."라는 Exception을 발생시킨다.
					System.out.println();
				}
				catch (Exception e) { // try에서 인위적 exception이 발생하였을 때 catch로 온다.
					String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
					System.out.println(msg); // msg를 출력한다.
					System.out.println();
				}
				
				if(menuNumber == 1) // menuNumber(입력)가 1일 때
				{
					mode = 1; // 모드를 사용자로 바꾼다.
				}
				else if (menuNumber == 2) // menuNumber(입력)가 2일 때
				{
					mode = 2; // 모드를 관리자로 바꾼다.
				}
				else if (menuNumber == 3) // menuNumber(입력)가 3일 때
				{
					Save(cms);
					break; // while문을 빠져나가 끝낸다.
				}
			}
			else if (mode == 1) // 사용자 모드
			{
				System.out.println();
				System.out.println("메뉴"); // "메뉴" 를 출력한다.
				System.out.println();
				System.out.println("1. check-in"); // "1. check-in" 를 출력한다.
				System.out.println("2. check-out"); // "2. check-out" 를 출력한다.
				System.out.println("3. 뒤로가기"); // "3. 뒤로가기" 를 출력한다.
				
				try { // 예외처리를 위해 try-catch문 사용
					menuNumber = scan.nextInt(); // 번호를 입력받는다.
					if((menuNumber != 1) && (menuNumber != 2) && (menuNumber != 3)) { // 1, 2, 3 이외의 숫자를 입력받았을 때
						throw new Exception("잘못 누르셨습니다."); // "잘못 누르셨습니다."라는 Exception을 발생시킨다.
					}
				}
				catch (java.util.InputMismatchException e) { // nextInt()에 Integer가 아닌 다른 것을 입력받았을 때
					scan.nextLine(); // 무한루프를 방지하기 위해 scan.nextLine()를 추가하여 nextInt() 메서드의 사용자 입력의 남겨진 개행문자를 제거한다.
					System.out.println("잘못 누르셨습니다."); // "잘못 누르셨습니다."라는 Exception을 발생시킨다.
					System.out.println();
				}
				catch (Exception e) { // try에서 인위적 exception이 발생하였을 때 catch로 온다.
					String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
					System.out.println(msg); // msg를 출력한다.
					System.out.println();
				}
				
				if(menuNumber == 1) // menuNumber(입력)가 1일 때
				{
					System.out.println(); 
					System.out.print("사용할 방의 이름을 입력하십시오. "); // "사용할 방의 이름을 입력해주십시오. " 를 출력한다.
					String roomName; // 사용자가 입력할 방의 이름을 받아낼 변수
					String userName ; // 사용자가 입력할 사용자의 이름을 받아낼 변수
					String userNum; // 사용자가 입력할 사용자의 전화번호를 받아낼 변수
					try {
						scan.nextLine(); // scan.nextLine()를 추가하여 nextInt() 메서드의 사용자 입력의 남겨진 개행문자를 제거한다.
						roomName = scan.nextLine(); // 방의 이름을 입력받아 roomName에 대입한다.
						System.out.print("사용자의 이름을 입력하십시오. "); // "사용자의 이름을 입력하십시오. " 를 출력한다.
						userName = scan.nextLine(); // 사용자의 번호를 입력받아 userNum에 대입한다.
						System.out.print("사용자의 번호를 입력하십시오. "); // "사용자의 번호를 입력해주십시오. " 를 출력한다.
						userNum = scan.nextLine(); // 사용자의 번호를 입력받아 userNum에 대입한다.
						User user = new User(userName, userNum); // new를 사용하여 사용자 객체를 생성하고 생성한 객체의 주소값을 user에 대입한다.
						Room useRoom = cms.checkIn(roomName, user); // roomName과 user을 매개변수로 하여 cms의 checkIn함수를 호출하고, checkIn함수가 반환한 Room 객체를 useRoom가 받는다.
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 aa hh시 mm분 ss초"); // SimpleDateFormat 생성자 파라미터로 날짜와 시간의 포맷을 넘기고, new로 객체를 생성한다.
						System.out.println("방 이름 : " + useRoom.getRoomName() + "\n최대 이용인원 : " + useRoom.getHeadcount() + "\n이용자 : " + useRoom.getWho().getName() + "\n시간당 가격 : " + useRoom.getPrice() + "\n입실 시간 : " + dateFormat.format(useRoom.getInTime())); // check-in한 방의 이름, 최대 이용 인원, 이용자, 시간당 가격, 입실 시간을 출력한다.
						System.out.println();
						//Save(cms);
					}
					catch (java.util.NoSuchElementException nsee) { // nextLine()가 입력을 받지 못했을 경우의 예외처리
						System.out.println("입력해 주십시오."); // "방 이름을 입력해 주십시오." 를 출력한다.
						System.out.println();
					}
					catch (Exception e) { // try에서 인위적 exception이 발생하였을 때
						String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
						if (msg.equals("Not Exist")) // 발생한 exception이 "Not Exist"인 경우
						{
							System.out.println("체크인 할 방이 없습니다.\n방 이름을 다시 확인하십시오."); // "체크인 할 방이 없습니다.\n방 이름을 다시 확인하십시오."을 출력한다.
						}
						else if (msg.equals("Ioobe")) // 발생한 exception이 "Ioobe"인 경우
						{
							System.out.println("방 리스트의 범위를 넘어갔습니다."); // "방 리스트의 범위를 넘어갔습니다."을 출력한다.
						}
						System.out.println();
					}
				}
				else if (menuNumber == 2) // menuNumber(입력)가 2일 때
				{
					System.out.println(); 
					System.out.print("방이름을 입력해주십시오. "); // "전화번호를 입력해주십시오. " 를 출력한다.
					String userNum; // 사용자가 입력할 사용자의 전화번호를 받아낼 변수
					try { 
						scan.nextLine(); // scan.nextLine()를 추가하여 nextInt() 메서드의 사용자 입력의 남겨진 개행문자를 제거한다.
						userNum = scan.nextLine(); // 방의 이름을 입력받아 roomName에 대입한다.
						Room usedRoom = cms.checkOut(userNum); // roomName을 매개변수로 하여 cms의 checkOut함수를 호출하고, checkOut함수가 반환한 Room 객체를 usedRoom가 받는다.
						int cost = cms.pay(usedRoom.getRoomName()); // usedRoom.getRoomName()을 매개변수로 받아 cms의 pay함수를 호출하고, pay함수가 반환한 비용을 cost에 넣는다.
						cms.setMonthlyStatistics(usedRoom.getOutTime().getMonth() + 1, cost); // usedRoom의 퇴실 날짜의 달(usedRoom.getOutTime().getMonth() + 1)과 cost를 매개변수로 받아 cms의 setMonthlyStatistics을 호출하여 월 수입에 저장한다.
						cms.setTotalIncome(usedRoom.getOutTime().getDate(), cost); // usedRoom의 퇴실 날짜의 일(usedRoom.getOutTime().getDate())과 cost를 매개변수로 받아 cms의 setTotalIncome를 호출하여 하루 수입에 저장한다.
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 aa hh시 mm분 ss초"); // SimpleDateFormat 생성자 파라미터로 날짜와 시간의 포맷을 넘기고, new로 객체를 생성한다.
						System.out.println(usedRoom.getRoomName() + " 방\n퇴실 시간 : " + dateFormat.format(usedRoom.getOutTime()) + "\n비용 : " + cost + "원"); // check-out한 방의 이름, 퇴실시간, 비용을 출력한다.
						System.out.println();
						//Save(cms);
					}
					catch (java.util.NoSuchElementException nsee) { // nextLine()가 입력을 받지 못했을 경우의 예외처리
						System.out.println("전화번호를 입력해주십시오. "); // "전화번호를 입력해주십시오. " 를 출력한다.
						System.out.println();
					}
					catch (Exception e) { // try에서 인위적 exception이 발생하였을 때
						String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
						if (msg.equals("Not Exist")) // 발생한 exception이 "Not Exist"인 경우
						{
							System.out.println("체크아웃 할 방이 없습니다.\n전화번호를 다시 확인하십시오."); // "체크아웃 할 방이 없습니다.\n전화번호를 다시 확인하십시오."을 출력한다.
						}
						else if (msg.equals("Ioobe")) // 발생한 exception이 "Ioobe"인 경우
						{
							System.out.println("방 리스트의 범위를 넘어갔습니다."); // "방 리스트의 범위를 넘어갔습니다."을 출력한다.
						}
						else
						{
							System.out.println(msg);
						}
						System.out.println();
					}
				}
				else if (menuNumber == 3) // menuNumber(입력)가 3일 때
				{
					mode = 0; // 모드를 메인으로 바꾼다.
				}
			}
			else if (mode == 2)
			{
				System.out.println("메뉴"); // "메뉴" 를 출력한다.
				System.out.println();
				System.out.println("1. 방 조회"); // "1. 방 조회" 를 출력한다.
				System.out.println("2. 방 추가"); // "2. 방 추가" 를 출력한다.
				System.out.println("3. 방 삭제"); // "3. 방 삭제" 를 출력한다.
				System.out.println("4. 가격 변경"); // "4. 가격 변경" 을 출력한다.
				System.out.println("5. 수익 조회"); // "5. 수익 조회" 을 출력한다.
				System.out.println("6. 뒤로가기"); // "6. 뒤로가기" 를 출력한다.
				
				try { // 예외처리를 위해 try-catch문 사용
					menuNumber = scan.nextInt(); // 번호를 입력받는다.
					if((menuNumber != 1) && (menuNumber != 2) && (menuNumber != 3) && (menuNumber != 4) && (menuNumber != 5) && (menuNumber != 6)) { // 1, 2, 3, 4, 6 이외의 숫자를 입력받았을 때
						throw new Exception("잘못 누르셨습니다."); // "잘못 누르셨습니다."라는 Exception을 발생시킨다.
					}
				}
				catch (java.util.InputMismatchException e) { // nextInt()에 Integer가 아닌 다른 것을 입력받았을 때
					scan.nextLine(); // 무한루프를 방지하기 위해 scan.nextLine()를 추가하여 nextInt() 메서드의 사용자 입력의 남겨진 개행문자를 제거한다.
					System.out.println("잘못 누르셨습니다."); // "잘못 누르셨습니다."라는 Exception을 발생시킨다.
					System.out.println();
				}
				catch (Exception e) { // try에서 인위적 exception이 발생하였을 때 catch로 온다.
					String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
					System.out.println(msg); // msg를 출력한다.
					System.out.println();
				}
				
				if(menuNumber == 1) { // menuNumber(입력)가 1일 때
					System.out.println("방이 다음과 같이 존재합니다."); // "빈 방이 다음과 같이 존재합니다." 를 출력한다.
					int num = cms.getRoomArr().size();
					for (int i = 0; i < num; i++) { // 0부터 emptyRoomArr.length만큼 반복 
						System.out.println("방 이름 : " + cms.getRoomArr().get(i).getRoomName() + ", 최대 이용인원 : " + cms.getRoomArr().get(i).getHeadcount() + "명, 시간 당 가격 : " + cms.getRoomArr().get(i).getPrice() + "원"); // 방의 번호, 최대 이용인원, 가격을 출력한다.
					}
					System.out.println();
				}
				else if(menuNumber == 2) { // menuNumber(입력)가 2일 때
					System.out.println(); 
					System.out.print("몇 인용 방을 생성하시겠습니까?(8, 4, 2 중 택 1) "); // "몇 인용 방을 생성하시겠습니까?(8, 4, 2 중 택 1) " 를 출력한다.
					int headcount; // 사용자가 입력할 몇 인용을 받아낼 변수
					String roomName; // 사용자가 입력할 방의 이름을 받아낼 변수
					try{ // 예외처리를 위해 try-catch문 사용
						headcount = scan.nextInt(); // 몇 인용을 입력받아 headcount에 대입한다.
						scan.nextLine();
						System.out.print("방 이름을 입력하십시오. "); // "방 이름을 입력하십시오. "라는 Exception을 발생시킨다.
						roomName = scan.nextLine(); // 방의 이름을 입력받아 roomName에 대입한다.
						cms.addRoom(headcount, roomName); // headcount와 roomName을 매개변수로 하여 cms의 addRoom함수를 호출한다.
						System.out.println("현재 방 개수\n8인용 방 " + cms.countBigRoom().size() + "개\n4인용 방 " + cms.countMiddleRoom().size() + "개\n2인용 방 " + cms.countSmallRoom().size() + "개"); // 각 최대인원수에 다른 방의 개수를 출력한다.
						System.out.println();
						//Save(cms);
					}
					catch (java.util.InputMismatchException ime) { // nextInt()에 Integer가 아닌 다른 것을 입력받았을 때의 예외처리
						scan.nextLine(); // scan.nextLine()를 추가하여 nextInt() 메서드의 사용자 입력의 남겨진 개행문자를 제거한다.
						System.out.println("숫자를 입력해 주십시오."); // "숫자를 입력해 주십시오." 를 출력한다.
						System.out.println();
					}
					catch (java.util.NoSuchElementException nsee) { // nextLine()가 입력을 받지 못했을 경우의 예외처리
						System.out.println("방의 이름을 입력해 주십시오."); // "방의 이름을 입력해 주십시오." 를 출력한다.
						System.out.println();
					}
					catch (Exception e) { // try에서 인위적 exception이 발생하였을 때
						String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
						if (msg.equals("Retry")) // 발생한 exception이 "Retry"인 경우
						{
							System.out.println("인원 수를 다시 입력해 주십시오."); // "인원 수를 다시 입력해 주십시오."을 출력한다.
						}
						System.out.println();
					}
				}
				else if(menuNumber == 3) { // menuNumber(입력)가 3일 때
					System.out.println(); 
					System.out.print("어떤 방을 제거하시겠습니까?(방의 이름을 입력하시오.) "); // "어떤 방을 제거하시겠습니까?(방의 이름을 입력하시오.) " 를 출력한다.
					String roomName; // 사용자가 입력할 방의 이름을 받아낼 변수
					try{ // 예외처리를 위해 try-catch문 사용
						scan.nextLine();
						roomName = scan.nextLine(); // 방의 이름을 입력받아 roomName에 대입한다.
						cms.removeRoom(roomName); // roomNum를 매개변수로 하여 cms의 removeRoom함수를 호출한다.
						
						System.out.println("현재 방 개수\n8인용 방 " + cms.countBigRoom().size() + "개\n4인용 방 " + cms.countMiddleRoom().size() + "개\n2인용 방 " + cms.countSmallRoom().size() + "개"); // 각 최대인원수에 다른 방의 개수를 출력한다.
						System.out.println();
						//Save(cms);
					}
					catch (java.util.NoSuchElementException nsee) { // nextLine()가 입력을 받지 못했을 경우의 예외처리
						System.out.println("방 이름을 입력해 주십시오."); // "방 이름을 입력해 주십시오." 를 출력한다.
						System.out.println();
					}
					catch (Exception e) { // try에서 인위적 exception이 발생하였을 때
						String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
						if (msg.equals("Not Exist")) // 발생한 exception이 "Not Exist"인 경우
						{
							System.out.println("제거할 방이 없습니다."); // "제거할 방이 없습니다."을 출력한다.
						}
						System.out.println();
					}
				}
				else if(menuNumber == 4) { // menuNumber(입력)가 4일 때
					System.out.println(); 
					System.out.print("몇 인용 방의 가격을 변경하시겠습니까?(8, 4, 2 중 택 1) "); // "몇 인용 방의 가격을 변경하시겠습니까?(8, 4, 2 중 택 1) " 를 출력한다.
					int headcount; // 사용자가 입력할 몇 인용을 받아낼 변수
					int newPrice; // 사용자가 입력할 수정 가격의 이름을 받아낼 변수
					try{ // 예외처리를 위해 try-catch문 사용
						headcount = scan.nextInt(); // 몇 인용을 입력받아 headcount에 대입한다.
						System.out.print("수정 가격을 입력하십시오. "); // "수정 가격을 입력하십시오. "를 출력한다.
						newPrice = scan.nextInt(); // 방의 이름을 입력받아 roomName에 대입한다.
						cms.setPrice(headcount, newPrice); // headcount와 newPrice을 매개변수로 하여 cms의 setPrice함수를 호출한다.
						System.out.println("현재 방 가격\n8인용 방 " + cms.getBigPrice() + "개\n4인용 방 " + cms.getMiddlePrice() + "개\n2인용 방 " + cms.getSmallPrice() + "개"); // 각 방의 가격 출력을 출력한다.
						System.out.println();
						//Save(cms);
					}
					catch (java.util.InputMismatchException ime) { // nextInt()에 Integer가 아닌 다른 것을 입력받았을 때의 예외처리
						scan.nextLine(); // scan.nextLine()를 추가하여 nextInt() 메서드의 사용자 입력의 남겨진 개행문자를 제거한다.
						System.out.println("숫자를 입력해 주십시오."); // "숫자를 입력해 주십시오." 를 출력한다.
						System.out.println();
					}
					catch (Exception e) { // try에서 인위적 exception이 발생하였을 때
						String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
						if (msg.equals("Retry")) // 발생한 exception이 "Retry"인 경우
						{
							System.out.println("인원 수를 다시 입력해 주십시오."); // "인원 수를 다시 입력해 주십시오."을 출력한다.
						}
						System.out.println();
					}
				}
				else if(menuNumber == 5) { // menuNumber(입력)가 5일 때
					System.out.println(); 
					System.out.print("수익 조회"); // "수익 조회" 를 출력한다.
					for (int i = 1; i <= 31; i++) {
						System.out.println(i + "일 : " + cms.getTotalIncome(i));
					}
					System.out.println();
					for (int i = 1; i <= 12; i++) {
						System.out.println(i + "월 : " + cms.getMonthlyStatistics(i));
					}
					System.out.println();
				}
				else if(menuNumber == 6) { // menuNumber(입력)가 6일 때
					mode = 0; // 모드를 메인으로 바꾼다.
				}
			}
		}
	}
	
	// 저장 코드를 매 조건문마다 작성하기에는 예외처리까지 포함한 코드가 너무 길어져 따로 함수를 만들어 호출하도록 하였습니다.
	static void Save(Management cms)
	{
		DataOutputStream out = null; // DataOutputStream 객체를 담을 변수 out을 생성한다.
		ObjectOutputStream objOut = null; // ObjectOutputStream 객체를 담을 변수 objOut을 생성한다.
		
		try {
			out = new DataOutputStream(new FileOutputStream("StudyCafeData.dat")); // "StudyCafeData.dat" 파일을 연다. FileOutputStream 객체를 생성해서 DataOutputStream 생성자의 파라미터로 사용한다.
			objOut = new ObjectOutputStream(new FileOutputStream("StudyCafeObjectSerialization.dat")); // "StudyCafeObjectSerialization.dat" 파일을 연다. ObjectOutputStream 객체를 생성한다.
			cms.Dataoutput(out, objOut); // cms의 Dataoutput 함수를 호출하여 데이터를 쓴다.
		}
		catch (IOException ioe) // 오류가 발생할 경우 예외처리
		{
			System.out.println("데이터를 저장할 수 없습니다."); // "데이터를 저장할 수 없습니다."을 출력한다.
		}
		catch (Exception e) { // try에서 인위적 exception이 발생하였을 때 catch로 온다.
			String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
			if (msg.equals("Can't Write")) // 발생한 exception이 "Can't Write"인 경우
			{
				System.out.println("데이터를 저장할 수 없습니다."); // "데이터를 저장할 수 없습니다."을 출력한다.
			}
			System.out.println();
		}
		finally { // 반드시 실행해야 하는 finally
			try {
				objOut.close(); // 파일을 닫는다.
				out.close(); // 파일을 닫는다.
			}
			catch (Exception e) { // 파일을 닫을 때 발생하는 예외를 처리
			}
		}
	}
}