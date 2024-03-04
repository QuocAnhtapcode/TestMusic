package club.mobile.d21.testmusic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import club.mobile.d21.testmusic.data.ApiService
import club.mobile.d21.testmusic.data.MyData
import club.mobile.d21.testmusic.data.RetrofitHelper
import kotlinx.coroutines.launch

class MainViewModel(application: Application):AndroidViewModel(application) {
    private val retrofitBuilder = RetrofitHelper.getInstance().create(ApiService::class.java)
    private val _data = MutableLiveData<MyData>()
    internal fun search(keyword: String){
        viewModelScope.launch {
            val retrofitData = retrofitBuilder.getData(keyword)
            _data.value = retrofitData
        }
    }
    val data: LiveData<MyData> = _data
}