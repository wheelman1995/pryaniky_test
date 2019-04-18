package ru.wheelman.pryaniky_test.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.arellomobile.mvp.MvpDelegate
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import kotlinx.android.synthetic.main.activity_main.*
import ru.wheelman.pryaniky_test.R
import ru.wheelman.pryaniky_test.model.entities.Hz
import ru.wheelman.pryaniky_test.model.entities.Picture
import ru.wheelman.pryaniky_test.model.entities.Selector
import ru.wheelman.pryaniky_test.presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainView {

    private val mMvpDelegate: MvpDelegate<out MainActivity> by lazy { MvpDelegate(this) }
    private val loadingStatus by lazy {
        Snackbar.make(
            llc_container,
            getString(R.string.data_is_loading),
            LENGTH_INDEFINITE
        )
    }
    @InjectPresenter internal lateinit var mainPresenter: MainPresenter

    override fun showError(message: String) = showSnackbar(message)

    override fun showLoading(show: Boolean) =
        if (show) loadingStatus.show() else loadingStatus.dismiss()

    override fun addHz(hz: Hz) {
        layoutInflater.inflate(R.layout.hz, llc_container, true).runOnLinearLayoutLastChild {
            this as AppCompatTextView
            text = hz.data.text
            setOnClickListener { showSnackbar(hz.name) }
        }
    }

    override fun addPicture(picture: Picture) {
        layoutInflater.inflate(R.layout.picture, llc_container, true).runOnLinearLayoutLastChild {
            this as AppCompatImageView
            Glide.with(this)
                .load(picture.data.url)
                .into(this)
            setOnClickListener { showSnackbar(picture.data.text) }
        }
    }

    override fun addSelector(selector: Selector) {
        layoutInflater.inflate(R.layout.selector, llc_container, true).runOnLinearLayoutLastChild {
            this as AppCompatSpinner
            adapter = ArrayAdapter<String>(
                this@MainActivity,
                R.layout.item_spinner,
                selector.data.variants.map { it.text }
            )
            setSelection(selector.data.selectedId, true)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    showSnackbar(
                        getString(
                            R.string.selector_item_click_message,
                            selector.data.variants[position].id,
                            selector.name
                        )
                    )
                }
            }
        }
    }

    private fun View.runOnLinearLayoutLastChild(block: View.() -> Unit) {
        this as LinearLayoutCompat
        with(getChildAt(childCount - 1)) { block() }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            llc_container,
            message,
            LENGTH_LONG
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMvpDelegate.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        mMvpDelegate.onAttach()
    }

    override fun onResume() {
        super.onResume()
        mMvpDelegate.onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMvpDelegate.onSaveInstanceState(outState)
        mMvpDelegate.onDetach()
    }

    override fun onStop() {
        super.onStop()
        mMvpDelegate.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpDelegate.onDestroyView()
        if (isFinishing) {
            mMvpDelegate.onDestroy()
        }
    }
}
