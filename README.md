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

There is two types of MVVM implementation:
- Common (Vanilla MVVM)
- Stated (MVVM with State and Action)

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
class MobbleAbstractViewModel: ViewModel(){
    protected open val defaultLoading: Loading.Fullscreen = DefaultFullscreen()
}

class DefaultFullscreen : Loading.Fullscreen()

class MobbleViewModel: MobbleAbstractViewModel(){
    private val _loading = handle.getLiveData<Loading>("loading")
    val loading: LiveData<Loading>
            get() = _loading
}

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

In this case you should override `MobbleAbstractFragment.handleCustomLoading` function in your associated fragment.

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

### Handle Failure state

Override `MobbleFragment.failureObserver` in your fragment to implement Failure handling (you can implement default handling in base fragment)

## MobbleStateFragment & MobbleStateViewModel
Implementation of MVVM pattern with some improvements:
- State
- Action
- State based Navigation (soon... in Mobble:Nav)

Base **ViewModel** class that contains MobbleState with common fields (Loading, Failure), tools to manage common state fields and features to implement your custom state by extending MobbleState.
This state is used to notify Fragment about which VM's fields should be observed. **IMPORTANT! WARNING! State should not contain bulk data!**

Base **Fragment** class that handles MobbleState processing and tools to handle your custom state.

### How to use

See example application

#### MobbleStateViewModel

**MobbleState** parent class for custom States, contains common state fields: Loading, Failure
**MobbleStateViewModel** contains **MobbleState** child

```kotlin

abstract class MobbleStateViewModel<S : MobbleStateViewModel.MobbleState>(handle: SavedStateHandle) :
    MobbleAbstractViewModel() {
    
    protected val _viewState = handle.getLiveData<S>("viewState")
    val viewState: LiveData<S>
        get() = _viewState
        
    //... 
    
    abstract class MobbleState(
    ) : Serializable {

        internal var _loading: Loading? = null
        val loading: Loading?
            get() = _loading

        internal var _failure: Failure? = null
        val failure: Failure?
            get() = _failure


    }
    
    //...
   
    
}

```

Extends from **MobbleStateViewModel** and implement your custom state:
**IMPORTANT! WARNING! State should not contain bulk data!**

```kotlin

class SomeStateViewModel(private val handle: SavedStateHandle) :
    MobbleStateViewModel<SomeStateViewModel.ViewState>(handle) {

    //LiveData fields here

    override val defaultState: ViewState
        get() = ViewState(ViewState.ColorType.COLOR_RED)

    //Here your custom state with some fields
    //IMPORTANT! WARNING! State should not contain bulk data!
    data class ViewState(
        val color: ColorType
    ) : MobbleState() {

        enum class ColorType {
            COLOR_RED,
            COLOR_GREEN,
            COLOR_BLUE
        }

    }


    fun setColorType(type: ViewState.ColorType) {
        updateState(_viewState.value?.copy(color = type))
    }


}

```

#### MobbleStateFragment

```kotlin
abstract class MobbleStateFragment<S : MobbleStateViewModel.MobbleState> :
    MobbleAbstractFragment()
    
    abstract val viewModel: MobbleStateViewModel<S>
    
    //...
    
}
```


##### Handle Loading state
Similar to **MobbleViewModel**

Implement your own Loading class and put as parameter to loading field of VM's State
```kotlin
class SomeStateViewModel(private val handle: SavedStateHandle) :
    MobbleStateViewModel<SomeStateViewModel.ViewState>(handle) {

    //...
    
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

Override `MobbleAbstractFragment.handleCustomLoading` function in your associated fragment
```kotlin
override fun handleCustomLoading(loading: Loading): AbstractLoadingDialog {
        return when (loading) {
            is SomeStateViewModel.CustomLoadingFullscreen -> CustomLoadingDialog.newInstance()
            else -> {
                super.handleCustomLoading(loading)
            }
        }
}
```

This function return an instance of AbstractLoadingDialog child.


##### Handle Failure state

Implement `MobbleStateFragment.handleFailure` in your fragment (you can implement default handling in base fragment)



More info -> see source code and example app

# Mobble:Nav
Under construction

# Mobble:Clean
Under construction

# Changelog
## 1.0.3
- MV Common: Loading handling feature
## 1.0.4
- MV Stated: ViewModel and Fragment



# License
  [MIT](https://github.com/antonsarmatin/Mobble/blob/master/LICENSE)
