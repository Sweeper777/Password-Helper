package com.compactpasswordgenerator.util;

import android.support.annotation.IntDef;

import java.util.HashMap;

public final class CharacterMaps {
	private CharacterMaps () {}

	@IntDef ({LOWERCASE, UPPERCASE, DIGITS})
	public @interface Maps {}

	private static HashMap<Integer, Character> lowercaseMap;
	private static HashMap<Integer, Character> uppercaseMap;
	private static HashMap<Integer, Character> digitsMap;

	public static final int LOWERCASE = 0;
	public static final int UPPERCASE = 1;
	public static final int DIGITS = 2;

	static {
		lowercaseMap = new HashMap<> ();
		char c = 'a';
		for (int i = 0 ; i < 26 ; i++) {
			lowercaseMap.put (i, c);
			c = getNextChar (c);
		}

		uppercaseMap = new HashMap<> ();
		c = 'A';
		for (int i = 0 ; i < 26 ; i++) {
			uppercaseMap.put (i, c);
			c = getNextChar (c);
		}

		digitsMap = new HashMap<> ();
		for (int i = 0 ; i < 10 ; i++) {
			digitsMap.put (i, getIntChar (i));
		}
	}

	private static char getIntChar(int i) {
		return Integer.toString (i).charAt (0);
	}

	private static char getNextChar(int c) {
		return (char)(c + 1);
	}

	public static HashMap<Integer, Character> getMap (@Maps int map) {
		switch (map) {
			case LOWERCASE:
				return lowercaseMap;
			case UPPERCASE:
				return uppercaseMap;
			case DIGITS:
				return digitsMap;
			default:
				throw new IllegalArgumentException (
						"The parameter 'map' is not valid for a value of " + map);
		}
	}
}
