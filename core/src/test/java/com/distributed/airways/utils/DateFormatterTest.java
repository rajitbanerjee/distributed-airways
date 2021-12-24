package com.distributed.airways.utils;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import org.junit.*;

public class DateFormatterTest {
    @Test
    public void testConversion() throws ParseException {
        assertEquals("Monday", DateFormatter.dateToDayOfWeek("2021-12-20"));
        assertEquals("Tuesday", DateFormatter.dateToDayOfWeek("2021-12-21"));
        assertEquals("Wednesday", DateFormatter.dateToDayOfWeek("2021-12-22"));
        assertEquals("Thursday", DateFormatter.dateToDayOfWeek("2021-12-23"));
        assertEquals("Friday", DateFormatter.dateToDayOfWeek("2021-12-24"));
        assertEquals("Saturday", DateFormatter.dateToDayOfWeek("2021-12-25"));
        assertEquals("Sunday", DateFormatter.dateToDayOfWeek("2021-12-26"));
    }
}
