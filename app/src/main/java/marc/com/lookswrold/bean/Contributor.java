package marc.com.lookswrold.bean;

/**
 * Created by Broderick on 16/9/12.
 */
public class Contributor {
	String login;
	int contributions;
	String avatar_url;
	String html_url;

	public String getAvatar_url() {
		return avatar_url;
	}

	public int getContributions() {
		return contributions;
	}

	public String getLogin() {
		return login;
	}

	public String getHtml_url() {
		return html_url;
	}
}
