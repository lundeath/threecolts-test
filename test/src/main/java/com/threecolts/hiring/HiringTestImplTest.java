package com.threecolts.hiring;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HiringTestImplTest {

    @Test
    void countUniqueUrls() {
        var hiringTest = new HiringTestImpl();
        assertEquals(1, hiringTest.countUniqueUrls(List.of("https://example.com/", "https://example.com")));
    }

    @Test
    void countUniqueUrls1() {
        var hiringTest = new HiringTestImpl();
        assertEquals(2, hiringTest.countUniqueUrls(List.of("https://example.com", "http://example.com")));
    }

    @Test
    void countUniqueUrls2() {
        var hiringTest = new HiringTestImpl();
        assertEquals(1, hiringTest.countUniqueUrls(List.of("https://example.com?", "https://example.com")));
    }

    @Test
    void countUniqueUrls3() {
        var hiringTest = new HiringTestImpl();
        assertEquals(1, hiringTest.countUniqueUrls(List.of("https://example.com?a=1&b=2", "https://example.com?b=2&a=1")));
    }

    @Test
    void countUniqueUrlsPerTopLevelDomain() {
        var hiringTest = new HiringTestImpl();
        assertEquals(2, hiringTest.countUniqueUrlsPerTopLevelDomain(List.of("https://example.com", "https://subdomain.example.com")));
    }
}