# Easy-Note
🗒️ Simple Offline Note App helps to to create your notes. You can 📝 edit , ❌ delete , lock and pin notes too.
App respects its Base (Fragment , Activity , ViewMode , UseCase)

## technology 
- Room DB
- coroutines, flow, and Sharedflow to fire data
- channel to send action
- repository pattern and use case
- dependency injection (dagger-hilt)
- clean architecture and clean code
- navigation components
- MVI (Model-View-Intent)  Base on MVVM(Model-View-View-Model) 
- and more..

# Screenshots

<img src="https://user-images.githubusercontent.com/62269304/213454740-c55264b7-d016-4d34-a60f-0ea0d485f019.jpg" width="200">&nbsp;
<img src="https://user-images.githubusercontent.com/62269304/213455245-eb1ae173-366f-419b-aa7a-741b575a5ee9.jpg" width="200">&nbsp;
<img src="https://user-images.githubusercontent.com/62269304/213455362-3ac157d7-56bc-49f2-aa55-f49ec949c628.jpg" width="200">&nbsp;

<img src="https://user-images.githubusercontent.com/62269304/213455385-c47c2e62-61da-46c5-b8e2-96d519b7da91.jpg" width="200">&nbsp;
<img src="https://user-images.githubusercontent.com/62269304/213455403-90a1bf3f-22bd-401f-96e6-c85c9231c108.jpg" width="200">&nbsp;
<img src="https://user-images.githubusercontent.com/62269304/213455417-d1f52a4d-03be-4684-a1fa-d0f3689a6832.jpg" width="200">&nbsp;

## Built With

* [Kotlin](https://kotlinlang.org) - As a programming language.
* [Coroutines](https://developer.android.com/kotlin/coroutines) - For multithreading while handling requests to the server and local database.
* [Flow](https://developer.android.com/kotlin/flow) - In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value. For example, you can use a flow to receive live updates from a database.
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - To handle app navigation.
* [Sharedflow & StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) -To fire data
* [MVI Architecture](https://medium.com/swlh/mvi-architecture-with-android-fcde123e3c4a) - With no clear state management, view rendering along with the business logic can get a little bit messy as the application grows or adding functionalities or a feature that was not planned beforehand, and let’s be honest that can happen often, it’s rare to have all the features clearly and fully defined from the start of the project specifications, the more the app codebase is scalable the more it’s flexible to embrace new ideas and updates.
* [Model-View-ViewModel(MVVM)](https://developer.android.com/topic/architecture) - Offers an implementation of observer design pattern.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notifies views of any database changes in an observer way.
* [Room](https://developer.android.com/jetpack/androidx/releases/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [Glide](https://github.com/bumptech/glide) - It is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.
* [Koin](https://insert-koin.io/docs/quickstart/android/) - It is arguably the most used Dependency Injection, or DI, framework for Android. Many Android projects use Dagger to simplify building and providing dependencies across the app. It gives you the ability to create specific scopes, modules, and components, where each forms a piece of a puzzle: The dependency graph.
* [Clean Architecture](https://www.raywenderlich.com/3595916-clean-architecture-tutorial-for-android-getting-started) - Applying Clean Architecture , Solid Principles and use cases  to build a robust, maintainable, and testabl
