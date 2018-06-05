package com.clivelee.utils;

import org.junit.Test;

import java.util.Set;

import static com.google.common.truth.Truth.*;

public class TextFinderTest {

    @Test
    public void findEmails_noEmail() {
        Set<String> result = TextFinder.findEmails("no email in here unforunately");

        assertThat(result).isEmpty();
    }

    @Test
    public void findEmails_oneEmail() {
        Set<String> result = TextFinder.findEmails("one email hidden in here somewhere where could it be? a@b.c");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.iterator().next()).isEqualTo("a@b.c");
    }
}