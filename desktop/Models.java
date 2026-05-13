import java.util.*;

class Student {
    private String id, fullName, className, parentName, parentPhone, admissionDate;
    
    public Student(String fullName, String className, String parentName, String parentPhone) {
        this.fullName = fullName;
        this.className = className;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.admissionDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFullName() { return fullName; }
    public String getClassName() { return className; }
    public String getParentName() { return parentName; }
    public String getParentPhone() { return parentPhone; }
    public String getAdmissionDate() { return admissionDate; }
}

class Subject {
    private String id, subjectName, className;
    
    public Subject(String subjectName, String className) {
        this.subjectName = subjectName;
        this.className = className;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSubjectName() { return subjectName; }
    public String getClassName() { return className; }
}

class Class {
    private String id, className, description;
    
    public Class(String className, String description) {
        this.className = className;
        this.description = description;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClassName() { return className; }
    public String getDescription() { return description; }
}

class Result {
    private String id, studentId, studentName, className;
    private Map<String, Integer> marks = new HashMap<>();
    private String grade = "", division = "";
    private int totalMarks = 0, position = 0;
    
    public Result(String studentId, String studentName, String className) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.className = className;
    }
    
    public void calculateGradeAndDivision() {
        if (marks.isEmpty()) return;
        int total = 0;
        for (int mark : marks.values()) total += mark;
        int average = total / marks.size();
        if (average >= 80) grade = "A";
        else if (average >= 70) grade = "B";
        else if (average >= 60) grade = "C";
        else if (average >= 50) grade = "D";
        else if (average >= 40) grade = "E";
        else grade = "F";
        if (average >= 70) division = "Division One";
        else if (average >= 60) division = "Division Two";
        else if (average >= 50) division = "Division Three";
        else if (average >= 40) division = "Division Four";
        else division = "Division Zero";
        totalMarks = total;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getClassName() { return className; }
    public Map<String, Integer> getMarks() { return marks; }
    public void setMarks(Map<String, Integer> marks) { this.marks = marks; }
    public String getGrade() { return grade; }
    public String getDivision() { return division; }
    public int getTotalMarks() { return totalMarks; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
}

class Payment {
    private String id, studentId, studentName, description;
    private double amount;
    private String date;
    
    public Payment(String studentId, String studentName, double amount, String description) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.amount = amount;
        this.description = description;
        this.date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getStudentName() { return studentName; }
}

class Invoice {
    private String id, studentId, studentName, description;
    private double amount;
    private String date, status;
    
    public Invoice(String studentId, String studentName, double amount, String description) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.amount = amount;
        this.description = description;
        this.date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        this.status = "Pending";
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getStudentName() { return studentName; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
}
