package edu.hw2.task4;

public final class Task4 {

    private Task4() {
    }

    public static CallingInfo callingInfo() {
        var stackTraceInfo = new Throwable().getStackTrace()[1];
        return new CallingInfo(stackTraceInfo.getClassName(), stackTraceInfo.getMethodName());
    }
}
