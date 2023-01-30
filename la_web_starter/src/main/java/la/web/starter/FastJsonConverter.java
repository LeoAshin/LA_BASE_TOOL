package la.web.starter;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.smile.MappingJackson2SmileHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LeoAshin
 * @date 2023/1/30 16:47
 */
@Configuration
public class FastJsonConverter implements WebMvcConfigurer, Ordered {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
        converters.removeIf(MappingJackson2SmileHttpMessageConverter.class::isInstance);
        converters.removeIf(MappingJackson2CborHttpMessageConverter.class::isInstance);
        var fastJsonConverter = new FastJsonHttpMessageConverter();
        var fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean);
        fastJsonConfig.setWriterFeatures(JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.NotWriteDefaultValue);
        fastJsonConverter.setDefaultCharset(StandardCharsets.UTF_8);

        fastJsonConverter.setFastJsonConfig(fastJsonConfig);
        var fastJsonMediaTypes = new ArrayList<MediaType>();
        fastJsonMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonMediaTypes.add(MediaType.TEXT_PLAIN);
        fastJsonConverter.setSupportedMediaTypes(fastJsonMediaTypes);
        converters.add(fastJsonConverter);
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }
}
