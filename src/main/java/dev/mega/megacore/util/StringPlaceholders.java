package dev.mega.megacore.util;

import lombok.Getter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An immutable class that holds a map of placeholders and their values
 */
@Getter
public final class StringPlaceholders {

    private final static StringPlaceholders EMPTY = new StringPlaceholders(Collections.emptyMap(), "%", "%");

    /**
     * -- GETTER --
     */
    private final String startDelimiter, /**
     * -- GETTER --
     */
            endDelimiter;
    /**
     * -- GETTER --
     */
    private final Map<String, String> placeholders;

    private StringPlaceholders(Map<String, String> placeholders, String startDelimiter, String endDelimiter) {
        this.placeholders = Collections.unmodifiableMap(placeholders);
        this.startDelimiter = startDelimiter;
        this.endDelimiter = endDelimiter;
    }

    /**
     * Applies the placeholders to the given string
     *
     * @param string the string to apply the placeholders to
     * @return the string with the placeholders replaced
     */
    public String apply(String string) {
        for (String key : this.placeholders.keySet())
            string = string.replaceAll(Pattern.quote(this.startDelimiter + key + this.endDelimiter), Matcher.quoteReplacement(this.placeholders.get(key)));
        return string;
    }

    /**
     * @return a new StringPlaceholders builder with delimiters initially set to %
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new builder with delimiters initially set to % and a placeholder added
     *
     * @param placeholder the placeholder to add
     * @param value the value to replace the placeholder with
     * @return a new StringPlaceholders builder with delimiters initially set to % and a placeholder added
     */
    public static Builder builder(String placeholder, Object value) {
        return new Builder().add(placeholder, value);
    }

    /**
     * @return the empty StringPlaceholders instance
     */
    public static StringPlaceholders empty() {
        return EMPTY;
    }

    /**
     * Creates a new StringPlaceholders instance with delimiters set to % and one placeholder added
     *
     * @param placeholder the placeholder to add
     * @param value the value to replace the placeholder with
     * @return a new StringPlaceholders instance with delimiters set to % and one placeholder added
     */
    public static StringPlaceholders of(String placeholder, Object value) {
        return builder(placeholder, value).build();
    }

    /**
     * Creates a new StringPlaceholders instance with delimiters set to % and one placeholder added
     * {@code StringPlaceholders.of("some string %placeholder%", new Object())}
     *
     * @param placeholdersAndValues the placeholders to add and the values to replace
     * @return a new StringPlaceholders instance with delimiters set to % and one placeholder added
     */
    public static StringPlaceholders of(Object... placeholdersAndValues) {
        if (placeholdersAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("[DEV ISSUE] Invalid number of arguments.");
        }

        StringPlaceholders.Builder builder = builder(placeholdersAndValues[0].toString(), placeholdersAndValues[1]);

        for (int i = 2; i < placeholdersAndValues.length; i += 2) {
            builder.add(placeholdersAndValues[i].toString(), placeholdersAndValues[i + 1]);
        }

        return builder.build();
    }

    public static class Builder {

        private String startDelimiter, endDelimiter;
        private final Map<String, String> placeholders;

        private Builder() {
            //фикс гитхаба
            this.startDelimiter = "%";
            this.endDelimiter = "%";
            this.placeholders = new HashMap<>();
        }

        /**
         * Adds a placeholder
         *
         * @param placeholder The placeholder to add
         * @param value The value to replace the placeholder with
         * @return this
         */
        public Builder add(String placeholder, Object value) {
            this.placeholders.put(placeholder, Objects.toString(value, "null"));
            return this;
        }

        /**
         * Adds a placeholder with any % characters in the value removed
         *
         * @param placeholder The placeholder to add
         * @param value The value to replace the placeholder with
         * @return this
         */
        public Builder addSanitized(String placeholder, Object value) {
            this.placeholders.put(placeholder, Objects.toString(value, "null").replace("%", ""));
            return this;
        }

        /**
         * Sets the delimiters
         *
         * @param startDelimiter The start delimiter
         * @param endDelimiter The end delimiter
         * @return this
         */
        public Builder delimiters(String startDelimiter, String endDelimiter) {
            this.startDelimiter = startDelimiter;
            this.endDelimiter = endDelimiter;
            return this;
        }

        /**
         * Adds all placeholders from another StringPlaceholders instance
         *
         * @param placeholders The StringPlaceholders instance to add placeholders from
         * @return this
         */
        public Builder addAll(StringPlaceholders placeholders) {
            return this.addAll(placeholders.getPlaceholders());
        }

        /**
         * Adds all placeholders from a map
         *
         * @param placeholders The map to add placeholders from
         * @return this
         */
        public Builder addAll(Map<String, String> placeholders) {
            this.placeholders.putAll(placeholders);
            return this;
        }

        /**
         * @return a new StringPlaceholders instance
         */
        public StringPlaceholders build() {
            return new StringPlaceholders(this.placeholders, this.startDelimiter, this.endDelimiter);
        }
    }

}
