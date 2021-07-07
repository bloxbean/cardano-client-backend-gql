package com.bloxbean.cardano.client.backend.gql.it;

public class GqlBaseTest {
    public String authKey;

    public GqlBaseTest() {
        authKey = System.getProperty("CARDANO_GRAPHQL_AUTH_KEY");
        if(authKey == null || authKey.isEmpty()) {
            authKey = System.getenv("CARDANO_GRAPHQL_AUTH_KEY");
        }

//        authKey = "";

    }
}
