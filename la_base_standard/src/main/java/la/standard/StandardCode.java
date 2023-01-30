package la.standard;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author LeoAshin
 * @date 2023/1/30 15:37
 */
@Getter
@Setter
public abstract class StandardCode extends Exception implements Serializable {

    private static final long serialVersionUID = 2113634625148983511L;

    private String msg;
    private int code;
}
