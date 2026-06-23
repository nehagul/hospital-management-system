import java.util.Scanner;
import java.io.*;

public class HospitalManagementSystem05 {

    static Scanner input = new Scanner(System.in);

    // Patient Information Arrays
    static String[] patientID = new String[100];
    static String[] patientName = new String[100];
    static int[] patientAge = new int[100];
    static String[] patientGender = new String[100];
    static String[] patientDisease = new String[100];
    static String[] patientDoctor = new String[100];
    static String[] admissionDate = new String[100];
    static String[] roomNumber = new String[100];
    static String[] patientContact = new String[100];
    static String[] emergencyPriority = new String[100]; // Normal / High
    static String[] appointmentDate = new String[100];
    static String[] appointmentTime = new String[100];
    static String[] appointmentDoctor = new String[100];

    static int patientCount = 0;
    static String adminUser, adminPass;

    public static void main(String[] args) {
        showTitle();
        signUp();
        loadFromFile(); // Load previous data at program start
        login();
    }

    // -------------------- TITLE --------------------
    static void showTitle() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                            ║");
        System.out.println("║        WELCOME TO HOSPITAL MANAGEMENT SYSTEM               ║");
        System.out.println("║            PATIENT RECORD DASHBOARD                        ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }

    // -------------------- SIGN UP --------------------
    static void signUp() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║         ADMIN SIGN UP          ║");
        System.out.println("╚════════════════════════════════╝\n");
        System.out.print("Enter new admin username: ");
        adminUser = input.nextLine();
        System.out.print("Enter new admin password: ");
        adminPass = input.nextLine();
        System.out.println("\nSign up successful! Please login to continue.\n");
    }

