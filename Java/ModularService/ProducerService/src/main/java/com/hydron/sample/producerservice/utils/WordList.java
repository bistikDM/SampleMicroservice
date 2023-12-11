package com.hydron.sample.producerservice.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;

public class WordList {
    private final String serviceName;
    private static String[] wordArray;
    private static Random randomGenerator;

    public WordList(String serviceName) throws IOException {
        this.serviceName = serviceName;
        System.out.printf("[%s] [%s] -- Generating word list...%n", this.serviceName, Instant.now());
        InputStream is = this.getClass()
                .getClassLoader()
                .getResourceAsStream("wordlist");
        if (Objects.isNull(is)) {
            throw new IOException("Resource not found!");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        wordArray = br.lines()
                .toArray(String[]::new);
        randomGenerator = new Random();
        System.out.printf("[%s] [%s] -- Word list generated!%n", this.serviceName, Instant.now());
    }

    public String getWord() {
        return wordArray[randomGenerator.nextInt(wordArray.length)];
    }
}
