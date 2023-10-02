package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.responseDTO.IssueBookResponse;
import com.example.librarymanagementsystem.exception.TransactionNotFoundException;
import com.example.librarymanagementsystem.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    TransactionServiceImpl transactionService;

    @PostMapping("/issue/book-id/{book-id}/student-id/{student-id}")
    public ResponseEntity issueBook(@PathVariable("book-id") int bookId,
                                    @PathVariable("student-id")int studentId) {
        try{
            IssueBookResponse response = transactionService.issueBook(bookId, studentId);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //return book api

    @DeleteMapping("/release-book")
    public ResponseEntity releaseBook(@RequestParam("transactionId") int transactionId) {
        try{
            transactionService.releaseBook(transactionId);
            return new ResponseEntity("Book has been released!", HttpStatus.OK);
        }
        catch (TransactionNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
