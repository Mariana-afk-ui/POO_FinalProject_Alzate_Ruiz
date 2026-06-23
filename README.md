# Video Game Store Management System

Team Members
* **Name: [Carlos Augusto Ruiz Mendez] - **ID:** [53576]
* **Name: [Mariana Alzate Salazar] - **ID:** [55512]

---

## General System Description
This system is an object-oriented software solution designed to manage the internal operations of a video game store. The project was structured following a clean architecture divided into three independent layers:
* **Domain Layer (`domain`):** Contains the business logic, data entities (`VideoGame`, `Customer`, `Employee`, `Sale`, `Category`), and transactional validation rules (stock control and financial revenue calculations).
* **User Interface Layer (`ui`):** An interactive console menu driven by the optimized helper class `Console`, facilitating user interaction without blocking execution threads.
* **Data Layer (`data`):** A persistence component based on object serialization (`.dat`), allowing the system to automatically save and retrieve its complete state across sessions.

### Artificial Intelligence (AI) Component
The system features a native, purely algorithmic **Content-Based Recommendation Engine**. When a specific customer is selected, the AI analyzes their transactional purchase history to determine their favorite video game category. It then predictively scans the current inventory to generate personalized title suggestions matching their preferences, automatically excluding out-of-stock items or games the customer already owns.

---

## Execution Instructions
1. Ensure you have the **Java Development Kit (JDK 17 or higher)** installed.
2. Open the project root folder in your preferred integrated development environment (IDE) (**Visual Studio Code** or **IntelliJ IDEA** are recommended).
3. Locate the main executable file at `src/Main.java`.
4. Run the file by clicking the **"Run"** button in your IDE or by using the following terminal commands:
   ```bash
   javac src/Main.java src/domain/*.java src/ui/*.java src/data/*.java -d bin
   java -cp bin Main

=========================================
     VIDEO GAME STORE - INTERNAL SYSTEM  
=========================================
1. Register Video Game
2. List & Search Video Games
3. Modify Video Game
4. Delete Video Game
5. Register Customer
6. Register a Sale (Transaction)
7. AI Component: View Customer Recommendations
8. View Financial Revenue Report
9. Exit

Select an option: 7

Enter customer ID to analyze preferences: C01
Running basic AI matching algorithm based on customer history...

>>> AI RECOMMENDED TITLES FOR ALICE SMITH <<<
 - The Witcher 3 (RPG) at $39.99