package io.github.jadefalke.expression;

public sealed interface Token
	permits TokenDefinitions.Identifier, TokenDefinitions.Number, TokenDefinitions.Operator {}
