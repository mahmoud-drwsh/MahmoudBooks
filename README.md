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
7. A button to mark the book as a favorite as vice versa.

## On the favorites screen, the user should be shown:

1. A list of favorite books.

## On the settings screen, the user should be shown:

1. An option for changing the UI mode from dark to light and vice versa.

# The layers

The app has the following modules:

1. The app module connects everything else to produce a working app.
2. The data module responsible for everything to deal with data.
3. The presentation layer responsible for the UI of the various screens.
4. The domain layer which works as an added abstraction layer between the presentation and data
   layers.
5. The compose_components layer which contains various reusable Composables and contains the string
   resources used in the UI.
6. The favorites dynamic feature module which contains the code for showing the list of favorite
   books.

## The libraries

1. Jetpack Compose.
2. Room
3. DataStore
4. Retrofit
5. Compose Destinations. The docs: https://composedestinations.rafaelcosta.xyz/
6. Flows and coroutines.
7. Hilt
8. Landscapist Glide. https://github.com/skydoves/landscapist