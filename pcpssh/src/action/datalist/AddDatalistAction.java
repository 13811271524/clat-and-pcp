package action.datalist;

import com.opensymphony.xwork2.ActionSupport;

import dao.DatalistDao;
import bean.Datalist;

public class AddDatalistAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Datalist Datalist;

	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Datalist getDatalist() {
		return Datalist;
	}

	public void setDatalist(Datalist Datalist) {
		this.Datalist = Datalist;
	}

	// 新增用户
	public String addDatalist() {
		DatalistDao dao = new DatalistDao();

		dao.insert(Datalist);
		result = "success";

		return SUCCESS;
	}

	public static void main(String[] args) {
	}

}
