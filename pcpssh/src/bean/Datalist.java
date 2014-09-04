package bean;

/**
 * 
 * @author 用户信息�?对应数据库中的user�?
 */
public class Datalist {

	private long id; // 主键，自�?
	private String Ipv6fix;// 用户�?
	private String Ipv4addr;// 密码(加密)
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getIpv6fix() {
		return Ipv6fix;
	}

	public void setIpv6fix(String Ipv6fix) {
		this.Ipv6fix = Ipv6fix;
	}

	public String getIpv4addr() {
		return Ipv4addr;
	}

	public void setIpv4addr(String Ipv4addr) {
		this.Ipv4addr = Ipv4addr;
	}

	@Override
	public String toString() {
		return "Datalist [id=" + id + ",Ipv6fix=" + Ipv6fix + ", Ipv4addr=" + Ipv4addr + "]";
	}

}
