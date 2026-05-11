import com.google.gson.*;
import com.google.gson.reflect.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataManager {
    private List<Student> students = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();
    private List<Class> classes = new ArrayList<>();
    private List<Result> results = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();
    
    private final String DATA_DIR = "data/";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
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
    
    public void saveStudents() {
        saveToFile("students.json", students);
    }
    
    public void loadStudents() {
        students = loadFromFile("students.json", new TypeToken<ArrayList<Student>>(){}.getType());
        if (students == null) students = new ArrayList<>();
    }
    
    public void addStudent(Student student) {
        student.setId(generateId("STU"));
        students.add(student);
        saveStudents();
    }
    
    public List<Student> getAllStudents() { return students; }
    
    public void saveSubjects() {
        saveToFile("subjects.json", subjects);
    }
    
    public void loadSubjects() {
        subjects = loadFromFile("subjects.json", new TypeToken<ArrayList<Subject>>(){}.getType());
        if (subjects == null) subjects = new ArrayList<>();
    }
    
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
    
    public void saveClasses() {
        saveToFile("classes.json", classes);
    }
    
    public void loadClasses() {
        classes = loadFromFile("classes.json", new TypeToken<ArrayList<Class>>(){}.getType());
        if (classes == null) classes = new ArrayList<>();
    }
    
    public void addClass(Class newClass) {
        newClass.setId(generateId("CLS"));
        classes.add(newClass);
        saveClasses();
    }
    
    public List<Class> getAllClasses() { return classes; }
    
    public void saveResults() {
        saveToFile("results.json", results);
    }
    
    public void loadResults() {
        results = loadFromFile("results.json", new TypeToken<ArrayList<Result>>(){}.getType());
        if (results == null) results = new ArrayList<>();
    }
    
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
    
    public void savePayments() {
        saveToFile("payments.json", payments);
    }
    
    public void loadPayments() {
        payments = loadFromFile("payments.json", new TypeToken<ArrayList<Payment>>(){}.getType());
        if (payments == null) payments = new ArrayList<>();
    }
    
    public void addPayment(Payment payment) {
        payment.setId(generateId("PAY"));
        payments.add(payment);
        savePayments();
    }
    
    public void saveInvoices() {
        saveToFile("invoices.json", invoices);
    }
    
    public void loadInvoices() {
        invoices = loadFromFile("invoices.json", new TypeToken<ArrayList<Invoice>>(){}.getType());
        if (invoices == null) invoices = new ArrayList<>();
    }
    
    public void addInvoice(Invoice invoice) {
        invoice.setId(generateId("INV"));
        invoices.add(invoice);
        saveInvoices();
    }
    
    private String generateId(String prefix) {
        return prefix + "_" + System.currentTimeMillis();
    }
    
    private void saveToFile(String filename, Object data) {
        try (FileWriter writer = new FileWriter(DATA_DIR + filename)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private <T> T loadFromFile(String filename, java.lang.reflect.Type type) {
        File file = new File(DATA_DIR + filename);
        if (!file.exists()) return null;
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            return null;
        }
    }
}

    public List<Invoice> getAllInvoices() { return invoices; }
    public List<Payment> getAllPayments() { return payments; }
