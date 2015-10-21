package com.compactpasswordgenerator.util;

import com.orm.SugarRecord;

public class Options extends SugarRecord<Options> {
	public static final String SP_KEY_CHAR_COUNT = "charCount";
	public static final String SP_KEY_INCLUDED_CHARS = "includedChars";
	public static final String SP_KEY_INCLUDE_LOWERCASE = "includeLowercase";
	public static final String SP_KEY_INCLUDE_UPPERCASE = "includeUppercase";
	public static final String SP_KEY_INCLUDE_NUMBERS = "includeNumbers";
	public boolean includeNumbers;
	public boolean includeLowercase;
	public boolean includeUppercase;
	public int charCount;
	public String includedChars;

	public Options () {}
}
