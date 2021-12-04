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
	
	// �޴�
	private JButton checkInBtn; // üũ �� ��ư
	private JButton checkOutBtn; // üũ �ƿ� ��ư
	private JButton bakckBtn; // �ڷΰ��� ��ư
	
	private JPanel centerP; // �ٲ�� ������ �г�
	
	private CardLayout card = new CardLayout(); // ī�� ������
	
	// üũ ��
	private JButton cIokayBtn; // Ȯ�� ��ư
	private JButton checkInOkayBtn; // üũ �� ��ư
	
	private JPanel checkInP; // üũ �� �г�
	private JPanel cIInP; // üũ �� �Է� �г�
	private JPanel cIoutP; // üũ �� ��� �г�
	private JLabel choiceR; // ������ �� ��� �г�
	private JPanel userP; //  �̸�, ��ȭ��ȣ �ؽ�ũ �ʵ�, üũ �� ��ư �г�
	
	private JTextField cImemberField; // �ο� �� �ؽ�Ʈ �ʵ�
	private JTextField cInameTextField; // �̸� �ؽ�Ʈ �ʵ�
	private JTextField cIphoneNumTextField; // ��ȭ��ȣ �ؽ�Ʈ �ʵ�
	
	private DefaultTableModel roomsModel; // �� ���̺� ��
	private JTable roomTable; // �� ���̺�
	
	private ArrayList<Room> emptyRoomList; // �� �� ����Ʈ
	
	private int row = -1;
	
	
	// üũ �ƿ�
	private JPanel checkOutP; // üũ �ƿ� �г�
	
	private JTextField cOphoneNumTextField; // ��ȭ���� �ؽ�Ʈ �ʵ�
	
	private JButton cOokayBtn; // Ȯ�� ��ư
	private JButton CheckOutokayBtn; // üũ �ƿ� ��ư
	
	private DefaultTableModel roomsInfoModel; // �� ���� ���̺� ��
	private JTable roomInfoTable; // �� ���� ���̺�
	
	private Room cOuseRoom; // üũ �ƿ� �� �� ��
	
	private int cost; // ���
	
	private boolean isFRoom = false;
	
	
	public GUI_UserMode(Management cms) {
		this.cms = cms;
		
		setTitle("StudyCafe_User");
		setPreferredSize(new Dimension(1535, 800)); // ������ ũ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ����
		JPanel bp1 = new JPanel();
		bp1.setPreferredSize(new Dimension(150, 0));
		JPanel bp3 = new JPanel();
		bp3.setPreferredSize(new Dimension(150, 0));
		JPanel bp4 = new JPanel();
		bp4.setPreferredSize(new Dimension(0, 100));
		
		getContentPane().add(bp1, BorderLayout.WEST);
		getContentPane().add(bp3, BorderLayout.EAST);
		getContentPane().add(bp4, BorderLayout.SOUTH);
		
		// ���� �г�
		centerP = new JPanel();
		centerP.setPreferredSize(new Dimension(1235, 600));
		centerP.setLayout(card);
		
		// �޴� ��ư
		JPanel menuP = new JPanel();
		menuP.setPreferredSize(new Dimension(0, 100));
		getContentPane().add(menuP, BorderLayout.NORTH);
		menuP.setLayout(new GridLayout(0, 3, 0, 0));
		
		checkInBtn = new JButton("üũ ��");
		checkInBtn.setFont(new Font("����", Font.BOLD, 55));
		checkInBtn.addActionListener(new userMyListener());
		menuP.add(checkInBtn);
		checkOutBtn = new JButton("üũ �ƿ�");
		checkOutBtn.setFont(new Font("����", Font.BOLD, 55));
		checkOutBtn.addActionListener(new userMyListener());
		menuP.add(checkOutBtn);
		bakckBtn = new JButton("�ڷΰ���");
		bakckBtn.setFont(new Font("����", Font.BOLD, 55));
		bakckBtn.addActionListener(new userMyListener(this));
		menuP.add(bakckBtn);
		
		// üũ �� �г�
		CheckInPanel();
		CheckOutPanel();
		
		centerP.add("checkin", checkInP);
		centerP.add("checkout", checkOutP);
		card.show(centerP, "checkin");
		
		getContentPane().add(centerP, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}
	
	// üũ �� �г�
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
		
		// cIoutP ��� ��� �г�
		JLabel cIstr = new JLabel("üũ �� �Ͻðڽ��ϱ�?");
		cIstr.setBounds(0, 0, 560, 100);
		cIstr.setFont(new Font("����", Font.BOLD, 50));
		cIstr.setHorizontalAlignment(SwingConstants.LEFT);
		cIoutP.add(cIstr);
		
		// �� table
		String colNames[] = {"�� �̸�", "����(��)", "�ð� �� ����(��)"};
		roomsModel = new DefaultTableModel(colNames, 0);
		roomTable = new JTable(roomsModel);
		JScrollPane scrollPane = new JScrollPane(roomTable);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setLocation(0, 100);
		scrollPane.setSize(560, 463);
		roomTable.addMouseListener(new userMyListener());
		cIoutP.add(scrollPane);
		
		
		// cIInP �Է� �г�
		JPanel bp5 = new JPanel();
		bp5.setBounds(0, 0, 560, 100);
		bp5.setPreferredSize(new Dimension(560, 100));
		
		JPanel memberP = new JPanel();
		memberP.setLocation(0, 98);
		memberP.setSize(560, 100);
		
		// �ο� �� �Է�
		JLabel memberStr = new JLabel("�ο� ��");
		memberStr.setBounds(0, 10, 146, 65);
		memberStr.setFont(new Font("����", Font.BOLD, 35));
		cImemberField = new JTextField();
		cImemberField.setFont(new Font("����", Font.PLAIN, 30));
		cImemberField.setBounds(158, 11, 262, 64);
		cIokayBtn = new JButton("Ȯ��");
		cIokayBtn.setFont(new Font("����", Font.BOLD, 35));
		cIokayBtn.setBounds(432, 10, 128, 65);
		cIokayBtn.addActionListener(new userMyListener());
		memberP.setLayout(null);
		memberP.add(memberStr);
		memberP.add(cImemberField);
		memberP.add(cIokayBtn);
		cIInP.add(bp5);
		cIInP.add(memberP);
		
		// ������ �� ��, �̸�, ��ȭ��ȣ �ؽ�ũ �ʵ�, üũ �� ��ư
		JPanel choiceRP = new JPanel();
		choiceRP.setLayout(null);
		choiceRP.setBounds(0, 197, 560, 100);
		cIInP.add(choiceRP);
		
		choiceR = new JLabel("");
		choiceR.setFont(new Font("����", Font.BOLD, 35));
		choiceR.setBounds(0, 10, 560, 65);
		choiceRP.add(choiceR);
		
		userP = new JPanel();
		userP.setLayout(null);
		userP.setBounds(0, 295, 560, 268);
		cIInP.add(userP);
		
		
		JLabel nameStr = new JLabel("�̸�");
		nameStr.setFont(new Font("����", Font.BOLD, 35));
		nameStr.setBounds(0, 9, 146, 65);
		userP.add(nameStr);
		
		cInameTextField = new JTextField();
		cInameTextField.setFont(new Font("����", Font.PLAIN, 30));
		cInameTextField.setBounds(158, 11, 402, 64);
		userP.add(cInameTextField);
		
		cIphoneNumTextField = new JTextField();
		cIphoneNumTextField.setFont(new Font("����", Font.PLAIN, 30));
		cIphoneNumTextField.setBounds(158, 107, 402, 64);
		userP.add(cIphoneNumTextField);
		
		JLabel phoneNumStr = new JLabel("��ȭ��ȣ");
		phoneNumStr.setFont(new Font("����", Font.BOLD, 35));
		phoneNumStr.setBounds(0, 105, 146, 65);
		userP.add(phoneNumStr);
		
		checkInOkayBtn = new JButton("üũ ��");
		checkInOkayBtn.setFont(new Font("����", Font.BOLD, 35));
		checkInOkayBtn.setBounds(392, 203, 168, 65);
		checkInOkayBtn.addActionListener(new userMyListener());
		userP.add(checkInOkayBtn);
		
		checkInP.add(cIoutP, BorderLayout.CENTER);
		checkInP.add(cIInP, BorderLayout.EAST);
		
	}
	
	// üũ �ƿ� �г�
	void CheckOutPanel() {
		checkOutP = new JPanel();
		checkOutP.setPreferredSize(new Dimension(1235, 600));
		checkOutP.setLayout(null);
		
		// üũ �ƿ� ��
		JLabel cOstr = new JLabel("üũ �ƿ� �Ͻðڽ��ϱ�?");
		cOstr.setBounds(0, 0, 875, 100);
		cOstr.setHorizontalAlignment(SwingConstants.LEFT);
		cOstr.setFont(new Font("����", Font.BOLD, 50));
		checkOutP.add(cOstr);
		
		// ��ȭ��ȣ �Է�, �� ���� ���̺� �г�
		JPanel cOoutP = new JPanel();
		cOoutP.setBounds(0, 100, 1221, 463);
		checkOutP.add(cOoutP);
		cOoutP.setLayout(null);
		
		JLabel phoneNumStr = new JLabel("��ȭ��ȣ");
		phoneNumStr.setBounds(0, 10, 146, 65);
		phoneNumStr.setFont(new Font("����", Font.BOLD, 35));
		cOoutP.add(phoneNumStr);
		
		cOphoneNumTextField = new JTextField();
		cOphoneNumTextField.setBounds(158, 11, 923, 64);
		cOphoneNumTextField.setFont(new Font("����", Font.PLAIN, 30));
		cOoutP.add(cOphoneNumTextField);
		
		cOokayBtn = new JButton("Ȯ��");
		cOokayBtn.setBounds(1093, 10, 128, 65);
		cOokayBtn.setFont(new Font("����", Font.BOLD, 35));
		cOokayBtn.addActionListener(new userMyListener());
		cOoutP.add(cOokayBtn);
		
		// �� ���� ���̺�
		String colNames[] = {"", "�� ����"};
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
		
		CheckOutokayBtn = new JButton("üũ �ƿ�");
		CheckOutokayBtn.setFont(new Font("����", Font.BOLD, 35));
		CheckOutokayBtn.setBounds(1022, 398, 199, 65);
		CheckOutokayBtn.addActionListener(new userMyListener());
		cOoutP.add(CheckOutokayBtn);
		
	}
	
	
	
	// �̺�Ʈ �Լ�
	// üũ �� �гο��� Ȯ�� ��ư�� ������ ��
	void cIOkayBtnPress(){
		// �� table�� �� �� �߰�
		roomsModel.setNumRows(0);
		
		if (cImemberField.getText().equals("")) {
			JOptionPane.showMessageDialog(checkInP, "�ο� ���� �־��ֽʽÿ�.", "Not Number", JOptionPane.WARNING_MESSAGE);
		}
		else {
			try {
				int personCount =  Integer.parseInt(cImemberField.getText());
				
				if (personCount <= 0) {
					JOptionPane.showMessageDialog(checkInP, "�ùٸ� �ο� ���� �־��ֽʽÿ�.", "Not Correct Number", JOptionPane.WARNING_MESSAGE);
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
				JOptionPane.showMessageDialog(checkInP, "���ڸ� �Է����ֽʽÿ�.", "Not Correct Number", JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� ��
				String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
				if (msg.equals("Not Exist")) // �߻��� exception�� "Not Exist"�� ���
				{
					JOptionPane.showMessageDialog(checkInP, "�� ���� �������� �ʽ��ϴ�.", "Not Exist", JOptionPane.WARNING_MESSAGE);
				}
			}
		}		
	}
	
	// üũ �� �гο��� üũ �� ��ư�� ������ ��
	void checkInOkayBtnPress() {
		String userName = cInameTextField.getText();
		String userPhoneNum = cIphoneNumTextField.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� aa hh�� mm�� ss��"); // SimpleDateFormat ������ �Ķ���ͷ� ��¥�� �ð��� ������ �ѱ��, new�� ��ü�� �����Ѵ�.
		
		try {
			if (row == -1)
			{
				JOptionPane.showMessageDialog(checkInP, "���� �������ֽʽÿ�.", "Not Choice", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else {
				if (userName.equals("") || userPhoneNum.equals(""))
				{
					JOptionPane.showMessageDialog(checkInP, "�̸� �Ǵ� ��ȭ��ȣ�� �Է����ֽʽÿ�.", "Not Exist", JOptionPane.WARNING_MESSAGE);
				}
				else {
					User user = new User(userName, userPhoneNum);
					Room useRoom = cms.checkIn(emptyRoomList.get(row).getRoomName(), user);
					
					JOptionPane.showMessageDialog(checkInP, "���� üũ �� �Ǿ����ϴ�.\n"
							+ useRoom.getRoomName() + "�� " + useRoom.getHeadcount() + "�ο� �ð� �� " + useRoom.getPrice() + "��\n"
							+ "�Խǽð� : " + dateFormat.format(useRoom.getInTime()), "StudyCafe_User_CheckIn", JOptionPane.PLAIN_MESSAGE);
					
					roomsModel.setNumRows(0);
					
					cInameTextField.setText("");
					cIphoneNumTextField.setText("");
					cImemberField.setText("");
					choiceR.setText("");
				}
			}
		}
		catch (Exception e) {
			String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
			if (msg.equals("Not Exist")) // �߻��� exception�� "Not Exist"�� ���
			{
				JOptionPane.showMessageDialog(checkInP, "üũ�� �� ���� �����ϴ�.\n�� �̸��� �ٽ� Ȯ���Ͻʽÿ�.", "Not Exist", JOptionPane.WARNING_MESSAGE);
			}
			else if (msg.equals("Ioobe")) // �߻��� exception�� "Ioobe"�� ���
			{
				JOptionPane.showMessageDialog(checkInP, "�� ����Ʈ�� ������ �Ѿ���ϴ�.", "Ioobe", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	// üũ �ƿ� �гο��� Ȯ�� ��ư�� ������ ��
	void cOOkayBtnPress() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� aa hh�� mm�� ss��"); // SimpleDateFormat ������ �Ķ���ͷ� ��¥�� �ð��� ������ �ѱ��, new�� ��ü�� �����Ѵ�.
		
		roomsInfoModel.setNumRows(0);
		
		if(cOphoneNumTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(checkOutP, "��ȭ��ȣ�� �Է����ֽʽÿ�.", "Not Exist", JOptionPane.WARNING_MESSAGE);
		}
		else {
			try {
				String userPhoneNum = cOphoneNumTextField.getText();
				int index = cms.searchUserNum(userPhoneNum);
				
				if (index == -1){
					JOptionPane.showMessageDialog(checkOutP, "üũ�ƿ� �� ���� �����ϴ�.\n��ȭ��ȣ�� �ٽ� Ȯ���Ͻʽÿ�.", "Not Exist", JOptionPane.WARNING_MESSAGE);
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
					
					roomsInfoModel.addRow(new Object[] {"�����", userName});
					roomsInfoModel.addRow(new Object[] {"��ȭ��ȣ", userPhoneNum});
					roomsInfoModel.addRow(new Object[] {"�� �̸�", roomName});
					roomsInfoModel.addRow(new Object[] {"����(��)", roomCount});
					roomsInfoModel.addRow(new Object[] {"�ð� �� ����(��)", roomPrice});
					roomsInfoModel.addRow(new Object[] {"�Խǽð�", inTime});
					roomsInfoModel.addRow(new Object[] {"���(��)", cost});
					
					isFRoom = true;
				}
				
			}
			catch (Exception e) {
				String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
				if (msg.equals("Not Exist")) // �߻��� exception�� "Not Exist"�� ���
				{
					JOptionPane.showMessageDialog(checkOutP, "üũ�ƿ� �� ���� �����ϴ�.\n��ȭ��ȣ�� �ٽ� Ȯ���Ͻʽÿ�.", "Not Exist", JOptionPane.WARNING_MESSAGE);
				}
				else if (msg.equals("Ioobe")) // �߻��� exception�� "Ioobe"�� ���
				{
					JOptionPane.showMessageDialog(checkOutP, "�� ����Ʈ�� ������ �Ѿ���ϴ�.", "Ioobe", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	// üũ �ƿ� �гο��� üũ �ƿ� ��ư�� ������ ��
	void checkOutOkayBtnPress(int cost) {
		roomsInfoModel.setNumRows(0);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� aa hh�� mm�� ss��"); // SimpleDateFormat ������ �Ķ���ͷ� ��¥�� �ð��� ������ �ѱ��, new�� ��ü�� �����Ѵ�.
		Room chRoom;
		
		try {
			chRoom = cms.checkOut(cOuseRoom.getRoomName());
			cms.setMonthlyStatistics(chRoom.getOutTime().getMonth() + 1, cost); // usedRoom�� ��� ��¥�� ��(usedRoom.getOutTime().getMonth() + 1)�� cost�� �Ű������� �޾� cms�� setMonthlyStatistics�� ȣ���Ͽ� �� ���Կ� �����Ѵ�.
			cms.setTotalIncome(chRoom.getOutTime().getDate(), cost); // usedRoom�� ��� ��¥�� ��(usedRoom.getOutTime().getDate())�� cost�� �Ű������� �޾� cms�� setTotalIncome�� ȣ���Ͽ� �Ϸ� ���Կ� �����Ѵ�.
			JOptionPane.showMessageDialog(checkInP, chRoom.getRoomName() + "���� üũ �ƿ� �Ǿ����ϴ�.\n"
					+ "��ǽð� : " + dateFormat.format(chRoom.getOutTime()), "StudyCafe_User_CheckOut", JOptionPane.PLAIN_MESSAGE);
			
			cOphoneNumTextField.setText("");
		}
		catch (Exception e) {
			String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
			if (msg.equals("Not Exist")) // �߻��� exception�� "Not Exist"�� ���
			{
				JOptionPane.showMessageDialog(checkOutP, "üũ�ƿ� �� ���� �����ϴ�." + "\n��ȭ��ȣ�� �ٽ� Ȯ���Ͻʽÿ�.", "Not Exist", JOptionPane.WARNING_MESSAGE);
			}
			else if (msg.equals("Ioobe")) // �߻��� exception�� "Ioobe"�� ���
			{
				JOptionPane.showMessageDialog(checkOutP, "�� ����Ʈ�� ������ �Ѿ���ϴ�.", "Ioobe", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	// �̺�Ʈ
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
					JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� ���� �˻����ֽʽÿ�.", "Not Find", JOptionPane.WARNING_MESSAGE);
				}
			}
			else if (e.getSource() == bakckBtn) {
				f.setVisible(false);
			}
		}
		
		public void mouseClicked(MouseEvent e) {
			// ���̺� Ŭ�� �� �� ����
			if (e.getSource() == roomTable) {
				row = roomTable.getSelectedRow();
				if(row == -1)
					return;
				choiceR.setText("���� : " + emptyRoomList.get(row).getRoomName() + "�� " + emptyRoomList.get(row).getHeadcount() + "�ο� �ð� �� " + emptyRoomList.get(row).getPrice() + "��");
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
		DataOutputStream out = null; // DataOutputStream ��ü�� ���� ���� out�� �����Ѵ�.
		ObjectOutputStream objOut = null; // ObjectOutputStream ��ü�� ���� ���� objOut�� �����Ѵ�.
		
		try {
			out = new DataOutputStream(new FileOutputStream("StudyCafeData.dat")); // "StudyCafeData.dat" ������ ����. FileOutputStream ��ü�� �����ؼ� DataOutputStream �������� �Ķ���ͷ� ����Ѵ�.
			objOut = new ObjectOutputStream(new FileOutputStream("StudyCafeObjectSerialization.dat")); // "StudyCafeObjectSerialization.dat" ������ ����. ObjectOutputStream ��ü�� �����Ѵ�.
			cms.Dataoutput(out, objOut); // cms�� Dataoutput �Լ��� ȣ���Ͽ� �����͸� ����.
		}
		catch (IOException ioe) // ������ �߻��� ��� ����ó��
		{
			JOptionPane.showMessageDialog(null, "�����͸� ������ �� �����ϴ�.", "Can't Save", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� �� catch�� �´�.
			String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
			if (msg.equals("Can't Write")) // �߻��� exception�� "Can't Write"�� ���
			{
				JOptionPane.showMessageDialog(null, "�����͸� ������ �� �����ϴ�.", "Can't Write", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}