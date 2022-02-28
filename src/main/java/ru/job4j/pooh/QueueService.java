package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * QueueService.
 * <p>
 * Queue mode.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 27.02.2022.
 */
public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();

    /**
     * Process request.
     * If GET request, than take data from queue (by it's name) head and return in Resp with code 200.
     * If POST request, than post data to queue tail (if map already contains queue with given name,
     * otherwise create new queue with given name)
     * and return in Resp with code 200.
     * If problems with returning data, than return Resp with code 204.
     *
     * @param req Server request.
     * @return Respond.
     */
    @Override
    public Resp process(Req req) {
        var reqType = req.httpRequestType();
        var name = req.getSourceName();
        var data = req.getParam();
        if ("POST".equals(reqType)) {
            queues.putIfAbsent(name, new ConcurrentLinkedQueue<>());
            queues.get(name).add(data);
            return new Resp(data, "200");
        }
        if ("GET".equals(reqType)) {
            return new Resp(queues.get(name).poll(), "200");
        }
        return new Resp("Something went wrong!", "204");
    }
}
