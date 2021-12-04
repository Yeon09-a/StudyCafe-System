import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

public class GUI {
	public static void main(String[] args) {
		DataInputStream in = null; // DataInputStream ��ü�� ���� ���� in�� �����Ѵ�
		ObjectInputStream objIn = null; // ObjectInputStream ��ü�� ���� ���� objIn�� �����Ѵ�..
			
		Management cms = null; // Management ��ü�� ���� ���� cms�� �����Ѵ�.
			
		try {
			in = new DataInputStream(new FileInputStream("StudyCafeData.dat")); // "StudyCafeData.dat" ������ ����. FileInputStream ��ü�� �����ؼ� DataInputStream �������� �Ķ���ͷ� ����Ѵ�.
			objIn = new ObjectInputStream(new FileInputStream("StudyCafeObjectSerialization.dat")); // "StudyCafeObjectSerialization.dat" ������ ����. ObjectInputStream ��ü�� �����Ѵ�.
			cms = new Management(in, objIn); // Management ��ü�� �����Ѵ�.
			//cms = new Management();
		}
		catch (FileNotFoundException fnfe) { // ���� ������ �������� ���� ��쿡 �߻��ϴ� ���ܸ� ó��
			JOptionPane.showMessageDialog(null, "������ ã�� �� �����ϴ�.", "Can't Find", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) { // try���� ������ exception�� �߻��Ͽ��� �� catch�� �´�.
			String msg = e.getMessage(); // getMessage() �޼ҵ带 ����Ͽ� exception�� �޼����� ������ msg�� �ִ´�.
			if (msg.equals("ERROR")) // �߻��� exception�� "ERROR"�� ���
			{
				JOptionPane.showMessageDialog(null, "������ �߻��Ͽ����ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else if (msg.equals("Can't Read")){
				JOptionPane.showMessageDialog(null, "�����͸� �ҷ��� �� �����ϴ�.", "Can't Read", JOptionPane.ERROR_MESSAGE);
			}
			System.out.println();
		}
		
		GUI_MainMode mainMode = new GUI_MainMode(cms);
	
	}
}
