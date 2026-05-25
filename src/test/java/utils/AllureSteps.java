package utils;

import io.qameta.allure.Step;

public class AllureSteps {

    @Step("{0}")
    public static <T> T step(String description, StepAction<T> action) {
        try {
            return action.execute();
        } catch (Exception e) {
            throw new RuntimeException("Step failed: " + description, e);
        }
    }

    @Step("{0}")
    public static void step(String description, StepVoid action) {
        try {
            action.execute();
        } catch (Exception e) {
            throw new RuntimeException("Step failed: " + description, e);
        }
    }

    @FunctionalInterface
    public interface StepAction<T> {
        T execute();
    }

    @FunctionalInterface
    public interface StepVoid {
        void execute();
    }
}
