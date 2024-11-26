package com.webcinema.service.branch;

import com.webcinema.extension.InternalServerException;
import com.webcinema.extension.ResourceNotFoundException;
import com.webcinema.model.Branch;
import com.webcinema.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService implements IBranchService{
    private final BranchRepository branchRepository;


    @Override
    public Branch addBranch(MultipartFile img, String name, String address, String phoneNumber) throws SQLException, IOException {
        Branch newBranch = new Branch();

        if(!img.isEmpty()){
            byte[] photoBytes = img.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            newBranch.setImg(photoBlob);
        }
        newBranch.setName(name);
        newBranch.setAddress(address);
        newBranch.setPhoneNumber(phoneNumber);
        return branchRepository.save(newBranch);
    }

    @Override
    public Branch updateBranch(Long id, byte[]  photoBytes, String name, String address, String phoneNumber) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sorry, Branch not found!"));

        if(name != null) branch.setName(name);
        if(address != null) branch.setAddress(address);
        if(phoneNumber != null) branch.setPhoneNumber(phoneNumber);
        if(photoBytes != null && photoBytes.length > 0){
            try {
                branch.setImg(new SerialBlob(photoBytes));
            } catch (SQLException ex){
                new InternalServerException("Error updating branch");
            }
        }
        return branchRepository.save(branch);
    }

    @Override
    public byte[] getBranchPhotoById(Long branchId) throws SQLException {
        Optional<Branch> branch = branchRepository.findById(branchId);
        if(branch.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Branch not found!");
        }
        Blob imgBranch = branch.get().getImg();
        if(imgBranch != null){
            return imgBranch.getBytes(1, (int)imgBranch.length());
        }
        return null;
    }

    @Override
    public List<Branch> getAllBranch() {
        return branchRepository.findAll();
    }

    @Override
    public void deleteBranch(Long id) {
        Optional<Branch> branch = branchRepository.findById(id);

        if(branch.isPresent()){
            branchRepository.deleteById(id);
        }
    }

    @Override
    public List<String> allNameBranch() {
        return branchRepository.findAllNameBranch();
    }

    @Override
    public Branch getBrandById(Long id) {
        Optional<Branch> branch = branchRepository.findById(id);

        if(branch.isPresent()){
            return branch.get();
        }
        return null;
    }
}
