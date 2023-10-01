# ToDoList
# Todo List Application

Welcome to the Todo List Application repository. This application has been developed with a focus on providing a comprehensive task management experience. Below, you will find information about the features and functionalities of this application, along with insights into the architectural choices made.

## Architecture: MVVM,MVI

Key Components:

1. View: Renders the UI, observes changes in the state, or receives user actions.

2. Actions: Represents user interactions and intentions, triggering changes in the application.

3. Reducer: Computes the new state based on actions and the current state.

4. ViewModel: Exposes data and behaviors to the View, manages UI logic, and observes the Model.

5. State: Represents the current application state or aggregates data for the UI.

6. Store Container: Manages the current state and notifies the View of changes.

7. Model: Represents data and business logic, often including entities and use cases.

8. Repository: Manages data sources and provides a clean API for data access.

9. Use Cases: Encapsulate specific business logic and rules.

## Features

 1. Task Management
- Add New Tasks: Users can add new tasks with various attributes such as title, date, category, description, and priority.
- Mark Tasks as Completed: Users can mark tasks as completed once they are done.
- Delete Tasks: An option is provided to delete tasks when they are no longer needed.

2. Task Categories
- Categorize Tasks: Users can categorize tasks into different categories such as work, personal, shopping, and more.
- Filter by Category: The application allows users to filter tasks based on their chosen categories.

 3. Persistent Storage
- Local Storage: Tasks are stored locally using Datastore, ensuring data persistence across application sessions.

 4. User Interface (UI)
- Intuitive Design: The user interface is designed to be intuitive and user-friendly, making it easy for users to add, view, and filter tasks.
- Task Management: The UI provides a seamless experience for managing tasks efficiently.

 5. Task Notifications
- Set Reminders: Users can set reminders or notifications for tasks to help them stay organized and on top of their to-do lists.

 6. Sorting and Filtering
- Sort Tasks: Users have the option to sort tasks by various criteria, including priority, due date, and completeness.
- Filtering: The application allows users to filter tasks based on category and other parameters.
 7. Search
- Search Functionality: Users can easily search for specific tasks using the search feature, helping them quickly locate the tasks they need.

 Getting Started

To get started with the Todo List Application, follow these steps:

1. Clone the repository to your local machine.

2. Open the project in Android Studio IDE.

3. Build and run the application.

4. Explore the features.
[![Screenshot-20231001-232504-To-Do-List.jpg](https://i.postimg.cc/9Fd2LyFY/Screenshot-20231001-232504-To-Do-List.jpg)](https://postimg.cc/Vdk26rtJ)
[![Screenshot-20231001-232259-To-Do-List.jpg](https://i.postimg.cc/90f9LcyG/Screenshot-20231001-232259-To-Do-List.jpg)](https://postimg.cc/cgV6Hy9H)
[![Screenshot-20231001-232320-To-Do-List.jpg](https://i.postimg.cc/rmf0077z/Screenshot-20231001-232320-To-Do-List.jpg)](https://postimg.cc/njQcNSFt)


