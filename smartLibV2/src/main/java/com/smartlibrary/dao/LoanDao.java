package com.smartlibrary.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.smartlibrary.entity.Loan;
import com.smartlibrary.util.HibernateUtil;

public class LoanDao {

    public void save(Loan loan) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(loan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void update(Loan loan) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(loan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Loan loan = session.get(Loan.class, id);
            if (loan != null) {
                session.remove(loan);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Loan getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Loan.class, id);
        }
    }

    public List<Loan> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Fetch book and student with join fetch so associations are initialized
            return session.createQuery("select l from Loan l left join fetch l.book left join fetch l.student", Loan.class).list();
        }
    }
    
    // Aktif ödünçleri bulmak için ek metod
    public Loan getActiveLoanByBookId(Long bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select l from Loan l left join fetch l.book left join fetch l.student where l.book.id = :bookId and l.returnDate IS NULL", Loan.class)
                    .setParameter("bookId", bookId)
                    .uniqueResult();
        }
    }
}
