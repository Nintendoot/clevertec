package by.nintendo.clevertec.util.converter;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;

@Component
public class ProtoConverter {

    public <T extends Message> String objectToJson(T o) {
        try {
            return JsonFormat.printer().print(o);
        } catch (Exception e) {
            throw new RuntimeException("Error convert Object to JSON");
        }
    }


}
