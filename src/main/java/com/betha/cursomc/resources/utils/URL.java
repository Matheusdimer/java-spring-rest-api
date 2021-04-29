package com.betha.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
    public static List<Integer> decodeIntList(String s) {
        String[] vet = s.split(",");
        List<Integer> list = new ArrayList<>();

        for (String c : vet) {
            list.add(Integer.parseInt(c));
        }
        return list;
    }

    public static String decodeParam(String param) {
        try {
            return URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
