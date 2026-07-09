package com.arbor.runtime;

import java.util.List;

public interface ArborCallable {
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}
