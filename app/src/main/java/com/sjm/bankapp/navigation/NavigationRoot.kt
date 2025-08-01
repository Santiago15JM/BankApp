package com.sjm.bankapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.sjm.bankapp.screens.details.PostPaymentScreen
import com.sjm.bankapp.screens.details.TransactionDetails
import com.sjm.bankapp.screens.history.History
import com.sjm.bankapp.screens.home.Home
import com.sjm.bankapp.screens.login.Login
import com.sjm.bankapp.screens.pay_bill.PayBill
import com.sjm.bankapp.screens.pay_bill.PostBillScreen
import com.sjm.bankapp.screens.send_cash.SendCash
import com.sjm.bankapp.screens.settings.Settings
import com.sjm.bankapp.screens.settings.saved_accounts.ManageSavedAccounts

@Composable
fun NavigationRoot() {
    val navStack: NavBackStack = rememberNavBackStack(LoginKey)

    val navigateTo: (NavType) -> Unit = { navStack.add(it) }
    val navigateBack: () -> Unit = { navStack.removeLastOrNull() }
    val returnHome: () -> Unit = { navStack.returnTo(HomeKey) }
    val returnToLogin: () -> Unit = { navStack.returnTo(LoginKey) }

    NavDisplay(
        backStack = navStack, entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator(),
        ), transitionSpec = {
            fadeIn(animationSpec = tween(400)) togetherWith fadeOut(animationSpec = tween(300))
        }, popTransitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        }, entryProvider = entryProvider {
            entry<LoginKey> {
                Login(navigateTo)
            }

            entry<HomeKey> {
                Home(navigateTo, navigateBack)
            }

            entry<TransactionHistoryKey> {
                History(navigateTo, navigateBack)
            }

            entry<TransactionDetailsKey> {
                TransactionDetails(it.transaction, it.entry, navigateBack)
            }

            entry<SendMoneyKey> {
                SendCash(navigateTo, navigateBack)
            }

            entry<PostPaymentKey> {
                PostPaymentScreen(it.transaction, returnHome)
            }

            entry<PayBillKey> {
                PayBill(navigateTo, navigateBack)
            }

            entry<PostBillKey> {
                PostBillScreen(it.bill, it.transaction, it.businessName, returnHome)
            }

            entry<SettingsKey> {
                Settings(navigateTo, navigateBack, returnToLogin)
            }

            entry<ManageContactsKey> {
                ManageSavedAccounts(navigateBack)
            }
        })
}

fun NavBackStack.returnTo(key: NavType) {
    retainAll(dropLastWhile { it::class != key::class })
}
