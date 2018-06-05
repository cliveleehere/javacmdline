package com.clivelee.config;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.google.common.truth.Truth.assertThat;


public class ArgumentProcessorTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void parseArguments_valid() {
        String[] args = {"https://www.example.com"};

        Configuration config = new ArgumentProcessor().parseArguments(args);

        assertThat(config.domainName()).isEqualTo("https://www.example.com");
    }

    @Test
    public void parseArguments_noScheme() {
        String[] args = {"www.example.com"};

        Configuration config = new ArgumentProcessor().parseArguments(args);

        assertThat(config.domainName()).isEqualTo("www.example.com");
    }

    @Test
    public void parseArguments_emptyArgs() {
        String[] args = {};

        exception.expect(UrlNotSpecifiedException.class);
        new ArgumentProcessor().parseArguments(args);
    }

    @Test
    public void parseArguments_longArgs() {
        String[] args = {"www.example.com", "oops"};
        exception.expect(UrlNotSpecifiedException.class);
        new ArgumentProcessor().parseArguments(args);
    }

    @Test
    public void parseArguments_malformedUrl() {
        String[] args = {"htttp://www.example.com"};
        exception.expect(UrlMalformed.class);
        new ArgumentProcessor().parseArguments(args);
    }

    @Test
    public void isValidDomain_noScheme() {
        String testDomain = "www.example.com";
        assertThat(ArgumentProcessor.isValidDomain(testDomain)).isTrue();
    }

    @Test
    public void isValidDomain_withHttpScheme() {
        String testDomain = "http://www.example.com";
        assertThat(ArgumentProcessor.isValidDomain(testDomain)).isTrue();
    }

    @Test
    public void isValidDomain_withHttpsScheme() {
        String testDomain = "https://gobbledygook.co";
        assertThat(ArgumentProcessor.isValidDomain(testDomain)).isTrue();
    }

    @Test
    public void isValidDomain_invalidSpecial() {
        String testDomain = "gobbledygook.!!!.com";
        assertThat(ArgumentProcessor.isValidDomain(testDomain)).isFalse();
    }

    @Test
    public void isValidDomain_invalid() {
        String testDomain = "gobbledygook";
        assertThat(ArgumentProcessor.isValidDomain(testDomain)).isFalse();
    }

    @Test
    public void isValidDomain_invalidTwoPeriods() {
        String testDomain = "gobbledygook..com";
        assertThat(ArgumentProcessor.isValidDomain(testDomain)).isFalse();
    }
}