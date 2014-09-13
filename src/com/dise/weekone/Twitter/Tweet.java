package com.dise.weekone.Twitter;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Tweet {



	@SerializedName("created_at")
	private String DateCreated;

	@SerializedName("id")
	private String Id;

	@SerializedName("text")
	private String Text;

	@SerializedName("in_reply_to_status_id")
	private String InReplyToStatusId;

	@SerializedName("in_reply_to_user_id")
	private String InReplyToUserId;

	@SerializedName("in_reply_to_screen_name")
	private String InReplyToScreenName;

	@SerializedName("user")
	private TwitterUser User;

	@SerializedName("entities")
	private Entities Entities;

	public class Entities {

		@SerializedName("urls")
		private List<Urls> Urls;

		public class Urls {

			@SerializedName("expanded_url")
			private String ExpandedUrl;

			@SerializedName("url")
			private String Url;
			
			@SerializedName("display_url")
			private String DisplayUrl;
			
			@SerializedName("indices")
			private String Indices;
						
			public void setExpandedUrl(String expandedUrl) {

				ExpandedUrl = expandedUrl;
			}

			public void setUrl(String url) {

				Url = url;
			}
			
			public String getExpandedUrl() {

				return ExpandedUrl;
			}

			public String getUrl() {

				return Url;
			}

		}

		public void setUrls(List<Urls> urls) {

			Urls = urls;
		}

		public List<Urls> getUrls() {

			return Urls;
		}

	}
	
	public String getDateCreated() {
		return DateCreated;
	}

	public String getId() {
		return Id;
	}

	public Entities getEntities() {
		return Entities;
	}

	public String getInReplyToScreenName() {
		return InReplyToScreenName;
	}

	public String getInReplyToStatusId() {
		return InReplyToStatusId;
	}

	public String getInReplyToUserId() {
		return InReplyToUserId;
	}

	public String getText() {
		return Text;
	}

	public void setDateCreated(String dateCreated) {
		DateCreated = dateCreated;
	}

	public void setId(String id) {

		Id = id;
	}

	public void setEntities(Entities entities) {

		Entities = entities;

	}

	public void setInReplyToScreenName(String inReplyToScreenName) {
		InReplyToScreenName = inReplyToScreenName;
	}

	public void setInReplyToStatusId(String inReplyToStatusId) {
		InReplyToStatusId = inReplyToStatusId;
	}

	public void setInReplyToUserId(String inReplyToUserId) {
		InReplyToUserId = inReplyToUserId;
	}

	public void setText(String text) {
		Text = text;
	}

	public void setUser(TwitterUser user) {
		User = user;
	}

	public TwitterUser getUser() {
		return User;
	}

	@Override
	public String toString() {
		return getText();
	}
}