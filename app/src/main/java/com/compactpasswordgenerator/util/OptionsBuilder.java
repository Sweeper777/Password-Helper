package com.compactpasswordgenerator.util;

public class OptionsBuilder {
	private Options options;

	public OptionsBuilder setIncludedChars(String includedChars) {
		options.includedChars = includedChars;
		return this;
	}

	public OptionsBuilder setCharCount(int charCount) {
		options.charCount = charCount;
		return this;
	}

	public OptionsBuilder setIncludeUppercase(boolean includeUppercase) {
		options.includeUppercase = includeUppercase;
		return this;
	}

	public OptionsBuilder setIncludeLowercase(boolean includeLowercase) {
		options.includeLowercase = includeLowercase;
		return this;
	}

	public OptionsBuilder setIncludeNumbers(boolean includeNumbers) {
		options.includeNumbers = includeNumbers;
		return this;
	}

	public Options build () {
		return options;
	}

	public OptionsBuilder () {
		options = new Options ();
	}
}
