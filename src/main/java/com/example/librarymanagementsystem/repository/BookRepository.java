package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByTitle(String title);

    List<Book> findByGenre(Genre genre);


                                       //genre is attribute :genre is variable
    @Query(value = "select * from book where genre = :genre and cost > :cost", nativeQuery = true)
    List<Book> getBooksByGenreAndCostGreaterThan(String genre, double cost);

    @Query(value = "select b from Book b where b.genre = :genre and b.cost > :cost")
    List<Book> getBooksByGenreAndCostGreaterThanHQL(Genre genre, double cost);

    /*this is how we write HQL queries using Java syntax. Just keep in mind the following things:
            1. We write table and column names as they are in our application, not in table.
            2. this HQL query does the same work as the previous one but the method of writing is in compliance with java objects, not database.
            */

    @Query(value = "select b from Book b where b.noOfPages > :a and b.noOfPages < :b")
    List<Book> getBooksByNoOfPagesBetweenAandB(int a, int b);

    /* This is how we write custom SQL queries in Spring application. This is normal SQL query. Just keep in mind two following things:
    1. we write variable with ":" before them.
    2. we keep nativeQuery to true in order to tell Hibernate that this is an original SQL query (native to SQL).
     */
}
