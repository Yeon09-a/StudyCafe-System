import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;

public class GUI_ManagerMode extends JFrame{
	Management cms;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 aa hh시 mm분 ss초"); // SimpleDateFormat 생성자 파라미터로 날짜와 시간의 포맷을 넘기고, new로 객체를 생성한다.
	
	// 메뉴
	private JButton searchRoomBtn; // 방 조회 버튼
	private JButton addRoomBtn; // 방 추가 버튼
	private JButton delRoomBtn; // 방 삭제 버튼
	private JButton priceChBtn; // 가격 변경 버튼
	private JButton showIncomeBtn; // 수익 조회 버튼
	private JButton bakckBtn; // 뒤로가기 버튼
	
	private JPanel centerP; // 바뀌는 안쪽의 패널
	
	private CardLayout card = new CardLayout(); // 카드 레이터
	
	
	// 방 조회
	private JPanel searchRoomP; // 방 조회 패널
	
	private DefaultTableModel roomsModel; // 방 테이블 모델
	private JTable roomTable; // 방 테이블
	
	private ArrayList<Room> roomArray;
	
	
	// 방 추가
	private JPanel addRoomP; // 방 추가 패널
	private JPanel aroutP; // 방 추가 출력 패널
	private JPanel arInP; // 방 추가 입력 패널
	
	private DefaultTableModel adRoomsModel; // 방 테이블 모델
	private JTable adRoomTable; // 방 테이블
	
	private JTextField adroomNameText; // 방 이름 텍스트 필드
	private JTextField adroomCountText; // 방 인원 수 텍스트 필드
	
	private JButton adokayBtn; // 확인 버튼
	private JButton addRoomOkayBtn; // 추가하기 버튼
	
	private ArrayList<Room> adRoomArray;
	
	private boolean isAdd = false;
	
	
	// 방 삭제
	private JPanel delRoomP; //  방 삭제 패널
	
	private DefaultTableModel deRoomsModel; // 방 테이블 모델
	private JTable deRoomTable; // 방 테이블 
	
	private JButton delRoomOkayBtn; // 삭제하기 버튼
	
	private ArrayList<Room> delRoomArray;
	
	
	// 가격 변경
	private JPanel PriceChp; // 가격 변경 패널
	private JPanel pcoutP; // 가격 변경 출력 패널
	private JPanel pcInP; // 가격 변경 입력 패널
	
	private DefaultTableModel priceChModel; // 가격 테이블 모델
	private JTable priceChTable; // 가격 테이블
	
	private JTextField pcheadcountText; // 정원(명) 텍스트 필드
	private JTextField pcPriceText; // 수정 가격 텍스트 필드
	
	private JButton priceChOkayBtn; // 변경하기 버튼
	
	// 수익 조회
	private JPanel shInhp; // 수익 조회 패널
	private JPanel dayInP; // 날짜 별 통계 패널
	private JPanel monInP; // 월별 통계 입력 패널
	
