package com.bloxbean.cardano.client.backend.gql;

import com.bloxbean.cardano.client.backend.api.*;

import java.util.Map;

public class GqlBackendService implements BackendService {
    private String gqlUrl;
    private Map<String, String> headers;

    public GqlBackendService(String gqlUrl) {
        this.gqlUrl = gqlUrl;
    }

    public GqlBackendService(String gqlUrl, Map<String, String> headers) {
        this.gqlUrl = gqlUrl;
        this.headers = headers;
    }

    @Override
    public AssetService getAssetService() {
        return new GqlAssetService(this.gqlUrl, this.headers);
    }

    @Override
    public BlockService getBlockService() {
        return new GqlBlockService(this.gqlUrl, this.headers);
    }

    @Override
    public NetworkInfoService getNetworkInfoService() {
        return new GqlNetworkInfoService(this.gqlUrl, this.headers);
    }

    @Override
    public TransactionService getTransactionService() {
        return new GqlTransactionService(this.gqlUrl, this.headers);
    }

    @Override
    public UtxoService getUtxoService() {
        return new GqlUtxoService(this.gqlUrl, this.headers);
    }

    @Override
    public AddressService getAddressService() {
        return new GqlAddressService(this.gqlUrl, this.headers);
    }

    @Override
    public EpochService getEpochService() {
        return new GqlEpochService(this.gqlUrl, this.headers);
    }

    @Override
    public MetadataService getMetadataService() {
        return new GqlMetadataService(this.gqlUrl, this.headers);
    }
}
