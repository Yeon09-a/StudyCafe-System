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
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy�� MM�� dd�� aa hh�� mm�� ss��"); // SimpleDateFormat ������ �Ķ���ͷ� ��¥�� �ð��� ������ �ѱ��, new�� ��ü�� �����Ѵ�.
	
	// �޴�
	private JButton searchRoomBtn; // �� ��ȸ ��ư
	private JButton addRoomBtn; // �� �߰� ��ư
	private JButton delRoomBtn; // �� ���� ��ư
	private JButton priceChBtn; // ���� ���� ��ư
	private JButton showIncomeBtn; // ���� ��ȸ ��ư
	private JButton bakckBtn; // �ڷΰ��� ��ư
	
	private JPanel centerP; // �ٲ�� ������ �г�
	
	private CardLayout card = new CardLayout(); // ī�� ������
	
	
	// �� ��ȸ
	private JPanel searchRoomP; // �� ��ȸ �г�
	
	private DefaultTableModel roomsModel; // �� ���̺� ��
	private JTable roomTable; // �� ���̺�
	
	private ArrayList<Room> roomArray;
	
	
	// �� �߰�
	private JPanel addRoomP; // �� �߰� �г�
	private JPanel aroutP; // �� �߰� ��� �г�
	private JPanel arInP; // �� �߰� �Է� �г�
	
	private DefaultTableModel adRoomsModel; // �� ���̺� ��
	private JTable adRoomTable; // �� ���̺�
	
	private JTextField adroomNameText; // �� �̸� �ؽ�Ʈ �ʵ�
	private JTextField adroomCountText; // �� �ο� �� �ؽ�Ʈ �ʵ�
	
	private JButton adokayBtn; // Ȯ�� ��ư
	private JButton addRoomOkayBtn; // �߰��ϱ� ��ư
	
	private ArrayList<Room> adRoomArray;
	
	private boolean isAdd = false;
	
	
	// �� ����
	private JPanel delRoomP; //  �� ���� �г�
	
	private DefaultTableModel deRoomsModel; // �� ���̺� ��
	private JTable deRoomTable; // �� ���̺� 
	
	private JButton delRoomOkayBtn; // �����ϱ� ��ư
	
	private ArrayList<Room> delRoomArray;
	
	
	// ���� ����
	private JPanel PriceChp; // ���� ���� �г�
	private JPanel pcoutP; // ���� ���� ��� �г�
	private JPanel pcInP; // ���� ���� �Է� �г�
	
	private DefaultTableModel priceChModel; // ���� ���̺� ��
	private JTable priceChTable; // ���� ���̺�
	
	private JTextField pcheadcountText; // ����(��) �ؽ�Ʈ �ʵ�
	private JTextField pcPriceText; // ���� ���� �ؽ�Ʈ �ʵ�
	
	private JButton priceChOkayBtn; // �����ϱ� ��ư
	
	// ���� ��ȸ
	private JPanel shInhp; // ���� ��ȸ �г�
	private JPanel dayInP; // ��¥ �� ��� �г�
	private JPanel monInP; // ���� ��� �Է� �г�
	
	private DefaultTableModel sIDayModel; // ��¥ �� ��� ���̺� ��
	private JTable sIDayTable; // ��¥ �� ��� ���̺�
	private DefaultTableModel sIMonModel; // ���� ��� ���̺� ��
	private JTable sIMonTable; // ���� ��� ���̺�
	
	
	public GUI_ManagerMode (Management cms) {
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
		menuP.setLayout(new GridLayout(0, 6, 0, 0));
		
		searchRoomBtn = new JButton("�� ��ȸ");
		searchRoomBtn.setFont(new Font("����", Font.BOLD, 45));
		searchRoomBtn.addActionListener(new ManagerMyListener());
		menuP.add(searchRoomBtn);
		addRoomBtn = new JButton("�� �߰�");
		addRoomBtn.setFont(new Font("����", Font.BOLD, 45));
		addRoomBtn.addActionListener(new ManagerMyListener());
		menuP.add(addRoomBtn);
		delRoomBtn = new JButton("�� ����");
		delRoomBtn.setFont(new Font("����", Font.BOLD, 45));
		delRoomBtn.addActionListener(new ManagerMyListener());
		menuP.add(delRoomBtn);
		priceChBtn = new JButton("���� ����");
		priceChBtn.setFont(new Font("����", Font.BOLD, 45));
		priceChBtn.addActionListener(new ManagerMyListener());
		menuP.add(priceChBtn);
		showIncomeBtn = new JButton("���� ��ȸ");
		showIncomeBtn.setFont(new Font("����", Font.BOLD, 45));
		showIncomeBtn.addActionListener(new ManagerMyListener());
		menuP.add(showIncomeBtn);
		bakckBtn = new JButton("�ڷΰ���");
		bakckBtn.setFont(new Font("����", Font.BOLD, 45));
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
	
	// �� ��ȸ �г�
	void SearchRoomPanel() {
		searchRoomP = new JPanel();
		searchRoomP.setPreferredSize(new Dimension(1235, 600));
		searchRoomP.setLayout(null);
		
		// �� ��ȸ ��
		JLabel srstr = new JLabel("�� ��ȸ");
		srstr.setBounds(0, 0, 875, 100);
		srstr.setHorizontalAlignment(SwingConstants.LEFT);
		srstr.setFont(new Font("����", Font.BOLD, 50));
		searchRoomP.add(srstr);
		
		// �� ���̺�
		String colNames[] = {"�� �̸�", "����(��)", "�ð� �� ����(��)", "���� ��� ����", "�����", "��ȭ��ȣ", "�Խǽð�"};
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
				data[3] = "��� ��";
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
	
	// �� �߰� �г�
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
		
		// aroutP ��� ��� �г�
		JLabel arstr = new JLabel("���� �߰��Ͻðڽ��ϱ�?");
		arstr.setBounds(0, 0, 685, 100);
		arstr.setFont(new Font("����", Font.BOLD, 50));
		arstr.setHorizontalAlignment(SwingConstants.LEFT);
		aroutP.add(arstr);
		
		// �� table
		String colNames[] = {"�� �̸�", "����(��)", "�ð� �� ����(��)"};
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
		
		
		// arInP �Է� �г�
		JPanel bp5 = new JPanel();
		bp5.setBounds(0, 0, 560, 100);
		bp5.setPreferredSize(new Dimension(560, 100));
		arInP.add(bp5);
		
		// �� �̸�, �ο� �� �Է�
		JLabel adroomNameStr = new JLabel("�� �̸�");
		adroomNameStr.setBounds(0, 110, 146, 65);
		adroomNameStr.setFont(new Font("����", Font.BOLD, 35));
		arInP.add(adroomNameStr);
		adroomNameText = new JTextField();
		adroomNameText.setBounds(158, 110, 402, 64);
		adroomNameText.setFont(new Font("����", Font.PLAIN, 30));
		arInP.add(adroomNameText);
		
		JLabel adroomCountStr = new JLabel("����(��)");
		adroomCountStr.setBounds(0, 210, 146, 65);
		adroomCountStr.setFont(new Font("����", Font.BOLD, 35));
		arInP.add(adroomCountStr);
		adroomCountText = new JTextField();
		adroomCountText.setBounds(158, 210, 402, 64);
		adroomCountText.setFont(new Font("����", Font.PLAIN, 30));
		arInP.add(adroomCountText);
		
		adokayBtn = new JButton("Ȯ��");
		adokayBtn.setBounds(432, 332, 128, 65);
		adokayBtn.setFont(new Font("����", Font.BOLD, 35));
		adokayBtn.addActionListener(new ManagerMyListener());
		arInP.add(adokayBtn);
		
		addRoomOkayBtn = new JButton("�߰��ϱ�");
		addRoomOkayBtn.setBounds(370, 498, 190, 65);
		addRoomOkayBtn.setFont(new Font("����", Font.BOLD, 35));
		addRoomOkayBtn.addActionListener(new ManagerMyListener());
		arInP.add(addRoomOkayBtn);
		
		addRoomP.add(arInP);
		
		JLabel adroomCountInfoStr = new JLabel("���� ���� 8��, 4��, 2�� �����մϴ�.");
		adroomCountInfoStr.setHorizontalAlignment(SwingConstants.RIGHT);
		adroomCountInfoStr.setFont(new Font("����", Font.BOLD, 19));
		adroomCountInfoStr.setBounds(0, 285, 560, 37);
		arInP.add(adroomCountInfoStr);
		addRoomP.add(aroutP);
	}
	
	// �� ���� �г�
	void DelRoomPanel() {
		delRoomP = new JPanel();
		delRoomP.setPreferredSize(new Dimension(1235, 600));
		
		// �� ���� ��
		JLabel drstr = new JLabel("���� �����Ͻðڽ��ϱ�?");
		drstr.setBounds(0, 0, 875, 100);
		drstr.setHorizontalAlignment(SwingConstants.LEFT);
		drstr.setFont(new Font("����", Font.BOLD, 50));
		delRoomP.add(drstr);
		
		// �� table
		String colNames[] = {"�� �̸�", "����(��)", "�ð� �� ����(��)"};
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
		
		// �����ϱ� ��ư
		delRoomOkayBtn = new JButton("�����ϱ�");
		delRoomOkayBtn.setBounds(1031, 498, 190, 65);
		delRoomOkayBtn.setFont(new Font("����", Font.BOLD, 35));
		delRoomOkayBtn.addActionListener(new ManagerMyListener());
		delRoomP.add(delRoomOkayBtn);
	}
	
	// ���� ���� �г�
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
		
		// aroutP ��� ��� �г�
		JLabel pcstr = new JLabel("������ �����Ͻðڽ��ϱ�?");
		pcstr.setBounds(0, 0, 735, 100);
		pcstr.setFont(new Font("����", Font.BOLD, 50));
		pcstr.setHorizontalAlignment(SwingConstants.LEFT);
		pcoutP.add(pcstr);
		
		// ���� table
		String colNames[] = {"����(��)", "�ð� �� ����(��)", "�� ����"};
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
		
		// pcInP �Է� �г�
		JPanel bp5 = new JPanel();
		bp5.setBounds(0, 0, 560, 100);
		bp5.setPreferredSize(new Dimension(560, 100));
		pcInP.add(bp5);
		
		// ����(��), ���� ���� �Է�
		JLabel pcheadcountStr = new JLabel("����(��)");
		pcheadcountStr.setBounds(0, 110, 146, 65);
		pcheadcountStr.setFont(new Font("����", Font.BOLD, 35));
		pcInP.add(pcheadcountStr);
		pcheadcountText = new JTextField();
		pcheadcountText.setBounds(169, 110, 391, 64);
		pcheadcountText.setFont(new Font("����", Font.PLAIN, 30));
		pcInP.add(pcheadcountText);
		
		JLabel pcPriceStr = new JLabel("���� ����");
		pcPriceStr.setBounds(0, 210, 157, 65);
		pcPriceStr.setFont(new Font("����", Font.BOLD, 35));
		pcInP.add(pcPriceStr);
		pcPriceText = new JTextField();
		pcPriceText.setBounds(169, 210, 391, 64);
		pcPriceText.setFont(new Font("����", Font.PLAIN, 30));
		pcInP.add(pcPriceText);
		
		priceChOkayBtn = new JButton("�����ϱ�");
		priceChOkayBtn.setBounds(366, 332, 194, 65);
		priceChOkayBtn.setFont(new Font("����", Font.BOLD, 35));
		priceChOkayBtn.addActionListener(new ManagerMyListener());
		pcInP.add(priceChOkayBtn);
		
		JLabel pChCountInfoStr = new JLabel("���� ������ 8��, 4��, 2�� �����մϴ�.");
		pChCountInfoStr.setHorizontalAlignment(SwingConstants.RIGHT);
		pChCountInfoStr.setFont(new Font("����", Font.BOLD, 19));
		pChCountInfoStr.setBounds(0, 285, 560, 37);
		pcInP.add(pChCountInfoStr);
		
		PriceChp.add(pcInP);
		PriceChp.add(pcoutP);
	}
	
	// ���� ��ȸ �г�
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
		
		// ���� ��ȸ ��
		JLabel sIstr = new JLabel("���� ��ȸ");
		sIstr.setBounds(0, 0, 512, 100);
		sIstr.setHorizontalAlignment(SwingConstants.LEFT);
		sIstr.setFont(new Font("����", Font.BOLD, 50));
		dayInP.add(sIstr);
		
		// ��¥ �� ��� ��
		JLabel daystr = new JLabel("��¥ �� ���(�̹� ��)");
		daystr.setBounds(0, 100, 384, 60);
		daystr.setHorizontalAlignment(SwingConstants.LEFT);
		daystr.setFont(new Font("����", Font.BOLD, 35));
		dayInP.add(daystr);
		
		// ��¥ �� ��� table
		String colDNames[] = {"��¥(��)", "����(��)"};
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
		
		// ���� ��� ��
		JLabel monstr = new JLabel("���� ���");
		monstr.setBounds(0, 100, 560, 60);
		monstr.setHorizontalAlignment(SwingConstants.LEFT);
		monstr.setFont(new Font("����", Font.BOLD, 35));
		monInP.add(monstr);
		
		// ���� ��� table
		String colMNames[] = {"��", "����(��)"};
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
	
	// �̺�Ʈ �Լ�
	// �� �߰� �гο��� Ȯ�� ��ư�� ������ ��
	void arOkayBtnPress() {
		String roomName = adroomNameText.getText();
		
		if (roomName.equals("") || adroomCountText.getText().equals(""))
		{
			JOptionPane.showMessageDialog(addRoomP, "�� �̸� �Ǵ� ����(��)�� �Է����ֽʽÿ�.", "Not Exist", JOptionPane.WARNING_MESSAGE);
			
		}
		else {
			adRoomsModel.setNumRows(0);
			
			try {
				int headCount = Integer.parseInt(adroomCountText.getText());
				
				cms.addRoom(headCount, roomName);
				
				isAdd = true;
			}
			catch (NumberFormatException NFE) {
				JOptionPane.showMessageDialog(addRoomP, "����(��)�� ���ڸ� �Է����ֽʽÿ�.", "Not Correct Number", JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception e) {
				String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
				if (msg.equals("Retry")) // �߻��� exception�� "Retry"�� ���
				{
					JOptionPane.showMessageDialog(addRoomP, "����(��)�� �ٽ� �Է��� �ֽʽÿ�.", "Retry", JOptionPane.WARNING_MESSAGE);
				}
				else if (msg.equals("Same Room")) // �߻��� exception�� "Same Room"�� ���
				{
					JOptionPane.showMessageDialog(addRoomP, "������ �̸��� ���� �����մϴ�.\n" + "�̸��� �ٽ� �Է��� �ֽʽÿ�.", "Same Room", JOptionPane.WARNING_MESSAGE);
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
	
	// �� �߰� �гο��� �߰��ϱ� ��ư�� ������ ��
	void addRoomOkayBtnPress() {
		try {
			int headCount = Integer.parseInt(adroomCountText.getText());
		}
		catch (NumberFormatException NFE){
			JOptionPane.showMessageDialog(addRoomP, "����(��)�� ���ڸ� �Է����ֽʽÿ�.", "Not Correct Number", JOptionPane.WARNING_MESSAGE);
		}
		
		JOptionPane.showMessageDialog(addRoomP, "���� �߰��Ǿ����ϴ�.", "StudyCafe_Manager_AddRoom", JOptionPane.PLAIN_MESSAGE);
		
		adroomNameText.setText("");
		adroomCountText.setText("");
	}
	
	// �� ���� �гο��� �����ϱ� ��ư�� ������ ��
	void delRoomOkayBtnPress() {
		int row = deRoomTable.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(delRoomP, "���� �������ֽʽÿ�.", "Not Choice", JOptionPane.WARNING_MESSAGE);
			return;
		}
		else {
			try {
				
				cms.removeRoom(delRoomArray.get(row).getRoomName());
				
				// �� ������Ʈ
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
				
				JOptionPane.showMessageDialog(delRoomP,delRoomArray.get(row).getRoomName() + "���� �����Ǿ����ϴ�.", "StudyCafe_Manager_AddRoom", JOptionPane.PLAIN_MESSAGE);
			} 
			catch (Exception e) {
				String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
				if (msg.equals("Not Exist")) // �߻��� exception�� "Not Exist"�� ���
				{
					JOptionPane.showMessageDialog(delRoomP, "������ ���� �����ϴ�.", "Not Exist", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	// ���� ���� �г� �����ϱ� ��ư�� ������ ��
	void priceChOkayBtnPress() {
		if (pcheadcountText.getText().equals("") || pcPriceText.getText().equals(""))
		{
			JOptionPane.showMessageDialog(PriceChp, "����(��) �Ǵ� ���� ������ �Է����ֽʽÿ�.", "Not Exist", JOptionPane.WARNING_MESSAGE);
			
		}
		else {
			try {
				int headcount = Integer.parseInt(pcheadcountText.getText());
				int price = Integer.parseInt(pcPriceText.getText());
				
				cms.setPrice(headcount, price);
				
				// ���̺� ������Ʈ
				priceChModel.setNumRows(0);
				
				Object bigPricedata[] = {8, cms.getBigPrice(), cms.countBigRoom().size()};
				Object middlePricedata[] = {4, cms.getMiddlePrice(), cms.countMiddleRoom().size()};
				Object smallPricedata[] = {2, cms.getSmallPrice(), cms.countSmallRoom().size()};
				priceChModel.addRow(bigPricedata);
				priceChModel.addRow(middlePricedata);
				priceChModel.addRow(smallPricedata);
				
				JOptionPane.showMessageDialog(PriceChp, "������ ����Ǿ����ϴ�.", "StudyCafe_Manager_ChangePrice", JOptionPane.PLAIN_MESSAGE);
				
				pcheadcountText.setText("");
				pcPriceText.setText("");
			}
			catch (NumberFormatException NFE) {
				JOptionPane.showMessageDialog(PriceChp, "���ڸ� �Է����ֽʽÿ�.", "Not Correct Number", JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception e) {
				String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
				if (msg.equals("Retry")) // �߻��� exception�� "Retry"�� ���
				{
					JOptionPane.showMessageDialog(PriceChp, "����(��)�� 8, 4, 2 �� �ϳ� �Է����ֽʽÿ�.", "Retry", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
	// ���̺� ������Ʈ
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
				data[3] = "��� ��";
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
	
	// �̺�Ʈ
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
					JOptionPane.showMessageDialog(null, "���� �߰����� �ʾҽ��ϴ�. (���� �� ������ �����ϴ�.)", "Not Add", JOptionPane.WARNING_MESSAGE);
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
