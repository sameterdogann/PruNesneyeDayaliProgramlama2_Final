package com.smartlibrary.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    // İlişki: Her ödünç alma işlemi bir öğrenciye aittir
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    // İlişki: Her ödünç alma işlemi bir kitaba aittir (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public Loan() {}

    public Loan(Student student, Book book, LocalDate borrowDate) {
        this.student = student;
        this.book = book;
        this.borrowDate = borrowDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    @Override
    public String toString() {
        String studentName = (student != null ? student.getName() : "-");
        String bookTitle = (book != null ? book.getTitle() : "-");
        return String.format("LoanID: %d | Öğrenci: %s | Kitap: %s | Alış: %s | İade: %s", 
            id, studentName, bookTitle, borrowDate, (returnDate == null ? "Teslim Edilmedi" : returnDate));
    }
}