	private DefaultTableModel sIDayModel; // 날짜 별 통계 테이블 모델
	private JTable sIDayTable; // 날짜 별 통계 테이블
	private DefaultTableModel sIMonModel; // 월별 통계 테이블 모델
	private JTable sIMonTable; // 월별 통계 테이블
	
	
	public GUI_ManagerMode (Management cms) {
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
		menuP.setLayout(new GridLayout(0, 6, 0, 0));
		
		searchRoomBtn = new JButton("방 조회");
		searchRoomBtn.setFont(new Font("굴림", Font.BOLD, 45));
		searchRoomBtn.addActionListener(new ManagerMyListener());
		menuP.add(searchRoomBtn);
		addRoomBtn = new JButton("방 추가");
		addRoomBtn.setFont(new Font("굴림", Font.BOLD, 45));
		addRoomBtn.addActionListener(new ManagerMyListener());
		menuP.add(addRoomBtn);
		delRoomBtn = new JButton("방 삭제");
		delRoomBtn.setFont(new Font("굴림", Font.BOLD, 45));
		delRoomBtn.addActionListener(new ManagerMyListener());
		menuP.add(delRoomBtn);
		priceChBtn = new JButton("가격 변경");
		priceChBtn.setFont(new Font("굴림", Font.BOLD, 45));
		priceChBtn.addActionListener(new ManagerMyListener());
		menuP.add(priceChBtn);
		showIncomeBtn = new JButton("수익 조회");
		showIncomeBtn.setFont(new Font("굴림", Font.BOLD, 45));
		showIncomeBtn.addActionListener(new ManagerMyListener());
		menuP.add(showIncomeBtn);
		bakckBtn = new JButton("뒤로가기");
		bakckBtn.setFont(new Font("굴림", Font.BOLD, 45));
		bakckBtn.addActionListener(new ManagerMyListener(this));
		menuP.add(bakckBtn);
		
		SearchRoomPanel();
		AddRoomPanel();
		DelRoomPanel();
		PriceChPanel();
		ShowIncomePanel();
		
		
		centerP.add("searchroom", searchRoomP);
		centerP.add("addroom", addRoomP);
		centerP.add("delroom", delRoomP);
		centerP.add("pricech", PriceChp);
		centerP.add("showincome", shInhp);
		card.show(centerP, "searchroom");
		
		getContentPane().add(centerP, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}
	
	// 방 조회 패널
	void SearchRoomPanel() {
		searchRoomP = new JPanel();
		searchRoomP.setPreferredSize(new Dimension(1235, 600));
		searchRoomP.setLayout(null);
		
		// 방 조회 라벨
		JLabel srstr = new JLabel("방 조회");
		srstr.setBounds(0, 0, 875, 100);
		srstr.setHorizontalAlignment(SwingConstants.LEFT);
		srstr.setFont(new Font("굴림", Font.BOLD, 50));
		searchRoomP.add(srstr);
		
		// 방 테이블
		String colNames[] = {"방 이름", "정원(명)", "시간 당 가격(원)", "현재 사용 유무", "사용자", "전화번호", "입실시간"};
		roomsModel = new DefaultTableModel(colNames, 0);
		roomTable = new JTable(roomsModel);
		roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		roomTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		roomTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		roomTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		roomTable.getColumnModel().getColumn(2).setPreferredWidth(130);
		roomTable.getColumnModel().getColumn(3).setPreferredWidth(130);
		roomTable.getColumnModel().getColumn(4).setPreferredWidth(130);
		roomTable.getColumnModel().getColumn(5).setPreferredWidth(200);
		roomTable.getColumnModel().getColumn(6).setPreferredWidth(413);
		roomArray = cms.getRoomArr();
		int size = roomArray.size();
		Object data[] = new Object[7];
		for (int i = 0; i < size; i++) {
			data[0] = roomArray.get(i).getRoomName();
			data[1] = roomArray.get(i).getHeadcount();
			data[2] = roomArray.get(i).getPrice();
			boolean isUsed = roomArray.get(i).getIsUsed();
			if (isUsed) {
				data[3] = "사용 중";
				data[4] = roomArray.get(i).getWho().getName();
				data[5] = roomArray.get(i).getWho().getPhoneNum();
				data[6] = dateFormat.format(roomArray.get(i).getInTime());
			}
			else {
				data[3] = "-";
				data[4] = "-";
				data[5] = "-";
				data[6] = "-";
			}
			roomsModel.addRow(data);
		}
		JScrollPane roomScrollPane = new JScrollPane(roomTable);
		roomScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		roomScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		roomScrollPane.setLocation(0, 100);
		roomScrollPane.setSize(1221, 463);
		searchRoomP.add(roomScrollPane);
	}
	
	// 방 추가 패널
	void AddRoomPanel() {
		addRoomP = new JPanel();
		addRoomP.setPreferredSize(new Dimension(1235, 600));
		
		aroutP = new JPanel();
		aroutP.setBounds(0, 0, 600, 563);
		aroutP.setPreferredSize(new Dimension(560, 563));
		arInP = new JPanel();
		arInP.setBounds(661, 0, 560, 563);
		arInP.setPreferredSize(new Dimension(560, 563));
		aroutP.setLayout(null);
		
		// aroutP 결과 출력 패널
		JLabel arstr = new JLabel("방을 추가하시겠습니까?");
		arstr.setBounds(0, 0, 685, 100);
		arstr.setFont(new Font("굴림", Font.BOLD, 50));
		arstr.setHorizontalAlignment(SwingConstants.LEFT);
		aroutP.add(arstr);
		
		// 방 table
		String colNames[] = {"방 이름", "정원(명)", "시간 당 가격(원)"};
		adRoomsModel = new DefaultTableModel(colNames, 0);
		adRoomTable = new JTable(adRoomsModel);
		adRoomArray = cms.getRoomArr();
		int size = adRoomArray.size();
		Object data[] = new Object[3];
		for (int i = 0; i < size; i++) {
			data[0] = adRoomArray.get(i).getRoomName();
			data[1] = adRoomArray.get(i).getHeadcount();
			data[2] = adRoomArray.get(i).getPrice();
			adRoomsModel.addRow(data);
		}
		JScrollPane scrollPane = new JScrollPane(adRoomTable);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setLocation(0, 100);
		scrollPane.setSize(560, 463);
		aroutP.add(scrollPane);
		addRoomP.setLayout(null);
		arInP.setLayout(null);
		
		
		// arInP 입력 패널
		JPanel bp5 = new JPanel();
		bp5.setBounds(0, 0, 560, 100);
		bp5.setPreferredSize(new Dimension(560, 100));
		arInP.add(bp5);
		
		// 방 이름, 인원 수 입력
		JLabel adroomNameStr = new JLabel("방 이름");
		adroomNameStr.setBounds(0, 110, 146, 65);
		adroomNameStr.setFont(new Font("굴림", Font.BOLD, 35));
		arInP.add(adroomNameStr);
		adroomNameText = new JTextField();
		adroomNameText.setBounds(158, 110, 402, 64);
		adroomNameText.setFont(new Font("굴림", Font.PLAIN, 30));
		arInP.add(adroomNameText);
		
		JLabel adroomCountStr = new JLabel("정원(명)");
		adroomCountStr.setBounds(0, 210, 146, 65);
		adroomCountStr.setFont(new Font("굴림", Font.BOLD, 35));
		arInP.add(adroomCountStr);
		adroomCountText = new JTextField();
		adroomCountText.setBounds(158, 210, 402, 64);
		adroomCountText.setFont(new Font("굴림", Font.PLAIN, 30));
		arInP.add(adroomCountText);
		
		adokayBtn = new JButton("확인");
		adokayBtn.setBounds(432, 332, 128, 65);
		adokayBtn.setFont(new Font("굴림", Font.BOLD, 35));
		adokayBtn.addActionListener(new ManagerMyListener());
		arInP.add(adokayBtn);
		
		addRoomOkayBtn = new JButton("추가하기");
		addRoomOkayBtn.setBounds(370, 498, 190, 65);
		addRoomOkayBtn.setFont(new Font("굴림", Font.BOLD, 35));
		addRoomOkayBtn.addActionListener(new ManagerMyListener());
		arInP.add(addRoomOkayBtn);
		
		addRoomP.add(arInP);
		
		JLabel adroomCountInfoStr = new JLabel("방의 정원 8명, 4명, 2명만 가능합니다.");
		adroomCountInfoStr.setHorizontalAlignment(SwingConstants.RIGHT);
		adroomCountInfoStr.setFont(new Font("굴림", Font.BOLD, 19));
		adroomCountInfoStr.setBounds(0, 285, 560, 37);
		arInP.add(adroomCountInfoStr);
		addRoomP.add(aroutP);
	}
	
	// 방 삭제 패널
	void DelRoomPanel() {
		delRoomP = new JPanel();
		delRoomP.setPreferredSize(new Dimension(1235, 600));
		
		// 방 삭제 라벨
		JLabel drstr = new JLabel("방을 삭제하시겠습니까?");
		drstr.setBounds(0, 0, 875, 100);
		drstr.setHorizontalAlignment(SwingConstants.LEFT);
		drstr.setFont(new Font("굴림", Font.BOLD, 50));
		delRoomP.add(drstr);
		
		// 방 table
		String colNames[] = {"방 이름", "정원(명)", "시간 당 가격(원)"};
		deRoomsModel = new DefaultTableModel(colNames, 0);
		deRoomTable = new JTable(deRoomsModel);
		delRoomArray = cms.getRoomArr();
		int size = delRoomArray.size();
		Object data[] = new Object[3];
		for (int i = 0; i < size; i++) {
			data[0] = delRoomArray.get(i).getRoomName();
			data[1] = delRoomArray.get(i).getHeadcount();
			data[2] = delRoomArray.get(i).getPrice();
			deRoomsModel.addRow(data);
		}
		JScrollPane scrollPane = new JScrollPane(deRoomTable);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setLocation(0, 100);
		scrollPane.setSize(1221, 388);
		delRoomP.add(scrollPane);
		delRoomP.setLayout(null);
		
		// 삭제하기 버튼
		delRoomOkayBtn = new JButton("삭제하기");
		delRoomOkayBtn.setBounds(1031, 498, 190, 65);
		delRoomOkayBtn.setFont(new Font("굴림", Font.BOLD, 35));
		delRoomOkayBtn.addActionListener(new ManagerMyListener());
		delRoomP.add(delRoomOkayBtn);
	}
	
	// 가격 변경 패널
	void PriceChPanel() {
		PriceChp = new JPanel();
		PriceChp.setPreferredSize(new Dimension(1235, 600)); 
		
		pcoutP = new JPanel();
		pcoutP.setBounds(0, 0, 700, 563);
		pcoutP.setPreferredSize(new Dimension(560, 563));
		pcInP = new JPanel();
		pcInP.setBounds(661, 0, 560, 563);
		pcInP.setPreferredSize(new Dimension(560, 563));
		pcoutP.setLayout(null);
		
		// aroutP 결과 출력 패널
		JLabel pcstr = new JLabel("가격을 변경하시겠습니까?");
		pcstr.setBounds(0, 0, 735, 100);
		pcstr.setFont(new Font("굴림", Font.BOLD, 50));
		pcstr.setHorizontalAlignment(SwingConstants.LEFT);
		pcoutP.add(pcstr);
		
		// 가격 table
		String colNames[] = {"정원(명)", "시간 당 가격(원)", "방 개수"};
		priceChModel = new DefaultTableModel(colNames, 0);
		priceChTable = new JTable(priceChModel);
		Object bigPricedata[] = {8, cms.getBigPrice(), cms.countBigRoom().size()};
		Object middlePricedata[] = {4, cms.getMiddlePrice(), cms.countMiddleRoom().size()};
		Object smallPricedata[] = {2, cms.getSmallPrice(), cms.countSmallRoom().size()};
		priceChModel.addRow(bigPricedata);
		priceChModel.addRow(middlePricedata);
		priceChModel.addRow(smallPricedata);
		JScrollPane scrollPane = new JScrollPane(priceChTable);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setLocation(0, 100);
		scrollPane.setSize(560, 463);
		pcoutP.add(scrollPane);
		PriceChp.setLayout(null);
		pcInP.setLayout(null);
		
		// pcInP 입력 패널
		JPanel bp5 = new JPanel();
		bp5.setBounds(0, 0, 560, 100);
		bp5.setPreferredSize(new Dimension(560, 100));
		pcInP.add(bp5);
		
		// 정원(명), 수정 가격 입력
		JLabel pcheadcountStr = new JLabel("정원(명)");
		pcheadcountStr.setBounds(0, 110, 146, 65);
		pcheadcountStr.setFont(new Font("굴림", Font.BOLD, 35));
		pcInP.add(pcheadcountStr);
		pcheadcountText = new JTextField();
		pcheadcountText.setBounds(169, 110, 391, 64);
		pcheadcountText.setFont(new Font("굴림", Font.PLAIN, 30));
		pcInP.add(pcheadcountText);
		
		JLabel pcPriceStr = new JLabel("수정 가격");
		pcPriceStr.setBounds(0, 210, 157, 65);
		pcPriceStr.setFont(new Font("굴림", Font.BOLD, 35));
		pcInP.add(pcPriceStr);
		pcPriceText = new JTextField();
		pcPriceText.setBounds(169, 210, 391, 64);
		pcPriceText.setFont(new Font("굴림", Font.PLAIN, 30));
		pcInP.add(pcPriceText);
		
		priceChOkayBtn = new JButton("변경하기");
		priceChOkayBtn.setBounds(366, 332, 194, 65);
		priceChOkayBtn.setFont(new Font("굴림", Font.BOLD, 35));
		priceChOkayBtn.addActionListener(new ManagerMyListener());
		pcInP.add(priceChOkayBtn);
		
		JLabel pChCountInfoStr = new JLabel("방의 정원은 8명, 4명, 2명만 가능합니다.");
		pChCountInfoStr.setHorizontalAlignment(SwingConstants.RIGHT);
		pChCountInfoStr.setFont(new Font("굴림", Font.BOLD, 19));
		pChCountInfoStr.setBounds(0, 285, 560, 37);
		pcInP.add(pChCountInfoStr);
		
		PriceChp.add(pcInP);
		PriceChp.add(pcoutP);
	}
	
	// 수익 조회 패널
	void ShowIncomePanel() {
		shInhp = new JPanel();
		shInhp.setPreferredSize(new Dimension(1235, 600)); 
		shInhp.setLayout(null);
		
		dayInP = new JPanel();
		dayInP.setBounds(0, 0, 600, 563);
		dayInP.setPreferredSize(new Dimension(560, 563));
		monInP = new JPanel();
		monInP.setBounds(661, 0, 560, 563);
		monInP.setPreferredSize(new Dimension(560, 563));
		dayInP.setLayout(null);
		monInP.setLayout(null);
		
		// 수익 조회 라벨
		JLabel sIstr = new JLabel("수익 조회");
		sIstr.setBounds(0, 0, 512, 100);
		sIstr.setHorizontalAlignment(SwingConstants.LEFT);
		sIstr.setFont(new Font("굴림", Font.BOLD, 50));
		dayInP.add(sIstr);
		
		// 날짜 별 통계 라벨
		JLabel daystr = new JLabel("날짜 별 통계(이번 달)");
		daystr.setBounds(0, 100, 384, 60);
		daystr.setHorizontalAlignment(SwingConstants.LEFT);
		daystr.setFont(new Font("굴림", Font.BOLD, 35));
		dayInP.add(daystr);
		
		// 날짜 별 통계 table
		String colDNames[] = {"날짜(일)", "수익(원)"};
		sIDayModel = new DefaultTableModel(colDNames, 0);
		sIDayTable = new JTable(sIDayModel);
		Object day[] = new Object[2];
		for (int i = 1; i <= 31; i++) {
			day[0] = i;
			day[1] = cms.getTotalIncome(i);
			sIDayModel.addRow(day);
		}
		JScrollPane scrollDPane = new JScrollPane(sIDayTable);
		scrollDPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollDPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDPane.setLocation(0, 160);
		scrollDPane.setSize(560, 403);
		dayInP.add(scrollDPane);
		
		// 월별 통계 라벨
		JLabel monstr = new JLabel("월별 통계");
		monstr.setBounds(0, 100, 560, 60);
		monstr.setHorizontalAlignment(SwingConstants.LEFT);
		monstr.setFont(new Font("굴림", Font.BOLD, 35));
		monInP.add(monstr);
		
		// 월별 통계 table
		String colMNames[] = {"월", "수익(원)"};
		sIMonModel = new DefaultTableModel(colMNames, 0);
		sIMonTable = new JTable(sIMonModel);
		Object Mon[] = new Object[2];
		for (int i = 1; i <= 12; i++) {
			Mon[0] = i;
			Mon[1] = cms.getMonthlyStatistics(i);
			sIMonModel.addRow(Mon);
		}
		JScrollPane scrollMPane = new JScrollPane(sIMonTable);
		scrollMPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollMPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollMPane.setLocation(0, 160);
		scrollMPane.setSize(560, 403);
		monInP.add(scrollMPane);
		
		shInhp.add(monInP);
		shInhp.add(dayInP);
	}
	
	// 이벤트 함수
	// 방 추가 패널에서 확인 버튼을 눌렀을 때
	void arOkayBtnPress() {
		String roomName = adroomNameText.getText();
		
		if (roomName.equals("") || adroomCountText.getText().equals(""))
		{
			JOptionPane.showMessageDialog(addRoomP, "방 이름 또는 정원(명)을 입력해주십시오.", "Not Exist", JOptionPane.WARNING_MESSAGE);
			
		}
		else {
			adRoomsModel.setNumRows(0);
			
			try {
				int headCount = Integer.parseInt(adroomCountText.getText());
				
				cms.addRoom(headCount, roomName);
				
				isAdd = true;
			}
			catch (NumberFormatException NFE) {
				JOptionPane.showMessageDialog(addRoomP, "정원(명)에 숫자를 입력해주십시오.", "Not Correct Number", JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception e) {
				String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
				if (msg.equals("Retry")) // 발생한 exception이 "Retry"인 경우
				{
					JOptionPane.showMessageDialog(addRoomP, "정원(명)을 다시 입력해 주십시오.", "Retry", JOptionPane.WARNING_MESSAGE);
				}
				else if (msg.equals("Same Room")) // 발생한 exception이 "Same Room"인 경우
				{
					JOptionPane.showMessageDialog(addRoomP, "동일한 이름의 방이 존재합니다.\n" + "이름을 다시 입력해 주십시오.", "Same Room", JOptionPane.WARNING_MESSAGE);
				}
			}
			finally {

				adRoomArray = cms.getRoomArr();
				int size = adRoomArray.size();
				Object data[] = new Object[3];
				for (int i = 0; i < size; i++) {
					data[0] = adRoomArray.get(i).getRoomName();
					data[1] = adRoomArray.get(i).getHeadcount();
					data[2] = adRoomArray.get(i).getPrice();
					adRoomsModel.addRow(data);
				}
			}
		}
	}
	
	// 방 추가 패널에서 추가하기 버튼을 눌렀을 때
	void addRoomOkayBtnPress() {
		try {
			int headCount = Integer.parseInt(adroomCountText.getText());
		}
		catch (NumberFormatException NFE){
			JOptionPane.showMessageDialog(addRoomP, "정원(명)에 숫자를 입력해주십시오.", "Not Correct Number", JOptionPane.WARNING_MESSAGE);
		}
		
		JOptionPane.showMessageDialog(addRoomP, "방이 추가되었습니다.", "StudyCafe_Manager_AddRoom", JOptionPane.PLAIN_MESSAGE);
		
		adroomNameText.setText("");
		adroomCountText.setText("");
	}
	
	// 방 삭제 패널에서 삭제하기 버튼을 눌렀을 때
	void delRoomOkayBtnPress() {
		int row = deRoomTable.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(delRoomP, "방을 선택해주십시오.", "Not Choice", JOptionPane.WARNING_MESSAGE);
			return;
		}
		else {
			try {
				
				cms.removeRoom(delRoomArray.get(row).getRoomName());
				
				// 방 업데이트
				deRoomsModel.setNumRows(0);
				
				delRoomArray = cms.getRoomArr();
				int size = delRoomArray.size();
				Object data[] = new Object[3];
				for (int i = 0; i < size; i++) {
					data[0] = delRoomArray.get(i).getRoomName();
					data[1] = delRoomArray.get(i).getHeadcount();
					data[2] = delRoomArray.get(i).getPrice();
					deRoomsModel.addRow(data);
				}
				
				JOptionPane.showMessageDialog(delRoomP,delRoomArray.get(row).getRoomName() + "방이 삭제되었습니다.", "StudyCafe_Manager_AddRoom", JOptionPane.PLAIN_MESSAGE);
			} 
			catch (Exception e) {
				String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
				if (msg.equals("Not Exist")) // 발생한 exception이 "Not Exist"인 경우
				{
					JOptionPane.showMessageDialog(delRoomP, "제거할 방이 없습니다.", "Not Exist", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	// 가격 변경 패널 변경하기 버튼을 눌렀을 때
	void priceChOkayBtnPress() {
		if (pcheadcountText.getText().equals("") || pcPriceText.getText().equals(""))
		{
			JOptionPane.showMessageDialog(PriceChp, "정원(명) 또는 수정 가격을 입력해주십시오.", "Not Exist", JOptionPane.WARNING_MESSAGE);
			
		}
		else {
			try {
				int headcount = Integer.parseInt(pcheadcountText.getText());
				int price = Integer.parseInt(pcPriceText.getText());
				
				cms.setPrice(headcount, price);
				
				// 테이블 업데이트
				priceChModel.setNumRows(0);
				
				Object bigPricedata[] = {8, cms.getBigPrice(), cms.countBigRoom().size()};
				Object middlePricedata[] = {4, cms.getMiddlePrice(), cms.countMiddleRoom().size()};
				Object smallPricedata[] = {2, cms.getSmallPrice(), cms.countSmallRoom().size()};
				priceChModel.addRow(bigPricedata);
				priceChModel.addRow(middlePricedata);
				priceChModel.addRow(smallPricedata);
				
				JOptionPane.showMessageDialog(PriceChp, "가격이 변경되었습니다.", "StudyCafe_Manager_ChangePrice", JOptionPane.PLAIN_MESSAGE);
				
				pcheadcountText.setText("");
				pcPriceText.setText("");
			}
			catch (NumberFormatException NFE) {
				JOptionPane.showMessageDialog(PriceChp, "숫자를 입력해주십시오.", "Not Correct Number", JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception e) {
				String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
				if (msg.equals("Retry")) // 발생한 exception이 "Retry"인 경우
				{
					JOptionPane.showMessageDialog(PriceChp, "정원(명)을 8, 4, 2 중 하나 입력해주십시오.", "Retry", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	// 테이블 업데이트
	void searchRoom () {
		roomsModel.setNumRows(0);
		
		roomArray = cms.getRoomArr();
		int size = roomArray.size();
		Object data[] = new Object[7];
		for (int i = 0; i < size; i++) {
			data[0] = roomArray.get(i).getRoomName();
			data[1] = roomArray.get(i).getHeadcount();
			data[2] = roomArray.get(i).getPrice();
			boolean isUsed = roomArray.get(i).getIsUsed();
			if (isUsed) {
				data[3] = "사용 중";
				data[4] = roomArray.get(i).getWho().getName();
				data[5] = roomArray.get(i).getWho().getPhoneNum();
				data[6] = dateFormat.format(roomArray.get(i).getInTime());
			}
			else {
				data[3] = "-";
				data[4] = "-";
				data[5] = "-";
				data[6] = "-";
			}
			roomsModel.addRow(data);
		}
	}
	
	void updateAdRoom () {
		adRoomsModel.setNumRows(0);
		
		adRoomArray = cms.getRoomArr();
		int size = adRoomArray.size();
		Object data[] = new Object[3];
		for (int i = 0; i < size; i++) {
			data[0] = adRoomArray.get(i).getRoomName();
			data[1] = adRoomArray.get(i).getHeadcount();
			data[2] = adRoomArray.get(i).getPrice();
			adRoomsModel.addRow(data);
		}
	}
	
	void updateDelRoom() {
		deRoomsModel.setNumRows(0);
		
		delRoomArray = cms.getRoomArr();
		int size = delRoomArray.size();
		Object data[] = new Object[3];
		for (int i = 0; i < size; i++) {
			data[0] = delRoomArray.get(i).getRoomName();
			data[1] = delRoomArray.get(i).getHeadcount();
			data[2] = delRoomArray.get(i).getPrice();
			deRoomsModel.addRow(data);
		}
	}
	
	void updatePrice() {
		priceChModel.setNumRows(0);
		
		Object bigPricedata[] = {8, cms.getBigPrice(), cms.countBigRoom().size()};
		Object middlePricedata[] = {4, cms.getMiddlePrice(), cms.countMiddleRoom().size()};
		Object smallPricedata[] = {2, cms.getSmallPrice(), cms.countSmallRoom().size()};
		priceChModel.addRow(bigPricedata);
		priceChModel.addRow(middlePricedata);
		priceChModel.addRow(smallPricedata);
	}
	
	void updateShowIn() {
		sIDayModel.setNumRows(0);
		sIMonModel.setNumRows(0);
		
		Object day[] = new Object[2];
		for (int i = 1; i <= 31; i++) {
			day[0] = i;
			day[1] = cms.getTotalIncome(i);
			sIDayModel.addRow(day);
		}
		
		Object Mon[] = new Object[2];
		for (int i = 1; i <= 12; i++) {
			Mon[0] = i;
			Mon[1] = cms.getMonthlyStatistics(i);
			sIMonModel.addRow(Mon);
		}
		
	}
	
	// 이벤트
	private class ManagerMyListener implements ActionListener {
		JFrame f;
		public ManagerMyListener(JFrame f) {
			this.f = f;
		}
		
		public ManagerMyListener() {
			
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == searchRoomBtn) {
				searchRoom();
				card.show(centerP, "searchroom");
			}
			else if (e.getSource() == addRoomBtn) {
				updateAdRoom();
				card.show(centerP, "addroom");
				adroomNameText.setText("");
				adroomCountText.setText("");
			}
			else if (e.getSource() == delRoomBtn) {
				updateDelRoom();
				card.show(centerP, "delroom");
			}
			else if (e.getSource() == priceChBtn) {
				updatePrice();
				card.show(centerP, "pricech");
				pcheadcountText.setText("");
				pcPriceText.setText("");
			}
			else if (e.getSource() == showIncomeBtn) {
				updateShowIn();
				card.show(centerP, "showincome");
			}
			else if (e.getSource() == bakckBtn) {
				f.setVisible(false);
			}
			else if (e.getSource() == adokayBtn) {
				arOkayBtnPress();
			}
			else if (e.getSource() == addRoomOkayBtn) {
				if (isAdd) {
					addRoomOkayBtnPress();
					Save();
					isAdd = false;
				}
				else {
					JOptionPane.showMessageDialog(null, "방을 추가하지 않았습니다. (변경 된 사항이 없습니다.)", "Not Add", JOptionPane.WARNING_MESSAGE);
				}
			}
			else if (e.getSource() == delRoomOkayBtn) {
				delRoomOkayBtnPress();
				Save();
			}
			else if (e.getSource() == priceChOkayBtn) {
				priceChOkayBtnPress();
				Save();
			}
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
