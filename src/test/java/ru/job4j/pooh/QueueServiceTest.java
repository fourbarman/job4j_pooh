package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * QueueServiceTest.
 * <p>
 * Queue mode test.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 27.02.2022.
 */
public class QueueServiceTest {
    /**
     * Test process in queue mode when POST param and GET than return param.
     */
    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }

    /**
     * Test process in queue mode when request type is undefined than return empty text with 501 status.
     */
    @Test
    public void whenReqTypeIsUndefinedThanReturnEmptyTextAndStatus501() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
                new Req("undefined", "queue", "weather", null)
        );
        assertThat(result.text(), is(""));
        assertThat(result.status(), is("501"));
    }

    /**
     * Test process in queue mode when GET and has no such sourceName than return empty text and 204 status.
     */
    @Test
    public void whenGETAndQueueIsEmptyThanReturnEmptyTextAndStatus204() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is(""));
        assertThat(result.status(), is("204"));
    }
    /**
     * Test process in queue mode when POST param and GET
     * and has empty queue for sourceName
     * than return empty text and 204 status.
     */
    @Test
    public void whenPostThenGetQueueAndPollIsNullThanReturnEmptyTextAndStatus204() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is(""));
        assertThat(result.status(), is("204"));
    }
}
