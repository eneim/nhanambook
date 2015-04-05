package im.ene.lab.nnbook.models;

import java.io.Serializable;

import android.content.Context;


public class NNCore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private NNCore() {
		
	}

	private static class SingletonHolder {
		public static final NNCore INSTANCE = new NNCore();
	}
	
	public static NNCore getInstance(Context context) {
		
		return SingletonHolder.INSTANCE;
	}
}
