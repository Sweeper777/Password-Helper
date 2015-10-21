package com.compactpasswordgenerator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.compactpasswordgenerator.util.Utility;

import java.util.HashMap;
import java.util.Map;

public class PasswordActivity extends Activity {
	protected LinearLayout parent;
	private HashMap<String, String> passwords;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_password);

		parent = (LinearLayout)findViewById (R.id.parent_layout);

		loadPasswords ();
	}

	private void loadPasswords() {
		passwords = Utility.getPasswords ();

		if (!passwords.isEmpty ()) {
			for (Map.Entry<String, String> entry : passwords.entrySet ()) {
				displayAPassword (entry.getKey (), entry.getValue ());
			}
		} else {
			showNoPasswordsMsg ();
		}
	}

	private void showNoPasswordsMsg () {
		Resources r = getResources ();
		TextView text = new TextView (this);
		text.setText (R.string.no_passwords);
		//Log.d ("My App", r.getString (R.string.no_passwords));
		text.setTextSize (TypedValue.COMPLEX_UNIT_PX, r.getDimension (R.dimen.prompt_size));
		//Log.d ("My App", "Text size: " + r.getDimension (R.dimen.prompt_size));

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int mrgn = (int)r.getDimension (R.dimen.seperator);
		params.setMargins (0, mrgn, 0, mrgn);
		text.setLayoutParams (params);
		parent.addView (text);
	}

	private void displayAPassword (final String key, String value) {
		Resources r = getResources ();

		//create parent layout manager
		LinearLayout entryParent = new LinearLayout (this);
		LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
		int margin = (int)r.getDimension (R.dimen.button_margin);
		parentParams.setMargins (margin, margin, margin, margin);
		entryParent.setLayoutParams (parentParams);
		entryParent.setOrientation (LinearLayout.HORIZONTAL);

		//create text view
		TextView tvPassword = new TextView (this);
		tvPassword.setLayoutParams (new LinearLayout.LayoutParams (0, ViewGroup.LayoutParams.WRAP_CONTENT, 3));
		tvPassword.setText (key + ":\n" + value);
		tvPassword.setTextSize (TypedValue.COMPLEX_UNIT_PX, r.getDimension (R.dimen.button_text_size));

		entryParent.addView (tvPassword);

		//create delete button
		Button btnDelete = new Button (this);
		btnDelete.setLayoutParams (new LinearLayout.LayoutParams (0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
		btnDelete.setText (R.string.delete_text);
		btnDelete.setBackgroundColor (r.getColor (R.color.delete_button_bg));
		btnDelete.setTextColor (Color.WHITE);
		btnDelete.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder (PasswordActivity.this);
				builder.setMessage (R.string.delete_confirm)
						.setNegativeButton (R.string.cancel_text, null)
						.setPositiveButton (R.string.delete_text, new DialogInterface.OnClickListener () {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Utility.removePassword (key);

								PasswordActivity.this.parent.removeAllViewsInLayout ();
								PasswordActivity.this.loadPasswords ();
							}
						})
						.setTitle (R.string.delete_text)
						.create ().show ();
			}
		});

		entryParent.addView (btnDelete);

		parent.addView (entryParent);
	}
}
