package im.ene.lab.nnbook.models;

import java.util.ArrayList;
import java.util.List;

public class NNBookShelf {

	public String title;

	public String url;
	
	public List<NNBook> books;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<NNBook> getBooks() {
		return books;
	}

	public void setBooks(List<NNBook> books) {
		this.books = books;
	}

	public void addBook(NNBook book) {
		if (this.books == null)
			this.books = new ArrayList<NNBook>();
		
		this.books.add(book);
	}
	
}
