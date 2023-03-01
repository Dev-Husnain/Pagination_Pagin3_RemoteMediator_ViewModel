package com.pagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.pagination.models.Results
import com.pagination.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class QuoteViewModel @Inject constructor(quoteRepository: QuoteRepository) : ViewModel() {
    val list = quoteRepository.getQuotes().cachedIn(viewModelScope)
}