
package com.santanas.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private val green = Color.rgb(35, 190, 105)
    private val black = Color.rgb(15, 15, 15)
    private val white = Color.WHITE

    private lateinit var content: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showHome()
    }

    private fun showHome() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(black)
            setPadding(28, 35, 28, 20)
        }

        val logo = TextView(this).apply {
            text = "SANTANA'S"
            textSize = 30f
            setTextColor(green)
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "Sua vida conectada."
            textSize = 15f
            setTextColor(white)
            gravity = Gravity.CENTER
        }

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 35, 0, 20)
        }

        root.addView(logo)
        root.addView(subtitle)
        root.addView(content)

        val conversations = makeButton("CONVERSAS")
        conversations.setOnClickListener { showConversations() }

        val location = makeButton("LOCALIZAÇÃO")
        location.setOnClickListener { showLocation() }

        root.addView(conversations)
        root.addView(location)

        setContentView(root)
        showConversations()
    }

    private fun makeButton(label: String): Button {
        return Button(this).apply {
            text = label
            textSize = 15f
            setTextColor(white)
            setBackgroundColor(green)
        }
    }

    private fun showConversations() {
        content.removeAllViews()

        val title = makeText("Minhas conversas", 23f, green)
        val message = makeText(
            "Bem-vindo ao Santana's!\n\n" +
            "Em breve você poderá conversar " +
            "e compartilhar momentos com seus contatos.",
            17f, white
        )

        content.addView(title)
        content.addView(message)
    }

    private fun showLocation() {
        content.removeAllViews()

        val title = makeText("Minha localização", 23f, green)
        val message = makeText(
            "Compartilhe sua localização com " +
            "as pessoas importantes para você.\n\n" +
            "O controle de localização será ligado " +
            "ou desligado por você.",
            17f, white
        )

        content.addView(title)
        content.addView(message)
    }

    private fun makeText(
        value: String,
        size: Float,
        color: Int
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            setPadding(0, 15, 0, 20)
        }
    }
}
