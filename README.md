# Introduction

This app is a partial clone of Google books. The reason for that is the goal of this submission is
showing that we understand the concepts mentioned in the class up to the respective submission.

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
