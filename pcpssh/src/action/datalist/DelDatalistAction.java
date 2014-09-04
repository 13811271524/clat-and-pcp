package action.datalist;

import java.util.Map;

import bean.Datalist;
import dao.DatalistDao;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DelDatalistAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Datalist Datalist;
	private String result;

	public Datalist getDatalist() {
		return Datalist;
	}

	public void setDatalist(Datalist Datalist) {
		this.Datalist = Datalist;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String deleteDatalist() {
		DatalistDao dao = new DatalistDao();
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (session.get("role").equals("admin")) {
			dao.delDatalist(Datalist.getIpv6fix());
			result = "success";
		}
		if (session.get("role").equals("user")) {
			result = "error";
		}
		return SUCCESS;
	}
}
