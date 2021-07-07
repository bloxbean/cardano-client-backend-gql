package com.bloxbean.cardano.client.backend.gql.it;

import com.bloxbean.cardano.client.backend.exception.ApiException;
import com.bloxbean.cardano.client.backend.gql.GqlAddressService;
import com.bloxbean.cardano.client.backend.model.AddressContent;
import com.bloxbean.cardano.client.backend.model.Result;
import com.bloxbean.cardano.client.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GqlAddressServiceTest {
    GqlAddressService gqlAddressService;

    @BeforeEach
    public void setup() {
        gqlAddressService = new GqlAddressService(Constant.GQL_URL);
    }

    @Test
    public void testFetchAddressSummary() throws ApiException {
        String address = "addr_test1qrynkm9vzsl7vrufzn6y4zvl2v55x0xwc02nwg00x59qlkxtsu6q93e6mrernam0k4vmkn3melezkvgtq84d608zqhnsn48axp";

        Result<AddressContent> addressContentResult = gqlAddressService.getAddressInfo(address);
        System.out.println(JsonUtil.getPrettyJson(addressContentResult.getValue()));
        Assertions.assertNotNull(addressContentResult.getValue());
        Assertions.assertTrue(addressContentResult.getValue().getAmount().size() > 0);
    }

}
