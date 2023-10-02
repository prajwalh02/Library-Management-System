package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;
import com.example.librarymanagementsystem.exception.BookNotAvailableException;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookServiceImpl bookService;

    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {

        try {
            String response =  bookService.addBook(book);
            return response;
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    //delete a book
    @DeleteMapping("/delete/{title}")
    public ResponseEntity deleteBook(@PathVariable("title") String title) {
        try {
            String response = bookService.deleteBook(title);
            return new ResponseEntity(response, HttpStatus.ACCEPTED);
        }
        catch(BookNotAvailableException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //give me a names of all the books of the particular genre


    //give me a names of all the books of the particular genre and having cost > x Rs
    @GetMapping("/get-by-genre-cost")
    public List<BookResponse> getBooksByGenreAndCostGreaterThan(@RequestParam("genre") String genre,
                                                                @RequestParam("cost") double cost) {
        return bookService.getBooksByGenreAndCostGreaterThan(genre, cost);
    }

    @GetMapping("/get-by-genre-cost-hql")
    public List<BookResponse> getBooksByGenreAndCostGreaterThanHQL(@RequestParam("genre") Genre genre,
                                                                   @RequestParam("cost") double cost) {
        return bookService.getBooksByGenreAndCostGreaterThanHQL(genre, cost);
    }


    //give me a all the books having number of pages between 'a' and 'b'
    @GetMapping("/get-by-no-of-pages")
    public List<BookResponse> getBooksByNoOfPagesBetweenAandB(@RequestParam("a") int a,
                                                              @RequestParam("b") int b) {
        return bookService.getBooksByNoOfPagesBetweenAandB(a, b);
    }


    //give me the names of all the authors who write a particular genre


}
