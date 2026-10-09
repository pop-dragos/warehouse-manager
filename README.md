# Warehouse Manager

📌 **Project Overview**

This application is an **Orders Management System** designed to process client orders for a warehouse. The project was developed for the *Fundamental Programming Techniques* course at the **Technical University of Cluj-Napoca (TUCN)**[cite: 2].

The system focuses on efficient order processing and data management, utilizing a **Layered Architectural Pattern** and **Relational Databases** to store clients, products, and orders[cite: 2, 3].

🛠️ **Key Features**

* **Client Management:** Functionality to add a new client, edit, delete, and view all clients in a table (`JTable`).
* **Product Management:** Functionality to add a new product, edit, delete, and view all products in a table (`JTable`)[cite: 3].
* **Order Processing & Stock Logic:**
  * Create orders by selecting an existing client, an existing product, and inserting a desired quantity[cite: 3].
  * Displays an under-stock message in case there are not enough products[cite: 3].
  * Automatically decrements the product stock after the order is finalized[cite: 3].
  * Generates an immutable **Bill** using Java records for each order, which is stored in a Log table[cite: 3].
* **Advanced Reflection Techniques:**
  * Dynamically extracts object properties to generate table headers and populates the table with list elements[cite: 3].
  * A generic class containing methods (create, edit, delete, find) for database access[cite: 3].
* **Data Processing:** Uses lambda expressions and streams for list and array processing[cite: 3].

🏛️ **Architecture & Requirements**

The project follows a **Layered Architectural Pattern** containing at least 4 packages (`dataAccessLayer`, `businessLayer`, `model`, and `presentation`) and adheres to the following technical constraints[cite: 3]:

* **Relational Database:** Stores the data using a minimum of three tables: Client, Product, and Order[cite: 3].
* **Swing GUI:** A graphical user interface for all client, product, and order operations[cite: 3].
* **Clean Code:**
  * Methods are limited to a maximum of 30 lines[cite: 3].
  * Classes are limited to a maximum of 300 lines (excluding UI classes)[cite: 3].
  * Adherence to standard Java naming conventions[cite: 3].

📁 **Deliverables**

* **Source Code:** Java files accompanied by generated JavaDoc files[cite: 3].
* **Database Dump:** SQL dump file containing the statements for creating and populating the database tables[cite: 2].
* **UML Diagrams:** Use Case, Package, and Class diagrams (draw.io) included in the repository[cite: 2].

🚀 **Installation & Running**

To run this project locally, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/pop-dragos/warehouse-manager.git](https://github.com/pop-dragos/warehouse-manager.git)
