package com.finhouse.model.response;

import lombok.Data;
import java.util.List;

@Data
public class TransactionReportResponse {
    private String status;
    private List<ReportData> response;

    @Data
    public static class ReportData {
        private int count;
        private double total;
        private String currency;
    }
}