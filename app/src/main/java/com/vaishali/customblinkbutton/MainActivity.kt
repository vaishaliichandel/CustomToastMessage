package com.vaishali.customblinkbutton

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vaishali.customblinkbutton.databinding.ActivityMainBinding
import com.vaishali.customtoastmessage.Snacking


class MainActivity : AppCompatActivity() {

    private var titles = arrayListOf<String>()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = AdapterRecyclerView(callback())
        binding.rvSnackBarItems.adapter = adapter
        titles = arrayListOf(
            "Basic SnackBar",
            "SnackBar With Elevation",
            "SnackBar With Icon with custom tint",
            "SnackBar With Action",
            "SnackBar With close icon",
            "SnackBar with Corner Radius",
            "SnackBar with Corner Radius (Custom)",
            "SnackBar With Border",
            "Background Color",
            "SnackBar with Text Color",
            "SnackBar with Text Style",
            "SnackBar with Custom Font Family",
            "SnackBar with Top Position",
            "SnackBar with Message Max Lines",
            "SnackBar with INDEFINITE Duration",
            "SnackBar with Without Margin",
        )
        adapter.setList(titles)
    }

    private fun callback(): AdapterRecyclerView.Callback {
        return object : AdapterRecyclerView.Callback {
            override fun onItemClick(message: String?) {
                when (message) {
                    titles[0] -> {
                        Snacking.Builder(binding.root, "Hello! this is basic message").build()
                            .show()

                    }

                    titles[1] -> {
                        Snacking.Builder(binding.root, "Hello! this is basic message")
                            .elevation(5F)
                            .build()
                            .show()

                    }

                    titles[2] -> {
                        Snacking.Builder(binding.root, "This message with icon")
                            .icon(R.drawable.ic_info, R.color.teal_200)
                            .build()
                            .show()
                    }

                    titles[3] -> {
                        Snacking.Builder(binding.root, "Click to dismiss message")
                            .action("Dismiss", R.color.teal_200, object : Snacking.Callback {
                                override fun onActionClick(snackBar: Snacking?) {
                                    snackBar?.dismiss()
                                    showToast()
                                }

                            })
                            .build()
                            .show()
                    }

                    titles[4] -> {
                        Snacking.Builder(binding.root, "Click to dismiss message")
                            .actionClose(R.drawable.ic_close,
                                R.color.teal_200,
                                object : Snacking.Callback {
                                    override fun onActionClick(snackBar: Snacking?) {
                                        snackBar?.dismiss()
                                        showToast()
                                    }

                                })
                            .build()
                            .show()
                    }

                    titles[5] -> {
                        Snacking.Builder(binding.root, "This message with corner")
                            .cornerRadius(30F)
                            .build()
                            .show()
                    }

                    titles[6] -> {
                        Snacking.Builder(binding.root, "This message with custom corner")
                            .cornerRadius(
                                15f,
                                0f,
                                0f,
                                15f
                            )
                            .build()
                            .show()
                    }

                    titles[7] -> {
                        Snacking.Builder(binding.root, "This message with border")
                            .border(2F, R.color.colorPrimary)
                            .cornerRadius(10F)
                            .build()
                            .show()
                    }

                    titles[8] -> {
                        Snacking.Builder(binding.root, "This is custom background color")
                            .backgroundColor(R.color.purple_200)
                            .build()
                            .show()
                    }

                    titles[9] -> {
                        Snacking.Builder(binding.root, "This is custom text color")
                            .textColor(R.color.teal_200)
                            .build()
                            .show()
                    }

                    titles[10] -> {
                        Snacking.Builder(binding.root, "This is bold italic text")
                            .fontFamily(R.font.montserrat)
                            .textStyle(Snacking.BOLD_ITALIC)
                            .build()
                            .show()
                    }

                    titles[11] -> {
                        Snacking.Builder(binding.root, "This is custom font family")
                            .fontFamily(R.font.montserrat)
                            .build()
                            .show()
                    }

                    titles[12] -> {
                        Snacking.Builder(binding.root, "This message is on top position")
                            .icon(R.drawable.ic_info)
                            .position(Snacking.TOP)
                            .cornerRadius(25F)
                            .border(1F)
                            .action("Cancel", object : Snacking.Callback {
                                override fun onActionClick(snackBar: Snacking?) {
                                    showToast()
                                }
                            })
                            .build()
                            .show()
                    }

                    titles[13] -> {
                        Snacking.Builder(
                            binding.root,
                            "This is long message, this is long message, this is long message, this is long message, this is long message, this is long message"
                        )
                            .action("Long Button Text", object : Snacking.Callback {
                                override fun onActionClick(snackBar: Snacking?) {
                                    showToast()

                                }
                            })
                            .messageMaxLines(2)
                            .build()
                            .show()
                    }

                    titles[14] -> {
                        Snacking.Builder(binding.root, "This is a message with INDEFINITE duration")
                            .duration(Snacking.INDEFINITE)
                            .build()
                            .show()
                    }

                    titles[15] -> {
                        Snacking.Builder(binding.root, "This is a message")
                            .removeMargin()
                            .build()
                            .show()
                    }
                }
            }
        }
    }

    private fun showToast() {
        Toast.makeText(this, "Action Click", Toast.LENGTH_SHORT).show()
    }
}