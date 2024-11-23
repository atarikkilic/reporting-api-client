package com.finhouse.model.response;

import lombok.Data;
import java.util.List;

@Data
public class TransactionQueryResponse {
    private int perPage;
    private int currentPage;
    private String nextPageUrl;
    private String prevPageUrl;
    private int from;
    private int to;
    private List<TransactionData> data;

    @Data
    public static class TransactionData {
        private FxData fx;
        private CustomerInfo customerInfo;
        private MerchantInfo merchant;
        private IpnInfo ipn;
        private TransactionInfo transaction;
        private AcquirerInfo acquirer;
        private boolean refundable;
    }

    @Data
    public static class FxData {
        private MerchantFx merchant;
    }

    @Data
    public static class MerchantFx {
        private double originalAmount;
        private String originalCurrency;
    }

    @Data
    public static class CustomerInfo {
        private String number;
        private String email;
        private String billingFirstName;
        private String billingLastName;
    }

    @Data
    public static class MerchantInfo {
        private Integer id;
        private String name;
    }

    @Data
    public static class IpnInfo {
        private boolean received;
    }

    @Data
    public static class TransactionInfo {
        private String referenceNo;
        private String status;
        private String operation;
        private String message;
        private String createdAt;
        private String transactionId;
    }

    @Data
    public static class AcquirerInfo {
        private Integer id;
        private String name;
        private String code;
        private String type;
    }
}