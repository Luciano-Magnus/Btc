package com.magnus.btc

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_save_compras.*
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class SaveComprasActivity : AppCompatActivity() {
    private var asyncTask: StatesTask? = null
    var codigo: String = ""
    var valor = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_compras)

        val compra = intent.getParcelableExtra<Nome>("nomeAtivo")
        if (compra.nome.equals("Bitcoin")) {
            codigo = "BTC"
        }
        if (compra.nome.equals("Litecoin")) {
            codigo = "LTC"
        }
        if (compra.nome.equals("XRP")) {
            codigo = "XRP"
        }
        if (compra.nome.equals("BCash")) {
            codigo = "BCH"
        }
        if(compra.nome.equals("Ethereum")){
            codigo = "ETH"
        }
        CarregaDados()

        FABBack.setOnClickListener({
            onBackPressed()
        })


        FABSave.setOnClickListener {

            try {
                val verificacao = edtQtd.text.toString().toFloat()
                val dStr = getDate()



                var compra = Compra(
                    null,
                    compra.nome.toString(),
                    dStr.toString(),
                    edtQtd.text.toString().toDouble(),
                    valor
                )
                var compraDao = ComprasDAO(this)
                compraDao.insert(compra)
                onBackPressed()
            } catch (e: Exception) {
                val alerta =
                    AlertDialog.Builder(this@SaveComprasActivity)
                alerta.setTitle("Aviso")
                alerta
                    .setIcon(R.drawable.ic_info_foreground)
                    .setMessage("Você deve digitar apenas numeros.")
                    .setCancelable(true)
                    .setPositiveButton(
                        "OK",
                        DialogInterface.OnClickListener({ dialogInterface, i ->
                        })
                    )


                val alertDialog = alerta.create()
                alertDialog.show()
            }


        }

    }

    fun CarregaDados() {
        if (asyncTask == null) {
            if (MoedasComprasHTTP.hasConnection(this)) {
                if (asyncTask?.status != AsyncTask.Status.RUNNING) {
                    asyncTask = StatesTask()
                    asyncTask?.execute()
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class StatesTask : AsyncTask<Void, Void, MoedasCompras?>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }


        @SuppressLint("WrongThread")
        @RequiresApi(Build.VERSION_CODES.O)
        override fun doInBackground(vararg params: Void?): MoedasCompras? {
            return MoedasComprasHTTP.loadMoedas(codigo)
        }


        private fun update(result: MoedasCompras?) {
        var df = DecimalFormat("#0.00")
            if (result != null) {
               valor = result.buy.toDouble()
                }
            txt_valor.text = "R$ "+df.format(valor)

            asyncTask = null
        }

        override fun onPostExecute(result: MoedasCompras?) {
            super.onPostExecute(result)
            update(result as MoedasCompras?)
        }
    }

    private fun getDate(): String? {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return dateFormat.format(date)
    }
}
