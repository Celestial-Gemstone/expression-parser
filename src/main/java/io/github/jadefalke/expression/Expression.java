package io.github.jadefalke.expression;


import java.util.function.DoubleBinaryOperator;

public interface Expression {

	double eval ();

	static Expression value (double val) {
		return () -> val;
	}

	static Expression compose (Expression a, Expression b, TokenDefinitions.Operator op) {
		return () -> op.op.applyAsDouble(a.eval(), b.eval());
	}
}
