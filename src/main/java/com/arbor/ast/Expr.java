package com.arbor.ast;

import com.arbor.ast.expression.*;

public abstract class Expr {
    public interface Visitor<R> {
        R visitAssignExpr(AssignExpr expr);
        R visitBinaryExpr(BinaryExpr expr);
        R visitCallExpr(CallExpr expr);
        R visitGroupingExpr(GroupingExpr expr);
        R visitLiteralExpr(LiteralExpr expr);
        R visitUnaryExpr(UnaryExpr expr);
        R visitVariableExpr(VariableExpr expr);
    }

    public abstract <R> R accept(Visitor<R> visitor);
}
