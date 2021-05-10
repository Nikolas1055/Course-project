import baseclasses.DataBase;
import baseclasses.Role;
import officeclasses.AdminPanel;
import officeclasses.HROffice;
import serviceclasses.*;

public class Main {
    public static void main(String[] args) {
        DataBase dataBase = FileOperations.loadDataBaseFromFile();
        Role authUserRole = Authorization.authUser(dataBase.getEmployees());
        while (true) {
            Menu.mainMenu(authUserRole);
            int choice = Service.selector();
            switch (choice) {
                default -> defaultChoice(dataBase);
                case 1 -> findEmployee(dataBase, authUserRole);
                case 2 -> reports(dataBase);
                case 3 -> {
                    if (authUserRole.equals(Role.HR_OFFICER) || authUserRole.equals(Role.ADMINISTRATOR)) {
                        hrOffice(dataBase);
                    } else {
                        defaultChoice(dataBase);
                    }
                }
                case 4 -> {
                    if (authUserRole.equals(Role.ADMINISTRATOR)) {
                        adminPanel(dataBase);
                    } else {
                        defaultChoice(dataBase);
                    }
                }
            }
        }
    }

    public static void defaultChoice(DataBase dataBase) {
        FileOperations.saveDataBaseToFile(dataBase);
        System.exit(0);
    }

    public static void findEmployee(DataBase dataBase, Role authUserRole) {
        while (true) {
            Service.println("Выберите тип поиска (любой другой выбор для выхода):");
            Menu.findMenu();
            int choice = Service.selector();
            switch (choice) {
                case 1 -> EmployeeFinder.findEmployeeByFullName(dataBase.getEmployees(), authUserRole);
                case 2 -> EmployeeFinder.findEmployeeByPost(dataBase.getEmployees(), dataBase.getPosts(), authUserRole);
                case 3 -> EmployeeFinder.findEmployeeByDepartment(dataBase.getEmployees(), dataBase.getDepartments(), authUserRole);
                case 4 -> EmployeeFinder.findEmployeeByChief(dataBase.getEmployees(), authUserRole);
                default -> {
                    return;
                }
            }
        }
    }

    public static void reports(DataBase dataBase) {
        Reports reports = new Reports(dataBase);
        while (true) {
            Service.println("Выберите отчет (любой другой выбор для выхода):");
            Menu.reportMenu();
            int choice = Service.selector();
            switch (choice) {
                case 1 -> reports.printCompanyStructure();
                case 2 -> reports.printAverageSalaries();
                case 3 -> reports.printMostExpensiveEmployees();
                case 4 -> reports.printMostDedicatedEmployees();
                default -> {
                    return;
                }
            }
        }
    }

    public static void hrOffice(DataBase dataBase) {
        HROffice hrOffice = new HROffice(dataBase);
        while (true) {
            Service.println("Выберите действие (любой другой выбор для выхода):");
            Menu.hrMenu();
            int choice = Service.selector();
            switch (choice) {
                case 1 -> hrOffice.addNewEmployee();
                case 2 -> hrOffice.addNewPost();
                case 3 -> hrOffice.addNewDepartment();
                case 4 -> hrOffice.changeEmployee();
                case 5 -> hrOffice.changeDepartment();
                case 6 -> hrOffice.dismissEmployee();
                default -> {
                    return;
                }
            }
        }
    }

    public static void adminPanel(DataBase dataBase) {
        AdminPanel adminPanel = new AdminPanel(dataBase);
        while (true) {
            Service.println("Выберите действие (любой другой выбор для выхода):");
            Menu.adminMenu();
            int choice = Service.selector();
            switch (choice) {
                case 1 -> adminPanel.addNewEmployee();
                case 2 -> adminPanel.deleteDepartment();
                default -> {
                    return;
                }
            }
        }
    }
}
