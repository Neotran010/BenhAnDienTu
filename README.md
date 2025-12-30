# BenhAnDienTu - Electronic Medical Records Application

A desktop application for managing electronic medical records built with Java 21 and Swing.

## Features

- Patient information management
- Medical records tracking by month and date
- Treatment timeline with detailed records
- SQLite database for data persistence
- User-friendly Vietnamese interface

## Requirements

- Java 21 or higher
- SQLite JDBC Driver (included in lib/)

## Project Structure

```
BenhAnDienTu/
├── src/main/java/com/benhan/
│   ├── BenhAnDienTu.java         # Main application entry point
│   ├── models/
│   │   ├── Patient.java           # Patient data model
│   │   └── DieuTri.java           # Treatment data model
│   ├── database/
│   │   └── DatabaseManager.java   # SQLite database operations
│   └── ui/
│       ├── Frame1.java            # Search/Create patient frame
│       ├── Frame2.java            # Patient information frame
│       ├── Frame3.java            # Monthly records frame
│       ├── Frame4.java            # Daily records frame
│       └── Frame5.java            # Treatment details frame
├── lib/
│   └── sqlite-jdbc-3.45.0.0.jar  # SQLite JDBC driver
├── build.sh                       # Build script (Linux/Mac)
├── build.bat                      # Build script (Windows)
├── run.sh                         # Run script (Linux/Mac)
└── run.bat                        # Run script (Windows)
```

## Building the Application

### On Linux/Mac:
```bash
./build.sh
```

### On Windows:
```cmd
build.bat
```

This will:
1. Compile all Java source files
2. Create a JAR file with all dependencies
3. Generate `BenhAnDienTu.jar`

## Running the Application

### Using the run scripts:

**Linux/Mac:**
```bash
./run.sh
```

**Windows:**
```cmd
run.bat
```

### Or directly:
```bash
java -jar BenhAnDienTu.jar
```

## Application Workflow

### Frame 1: Search Patient (TÌM KIẾM THÔNG TIN BỆNH ÁN)
- Enter patient ID (Mã Số Bệnh Nhân)
- Click **TÌM KIẾM** to search for existing patient
- Click **TẠO MỚI** to create a new patient record

### Frame 2: Patient Information (THÔNG TIN BỆNH ÁN)
- View and edit patient details:
  - Basic info: ID, Name, Birth date, Gender
  - Insurance: BHYT number and validity period
  - Medical info: Admission time, reason, medical history
  - Diagnosis: Main and additional diagnoses
  - Doctor in charge
- Click **Lưu** to save changes
- Click **Hủy bỏ** to cancel and close
- Click month buttons (Tháng 1-12) to view monthly records

### Frame 3: Monthly Records (HỒ SƠ ĐIỀU TRỊ - THÁNG)
- View all examination dates for selected month
- Click on a date to view daily records
- Click **Quay lại** to return to Frame 2

### Frame 4: Daily Records (HỒ SƠ ĐIỀU TRỊ - NGÀY)
- View all treatment timestamps for selected date
- Click on a timestamp to view treatment details
- Click **+ Thêm điều trị mới** to add new treatment record
- Click **Quay lại** to return to Frame 3

### Frame 5: Treatment Details (CHI TIẾT ĐIỀU TRỊ)
- Enter treatment information:
  - **Diễn tiến**: Progress/observations
  - **Y lệnh**: Physician's orders
- Click **Lưu** to save treatment details
- Click **Hủy bỏ** to cancel and return

## Database Schema

### Patients Table
- id (TEXT, PRIMARY KEY): Patient ID
- name (TEXT): Full name
- birth (TEXT): Date of birth
- sex (INTEGER): Gender (1=Male, 2=Female)
- bhyt (TEXT): Health insurance number
- bhytFrom (TEXT): Insurance valid from
- bhytTo (TEXT): Insurance valid to
- gioNhapVien (TEXT): Admission time
- lyDoNhapVien (TEXT): Admission reason
- quaTrinhBenhLy (TEXT): Medical history
- tienSuBenh (TEXT): Past illness
- vitalSigns (TEXT): Vital signs (pulse, blood pressure, weight)
- chanDoanChinh (TEXT): Main diagnosis
- chanDoanPhu (TEXT): Additional diagnosis
- bacSiDieuTri (TEXT): Attending doctor

### Treatment Records Table (dieu_tri)
- id (INTEGER, PRIMARY KEY AUTOINCREMENT)
- patientId (TEXT, FOREIGN KEY)
- date (TEXT): Date (YYYY-MM-DD)
- timestamp (TEXT): Timestamp (YYYY-MM-DD HH:MM:SS)
- dienTien (TEXT): Progress/observations
- yLenh (TEXT): Physician's orders

## Creating Windows Executable (BenhAnDienTu.exe)

To create a Windows executable, you can use tools like:

### Option 1: Using Launch4j
1. Download Launch4j from http://launch4j.sourceforge.net/
2. Configure Launch4j:
   - Output file: BenhAnDienTu.exe
   - Jar: BenhAnDienTu.jar
   - Min JRE version: 21
3. Build the executable

### Option 2: Using jpackage (Java 21+)
```bash
jpackage --input . --name BenhAnDienTu --main-jar BenhAnDienTu.jar --main-class com.benhan.BenhAnDienTu --type exe --java-options "-Xmx512m"
```

## License

This project is for educational and medical record management purposes.