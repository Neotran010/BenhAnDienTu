# Quick Start Guide - BenhAnDienTu

## Installation

### Prerequisites
- Java 21 or higher must be installed on your system
- Windows, macOS, or Linux operating system

### Check Java Installation
Open a terminal/command prompt and run:
```bash
java -version
```

You should see Java version 21 or higher. If not, download and install Java from:
- Oracle JDK: https://www.oracle.com/java/technologies/downloads/
- OpenJDK: https://adoptium.net/

## Building the Application

### On Windows:
1. Open Command Prompt in the project directory
2. Run: `build.bat`

### On Linux/Mac:
1. Open Terminal in the project directory
2. Run: `./build.sh`

This will compile all Java files and create `BenhAnDienTu.jar` (approximately 13MB).

## Running the Application

### On Windows:
Double-click `run.bat` or run in Command Prompt:
```cmd
run.bat
```

### On Linux/Mac:
Run in Terminal:
```bash
./run.sh
```

### Or directly run the JAR:
```bash
java -jar BenhAnDienTu.jar
```

## Using the Application

### Step 1: Search or Create Patient

When you launch the application, you'll see the **Search Frame**:

1. **Enter Patient ID** (Mã Số Bệnh Nhân)
   - Example: `BN001`, `BN002`, etc.

2. **Click TÌM KIẾM** (Search)
   - If patient exists: Opens their record
   - If patient doesn't exist: Opens a new empty record

3. **Click TẠO MỚI** (Create New)
   - Creates a new patient record with the entered ID
   - Only works if the ID doesn't already exist

### Step 2: Enter Patient Information

The **Patient Information Frame** allows you to:

1. **Fill in Basic Information:**
   - Mã số BN (Patient ID) - Auto-filled, cannot be edited
   - Họ tên (Full Name)
   - Ngày sinh (Date of Birth)
   - Giới tính (Gender): Nam (Male) or Nữ (Female)

2. **Health Insurance Information:**
   - Số BHYT (Insurance Number)
   - Valid from date (Từ)
   - Valid to date (Đến)

3. **Medical Information:**
   - Giờ nhập viện (Admission Time)
   - Lý do nhập viện (Reason for Admission)
   - Quá trình bệnh lý (Medical History)
   - Tiền sử bệnh (Past Illness)
   - Mạch, HA, CN (Vital Signs: Pulse, Blood Pressure, Weight)
   - Chẩn đoán chính (Main Diagnosis)
   - Chẩn đoán phụ (Additional Diagnosis)
   - Bác sĩ điều trị (Attending Doctor)

4. **Save or Cancel:**
   - Click **Lưu** to save all changes
   - Click **Hủy bỏ** to cancel and close

### Step 3: View Monthly Records

At the bottom of the Patient Information Frame:

1. You'll see buttons labeled **Tháng 1** through **Tháng 12** (Month 1-12)
2. Enabled buttons indicate months with treatment records
3. Click on an enabled month button to view records for that month

### Step 4: View Daily Records

In the **Monthly Records Frame**:

1. You'll see buttons for each date with records
2. Example: `2024-12-01`, `2024-12-15`, etc.
3. Click on a date to view all treatment records for that day
4. Click **Quay lại** to return to Patient Information

### Step 5: View Treatment Timeline

In the **Daily Records Frame**:

1. You'll see buttons with timestamps for each treatment session
2. Example: `2024-12-01 09:00:00`, `2024-12-01 15:30:00`
3. Click on a timestamp to view treatment details
4. Click **+ Thêm điều trị mới** to add a new treatment record
5. Click **Quay lại** to return to Monthly Records

### Step 6: Enter Treatment Details

In the **Treatment Details Frame**:

1. **Diễn tiến** (Progress/Observations)
   - Enter patient's condition, observations, progress notes
   - Example: "Bệnh nhân ổn định, không sốt, ăn uống được"

2. **Y lệnh** (Physician's Orders)
   - Enter treatment orders, medications, procedures
   - Example: "Truyền NaCl 0.9% 500ml, Paracetamol 500mg x 3 lần/ngày"

3. **Save or Cancel:**
   - Click **Lưu** to save treatment details
   - Click **Hủy bỏ** to cancel and return to Daily Records

## Example Workflow

### Creating a New Patient Record:

1. Launch application
2. Enter ID: `BN001`
3. Click **TẠO MỚI**
4. Fill in patient information:
   - Name: Nguyen Van A
   - Birth: 1990-01-01
   - Gender: Nam
   - Insurance: 123456789
   - Diagnosis: Viêm ruột thừa
5. Click **Lưu**

### Adding Treatment Records:

1. Search for patient `BN001`
2. Since no treatment exists yet, month buttons are disabled
3. Close the patient frame
4. Use database operations to add treatments (or wait for treatments to be added through the system)

### Viewing Treatment History:

1. Search for patient with existing treatments
2. Click on an enabled month button (e.g., **Tháng 12**)
3. Click on a date (e.g., `2024-12-15`)
4. Click on a timestamp to view details

## Data Storage

- All data is stored in SQLite database: `benhan.db`
- Database is created automatically on first run
- Database file is in the same directory as the application
- Backup the `benhan.db` file regularly to prevent data loss

## Troubleshooting

### Application won't start:
- Verify Java 21+ is installed: `java -version`
- Check if all files are present (JAR file, lib folder)
- Try running from command line to see error messages

### Can't save data:
- Check if application has write permissions in its directory
- Verify `benhan.db` is not read-only

### Month buttons are disabled:
- No treatment records exist for those months
- Add treatment records first

### Application is slow:
- Check available system memory
- Close other applications
- Database file might be large (consider archiving old records)

## Tips

1. **Patient ID Format**: Use a consistent format like `BN001`, `BN002` for easy management
2. **Date Format**: Use YYYY-MM-DD format (e.g., 2024-12-30)
3. **Backup**: Regularly backup the `benhan.db` file
4. **Records**: Keep detailed treatment notes in "Diễn tiến" and "Y lệnh" fields
5. **Organization**: Add treatment records daily to maintain accurate timeline

## Support

For issues or questions:
1. Check the README.md for detailed documentation
2. Check WINDOWS_EXE_GUIDE.md for creating Windows executables
3. Review the problem statement for application specifications

## Security Note

- This application stores medical data locally
- Ensure the database file is properly secured
- Do not share the database file with unauthorized persons
- Follow your organization's data protection policies
