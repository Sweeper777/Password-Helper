package com.compactpasswordgenerator.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Utility {

	public static final String PASSWORD_KEYS = "passwordKeys";
	public static final String PASSWORD_VALUES = "passwordValues";

	private Utility () {}

	@IntDef ({SUCCESS, KEY_DUPLICATE, VALUE_DUPLICATE})
	public @interface SaveStatusCode {}

	public static final int SUCCESS = 0;
	public static final int KEY_DUPLICATE = -1;
	public static final int VALUE_DUPLICATE = -2;

	private static SecureRandom random = new SecureRandom ();

	public static <T> T getRandomFrom (T... values) {
		ArrayList<T> valuesWithoutNull = new ArrayList<> ();
		for (T value : values) {
			if (value != null)
				valuesWithoutNull.add (value);
		}
		int randomNumber = random.nextInt (valuesWithoutNull.size ());
		return valuesWithoutNull.get (randomNumber);
	}

	public static <K, V> V getRandomValInMap (Map<K, V> map) {
		Collection<V> values = map.values ();

		Iterator<V> it = values.iterator();
		int choosen = random.nextInt (values.size ());
		for (int i=0; i<values.size();i++) {
			V value = it.next();
			if (i==choosen) return value;
		}
		throw new AssertionError("Choosen value out of bounds");
	}

	public static Map<Integer, Character> formCharacterMap (CharSequence chars) {
		HashMap<Integer, Character> map = new HashMap<> ();
		for (int i = 0 ; i < chars.length () ; i++) {
			map.put (i, chars.charAt (i));
		}
		return map;
	}

	@SaveStatusCode
	public static int savePassword (String passwordKey, String passwordValue) {
		com.compactpasswordgenerator.util.Password password = new com.compactpasswordgenerator.util.Password (passwordKey, passwordValue);
		List<com.compactpasswordgenerator.util.Password> duplicateNames = com.compactpasswordgenerator.util.Password.find (com.compactpasswordgenerator.util.Password.class, "name = ?", passwordKey);
		List<com.compactpasswordgenerator.util.Password> duplicateValues = com.compactpasswordgenerator.util.Password.find (com.compactpasswordgenerator.util.Password.class, "value = ?", passwordValue);
		if (!duplicateNames.isEmpty ()) {
			return KEY_DUPLICATE;
		}

		if (!duplicateValues.isEmpty ()) {
			return VALUE_DUPLICATE;
		}
		password.save ();
		return SUCCESS;
	}

	public static void removePassword (String passwordKey) {
		List<com.compactpasswordgenerator.util.Password> passwordList = com.compactpasswordgenerator.util.Password.find (com.compactpasswordgenerator.util.Password.class, "name = ?", passwordKey);
		if (passwordList.size () == 1) {
			passwordList.get (0).delete ();
		} else if (passwordList.size () > 1) {
			throw new AssertionError ();
		}
	}

	public static HashMap<String, String> getPasswords () {
		HashMap<String, String> map = new HashMap<> ();
		List<com.compactpasswordgenerator.util.Password> allPasswords = com.compactpasswordgenerator.util.Password.listAll (com.compactpasswordgenerator.util.Password.class);
		for (com.compactpasswordgenerator.util.Password password : allPasswords) {
			map.put (password.name, password.value);
		}
		return map;
	}

	public static Options getOptions (Context c) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (c);
		return new com.compactpasswordgenerator.util.OptionsBuilder ().setCharCount (prefs.getInt (com.compactpasswordgenerator.util.Options.SP_KEY_CHAR_COUNT, 8))
				.setIncludedChars (prefs.getString (com.compactpasswordgenerator.util.Options.SP_KEY_INCLUDED_CHARS, ""))
				.setIncludeLowercase (prefs.getBoolean (com.compactpasswordgenerator.util.Options.SP_KEY_INCLUDE_LOWERCASE, true))
				.setIncludeUppercase (prefs.getBoolean (com.compactpasswordgenerator.util.Options.SP_KEY_INCLUDE_UPPERCASE, false))
				.setIncludeNumbers (prefs.getBoolean(com.compactpasswordgenerator.util.Options.SP_KEY_INCLUDE_NUMBERS, true)).build ();
	}

	public static void saveOptions (Context c, Options options) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences (c).edit ();
		editor.putInt (com.compactpasswordgenerator.util.Options.SP_KEY_CHAR_COUNT, options.charCount)
				.putString (com.compactpasswordgenerator.util.Options.SP_KEY_INCLUDED_CHARS, options.includedChars)
				.putBoolean (com.compactpasswordgenerator.util.Options.SP_KEY_INCLUDE_LOWERCASE, options.includeLowercase)
				.putBoolean (com.compactpasswordgenerator.util.Options.SP_KEY_INCLUDE_UPPERCASE, options.includeUppercase)
				.putBoolean (com.compactpasswordgenerator.util.Options.SP_KEY_INCLUDE_NUMBERS, options.includeNumbers)
				.apply ();
	}
}
