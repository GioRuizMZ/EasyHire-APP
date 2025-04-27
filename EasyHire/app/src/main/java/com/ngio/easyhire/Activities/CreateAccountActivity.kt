package com.ngio.easyhire.Activities

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.ngio.easyhire.R

class CreateAccountActivity :AppCompatActivity() {

    private lateinit var lay_nombre : TextInputLayout
    private lateinit var lay_correo : TextInputLayout
    private lateinit var lay_contraseña : TextInputLayout
    private lateinit var lay_rep_contraseña : TextInputLayout
    private lateinit var lay_telefono : TextInputLayout
    private lateinit var edt_nombre : TextInputEditText
    private lateinit var edt_correo : TextInputEditText
    private lateinit var edt_contraseña : TextInputEditText
    private lateinit var edt_rep_contraseña : TextInputEditText
    private lateinit var edt_telefono : TextInputEditText
    private lateinit var terminos_y_condiciones : CheckBox
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crearcuenta)

        lay_nombre = findViewById(R.id.layout_nombre)
        lay_correo = findViewById(R.id.layout_correo)
        lay_contraseña = findViewById(R.id.layout_contraseña)
        lay_rep_contraseña = findViewById(R.id.layout_rep_contraseña)
        lay_telefono = findViewById(R.id.layout_tel)
        edt_nombre = findViewById(R.id.edt_nombre)
        edt_correo = findViewById(R.id.edt_correo)
        edt_contraseña = findViewById(R.id.edt_contraseña)
        edt_rep_contraseña = findViewById(R.id.edt_rep_contraseña)
        edt_telefono = findViewById(R.id.edt_tel)
        terminos_y_condiciones = findViewById(R.id.term_y_cond)
        var BtnCrearCuenta : Button = findViewById(R.id.CrearCuenta)
        var BtnRegresar : ImageButton = findViewById(R.id.BackButton)

        BtnCrearCuenta.setOnClickListener(){
            validarCampos()
        }
        BtnRegresar.setOnClickListener(){
            this.finish()
        }
    }
    fun mostrarDialogoPersonalizado() {
        val dialogo = Dialog(this)
        dialogo.setContentView(R.layout.alerta_informativa)
        dialogo.window?.setBackgroundDrawableResource(R.drawable.fondo_alertas)

        val titulo = dialogo.findViewById<TextView>(R.id.Titulo)
        titulo.text = "Se envio un correo de verificacion"
        val btnAceptar = dialogo.findViewById<Button>(R.id.Boton)
        btnAceptar.text = "Aceptar"

        btnAceptar.setOnClickListener {
            dialogo.dismiss()
        }
        dialogo.setCancelable(false)
        dialogo.show()
    }
    private fun validarCampos() {
        if(!validarNombre() and !validarEmail() and !validarPassword() and !validarTel() ){
           crearCuenta()
        }
        else{
            edt_nombre.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validarNombre()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
            edt_correo.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validarEmail()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
            edt_contraseña.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validarPassword()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
            edt_rep_contraseña.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validarPassword()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
            edt_telefono.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validarTel()
                }
                override fun afterTextChanged(s: Editable?) {}
            })

        }
    }
    private fun validarNombre(): Boolean{
        var txt_nombre = edt_nombre.text.toString()
        var errorn = true

        if(txt_nombre.isNullOrEmpty()){
            lay_nombre.error = "Ingresa un numobre"
        } else if(txt_nombre.length <=3 ){
            lay_nombre.error = "Escribe minimo 4 caracteres"
        }else if(txt_nombre.length >15){
            lay_nombre.error = "El nombre muy largo"
        }else {
            errorn = false
        }
        return errorn
    }
    private fun validarEmail(): Boolean {
        var txt_email = edt_correo.text.toString()
        var errorc = true

        if (txt_email.isNullOrEmpty()) {
            lay_correo.error = "Ingrese un correo"
        } else if (!isValidEmail(txt_email)) {
            lay_correo.error = "Correo invalido"
        } else {
            lay_correo.error = null
            errorc = false
        }
        return errorc
    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun validarPassword(): Boolean {
        var errorp = true
        var txt_password1 = edt_contraseña.text.toString()
        var txt_password2 = edt_contraseña.text.toString()

        if (txt_password1.isNullOrEmpty()) {
            lay_contraseña.error = "Ingrese una contraseña"
        } else if (txt_password1.length < 8) {
            lay_contraseña.error = "Ingrese mínimo 8 caracteres"
        }else if (txt_password2.isNullOrEmpty()) {
            lay_contraseña.error = "Ingrese una contraseña"
        } else if (txt_password2.length < 8) {
            lay_rep_contraseña.error = "Ingrese mínimo 8 caracteres"
        }
        else if(txt_password1 != txt_password2){
            lay_rep_contraseña.error = "Las contraseñas no coinciden"
        } else {
            lay_rep_contraseña.error = null
            errorp = false
        }
        return errorp
    }
    private fun validarTel(): Boolean {
        var errort = true
        var txt_tel = edt_telefono.text.toString()

        if(txt_tel.isNullOrEmpty()){
            lay_telefono.error = "Ingresa un telefono"
        } else if(txt_tel.length!=10){
            lay_telefono.error = "Numero invalido"
        } else if(!txt_tel.startsWith("33")){
            lay_telefono.error = "Region fuera de soporte"
        } else if(!txt_tel.isDigitsOnly()){
            lay_telefono.error = "Escribe solo numeros"
        } else{
            errort = false
        }
        return errort
    }
    private fun crearCuenta(){
        val nombre = edt_nombre.text.toString()
        val correo = edt_correo.text.toString()
        val contraseña = edt_contraseña.text.toString()
        val telefono = edt_telefono.text.toString()

        auth.createUserWithEmailAndPassword(correo,contraseña).addOnCompleteListener(this){
            task->
            if(task.isSuccessful){
                var usuario = auth.currentUser
                usuario?.sendEmailVerification()
                mostrarDialogoPersonalizado()
            }
            else if (task.isCanceled){
                Toast.makeText(this,"Proceso cancelado",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Hubo un error con el servidor",Toast.LENGTH_LONG).show()
            }
        }
    }
}