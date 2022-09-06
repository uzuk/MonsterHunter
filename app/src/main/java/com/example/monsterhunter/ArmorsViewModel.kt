package com.example.monsterhunter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


enum class ApiStatus { LOADING, ERROR, DONE }
class ArmorsViewModel : ViewModel() {

    //API status
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    //armors livedata
    private var _armors = MutableLiveData<List<Armor>>()
    val armors: LiveData<List<Armor>> = _armors

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getArmors()
    }

    fun getArmors() {
        _status.value = ApiStatus.LOADING
        coroutineScope.launch {
            val getProductsDeferred = ArmorsApi.retrofitService.getArmorAsync()

            try {
                // this will run on a thread managed by Retrofit
                val armorsFullData = getProductsDeferred.await()
                _armors.value = armorsFullData
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }


    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}