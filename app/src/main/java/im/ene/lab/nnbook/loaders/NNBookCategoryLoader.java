package im.ene.lab.nnbook.loaders;

import im.ene.lab.nnbook.NNHome;
import im.ene.lab.nnbook.loaders.NNBookShelvesLoader.OnBooksLoadedCallback;
import im.ene.lab.nnbook.models.NNBookShelf;
import im.ene.lab.nnbook.models.NNCategory;
import im.ene.lab.nnbook.models.NNCategoryHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class NNBookCategoryLoader extends
		AsyncTask<String, NNCategory, List<NNCategory>> {

	private final Object LOCK = NNBookCategoryLoader.this;

	public interface OnCategoriesLoadedCallback {
		void onCategoryItemLoaded(NNCategory category);

		void onCategoriesLoaded(List<NNCategory> result);
	}

	private OnCategoriesLoadedCallback mCallback;

	public NNBookCategoryLoader(OnCategoriesLoadedCallback callback) {
		this.mCallback = callback;
	}

	@Override
	protected List<NNCategory> doInBackground(String... params) {
		List<NNCategory> result = new ArrayList<NNCategory>();

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

			Element container = doc.getElementById("mainnav");
			Elements wrappers = container.getElementsByClass("wrapper");

			Elements cat_list = wrappers.select("ul.menu.clearfix > li")
					.select("ul.submenu > li");

			if (cat_list == null || cat_list.size() == 0)
				return result;

			for (int i = 0; i < cat_list.size(); i++) {
				NNCategory category = new NNCategory();

				Element e_cat = cat_list.get(i);

				NNCategoryHeader parentHead = new NNCategoryHeader();
				parentHead.setTitle(e_cat.select("a").first().text());
				parentHead.setUrl(e_cat.select("a").first().attr("href"));

				category.setParentHead(parentHead);

				List<NNCategoryHeader> childHeads = new ArrayList<NNCategoryHeader>();
				Elements e_childHeads = e_cat.select("ul.submenu2 > li");

				for (Element e_childHead : e_childHeads) {
					NNCategoryHeader child = new NNCategoryHeader();
					child.setTitle(e_childHead.select("a").first().text());
					child.setUrl(e_childHead.select("a").first().attr("href"));
					
					childHeads.add(child);
				}

				category.setChildHeads(childHeads);
				
				synchronized (LOCK) {
					publishProgress(category);
				}

				result.add(category);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	protected void onProgressUpdate(final NNCategory... values) {
		if (values == null || values.length == 0)
			return;

		String url = values[0].getParentHead().url;
		new NNBookShelvesLoader(new OnBooksLoadedCallback() {

			@Override
			public void shelvesLoaded(List<NNBookShelf> shelves) {
				values[0].setParentBookShelves(shelves);

				if (mCallback != null)
					mCallback.onCategoryItemLoaded(values[0]);
			}
		}).execute(url);

	}

	@Override
	protected void onPostExecute(List<NNCategory> result) {
		if (this.mCallback != null)
			this.mCallback.onCategoriesLoaded(result);
		else
			super.onPostExecute(result);
	}
}
