package com.smartlibrary.app;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.smartlibrary.dao.BookDao;
import com.smartlibrary.dao.LoanDao;
import com.smartlibrary.dao.StudentDao;
import com.smartlibrary.entity.Book;
import com.smartlibrary.entity.Loan;
import com.smartlibrary.entity.Student;
import com.smartlibrary.util.HibernateUtil;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookDao bookDao = new BookDao();
    private static final StudentDao studentDao = new StudentDao();
    private static final LoanDao loanDao = new LoanDao();

    public static void main(String[] args) {
        // Hibernate'in aşırı loglarını kapatmak için
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        System.out.println("=== SmartLibraryPlus Başlatılıyor ===");
        
        while (true) {
            System.out.println("\n--- MENÜ ---");
            System.out.println("1 - Kitap Ekle");
            System.out.println("2 - Kitapları Listele");
            System.out.println("3 - Öğrenci Ekle");
            System.out.println("4 - Öğrencileri Listele");
            System.out.println("5 - Kitap Ödünç Ver");
            System.out.println("6 - Ödünç Listesini Görüntüle");
            System.out.println("7 - Kitap Geri Teslim Al");
            System.out.println("0 - Çıkış");
            System.out.print("Seçiminiz: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir sayı giriniz.");
                continue;
            }

            switch (choice) {
                case 1: kitapEkle(); break;
                case 2: kitaplariListele(); break;
                case 3: ogrenciEkle(); break;
                case 4: ogrencileriListele(); break;
                case 5: kitapOduncVer(); break;
                case 6: oduncListesi(); break;
                case 7: kitapIadeAl(); break;
                case 0:
                    System.out.println("Çıkış yapılıyor...");
                    HibernateUtil.shutdown();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Geçersiz seçim.");
            }
        }
    }

    private static void kitapEkle() {
        System.out.print("Kitap Başlığı: ");
        String title = scanner.nextLine();
        System.out.print("Yazar: ");
        String author = scanner.nextLine();
        System.out.print("Yıl: ");
        int year = Integer.parseInt(scanner.nextLine());

        Book book = new Book(title, author, year);
        bookDao.save(book);
        System.out.println("Kitap eklendi: " + title);
    }

    private static void kitaplariListele() {
        List<Book> books = bookDao.getAll();
        if (books.isEmpty()) {
            System.out.println("Hiç kitap yok.");
        } else {
            System.out.println("--- Kitap Listesi ---");
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    private static void ogrenciEkle() {
        System.out.print("Öğrenci Adı: ");
        String name = scanner.nextLine();
        System.out.print("Bölüm: ");
        String dept = scanner.nextLine();

        Student student = new Student(name, dept);
        studentDao.save(student);
        System.out.println("Öğrenci eklendi: " + name);
    }

    private static void ogrencileriListele() {
        List<Student> students = studentDao.getAll();
        if (students.isEmpty()) {
            System.out.println("Hiç öğrenci yok.");
        } else {
            System.out.println("--- Öğrenci Listesi ---");
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    private static void kitapOduncVer() {
        System.out.print("Öğrenci ID: ");
        Long studentId = Long.parseLong(scanner.nextLine());
        System.out.print("Kitap ID: ");
        Long bookId = Long.parseLong(scanner.nextLine());

        Student student = studentDao.getById(studentId);
        Book book = bookDao.getById(bookId);

        if (student == null) {
            System.out.println("Öğrenci bulunamadı!");
            return;
        }
        if (book == null) {
            System.out.println("Kitap bulunamadı!");
            return;
        }

        if ("BORROWED".equals(book.getStatus())) {
            System.out.println("HATA: Bu kitap şu an başkasında (BORROWED)!");
            return;
        }

        // Kitabın durumunu güncelle
        book.setStatus("BORROWED");
        bookDao.update(book);

        // Ödünç kaydı oluştur
        Loan loan = new Loan(student, book, LocalDate.now());
        loanDao.save(loan);

        System.out.println("İşlem Başarılı! Kitap ödünç verildi.");
    }

    private static void oduncListesi() {
        List<Loan> loans = loanDao.getAll();
        if (loans.isEmpty()) {
            System.out.println("Ödünç kaydı yok.");
        } else {
            System.out.println("--- Ödünç Listesi ---");
            for (Loan l : loans) {
                System.out.println(l);
            }
        }
    }

    private static void kitapIadeAl() {
        System.out.print("İade edilecek Kitap ID: ");
        Long bookId = Long.parseLong(scanner.nextLine());

        // Bu kitaba ait, iade tarihi null olan kaydı bul
        Loan activeLoan = loanDao.getActiveLoanByBookId(bookId);

        if (activeLoan == null) {
            System.out.println("Bu kitap için aktif bir ödünç kaydı bulunamadı.");
            return;
        }

        // İade işlemini yap
        activeLoan.setReturnDate(LocalDate.now());
        loanDao.update(activeLoan);

        // Kitabın durumunu boşa çıkar
        Book book = activeLoan.getBook();
        book.setStatus("AVAILABLE");
        bookDao.update(book);

        System.out.println("Kitap iade alındı. Teşekkürler!");
    }
}
