package com.ahmedapps.watchy.main.presentation.main

sealed class MainUiEvents {
    data class Refresh(val route: String) : MainUiEvents()
    data class OnPaginate(val route: String) : MainUiEvents()
    object LoadAll: MainUiEvents()
}
