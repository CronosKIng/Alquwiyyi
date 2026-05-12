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
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
        loadAllData();
    }
    
    public void loadAllData() {
        loadClasses();
        loadStudents();
        loadSubjects();
        loadResults();
        loadPayments();
        loadInvoices();
    }
    
    // --- CLASSES ---
    public void saveClasses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "classes.txt"))) {
            for (Class c : classes) writer.println(c.getId() + "|" + c.getClassName() + "|" + c.getDescription());
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void loadClasses() {
        classes.clear();
        File file = new File(DATA_DIR + "classes.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) { Class c = new Class(parts[1], parts[2]); c.setId(parts[0]); classes.add(c); }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void addClass(Class c) { c.setId("CLS_"+System.currentTimeMillis()); classes.add(c); saveClasses(); }
    public List<Class> getAllClasses() { return classes; }

    // --- STUDENTS ---
    public void saveStudents() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "students.txt"))) {
            for (Student s : students) writer.println(s.getId() + "|" + s.getFullName() + "|" + s.getClassName() + "|" + s.getParentName() + "|" + s.getParentPhone());
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void loadStudents() {
        students.clear();
        File file = new File(DATA_DIR + "students.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) { Student s = new Student(parts[1], parts[2], parts[3], parts[4]); s.setId(parts[0]); students.add(s); }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void addStudent(Student s) { s.setId("STU_"+System.currentTimeMillis()); students.add(s); saveStudents(); }
    public List<Student> getAllStudents() { return students; }

    // --- SUBJECTS ---
    public void saveSubjects() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "subjects.txt"))) {
            for (Subject s : subjects) writer.println(s.getId() + "|" + s.getSubjectName() + "|" + s.getClassName());
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void loadSubjects() {
        subjects.clear();
        File file = new File(DATA_DIR + "subjects.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) { Subject s = new Subject(parts[1], parts[2]); s.setId(parts[0]); subjects.add(s); }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void addSubject(Subject s) { s.setId("SUB_"+System.currentTimeMillis()); subjects.add(s); saveSubjects(); }
    public List<Subject> getAllSubjects() { return subjects; }
    public List<Subject> getSubjectsByClass(String className) {
        List<Subject> filtered = new ArrayList<>();
        for (Subject s : subjects) if (s.getClassName().equals(className)) filtered.add(s);
        return filtered;
    }

    // --- RESULTS ---
    public void saveResults() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "results.txt"))) {
            for (Result r : results) writer.println(r.getId() + "|" + r.getStudentId() + "|" + r.getStudentName() + "|" + r.getClassName() + "|" + r.getGrade() + "|" + r.getDivision() + "|" + r.getTotalMarks());
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void loadResults() {
        results.clear();
        File file = new File(DATA_DIR + "results.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) { Result r = new Result(parts[1], parts[2], parts[3]); r.setId(parts[0]); results.add(r); }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void addResult(Result r) { r.setId("RES_"+System.currentTimeMillis()); results.add(r); saveResults(); }
    public List<Result> getResultsByClass(String className) {
        List<Result> filtered = new ArrayList<>();
        for (Result r : results) if (r.getClassName().equals(className)) filtered.add(r);
        return filtered;
    }

    // --- INVOICES & PAYMENTS ---
    public void addInvoice(Invoice i) { i.setId("INV_"+System.currentTimeMillis()); invoices.add(i); saveInvoices(); }
    public void saveInvoices() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "invoices.txt"))) {
            for (Invoice i : invoices) writer.println(i.getId() + "|" + i.getAmount() + "|" + i.getDescription() + "|" + i.getDate() + "|" + i.getStatus());
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void loadInvoices() {
        invoices.clear();
        File file = new File(DATA_DIR + "invoices.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) { Invoice i = new Invoice("", "", Double.parseDouble(parts[1]), parts[2]); i.setId(parts[0]); invoices.add(i); }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public List<Invoice> getAllInvoices() { return invoices; }

    public void addPayment(Payment p) { p.setId("PAY_"+System.currentTimeMillis()); payments.add(p); savePayments(); }
    public void savePayments() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_DIR + "payments.txt"))) {
            for (Payment p : payments) writer.println(p.getId() + "|" + p.getAmount() + "|" + p.getDescription() + "|" + p.getDate());
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void loadPayments() {
        payments.clear();
        File file = new File(DATA_DIR + "payments.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4) { Payment p = new Payment("", "", Double.parseDouble(parts[1]), parts[2]); p.setId(parts[0]); payments.add(p); }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public List<Payment> getAllPayments() { return payments; }
}
