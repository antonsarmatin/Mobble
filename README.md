# Mobble
Mobble framework - CleanArch, Navigation detached form UI, MVVM like Presentation Layer Arch and other small solutions/utils for comfortable life in android dev with Clean and MVVM.


## Mobble framework has the following libraries:

1. Mobble:Utils [ ![Download](https://api.bintray.com/packages/antonsarmatin/Mobble/utils/images/download.svg) ](https://bintray.com/antonsarmatin/Mobble/utils/_latestVersion)
2. Mobble:MV [ ![Download](https://api.bintray.com/packages/antonsarmatin/Mobble/mv/images/download.svg) ](https://bintray.com/antonsarmatin/Mobble/mv/_latestVersion)
3. Mobble:Nav
4. Mobble:Clean

## Plans for the future

This framework is under construction!

- [x] Create
- [x] Mobble:Utils - Consumable LiveData, EventLiveData, etc.
- [x] Mobble:MV - Base App classes with error and loading handling
- [ ] ...
- [ ] Mobble:Utils - Small but useful utils
- [ ] Mobble:MV - MVVM like UI layer pattern with Events and States(based on Android MVVM)
- [ ] Mobble:Nav - Detached navigation based on MV State
- [ ] Mobble:Clean - CleanArchitecture library for fast implementation in new project

# Mobble:Utils

[ ![Download](https://api.bintray.com/packages/antonsarmatin/Mobble/utils/images/download.svg) ](https://bintray.com/antonsarmatin/Mobble/utils/_latestVersion)
### Add to your project

To start using this part, add these lines to the build.gradle of your project:

```xml
repositories {
    jcenter()
}

dependencies {
    implementation 'ru.sarmatin.mobble:utils:latest_version'
}
```

## ConsumableLiveData

LiveData class that wrapping ConsumableValue and uses ConsumableObserver.
Allows build consumable data with LiveData class.
Example: use it in SharedViewModels

### How to use

```kotlin

class SharedViewModel : ViewModel() {


    private val _data = ConsumableLiveData<String>()
    val data: LiveData<ConsumableValue<String>>
    get()  = _data

    //Use extension function postValue(value: String)
    fun setData(string: String){
        _data.postValue(string)
    }

}

sharedViewModel.setData("someData")

sharedViewModel.data.observe(viewLifecycleOwner, ConsumableObserver { data ->
            //TODO
        })


```

## EventLiveData

LiveData class with Event-like behavior. Every observer that subsribed this LiveData will be notified only after data is changed.

### How to use

```kotlin

class SomeViewModel : ViewModel() {

    private val _data = EventLiveData<String>()
    val data: LiveData<String>
    get()  = _data

    fun setData(string: String){
        _data.postValue(string)
    }

}

someViewModel.setData("someData")

//This Observer will recieve data after data changes, not "cached" data.
someViewModel.data.observe(viewLifecycleOwner, ConsumableObserver { data ->
            //TODO
        })


```
# Mobble:MV

[ ![Download](https://api.bintray.com/packages/antonsarmatin/Mobble/mv/images/download.svg) ](https://bintray.com/antonsarmatin/Mobble/mv/_latestVersion)

MV extends MVVM presentation architecture with state and actions, also it deliver base platform classes: Application, Activity, Fragment, ViewModel and etc.

- MobbleApplication
- MobbleActivity
- MobbleFragment
- MobbleViewModel
- ...

### Add to your project

To start using this part, add these lines to the build.gradle of your project:

```xml
repositories {
    jcenter()
}

dependencies {
    implementation 'ru.sarmatin.mobble:mv:latest_version'
}
```

## MobbleFragment & MobbleViewModel

Base **Fragment** class that holds error handling and loading processing with MobbleViewModel.
You can override failure and loading observers to implement custom behaviour

Base **ViewModel** class that holds error and loading state fields. This fields can be observed by Fragment that extends MobbleFragment, or it may be observe by any custom Fragment class.

### How to use
See example application

#### Handle Loading state
There are few tools for easy loading state handle.

State is represented by sealed class named **Loading**

    sealed class Loading {

        object NoLoading : Loading(), Serializable

        abstract class Fullscreen : Loading(), Serializable

    }

At this time, there is only one loading state called Fullscreen.


**MobbleViewModel** has own default loading state
This loading state is used by default for all loading events.
```kotlin
private val _loading = handle.getLiveData<Loading>("loading")
val loading: LiveData<Loading>
        get() = _loading

protected open val defaultLoading: Loading.Fullscreen = DefaultFullscreen()

class DefaultFullscreen : Loading.Fullscreen()
```

You can set loading state by using `handleLoading(state: Boolean)` function. This will post `defaultLoading` state to loading LiveData field.
Or you can use `handleLoading(loading: Loading)` to more precise control of loading states.

**MobbleFragment** observes loading state of **MobbleViewModel** and handle it with implementation of  AbstractLoadingDialog.

**Custom loading states:**
Implement own Loading.Fullscreen.

You can override `defaultLoading` with your own loading state. Or post your state via `handleLoading(loading: Loading)`

```kotlin
class SomeScreenViewModel(handle: SavedStateHandle) : MobbleViewModel(handle) {

	fun fetchData() {
        viewModelScope.launch {
            handleLoading(CustomLoadingFullscreen())
            //Simulate network fetch
            withContext(Dispatchers.IO) {
                delay(3000)
            }
            handleLoading(false)
        }
    }

    class CustomLoadingFullscreen : Loading.Fullscreen()
}
```

In this case you should override `MobbleFragment.handleCustomLoading` function in your associated fragment.

```kotlin
override fun handleCustomLoading(loading: Loading): AbstractLoadingDialog {
        return when (loading) {
            is SomeScreenViewModel.CustomLoadingFullscreen -> CustomLoadingDialog.newInstance()
            else -> {
                super.handleCustomLoading(loading)
            }
        }
}
```

This function return an instance of AbstractLoadingDialog child.

More info -> see source code and example app

# Mobble:Nav
Under construction

# Mobble:Clean
Under construction

# Changelog
## 1.0.3



# License
  [MIT](https://github.com/antonsarmatin/Mobble/blob/master/LICENSE)
