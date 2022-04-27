package io.github.jadefalke.expression;

import java.util.*;

public class ExpressionParser {

	public Expression parse (String expression) {
		var tokenizer = new ExpressionTokenizer();
		return shuntingYard(tokenizer.tokenize(expression));
	}

	private Expression shuntingYard (List<Token> tokens) {
		final Stack<TokenDefinitions.Operator> operatorStack = new Stack<>();
		final Stack<Expression> operandStack = new Stack<>();

		main:
		for (var token : tokens) {
			switch (token) {
				case (TokenDefinitions.Operator op) -> {
					switch (op) {
						case LPAREN -> operatorStack.push(TokenDefinitions.Operator.LPAREN);
						case RPAREN -> {
							while(!operatorStack.isEmpty()) {
								var operator = operatorStack.pop();
								if (operator == TokenDefinitions.Operator.LPAREN) {
									continue main;
								} else {
									var r = operandStack.pop();
									var l = operandStack.pop();
									operandStack.push(Expression.compose(l, r, operator));
								}
							}
							throw new IllegalStateException("Unbalanced right parentheses");
						}
						default -> {
							TokenDefinitions.Operator o2;
							while(!operatorStack.isEmpty() && null != (o2 = operatorStack.peek())) {
								if((op.associativity == TokenDefinitions.Operator.Associativity.LEFT &&
									0 == op.comparePrecedence(o2)) ||
									op.comparePrecedence(o2) < 0) {
									operatorStack.pop();
									var r = operandStack.pop();
									var l = operandStack.pop();
									operandStack.push(Expression.compose(l, r, o2));
								} else {
									break;
								}
							}
							operatorStack.push((TokenDefinitions.Operator) token);
						}
					}
				}
				case (TokenDefinitions.Number num) -> operandStack.push(Expression.value(num.number()));
				case (TokenDefinitions.Identifier identifier) -> {
					throw new IllegalArgumentException();
				}
			}
		}
		while(!operatorStack.isEmpty()) {
			var r = operandStack.pop();
			var l = operandStack.pop();
			operandStack.push(Expression.compose(l, r, operatorStack.pop()));
		}
		return operandStack.pop();
	}
}
