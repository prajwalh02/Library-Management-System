package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookServiceImpl bookServiceImpl;

    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {

        try {
            String response =  bookServiceImpl.addBook(book);
            return response;
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    //delete a code

    //give me a names of all the books of the particular genre

    //give me a names of all the books of the particular genre and having cost > x Rs
    @GetMapping("/get-by-genre-cost")
    public List<BookResponse> getBooksByGenreAndCostGreaterThan(@RequestParam("genre") String genre,
                                                                @RequestParam("cost") double cost) {
        return bookServiceImpl.getBooksByGenreAndCostGreaterThan(genre, cost);
    }

    @GetMapping("/get-by-genre-cost-hql")
    public List<BookResponse> getBooksByGenreAndCostGreaterThanHQL(@RequestParam("genre") Genre genre,
                                                                   @RequestParam("cost") double cost) {
        return bookServiceImpl.getBooksByGenreAndCostGreaterThanHQL(genre, cost);
    }


    //give me a all the books having number of pages between 'a' and 'b'
    @GetMapping("/get-by-no-of-pages")
    public List<BookResponse> getBooksByNoOfPagesBetweenAandB(@RequestParam("a") int a,
                                                              @RequestParam("b") int b) {
        return bookServiceImpl.getBooksByNoOfPagesBetweenAandB(a, b);
    }


    //give me the names of all the authors who write a particular genre


}
