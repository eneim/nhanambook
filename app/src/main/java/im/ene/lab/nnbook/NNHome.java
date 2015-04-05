package im.ene.lab.nnbook;

import android.app.Application;
import android.os.AsyncTask;

import com.crashlytics.android.Crashlytics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;

public class NNHome extends Application {

	public static final String HomeURL = "http://nhanam.com.vn";
	public static Document HOME;

	@Override
	public void onCreate() {
		super.onCreate();
		Fabric.with(this, new Crashlytics());
		getHome();
	}

	public static void getHome() {
		if (HOME == null) {
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					try {
						HOME = Jsoup.connect(HomeURL).timeout(5000).get();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}

			}.execute();
		}
	}

}
