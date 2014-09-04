package dao;

import java.util.List;

import bean.Datalist;
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
public class DatalistDao {

	/**
	 * 插入数据信息
	 * 
	 * @param user
	 */
	public void insert(Datalist datalist) {
		Transaction t = null;
		SessionFactory sf = GetSessionFactory.getInstance();
		Session s = null;
		try {
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			s.save(datalist);
			t.commit();
		} catch (Exception err) {
			t.rollback();
			err.printStackTrace();
		} finally {
		}
	}

	/**
	 * 更新数据信息
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

	public static void main(String[] args) {

		// System.out.println("res:" + checkUserNameIsAvailable("admin"));

	}

	// 得到�?��的用户数�?
	public int getTotalDatalist() {
		int res = 0;
		Session s = null;
		SessionFactory sf = null;
		Transaction t = null;
		try {
			sf = GetSessionFactory.getInstance();
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s.createQuery("from Datalist");
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
	public List<Datalist> getDatalistListByPage(int page, int rows) {
		Session s = null;
		SessionFactory sf = null;
		List<Datalist> list = null;
		Transaction t = null;
		try {
			sf = GetSessionFactory.getInstance();
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s.createQuery("from Datalist")
					.setFirstResult((page - 1) * rows).setMaxResults(rows);
			list = query.list();
			t.commit();
		} catch (Exception e) {
			System.out.println("login():" + e.getMessage());
		} finally {
		}
		return list;
	}

	// 删除文档
	public void delDatalist(String Datalist) {
		Transaction t = null;
		SessionFactory sf = GetSessionFactory.getInstance();
		Session s = null;
		try {
			s = sf.getCurrentSession();
			t = s.beginTransaction();
			Query query = s.createQuery("delete from Datalist where ipv6fix in ("
					+ Datalist + ") ");
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
}
