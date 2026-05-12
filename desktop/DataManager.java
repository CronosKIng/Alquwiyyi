import java.io.*;
import java.util.*;

public class DataManager implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Student> students = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();
    private List<Class> classes = new ArrayList<>();
    private List<Result> results = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();
    
    private final String DATA_DIR = "data/";
    
    public DataManager() {
        new File(DATA_DIR).mkdirs();
        loadAllData();
    }
    
    @SuppressWarnings("unchecked")
    public void loadAllData() {
        loadStudents();
        loadSubjects();
        loadClasses();
        loadResults();
        loadPayments();
        loadInvoices();
    }
    
    private void loadStudents() {
        students = (List<Student>) loadFromFile("students.ser");
        if (students == null) students = new ArrayList<>();
    }
    
    private void loadSubjects() {
        subjects = (List<Subject>) loadFromFile("subjects.ser");
        if (subjects == null) subjects = new ArrayList<>();
    }
    
    public void loadClasses() {
        classes = (List<Class>) loadFromFile("classes.ser");
        if (classes == null) classes = new ArrayList<>();
    }
    
    private void loadResults() {
        results = (List<Result>) loadFromFile("results.ser");
        if (results == null) results = new ArrayList<>();
    }
    
    private void loadPayments() {
        payments = (List<Payment>) loadFromFile("payments.ser");
        if (payments == null) payments = new ArrayList<>();
    }
    
    private void loadInvoices() {
        invoices = (List<Invoice>) loadFromFile("invoices.ser");
        if (invoices == null) invoices = new ArrayList<>();
    }
    
    public void saveStudents() { saveToFile("students.ser", students); }
    public void addStudent(Student student) {
        student.setId(generateId("STU"));
        students.add(student);
        saveStudents();
    }
    public List<Student> getAllStudents() { return students; }
    
    public void saveSubjects() { saveToFile("subjects.ser", subjects); }
    public void addSubject(Subject subject) {
        subject.setId(generateId("SUB"));
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
    
    public void saveClasses() { saveToFile("classes.ser", classes); }
    public void addClass(Class newClass) {
        newClass.setId(generateId("CLS"));
        classes.add(newClass);
        saveClasses();
    }
    public List<Class> getAllClasses() { 
        loadClasses();
        return classes; 
    }
    
    public void saveResults() { saveToFile("results.ser", results); }
    public void addResult(Result result) {
        result.setId(generateId("RES"));
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
    
    public void savePayments() { saveToFile("payments.ser", payments); }
    public void addPayment(Payment payment) {
        payment.setId(generateId("PAY"));
        payments.add(payment);
        savePayments();
    }
    public List<Payment> getAllPayments() { return payments; }
    
    public void saveInvoices() { saveToFile("invoices.ser", invoices); }
    public void addInvoice(Invoice invoice) {
        invoice.setId(generateId("INV"));
        invoices.add(invoice);
        saveInvoices();
    }
    public List<Invoice> getAllInvoices() { return invoices; }
    
    private String generateId(String prefix) {
        return prefix + "_" + System.currentTimeMillis();
    }
    
    private void saveToFile(String filename, Object data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIR + filename))) {
            oos.writeObject(data);
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    private Object loadFromFile(String filename) {
        File file = new File(DATA_DIR + filename);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) { return null; }
    }
}
