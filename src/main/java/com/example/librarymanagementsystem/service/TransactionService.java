package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.responseDTO.IssueBookResponse;

public interface TransactionService {

    public IssueBookResponse issueBook(int bookId, int studentId);
}
