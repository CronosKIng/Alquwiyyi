import java.sql.*;
import java.util.*;
import java.io.*;

public class DataManager {
    private static DataManager instance;
    private Connection conn;
    private final String DATA_DIR = "data/";
    private String DB_URL;
    private int lastStudentNumber = 0;
    
    private DataManager() {
        try {
            String userDir = System.getProperty("user.dir");
            File dbFile = new File(userDir, DATA_DIR + "alquwiyyi.db");
            File parentDir = dbFile.getParentFile();
            if (!parentDir.exists()) parentDir.mkdirs();
            DB_URL = "jdbc:sqlite:" + dbFile.getAbsolutePath();
            conn = DriverManager.getConnection(DB_URL);
            createTables();
            loadLastStudentNumber();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public static DataManager getInstance() {
        if (instance == null) instance = new DataManager();
        return instance;
    }
    
    private void createTables() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS classes (id TEXT PRIMARY KEY, name TEXT, description TEXT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS students (id TEXT PRIMARY KEY, student_no TEXT, full_name TEXT, class_name TEXT, parent_name TEXT, parent_phone TEXT, admission_date TEXT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS subjects (id TEXT PRIMARY KEY, subject_name TEXT, class_name TEXT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS results (id TEXT PRIMARY KEY, student_id TEXT, student_name TEXT, class_name TEXT, marks TEXT, total_marks INTEGER, grade TEXT, division TEXT, date TEXT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS invoices (id TEXT PRIMARY KEY, student_name TEXT, amount REAL, paid_amount REAL, description TEXT, date TEXT, status TEXT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS payments (id TEXT PRIMARY KEY, student_name TEXT, amount REAL, description TEXT, date TEXT)");
    }
    
    private void loadLastStudentNumber() {
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT MAX(CAST(SUBSTR(student_no, 4) AS INTEGER)) as max_num FROM students")) {
            if (rs.next()) {
                lastStudentNumber = rs.getInt("max_num");
            }
        } catch (SQLException e) {}
    }
    
    private String generateStudentNo() {
        lastStudentNumber++;
        return String.format("STU%03d", lastStudentNumber);
    }
    
    public void addClass(SchoolClass c) {
        String sql = "INSERT INTO classes(id, name, description) VALUES(?,?,?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "CLS_" + System.currentTimeMillis());
            p.setString(2, c.getClassName());
            p.setString(3, c.getDescription());
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<SchoolClass> getAllClasses() {
        List<SchoolClass> list = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM classes ORDER BY name")) {
            while (rs.next()) {
                SchoolClass c = new SchoolClass(rs.getString("name"), rs.getString("description"));
                c.setId(rs.getString("id"));
                list.add(c);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public void addStudent(Student s) {
        String sql = "INSERT INTO students(id, student_no, full_name, class_name, parent_name, parent_phone, admission_date) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            String studentNo = generateStudentNo();
            p.setString(1, "STU_" + System.currentTimeMillis());
            p.setString(2, studentNo);
            p.setString(3, s.getFullName());
            p.setString(4, s.getClassName());
            p.setString(5, s.getParentName());
            p.setString(6, s.getParentPhone());
            p.setString(7, s.getAdmissionDate());
            p.executeUpdate();
            System.out.println("Student added with No: " + studentNo);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM students ORDER BY student_no")) {
            while (rs.next()) {
                Student s = new Student(rs.getString("full_name"), rs.getString("class_name"), 
                                        rs.getString("parent_name"), rs.getString("parent_phone"));
                s.setId(rs.getString("student_no"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public List<Student> getStudentsByClass(String className) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE class_name = ? ORDER BY full_name";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, className);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Student s = new Student(rs.getString("full_name"), rs.getString("class_name"), 
                                        rs.getString("parent_name"), rs.getString("parent_phone"));
                s.setId(rs.getString("student_no"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
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
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM subjects ORDER BY subject_name")) {
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
        String sql = "SELECT * FROM subjects WHERE class_name = ? ORDER BY subject_name";
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
    
    public void addResult(Result r) {
        String sql = "INSERT INTO results(id, student_id, student_name, class_name, marks, total_marks, grade, division, date) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "RES_" + System.currentTimeMillis());
            p.setString(2, r.getStudentId());
            p.setString(3, r.getStudentName());
            p.setString(4, r.getClassName());
            p.setString(5, r.getMarksAsString());
            p.setInt(6, r.getTotalMarks());
            p.setString(7, r.getGrade());
            p.setString(8, r.getDivision());
            p.setString(9, new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Result> getResultsByClass(String className) {
        List<Result> list = new ArrayList<>();
        String sql = "SELECT * FROM results WHERE class_name = ? ORDER BY total_marks DESC";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, className);
            ResultSet rs = p.executeQuery();
            int position = 1;
            while (rs.next()) {
                Result r = new Result(rs.getString("student_id"), rs.getString("student_name"), rs.getString("class_name"));
                r.setId(rs.getString("id"));
                r.setTotalMarks(rs.getInt("total_marks"));
                r.setGrade(rs.getString("grade"));
                r.setDivision(rs.getString("division"));
                r.setPosition(position++);
                list.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public List<Result> getAllResults() {
        List<Result> list = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM results ORDER BY date DESC")) {
            while (rs.next()) {
                Result r = new Result(rs.getString("student_id"), rs.getString("student_name"), rs.getString("class_name"));
                r.setId(rs.getString("id"));
                r.setTotalMarks(rs.getInt("total_marks"));
                r.setGrade(rs.getString("grade"));
                r.setDivision(rs.getString("division"));
                list.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public void addInvoice(Invoice i) {
        String sql = "INSERT INTO invoices(id, student_name, amount, paid_amount, description, date, status) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "INV_" + System.currentTimeMillis());
            p.setString(2, i.getStudentName());
            p.setDouble(3, i.getAmount());
            p.setDouble(4, i.getPaidAmount());
            p.setString(5, i.getDescription());
            p.setString(6, i.getDate());
            p.setString(7, i.getStatus());
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public List<Invoice> getAllInvoices() {
        List<Invoice> list = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM invoices ORDER BY date DESC")) {
            while (rs.next()) {
                Invoice i = new Invoice("", rs.getString("student_name"), rs.getDouble("amount"), rs.getString("description"));
                i.setId(rs.getString("id"));
                i.setPaidAmount(rs.getDouble("paid_amount"));
                i.setStatus(rs.getString("status"));
                list.add(i);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public void updateInvoiceStatus(String invoiceId, String status, double paidAmount) {
        String sql = "UPDATE invoices SET status = ?, paid_amount = ? WHERE id = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, status);
            p.setDouble(2, paidAmount);
            p.setString(3, invoiceId);
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public double getTotalPaidAmount() {
        double total = 0;
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT SUM(paid_amount) as total FROM invoices")) {
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return total;
    }
    
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
        try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM payments ORDER BY date DESC")) {
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
