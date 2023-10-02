package com.example.librarymanagementsystem.service.impl;

import com.example.librarymanagementsystem.Enum.TransactionStatus;
import com.example.librarymanagementsystem.dto.responseDTO.IssueBookResponse;
import com.example.librarymanagementsystem.exception.BookNotAvailableException;
import com.example.librarymanagementsystem.exception.StudentNotFoundException;
import com.example.librarymanagementsystem.exception.TransactionNotFoundException;
import com.example.librarymanagementsystem.mail.MailComposer;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.LibraryCard;
import com.example.librarymanagementsystem.model.Student;
import com.example.librarymanagementsystem.model.Transaction;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.repository.StudentRepository;
import com.example.librarymanagementsystem.repository.TransactionRepository;
import com.example.librarymanagementsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TransactionRepository transactionRepository;

    public IssueBookResponse issueBook(int bookId, int studentId) {

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException("Invalid Student Id !!");
        }

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isEmpty()) {
            throw new BookNotAvailableException("Invalid Book Id !!");
        }

        Book book = bookOptional.get();
        if(book.isIssued()) {
            throw new BookNotAvailableException("Book already Issued");
        }

        // issue the book
        Student student = studentOptional.get();

        //create a transaction
        Transaction transaction = Transaction.builder()
                .transactionNumber(String.valueOf(UUID.randomUUID()))
                .transactionStatus(TransactionStatus.SUCCESS)
                .book(book)
                .libraryCard(student.getLibraryCard())
                .build();

        //this will have the primary key of transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        //update book
        book.setIssued(true);
        book.getTransactions().add(savedTransaction);

        //card changes
        student.getLibraryCard().getTransactions().add(savedTransaction);

        Book savedBook = bookRepository.save(book);         //save book and transaction
        Student savedStudent = studentRepository.save(student);    //save student and transaction


        //  send an email
        SimpleMailMessage message = MailComposer.composeIssueBookEmail(savedBook, savedStudent, savedTransaction);
        javaMailSender.send(message);


//        String text = "Hi !!, " + student.getName() + " \n\n\tThe below Book has been issued to you\n" +
//                book.getTitle() + " \n\n\tThis is the transaction number: "+savedTransaction.getTransactionNumber() +
//                " \n\tTransaction Date & Time: " + savedTransaction.getTransactionTime();
//
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom("prajwalspring@gmail.com");
//        simpleMailMessage.setTo(student.getEmail());
//        simpleMailMessage.setSubject("Congrats!! Book Issued");
//        simpleMailMessage.setText(text);
//
//        javaMailSender.send(simpleMailMessage);

        //prepare response
        return IssueBookResponse.builder()
                .bookName(savedBook.getTitle())
                .transactionStatus(savedTransaction.getTransactionStatus())
                .transactionTime(savedTransaction.getTransactionTime())
                .transactionNumber(savedTransaction.getTransactionNumber())
                .studentName(savedStudent.getName())
                .libraryCardNumber(savedStudent.getLibraryCard().getCardNo())
                .authorName(savedBook.getAuthor().getName())
                .build();

    }

    @Override
    public void releaseBook(int transactionId) {
        // check if transaction exists
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if(transactionOptional.isEmpty()){
            throw new TransactionNotFoundException("Invalid transaction Id!");
        }

        // get transaction
        Transaction transaction = transactionOptional.get();

        // get corresponding book and library card
        Book book = transaction.getBook();
        LibraryCard libraryCard = transaction.getLibraryCard();

        // set book free
        book.setIssued(false);

        // remove transaction from its parent tables i.e. book and library card
        book.getTransactions().remove(transaction);
        libraryCard.getTransactions().remove(transaction);

        // remove transaction from transaction DB
        transactionRepository.delete(transaction);

        // save parent entities to update them
        Book savedBook = bookRepository.save(book);
        Student savedStudent = studentRepository.save(libraryCard.getStudent());

        // send email
        SimpleMailMessage message = MailComposer.sendReleaseBookEmail(savedStudent, savedBook, transaction);
        javaMailSender.send(message);
    }
}
