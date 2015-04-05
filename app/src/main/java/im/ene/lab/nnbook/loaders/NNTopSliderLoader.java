package im.ene.lab.nnbook.loaders;

import im.ene.lab.nnbook.NNHome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class NNTopSliderLoader extends AsyncTask<Void, Void, List<String>> {

	private OnContentLoadedCallback mCallback;

	public NNTopSliderLoader(OnContentLoadedCallback callback) {
		this.mCallback = callback;
	}

	public interface OnContentLoadedCallback {
		public void onLoaded(List<String> results);
	}

	@Override
	protected List<String> doInBackground(Void... params) {
		List<String> result = new ArrayList<String>();
		try {
			if (NNHome.HOME == null)
				NNHome.HOME = Jsoup.connect(NNHome.HomeURL).timeout(5000).get();

			Element topSlider = NNHome.HOME.getElementById("slider");
			Elements topSliderElements = topSlider.select("a[href");

			if (topSliderElements.size() > 0) {
				for (Element element : topSliderElements) {
					result.add(element.select("img").attr("src"));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	protected void onPostExecute(List<String> result) {
		if (mCallback != null)
			mCallback.onLoaded(result);
	}

	public void load() {
		this.execute();
	}
}
