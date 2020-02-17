# Mobble
Mobble framework - CleanArch, Navigation detached form UI, MVVM like Presentation Layer Arch and other small solutions/utils for comfortable life in android dev with Clean and MVVM. 


## Mobble framework has the following libraries:

1. Mobble:Utils
2. Mobble:MV
3. Mobble:Nav
4. Mobble:Clean

## Plans for the future

This framework is under construction!

- [x] Create
- [x] Mobble:Utils - Consumable LiveData
- [ ] ...
- [ ] Mobble:Utils - Small but useful utils
- [ ] Mobble:MV - MVVM like UI layer pattern with Events and States(based on Android MVVM)
- [ ] Mobble:Nav - Detached navigation based on MV State
- [ ] Mobble:Clean - CleanArchitecture library for fast implementation in new project

# Mobble:Utils

## ConsumableLiveData

LiveData class that wrapping ConsumableValue and uses ConsumableObserver. 
Allows build consumable data with LiveData class. 
Example: use it in SharedViewModels

### Add to your project

To start using this library, add these lines to the build.gradle of your project:

```xml
repositories {
    jcenter()
}

dependencies {
    implementation 'ru.sarmatin.mobble:consumable-livedata:1.0.1'
}
```

### How to use

```kotlin

class SharedViewModel : ViewModel() {


    private val _data = ConsumableLiveData<String>()
    val data: LiveData<ConsumableValue<String>>
    get()  = _data


    fun setData(string: String){
        _data.postValue(string)
    }

}

sharedViewModel.setData("someData")

sharedViewModel.data.observe(viewLifecycleOwner, ConsumableObserver { data ->
            //TODO
        })


```

# License
  [MIT](https://github.com/antonsarmatin/Mobble/blob/master/LICENSE)
