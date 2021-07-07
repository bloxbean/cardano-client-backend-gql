package com.bloxbean.cardano.client.backend.gql;

import com.bloxbean.cardano.client.backend.api.UtxoService;
import com.bloxbean.cardano.client.backend.common.OrderEnum;
import com.bloxbean.cardano.client.backend.exception.ApiException;
import com.bloxbean.cardano.client.backend.model.Amount;
import com.bloxbean.cardano.client.backend.model.Result;
import com.bloxbean.cardano.client.backend.model.Utxo;
import com.bloxbean.cardano.gql.UtxosQuery;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bloxbean.cardano.client.common.CardanoConstants.LOVELACE;

public class GqlUtxoService extends BaseGqlService implements UtxoService {

    public GqlUtxoService(String gqlUrl) {
        super(gqlUrl);
    }

    @Override
    public Result<List<Utxo>> getUtxos(String address, int count, int page) throws ApiException {
        if(page > 0)
            page = page - 1;
        int offset = count * page;

        UtxosQuery.Data data = execute(new UtxosQuery(address, count, offset));
        if(data == null || data.utxos() == null || data.utxos().size() == 0)
            return Result.error("No utxos found");

        List<Utxo> utxos = data.utxos().stream()
                .map(ux -> {
                    List<Amount> amounts = new ArrayList<>();
                    Amount lovelaceAmt = Amount.builder()
                            .unit(LOVELACE)
                            .quantity(new BigInteger(ux.value()))
                            .build();
                    amounts.add(lovelaceAmt);

                    for(UtxosQuery.Token token: ux.tokens()) {
                        amounts.add(Amount.builder()
                            .unit(token.asset().assetId())
                                .quantity(new BigInteger(token.quantity()))
                                .build()
                        );
                    }

                    Utxo utxo = Utxo.builder()
                            .txHash((String) ux.txHash())
                            .outputIndex(ux.index())
                            .amount(amounts).build();
                    return utxo;
                }).collect(Collectors.toList());

        return processSuccessResult(utxos);
    }

    @Override
    public Result<List<Utxo>> getUtxos(String address, int count, int page, OrderEnum order) throws ApiException {
        return getUtxos(address, count, page);
    }

}
