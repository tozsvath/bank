package mentoring.epam.auth.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import mentoring.epam.auth.config.AuthConfig;
import mentoring.epam.auth.domain.TokenValidation;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.representation.TokenIntrospectionResponse;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


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
    //public static final MediaType JSON
      //      = MediaType.parseMediaType("application/json; charset=utf-8");


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
        //this.port = authConfig.getPort();
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
//        try {
//            uri = new URI(AUTH, serverUrl, QUERY, FRAGMENT);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", token);

//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//Add the Jackson Message converter
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//
//// Note: here we are making this converter to process any kind of response,
//// not only application/*json, which is the default behaviour
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);


//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode objectNode = mapper.createObjectNode();
//        objectNode.put("token_type_hint","requesting_party_token");
//        objectNode.put("client_id",realm);
//        objectNode.put("client_secret",clientSecret);
//        objectNode.put("token",token.replace("Bearer ",""));

        TokenValidationRequest tokenValidationRequest = new TokenValidationRequest(clientId,clientSecret,token);
        String body = "token_type_hint=requesting_party_token&client_id="+ clientId +"&client_secret="+ clientSecret + "&token="+token.replace("Bearer ","");

//        restTemplate.setMessageConverters(messageConverters);
//        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(body,headers);

//        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML));
//        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);


        ResponseEntity<TokenIntrospectionResponse> result = restTemplate.postForEntity(uri+"auth/realms/bank/protocol/openid-connect/token/introspect", request, TokenIntrospectionResponse.class);

//        AuthzClient authzClient = AuthzClient.create();
//        AuthorizationResponse response = authzClient.authorization("test1", "kecske123").authorize();
//        String rpt = response.getToken();
//        TokenIntrospectionResponse requestingPartyToken = authzClient.protection().introspectRequestingPartyToken(token);



        //result.getBody()
        return result.getBody();
    }


//    public TokenIntrospectionResponse introspectRequestingPartyToken(String rpt) {
//        return this.http.<TokenIntrospectionResponse>post(getTokenEndpoint())
//                .authentication()
//                .client()
//                .form()
//                .param("token_type_hint", "requesting_party_token")
//                .param("token", rpt)
//                .response().json(TokenIntrospectionResponse.class).execute();
//    }
}



