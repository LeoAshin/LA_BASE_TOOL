package la.standard.tool;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author LeoAshin
 * @date 2023/1/30 17:02
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpPoster {

    public static String post(String url, Consumer<HttpRequest> consumer) {
        var post = HttpRequest.post(url);
        return execute(post, consumer);
    }

    public static <T> T post(String url, Consumer<HttpRequest> consumer, Class<T> clazz) {
        var post = HttpRequest.post(url);
        var body = execute(post, consumer);
        return JSON.parseObject(body, clazz);
    }

    public static String get(String url, Consumer<HttpRequest> consumer) {
        var get = HttpRequest.post(url);
        return execute(get, consumer);
    }

    public static String get(String url) {
        var get = HttpRequest.get(url);
        return execute(get, null);
    }

    public static <T> T get(String url, Class<T> clazz) {
        var get = HttpRequest.post(url);
        var body = execute(get, null);
        return JSON.parseObject(body, clazz);
    }

    public static String get(String url, Function<String, Boolean> resend) {
        var s = get(url);
        var apply = resend.apply(s);
        if (!apply) {
            return get(url);
        }
        return s;
    }

    private static String execute(HttpRequest request, Consumer<HttpRequest> consumer) {
        if (consumer != null) {
            consumer.accept(request);
        }

        try (var response = request.charset(StandardCharsets.UTF_8).execute()) {
            if (!response.isOk()) {
                return null;
            }
            return response.body();
        } catch (Exception e) {
            throw e;
        }
    }
}
