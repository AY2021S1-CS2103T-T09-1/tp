package seedu.gradpad.commons.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import seedu.gradpad.nusmods.exceptions.NusmodsException;

/**
 * Utilities for HTTP requests.
 */
public class HttpUtil {
    public static final HttpUtil SINGLETON = new HttpUtil();
    /**
     * Makes a HTTP GET request to a URL and returns the response as a string.
     *
     * @param urlString The URL to send the GET request to.
     * @return The HTTP response as a string.
     * @throws NusmodsException an error occurs while making the request.
     */
    //CHECKSTYLE.OFF: AbbreviationAsWordInName
    public String makeGETRequest(String urlString) throws NusmodsException {
        //CHECKSTYLE.ON: AbbreviationAsWordInName
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                      .timeout(Duration.ofSeconds(3))
                                      .uri(URI.create(urlString))
                                      .GET()
                                      .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException ex) {
            throw new NusmodsException(ex);
        }
    }
}
