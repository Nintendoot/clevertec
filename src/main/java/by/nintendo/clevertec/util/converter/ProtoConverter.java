package by.nintendo.clevertec.util.converter;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;

/**
 * Converter of proto classes to JSON
 */
@Component
public class ProtoConverter {

    /**
     * Builds JSON from a proto object
     *
     * @param o proto object
     * @param <T>
     * @return JSON
     */
    public <T extends Message> String objectToJson(T o) {
        try {
            return JsonFormat.printer().print(o);
        } catch (Exception e) {
            throw new RuntimeException("Error convert Object to JSON");
        }
    }
}
