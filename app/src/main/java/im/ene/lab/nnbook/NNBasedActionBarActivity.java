package im.ene.lab.nnbook;

import im.ene.lab.nnbook.utils.ENEUtils;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class NNBasedActionBarActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		ENEUtils.setupProxy(this);
	}
}
