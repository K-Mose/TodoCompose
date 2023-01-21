package com.mose.kim.todocompose.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mose.kim.todocompose.data.model.ToDoTask
import com.mose.kim.todocompose.repository.TodoRepository
import com.mose.kim.todocompose.util.RequestState
import com.mose.kim.todocompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // viewModel 의존성 주입하기 위해
class SharedViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    // 초기값이 emptyList가 들어가기 때문에 초기 화면에 EmptyContent가 출력됨. Sealed class로 변경
    private val _allTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    fun getAllTasks() {
        // 함수 실행 시 로딩 상태로 변경
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    // 디비에서 값 가져오기 성공 시 Success로 데이터 리스트 넘김
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

}