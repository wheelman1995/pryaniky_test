package ru.wheelman.pryaniky_test.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.wheelman.pryaniky_test.model.entities.Hz
import ru.wheelman.pryaniky_test.model.entities.Picture
import ru.wheelman.pryaniky_test.model.entities.Selector

interface MainView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class) fun showError(message: String)
    @StateStrategyType(AddToEndSingleStrategy::class) fun showLoading(show: Boolean)
    @StateStrategyType(AddToEndStrategy::class) fun addHz(hz: Hz)
    @StateStrategyType(AddToEndStrategy::class) fun addPicture(picture: Picture)
    @StateStrategyType(AddToEndStrategy::class) fun addSelector(selector: Selector)

}