package io.github.jadefalke.expression;

import java.util.ArrayList;
import java.util.List;

public class ExpressionTokenizer {

	private String string;

	private int index;

	public ExpressionTokenizer () {
		this.index = 0;
	}

	public List<Token> tokenize (String string) {
		this.string = string;
		index = 0;
		List<Token> tokens = new ArrayList<>();
		while (index < string.length()) {
			tokens.add(next());
		}
		return tokens;
	}

	private Token next () {
		while (string.charAt(index) == ' ') {
			index++;
		}

		if (Character.isDigit(string.charAt(index))) {
			int start = index;
			do {
				index++;
			} while (index < string.length() && Character.isDigit(string.charAt(index)));
			return new TokenDefinitions.Number(
				Integer.parseInt(string.substring(start, index))
			);
		} else if (Character.isAlphabetic(string.charAt(index))) {
			int start = index;
			do {
				index++;
			} while (index < string.length() && Character.isAlphabetic(string.charAt(index)));
			return new TokenDefinitions.Identifier(
				string.substring(start, index)
			);
		} else {
			return TokenDefinitions.Operator.of(string.charAt(index++));
		}
	}
}
