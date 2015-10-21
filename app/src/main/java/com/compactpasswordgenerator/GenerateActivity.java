package com.compactpasswordgenerator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.compactpasswordgenerator.util.CharacterMaps;
import com.compactpasswordgenerator.util.Options;
import com.compactpasswordgenerator.util.Utility;

import java.util.Map;

public class GenerateActivity extends Activity {

	protected TextView result;
	private String currentPassword;
	private EditText dialogTextbox;
	private AlertDialog saveDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_generate);

		dialogTextbox = new EditText (this);
		dialogTextbox.setHint (R.string.password_name_hint);
		dialogTextbox.setLayoutParams (new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		CharSequence password = generatePassword ();

		result = (TextView) findViewById (R.id.tv_password);
		result.setText (password);

		saveDialog = new AlertDialog.Builder (this).setTitle (R.string.enter_password_name).setPositiveButton (R.string.save_text, new DialogInterface.OnClickListener () {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String passwordKey = GenerateActivity.this.dialogTextbox.getText ().toString ();
				int result = Utility.savePassword (passwordKey, currentPassword);
				//Log.d ("My App", currentPassword);
				if (result == Utility.SUCCESS) {
					Toast.makeText (GenerateActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show ();
				} else if (result == Utility.KEY_DUPLICATE) {
					Toast.makeText (GenerateActivity.this, R.string.duplicate_key, Toast.LENGTH_SHORT).show ();
					dialog.dismiss ();
				} else if (result == Utility.VALUE_DUPLICATE) {
					Toast.makeText (GenerateActivity.this, R.string.duplicate_password, Toast.LENGTH_SHORT).show ();
				}
			}
		}).setNegativeButton (R.string.cancel_text, null).setView (dialogTextbox).create ();
	}

	private CharSequence generatePassword() {
		Options options = Utility.getOptions (this);
		int digitCount = options.charCount;
		boolean includeDigits = options.includeNumbers;
		boolean includeLowercase = options.includeLowercase;
		boolean includeUppercase = options.includeUppercase;
		String includedChars = options.includedChars;

		StringBuilder builder = new StringBuilder ();
		for (int i = 0; i < digitCount; i++) {
			Map<Integer, Character> mapToUse = Utility.getRandomFrom (includeDigits ? CharacterMaps.getMap (CharacterMaps.DIGITS) : null, includeLowercase ? CharacterMaps.getMap (CharacterMaps.LOWERCASE) : null, includeUppercase ? CharacterMaps.getMap (CharacterMaps.UPPERCASE) : null, includedChars.length () > 0 ? Utility.formCharacterMap (includedChars) : null);
			char charToAppend = Utility.getRandomValInMap (mapToUse);
			builder.append (charToAppend);
		}
		Toast.makeText (this, R.string.success_generate_text, Toast.LENGTH_SHORT).show ();
		currentPassword = builder.toString ();
		return builder;
	}

	public void discardClick(View view) {
		String password = generatePassword ().toString ();
		result.setText (password);
	}

	public void saveClick(View view) {
		saveDialog.show ();
	}
}
