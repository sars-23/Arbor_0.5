package com.arbor.ast;

import com.arbor.ast.statement.*;

public abstract class Stmt {
    public interface Visitor<R> {
        R visitBlockStmt(BlockStmt stmt);
        R visitExpressionStmt(ExpressionStmt stmt);
        R visitFunctionStmt(FunctionStmt stmt);
        R visitIfStmt(IfStmt stmt);
        R visitPrintStmt(PrintStmt stmt);
        R visitReturnStmt(ReturnStmt stmt);
        R visitVarStmt(VarStmt stmt);
        R visitWhileStmt(WhileStmt stmt);
    }

    public abstract <R> R accept(Visitor<R> visitor);
}
