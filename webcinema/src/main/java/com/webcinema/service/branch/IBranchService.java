package com.webcinema.service.branch;

import com.webcinema.model.Branch;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IBranchService {
    Branch addBranch(MultipartFile img, String name, String address, String phoneNumber) throws SQLException, IOException;

    Branch updateBranch(Long id, byte[]  photoBytes, String name, String address, String phoneNumber);

    byte[] getBranchPhotoById(Long branchId) throws SQLException;

    List<Branch> getAllBranch();

    void deleteBranch(Long id);

    List<String> allNameBranch();

    Branch getBrandById(Long id);
}
