package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.responseDTO.IssueBookResponse;

public interface TransactionService {

    IssueBookResponse issueBook(int bookId, int studentId);

    void releaseBook(int transactionId);
}
