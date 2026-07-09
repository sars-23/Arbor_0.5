package com.arbor.runtime;

import com.arbor.ast.statement.FunctionStmt;
import java.util.List;

public class ArborFunction implements ArborCallable {
    private final FunctionStmt declaration;
    private final Environment closure;

    public ArborFunction(FunctionStmt declaration, Environment closure) {
        this.closure = closure;
        this.declaration = declaration;
    }

    @Override
    public int arity() {
        return declaration.getParams().size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.getParams().size(); i++) {
            environment.define(declaration.getParams().get(i).getLexeme(), arguments.get(i));
        }

        try {
            interpreter.executeBlock(declaration.getBody(), environment);
        } catch (Return returnValue) {
            return returnValue.getValue();
        }
        return null;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.getName().getLexeme() + ">";
    }
}
