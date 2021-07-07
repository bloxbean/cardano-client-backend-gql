package com.bloxbean.cardano.client.backend.gql.it;

import com.bloxbean.cardano.client.backend.common.OrderEnum;
import com.bloxbean.cardano.client.backend.exception.ApiException;
import com.bloxbean.cardano.client.backend.gql.GqlAddressService;
import com.bloxbean.cardano.client.backend.gql.GqlEpochService;
import com.bloxbean.cardano.client.backend.gql.GqlUtxoService;
import com.bloxbean.cardano.client.backend.model.AddressContent;
import com.bloxbean.cardano.client.backend.model.Result;
import com.bloxbean.cardano.client.backend.model.Utxo;
import com.bloxbean.cardano.client.util.JsonUtil;

import java.util.List;

public class GraphqlApiTest {
    private final static String URL = "https://graphql-api.testnet.dandelion.link/";

    public void fetchUtxos() throws ApiException {
        String address = "addr_test1qqwpl7h3g84mhr36wpetk904p7fchx2vst0z696lxk8ujsjyruqwmlsm344gfux3nsj6njyzj3ppvrqtt36cp9xyydzqzumz82";

        GqlUtxoService gqlUtxoService = new GqlUtxoService(URL);
        Result<List<Utxo>> utxos = gqlUtxoService.getUtxos(address, 50, 0);

        System.out.println(JsonUtil.getPrettyJson(utxos.getValue()));
    }

    public void fetchAddressSummary() throws ApiException {
        String address = "addr_test1qqwpl7h3g84mhr36wpetk904p7fchx2vst0z696lxk8ujsjyruqwmlsm344gfux3nsj6njyzj3ppvrqtt36cp9xyydzqzumz82";

        GqlAddressService gqlAddressService = new GqlAddressService(URL);
        Result<AddressContent> addressContentResult = gqlAddressService.getAddressInfo(address);
//        System.out.println(JsonUtil.getPrettyJson(addressContentResult));
        System.out.println(JsonUtil.getPrettyJson(addressContentResult.getValue()));
    }

    public void fetchTransactions() throws ApiException {
        String address = "addr_test1qqy3df0763vfmygxjxu94h0kprwwaexe6cx5exjd92f9qfkry2djz2a8a7ry8nv00cudvfunxmtp5sxj9zcrdaq0amtqmflh6v";

        GqlAddressService gqlAddressService = new GqlAddressService(URL);
        Result<List<String>> transactions = gqlAddressService.getTransactions(address, 10, 0, OrderEnum.asc);
//        System.out.println(JsonUtil.getPrettyJson(addressContentResult));
        System.out.println(JsonUtil.getPrettyJson(transactions.getValue()));
    }

    public void fetchLatestEpoch() throws ApiException {
        GqlEpochService gqlEpochService = new GqlEpochService(URL);
        System.out.println(JsonUtil.getPrettyJson(gqlEpochService.getLatestEpoch().getValue()));
    }

    public void fetchEpoch() throws ApiException {
        GqlEpochService gqlEpochService = new GqlEpochService(URL);
        System.out.println(JsonUtil.getPrettyJson(gqlEpochService.getEpoch(141).getValue()));
    }

    public void fetchLatestProtocolParams() throws ApiException {
        GqlEpochService gqlEpochService = new GqlEpochService(URL);
        System.out.println(JsonUtil.getPrettyJson(gqlEpochService.getProtocolParameters().getValue()));
    }

    public void fetchProtocolParamsByEpoch() throws ApiException {
        GqlEpochService gqlEpochService = new GqlEpochService(URL);
        System.out.println(JsonUtil.getPrettyJson(gqlEpochService.getProtocolParameters(80).getValue()));
    }

    public static void main(String[] args) throws ApiException {
//        new Test().fetchUtxos();
//        new Test().fetchAddressSummary();
//        new Test().fetchTransactions();
//        new Test().fetchLatestEpoch();
//        new Test().fetchEpoch();
        new GraphqlApiTest().fetchLatestProtocolParams();
        new GraphqlApiTest().fetchProtocolParamsByEpoch();
    }
}
