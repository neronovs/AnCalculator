package ru.narod.nod.ancalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import ru.narod.nod.ancalculator.databinding.ActivityMainBinding
import ru.narod.nod.ancalculator.privacy_policy.PrivacyPolicyFragment.Companion.newInstance

class MainActivity : MvpActivity<MainView?, MainPresenter>(), MainView, View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClicker()
        presenter.showResult("0")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return if (item.itemId == R.id.menuPrivacyPolicy) {
            showPrivacyPolicy()
            true
        } else if (item.itemId == R.id.menuClose) {
            closePrivacyPolicy()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun closePrivacyPolicy() {
        supportFragmentManager.popBackStackImmediate()
    }

    private fun showPrivacyPolicy() {
        val fragment = newInstance()

        // Begin the transaction
        val ft = supportFragmentManager.beginTransaction()
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.main_container, fragment)
        // Add to bakstack
        ft.addToBackStack(fragment.tag)
        // Complete the changes added above
        ft.commit()
        fragment.setMenu(menu!!)
    }

    override fun createPresenter(): MainPresenter {
        return MainPresenterImpl(this)
    }

    override fun showTable(view: String, text: String) {
        when (view) {
            "mainView_table" -> binding.mainViewTable.text = text
            "mainView_action" -> binding.mainViewAction.text = text
            "mainView_firstNumber" -> binding.mainViewFirstNumber.text = text
            "mainView_secondNumber" -> binding.mainViewSecondNumber.text = text
            "mainView_memo" -> {
                if (text == "visible") binding.mainViewMemo.visibility =
                    View.VISIBLE else binding.mainViewMemo.visibility = View.INVISIBLE
            }
        }
    }

    override fun onClick(view: View) {
        //Pressing animation
        runOnUiThread {
            YoYo.with(Techniques.Pulse)
                .duration(300)
                .repeat(0)
                .playOn(view)
        }
        presenter.buttonPressed(view.id)
    }

    private fun setOnClicker() = binding.run {
        buttonC.setOnClickListener(this@MainActivity)
        buttonMPlus.setOnClickListener(this@MainActivity)
        buttonMMinus.setOnClickListener(this@MainActivity)
        buttonMR.setOnClickListener(this@MainActivity)
        buttonMC.setOnClickListener(this@MainActivity)
        button0.setOnClickListener(this@MainActivity)
        button1.setOnClickListener(this@MainActivity)
        button2.setOnClickListener(this@MainActivity)
        button3.setOnClickListener(this@MainActivity)
        button4.setOnClickListener(this@MainActivity)
        button5.setOnClickListener(this@MainActivity)
        button6.setOnClickListener(this@MainActivity)
        button7.setOnClickListener(this@MainActivity)
        button8.setOnClickListener(this@MainActivity)
        button9.setOnClickListener(this@MainActivity)
        buttonDivide.setOnClickListener(this@MainActivity)
        buttonMultiply.setOnClickListener(this@MainActivity)
        buttonMinus.setOnClickListener(this@MainActivity)
        buttonPlus.setOnClickListener(this@MainActivity)
        buttonDot.setOnClickListener(this@MainActivity)
        buttonEqual.setOnClickListener(this@MainActivity)
        buttonBack.setOnClickListener(this@MainActivity)
        buttonReverse.setOnClickListener(this@MainActivity)
        buttonPercent.setOnClickListener(this@MainActivity)
        buttonSQRT.setOnClickListener(this@MainActivity)
        buttonPow.setOnClickListener(this@MainActivity)
    }
}
