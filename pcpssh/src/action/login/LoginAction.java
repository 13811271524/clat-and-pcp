package action.login;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import dao.UserDao;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String userpwd;
	private String userrole;
	private String result;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	
	public String getUserrole() {
		return userrole;
	}

	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	@Override
	public String execute() throws Exception {
		UserDao dao = new UserDao();
		if(dao.login(username, userpwd,userrole)){
			if(userrole.equals("admin")){
				result="ADMIN";
			}else{
				result="USER";
			}
			Map<String, Object> session = ActionContext.getContext().getSession();
	        session.put("user", username);
	        session.put("role", userrole);
		}else{
			result = "账号或密码错误！";
		}
		return SUCCESS;
	}
	
	public String goLoginpage(){
		return SUCCESS;
	}
	public String goUserPage(){
		return SUCCESS;
	}
	public String goDocPage(){
		return SUCCESS;
	}
	public String logout(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		UserDao u=new UserDao();
		u.setIpTime(session.get("user").toString(), session.get("ip").toString(), session.get("time").toString());
        session.remove("user");
        session.remove("role");
        session.remove("time");
        session.remove("ip");
		return SUCCESS;
	}
}
