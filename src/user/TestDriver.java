package user;

import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDriver {

	public static void main(String[] args) {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/null2?serverTimezone=UTC";
		String user = "root";
		String password = "dhkd6029";
		try {
			Class.forName(driver);
			System.out.println("jdbc driver �ε� ����");
			DriverManager.getConnection(url, user, password);
			System.out.println("����Ŭ ���� ����");
		} catch (ClassNotFoundException e) {
			System.out.println("jdbc driver �ε� ����");
		} catch (SQLException e) {
			System.out.println("����Ŭ ���� ����");
		}

	}

}
