
# MovieApp
Movie Info App is an Android application that allows users to explore various movie information. It utilizes the TMDB API to fetch movie data, implements caching techniques using Room Database, incorporates pagination, and displays different types of movie information. The app follows the MVVM pattern and adheres to clean architecture principles.


## Features
- Jetpack Compose UI: Utilizes Jetpack Compose for building the user interface, enabling a modern and reactive UI development experience.
- Fetching Movie Info: The app fetches movie information from the   TMDB API using Retrofit.
- Caching Technique: Implemented caching techniques using Room Database to store fetched movie data locally, providing faster access and offline capabilities.
- Pagination: Utilizes pagination to efficiently handle large amounts of movie data and optimize performance.
- Different Movie Info: Displays various types of movie information such as popular movies, top-rated movies, upcoming movies, etc.
  
## Preview

![App Screenshot](https://github.com/Bhushanp091/MovieApp/blob/master/ScreenShots/Screenshot%202024-03-02%20213233.png?raw=true) 
![App Screenshot](https://github.com/Bhushanp091/MovieApp/blob/master/ScreenShots/Screenshot%202024-03-02%20213223.png?raw=true)

# Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern, combined with clean architecture principles for better separation of concerns and maintainability.

## Clean Architecture Layers:

- Presentation Layer: Contains the UI components and ViewModels responsible for presenting data to the user and handling user interactions.

- Domain Layer: Contains uistate and use cases that are independent of any specific framework or technology. It defines entities, repositories, and use cases.

- Data Layer: Handles data operations such as fetching data from APIs or databases, caching, and data mapping. It consists of repositories, data sources, and database entities.


![App Screenshot](https://github.com/Bhushanp091/MovieApp/blob/master/ScreenShots/Screenshot%202024-03-02%20213152.png?raw=true)
![App Screenshot](https://github.com/Bhushanp091/MovieApp/blob/master/ScreenShots/Screenshot%202024-03-02%20213213.png?raw=true)
