package mentoring.epam.bank.repository.keycloak.auth.client;


import lombok.Getter;
import lombok.Setter;
import mentoring.epam.bank.repository.keycloak.auth.config.AuthConfig;
import org.apache.http.client.utils.URIBuilder;
import org.keycloak.authorization.client.representation.TokenIntrospectionResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



@Service
@Getter
@Setter
public class KeycloakClient {

    private static String PROTOCOL = "http";
    private static String PATH_DEPOSIT = "/deposit";
    private static String PATH_WITHDRAW = "/withdraw";
    private static String VALI = "/token/introspect";
    private static String AUTH = null;
    private static String FRAGMENT = null;
    private static String QUERY = null;


    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
    private Integer port=8080;


    @Autowired
    public KeycloakClient(AuthConfig authConfig) {
        this.serverUrl = authConfig.getAuthServerUrl();
        this.realm = authConfig.getRealm();
        this.clientId = authConfig.getClientId();
        this.clientSecret = authConfig.getClientSecret();
    }

    private String getTokenEndpoint() {
        return serverUrl + "/realms/" + realm + "/protocol/openid-connect/token/introspect";
    }

    public TokenIntrospectionResponse validateToken(String token) throws IOException {

        URI uri = null;
        try {
            uri = new URIBuilder(serverUrl).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", token);
        
        String body = "token_type_hint=requesting_party_token&client_id="+ clientId +"&client_secret="+ clientSecret + "&token="+token.replace("Bearer ","");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(body,headers);

        ResponseEntity<TokenIntrospectionResponse> result = restTemplate.postForEntity(uri+"auth/realms/bank/protocol/openid-connect/token/introspect", request, TokenIntrospectionResponse.class);

        return result.getBody();
    }


}



