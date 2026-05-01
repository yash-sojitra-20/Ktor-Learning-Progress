//package com.example.model
//
//val tasks = mutableListOf(
//    Task("cleaning", "Clean the house", Priority.Low),
//    Task("gardening", "Mow the lawn", Priority.Medium),
//    Task("shopping", "Buy the groceries", Priority.High),
//    Task("painting", "Paint the fence", Priority.Medium)
//)

package com.example.model

object TaskRepository {
    private val tasks = mutableListOf(
        Task("cleaning", "Clean the house", Priority.Low),
        Task("gardening", "Mow the lawn", Priority.Medium),
        Task("shopping", "Buy the groceries", Priority.High),
        Task("painting", "Paint the fence", Priority.Medium)
    )

    fun allTasks(): List<Task> = tasks.toList()

    fun tasksByPriority(priority: Priority) = tasks.filter {
        it.priority == priority
    }

    fun taskByName(name: String) = tasks.find {
        it.name.equals(name, ignoreCase = true)
    }

    fun addTask(task: Task) {
        if(taskByName(task.name) != null) {
            throw IllegalStateException("Cannot duplicate task names!")
        }
        tasks.add(task)
    }
}

//============================
//============================
//============================
//
//Prompt:
//why we have created object instead of the class?
//
//---
//
//Answer:
//
//Good question — this is actually an important Kotlin design concept.
//
//---
//
//## 🧠 Why `object` instead of `class`?
//
//In Kotlin:
//
//👉 `object` creates a **singleton**
//👉 `class` creates **multiple instances**
//
//---
//
//## 🔑 What your code represents
//
//```kotlin id="x8m2qz"
//object TaskRepository
//```
//
//This means:
//
//> There will be **only ONE TaskRepository in the entire application**
//
//---
//
//## ⚙️ Why that makes sense here
//
//Your repository contains:
//
//```kotlin id="p2k9lm"
//private val tasks = mutableListOf(...)
//```
//
//This is **shared application data**
//
//So you want:
//
//* One central list of tasks
//* One consistent state
//* No duplicates of repository
//
//---
//
//## ❌ What would happen if you used `class`
//
//If you wrote:
//
//```kotlin id="c9v2kd"
//class TaskRepository
//```
//
//Then you could do:
//
//```kotlin id="w3k9lp"
//val repo1 = TaskRepository()
//val repo2 = TaskRepository()
//```
//
//👉 Now you have:
//
//* multiple task lists
//* inconsistent data
//* confusion in backend state
//
//---
//
//## 🧠 Real-world analogy
//
//### `class`
//
//👉 blueprint for multiple objects
//Example: `Task`, `User`
//
//---
//
//### `object`
//
//👉 single shared system
//Example:
//
//* database connection
//* logger
//* repository
//
//---
//
//## 🧩 Why repository is usually an object
//
//In backend apps like Ktor:
//
//A repository:
//
//* manages data
//* should be consistent
//* should not duplicate state
//
//So singleton is perfect.
//
//---
//
//## ⚡ Important insight
//
//Your code is basically:
//
//> “global in-memory database for tasks”
//
//That’s why `object` is used.
//
//---
//
//## 🧠 One-line summary
//
//👉 `object TaskRepository` = **one shared instance for whole app**
//👉 `class TaskRepository` = **multiple independent copies**
//
//---
//
//## 💡 When to use what
//
//| Use `class`               | Use `object`                 |
//| ------------------------- | ---------------------------- |
//| Users, Orders, Tasks      | Repositories, Config, Logger |
//| Multiple instances needed | Single shared instance       |
//
//---
//
//## 🏁 Final takeaway
//
//You used `object` because:
//
//✔ You want **one shared task storage**
//✔ You don’t want multiple copies of repository
//✔ It behaves like a simple in-memory database
//
//---
//
//If you want, I can show:
//👉 how this changes when you move to real DB (PostgreSQL/MongoDB)
//👉 or how Spring Boot does the same thing using `@Service` (useful comparison)

//============================
//============================
//============================
//
//Prompt:
//(
//👉 how this changes when you move to real DB (PostgreSQL/MongoDB)
//)
//
//Yes
//
//---
//
//Answer:
//
//Good — this is the exact next step after your `TaskRepository object`.
//
//Let’s connect the idea of your in-memory repository to a real database like **PostgreSQL**.
//
//---
//
//## 🧠 What you have now (in-memory)
//
//Your current `TaskRepository`:
//
//```kotlin
//object TaskRepository {
//    private val tasks = mutableListOf<Task>()
//}
//```
//
//### Meaning:
//
//* Data lives in **RAM (memory)**
//* Resets when app restarts
//* Only one copy exists (singleton)
//
//👉 This is NOT persistent storage
//
//---
//
//## 🧱 What changes with PostgreSQL
//
//With PostgreSQL:
//
//* Data is stored on **disk**
//* Survives restarts
//* Can be accessed by multiple apps/users
//* Is queried using SQL
//
//So instead of:
//
//```kotlin
//tasks.add(task)
//tasks.filter { ... }
//```
//
//You do:
//
//```sql
//INSERT INTO tasks ...
//SELECT * FROM tasks WHERE priority = 'HIGH'
//```
//
//---
//
//## 🔁 Mapping concept (very important)
//
//| Your Repository (now) | PostgreSQL world |
//| --------------------- | ---------------- |
//| `mutableListOf()`     | Table            |
//| `Task` object         | Row              |
//| `tasks.add()`         | INSERT           |
//| `filter()`            | SELECT WHERE     |
//| `find()`              | SELECT LIMIT 1   |
//
//---
//
//## ⚙️ How Ktor works with PostgreSQL
//
//In Ktor, you typically:
//
//### Option 1: SQL directly (simple learning)
//
//Use JDBC:
//
//```kotlin
//val result = connection.prepareStatement("SELECT * FROM tasks").executeQuery()
//```
//
//---
//
//### Option 2 (modern): ORM / DSL
//
//Use tools like:
//
//* Exposed (JetBrains ORM)
//* Hibernate (JPA)
//* R2DBC (reactive)
//
//Example with Exposed:
//
//```kotlin
//Tasks.selectAll()
//```
//
//---
//
//## 🧠 What replaces your `object TaskRepository`?
//
//Instead of:
//
//```kotlin
//object TaskRepository
//```
//
//You still *can keep a repository*, but now it becomes:
//
//> a **bridge between Ktor and PostgreSQL**
//
//So it becomes:
//
//```kotlin
//object TaskRepository {
//    fun allTasks(): List<Task> = database query
//}
//```
//
//---
//
//## 🔥 Key conceptual shift
//
//### Before (your current code)
//
//👉 “I store and manage data myself”
//
//### After PostgreSQL
//
//👉 “Database stores data, I just ask for it”
//
//---
//
//## 🧩 Why object still exists even with DB
//
//Even with PostgreSQL:
//
//✔ Repository pattern still used
//✔ But it no longer stores data
//✔ It only *fetches/manipulates DB data*
//
//So `object` becomes:
//
//* a **single gateway to database**
//
//---
//
//## 🏁 Final understanding
//
//* Your current `TaskRepository` = in-memory fake database
//* PostgreSQL = real persistent database
//* Repository pattern stays, but implementation changes
//
//---
//
//## 💡 One-line summary
//
//👉 PostgreSQL replaces your `mutableListOf()` with a real persistent table, while your repository becomes a query layer instead of a storage layer.
//
//---
//
//If you want next step, I can show:
//👉 how to convert your exact `TaskRepository` into PostgreSQL using Exposed (step-by-step)
//👉 or how real backend apps structure repository → service → controller layers (very important for jobs)
