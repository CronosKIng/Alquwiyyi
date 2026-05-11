import java.io.*;
import java.util.*;

public class DataManager {
    private List<Student> students = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();
    private List<Class> classes = new ArrayList<>();
    private List<Result> results = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();
    
    private final String DATA_DIR = "data/";
    
    public DataManager() {
        new File(DATA_DIR).mkdirs();
    }
    
    public void loadAllData() {
        loadStudents();
        loadSubjects();
        loadClasses();
        loadResults();
        loadPayments();
        loadInvoices();
    }
    
    // Student methods
    @SuppressWarnings("unchecked")
    public void loadStudents() {
        File file = new File(DATA_DIR + "students.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                students = (List<Student>) ois.readObject();
            } catch (Exception e) {
                students = new ArrayList<>();
            }
        }
    }
    
    public void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIR + "students.ser"))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addStudent(Student student) {
        student.setId("STU_" + System.currentTimeMillis());
        students.add(student);
        saveStudents();
    }
    
    public List<Student> getAllStudents() { return students; }
    
    // Subject methods
    @SuppressWarnings("unchecked")
    public void loadSubjects() {
        File file = new File(DATA_DIR + "subjects.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                subjects = (List<Subject>) ois.readObject();
            } catch (Exception e) {
                subjects = new ArrayList<>();
            }
        }
    }
    
    public void saveSubjects() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIR + "subjects.ser"))) {
            oos.writeObject(subjects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addSubject(Subject subject) {
        subject.setId("SUB_" + System.currentTimeMillis());
        subjects.add(subject);
        saveSubjects();
    }
    
    public List<Subject> getSubjectsByClass(String className) {
        List<Subject> filtered = new ArrayList<>();
        for (Subject s : subjects) {
            if (s.getClassName().equals(className)) filtered.add(s);
        }
        return filtered;
    }
    
    public List<Subject> getAllSubjects() { return subjects; }
    
    // Class methods
    @SuppressWarnings("unchecked")
    public void loadClasses() {
        File file = new File(DATA_DIR + "classes.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                classes = (List<Class>) ois.readObject();
            } catch (Exception e) {
                classes = new ArrayList<>();
            }
        }
    }
    
    public void saveClasses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIR + "classes.ser"))) {
            oos.writeObject(classes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addClass(Class newClass) {
        newClass.setId("CLS_" + System.currentTimeMillis());
        classes.add(newClass);
        saveClasses();
    }
    
    public List<Class> getAllClasses() { return classes; }
    
    // Result methods
    @SuppressWarnings("unchecked")
    public void loadResults() {
        File file = new File(DATA_DIR + "results.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                results = (List<Result>) ois.readObject();
            } catch (Exception e) {
                results = new ArrayList<>();
            }
        }
    }
    
    public void saveResults() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIR + "results.ser"))) {
            oos.writeObject(results);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addResult(Result result) {
        result.setId("RES_" + System.currentTimeMillis());
        results.add(result);
        saveResults();
    }
    
    public List<Result> getResultsByClass(String className) {
        List<Result> filtered = new ArrayList<>();
        for (Result r : results) {
            if (r.getClassName().equals(className)) filtered.add(r);
        }
        return filtered;
    }
    
    public List<Result> getAllResults() { return results; }
    
    // Payment methods
    @SuppressWarnings("unchecked")
    public void loadPayments() {
        File file = new File(DATA_DIR + "payments.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                payments = (List<Payment>) ois.readObject();
            } catch (Exception e) {
                payments = new ArrayList<>();
            }
        }
    }
    
    public void savePayments() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIR + "payments.ser"))) {
            oos.writeObject(payments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addPayment(Payment payment) {
        payment.setId("PAY_" + System.currentTimeMillis());
        payments.add(payment);
        savePayments();
    }
    
    public List<Payment> getAllPayments() { return payments; }
    
    // Invoice methods
    @SuppressWarnings("unchecked")
    public void loadInvoices() {
        File file = new File(DATA_DIR + "invoices.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                invoices = (List<Invoice>) ois.readObject();
            } catch (Exception e) {
                invoices = new ArrayList<>();
            }
        }
    }
    
    public void saveInvoices() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIR + "invoices.ser"))) {
            oos.writeObject(invoices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addInvoice(Invoice invoice) {
        invoice.setId("INV_" + System.currentTimeMillis());
        invoices.add(invoice);
        saveInvoices();
    }
    
    public List<Invoice> getAllInvoices() { return invoices; }
}
