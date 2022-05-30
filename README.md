# Important Note

I used {Android Studio Chipmunk | 2021.2.1} for building and testing this app. Please use the same version to build the app and test it.

AGP version     = 7.2.0
Gradle version  = 7.4.2

# About The App

This app is a partial clone of Google books. The reason for that is the goal of this submission is
showing that we understand the concepts mentioned in the class up to the respective submission.

# Architecture

- The app has a cache layer to help with reducing the number of requests to the server made. 
- The app applies the MVVM architecture pattern. 
- There are data models for the domain, data, and UI layers each, with each being having a suffix denoting the layer to which it belongs except for the model for the domain layer.
- The principle of data flowing up and events flowing down is followed closely in this implementation of the app.
- Flows are extensively used to apply the principle of reactive programming.
- Koin is used for dependency injection.
- The app uses Compose Destinations for managing the screen destinations and navigation in the app.

# The API Used

I used the following endpoint for requesting volumes data from the API.
https://www.googleapis.com/books/v1/volumes

# Tests

The app has two tests to ensure that the data returned by the Google Books API is what is expected.

# Code Comments

I have written comments where I felt confusion might arise or of lack of clarity might be present. I have tried my best to use expressive names which I have found reduces the need for extensive commenting.

# Internationalization

I have tried to my best to make this code easily prepared for being internationalized by saving all the strings in the data module in main\res\values\strings.xml  

# Main Features

1. Searching for books
2. Viewing books details
3. Adding books to the list of favorites
4. Removing books from the list of favorites
5. Search results caching.

# User requirements

## On the home screen, the user should be shown:

1. A search bar and the results below it.
2. The source of the data shown.
    - The results are loaded from the cache by default unless no results are found in the cache.
3. A button to force data to be fetched from the live server.

## On the details screen, the user should be shown:

1. A book thumbnail.
2. The list of authors.
3. The book title.
4. The average rating and number of ratings.
5. The number of pages.
6. A button to go to Google Play to buy the book.
7. A button to mark the book as a favorite and vice versa.

## On the favorites screen, the user should be shown:

1. A list of favorite books.
   1. When the user clicks on one of the images, the details are shown as in the app's home screen 

# The project modules

The app has the following modules:

- The app module connects everything else to produce a working app.
- The core module works as an added abstraction layer between the presentation and data layers.
- The data module responsible for everything to deal with data.
- The ui_core layer which contains various reusable Composables and contains the string resources used in the UI.
- The main_ui module is responsible for the UI of the various screens needed for running the app.
- The ui_favorites dynamic feature module which contains the code for showing the list of favorite books.

## The libraries

- Jetpack Compose. https://developer.android.com/jetpack/compose
- Room. https://developer.android.com/training/data-storage/room
- Retrofit. https://square.github.io/retrofit
- Flows and coroutines. 
  - https://kotlinlang.org/docs/flow.html
  - https://kotlinlang.org/docs/coroutines-overview.html
- Koin. https://insert-koin.io
- Compose Destinations. The docs: https://composedestinations.rafaelcosta.xyz
- Landscapist Glide. https://github.com/skydoves/landscapist
