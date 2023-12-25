package com.hydron.sample.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;

/**
 * A utility class that reads in a file containing a list of words and place it in an array.
 */
public class WordList {
    private final String serviceName;
    private static String[] wordArray;
    private static Random randomGenerator;

    /**
     * Construct a {@code WordList} object and tries to read in a file containing the list of words.
     *
     * @param serviceName The name of the application.
     * @throws IOException If the file is unavailable for any reason.
     */
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

    /**
     * Retrieves a random word from the word list.
     *
     * @return A random word.
     */
    public String getWord() {
        return wordArray[randomGenerator.nextInt(wordArray.length)];
    }
}
