import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

public class GUI_UserMode extends JFrame{
	Management cms;
	
	// 메뉴
	private JButton checkInBtn; // 체크 인 버튼
	private JButton checkOutBtn; // 체크 아웃 버튼
	private JButton bakckBtn; // 뒤로가기 버튼
	
	private JPanel centerP; // 바뀌는 안쪽의 패널
	
	private CardLayout card = new CardLayout(); // 카드 레이터
	
	// 체크 인
	private JButton cIokayBtn; // 확인 버튼
	private JButton checkInOkayBtn; // 체크 인 버튼
	
	private JPanel checkInP; // 체크 인 패널
	private JPanel cIInP; // 체크 인 입력 패널
	private JPanel cIoutP; // 체크 인 출력 패널
	private JLabel choiceR; // 선택한 방 출력 패널
	private JPanel userP; //  이름, 전화번호 텍스크 필드, 체크 인 버튼 패널
	
	private JTextField cImemberField; // 인원 수 텍스트 필드
	private JTextField cInameTextField; // 이름 텍스트 필드
	private JTextField cIphoneNumTextField; // 전화번호 텍스트 필드
	
	private DefaultTableModel roomsModel; // 방 테이블 모델
	private JTable roomTable; // 방 테이블
	
	private ArrayList<Room> emptyRoomList; // 빈 방 리스트
	
	private int row = -1;
	
	
	// 체크 아웃
	private JPanel checkOutP; // 체크 아웃 패널
	
	private JTextField cOphoneNumTextField; // 전화번로 텍스트 필드
	
	private JButton cOokayBtn; // 확인 버튼
	private JButton CheckOutokayBtn; // 체크 아웃 버튼
	
	private DefaultTableModel roomsInfoModel; // 방 정보 테이블 모델
	private JTable roomInfoTable; // 방 정보 테이블
	
	private Room cOuseRoom; // 체크 아웃 할 방 방
	
	private int cost; // 비용
	
