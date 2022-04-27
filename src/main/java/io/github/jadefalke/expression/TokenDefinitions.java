package io.github.jadefalke.expression;

import java.util.function.DoubleBinaryOperator;


public class TokenDefinitions {

	public enum Operator implements Token {
		ADD(Double::sum, Associativity.LEFT, 2),
		SUB((a, b) -> a - b, Associativity.LEFT, 2),
		MULT((a, b) -> a * b, Associativity.LEFT, 3),
		DIV((a, b) -> a / b, Associativity.LEFT, 3),
		POW(Math::pow, Associativity.RIGHT, 4),
		LPAREN(null, null, -1),
		RPAREN(null, null, -1);


		public final DoubleBinaryOperator op;
		public final Associativity associativity;
		public final int precedence;

		Operator (DoubleBinaryOperator op, Associativity associativity, int precedence) {
			this.op = op;
			this.associativity = associativity;
			this.precedence = precedence;
		}

		public static Token of (char identifier) {
			return switch (identifier) {
				case '+' -> ADD;
				case '-' -> SUB;
				case '*' -> MULT;
				case '/' -> DIV;
				case '^' -> POW;
				case '(' -> LPAREN;
				case ')' -> RPAREN;
				default -> throw new IllegalArgumentException("invalid operator: " + identifier);
			};
		}

		public int comparePrecedence (Operator other) {
			return Integer.compare(precedence, other.precedence);
		}

		public enum Associativity {
			LEFT,
			RIGHT
		}
	}

	public record Number (double number) implements Token {}

	public record Identifier (String str) implements Token {}
}
