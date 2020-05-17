package ru.sarmatin.mobble.utils.liveevent

import androidx.annotation.MainThread
import androidx.collection.ArraySet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Created by antonsarmatin
 * Date: 2020-02-20
 * Project: Mobble
 */
open class EventLiveData<T> : MutableLiveData<T>() {

    private val observers = ArraySet<EventPendingObserver<in T>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = EventPendingObserver(observer)
        observers.add(wrapper)
        super.observe(owner, wrapper)
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        val pendingObserver = EventPendingObserver(observer)
        observers.add(pendingObserver)
        super.observeForever(pendingObserver)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
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

    @MainThread
    override fun setValue(t: T?) {
        observers.forEach { it.newValue() }
        super.setValue(t)
    }

    private class EventPendingObserver<T> (val observer: Observer<T>) : Observer<T> {

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