	private boolean isFRoom = false;
	
	
	public GUI_UserMode(Management cms) {
		this.cms = cms;
		
		setTitle("StudyCafe_User");
		setPreferredSize(new Dimension(1535, 800)); // 프레임 크기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 여백
		JPanel bp1 = new JPanel();
		bp1.setPreferredSize(new Dimension(150, 0));
		JPanel bp3 = new JPanel();
		bp3.setPreferredSize(new Dimension(150, 0));
		JPanel bp4 = new JPanel();
		bp4.setPreferredSize(new Dimension(0, 100));
		
		getContentPane().add(bp1, BorderLayout.WEST);
		getContentPane().add(bp3, BorderLayout.EAST);
		getContentPane().add(bp4, BorderLayout.SOUTH);
		
		// 센터 패널
		centerP = new JPanel();
		centerP.setPreferredSize(new Dimension(1235, 600));
		centerP.setLayout(card);
		
		// 메뉴 버튼
		JPanel menuP = new JPanel();
		menuP.setPreferredSize(new Dimension(0, 100));
		getContentPane().add(menuP, BorderLayout.NORTH);
		menuP.setLayout(new GridLayout(0, 3, 0, 0));
		
		checkInBtn = new JButton("체크 인");
		checkInBtn.setFont(new Font("굴림", Font.BOLD, 55));
		checkInBtn.addActionListener(new userMyListener());
		menuP.add(checkInBtn);
		checkOutBtn = new JButton("체크 아웃");
		checkOutBtn.setFont(new Font("굴림", Font.BOLD, 55));
		checkOutBtn.addActionListener(new userMyListener());
		menuP.add(checkOutBtn);
		bakckBtn = new JButton("뒤로가기");
		bakckBtn.setFont(new Font("굴림", Font.BOLD, 55));
		bakckBtn.addActionListener(new userMyListener(this));
		menuP.add(bakckBtn);
		
		// 체크 인 패널
		CheckInPanel();
		CheckOutPanel();
		
		centerP.add("checkin", checkInP);
		centerP.add("checkout", checkOutP);
		card.show(centerP, "checkin");
		
		getContentPane().add(centerP, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}
	
	// 체크 인 패널
	void CheckInPanel()
	{
		checkInP = new JPanel();
		checkInP.setPreferredSize(new Dimension(1235, 600));
		checkInP.setLayout(new BorderLayout(0, 0));
		cIoutP = new JPanel();
		cIoutP.setPreferredSize(new Dimension(560, 563));
		cIInP = new JPanel();
		cIInP.setPreferredSize(new Dimension(560, 563));
		cIoutP.setLayout(null);
		cIInP.setLayout(null);
		
		// cIoutP 결과 출력 패널
		JLabel cIstr = new JLabel("체크 인 하시겠습니까?");
		cIstr.setBounds(0, 0, 560, 100);
		cIstr.setFont(new Font("굴림", Font.BOLD, 50));
		cIstr.setHorizontalAlignment(SwingConstants.LEFT);
		cIoutP.add(cIstr);
		
		// 방 table
		String colNames[] = {"방 이름", "정원(명)", "시간 당 가격(원)"};
		roomsModel = new DefaultTableModel(colNames, 0);
		roomTable = new JTable(roomsModel);
		JScrollPane scrollPane = new JScrollPane(roomTable);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setLocation(0, 100);
		scrollPane.setSize(560, 463);
		roomTable.addMouseListener(new userMyListener());
		cIoutP.add(scrollPane);
		
		
		// cIInP 입력 패널
		JPanel bp5 = new JPanel();
		bp5.setBounds(0, 0, 560, 100);
		bp5.setPreferredSize(new Dimension(560, 100));
		
		JPanel memberP = new JPanel();
		memberP.setLocation(0, 98);
		memberP.setSize(560, 100);
		
		// 인원 수 입력
		JLabel memberStr = new JLabel("인원 수");
		memberStr.setBounds(0, 10, 146, 65);
		memberStr.setFont(new Font("굴림", Font.BOLD, 35));
		cImemberField = new JTextField();
		cImemberField.setFont(new Font("굴림", Font.PLAIN, 30));
		cImemberField.setBounds(158, 11, 262, 64);
		cIokayBtn = new JButton("확인");
		cIokayBtn.setFont(new Font("굴림", Font.BOLD, 35));
		cIokayBtn.setBounds(432, 10, 128, 65);
		cIokayBtn.addActionListener(new userMyListener());
		memberP.setLayout(null);
		memberP.add(memberStr);
		memberP.add(cImemberField);
		memberP.add(cIokayBtn);
		cIInP.add(bp5);
		cIInP.add(memberP);
		
		// 선택한 방 라벨, 이름, 전화번호 텍스크 필드, 체크 인 버튼
		JPanel choiceRP = new JPanel();
		choiceRP.setLayout(null);
		choiceRP.setBounds(0, 197, 560, 100);
		cIInP.add(choiceRP);
		
		choiceR = new JLabel("");
		choiceR.setFont(new Font("굴림", Font.BOLD, 35));
		choiceR.setBounds(0, 10, 560, 65);
		choiceRP.add(choiceR);
		
		userP = new JPanel();
		userP.setLayout(null);
		userP.setBounds(0, 295, 560, 268);
		cIInP.add(userP);
		
		
		JLabel nameStr = new JLabel("이름");
		nameStr.setFont(new Font("굴림", Font.BOLD, 35));
		nameStr.setBounds(0, 9, 146, 65);
		userP.add(nameStr);
		
		cInameTextField = new JTextField();
		cInameTextField.setFont(new Font("굴림", Font.PLAIN, 30));
		cInameTextField.setBounds(158, 11, 402, 64);
		userP.add(cInameTextField);
		
		cIphoneNumTextField = new JTextField();
		cIphoneNumTextField.setFont(new Font("굴림", Font.PLAIN, 30));
		cIphoneNumTextField.setBounds(158, 107, 402, 64);
		userP.add(cIphoneNumTextField);
		
		JLabel phoneNumStr = new JLabel("전화번호");
		phoneNumStr.setFont(new Font("굴림", Font.BOLD, 35));
		phoneNumStr.setBounds(0, 105, 146, 65);
		userP.add(phoneNumStr);
		
		checkInOkayBtn = new JButton("체크 인");
		checkInOkayBtn.setFont(new Font("굴림", Font.BOLD, 35));
		checkInOkayBtn.setBounds(392, 203, 168, 65);
		checkInOkayBtn.addActionListener(new userMyListener());
		userP.add(checkInOkayBtn);
		
		checkInP.add(cIoutP, BorderLayout.CENTER);
		checkInP.add(cIInP, BorderLayout.EAST);
		
	}
	
	// 체크 아웃 패널
	void CheckOutPanel() {
		checkOutP = new JPanel();
		checkOutP.setPreferredSize(new Dimension(1235, 600));
		checkOutP.setLayout(null);
		
		// 체크 아웃 라벨
		JLabel cOstr = new JLabel("체크 아웃 하시겠습니까?");
		cOstr.setBounds(0, 0, 875, 100);
		cOstr.setHorizontalAlignment(SwingConstants.LEFT);
		cOstr.setFont(new Font("굴림", Font.BOLD, 50));
		checkOutP.add(cOstr);
		
		// 전화번호 입력, 방 정보 테이블 패널
		JPanel cOoutP = new JPanel();
		cOoutP.setBounds(0, 100, 1221, 463);
		checkOutP.add(cOoutP);
		cOoutP.setLayout(null);
		
		JLabel phoneNumStr = new JLabel("전화번호");
		phoneNumStr.setBounds(0, 10, 146, 65);
		phoneNumStr.setFont(new Font("굴림", Font.BOLD, 35));
		cOoutP.add(phoneNumStr);
		
		cOphoneNumTextField = new JTextField();
		cOphoneNumTextField.setBounds(158, 11, 923, 64);
		cOphoneNumTextField.setFont(new Font("굴림", Font.PLAIN, 30));
		cOoutP.add(cOphoneNumTextField);
		
		cOokayBtn = new JButton("확인");
		cOokayBtn.setBounds(1093, 10, 128, 65);
		cOokayBtn.setFont(new Font("굴림", Font.BOLD, 35));
		cOokayBtn.addActionListener(new userMyListener());
		cOoutP.add(cOokayBtn);
		
		// 방 정보 테이블
		String colNames[] = {"", "방 정보"};
		roomsInfoModel = new DefaultTableModel(colNames, 0);
		roomInfoTable = new JTable(roomsInfoModel);
		roomInfoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		roomInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane roomScrollPane = new JScrollPane(roomInfoTable);
		roomScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		roomScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		roomScrollPane.setLocation(0, 100);
		roomScrollPane.setSize(1221, 288);
		cOoutP.add(roomScrollPane);
		
		CheckOutokayBtn = new JButton("체크 아웃");
		CheckOutokayBtn.setFont(new Font("굴림", Font.BOLD, 35));
		CheckOutokayBtn.setBounds(1022, 398, 199, 65);
		CheckOutokayBtn.addActionListener(new userMyListener());
		cOoutP.add(CheckOutokayBtn);
		
	}
	
	
	
	// 이벤트 함수
	// 체크 인 패널에서 확인 버튼을 눌렀을 때
	void cIOkayBtnPress(){
		// 방 table에 빈 방 추가
		roomsModel.setNumRows(0);
		
		if (cImemberField.getText().equals("")) {
			JOptionPane.showMessageDialog(checkInP, "인원 수를 넣어주십시오.", "Not Number", JOptionPane.WARNING_MESSAGE);
		}
		else {
			try {
				int personCount =  Integer.parseInt(cImemberField.getText());
				
				if (personCount <= 0) {
					JOptionPane.showMessageDialog(checkInP, "올바른 인원 수를 넣어주십시오.", "Not Correct Number", JOptionPane.WARNING_MESSAGE);
				}
				else if (personCount > 0){
					emptyRoomList = cms.findEmpty(personCount);
					int size = emptyRoomList.size();
					for (int i = 0; i < size; i++) {
						Object data[] = new Object[3];
						data[0] = emptyRoomList.get(i).getRoomName();
						data[1] = emptyRoomList.get(i).getHeadcount();
						data[2] = emptyRoomList.get(i).getPrice();
						
						roomsModel.addRow(data);
					}
				}
			}
			catch (NumberFormatException NFE) {
				JOptionPane.showMessageDialog(checkInP, "숫자를 입력해주십시오.", "Not Correct Number", JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception e) { // try에서 인위적 exception이 발생하였을 때
				String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
				if (msg.equals("Not Exist")) // 발생한 exception이 "Not Exist"인 경우
				{
					JOptionPane.showMessageDialog(checkInP, "빈 방이 존재하지 않습니다.", "Not Exist", JOptionPane.WARNING_MESSAGE);
				}
			}
		}		
	}
	
	// 체크 인 패널에서 체크 인 버튼을 눌렀을 때
	void checkInOkayBtnPress() {
		String userName = cInameTextField.getText();
		String userPhoneNum = cIphoneNumTextField.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 aa hh시 mm분 ss초"); // SimpleDateFormat 생성자 파라미터로 날짜와 시간의 포맷을 넘기고, new로 객체를 생성한다.
		
		try {
			if (row == -1)
			{
				JOptionPane.showMessageDialog(checkInP, "방을 선택해주십시오.", "Not Choice", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else {
				if (userName.equals("") || userPhoneNum.equals(""))
				{
					JOptionPane.showMessageDialog(checkInP, "이름 또는 전화번호를 입력해주십시오.", "Not Exist", JOptionPane.WARNING_MESSAGE);
				}
				else {
					User user = new User(userName, userPhoneNum);
					Room useRoom = cms.checkIn(emptyRoomList.get(row).getRoomName(), user);
					
					JOptionPane.showMessageDialog(checkInP, "방이 체크 인 되었습니다.\n"
							+ useRoom.getRoomName() + "방 " + useRoom.getHeadcount() + "인용 시간 당 " + useRoom.getPrice() + "원\n"
							+ "입실시간 : " + dateFormat.format(useRoom.getInTime()), "StudyCafe_User_CheckIn", JOptionPane.PLAIN_MESSAGE);
					
					roomsModel.setNumRows(0);
					
					cInameTextField.setText("");
					cIphoneNumTextField.setText("");
					cImemberField.setText("");
					choiceR.setText("");
				}
			}
		}
		catch (Exception e) {
			String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
			if (msg.equals("Not Exist")) // 발생한 exception이 "Not Exist"인 경우
			{
				JOptionPane.showMessageDialog(checkInP, "체크인 할 방이 없습니다.\n방 이름을 다시 확인하십시오.", "Not Exist", JOptionPane.WARNING_MESSAGE);
			}
			else if (msg.equals("Ioobe")) // 발생한 exception이 "Ioobe"인 경우
			{
				JOptionPane.showMessageDialog(checkInP, "방 리스트의 범위를 넘어갔습니다.", "Ioobe", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	// 체크 아웃 패널에서 확인 버튼을 눌렀을 때
	void cOOkayBtnPress() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 aa hh시 mm분 ss초"); // SimpleDateFormat 생성자 파라미터로 날짜와 시간의 포맷을 넘기고, new로 객체를 생성한다.
		
		roomsInfoModel.setNumRows(0);
		
		if(cOphoneNumTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(checkOutP, "전화번호를 입력해주십시오.", "Not Exist", JOptionPane.WARNING_MESSAGE);
		}
		else {
			try {
				String userPhoneNum = cOphoneNumTextField.getText();
				int index = cms.searchUserNum(userPhoneNum);
				
				if (index == -1){
					JOptionPane.showMessageDialog(checkOutP, "체크아웃 할 방이 없습니다.\n전화번호를 다시 확인하십시오.", "Not Exist", JOptionPane.WARNING_MESSAGE);
				}
				else {
					ArrayList<Room> roomArray = cms.getRoomArr();
					cOuseRoom = roomArray.get(index);
					String userName = cOuseRoom.getWho().getName();
					String roomName = cOuseRoom.getRoomName();
					int roomCount = cOuseRoom.getHeadcount();
					int roomPrice = cOuseRoom.getPrice();
					String inTime = dateFormat.format(cOuseRoom.getInTime());
					cost = cms.pay(roomName);
					
					roomsInfoModel.addRow(new Object[] {"사용자", userName});
					roomsInfoModel.addRow(new Object[] {"전화번호", userPhoneNum});
					roomsInfoModel.addRow(new Object[] {"방 이름", roomName});
					roomsInfoModel.addRow(new Object[] {"정원(명)", roomCount});
					roomsInfoModel.addRow(new Object[] {"시간 당 가격(원)", roomPrice});
					roomsInfoModel.addRow(new Object[] {"입실시간", inTime});
					roomsInfoModel.addRow(new Object[] {"비용(원)", cost});
					
					isFRoom = true;
				}
				
			}
			catch (Exception e) {
				String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
				if (msg.equals("Not Exist")) // 발생한 exception이 "Not Exist"인 경우
				{
					JOptionPane.showMessageDialog(checkOutP, "체크아웃 할 방이 없습니다.\n전화번호를 다시 확인하십시오.", "Not Exist", JOptionPane.WARNING_MESSAGE);
				}
				else if (msg.equals("Ioobe")) // 발생한 exception이 "Ioobe"인 경우
				{
					JOptionPane.showMessageDialog(checkOutP, "방 리스트의 범위를 넘어갔습니다.", "Ioobe", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	// 체크 아웃 패널에서 체크 아웃 버튼을 눌렀을 때
	void checkOutOkayBtnPress(int cost) {
		roomsInfoModel.setNumRows(0);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 aa hh시 mm분 ss초"); // SimpleDateFormat 생성자 파라미터로 날짜와 시간의 포맷을 넘기고, new로 객체를 생성한다.
		Room chRoom;
		
		try {
			chRoom = cms.checkOut(cOuseRoom.getRoomName());
			cms.setMonthlyStatistics(chRoom.getOutTime().getMonth() + 1, cost); // usedRoom의 퇴실 날짜의 달(usedRoom.getOutTime().getMonth() + 1)과 cost를 매개변수로 받아 cms의 setMonthlyStatistics을 호출하여 월 수입에 저장한다.
			cms.setTotalIncome(chRoom.getOutTime().getDate(), cost); // usedRoom의 퇴실 날짜의 일(usedRoom.getOutTime().getDate())과 cost를 매개변수로 받아 cms의 setTotalIncome를 호출하여 하루 수입에 저장한다.
			JOptionPane.showMessageDialog(checkInP, chRoom.getRoomName() + "방이 체크 아웃 되었습니다.\n"
					+ "퇴실시간 : " + dateFormat.format(chRoom.getOutTime()), "StudyCafe_User_CheckOut", JOptionPane.PLAIN_MESSAGE);
			
			cOphoneNumTextField.setText("");
		}
		catch (Exception e) {
			String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
			if (msg.equals("Not Exist")) // 발생한 exception이 "Not Exist"인 경우
			{
				JOptionPane.showMessageDialog(checkOutP, "체크아웃 할 방이 없습니다." + "\n전화번호를 다시 확인하십시오.", "Not Exist", JOptionPane.WARNING_MESSAGE);
			}
			else if (msg.equals("Ioobe")) // 발생한 exception이 "Ioobe"인 경우
			{
				JOptionPane.showMessageDialog(checkOutP, "방 리스트의 범위를 넘어갔습니다.", "Ioobe", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	// 이벤트
	private class userMyListener implements ActionListener, MouseListener{
		JFrame f;
		public userMyListener(JFrame f) {
			this.f = f;
		}
		
		public userMyListener() {
			
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == checkInBtn) {
				card.show(centerP, "checkin");
				roomsModel.setNumRows(0);
				cInameTextField.setText("");
				cIphoneNumTextField.setText("");
				cImemberField.setText("");
				choiceR.setText("");
			}
			else if (e.getSource() == checkOutBtn) {
				card.show(centerP, "checkout");
				cOphoneNumTextField.setText("");
			}
			else if (e.getSource() == cIokayBtn) {
				cIOkayBtnPress();
			}
			else if (e.getSource() == checkInOkayBtn) {
				checkInOkayBtnPress();
				Save();
			}
			else if (e.getSource() == cOokayBtn) {
				cOOkayBtnPress();
			}
			else if (e.getSource() == CheckOutokayBtn) {
				if (isFRoom) {
					checkOutOkayBtnPress(cost);
					Save();
					isFRoom = false;
				}
				else {
					JOptionPane.showMessageDialog(null, "전화번호로 방을 검색해주십시오.", "Not Find", JOptionPane.WARNING_MESSAGE);
				}
			}
			else if (e.getSource() == bakckBtn) {
				f.setVisible(false);
			}
		}
		
		public void mouseClicked(MouseEvent e) {
			// 테이블 클릭 시 방 선택
			if (e.getSource() == roomTable) {
				row = roomTable.getSelectedRow();
				if(row == -1)
					return;
				choiceR.setText("선택 : " + emptyRoomList.get(row).getRoomName() + "방 " + emptyRoomList.get(row).getHeadcount() + "인용 시간 당 " + emptyRoomList.get(row).getPrice() + "원");
			}
		}
		
		public void mouseEntered(MouseEvent e) {
			
		}
		
		public void mouseExited(MouseEvent e) {
			
		}
		
		public void mousePressed(MouseEvent e) {
		}
		
		public void mouseReleased(MouseEvent e) {
			
		}
	}
	
	void Save()
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
			JOptionPane.showMessageDialog(null, "데이터를 저장할 수 없습니다.", "Can't Save", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) { // try에서 인위적 exception이 발생하였을 때 catch로 온다.
			String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
			if (msg.equals("Can't Write")) // 발생한 exception이 "Can't Write"인 경우
			{
				JOptionPane.showMessageDialog(null, "데이터를 저장할 수 없습니다.", "Can't Write", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}