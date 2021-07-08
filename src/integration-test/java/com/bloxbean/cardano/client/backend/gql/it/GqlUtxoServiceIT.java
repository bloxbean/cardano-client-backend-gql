package com.bloxbean.cardano.client.backend.gql.it;

import com.bloxbean.cardano.client.backend.api.UtxoService;
import com.bloxbean.cardano.client.backend.exception.ApiException;
import com.bloxbean.cardano.client.backend.impl.blockfrost.common.Constants;
import com.bloxbean.cardano.client.backend.impl.blockfrost.service.BFUtxoService;
import com.bloxbean.cardano.client.backend.model.Result;
import com.bloxbean.cardano.client.backend.model.Utxo;
import com.bloxbean.cardano.client.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GqlUtxoServiceIT extends GqlBaseTest {
    UtxoService utxoService;

    @BeforeEach
    public void setup() {
        this.utxoService = backendService.getUtxoService();
    }

    @Test
    public void testGetUtxos() throws ApiException, JsonProcessingException {
        String address = "addr_test1qqwpl7h3g84mhr36wpetk904p7fchx2vst0z696lxk8ujsjyruqwmlsm344gfux3nsj6njyzj3ppvrqtt36cp9xyydzqzumz82";

        Result<List<Utxo>> result = utxoService.getUtxos(address, 40, 0);

        System.out.println(JsonUtil.getPrettyJson(result.getValue()));
        assertTrue(result.isSuccessful());
        assertTrue(result.code() == 200);
        assertNotNull(result.getValue().size() > 0);
    }
}
