package com.example.restfulapi.controller;

import com.example.restfulapi.dto.request.AccountRequest;
import com.example.restfulapi.dto.response.AccountResponse;
import com.example.restfulapi.dto.response.ResourcePaginationResponse;
import com.example.restfulapi.dto.response.ResourceResponse;
import com.example.restfulapi.entity.Role;
import com.example.restfulapi.service.AccountService;
import com.example.restfulapi.service.RoleSevice;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AccountController {


    private final AccountService accountService;
    private final RoleSevice roleSevice;

    @GetMapping("/accounts")
    public ResourcePaginationResponse<List<AccountResponse>> getAllAccount(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size) {
        return accountService.getAllUser(page, size);
    }

    @GetMapping("/accounts/{id}")
    public ResourceResponse<AccountResponse> getAccountById(@PathVariable int id) {
        AccountResponse account = accountService.getUserById(id);
        return ResourceResponse.<AccountResponse>builder().message("delelte successfully Account").status(true).data(account).build();
    }

    @PostMapping("/accounts")
    public ResourceResponse<?> createAccount(@RequestBody AccountRequest accountRequest) {
        accountService.createAccount(accountRequest);
        return ResourceResponse.builder().status(true).message("Create Account successfully").build();
    }

    @DeleteMapping("/accounts/{id}")
    public ResourceResponse<?> DeleteAccountById(@PathVariable int id) {
        accountService.DeleteUserById(id);
        return ResourceResponse.builder().message("get all Account").status(true).build();
    }

    @PutMapping("/accounts/{id}")
    public ResourceResponse<?> updateAccountById(@PathVariable int id, @RequestBody AccountRequest acc) {
        accountService.updateUserById(id, acc);
        return ResourceResponse.builder().message("update Successfully").status(true).build();
    }
    @PatchMapping("/accounts/block/{id}")
    public ResourceResponse<?> blockAccount(@PathVariable Integer id, @RequestParam Boolean block) {
        accountService.blockAccount(id, block);
        return ResourceResponse.builder().message("This account is blocked Successfully").status(true).build();
    }

    @GetMapping("/roles")
    public ResourceResponse<List<Role>> getAllRole() {
        List<Role> roles = roleSevice.getAll();
        return ResourceResponse.<List<Role>>builder().message("all role").status(true).data(roles).build();
    }

}
