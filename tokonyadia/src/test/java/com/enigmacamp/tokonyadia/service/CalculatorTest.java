package com.enigmacamp.tokonyadia.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    @DisplayName("add two numbers")
    void sumTest() {

    }

    @CsvSource({
            "0, 1, 1",
            "1, 2, 3",
            "-5, -5, -10"
    })
    @ParameterizedTest(name = "{0} + {1} = {2}")
    void sumCSV(int a, int b, int expected) {

    }

    @Test
    void mulTest() {

    }
}
