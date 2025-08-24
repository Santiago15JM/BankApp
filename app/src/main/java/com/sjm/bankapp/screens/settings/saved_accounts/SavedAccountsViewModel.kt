package com.sjm.bankapp.screens.settings.saved_accounts

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjm.bankapp.logic.Session
import com.sjm.bankapp.logic.models.SavedAccount
import kotlinx.coroutines.launch

class SavedAccountsViewModel: ViewModel() {
    val savedAccounts: MutableList<SavedAccount> = mutableStateListOf()
    var selectedAccount: SavedAccount? = null
    private var dao = Session.getSavedAccountsDao()

    init {
        viewModelScope.launch {
            dao.getAll().collect {
                savedAccounts.clear()
                savedAccounts.addAll(it)
            }
        }
    }

    fun addAccount(new: SavedAccount) {
        viewModelScope.launch {
            dao.upsert(new)
        }
    }

    fun editAccount(newDescription: String, newAccountID: Long) {
        selectedAccount?.description = newDescription
        selectedAccount?.aId = newAccountID
        viewModelScope.launch {
            dao.upsert(selectedAccount!!)
        }
    }

    fun removeAccount() {
        viewModelScope.launch {
            dao.delete(selectedAccount!!)
        }
    }
}
