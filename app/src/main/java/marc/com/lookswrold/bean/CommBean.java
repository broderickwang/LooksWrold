package marc.com.lookswrold.bean;

import java.util.List;

/**
 * Created by Broderick on 16/9/14.
 */
public class CommBean {
	/**
	 * author : 2878313650
	 * content : 想起来一个笑话，英吉利海峡要挖海底隧道，政府开始招标，看到一家公司投标只用十万英镑，就很好奇，把承包商叫过来问怎么做到的。承包商说:“很简单，我和我的合伙人各拿一把铲子，我在英国这边开始挖，他在法国开始挖，只要我们在中间汇合，你就能得到一条海底隧道。”政府主管很疑惑：“如果你们没有汇合呢？”承包商拿出两个手指，“那你将得到两条。”
	 * avatar : http://pic1.zhimg.com/6beb25450_im.jpg
	 * time : 1473815884
	 * id : 26560026
	 * likes : 1
	 */

	private List<CommentsBean> comments;

	public List<CommentsBean> getComments() {
		return comments;
	}

	public void setComments(List<CommentsBean> comments) {
		this.comments = comments;
	}

	public static class CommentsBean {
		private String author;
		private String content;
		private String avatar;
		private long time;
		private int id;
		private int likes;

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getLikes() {
			return likes;
		}

		public void setLikes(int likes) {
			this.likes = likes;
		}
	}
}
