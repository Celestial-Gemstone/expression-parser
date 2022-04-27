package io.github.jadefalke;

import java.util.Set;

public enum Command {
	QUIT("quit", "q"),
	EVAL("eval", "e");

	private final Set<String> identifiers;

	Command (String... identifiers) {
		this.identifiers = Set.of(identifiers);
	}

	public static Command fromString (String str) {
		for (Command c : values()) {
			if (c.identifiers.contains(str)) {
				return c;
			}
		}
		throw new IllegalArgumentException(str + " is not a valid command-name");
	}
}
