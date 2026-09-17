package com.CeritaBakmiBE.CB.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class BranchResponse {

    private long branchId;
    private String branchName;
    private String address;
    private boolean isActive;
}
