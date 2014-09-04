package action.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import bean.User;
import dao.UserDao;

import com.opensymphony.xwork2.ActionSupport;

public class QueryUserAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JSONObject result;// 返回的json

	private String rows;// 每页显示的记录数

	private String page;// 当前第几�?

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	//查询在用的用�?
	public String listUser() {
		
		JsonConfig jsonConfig = new JsonConfig();   //JsonConfig是net.sf.json.JsonConfig中的这个，为固定写法  
		// 当前�?
		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页显示条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10"
				: rows);
		UserDao dao = new UserDao();
		List<User> list = dao.getUserListByPage(intPage, number);// 每页的数据，放入list
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", dao.getTotalUser());// total�?存放总记录数，必须的
		jsonMap.put("rows", list);// rows�?存放每页记录 list
		result = JSONObject.fromObject(jsonMap, jsonConfig);// 格式化result �?��要是JSONObject
		return SUCCESS;
	}
}
