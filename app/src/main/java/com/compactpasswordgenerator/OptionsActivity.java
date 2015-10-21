package com.compactpasswordgenerator;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.compactpasswordgenerator.util.Options;
import com.compactpasswordgenerator.util.Utility;

public class OptionsActivity extends Activity {
	protected SeekBar seekNumberOfChars;
	protected CheckBox cbIncludeNumbers;
	protected CheckBox cbIncludeLowercase;
	protected CheckBox cbIncludeUppercase;
	protected EditText editIncludedSymbols;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_options);

		seekNumberOfChars = (SeekBar)findViewById (R.id.seek_number_char);
		cbIncludeNumbers = (CheckBox)findViewById (R.id.include_numbers);
		cbIncludeLowercase = (CheckBox)findViewById (R.id.include_lowercase);
		cbIncludeUppercase = (CheckBox)findViewById (R.id.include_uppercase);
		editIncludedSymbols = (EditText)findViewById (R.id.include_chars);

		editIncludedSymbols.clearFocus ();

		Options options = Utility.getOptions (this);
		seekNumberOfChars.setProgress (options.charCount - 4);
		cbIncludeNumbers.setChecked (options.includeNumbers);
		cbIncludeLowercase.setChecked (options.includeLowercase);
		cbIncludeUppercase.setChecked (options.includeUppercase);
		editIncludedSymbols.setText (options.includedChars);
	}

	public void saveOptions (View view) {
		boolean includeNumbers = cbIncludeNumbers.isChecked ();
		boolean includeLowercase = cbIncludeLowercase.isChecked ();
		boolean includeUppercase = cbIncludeUppercase.isChecked ();
		int charCount = seekNumberOfChars.getProgress () + 4;
		String includedSymbols = editIncludedSymbols.getText ().toString ();

		if (!(includeLowercase || includeNumbers || includeUppercase)) {
			showErrorMsg (R.string.error_select_one_option);
			return;
		}

		if (!isAllSymbols (includedSymbols)) {
			showErrorMsg (R.string.error_only_symbols);
			return;
		}

		Options options = Utility.getOptions (this);
		options.charCount = charCount;
		options.includedChars = includedSymbols;
		options.includeLowercase = includeLowercase;
		options.includeUppercase = includeUppercase;
		options.includeNumbers = includeNumbers;
		Utility.saveOptions (this, options);
		Toast.makeText (this, R.string.save_success, Toast.LENGTH_SHORT).show ();
	}

	private void showErrorMsg (@StringRes int resId) {
		AlertDialog dialog = new AlertDialog.Builder (this)
				.setTitle (R.string.error_text)
				.setMessage (resId)
				.setPositiveButton (R.string.ok_text, null)
				.create ();
		dialog.show ();
	}

	private boolean isAllSymbols (String s) {
		for (int i = 0 ; i < s.length () ; i++) {
			char c = s.charAt (i);
			if (Character.isLetterOrDigit (c)) {
				return false;
			}
		}
		return true;
	}
}
