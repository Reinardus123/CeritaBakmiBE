package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.request.BranchRequest;
import com.CeritaBakmiBE.CB.response.BranchResponse;

import java.io.IOException;
import java.util.List;

public interface BranchService {

    BranchResponse addBranch(BranchRequest branchRequest) throws IOException;
    void removeBranch(long id);
    List<BranchResponse> getAllBranch();
    BranchResponse updateBranch(long branchId, BranchRequest branchRequest) throws IOException;
}
