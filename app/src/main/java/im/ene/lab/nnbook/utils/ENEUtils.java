package im.ene.lab.nnbook.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;

public class ENEUtils {

	private static final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

	private static final Gson gson = new Gson();

	private ENEUtils() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static Point getFullDisplaySize(Context context) {
		Point point = new Point();

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		Display display = wm.getDefaultDisplay();
		Method mGetRawH = null, mGetRawW = null;

		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
			display.getRealMetrics(outMetrics);
			point.x = outMetrics.widthPixels;
			point.y = outMetrics.heightPixels;
		} else {
			try {
				mGetRawH = Display.class.getMethod("getRawHeight");
				mGetRawW = Display.class.getMethod("getRawWidth");
				point.x = (Integer) mGetRawW.invoke(display);
				point.y = (Integer) mGetRawH.invoke(display);
			} catch (Exception e) {
				display.getMetrics(outMetrics);
				point.x = display.getWidth();
				point.y = display.getHeight();
				e.printStackTrace();
			}
		}

		return point;
	}

	public static int getNaviBarHeight(Context context) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			return resources.getDimensionPixelSize(resourceId);
		}
		return 0;
	}

	public static int getStatusBarHeight(Context context) {
		Resources resources = context.getResources();

		int result = 0;
		int resourceId = resources.getIdentifier("status_bar_height", "dimen",
				"android");
		if (resourceId > 0) {
			result = resources.getDimensionPixelSize(resourceId);
		}

		return result;
	}

	public static float getPixelFromDP(Context context, float dp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}

	@SuppressWarnings("deprecation")
	public static void setupProxy(Context context) {
		String proxyAddress;
		int proxyPort;

		boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

		if (IS_ICS_OR_LATER) {
			proxyAddress = System.getProperty("http.proxyHost");
			String portStr = System.getProperty("http.proxyPort");
			proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
		} else {
			proxyAddress = android.net.Proxy.getHost(context);
			proxyPort = android.net.Proxy.getPort(context);
		}

		if (-1 != proxyPort) {
			Ion.getDefault(context).configure().proxy(proxyAddress, proxyPort);
		}
	}

	@SuppressLint("NewApi")
	public static void setViewDimens(Context context, final ViewGroup parent,
			final View view, final int width, final int height) {
		parent.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						LayoutParams params = view.getLayoutParams();
						if (params == null) {
							params = new LayoutParams(width, height);
							view.setLayoutParams(params);
						} else {
							params.width = width;
							params.height = height;
						}

						// remove OnGlobalLayoutListener, check Android version
						// too
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
							parent.getViewTreeObserver()
									.removeOnGlobalLayoutListener(this);
						else
							parent.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
					}
				});
	}

	public static void saveObjectToFile(Context context, Object object,
			String fileName) {
		if (object == null)
			return;

		// String fileName = "_" + System.nanoTime() + "_"
		// + object.getClass().getSimpleName();
		String obsJson = gson.toJson(object);

		File outFile = new File(getDataDir(context).toString() + "/" + fileName);

		try {
			FileOutputStream fos = new FileOutputStream(outFile);
			OutputStreamWriter out = new OutputStreamWriter(fos);
			out.write(obsJson);
			out.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File getDataDir(Context context) {
		File file = new File(Environment.getExternalStorageDirectory(),
				context.getPackageName() + "/data");
		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}
	
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line).append("\n");
	    }
	    reader.close();
	    return sb.toString();
	}

	public static String getStringFromFile(String filePath) throws Exception {
	    File fl = new File(filePath);
	    FileInputStream fin = new FileInputStream(fl);
	    String ret = convertStreamToString(fin);
	    //Make sure you close all streams.
	    fin.close();        
	    return ret;
	}
}
