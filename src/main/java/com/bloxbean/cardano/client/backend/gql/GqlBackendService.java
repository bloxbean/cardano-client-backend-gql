package com.bloxbean.cardano.client.backend.gql;

import com.bloxbean.cardano.client.backend.api.*;

public class GqlBackendService implements BackendService {
    private String gqlUrl;
    public GqlBackendService(String gqlUrl) {
        this.gqlUrl = gqlUrl;
    }

    @Override
    public AssetService getAssetService() {
        return new GqlAssetService(this.gqlUrl);
    }

    @Override
    public BlockService getBlockService() {
        return new GqlBlockService(this.gqlUrl);
    }

    @Override
    public NetworkInfoService getNetworkInfoService() {
        return new GqlNetworkInfoService(this.gqlUrl);
    }

    @Override
    public TransactionService getTransactionService() {
        return new GqlTransactionService(this.gqlUrl);
    }

    @Override
    public UtxoService getUtxoService() {
        return new GqlUtxoService(this.gqlUrl);
    }

    @Override
    public AddressService getAddressService() {
        return new GqlAddressService(this.gqlUrl);
    }

    @Override
    public EpochService getEpochService() {
        return new GqlEpochService(this.gqlUrl);
    }

    @Override
    public MetadataService getMetadataService() {
        return new GqlMetadataService(this.gqlUrl);
    }
}
