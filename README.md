# TmdbClient
A simple native Android application consuming the [MovieDB/TMDB API](https://developers.themoviedb.org/3) for constructing RESTful API.<br>
MoviesDb/TMDB API provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Movies.

#Tools
- Android Studio Bumblebee | 2021.1.1 Patch 2
  Build #AI-211.7628.21.2111.8193401, built on February 17, 2022
  Runtime version: 11.0.11+0-b60-7590822 x86_64
  VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
- Postman

#Implementation Notes
- Project has been implemented using Model–view–viewmodel (MVVM) this allows developers to appripriately separates UI logic from backend logic.
- On the homepage just after the SplashScreen movies are loaded for only one page and Cached locally using room database.
- When user clicks on movie item the will be presented to more details page, when they click on view all data will be loaded for that given category
  using paginator giving them an endless experience.
- The local caching enables the user to view movies that were earlier loaded with images, to view more details we prompt the user to make sure they are connected 
   to the internet. This enables the app to work partially when user is offline.
 
 #Open source Libraries used 
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Hilt for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs and paging network data.
- [Gson](https://github.com/square/gson/) - A modern JSON library for Kotlin and Java.
- [Coil](https://github.com/coil-kt/coil) - loading images to UI powered by coroutines.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- Room database Apis - For local caching of data.
- [SkeletonLayout](https://github.com/Faltenreich/SkeletonLayout) - Display animations while content is loading.
- [Timber](https://github.com/JakeWharton/timber)- Logging data for easier debugging of code.
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) - For displaying Circular imageviews for movie casts.
- [android-youtube-player](https://github.com/PierfrancescoSoffritti/android-youtube-player) - Play youtube trailer content and videos from TMDB.
- [Spotless](https://github.com/diffplug/spotless) - Code formatter and fixing issues with code.

#Assumptions made based on the requirements
- Since themoviedb.org is a very expansive API in itself, i assumed to work with resources from this url https://developers.themoviedb.org/3/movies since
  the requirements only mention Movies from TMDB.
  
#Running this App
- Clone and build the code using Android studio.
- On the root folder of your project create local.properties and add below line with your API key from themoviedb.org. If it already exists just add the line
 with your API key.
  serviceApiKey = your api key
- Rebuild the project and the config key should be picked automatically.
- You should be able to run the App and view movies successfully.  
 
