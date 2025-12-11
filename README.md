# **OOP Final Project — Budget Tracking**

A Java desktop application for tracking expenses and visualizing spending. 
---

## **Features**

* Add new expenses with descriptions, store, price, category, and date
* View all expenses in a sorted table
* Search for a specific expense based on title or description keyowrds
* Pie chart visualization of spending by category
* Time-series chart showing spending over time

---

## **Technologies Used**

| Component   | Technology |
| ----------- | ---------- |
| Language    | Java       |
| GUI         | Swing      |
| Charts      | JFreeChart |
| Date Picker | JCalendar  |
| Styling     | JGoodies   |

---

## **Required Libraries**

To run the project, the following `.jar` files must be added to your classpath:

* `jcalendar-1.4.jar`
* `jfreechart-2.0.0-SNAPSHOT.jar`
* `jgoodies-common-1.2.0.jar`
* `jgoodies-looks-2.4.1.jar`
* `junit-4.6.jar`

These are included in the **lib/** directory.

---

## **How to Run**

### **1. Clone the repository**

```bash
git clone https://github.com/bdeweesevans/oop-final-project.git
cd oop-final-project
```

### **2. Add external libraries to classpath**

**Eclipse:**
`Right-click project → Build Path → Configure Build Path → Add External JARs`

Ensure *all JARs* in the **lib/** folder are added.

### **3. Compile & Run**

**Run from the controller:**

`src/controller/Main.java`

---

## **Architecture Overview**

### **Model**

* **Expense** — represents a single expense record
* **ExpenseType** — enumeration for categories

### **View**

* **MainGUI** — main window
* **AddExpenseDialog** — form for adding expenses
* **ExpenseTablePanel** — table of expenses
* **PieChartPanel** — expense type visualization
* **TimeSeriesChartPanel** — spending over time visualization

### **Controller**

* **Main.java** — entry point, wires components together

### **Utility**

* **ExpenseManager** — add, retrieve, and search expense logic
* **FileManager** — saving and loading data

---

## **Future Improvements**

* Add budget goals and alerts
* Dark mode
* Cloud synchronization
* User sign-in features

---

**Developed by:** Benjamin DeWeese, Jania Jones, and Olivia Moody

---
