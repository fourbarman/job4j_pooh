package ru.job4j.pooh;

/**
 * Req.
 * <p>
 * Parses server request.
 * Takes from request:
 * httpRequestType - request type (GET or POST),
 * poohMode - mode (queue or topic),
 * sourceName - queue name or topic name,
 * param - request data.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 27.02.2022.
 */
public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    /**
     * Parse data from request.
     *
     * @param content String request.
     * @return new Req object with parsed data.
     */
    public static Req of(String content) {
        String type = "";
        String mode = "";
        String sourceName = "";
        String param = "";
        String[] lines = content.split("[\s/|\s|/|\n\r]");
        type = lines[0];
        mode = lines[2];
        sourceName = lines[3];
        if ("GET".equals(type)) {
            if ("queue".equals(mode)) {
                param = "";
            }
            if ("topic".equals(mode)) {
                param = lines[4];
            }
        } else {
            param = lines[lines.length - 1];
        }
        return new Req(type, mode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
