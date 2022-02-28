package ru.job4j.pooh;

/**
 * Resp.
 * <p>
 * Represents server respond.
 * text - server message.
 * status - HTTP response status code.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 27.02.2022.
 */
public class Resp {
    private final String text;
    private final String status;

    public Resp(String text, String status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public String status() {
        return status;
    }
}
