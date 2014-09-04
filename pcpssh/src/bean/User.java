package bean;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * 
 * @author 用户信息�?对应数据库中的user�?
 */
public class User {

	private long id; // 主键，自�?
	private String userName;// 用户�?
	private String userPwd;// 密码(加密)

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", userPwd="
				+ userPwd + "]";
	}

	public static void main(String[] args) {

		Transaction t = null;

		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		StandardServiceRegistry sr = srb.build();
		SessionFactory sf = cfg.buildSessionFactory(sr);
		Session s = sf.openSession();
		try {
			// 准备数据
			User um = new User();
			um.setUserName("admin");
			um.setUserPwd("37343n3s3o13o1o3642s6o1i6o217ng4");
			s = sf.openSession();
			t = s.beginTransaction();
			s.save(um);
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
			s.close();
		}
	}

}
