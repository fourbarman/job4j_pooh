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
     * If POST and queues contains topic than add data to all subscribers queues and return data in Resp with code 200.
     * If POST and queues doesn't have topic than ignore.
     * If GET and queues contains topic and subscriber than return data from subscribers queue with code 200.
     * If GET and queues doesn't have topic, than create new and create new subscribers queue with code 200.
     * If problems with returning data, than return Resp with code 204.
     * If request type is unknown return Resp with code 501.
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
        } else if ("GET".equals(reqType)) {
            /* если топика нет, то добавим */
            queues.putIfAbsent(topicName, new ConcurrentHashMap<>());
            /* если нет данных подписчика в топике, то добавим новую очередь */
            queues.get(topicName).putIfAbsent(data, new ConcurrentLinkedQueue<>());
            var extracted = queues.get(topicName).get(data).poll();
            if (extracted != null) {
                return new Resp(extracted, "200");
            } else {
                return new Resp("", "204");
            }
        } else {
            return new Resp("", "501");
        }
    }
}
