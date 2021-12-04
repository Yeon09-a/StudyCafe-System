import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI_MainMode extends JFrame {

	Management cms;
	
	private JButton userBtn; // 사용자 모드 버튼
	private JButton MgrBtn; // 매니저 모드 버튼
	private JButton closeBtn; // 종료 버튼
	
	public GUI_MainMode(Management cms) {
		this.cms = cms;
		setTitle("StudyCafe");
		setPreferredSize(new Dimension(1535, 800)); // 프레임 크기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(1235, 600));
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		// 여백
		JPanel bp1 = new JPanel();
		bp1.setPreferredSize(new Dimension(150, 0));
		JPanel bp2 = new JPanel();
		bp2.setPreferredSize(new Dimension(0, 100));
		JPanel bp3 = new JPanel();
		bp3.setPreferredSize(new Dimension(150, 0));
		JPanel bp4 = new JPanel();
		bp4.setPreferredSize(new Dimension(0, 100));
		
		// mainPanel 메인 패널
		JPanel wtsc = new JPanel();
		wtsc.setPreferredSize(new Dimension(560, 100));
		JPanel modeBtnP = new JPanel();
		modeBtnP.setPreferredSize(new Dimension(560, 100));
		
		
		// wtsc WELCOME TO STUDYCAFE 패널
		JLabel W = new JLabel("WELCOME");
		W.setBounds(0, 75, 560, 93);
		W.setHorizontalAlignment(SwingConstants.CENTER);
		W.setFont(new Font("굴림", Font.BOLD, 80));
		JLabel T = new JLabel("TO");
		T.setBounds(0, 240, 560, 93);
		T.setHorizontalAlignment(SwingConstants.CENTER);
		T.setFont(new Font("굴림", Font.BOLD, 80));
		JLabel S = new JLabel("STUDYCAFE");
		S.setBounds(0, 398, 560, 93);
		S.setFont(new Font("굴림", Font.BOLD, 80));
		S.setHorizontalAlignment(SwingConstants.CENTER);
		wtsc.setLayout(null);
		wtsc.add(W);
		wtsc.add(T);
		wtsc.add(S);
		
		// modeBtnP 버튼 패널
		userBtn = new JButton("사용자");
		userBtn.setBounds(0, 0, 560, 150);
		userBtn.setFont(new Font("굴림", Font.BOLD, 65));
		userBtn.addActionListener(new mainMyListener());
		MgrBtn = new JButton("관리자");
		MgrBtn.setBounds(0, 206, 560, 150);
		MgrBtn.setFont(new Font("굴림", Font.BOLD, 65));
		MgrBtn.addActionListener(new mainMyListener());
		closeBtn = new JButton("종료");
		closeBtn.setBounds(0, 413, 560, 150);
		closeBtn.setFont(new Font("굴림", Font.BOLD, 65));
		closeBtn.addActionListener(new mainMyListener());
		modeBtnP.setLayout(null);
		modeBtnP.add(userBtn);
		modeBtnP.add(MgrBtn);
		modeBtnP.add(closeBtn);
		
		
		getContentPane().add(bp1, BorderLayout.WEST);
		getContentPane().add(bp2, BorderLayout.NORTH);
		getContentPane().add(bp3, BorderLayout.EAST);
		getContentPane().add(bp4, BorderLayout.SOUTH);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(wtsc, BorderLayout.WEST);
		mainPanel.add(modeBtnP, BorderLayout.EAST);
		
		this.pack();
		this.setVisible(true);
	}
	
	private class mainMyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == userBtn) {
				GUI_UserMode userMode = new GUI_UserMode(cms);
			}
			else if (e.getSource() == MgrBtn) {
				GUI_ManagerMode managerMode = new GUI_ManagerMode(cms);
			}
			else if (e.getSource() == closeBtn) {
				System.exit(0);
			}
		}
	}
}
		
		


