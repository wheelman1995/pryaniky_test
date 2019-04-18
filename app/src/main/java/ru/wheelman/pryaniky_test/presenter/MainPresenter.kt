package ru.wheelman.pryaniky_test.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.wheelman.pryaniky_test.model.entities.DataType.*
import ru.wheelman.pryaniky_test.model.entities.Result
import ru.wheelman.pryaniky_test.model.entities.Result.Success
import ru.wheelman.pryaniky_test.model.network.IRemoteDataSource
import ru.wheelman.pryaniky_test.view.MainView
import kotlin.coroutines.CoroutineContext

@InjectViewState
class MainPresenter : MvpPresenter<MainView>(), CoroutineScope, KoinComponent {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()
    private val remoteDataSource: IRemoteDataSource by inject()

    init {
        launch {
            when (val result = loadData { remoteDataSource.getData() }) {
                is Success -> {
                    result.view.forEach {
                        when (it) {
                            HZ -> viewState.addHz(result.data.hz)
                            PICTURE -> viewState.addPicture(result.data.picture)
                            SELECTOR -> viewState.addSelector(result.data.selector)
                        }
                    }
                }
                is Result.Failure -> viewState.showError(result.error)
            }
        }
    }

    private suspend fun <T> loadData(block: suspend () -> T): T {
        viewState.showLoading(true)
        val result = block()
        viewState.showLoading(false)
        return result
    }

    override fun onDestroy() {
        coroutineContext.cancel()
    }
}