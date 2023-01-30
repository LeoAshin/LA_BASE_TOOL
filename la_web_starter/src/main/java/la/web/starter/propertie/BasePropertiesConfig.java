package la.web.starter.propertie;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author LeoAshin
 * @date 2023/1/30 15:23
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "la.web")
public class BasePropertiesConfig implements Serializable {

    private static final long serialVersionUID = 1632586583925659767L;

    private boolean logOn;
}
