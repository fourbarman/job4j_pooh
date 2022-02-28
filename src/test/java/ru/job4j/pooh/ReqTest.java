package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * ReqTest.
 * <p>.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 27.02.2022.
 */
public class ReqTest {
    /**
     * Test of when queue mode and method POST.
     */
    @Test
    public void whenQueueModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /queue/weather HTTP/1.1"
                + ls + "Host: localhost:9000"
                + ls + "User-Agent: curl/7.72.0"
                + ls + "Accept: */*"
                + ls + "Content-Length: 14"
                + ls + "Content-Type: application/x-www-form-urlencoded"
                + ls + ""
                + ls + "temperature=18" + ls;
        Req req = Req.of(content);
        assertThat(req.httpRequestType(), is("POST"));
        assertThat(req.getPoohMode(), is("queue"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is("temperature=18"));
    }

    /**
     * Test of when queue mode and GET method.
     */
    @Test
    public void whenQueueModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /queue/weather HTTP/1.1"
                + ls + "Host: localhost:9000"
                + ls + "User-Agent: curl/7.72.0"
                + ls + "Accept: */*"
                + ls + ls + ls;
        Req req = Req.of(content);
        assertThat(req.httpRequestType(), is("GET"));
        assertThat(req.getPoohMode(), is("queue"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is(""));
    }

    /**
     * Test of when topic mode and POST method.
     */
    @Test
    public void whenTopicModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /topic/weather HTTP/1.1"
                + ls + "Host: localhost:9000"
                + ls + "User-Agent: curl/7.72.0"
                + ls + "Accept: */*"
                + ls + "Content-Length: 14"
                + ls + "Content-Type: application/x-www-form-urlencoded"
                + ls + ""
                + ls + "temperature=18" + ls;
        Req req = Req.of(content);
        assertThat(req.httpRequestType(), is("POST"));
        assertThat(req.getPoohMode(), is("topic"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is("temperature=18"));
    }

    /**
     * Test when topic mode and GET method.
     */
    @Test
    public void whenTopicModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /topic/weather/client407 HTTP/1.1"
                + ls + "Host: localhost:9000"
                + ls + "User-Agent: curl/7.72.0"
                + ls + "Accept: */*" + ls + ls + ls;
        Req req = Req.of(content);
        assertThat(req.httpRequestType(), is("GET"));
        assertThat(req.getPoohMode(), is("topic"));
        assertThat(req.getSourceName(), is("weather"));
        assertThat(req.getParam(), is("client407"));
    }
}
