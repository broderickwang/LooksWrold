package marc.com.lookswrold.bean;

/**
 * Created by Broderick on 2017/2/21.
 */

public class ItemBean {
	private  String title;
	private  String time;
	private  boolean state;

	public ItemBean(String time, String title) {
		this.time = time;
		this.title = title;
	}

	public ItemBean(boolean state, String time, String title) {
		this.state = state;
		this.time = time;
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}
