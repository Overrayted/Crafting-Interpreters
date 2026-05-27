package com.craftinginterpreters.lox;

class RpnPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }
    @Override
    public String visitBinaryExpr(Expr.Binary expr) {

        StringBuilder builder = new StringBuilder();
        builder.append(expr.left.accept(this));
        builder.append(" ");
        builder.append(expr.right.accept(this));
        builder.append(" ");
        builder.append(expr.operator.lexeme);

        return builder.toString();
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return expr.expression.accept(this);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        StringBuilder builder = new StringBuilder();

        builder.append(expr.right.accept(this));
        builder.append(" ");
        builder.append(expr.operator.lexeme);

        return builder.toString();
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }


        return builder.toString();
    }

    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
                new Expr.Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123)),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(
                        new Expr.Literal(45.67)));

        System.out.println(new RpnPrinter().print(expression));
    }
}