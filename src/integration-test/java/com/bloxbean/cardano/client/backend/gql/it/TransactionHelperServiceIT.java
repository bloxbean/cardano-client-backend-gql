package com.bloxbean.cardano.client.backend.gql.it;

import com.bloxbean.cardano.client.account.Account;
import com.bloxbean.cardano.client.backend.api.BackendService;
import com.bloxbean.cardano.client.backend.api.BlockService;
import com.bloxbean.cardano.client.backend.api.TransactionService;
import com.bloxbean.cardano.client.backend.api.helper.FeeCalculationService;
import com.bloxbean.cardano.client.backend.api.helper.TransactionHelperService;
import com.bloxbean.cardano.client.backend.api.helper.model.TransactionResult;
import com.bloxbean.cardano.client.backend.exception.ApiException;
import com.bloxbean.cardano.client.backend.gql.GqlBackendService;
import com.bloxbean.cardano.client.backend.model.Block;
import com.bloxbean.cardano.client.backend.model.Result;
import com.bloxbean.cardano.client.backend.model.TransactionContent;
import com.bloxbean.cardano.client.common.model.Networks;
import com.bloxbean.cardano.client.exception.AddressExcepion;
import com.bloxbean.cardano.client.exception.CborSerializationException;
import com.bloxbean.cardano.client.transaction.model.PaymentTransaction;
import com.bloxbean.cardano.client.transaction.model.TransactionDetailsParams;
import com.bloxbean.cardano.client.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TransactionHelperServiceIT {
    TransactionHelperService transactionHelperService;
    FeeCalculationService feeCalculationService;
    private TransactionService transactionService;
    private BlockService blockService;

    @BeforeEach
    public void setup() {
        BackendService backendService = new GqlBackendService(Constant.GQL_URL);
        transactionService = backendService.getTransactionService();
        transactionHelperService = backendService.getTransactionHelperService();
        feeCalculationService = backendService.getFeeCalculationService();
        blockService = backendService.getBlockService();
    }

    @Test
    void transfer() throws CborSerializationException, AddressExcepion, ApiException {
        String senderMnemonic = "damp wish scrub sentence vibrant gauge tumble raven game extend winner acid side amused vote edge affair buzz hospital slogan patient drum day vital";
        Account sender = new Account(Networks.testnet(), senderMnemonic);
        String receiver = "addr_test1qqwpl7h3g84mhr36wpetk904p7fchx2vst0z696lxk8ujsjyruqwmlsm344gfux3nsj6njyzj3ppvrqtt36cp9xyydzqzumz82";

        PaymentTransaction paymentTransaction =
                PaymentTransaction.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .amount(BigInteger.valueOf(1500000))
                        .unit("lovelace")
                        .build();

        BigInteger fee = feeCalculationService.calculateFee(paymentTransaction, TransactionDetailsParams.builder().ttl(getTtl()).build(), null);

        paymentTransaction.setFee(fee);

        Result<TransactionResult> result = transactionHelperService.transfer(paymentTransaction, TransactionDetailsParams.builder().ttl(getTtl()).build());

        if(result.isSuccessful())
            System.out.println("Transaction Id: " + result.getValue());
        else
            System.out.println("Transaction failed: " + result);

        waitForTransaction(result);

        assertThat(result.isSuccessful(), is(true));
    }

    private void waitForTransaction(Result<TransactionResult> result) {
            if (result.isSuccessful()) { //Wait for transaction to be mined
                int count = 0;
                while (count < 60) {
                    try {
                        Result<TransactionContent> txnResult = transactionService.getTransaction(result.getValue().getTransactionId());
                        if (txnResult.isSuccessful()) {
                            System.out.println(JsonUtil.getPrettyJson(txnResult.getValue()));
                            break;
                        } else {
                            System.out.println("Waiting for transaction to be mined ....");
                        }

                        count++;
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            }
    }

    private long getTtl() throws ApiException {
        Block block = blockService.getLastestBlock().getValue();
        long slot = block.getSlot();
        return slot + 2000;
    }
}
