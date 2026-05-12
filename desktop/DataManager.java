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

    // ==================== CLASS MANAGEMENT ====================
    public void addClass(Class c) {
        c.setId("CLS_" + System.currentTimeMillis());
        classes.add(c);
        saveClasses();
    }

    public void saveClasses() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_DIR + "classes.txt"))) {
            for (Class c : classes) {
                pw.println(c.getId() + "|" + c.getClassName() + "|" + c.getDescription());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void loadClasses() {
        classes.clear();
        File f = new File(DATA_DIR + "classes.txt");
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length >= 3) {
                    Class c = new Class(p[1], p[2]);
                    c.setId(p[0]);
                    classes.add(c);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<Class> getAllClasses() {
        loadClasses();
        return classes;
    }

    // ==================== STUDENT MANAGEMENT ====================
    public void addStudent(Student s) {
        s.setId("STU_" + System.currentTimeMillis());
        students.add(s);
        saveStudents();
    }

    public void saveStudents() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_DIR + "students.txt"))) {
            for (Student s : students) {
                pw.println(s.getId() + "|" + s.getFullName() + "|" + s.getClassName() + "|" +
                           s.getParentName() + "|" + s.getParentPhone() + "|" + s.getAdmissionDate());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void loadStudents() {
        students.clear();
        File f = new File(DATA_DIR + "students.txt");
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length >= 6) {
                    Student s = new Student(p[1], p[2], p[3], p[4]);
                    s.setId(p[0]);
                    students.add(s);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<Student> getAllStudents() {
        loadStudents();
        return students;
    }

    // ==================== SUBJECT MANAGEMENT ====================
    public void addSubject(Subject sub) {
        sub.setId("SUB_" + System.currentTimeMillis());
        subjects.add(sub);
        saveSubjects();
    }

    public void saveSubjects() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_DIR + "subjects.txt"))) {
            for (Subject s : subjects) {
                pw.println(s.getId() + "|" + s.getSubjectName() + "|" + s.getClassName());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void loadSubjects() {
        subjects.clear();
        File f = new File(DATA_DIR + "subjects.txt");
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length >= 3) {
                    Subject s = new Subject(p[1], p[2]);
                    s.setId(p[0]);
                    subjects.add(s);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<Subject> getAllSubjects() {
        loadSubjects();
        return subjects;
    }
    
    public List<Subject> getSubjectsByClass(String className) {
        loadSubjects();
        List<Subject> list = new ArrayList<>();
        for (Subject s : subjects) {
            if (s.getClassName().equals(className)) list.add(s);
        }
        return list;
    }

    // ==================== RESULT MANAGEMENT ====================
    public void addResult(Result r) {
        r.setId("RES_" + System.currentTimeMillis());
        results.add(r);
        saveResults();
    }

    public void saveResults() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_DIR + "results.txt"))) {
            for (Result r : results) {
                pw.println(r.getId() + "|" + r.getStudentId() + "|" + r.getStudentName() + "|" +
                           r.getClassName() + "|" + r.getGrade() + "|" + r.getDivision() + "|" + r.getTotalMarks());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void loadResults() {
        results.clear();
        File f = new File(DATA_DIR + "results.txt");
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length >= 7) {
                    Result r = new Result(p[1], p[2], p[3]);
                    r.setId(p[0]);
                    results.add(r);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    public List<Result> getResultsByClass(String className) {
        loadResults();
        List<Result> list = new ArrayList<>();
        for (Result r : results) {
            if (r.getClassName().equals(className)) list.add(r);
        }
        return list;
    }

    // ==================== PAYMENT MANAGEMENT ====================
    public void addPayment(Payment p) {
        p.setId("PAY_" + System.currentTimeMillis());
        payments.add(p);
        savePayments();
    }

    public void savePayments() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_DIR + "payments.txt"))) {
            for (Payment p : payments) {
                pw.println(p.getId() + "|" + p.getStudentId() + "|" + p.getStudentName() + "|" +
                           p.getAmount() + "|" + p.getDescription() + "|" + p.getDate());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void loadPayments() {
        payments.clear();
        File f = new File(DATA_DIR + "payments.txt");
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length >= 6) {
                    Payment pay = new Payment(p[1], p[2], Double.parseDouble(p[3]), p[4]);
                    pay.setId(p[0]);
                    payments.add(pay);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    public List<Payment> getAllPayments() {
        loadPayments();
        return payments;
    }

    // ==================== INVOICE MANAGEMENT ====================
    public void addInvoice(Invoice i) {
        i.setId("INV_" + System.currentTimeMillis());
        invoices.add(i);
        saveInvoices();
    }

    public void saveInvoices() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_DIR + "invoices.txt"))) {
            for (Invoice i : invoices) {
                pw.println(i.getId() + "|" + i.getStudentId() + "|" + i.getStudentName() + "|" +
                           i.getAmount() + "|" + i.getDescription() + "|" + i.getDate() + "|" + i.getStatus());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void loadInvoices() {
        invoices.clear();
        File f = new File(DATA_DIR + "invoices.txt");
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length >= 7) {
                    Invoice inv = new Invoice(p[1], p[2], Double.parseDouble(p[3]), p[4]);
                    inv.setId(p[0]);
                    invoices.add(inv);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    public List<Invoice> getAllInvoices() {
        loadInvoices();
        return invoices;
    }
}
