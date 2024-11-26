package com.webcinema.controller;

import com.webcinema.dto.BranchDto;
import com.webcinema.model.Branch;
import com.webcinema.service.branch.IBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/branch")
public class BranchController {
    private final IBranchService branchService;

    @PostMapping("/add")
    public ResponseEntity<BranchDto> addBrand(@RequestParam("imgBranch") MultipartFile img,
                                               @RequestParam("nameBranch") String name,
                                               @RequestParam("address") String address,
                                               @RequestParam("phoneNumber") String phoneNumber) throws SQLException, IOException {

        BranchDto branchDto = new BranchDto();
        Branch branch = branchService.addBranch(img, name, address, phoneNumber);

        if(branch != null){
            branchDto.setStatus("Thêm thành công!");
        } else{
            branchDto.setStatus("Thêm thất bại!");
        }

        return ResponseEntity.ok(branchDto);

    }

    @PutMapping("/update/{branchId}")
    public ResponseEntity<BranchDto> updateBranch( @PathVariable Long branchId,
                                                    @RequestParam("imgBranch")MultipartFile img,
                                                    @RequestParam("nameBranch") String name,
                                                    @RequestParam("address") String address,
                                                    @RequestParam("phoneNumber") String phoneNumber) throws IOException, SQLException {

        byte[] photobytes = img != null && !img.isEmpty() ? img.getBytes() : branchService.getBranchPhotoById(branchId);

        Branch branch = branchService.updateBranch(branchId, photobytes, name, address, phoneNumber);

        BranchDto branchDto = new BranchDto();
        if(branch != null){
            branchDto.setStatus("Cập nhật thành công!");
        } else{
            branchDto.setStatus("Cập nhật thất bại!");
        }

        return ResponseEntity.ok(branchDto);
    }

    @GetMapping("/allBranch")
    public ResponseEntity<List<BranchDto>> getAllBranch() throws SQLException {
        List<BranchDto> branchDtos = new ArrayList<>();

        List<Branch> branches = branchService.getAllBranch();

        for(Branch branch : branches){
            BranchDto branchDto = new BranchDto();

            branchDto.setId(branch.getId());
            branchDto.setName(branch.getName());
            branchDto.setAddress(branch.getAddress());
            branchDto.setPhoneNumber(branch.getPhoneNumber());

            byte[] photoBytes = branchService.getBranchPhotoById(branch.getId());
            if(photoBytes != null && photoBytes.length > 0){
                String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
                branchDto.setImgBranch(base64Photo);
            }
            branchDtos.add(branchDto);
        }

        return ResponseEntity.ok(branchDtos);
    }

    @DeleteMapping("/delete/{branchId}")
    public ResponseEntity<Void> deleteBranchById(@PathVariable Long branchId){
            branchService.deleteBranch(branchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/allNameBranch")
    public List<String> allNameBranch(){
        return branchService.allNameBranch();
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long branchId) throws SQLException {
        BranchDto branchDto = new BranchDto();

        Branch branch = branchService.getBrandById(branchId);
        branchDto.setId(branch.getId());
        branchDto.setName(branch.getName());
        branchDto.setAddress(branch.getAddress());
        branchDto.setPhoneNumber(branchDto.getPhoneNumber());

        Blob photoBranch = branch.getImg();
        byte[] photoBytes = photoBranch.getBytes(1, (int)photoBranch.length());

        if(photoBytes != null && photoBytes.length > 0){
            String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
            branchDto.setImgBranch(base64Photo);
        }
        return ResponseEntity.ok(branchDto);
    }
}
