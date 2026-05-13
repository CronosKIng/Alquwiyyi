import java.sql.*;
import java.util.*;

public class DataManager {
    private static DataManager instance;
    private Connection conn;
    private final String DATA_DIR = "data/";
    private final String DB_URL = "jdbc:sqlite:" + DATA_DIR + "alquwiyyi.db";
    
    private DataManager() {
        try {
            java.io.File dir = new java.io.File(DATA_DIR);
            if (!dir.exists()) dir.mkdirs();
            conn = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public static DataManager getInstance() {
        if (instance == null) instance = new DataManager();
        return instance;
    }
    
    private void createTables() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS classes (id TEXT PRIMARY KEY, name TEXT, description TEXT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS students (id TEXT PRIMARY KEY, full_name TEXT, class_name TEXT, parent_name TEXT, parent_phone TEXT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS subjects (id TEXT PRIMARY KEY, subject_name TEXT, class_name TEXT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS results (id TEXT PRIMARY KEY, student_id TEXT, student_name TEXT, class_name TEXT, total_marks INTEGER, grade TEXT, division TEXT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS invoices (id TEXT PRIMARY KEY, student_name TEXT, amount REAL, description TEXT, date TEXT, status TEXT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS payments (id TEXT PRIMARY KEY, student_name TEXT, amount REAL, description TEXT, date TEXT)");
    }
    
    // CLASS METHODS
    public void addClass(Class c) {
        String sql = "INSERT INTO classes(id, name, description) VALUES(?,?,?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "CLS_" + System.currentTimeMillis());
            p.setString(2, c.getClassName());
            p.setString(3, c.getDescription());
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Class> getAllClasses() {
        List<Class> list = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM classes")) {
            while (rs.next()) {
                Class c = new Class(rs.getString("name"), rs.getString("description"));
                c.setId(rs.getString("id"));
                list.add(c);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    // STUDENT METHODS
    public void addStudent(Student s) {
        String sql = "INSERT INTO students(id, full_name, class_name, parent_name, parent_phone) VALUES(?,?,?,?,?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "STU_" + System.currentTimeMillis());
            p.setString(2, s.getFullName());
            p.setString(3, s.getClassName());
            p.setString(4, s.getParentName());
            p.setString(5, s.getParentPhone());
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM students")) {
            while (rs.next()) {
                Student s = new Student(rs.getString("full_name"), rs.getString("class_name"), rs.getString("parent_name"), rs.getString("parent_phone"));
                s.setId(rs.getString("id"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public List<Student> getStudentsByClass(String className) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE class_name = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, className);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Student s = new Student(rs.getString("full_name"), rs.getString("class_name"), rs.getString("parent_name"), rs.getString("parent_phone"));
                s.setId(rs.getString("id"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    // SUBJECT METHODS
    public void addSubject(Subject s) {
        String sql = "INSERT INTO subjects(id, subject_name, class_name) VALUES(?,?,?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "SUB_" + System.currentTimeMillis());
            p.setString(2, s.getSubjectName());
            p.setString(3, s.getClassName());
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM subjects")) {
            while (rs.next()) {
                Subject s = new Subject(rs.getString("subject_name"), rs.getString("class_name"));
                s.setId(rs.getString("id"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public List<Subject> getSubjectsByClass(String className) {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subjects WHERE class_name = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, className);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Subject s = new Subject(rs.getString("subject_name"), rs.getString("class_name"));
                s.setId(rs.getString("id"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    // RESULT METHODS
    public void addResult(Result r) {
        String sql = "INSERT INTO results(id, student_id, student_name, class_name, total_marks, grade, division) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "RES_" + System.currentTimeMillis());
            p.setString(2, r.getStudentId());
            p.setString(3, r.getStudentName());
            p.setString(4, r.getClassName());
            p.setInt(5, r.getTotalMarks());
            p.setString(6, r.getGrade());
            p.setString(7, r.getDivision());
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Result> getResultsByClass(String className) {
        List<Result> list = new ArrayList<>();
        String sql = "SELECT * FROM results WHERE class_name = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, className);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Result r = new Result(rs.getString("student_id"), rs.getString("student_name"), rs.getString("class_name"));
                r.setId(rs.getString("id"));
                list.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    // INVOICE METHODS
    public void addInvoice(Invoice i) {
        String sql = "INSERT INTO invoices(id, student_name, amount, description, date, status) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "INV_" + System.currentTimeMillis());
            p.setString(2, i.getStudentName());
            p.setDouble(3, i.getAmount());
            p.setString(4, i.getDescription());
            p.setString(5, i.getDate());
            p.setString(6, i.getStatus());
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Invoice> getAllInvoices() {
        List<Invoice> list = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM invoices")) {
            while (rs.next()) {
                Invoice i = new Invoice("", rs.getString("student_name"), rs.getDouble("amount"), rs.getString("description"));
                i.setId(rs.getString("id"));
                list.add(i);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    // PAYMENT METHODS
    public void addPayment(Payment p) {
        String sql = "INSERT INTO payments(id, student_name, amount, description, date) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "PAY_" + System.currentTimeMillis());
            pstmt.setString(2, p.getStudentName());
            pstmt.setDouble(3, p.getAmount());
            pstmt.setString(4, p.getDescription());
            pstmt.setString(5, p.getDate());
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM payments")) {
            while (rs.next()) {
                Payment p = new Payment("", rs.getString("student_name"), rs.getDouble("amount"), rs.getString("description"));
                p.setId(rs.getString("id"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public void loadAllData() {}
}
