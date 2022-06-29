package common;

import management.LoginControl;

public class Main {

	public static void main(String[] args) {
		// 로그인 control의 run메소드를 호출한다.
		LoginControl loginControl = new LoginControl();
		loginControl.run();
	}

}
