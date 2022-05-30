# Important Note

I used {Android Studio Electric Eel | 2022.1.1 Canary 2} for building and testing this app. Please use the same version to build the app and test it.

AGP version     = 7.2.0
Gradle version  = 7.4.2

# About The App

This app is a partial clone of Google books. The reason for that is that the goal of this submission is showing that we understand the concepts mentioned in the class up to the respective submission.

# Submission 2 requirements

1. CI application
   For CI, I used CircleCI. The workflow has two jobs, one for the Android instrumentation tests, and another for building the APK file and uploading it as an artifact. 
   The app has two instrumentation tests in the data module and they are the ones run by CircleCi.
   This link can be used to view the CircleCI project: https://app.circleci.com/pipelines/github/mahmoud-drwsh/MahmoudBooks?invite=true
   The project is available on GitHub as well and can be accessed by anyone on GitHub, and here's the link: https://github.com/mahmoud-drwsh/MahmoudBooks
2. LeakCanary
   I have added LeakCanary to this project and when the app is run, no issues are shown.
3. Performance-related issues
   There are no problems when running the Android performance code inspection.
4. Obfuscation
   As an added point, I enabled obfuscation for debug builds to ensure that there are no problems related to obfuscation.
5. Encryption
   The app uses SQLiteCipher for encrypting the SQLite database.
6. Certificate pinning
   The technique applied in this app is Hash Pinning.
   In the data module, in the Kotlin file named GoogleBooksApi.kt, more specifically in the function body of getBooksServiceInstance, declared therein, you'll find how Hash Pinning was applied with Retrofit and a provided OkHttpClient there.
7. All the previous features are maintained in this submission.

# Applied suggestions

1. The UI is declared in a way that makes it easy to understand & look at; proper margins and dimensions were applied to the UI elements.
2. The code in this project differs from the code introduced in this course. And Libraries like Jetpack Compose, and Compose Destinations have been used in this project and some weren't taught in this course, and others were used in ways that were not explicitly explained in the course.
3. The app has instrumentation tests to ensure that the data received from the server is properly processed.
4. The app has much more features than the 3 main ones.
5. An added aspect of security this app applies that is not explicitly mentioned in the course is keeping the dependencies used in the project up to date.

# Architecture

- The app has a cache layer to help with reducing the number of requests to the server made. 
- The app applies the MVVM architecture pattern. 
- There are data models for the domain, data, and UI layers each, with each being having a suffix denoting the layer to which it belongs, except for the model for the domain layer that is called just core.
- The principle of data flowing up and events flowing down is followed closely in this implementation of the app.
- Flows are extensively used to apply the principle of reactive programming.
- Koin is used for dependency injection.
- The app uses Compose Destinations for managing the screen destinations and navigation in the app.

# The API Used

I used the following endpoint for requesting volumes data from the API.
https://www.googleapis.com/books/v1/volumes

# Tests

The app has two tests to ensure that the data returned by the Google Books API is as expected.

# Code Comments

I have written comments where I felt confusion might arise or lack of clarity might be. I have tried my best to use expressive names which I have found something that reduces the need for extensive commenting.

# Internationalization

I have tried to my best to make this code easily prepared for being internationalized by saving all the strings in the data module in main\res\values\strings.xml  

# Main Features

1. Searching for books
2. Viewing books details
3. Adding books to the list of favorites
4. Removing books from the list of favorites
5. Search results caching.
6. Caching layer that makes the app usable in cases where previous search results satisfy the searches done when the phone is disconnected.

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
- The data module is responsible for everything to deal with data.
- The ui_core layer which contains various reusable Composables and contains the string resources used in the UI.
- The ui_main module is responsible for the UI of the various screens needed for running the app.
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
- Many more...
