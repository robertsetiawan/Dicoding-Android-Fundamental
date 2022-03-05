package com.robertas.ugithub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertas.ugithub.interfaces.IRepository
import com.robertas.ugithub.utils.mappers.UserMapper
import com.robertas.ugithub.models.domain.User
import com.robertas.ugithub.models.network.enums.NetworkResult
import com.robertas.ugithub.repositories.UserRepository
import com.robertas.ugithub.services.Network
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private var userRepository: IRepository<User> = UserRepository(Network.apiService, UserMapper())

    private val _requestGetUserList = MutableLiveData<NetworkResult<List<User>>>()

    val requestGetUserList: LiveData<NetworkResult<List<User>>> = _requestGetUserList

    private val _requestGetDetailUser = MutableLiveData<NetworkResult<User>>()

    val requestGetDetailUser: LiveData<NetworkResult<User>> = _requestGetDetailUser

    private val _requestGetFollowingList = MutableLiveData<NetworkResult<List<User>>>()

    val requestGetFollowingList: LiveData<NetworkResult<List<User>>> = _requestGetFollowingList

    private val _requestGetFollowerList = MutableLiveData<NetworkResult<List<User>>>()

    val requestGetFollowerList: LiveData<NetworkResult<List<User>>> = _requestGetFollowerList

    private val _querySearch = MutableLiveData<String?>()

    val querySearch: LiveData<String?> = _querySearch

    fun setQuerySearch(newQuery: String?) {
        _querySearch.value = newQuery
    }

    fun doneSearching() {
        setQuerySearch(null)
    }

    fun doneNavigatingToDetailFragment() {
        _requestGetDetailUser.value = NetworkResult.Loading()
    }

    fun doneLoadingFollowingList() {
        _requestGetFollowingList.value = NetworkResult.Loading()
    }

    fun doneLoadingFollowerList() {
        _requestGetFollowerList.value = NetworkResult.Loading()
    }

    fun getFilteredUserList(key: String) {
        _requestGetUserList.value = NetworkResult.Loading()

        viewModelScope.launch {

            try {
                val items = userRepository.getFilteredUser(key)

                _requestGetUserList.value = NetworkResult.Loaded(items)

            } catch (e: Exception) {
                _requestGetUserList.value = NetworkResult.Error(arrayListOf(), e.message)
            }
        }
    }

    fun getDetailUser(username: String) {
        _requestGetDetailUser.value = NetworkResult.Loading()

        viewModelScope.launch {
            try {
                val detailUser = userRepository.getDetailUser(username)

                _requestGetDetailUser.value = NetworkResult.Loaded(detailUser)
            } catch (e: Exception) {
                _requestGetDetailUser.value = NetworkResult.Error(User(), e.message)
            }
        }
    }

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

    fun getFollowerList(username: String) {
        _requestGetFollowerList.value = NetworkResult.Loading()

        viewModelScope.launch {
            try {
                val followerList = userRepository.getFollowerList(username)

                _requestGetFollowerList.value = NetworkResult.Loaded(followerList)
            } catch (e: Exception) {
                _requestGetFollowerList.value = NetworkResult.Error(arrayListOf(), e.message)
            }
        }
    }

    init {
        getFilteredUserList(DEFAULT_KEYWORD)
    }

    companion object {
        const val DEFAULT_KEYWORD = "agung"
    }
}