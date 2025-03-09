package com.vaishali.customblinkbutton

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaishali.customblinkbutton.helper.AdapterRecyclerView
import com.vaishali.customblinkbutton.helper.MainActivityHelper
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class MainActivity : AppCompatActivity() {

    private var titles: Array<String>? = null
    private var helper: MainActivityHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val parentView: View = findViewById(R.id.parentView)
        helper = MainActivityHelper(parentView)

        val recyclerView = findViewById<RecyclerView>(R.id.act_main_recyclerView)
        val adapter = AdapterRecyclerView(callback())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        titles = arrayOf(
            "Basic",
            "With Elevation",
            "With Icon with custom tint",
            "With Action",
            "With close icon",
            "Corner Radius",
            "Corner Radius (Custom)",
            "With Border",
            "Background Color",
            "Text Color",
            "Text Style",
            "Custom Font Family",
            "Top Position",
            "Message Max Lines",
            "Without Margin",
        )
        val list: MutableList<String> = ArrayList()
        titles?.let {
            list.addAll(it)
            adapter.setList(list)
        }
    }

    private fun callback(): AdapterRecyclerView.Callback {
        return object : AdapterRecyclerView.Callback {
            override fun onItemClick(message: String?) {
                when (message) {
                    titles?.get(0) -> { helper?.snackBarBasic() }
                    titles?.get(1) -> { helper?.snackBarWithElevation() }
                    titles?.get(2) -> { helper?.snackBarIcon() }
                    titles?.get(3) -> { helper?.snackBarAction() }
                    titles?.get(4) -> { helper?.snackBarCloseAction() }
                    titles?.get(5) -> { helper?.snackBarCorner() }
                    titles?.get(6) -> { helper?.snackBarCornerCustom() }
                    titles?.get(7) -> { helper?.snackBarBorder() }
                    titles?.get(8) -> { helper?.snackBarBackground() }
                    titles?.get(9) -> { helper?.snackBarTextColor() }
                    titles?.get(10) -> { helper?.snackBarBold() }
                    titles?.get(11) -> { helper?.snackBarFont() }
                    titles?.get(12) -> { helper?.snackBarPosition() }
                    titles?.get(13) -> { helper?.snackBarMaxLines() }
                    titles?.get(14) -> { helper?.snackWithoutMargin() }
                }
            }
        }
    }

}