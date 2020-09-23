package mentoring.epam.bank.commons.domain.bank.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import mentoring.epam.bank.commons.domain.bank.PaymentType;

import java.io.IOException;

//class PaymentDeserializerializer extends JsonDeserializer<PaymentType> {
//
//        @Override
//        public PaymentType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
//
//            ObjectCodec oc = jsonParser.getCodec();
//            JsonNode node = oc.readTree(jsonParser);
//
//            if (node == null) {
//                return null;
//            }
//
//            String text = node.textValue();
//
//            if (text == null) {
//                return null;
//            }
//
//            return PaymentType.fromText(text);
//        }
//    }

