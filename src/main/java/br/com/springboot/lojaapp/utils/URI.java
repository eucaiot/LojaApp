package br.com.springboot.lojaapp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class URI {


    public static List<UUID> CategoriasLista(String s) {
        if (s == null || s.isBlank()) {
            return List.of();
        }
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .filter(part -> !part.isEmpty())
                .map(UUID::fromString)
                .toList();
    }

    public static String decodeString(String nomeProduto) {
        try {
            return URLDecoder.decode(nomeProduto, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
