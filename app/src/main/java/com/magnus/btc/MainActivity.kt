package com.magnus.btc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var AtivoList = mutableListOf<Ativo>()
    private var asyncTask: StatesTask? = null
    var codigo : String = ""
    var valor = 0.0
    var n1 = 0.0
    var n2 = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Falta somar os valores da api pra ver a valorização dos ativos
        codigo = "LTC"
        CarregaDados()



        fab_add.setOnClickListener(View.OnClickListener {

            val it = Intent(this, SaveAtivosActivity::class.java)
            startActivity(it)
        })
        initRecyclerView()

    }

    private fun initRecyclerView() {
        Log.v("LOG", "Inicia RecyclerView")
        val adapter2 = AtivosAdapter(AtivoList)
        rv_dados.adapter = adapter2
        val layout = LinearLayoutManager(this)
        rv_dados.layoutManager = layout
    }

    private fun update() {
        val ativoDao = AtivosDAO(this)
        AtivoList.clear()
        AtivoList = ativoDao.select()
        val compraDao = ComprasDAO(this)
        val total = compraDao.selectTotal()
        txt_total.text = total.toString()


        if (AtivoList.isEmpty()) {
            rv_dados.setVisibility(View.GONE)
            txtMsg.setVisibility(View.VISIBLE)
            txtMsg.text = "Nenhum ativo adicionado"
        } else {
            rv_dados.setVisibility(View.VISIBLE)
            txtMsg.setVisibility(View.GONE)
        }
        rv_dados.adapter?.notifyDataSetChanged()
    }

    override fun onRestart() {
        super.onRestart()
        update()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        update()
        initRecyclerView()
    }

    fun CarregaDados() {
        if (asyncTask == null) {
            if (BoletimAtivosHTTP.hasConnection(this)) {
                if (asyncTask?.status != AsyncTask.Status.RUNNING) {
                    asyncTask = StatesTask()
                    asyncTask?.execute()
                }
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    inner class StatesTask : AsyncTask<Void, Void, BoletimAtivos?>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }


        @SuppressLint("WrongThread")
        @RequiresApi(Build.VERSION_CODES.O)
        override fun doInBackground(vararg params: Void?): BoletimAtivos? {
            return BoletimAtivosHTTP.loadState(codigo)
        }


        private fun update(result: BoletimAtivos?) {

            if (result != null) {
                if (codigo== "LTC") {
                    valor = result.buy.toDouble()
//                    txt_totalMoedas.text = valor.toString()
                }
                if (codigo=="BTC"){
                    n1= result.buy.toDouble()
                }
                if (codigo=="XRP"){
                    n2= result.buy.toDouble()
                }
                var teste = valor + n1 + n2
            }
            n2= n1

            asyncTask = null
        }

        override fun onPostExecute(result: BoletimAtivos?) {
            super.onPostExecute(result)
            update(result as BoletimAtivos?)
        }
    }
}
