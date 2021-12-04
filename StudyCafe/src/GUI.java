import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

public class GUI {
	public static void main(String[] args) {
		DataInputStream in = null; // DataInputStream 객체를 담을 변수 in을 생성한다
		ObjectInputStream objIn = null; // ObjectInputStream 객체를 담을 변수 objIn을 생성한다..
			
		Management cms = null; // Management 객체를 담을 변수 cms을 생성한다.
			
		try {
			in = new DataInputStream(new FileInputStream("StudyCafeData.dat")); // "StudyCafeData.dat" 파일을 연다. FileInputStream 객체를 생성해서 DataInputStream 생성자의 파라미터로 사용한다.
			objIn = new ObjectInputStream(new FileInputStream("StudyCafeObjectSerialization.dat")); // "StudyCafeObjectSerialization.dat" 파일을 연다. ObjectInputStream 객체를 생성한다.
			cms = new Management(in, objIn); // Management 객체를 생성한다.
			//cms = new Management();
		}
		catch (FileNotFoundException fnfe) { // 읽을 파일이 존재하지 않을 경우에 발생하는 예외를 처리
			JOptionPane.showMessageDialog(null, "파일을 찾을 수 없습니다.", "Can't Find", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) { // try에서 인위적 exception이 발생하였을 때 catch로 온다.
			String msg = e.getMessage(); // getMessage() 메소드를 사용하여 exception의 메세지를 가져와 msg에 넣는다.
			if (msg.equals("ERROR")) // 발생한 exception이 "ERROR"인 경우
			{
				JOptionPane.showMessageDialog(null, "오류가 발생하였습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else if (msg.equals("Can't Read")){
				JOptionPane.showMessageDialog(null, "데이터를 불러올 수 없습니다.", "Can't Read", JOptionPane.ERROR_MESSAGE);
			}
			System.out.println();
		}
		
		GUI_MainMode mainMode = new GUI_MainMode(cms);
	
	}
}
