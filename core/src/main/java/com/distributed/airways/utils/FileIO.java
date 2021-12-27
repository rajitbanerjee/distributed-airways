package com.distributed.airways.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;

public class FileIO {
    public static String readFileAsString(String filePath) throws IOException {
        URL url = Resources.getResource(filePath);
        return Resources.toString(url, Charsets.UTF_8);
    }
}
