package ru.sarmatin.mobble.mv.navigation

import androidx.annotation.MainThread
import androidx.collection.ArraySet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by antonsarmatin
 * Date: 17/05/2020
 * Project: Mobble
 */
open class NavigationEvent : LiveData<NavAction> {

    constructor() : super()

    constructor(value: NavAction) : super(value)

    protected val observers = ArraySet<EventPendingObserver<in NavAction>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in NavAction>) {
        val wrapper = EventPendingObserver(observer)
        observers.add(wrapper)
        super.observe(owner, wrapper)
    }

    @MainThread
    override fun observeForever(observer: Observer<in NavAction>) {
        val pendingObserver = EventPendingObserver(observer)
        observers.add(pendingObserver)
        super.observeForever(pendingObserver)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in NavAction>) {
        if (observers.remove(observer)) {
            super.removeObserver(observer)
            return
        }
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val pendingObserver = iterator.next()
            if (pendingObserver.observer == observer) {
                iterator.remove()
                super.removeObserver(pendingObserver)
                break
            }
        }
    }

    protected class EventPendingObserver<T>(val observer: Observer<T>) : Observer<T> {

        private var pending = false

        override fun onChanged(t: T?) {
            if (pending) {
                pending = false
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending = true
        }

    }


}