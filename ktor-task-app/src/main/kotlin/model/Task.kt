package com.example.model

enum class Priority {
    Low, Medium, High, Vital
}

data class Task(
    val name: String,
    val description: String,
    val priority: Priority
)

fun Task.taskAsRow() = """
    <tr>
        <td>$name</td><td>$description</td><td>$priority</td>
    </tr>
    """.trimIndent()

fun List<Task>.tasksAsTable() = this.joinToString(
    prefix = "<table rules=\"all\">",
    postfix = "</table>",
    separator = "\n",
    transform = Task::taskAsRow
)

//============================
//============================
//============================
//
//Prompt:
//does taskAsRow() and tasksAsTable() function belong to Task class?
//
//---
//
//Answer:
//Great question — this is exactly where Kotlin starts feeling “different”.
//
//---
//
//## 🧠 Short answer first
//
//👉 **No, these functions do NOT belong to the `Task` class.**
//
//They are **extension functions**, not class methods.
//
//---
//
//## 🔍 Let’s break your code
//
//### 1. Enum class
//
//```kotlin
//enum class Priority {
//    Low, Medium, High, Vital
//}
//```
//
//👉 Just defines fixed priority values.
//
//---
//
//### 2. Data class
//
//```kotlin
//data class Task(
//    val name: String,
//    val description: String,
//    val priority: Priority
//)
//```
//
//👉 This creates a model with:
//
//* name
//* description
//* priority
//
//Kotlin automatically gives:
//
//* `toString()`
//* `equals()`
//* `copy()`
//
//---
//
//## ⭐ 3. Extension function on Task
//
//```kotlin
//fun Task.taskAsRow() = """
//    <tr>
//        <td>$name</td><td>$description</td><td>$priority</td>
//    </tr>
//""".trimIndent()
//```
//
//### 🧠 What this means
//
//This is NOT inside the class.
//
//It means:
//
//> “Add a new function to `Task` as if it were part of it”
//
//So you can call:
//
//```kotlin
//task.taskAsRow()
//```
//
//even though `Task` class never defined it.
//
//---
//
//### 🔥 Important truth
//
//Inside this function:
//
//* `this` = the Task object
//* `name`, `description`, `priority` come from that object
//
//But:
//    👉 It is still a **static utility function behind the scenes**
//
//---
//
//## ⭐ 4. Extension on List<Task>
//
//```kotlin
//fun List<Task>.tasksAsTable() = this.joinToString(
//    prefix = "<table rules=\"all\">",
//    postfix = "</table>",
//    separator = "\n",
//    transform = Task::taskAsRow
//)
//```
//
//### 🧠 Meaning
//
//This adds a function to:
//
//> “any List of Task objects”
//
//So you can call:
//
//```kotlin
//tasks.tasksAsTable()
//```
//
//---
//
//### 🔧 What it does internally
//
//* loops through list
//* converts each Task → HTML row
//* joins them into a table
//
//Equivalent logic:
//
//```kotlin
//for (task in tasks) {
//    task.taskAsRow()
//}
//```
//
//---
//
//## ❓ Your main question
//
//### “Do these belong to Task class?”
//
//👉 **No.**
//
//They are:
//
//> **Extension functions defined outside the class but usable like they belong to it**
//
//---
//
//## 🧠 Mental model (very important)
//
//Think of it like this:
//
//### Normal method
//
//```kotlin
//class Task {
//    fun taskAsRow() {}
//}
//```
//
//👉 Truly inside the class
//
//---
//
//### Extension method (your case)
//
//```kotlin
//fun Task.taskAsRow()
//```
//
//👉 Defined outside
//👉 Used like inside
//👉 Actually compiled as a static function
//
//---
//
//## ⚡ Why Kotlin does this
//
//In Kotlin, extension functions exist to:
//
//✔ Add behavior without modifying original classes
//✔ Avoid inheritance
//✔ Keep code clean and modular
//
//---
//
//## 🏁 Final takeaway
//
//* ❌ Not part of `Task` class
//* ✅ Extension function (syntactic sugar)
//* ✅ Feels like a method, but isn’t actually one
//
//---
//
//If you want, I can show:
//👉 how this compiles to Java bytecode (very interesting)
//👉 or how extension functions differ from inheritance (common interview question)
