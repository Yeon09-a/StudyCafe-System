
public class User implements java.io.Serializable { // 사용자 클래스
	// 멤버변수
	private String name; // 사용자의 이름을 나타낸 변수 
	private String phoneNum; // 사용자의 전화번호를 나타낸 변수
	
	// 멤버함수
	public User(String name, String phoneNum) { // User 클래스의 생성자
		this.name = name; // User 클래스의 name를 매개변수 name로 초기화한다.
		this.phoneNum = phoneNum; // User 클래스의 phoneNum를 매개변수 phoneNum로 초기화한다.
	}
	
	public User(String phoneNum) { // User 클래스의 생성자
		this.phoneNum = phoneNum; // User 클래스의 phoneNum를 매개변수 phoneNum로 초기화한다.
	}
	
	
	public String getName() { // name를 반환하는 getter 메소드
		return name; // name을 반환한다.
	}
	
	public String getPhoneNum() { // phoneNum를 반환하는 getter 메소드
		return phoneNum; // phoneNum을 반환한다.
	}
	
	public void setName(String name) { // name를 설정하는 setter 메소드
		this.name = name; // User 클래스의 name를 매개변수 name로 초기화한다.
	}
	
	public void setPhoneNum(String phoneNum) { // phoneNum를 설정하는 setter 메소드
		this.phoneNum = phoneNum; // User 클래스의 phoneNum를 매개변수 phoneNum로 초기화한다.
	}

}
