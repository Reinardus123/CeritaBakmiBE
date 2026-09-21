package com.CeritaBakmiBE.CB.serviceImpl;

import com.CeritaBakmiBE.CB.entity.Branch;
import com.CeritaBakmiBE.CB.repository.BranchRepository;
import com.CeritaBakmiBE.CB.request.BranchRequest;
import com.CeritaBakmiBE.CB.response.BranchResponse;
import com.CeritaBakmiBE.CB.service.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;


    @Override
    public BranchResponse addBranch(BranchRequest branchRequest) throws IOException {


        Branch branch = new Branch();
        branch.setBranchName(branchRequest.getBranchName());
        branch.setAddress(branchRequest.getAddress());
        branch.setPhoneNumber(branchRequest.getPhoneNumber());

        Branch saveBranch = branchRepository.save(branch);

        return new BranchResponse(
                saveBranch.getBranchId(),
                saveBranch.getBranchName(),
                saveBranch.getAddress(),
                saveBranch.getPhoneNumber(),
                saveBranch.isActive()
        );
    }

    @Override
    public void removeBranch(long id) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "cabang tidak ada"
                        ));
        branch.setActive(false);
        branchRepository.save(branch);

    }
    @Override
    public List<BranchResponse> getAllBranch() {
        return branchRepository.findAll()
                .stream()
                .map(branch -> new BranchResponse(
                        branch.getBranchId(),
                        branch.getBranchName(),
                        branch.getAddress(),
                        branch.getPhoneNumber(),
                        branch.isActive()
                ))
                .toList();
    }

    @Override
    public BranchResponse updateBranch(long branchId, BranchRequest branchRequest) throws IOException{

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "cabang tidak ada"
                        ));
        branch.setBranchName(branchRequest.getBranchName());
        branch.setPhoneNumber(branchRequest.getPhoneNumber());
        branch.setAddress(branchRequest.getAddress());

        Branch savebranch = branchRepository.save(branch);



        return new BranchResponse(
                savebranch.getBranchId(),
                savebranch.getBranchName(),
                savebranch.getAddress(),
                savebranch.getPhoneNumber(),
                savebranch.isActive()
        );
    }
}
