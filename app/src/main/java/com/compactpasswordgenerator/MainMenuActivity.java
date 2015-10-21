package com.compactpasswordgenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_main_menu);
	}

	public void quitClick (View view) {
		finish ();
	}

	public void generateClick (View view) {
		startActivity (new Intent (this, GenerateActivity.class));
	}

	public void optionsClick (View view) {
		startActivity (new Intent (this, OptionsActivity.class));
	}

	public void passwordsClick (View view) {
		startActivity (new Intent (this, PasswordActivity.class));
	}
}
