package ru.job4j.pooh;

/**
 * Service.
 * <p>
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 27.02.2022.
 */
public interface Service {
    /**
     * Starts appropriate server mode, depending on Req object.
     *
     * @param req Server request.
     * @return Respond.
     */
    Resp process(Req req);
}
