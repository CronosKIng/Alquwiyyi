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
        loadClasses();
        loadStudents();
        loadSubjects();
        loadResults();
        loadPayments();
        loadInvoices();
    }
    
    // ==================== CLASS METHODS ====================
    public void saveClasses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "classes.txt"))) {
            for (Class c : classes) {
                writer.println(c.getId() + "|" + c.getClassName() + "|" + c.getDescription());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadClasses() {
        classes.clear();
        File file = new File(DATA_DIR + "classes.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    Class c = new Class(parts[1], parts[2]);
                    c.setId(parts[0]);
                    classes.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addClass(Class newClass) {
        newClass.setId(generateId("CLS"));
        classes.add(newClass);
        saveClasses();
    }
    
    public List<Class> getAllClasses() {
        return classes;
    }
    
    // ==================== STUDENT METHODS ====================
    public void saveStudents() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "students.txt"))) {
            for (Student s : students) {
                writer.println(s.getId() + "|" + s.getFullName() + "|" + s.getClassName() + "|" + 
                              s.getParentName() + "|" + s.getParentPhone() + "|" + s.getAdmissionDate());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadStudents() {
        students.clear();
        File file = new File(DATA_DIR + "students.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    Student s = new Student(parts[1], parts[2], parts[3], parts[4]);
                    s.setId(parts[0]);
                    students.add(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addStudent(Student student) {
        student.setId(generateId("STU"));
        students.add(student);
        saveStudents();
    }
    
    public List<Student> getAllStudents() { 
        return students; 
    }
    
    // ==================== SUBJECT METHODS ====================
    public void saveSubjects() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "subjects.txt"))) {
            for (Subject s : subjects) {
                writer.println(s.getId() + "|" + s.getSubjectName() + "|" + s.getClassName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadSubjects() {
        subjects.clear();
        File file = new File(DATA_DIR + "subjects.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    Subject sub = new Subject(parts[1], parts[2]);
                    sub.setId(parts[0]);
                    subjects.add(sub);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addSubject(Subject subject) {
        subject.setId(generateId("SUB"));
        subjects.add(subject);
        saveSubjects();
    }
    
    public List<Subject> getSubjectsByClass(String className) {
        List<Subject> filtered = new ArrayList<>();
        for (Subject s : subjects) {
            if (s.getClassName().equals(className)) {
                filtered.add(s);
            }
        }
        return filtered;
    }
    
    public List<Subject> getAllSubjects() { 
        return subjects; 
    }
    
    // ==================== RESULT METHODS ====================
    public void saveResults() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "results.txt"))) {
            for (Result r : results) {
                writer.println(r.getId() + "|" + r.getStudentId() + "|" + r.getStudentName() + "|" + 
                              r.getClassName() + "|" + r.getGrade() + "|" + r.getDivision() + "|" + r.getTotalMarks());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadResults() {
        results.clear();
        File file = new File(DATA_DIR + "results.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    Result r = new Result(parts[1], parts[2], parts[3]);
                    r.setId(parts[0]);
                    results.add(r);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addResult(Result result) {
        result.setId(generateId("RES"));
        results.add(result);
        saveResults();
    }
    
    public List<Result> getResultsByClass(String className) {
        List<Result> filtered = new ArrayList<>();
        for (Result r : results) {
            if (r.getClassName().equals(className)) {
                filtered.add(r);
            }
        }
        return filtered;
    }
    
    public List<Result> getAllResults() { 
        return results; 
    }
    
    // ==================== PAYMENT METHODS ====================
    public void savePayments() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "payments.txt"))) {
            for (Payment p : payments) {
                writer.println(p.getId() + "|" + p.getAmount() + "|" + p.getDescription() + "|" + p.getDate());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadPayments() {
        payments.clear();
        File file = new File(DATA_DIR + "payments.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    Payment p = new Payment("", "", Double.parseDouble(parts[1]), parts[2]);
                    p.setId(parts[0]);
                    payments.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addPayment(Payment payment) {
        payment.setId(generateId("PAY"));
        payments.add(payment);
        savePayments();
    }
    
    public List<Payment> getAllPayments() {
        return payments;
    }
    
    // ==================== INVOICE METHODS ====================
    public void saveInvoices() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "invoices.txt"))) {
            for (Invoice i : invoices) {
                writer.println(i.getId() + "|" + i.getAmount() + "|" + i.getDescription() + "|" + i.getDate() + "|" + i.getStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadInvoices() {
        invoices.clear();
        File file = new File(DATA_DIR + "invoices.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    Invoice i = new Invoice("", "", Double.parseDouble(parts[1]), parts[2]);
                    i.setId(parts[0]);
                    invoices.add(i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addInvoice(Invoice invoice) {
        invoice.setId(generateId("INV"));
        invoices.add(invoice);
        saveInvoices();
    }
    
    public List<Invoice> getAllInvoices() {
        return invoices;
    }
    
    private String generateId(String prefix) {
        return prefix + "_" + System.currentTimeMillis();
    }
}
