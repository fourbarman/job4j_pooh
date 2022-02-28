package ru.job4j.pooh;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * QueueService.
 * <p>
 * Queue mode.
 * If request contains GET mode, than
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 27.02.2022.
 */
public class TopicService implements Service {
    ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queues = new ConcurrentHashMap<>();

    /**
     * If POST and queues contains topic than add data to all subscribers queues.
     * If POST and queues doesn't have topic than ignore.
     * If GET and queues contains topic and subscriber than return data from subscribers queue.
     * If GET and queues doesn't have topic, than create new and create new subscribers queue.
     *
     * @param req Server request.
     * @return Respond.
     */
    @Override
    public Resp process(Req req) {
        var reqType = req.httpRequestType();
        var topicName = req.getSourceName();
        var data = req.getParam();

        if ("POST".equals(reqType)) {
            for (ConcurrentLinkedQueue<String> value : queues.get(topicName).values()) {
                value.add(data);
            }
            return new Resp(data, "200");
        }

        if ("GET".equals(reqType)) {
            /* если топика нет, то добавим */
            queues.putIfAbsent(topicName, new ConcurrentHashMap<>());
            /* если нет данных подписчика в топике, то добавим новую очередь */
            queues.get(topicName).putIfAbsent(data, new ConcurrentLinkedQueue<>());
            return new Resp(Optional.ofNullable(queues.get(topicName).get(data).poll()).orElse(""), "200");
        }

        return new Resp("Something went wrong", "204");
    }
}
