package com.Madhav.SocialMediaChecker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class SocialMediaCheckerTest {
	@Test
    void testDateParsing() {
        LocalDate date = SocialMediaChecker.parseDate("January 1, 2023");
        assertNotNull(date);
        assertEquals(2023, date.getYear());
    }

    @Test
    void testInvalidDateParsing() {
        LocalDate date = SocialMediaChecker.parseDate("Invalid Date");
        assertNull(date);
    }
}
