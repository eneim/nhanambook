package im.ene.lab.nnbook.models;

public class NNBookInfo {

	public NNBook book;
	
	public String title;
	
	public String cover;
	
	public String info;
	
	public String intro;

	public NNBook getBook() {
		return book;
	}

	public void setBook(NNBook book) {
		this.book = book;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
}
