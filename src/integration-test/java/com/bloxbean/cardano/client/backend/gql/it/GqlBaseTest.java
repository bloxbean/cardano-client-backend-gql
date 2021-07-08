package com.bloxbean.cardano.client.backend.gql.it;

import com.bloxbean.cardano.client.backend.gql.GqlBackendService;

public class GqlBaseTest {
    protected String authKey;
    protected GqlBackendService backendService;

    public GqlBaseTest() {
        authKey = System.getProperty("CARDANO_GRAPHQL_AUTH_KEY");
        if(authKey == null || authKey.isEmpty()) {
            authKey = System.getenv("CARDANO_GRAPHQL_AUTH_KEY");
        }

        backendService = new GqlBackendService(Constant.GQL_URL);
//        authKey = "";

    }
}
