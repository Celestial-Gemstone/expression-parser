package io.github.jadefalke;

import io.github.jadefalke.expression.ExpressionParser;

import java.util.Scanner;

public class ExpressionParserApplication {

	public static void main (String[] args) {
		launchShell();
	}

	private static void launchShell () {

		Scanner in = new Scanner(System.in);
		ExpressionParser parser = new ExpressionParser();

		while (true) {
			System.out.print("> ");
			double result = parser.parse(in.nextLine()).eval();
			System.out.println(result);
		}
	}

	private static boolean evaluate (Command command) {
		return command == Command.QUIT;
	}
}
