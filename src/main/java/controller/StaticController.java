package controller;

import request.HttpRequest;
import response.HttpResponse;
import utils.FileIoUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController extends AbstractController {

    private static final String TEMPLATES_FILE_PATH = "src/main/resources/templates";
    private static final String STATIC_FILE_PATH = "src/main/resources/static";
    private static final String TEMPLATES_PATH_PREFIX = "./templates";
    private static final String STATIC_PATH_PREFIX = "./static";
    private static final String HTML = "html";
    public static final String CSS = "css";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (fileExist(TEMPLATES_FILE_PATH, httpRequest.getUri())) {
            makeResponse(TEMPLATES_PATH_PREFIX, httpRequest.getUri(), httpResponse, HTML);
        }
        if (fileExist(STATIC_FILE_PATH, httpRequest.getUri())) {
            makeResponse(STATIC_PATH_PREFIX, httpRequest.getUri(), httpResponse, CSS);
        }

    }

    private boolean fileExist(String filePath, String uri) {
        return new File(filePath + uri).exists();
    }

    private void makeResponse(String filePathPrefix, String uri, HttpResponse httpResponse, String type) {
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath(filePathPrefix + uri);
            httpResponse.response200Header(body.length, type);
            httpResponse.responseBody(body);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
