package im.ene.lab.nnbook.loaders;

import im.ene.lab.nnbook.models.NNBook;
import im.ene.lab.nnbook.models.NNBookInfo;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class NNBookItemInfoLoader extends AsyncTask<NNBook, Void, NNBookInfo> {

	public interface OnBookInfoLoadedCallback {
		void onBookInfoLoaded(NNBookInfo bookInfo);
	}

	private OnBookInfoLoadedCallback mCallback;

	public NNBookItemInfoLoader(OnBookInfoLoadedCallback callback) {
		this.mCallback = callback;
	}

	@Override
	protected NNBookInfo doInBackground(NNBook... params) {
		if (params == null || params.length == 0)
			return null;

		NNBook book = params[0];
		String bookUrl = book.getUrl();
		if (bookUrl == null)
			return null;

		try {
			NNBookInfo bookInfo = new NNBookInfo();
			bookInfo.setBook(book);
			bookInfo.setCover(book.getCover());
			bookInfo.setTitle(book.getTitle());

			final Document doc = Jsoup.connect(bookUrl).timeout(5000).get();

			Elements wrappers = doc.getElementsByClass("bookdetailwrap");
			Elements e_info = wrappers.select("div.info > div.intro.clearfix");
			Elements attributes = e_info.select("div.attributes");

			Elements uls = attributes.select("ul > li");

			String info = "";
			for (Element ul : uls) {
				info += ul.text() + "\n";
			}

			bookInfo.setInfo(info);

			Elements e_intro = wrappers.select("div.bookdetailblockcontent > article");
			bookInfo.setIntro(e_intro.text());

			return bookInfo;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(NNBookInfo result) {
		if (this.mCallback != null)
			mCallback.onBookInfoLoaded(result);
		else
			super.onPostExecute(result);
	}

}
