# Warehouse Manager

📌 **Project Overview**

This application is an **Orders Management System** designed to process client orders for a warehouse. The project was developed for the *Fundamental Programming Techniques* course at the **Technical University of Cluj-Napoca (TUCN)**.

The system focuses on efficient order processing and data management, utilizing a **Layered Architectural Pattern** and **Relational Databases** to store clients, products, and orders.

🛠️ **Key Features**

* **Client Management:** Functionality to add a new client, edit, delete, and view all clients in a table (`JTable`).
* **Product Management:** Functionality to add a new product, edit, delete, and view all products in a table (`JTable`).
* **Order Processing & Stock Logic:**
  * Create orders by selecting an existing client, an existing product, and inserting a desired quantity.
  * Displays an under-stock message in case there are not enough products.
  * Automatically decrements the product stock after the order is finalized.
  * Generates an immutable **Bill** using Java records for each order, which is stored in a Log table.
* **Advanced Reflection Techniques:**
  * Dynamically extracts object properties to generate table headers and populates the table with list elements.
  * A generic class containing methods (create, edit, delete, find) for database access.
* **Data Processing:** Uses lambda expressions and streams for list and array processing.

🏛️ **Architecture & Requirements**

The project follows a **Layered Architectural Pattern** containing at least 4 packages (`dataAccessLayer`, `businessLayer`, `model`, and `presentation`) and adheres to the following technical constraints:

* **Relational Database:** Stores the data using a minimum of three tables: Client, Product, and Order.
* **Swing GUI:** A graphical user interface for all client, product, and order operations.
* **Clean Code:**
  * Methods are limited to a maximum of 30 lines.
  * Classes are limited to a maximum of 300 lines (excluding UI classes).
  * Adherence to standard Java naming conventions.

📁 **Deliverables**

* **Source Code:** Java files accompanied by generated JavaDoc files.
* **Database Dump:** SQL dump file containing the statements for creating and populating the database tables.
* **UML Diagrams:** Use Case, Package, and Class diagrams (draw.io) included in the repository.

🚀 **Installation & Running**

To run this project locally, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/pop-dragos/warehouse-manager.git](https://github.com/pop-dragos/warehouse-manager.git)
