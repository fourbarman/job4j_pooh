package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * TopicServiceTest.
 * <p>
 * Topic mode test.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 27.02.2022.
 */
public class TopicServiceTest {
    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        /* Режим topic. Подписываемся на топик weather. client407. */
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        /* Режим topic. Добавляем данные в топик weather. */
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        /* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client407. */
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        /* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client6565.
        Очередь отсутствует, т.к. еще не был подписан - получит пустую строку */
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result1.status(), is("200"));
        assertThat(result2.text(), is(""));
        assertThat(result2.status(), is("204"));
    }

    /**
     * Test process in topic mode when request type is undefined
     * than return empty text and status 501.
     */
    @Test
    public void whenUndefinedRequestTypeThenReturnEmptyTextAndStatus501() {
        TopicService topicService = new TopicService();
        Resp result = topicService.process(
                new Req("undefined", "topic", "weather", "param")
        );
        assertThat(result.text(), is(""));
        assertThat(result.status(), is("501"));
    }

    /**
     * Test process in topic mode when POST without run GET first
     * than return empty text string and 204 status.
     */
    @Test
    public void whenPostFirstThanReturnEmptyTextAndStatus204() {
        TopicService topicService = new TopicService();
        Resp result = topicService.process(
                new Req("POST", "topic", "weather", "param")
        );
        assertThat(result.text(), is(""));
        assertThat(result.status(), is("204"));
    }

    /**
     * Test process in topic mode when GET and didn't subscribed yet
     * than return empty text and status 204.
     */
    @Test
    public void whenGetAndNoSourceNameThanCreateNewAndReturnEmptyTextAndStatus204() {
        TopicService topicService = new TopicService();
        Resp result = topicService.process(
                new Req("GET", "topic", "weather", "subscriberId")
        );
        assertThat(result.text(), is(""));
        assertThat(result.status(), is("204"));
    }
}
