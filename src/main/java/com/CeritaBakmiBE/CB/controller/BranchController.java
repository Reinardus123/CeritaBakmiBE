package com.CeritaBakmiBE.CB.controller;

import com.CeritaBakmiBE.CB.request.BranchRequest;
import com.CeritaBakmiBE.CB.response.BranchResponse;
import com.CeritaBakmiBE.CB.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/branch")
@Tag(name = "Branch Endpoint", description = "Branch CRUD  operations")
public class BranchController {

    private final BranchService branchService;

    @Operation(summary = "Create new branch", description = "Create new Branch")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/createBranch")
    public BranchResponse createBranch(@Valid BranchRequest branchRequest) throws IOException {
        return branchService.addBranch(branchRequest);
    }

    @Operation(summary = "Get all branch", description = "Get all branch")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/getBranch")
    public List<BranchResponse> getAllMenu(){
        return branchService.getAllBranch();
    }

    @Operation(summary = "Soft delete branch", description = "Soft delete Branch")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void>deleteBranch(@Valid @PathVariable long id){
        branchService.removeBranch(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update Branch",description = "update branch")
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping(value = "/update/{id}")
    public BranchResponse updateBranch(@PathVariable long id, @Valid BranchRequest branchRequest) throws IOException{
        return branchService.updateBranch(id, branchRequest);
    }


}
