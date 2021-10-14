package com.browser.handlers;


import com.google.common.net.UrlEscapers;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Downloads {
    public void download(String path, String url) {
        try {
            FileUtils.copyURLToFile(new URL(url),new File(path));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
