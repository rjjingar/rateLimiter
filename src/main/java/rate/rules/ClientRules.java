package rate.rules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRules {
    private String clientId;
    private Map<String, Map<String, RateLimitRule>> clientRules;
}
