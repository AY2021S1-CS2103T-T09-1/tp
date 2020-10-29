package seedu.gradpad.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.gradpad.testutil.Assert.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.gradpad.nusmods.exceptions.NusmodsException;

public class HttpUtilTest {
    /**
     * This is a public API endpoint by Postman for testing purposes. It echoes all requests params.
     */
    private static final String VALID_DUMMY_ENDPOINT = "https://postman-echo.com/get?foo=bar";
    private static final String INVALID_ENDPOINT = "https://asdf";
    private final HttpUtil httpUtil = HttpUtil.SINGLETON;

    //CHECKSTYLE.OFF: AbbreviationAsWordInName
    @Test
    public void makeGETRequest_validURL_validResponse() throws NusmodsException, IOException {
        //CHECKSTYLE.ON: AbbreviationAsWordInName
        String response = httpUtil.makeGETRequest(VALID_DUMMY_ENDPOINT);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);
        String actualProperty = jsonNode.get("args").get("foo").asText();

        assertEquals("bar", actualProperty);
    }

    //CHECKSTYLE.OFF: AbbreviationAsWordInName
    @Test
    public void makeGETRequest_invalidURL_throwsNusmodsException() {
        //CHECKSTYLE.ON: AbbreviationAsWordInName
        assertThrows(NusmodsException.class, () -> httpUtil.makeGETRequest(INVALID_ENDPOINT));
    }
}
