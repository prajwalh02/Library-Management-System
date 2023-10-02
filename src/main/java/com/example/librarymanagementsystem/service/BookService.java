package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;
import com.example.librarymanagementsystem.model.Book;

import java.util.List;

public interface BookService {

    public String addBook(Book book);

    public List<BookResponse> getBooksByGenreAndCostGreaterThan(String genre, double cost);

    public List<BookResponse> getBooksByGenreAndCostGreaterThanHQL(Genre genre, double cost);

    public List<BookResponse> getBooksByNoOfPagesBetweenAandB(int a, int b);

    String deleteBook(String title);
}
