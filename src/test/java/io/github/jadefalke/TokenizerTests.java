package io.github.jadefalke;

import io.github.jadefalke.expression.ExpressionTokenizer;
import io.github.jadefalke.expression.TokenDefinitions;
import io.github.jadefalke.expression.TokenDefinitions.Identifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.jadefalke.expression.TokenDefinitions.Operator.*;
import io.github.jadefalke.expression.TokenDefinitions.Number;
import static org.junit.jupiter.api.Assertions.*;

public class TokenizerTests {

	private ExpressionTokenizer tokenizer;

	@BeforeEach
	void setUp () {
		tokenizer = new ExpressionTokenizer();
	}

	@Test
	void parentheses () {
		assertEquals(
			tokenizer.tokenize("(())"),
			List.of(LPAREN, LPAREN, RPAREN, RPAREN)
		);

		assertEquals(
			tokenizer.tokenize("()()()"),
			List.of(LPAREN, RPAREN, LPAREN, RPAREN, LPAREN, RPAREN)
		);

		assertEquals(
			tokenizer.tokenize("(())()"),
			List.of(LPAREN, LPAREN, RPAREN, RPAREN, LPAREN, RPAREN)
		);
	}

	@Test
	void all () {
		assertEquals(
			tokenizer.tokenize("1 + 7x - r^3 -abc(def)            abcdefg 123"),
			List.of(
				new Number(1),
				ADD,
				new Number(7),
				new Identifier("x"),
				SUB,
				new Identifier("r"),
				POW, new Number(3),
				SUB,
				new Identifier("abc"),
				LPAREN,
				new Identifier("def"),
				RPAREN,
				new Identifier("abcdefg"),
				new Number(123)
			)
		);
	}
}
