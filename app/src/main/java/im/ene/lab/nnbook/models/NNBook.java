package im.ene.lab.nnbook.models;

import im.ene.lab.nnbook.NNHome;

public class NNBook {

	public String title;

	public String cover;

	public String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = NNHome.HomeURL + url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

}
