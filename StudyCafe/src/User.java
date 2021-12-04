
public class User implements java.io.Serializable { // ����� Ŭ����
	// �������
	private String name; // ������� �̸��� ��Ÿ�� ���� 
	private String phoneNum; // ������� ��ȭ��ȣ�� ��Ÿ�� ����
	
	// ����Լ�
	public User(String name, String phoneNum) { // User Ŭ������ ������
		this.name = name; // User Ŭ������ name�� �Ű����� name�� �ʱ�ȭ�Ѵ�.
		this.phoneNum = phoneNum; // User Ŭ������ phoneNum�� �Ű����� phoneNum�� �ʱ�ȭ�Ѵ�.
	}
	
	public User(String phoneNum) { // User Ŭ������ ������
		this.phoneNum = phoneNum; // User Ŭ������ phoneNum�� �Ű����� phoneNum�� �ʱ�ȭ�Ѵ�.
	}
	
	
	public String getName() { // name�� ��ȯ�ϴ� getter �޼ҵ�
		return name; // name�� ��ȯ�Ѵ�.
	}
	
	public String getPhoneNum() { // phoneNum�� ��ȯ�ϴ� getter �޼ҵ�
		return phoneNum; // phoneNum�� ��ȯ�Ѵ�.
	}
	
	public void setName(String name) { // name�� �����ϴ� setter �޼ҵ�
		this.name = name; // User Ŭ������ name�� �Ű����� name�� �ʱ�ȭ�Ѵ�.
	}
	
	public void setPhoneNum(String phoneNum) { // phoneNum�� �����ϴ� setter �޼ҵ�
		this.phoneNum = phoneNum; // User Ŭ������ phoneNum�� �Ű����� phoneNum�� �ʱ�ȭ�Ѵ�.
	}

}
