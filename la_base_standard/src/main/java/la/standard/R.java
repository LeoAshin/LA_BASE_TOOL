package la.standard;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 标准返回体
 *
 * @author LeoAshin
 * @date 2023/1/30 15:32
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public final class R<T> implements Serializable {

    private static final long serialVersionUID = -3119281808137738327L;

    private T body;
    private int code;
    private String errorMsg;
    private Long index;
    private Long end;

    public R(int code) {
        this.code = code;
    }

    public R(int code, T data) {
        this.code = code;
        this.body = data;
    }

    /**
     * 标准错误返回
     *
     * @param error 定义的标准错误
     * @param <V>   必须是继承于StandardCode 的标准错误
     * @return 没有请求体的
     */
    public static <V extends StandardCode> R<Void> error(V error) {
        return new R<Void>().setCode(error.getCode()).setErrorMsg(error.getMsg());
    }

    /**
     * 标准数据返回,带返回体
     *
     * @param <T> 返回数据
     * @return
     */
    public static <T> R<T> ok(T data) {
        return new R<T>(1000, data);
    }

    /**
     * 没有返回数据的ok响应
     *
     * @return
     */
    public static R<Void> ok() {
        return new R<>(1000);
    }

    /**
     * 根据bool值判断返回操作是否成功
     *
     * @param check
     * @return
     */
    public static R<Void> ok(Boolean check) {
        return check ? R.ok() : new R<>();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, JSONWriter.Feature.PrettyFormat);
    }
}
