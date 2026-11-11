
package com.santanas.app

import android.provider.ContactsContract
import android.app.Activity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
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
    private var locationEnabled = false

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

        val logo = makeText("SANTANA'S", 30f, green).apply {
            gravity = Gravity.CENTER
        }

        val subtitle = makeText("Sua vida conectada.", 15f, white).apply {
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

        val contacts = makeButton("CONTATOS")
        contacts.setOnClickListener { showContacts() }

        root.addView(conversations)
        root.addView(location)
        root.addView(contacts)

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
        content.addView(makeText("Minhas conversas", 23f, green))
        content.addView(makeText(
            "Bem-vindo ao Santana's!\n\n" +
            "Em breve você poderá conversar " +
            "e compartilhar momentos com seus contatos.",
            17f, white
        ))
    }

    private fun showLocation() {
        content.removeAllViews()

        content.addView(makeText("Minha localização", 23f, green))
        content.addView(makeText(
            "Compartilhe sua localização com " +
            "as pessoas importantes para você.\n\n" +
            "O controle de localização será ligado " +
            "ou desligado por você.",
            17f, white
        ))

        val status = makeText(
            if (locationEnabled) "Localização ativada" 
            else "Localização desativada",
            17f, green
        )
        content.addView(status)

        val toggle = makeButton(
            if (locationEnabled) "DESATIVAR LOCALIZAÇÃO"
            else "ATIVAR LOCALIZAÇÃO"
        )

        toggle.setOnClickListener {
            if (locationEnabled) {
                locationEnabled = false
                showLocation()
            } else {
                requestLocationPermission()
            }
        }

        content.addView(toggle)
    }

    private fun requestLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            locationEnabled = true
            showLocation()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )

        if (requestCode == 100) {
            locationEnabled = grantResults.any {
                it == PackageManager.PERMISSION_GRANTED
            }
            showLocation()
        }
    }

    
private fun showContacts() {
    content.removeAllViews()

    content.addView(makeText("Meus contatos", 23f, green))

    if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {

        val permissionButton = makeButton("ACESSAR MEUS CONTATOS")

        permissionButton.setOnClickListener {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                200
            )
        }

        content.addView(makeText(
            "Autorize o acesso para visualizar sua agenda.",
            17f, white
        ))

        content.addView(permissionButton)
        return
    }

    var total = 0

    val cursor = contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        null,
        null,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
    )

    cursor?.use {
        while (it.moveToNext()) {
            val name = it.getString(0) ?: "Sem nome"
            val number = it.getString(1) ?: ""

            content.addView(makeText(
                "$name\n$number",
                16f,
                white
            ))

            total++
        }
    }

    if (total == 0) {
        content.addView(makeText(
            "Nenhum contato encontrado.",
            17f, white
        ))
    }
}
