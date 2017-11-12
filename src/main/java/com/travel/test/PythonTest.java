package com.travel.test;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class PythonTest {

	public static void test() {
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.execfile("e:/first_py.py");
		PyFunction func = (PyFunction) interpreter.get("adder",
				PyFunction.class);
		int a = 10;
		int b = 2;
		PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
		System.out.println("result=" + pyobj.toString());
	}

	public static void main(String[] args) {
		test();
	}
}
