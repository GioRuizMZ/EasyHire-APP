package com.ngio.easyhire.Activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ngio.easyhire.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var email: TextInputEditText
    private lateinit var lay_email: TextInputLayout
    private lateinit var password: TextInputEditText
    private lateinit var lay_password: TextInputLayout
    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth = FirebaseAuth.getInstance()
    private val callbackManager = CallbackManager.Factory.create();
    private val RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        FacebookSdk.sdkInitialize(applicationContext);
        loginButton = findViewById(R.id.LogIn)

        email = findViewById(R.id.edt_correo)
        lay_email = findViewById(R.id.layout_correo)
        password = findViewById(R.id.edt_contraseña)
        lay_password = findViewById(R.id.layout_contraseña)
        var txt_cc : TextView = findViewById(R.id.txt_crear_cuenta)
        var LoginGoogle : ImageButton = findViewById(R.id.btn_google)
        var LoginFacebook : ImageButton = findViewById(R.id.btn_face)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        loginButton.setOnClickListener {
            validarCampos()
        }
        txt_cc.setOnClickListener(){
            val intent = Intent(this@LoginActivity, CreateAccountActivity::class.java)
            startActivity(intent)
        }
        LoginGoogle.setOnClickListener(){
            signInWithGoogle()
        }
        LoginFacebook.setOnClickListener(){
            singInWithFacebook()
        }
    }

    private fun validarCampos() {
        if(!validarEmail() and !validarPassword()){
            Buscarcuenta()
        }
        else{
            email.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validarEmail()
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            password.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validarPassword()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun validarEmail(): Boolean {
        var txt_email = email.text.toString()
        var errorc = true
        if (txt_email.isEmpty()) {
            lay_email.error = "Ingrese un correo"
        } else if (!isValidEmail(txt_email)) {
            lay_email.error = "Correo invalido"
        } else {
            lay_email.error = null
            errorc = false
        }
        return errorc
    }

    private fun validarPassword(): Boolean {
        var errorp = true
        var txt_password = password.text.toString()
        if (txt_password.isEmpty()) {
            lay_password.error = "Ingrese una contraseña"
        } else if (txt_password.length < 8) {
            lay_password.error = "Ingrese mínimo 8 caracteres"
        } else {
            lay_password.error = null
            errorp = false
        }
        return errorp
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun Buscarcuenta(){
        var txt_email = email.text.toString().trim()
        var txt_password = password.text.toString().trim()

    }
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account?.idToken
            val displayName = account?.displayName

            if (idToken != null) {
                firebaseAuthWithGoogle(idToken)
            } else {
                Toast.makeText(this, "Token de ID nulo", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "Inicio de sesión fallido: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val mAuth = FirebaseAuth.getInstance()

        mAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithCredential:success")
                val currentUser = mAuth.currentUser
                val nombre = currentUser?.displayName ?: ""
                val correo = currentUser?.email ?: ""
                val uid = currentUser?.uid ?: ""

                Toast.makeText(this, "Bienvenido $nombre", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    delay(1000)
                    startActivity(Intent(this@LoginActivity, UserModeActivity::class.java))
                    finish()
                }
            } else {
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                Toast.makeText(this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun singInWithFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = loginResult.accessToken
            }

            override fun onCancel() {
                // valio
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext,"Inicio de sesion fallido",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
