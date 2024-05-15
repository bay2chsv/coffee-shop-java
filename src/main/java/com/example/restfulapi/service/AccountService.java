package com.example.restfulapi.service;

import com.example.restfulapi.dto.request.AccountRequest;
import com.example.restfulapi.dto.response.AccountResponse;
import com.example.restfulapi.dto.response.ResourcePaginationResponse;
import com.example.restfulapi.entity.Account;
import com.example.restfulapi.entity.Role;
import com.example.restfulapi.exception.BadExecption;
import com.example.restfulapi.repository.AccountRepository;
import com.example.restfulapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class AccountService {

    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    public ResourcePaginationResponse<List<AccountResponse>> getAllUser(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Account> accounts = accountRepository.findAll(pageable);
        List<AccountResponse> result = new ArrayList<>();
        for (Account item : accounts.getContent()) {
            AccountResponse accountDTO = new AccountResponse();
            accountDTO.setId(item.getId());
            accountDTO.setEmail(item.getEmail());
            accountDTO.setRole(item.getRole());
            accountDTO.setFullName(item.getFullName());
            result.add(accountDTO);
        }
        return ResourcePaginationResponse.<List<AccountResponse>>builder().message("get category list ").status(true).data(result).page(accounts.getNumber()).size(accounts.getSize()).totalPages(accounts.getTotalPages()).totalItem(accounts.getTotalElements()).build();

    }

    public AccountResponse getUserById(Integer id) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new BadExecption("No found User with " + id));
        AccountResponse result = new AccountResponse();
        result.setId(account.getId());
        result.setFullName(account.getFullName());
        result.setRole(account.getRole());
        result.setEmail(account.getEmail());
        result.setIsBlock(account.getIsBlock());
        return result;
    }

    public void DeleteUserById(Integer id) {

        if (!accountRepository.existsById(id)) throw new BadExecption(id + " doesn't exsit");
        accountRepository.deleteById(id);
    }

    public void createAccount(AccountRequest dto) {

        Objects.requireNonNull(dto.getEmail(), "Email cannot be null");
        Objects.requireNonNull(dto.getFullName(), "Name cannot be null");
        Objects.requireNonNull(dto.getRoleId(), "Role cannot be null");
        Objects.requireNonNull(dto.getPassword(), "Password cannot be null");
        if (accountRepository.existsByEmail(dto.getEmail())) {
            throw new BadExecption("Email already exists in the database");
        }
        if (!isEmailValid(dto.getEmail())) {
            throw new BadExecption("Invalid email format ex: abc@example.com");
        }
        if (!isStrongPassword(dto.getPassword())) {
            throw new BadExecption("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
        }
        Role role = roleRepository.findById(dto.getRoleId()).orElseThrow(() -> new BadExecption(("No exist Role on DB")));
        Account newUser = Account.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .fullName(dto.getFullName())
                .role(role)
                .build();
        accountRepository.save(newUser);
//        return AuthRespone.builder().userName(newAccount.getFullName()).email(newAccount.getEmail()).message("create successfully").build();
    }
    private boolean isEmailValid(String email) {
        // Email validation using regex
        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isStrongPassword(String password) {
        // Password must be at least 8 characters long
        if (password.length() < 8) {
            return false;
        }
        // Password must contain at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        // Password must contain at least one lowercase letter
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        // Password must contain at least one digit
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        // Password must contain at least one special character
        if (!password.matches(".*[!@#$%^&*()].*")) {
            return false;
        }
        return true;
    }
    public void updateUserById(Integer id, AccountRequest accountRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new BadExecption("No found User with " + id));
        // Check if the provided email is not already associated with another account
        if (!account.getEmail().equals(accountRequest.getEmail())) {
            // Perform email uniqueness check
            if (accountRepository.existsByEmail(accountRequest.getEmail())) {
                throw new BadExecption("Email already exists in the database");
            }
            // If email is unique, update it
            account.setEmail(accountRequest.getEmail());
        }
        Role role = roleRepository.findById(accountRequest.getRoleId())
                .orElseThrow(() -> new BadExecption("No exist Role on DB"));
        account.setFullName(accountRequest.getFullName());
        account.setRole(role);
        account.setIsBlock(accountRequest.getIsBlock());
        accountRepository.save(account);
    }
    public void blockAccount(Integer id,Boolean block){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new BadExecption("No found User with " + id));
        account.setIsBlock(block);
    }
}
