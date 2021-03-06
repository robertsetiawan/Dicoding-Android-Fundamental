package com.robertas.ugithub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertas.ugithub.abstractions.IRepository
import com.robertas.ugithub.models.domain.UserDomain
import com.robertas.ugithub.models.network.enums.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FollowingListViewModel @Inject constructor(
    private val userRepository: IRepository<UserDomain>
) : ViewModel() {

    private val _requestGetFollowingList = MutableLiveData<NetworkResult<List<UserDomain>>>()

    val requestGetFollowingList: LiveData<NetworkResult<List<UserDomain>>> =
        _requestGetFollowingList

    fun getFollowingList(username: String) {
        _requestGetFollowingList.value = NetworkResult.Loading()

        viewModelScope.launch {
            try {
                val followingList = userRepository.getFollowingList(username)

                _requestGetFollowingList.value = NetworkResult.Loaded(followingList)

            } catch (e: Exception) {
                _requestGetFollowingList.value = NetworkResult.Error(arrayListOf(), e.message)
            }
        }
    }
}