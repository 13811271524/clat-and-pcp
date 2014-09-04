package dao;

import java.util.List;

import bean.User;
import sessionfactory.GetSessionFactory;
import utils.MD5;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * User的数据库操作�?
 * 
 * @author song
 * 
 */
public class UserDao {

	/**
	 * 验证用户名是否被占用
	 * 
	 * @param userName
	 *            用户�?
	 * @return false 未被占用
	 */
	public boolean checkUserNameIsAvailable(String userName) {
		boolean res = true;
		Session s = null;
		SessionFactory sf = null;
		Transaction t = null;
		try {
			sf = GetSessionFactory.getInstance();
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s.createQuery("from User where userName=:userName");
			query.setString("userName", userName);
			List<User> list = query.list();
			if (list != null && list.size() == 0) {
				res = false;
			}
			t.commit();
		} catch (Exception e) {
			System.out.println("checkUserNameIsAvailable():" + e.getMessage());
		} finally {
		}
		return res;
	}

	/**
	 * 插入�?��用户信息
	 * 
	 * @param user
	 */
	public void insert(User user) {
		Transaction t = null;
		SessionFactory sf = GetSessionFactory.getInstance();
		Session s = null;
		try {
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			user.setUserPwd(MD5.getMd5(user.getUserPwd()));
			s.save(user);
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
		}
	}

	/**
	 * 更新�?��用户信息
	 * 
	 * @param user
	 */
	public void update(User user) {
		Transaction t = null;
		SessionFactory sf = GetSessionFactory.getInstance();
		Session s = null;
		try {
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s
					.createQuery("update User  set userRole=:userrole , menuId=:menuid where id =:mid ");
			query.setLong("mid", user.getId());
			query.executeUpdate();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
		}
	}

	public boolean login(String userName, String userPwd, String userRole) {
		boolean res = false;
		Session s = null;
		SessionFactory sf = null;
		Transaction t = null;
		try {
			sf = GetSessionFactory.getInstance();
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s
					.createQuery("from User where userName=:userName and userPwd=:userPwd");
			query.setString("userName", userName);
			query.setString("userPwd", MD5.getMd5(userPwd));
			List<User> list = query.list();
			if (list != null && list.size() == 1) {
				res = true;
			}
			t.commit();
		} catch (Exception e) {
			System.out.println("login():" + e.getMessage());
		} finally {
		}
		return res;
	}

	public static void main(String[] args) {

		// System.out.println("res:" + checkUserNameIsAvailable("admin"));

	}

	// 得到�?��的用户数�?
	public int getTotalUser() {
		int res = 0;
		Session s = null;
		SessionFactory sf = null;
		Transaction t = null;
		try {
			sf = GetSessionFactory.getInstance();
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s.createQuery("from User");
			List list = query.list();
			res = list.size();
			t.commit();
		} catch (Exception e) {
			System.out.println("login():" + e.getMessage());
		} finally {
			// if (s != null) {
			// s.close();
			// }
		}
		return res;
	}

	// 得到某页的用户list
	public List<User> getUserListByPage(int page, int rows) {
		Session s = null;
		SessionFactory sf = null;
		List<User> list = null;
		Transaction t = null;
		try {
			sf = GetSessionFactory.getInstance();
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s.createQuery("from User")
					.setFirstResult((page - 1) * rows).setMaxResults(rows);
			list = query.list();
			t.commit();
		} catch (Exception e) {
			System.out.println("login():" + e.getMessage());
		} finally {
		}
		return list;
	}

	// 根据用户名，修改用户标标志位(将用户放入回收站或恢�?
	public void setFlag(String userName, int flag) {

		Transaction t = null;
		SessionFactory sf = GetSessionFactory.getInstance();
		Session s = null;
		try {
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s
					.createQuery("update User  set flag=:flag where userName =:userName ");
			query.setInteger("flag", flag);
			query.setString("userName", userName);
			query.executeUpdate();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
		}
	}

	// 根据用户名，修改用户标标志位(将用户放入回收站或恢�?，批量操�?
	public void setFlagBatch(String userName, int flag) {

		Transaction t = null;
		SessionFactory sf = GetSessionFactory.getInstance();
		Session s = null;
		try {
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s
					.createQuery("update User  set flag=:flag where userName in ("
							+ userName + ") ");
			query.setInteger("flag", flag);
			query.executeUpdate();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
		}
	}

	// 根据用户名重置密码
	public void resetPwd(String userNames) {
		Transaction t = null;
		SessionFactory sf = GetSessionFactory.getInstance();
		Session s = null;
		try {
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s
					.createQuery("update User  set userPwd=:userPwd where userName in ("
							+ userNames + ") ");
			query.setString("userPwd", MD5.getMd5("123456"));
			query.executeUpdate();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
		}
	}

	// 根据用户名记录该用户的登录时间和ip地址
	public void setIpTime(String user, String ip, String time) {
		Transaction t = null;
		SessionFactory sf = GetSessionFactory.getInstance();
		Session s = null;
		try {
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s
					.createQuery("update User set lastLogTime=:lastlogtime,lastLogIp=:lastlogip where userName=:username");
			query.setString("lastlogtime", time);
			query.setString("lastlogip", ip);
			query.setString("username", user);
			query.executeUpdate();
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {

		}
	}
}
