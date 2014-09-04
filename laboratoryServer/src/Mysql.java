import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Mysql {
	Connection connectionsql;
	java.sql.Statement statementsql;

	public Mysql(Connection connectionsql, java.sql.Statement statementsql) {
		this.connectionsql = connectionsql;
		this.statementsql = statementsql;
	}

	public void connectmysql() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("jia zai qu dong cheng gong");
		} catch (Exception e) {
			System.out.println("jia zai qu dong shi bai");
		}
		try {
			connectionsql = DriverManager.getConnection(
					"jdbc:mysql://localhost/pcp", "root", "123456");
			System.out.println("lian jie mysql cheng gong");
			//
			statementsql = connectionsql.createStatement();
		} catch (Exception e) {
			System.out.println("lian jie mysql shi bai");
		}
	}

	public String[] search(String ipv6fix) {
		String sql = "select * from datalist where ipv6fix= '" + ipv6fix + "'; ";
		int length = 0;
		int i;
		String[] s = null;
		try {
			ResultSet rs = statementsql.executeQuery(sql);
			// fist time compute the length
			while (rs.next()) {
				length++;
			}
			// second time ,initialize variable
			rs = statementsql.executeQuery(sql);
			s = new String[length];
			i = 0;
			while (rs.next()) {
				s[i] = rs.getString("ipv6fix") + "," + rs.getString("ipv4addr");
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public String[] searchall() {
		String sql = "select * from datalist;";
		int length = 0;
		int i;
		String[] s = null;
		try {
			ResultSet rs = statementsql.executeQuery(sql);
			// fist time compute the length
			while (rs.next()) {
				length++;
			}
			// second time ,initialize variable
			rs = statementsql.executeQuery(sql);
			s = new String[length];
			i = 0;
			while (rs.next()) {
				s[i] = rs.getString("ipv6fix") + "," + rs.getString("ipv4addr");
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}
