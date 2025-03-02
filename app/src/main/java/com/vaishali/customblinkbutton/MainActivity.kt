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
        val fab = findViewById<ExtendedFloatingActionButton>(R.id.fab)
        helper = MainActivityHelper(parentView, fab)

        val recyclerView = findViewById<RecyclerView>(R.id.act_main_recyclerView)
        val adapter = AdapterRecyclerView(callback())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        titles = arrayOf(
            "Basic",
            "With Elevation",
            "With Icon",
            "With Action",
            "With Margin",
            "Corner Radius",
            "Corner Radius (Custom)",
            "With Border",
            "Background Color",
            "Background Image",
            "Text Color",
            "Text Style",
            "Custom Font Family",
            "Anchor View",
            "Top Position",
            "With State",
            "Message Max Lines",
            "Landscape Screen"
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
                    titles?.get(4) -> { helper?.snackBarMargin() }
                    titles?.get(5) -> { helper?.snackBarCorner() }
                    titles?.get(6) -> { helper?.snackBarCornerCustom() }
                    titles?.get(7) -> { helper?.snackBarBorder() }
                    titles?.get(8) -> { helper?.snackBarBackground() }
                    titles?.get(9) -> { helper?.snackBarBackground() }
                    titles?.get(0) -> { helper?.snackBarTextColor() }
                    titles?.get(11) -> { helper?.snackBarBold() }
                    titles?.get(12) -> { helper?.snackBarFont() }
                    titles?.get(13) -> { helper?.snackBarAnchor() }
                    titles?.get(14) -> { helper?.snackBarPosition() }
                    titles?.get(15) -> { helper?.snackBarState() }
                    titles?.get(16) -> { helper?.snackBarMaxLines() }
                    titles?.get(17) -> { helper?.snackBarLandscape() }
                }
            }
        }
    }

}