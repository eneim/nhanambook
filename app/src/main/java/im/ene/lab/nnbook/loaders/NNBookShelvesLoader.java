package im.ene.lab.nnbook.loaders;

import im.ene.lab.nnbook.NNHome;
import im.ene.lab.nnbook.models.NNBook;
import im.ene.lab.nnbook.models.NNBookShelf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class NNBookShelvesLoader extends
		AsyncTask<String, Void, List<NNBookShelf>> {

	private OnBooksLoadedCallback mCallback;

	public NNBookShelvesLoader(OnBooksLoadedCallback callback) {
		this.mCallback = callback;
	}

	public interface OnBooksLoadedCallback {
		public void shelvesLoaded(List<NNBookShelf> shelves);
	}

	@Override
	protected List<NNBookShelf> doInBackground(String... params) {
		List<NNBookShelf> result = new ArrayList<NNBookShelf>();

		try {
			final Document doc;

			if (params != null && params.length > 0) {
				doc = Jsoup.connect(params[0]).timeout(5000).get();
			} else {
				if (NNHome.HOME == null) {
					NNHome.HOME = Jsoup.connect(NNHome.HomeURL).timeout(5000)
							.get();
				}

				doc = NNHome.HOME;
			}

			Element container = doc.getElementsByClass("container").first();

			Elements e_pagetitle = container.getElementsByClass("pagetitle");
			Elements e_shelflist = container.select("ul.listbook.clearfix");

			int titles_size = e_pagetitle.size();
			int shelves_size = e_shelflist.size();

			if (titles_size > shelves_size)
				titles_size = shelves_size;
			shelves_size = Math.min(titles_size, shelves_size);

			if (shelves_size > 0) {
				for (int i = 0; i < shelves_size; i++) {
					NNBookShelf shelf = new NNBookShelf();

					Element e_title = e_pagetitle.get(i);
					shelf.setTitle(e_title.text());

					Element e_shelf = e_shelflist.get(i);

					Elements e_books = e_shelf.select("li.book.bookimage0");
					if (e_books != null && e_books.size() > 0) {
						for (int j = 0; j < e_books.size(); j++) {
							NNBook book = new NNBook();
							Element e_book = e_books.get(j);
							book.setCover(e_book.select("img").attr("src"));
							book.setTitle(e_book.select("a[title]").text());
							book.setUrl(e_book.select("a").attr("href"));
							
							shelf.addBook(book);
						}
					}

					result.add(shelf);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	protected void onPostExecute(List<NNBookShelf> result) {
		if (mCallback != null)
			mCallback.shelvesLoaded(result);
	}

}
