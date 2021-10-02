package rate.retriever;

import lombok.RequiredArgsConstructor;
import rate.rules.ClientRules;
import rate.rules.RateLimitRule;
import util.SerializationUtil;

import java.util.Map;

@RequiredArgsConstructor
public class StaticRulesRetriever implements RulesRetriever{
    private final String rulesFile;
    private ClientRules clientRules;

    private void fetchClientRules() {
        if (clientRules == null) {
            final String ruleString = FileReader.readEntireFile(rulesFile);
            clientRules = SerializationUtil.parseJson(ruleString, ClientRules.class);
        }
    }
    @Override
    public RateLimitRule retrieve(String namespace, String key) {
        if (clientRules == null) fetchClientRules();
        if (clientRules.getClientRules().containsKey(namespace)) {
            return clientRules.getClientRules().get(namespace).get(key);
        }
        return null;
    }

    @Override
    public Map<String, RateLimitRule> retrieveAllRules(String namespace) {
        if (clientRules == null) fetchClientRules();
        if (clientRules.getClientRules().containsKey(namespace)) {
            return clientRules.getClientRules().get(namespace);
        }
        return null;
    }

    @Override
    public Map<String, Map<String, RateLimitRule>> retrieveAllRules() {
        if (clientRules == null) fetchClientRules();
        if (clientRules != null) {
            return clientRules.getClientRules();
        }
        return null;
    }
}
