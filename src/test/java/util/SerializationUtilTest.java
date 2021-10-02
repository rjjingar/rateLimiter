package util;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import rate.rules.ClientRules;
import rate.rules.RateLimitRule;

public class SerializationUtilTest {

    @Test
    public void testSerialize() {
        ClientRules clientRules =
        ClientRules.builder().clientId("test-client")
                .clientRules(ImmutableMap.of("clientLimits",
                        ImmutableMap.of("clientA", new RateLimitRule(100, 10),
                                "clientB", new RateLimitRule(100, 10))))
                .build();
        String json = SerializationUtil.convertToJson(clientRules);
        System.out.println(json);
        ClientRules clientRules2 = SerializationUtil.parseJson(json, ClientRules.class);
        //System.out.println(SerializationUtil.convertToJson(new RateLimitRule(100, 100)));


    }
}