    // -------------------- LOGIN --------------------
    static void login() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║          LOGIN OPTIONS         ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.println("1. Admin Login");
        System.out.println("2. Faculty Login");
        System.out.print("Enter choice: ");
        String choice = input.nextLine();
        switch (choice) {
            case "1": adminLogin(); break;
            case "2": facultyLogin(); break;
            default: System.out.println("Invalid choice!"); login(); break;
        }
    }

    // -------------------- ADMIN LOGIN --------------------
    static void adminLogin() {
        while (true) {
            System.out.print("Enter username: ");
            String user = input.nextLine();
            System.out.print("Enter password: ");
            String pass = input.nextLine();
            if (user.equals(adminUser) && pass.equals(adminPass)) {
                System.out.println("\nLogin successful! Welcome, Admin.\n");
                adminMenu();
                break;
            } else System.out.println("Invalid credentials! Try again.");
        }
    }

    // -------------------- FACULTY LOGIN --------------------
    static void facultyLogin() {
        System.out.print("Enter faculty username: ");
        input.nextLine(); // dummy input
        System.out.print("Enter password: ");
        input.nextLine();
        System.out.println("\nFaculty Login successful!\n");

        loadFromFile(); // Load all patients added by Admin

        facultyMenu();
    }

    // -------------------- ADMIN MENU --------------------
    static void adminMenu() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║          ADMIN MENU            ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.println("1. Add Patient");
        System.out.println("2. Update Patient");
        System.out.println("3. Delete Patient");
        System.out.println("4. Display All Patients");
        System.out.println("5. Count Total Patients");
        System.out.println("6. Schedule Appointment");
        System.out.println("7. View Emergency Cases");
        System.out.println("8. Generate Report");
        System.out.println("9. View Statistics");
        System.out.println("10. Bill");
        System.out.println("11. Save & Exit");
        System.out.print("Enter choice: ");
        String choice = input.nextLine();
        switch (choice) {
            case "1": addPatient(); saveToFile(); break;
            case "2": updatePatient(); saveToFile(); break;
            case "3": deletePatient(); saveToFile(); break;
            case "4": displayPatients(); break;
            case "5": countPatients(); break;
            case "6": scheduleAppointment(); saveToFile(); break;
            case "7": viewEmergencyCases(); break;
            case "8": generateReport(); break;
            case "9": viewStatistics(); break;
            case "10": billing(); break;
            case "11": saveToFile(); System.out.println("Exiting system."); System.exit(0); break;
            default: System.out.println("Invalid choice!");
        }
        adminMenu();
    }

    // -------------------- FACULTY MENU --------------------
    static void facultyMenu() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║         FACULTY MENU           ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.println("1. View Patients");
        System.out.println("2. Search Patient");
        System.out.println("3. View Emergency Cases");
        System.out.println("4. View Statistics");
        System.out.println("5. Back to Login");
        System.out.print("Enter choice: ");
        String choice = input.nextLine();
        switch (choice) {
            case "1": displayPatients(); break;
            case "2": searchPatient(); break;
            case "3": viewEmergencyCases(); break;
            case "4": viewStatistics(); break;
            case "5": login(); break;
            default: System.out.println("Invalid choice!");
        }
        facultyMenu();
    }

    // -------------------- PATIENT FUNCTIONS --------------------
    static void addPatient() {
        System.out.println("╔══════ ADD PATIENT ══════╗");
        System.out.print("Enter Patient ID: "); String id = input.nextLine();
        System.out.print("Enter Name: "); String name = input.nextLine();
        System.out.print("Enter Age: "); int age = Integer.parseInt(input.nextLine());
        System.out.print("Enter Gender: "); String gender = input.nextLine();
        System.out.print("Enter Disease: "); String disease = input.nextLine();
        System.out.print("Enter Doctor Name: "); String doctor = input.nextLine();
        System.out.print("Enter Admission Date: "); String date = input.nextLine();
        System.out.print("Enter Contact: "); String contact = input.nextLine();

        // Manual room input
        System.out.print("Enter Room Number: ");
        String room = input.nextLine();

        // Emergency priority input
        System.out.print("Emergency Priority (H=High / N=Normal): ");
        String priorityInput = input.nextLine().toUpperCase();
        String priority = priorityInput.equals("H") ? "High" : "Normal";

        patientID[patientCount] = id;
        patientName[patientCount] = name;
        patientAge[patientCount] = age;
        patientGender[patientCount] = gender;
        patientDisease[patientCount] = disease;
        patientDoctor[patientCount] = doctor;
        admissionDate[patientCount] = date;
        roomNumber[patientCount] = room;
        patientContact[patientCount] = contact;
        emergencyPriority[patientCount] = priority;
        patientCount++;

        System.out.println("Patient added successfully! Assigned Room: " + room + "\n");
    }

    static void updatePatient() {
        System.out.print("Enter Patient ID to update: "); String id = input.nextLine();
        int index = -1;
        for (int i = 0; i < patientCount; i++) if (patientID[i].equals(id)) { index = i; break; }
        if (index == -1) { System.out.println("Patient not found!"); return; }

        System.out.println("1.Name 2.Age 3.Gender 4.Disease 5.Doctor 6.Room 7.Contact 8.Emergency Priority");
        String choice = input.nextLine();
        switch (choice) {
            case "1": System.out.print("New Name: "); patientName[index] = input.nextLine(); break;
            case "2": System.out.print("New Age: "); patientAge[index] = Integer.parseInt(input.nextLine()); break;
            case "3": System.out.print("New Gender: "); patientGender[index] = input.nextLine(); break;
            case "4": System.out.print("New Disease: "); patientDisease[index] = input.nextLine(); break;
            case "5": System.out.print("New Doctor: "); patientDoctor[index] = input.nextLine(); break;
            case "6": System.out.print("New Room: "); roomNumber[index] = input.nextLine(); break;
            case "7": System.out.print("New Contact: "); patientContact[index] = input.nextLine(); break;
            case "8": System.out.print("New Priority (H/N): "); 
                      String p = input.nextLine().toUpperCase();
                      emergencyPriority[index] = p.equals("H") ? "High" : "Normal"; break;
            default: System.out.println("Invalid choice"); return;
        }
        System.out.println("Patient updated successfully!");
    }

    static void deletePatient() {
        System.out.print("Enter Patient ID to delete: "); String id = input.nextLine();
        int index = -1;
        for (int i = 0; i < patientCount; i++) if (patientID[i].equals(id)) { index = i; break; }
        if (index == -1) { System.out.println("Patient not found!"); return; }

        for (int i = index; i < patientCount - 1; i++) {
            patientID[i] = patientID[i + 1];
            patientName[i] = patientName[i + 1];
            patientAge[i] = patientAge[i + 1];
            patientGender[i] = patientGender[i + 1];
            patientDisease[i] = patientDisease[i + 1];
            patientDoctor[i] = patientDoctor[i + 1];
            admissionDate[i] = admissionDate[i + 1];
            roomNumber[i] = roomNumber[i + 1];
            patientContact[i] = patientContact[i + 1];
            emergencyPriority[i] = emergencyPriority[i + 1];
            appointmentDate[i] = appointmentDate[i + 1];
            appointmentTime[i] = appointmentTime[i + 1];
            appointmentDoctor[i] = appointmentDoctor[i + 1];
        }
        patientCount--;
        System.out.println("Patient deleted successfully!");
    }

    // -------------------- DISPLAY --------------------
    static void displayPatients() {
        if (patientCount == 0) { System.out.println("No patients available."); return; }

        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                ALL PATIENTS RECORDS                                              ║");
        System.out.println("╠═════════╦════════════════╦═════╦═══════╦═════════════╦═════════════╦═════════════╦═══════════╦═════════════╦═════════════╣");
        System.out.printf("║ %-7s ║ %-14s ║ %-3s ║ %-6s ║ %-11s ║ %-11s ║ %-11s ║ %-9s ║ %-11s ║ %-5s ║\n",
            "ID","Name","Age","Gender","Disease","Doctor","Admission","Room","Contact","Priority");
        System.out.println("╠═════════╬════════════════╬═════╬═══════╬═════════════╬═════════════╬═════════════╬═══════════╬═════════════╬═════════════╣");
        for (int i = 0; i < patientCount; i++) {
            String prio = emergencyPriority[i].equalsIgnoreCase("High") ? "!!! High !!!" : emergencyPriority[i];
            System.out.printf("║ %-7s ║ %-14s ║ %-3d ║ %-6s ║ %-11s ║ %-11s ║ %-11s ║ %-9s ║ %-11s ║ %-11s ║\n",
                patientID[i], patientName[i], patientAge[i], patientGender[i], patientDisease[i],
                patientDoctor[i], admissionDate[i], roomNumber[i], patientContact[i], prio);
        }
        System.out.println("╚═════════╩════════════════╩═════╩═══════╩═════════════╩═════════════╩═════════════╩═══════════╩═════════════╩═════════════╝");
    }

    static void countPatients() { System.out.println("Total Patients: " + patientCount); }

    // -------------------- ADMIN FUNCTIONS --------------------
    static void scheduleAppointment() {
        System.out.print("Enter Patient ID: "); String id = input.nextLine();
        int index = -1; for (int i = 0; i < patientCount; i++) if (patientID[i].equals(id)) { index = i; break; }
        if (index == -1) { System.out.println("Patient not found"); return; }
        System.out.print("Enter Appointment Date: "); appointmentDate[index] = input.nextLine();
        System.out.print("Enter Appointment Time: "); appointmentTime[index] = input.nextLine();
        System.out.print("Enter Doctor: "); appointmentDoctor[index] = input.nextLine();
        System.out.println("Appointment scheduled!");
    }

    static void viewEmergencyCases() {
        System.out.println("╔═════════ EMERGENCY CASES ═════════╗");
        boolean found = false;
        for (int i = 0; i < patientCount; i++) {
            if (emergencyPriority[i].equalsIgnoreCase("High")) {
                System.out.println("ID:" + patientID[i] + " Name:" + patientName[i] + " Disease:" + patientDisease[i] + " Room:" + roomNumber[i]);
                found = true;
            }
        }
        if (!found) System.out.println("No high priority cases found.");
    }

    static void generateReport() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("hospital_report.txt"));
            for (int i = 0; i < patientCount; i++) {
                bw.write("ID:" + patientID[i] + " Name:" + patientName[i] + " Age:" + patientAge[i] + " Gender:" + patientGender[i] + " Disease:" + patientDisease[i] + " Doctor:" + patientDoctor[i] + " Admission:" + admissionDate[i] + " Room:" + roomNumber[i] + " Contact:" + patientContact[i] + " Priority:" + emergencyPriority[i] + "\n");
            }
            bw.close();
            System.out.println("Report generated as hospital_report.txt");
        } catch (Exception e) { System.out.println("Error generating report"); }
    }

    static void viewStatistics() {
        int high = 0;
        for (int i = 0; i < patientCount; i++) if (emergencyPriority[i].equalsIgnoreCase("High")) high++;
        System.out.println("Total Patients: " + patientCount);
        System.out.println("Emergency Cases: " + high);
    }

    static void billing() {
        System.out.print("Enter Patient ID: "); String id = input.nextLine();
        int index = -1; for (int i = 0; i < patientCount; i++) if (patientID[i].equals(id)) { index = i; break; }
        if (index == -1) { System.out.println("Patient not found"); return; }
        int doctorFee = 1500;
        System.out.print("Enter Medicine Charges: "); int medicine = Integer.parseInt(input.nextLine());
        System.out.print("Enter Room Charges: "); int roomCharge = Integer.parseInt(input.nextLine());
        int total = roomCharge + doctorFee + medicine;
        System.out.println("Room: " + roomCharge + " Doctor Fee: " + doctorFee + " Medicine: " + medicine + " Total: " + total);
    }

    // -------------------- FILE HANDLING --------------------
    static void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("patients.txt"));
            for (int i = 0; i < patientCount; i++) {
                bw.write(patientID[i] + "," + patientName[i] + "," + patientAge[i] + "," + patientGender[i] + "," + patientDisease[i] + "," + patientDoctor[i] + "," + admissionDate[i] + "," + roomNumber[i] + "," + patientContact[i] + "," + emergencyPriority[i] + "\n");
            }
            bw.close();
        } catch (Exception e) { System.out.println("Error saving data"); }
    }

    static void loadFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("patients.txt"));
            String line;
            patientCount = 0; // reset before loading
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                patientID[patientCount] = data[0];
                patientName[patientCount] = data[1];
                patientAge[patientCount] = Integer.parseInt(data[2]);
                patientGender[patientCount] = data[3];
                patientDisease[patientCount] = data[4];
                patientDoctor[patientCount] = data[5];
                admissionDate[patientCount] = data[6];
                roomNumber[patientCount] = data[7];
                patientContact[patientCount] = data[8];
                emergencyPriority[patientCount] = data[9];
                patientCount++;
            }
            br.close();
        } catch (Exception e) { System.out.println("No previous data found."); }
    }

    // -------------------- SEARCH --------------------
    static void searchPatient() {
        System.out.println("Search by: 1.ID 2.Name 3.Disease");
        String choice = input.nextLine();
        boolean found = false;
        switch (choice) {
            case "1": System.out.print("Enter ID: "); String id = input.nextLine();
                for (int i = 0; i < patientCount; i++) if (patientID[i].equals(id)) { 
                    System.out.println("Name:" + patientName[i] + " Age:" + patientAge[i] + " Disease:" + patientDisease[i] + " Room:" + roomNumber[i]); 
                    found = true;
                } break;
            case "2": System.out.print("Enter Name: "); String name = input.nextLine();
                for (int i = 0; i < patientCount; i++) if (patientName[i].equalsIgnoreCase(name)) { 
                    System.out.println("ID:" + patientID[i] + " Age:" + patientAge[i] + " Disease:" + patientDisease[i] + " Room:" + roomNumber[i]); 
                    found = true;
                } break;
            case "3": System.out.print("Enter Disease: "); String dis = input.nextLine();
                for (int i = 0; i < patientCount; i++) if (patientDisease[i].equalsIgnoreCase(dis)) { 
                    System.out.println("ID:" + patientID[i] + " Name:" + patientName[i] + " Age:" + patientAge[i] + " Room:" + roomNumber[i]); 
                    found = true;
                } break;
            default: System.out.println("Invalid choice");
        }
        if (!found) System.out.println("No patient found.");
    }
}
