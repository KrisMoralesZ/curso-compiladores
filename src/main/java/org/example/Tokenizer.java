package org.example;

import java.util.*;
import java.util.regex.*;

public class Tokenizer {
    // Definimos los tipos de tokens con expresiones regulares
    private static final String KEYWORD = "\\b(IF|THEN|ENDIF)\\b";
    private static final String IDENTIFIER = "[a-zA-Z_][a-zA-Z_0-9]*";
    private static final String NUMBER = "\\b\\d+\\b";
    private static final String OPERATOR = "[+\\-*/=]";
    private static final String WHITESPACE = "\\s+";
    private static final String UNKNOWN = ".";

    // Combinamos todos los patrones en un solo patrón
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            String.format("(?<KEYWORD>%s)|(?<IDENTIFIER>%s)|(?<NUMBER>%s)|(?<OPERATOR>%s)|(?<WHITESPACE>%s)|(?<UNKNOWN>%s)",
                    KEYWORD, IDENTIFIER, NUMBER, OPERATOR, WHITESPACE, UNKNOWN)
    );

    // Método para tokenizar una expresión
    public static List<Map<String, String>> tokenize(String input) {
        List<Map<String, String>> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(input);

        while (matcher.find()) {
            String type = null;
            if (matcher.group("KEYWORD") != null) type = "KEYWORD";
            else if (matcher.group("IDENTIFIER") != null) type = "IDENTIFIER";
            else if (matcher.group("NUMBER") != null) type = "NUMBER";
            else if (matcher.group("OPERATOR") != null) type = "OPERATOR";
            else if (matcher.group("UNKNOWN") != null) type = "UNKNOWN";

            // Ignoramos espacios en blanco
            if (!"WHITESPACE".equals(type) && type != null) {
                Map<String, String> token = new HashMap<>();
                token.put("type", type);
                token.put("value", matcher.group());
                tokens.add(token);
            }
        }
        return tokens;
    }

    public static void main(String[] args) {
        // Expresiones de entrada
        String[] expressions = {
                "X = 5",
                "y = 0",
                "IF X = 5 THEN",
                "Y = X / Y",
                "ENDIF"
        };

        // Tokenizamos y mostramos los resultados
        for (int i = 0; i < expressions.length; i++) {
            System.out.println("Tokens de la expresión " + (i + 1) + ": '" + expressions[i] + "'");
            List<Map<String, String>> tokens = tokenize(expressions[i]);
            for (Map<String, String> token : tokens) {
                System.out.println("  " + token);
            }
            System.out.println();
        }
    }
}
