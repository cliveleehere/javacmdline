package com.clivelee.config;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;


public class ArgumentProcessorTest {

    @Test
    public void parseArguments_valid() {
        String[] args = {"https://www.example.com"};

        Configuration config = new ArgumentProcessor().parseArguments(args);

        assertThat(config.domainName()).isEqualTo("https://www.example.com");
    }

    @Test
    public void parseArguments_no_scheme() {
        String[] args = {"www.example.com"};

        Configuration config = new ArgumentProcessor().parseArguments(args);

        assertThat(config.domainName()).isEqualTo("http://www.example.com");
    }
}