
package com.santanas.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(Color.BLACK)
            setPadding(32, 32, 32, 32)
        }

        val logo = TextView(this).apply {
            text = "S"
            textSize = 76f
            setTextColor(Color.GREEN)
            gravity = Gravity.CENTER
        }

        val title = TextView(this).apply {
            text = "SANTANA'S"
            textSize = 30f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "Conectando vidas."
            textSize = 16f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }

        layout.addView(logo)
        layout.addView(title)
        layout.addView(subtitle)

        setContentView(layout)
    }
}
