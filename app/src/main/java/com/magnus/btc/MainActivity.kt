package com.magnus.btc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private var AtivoList = mutableListOf<Ativo>()
    private var asyncTask: StatesTask? = null
    var valorLTC = 0.0
    var valorBTC = 0.0
    var valorXRP = 0.0
    var valorBCH = 0.0
    var valorETH = 0.0
    var qtdBTC = 0.0
    var qtdLTC = 0.0
    var qtdBCH =0.0
    var qtdXRP = 0.0
    var qtdETH = 0.0
    var total = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_add.setOnClickListener(View.OnClickListener {

            val it = Intent(this, SaveAtivosActivity::class.java)
            startActivity(it)
        })
        initRecyclerView()
        CarregaDados()
    }

    private fun initRecyclerView() {
        Log.v("LOG", "Inicia RecyclerView")
        val adapter2 = AtivosAdapter(AtivoList)
        rv_dados.adapter = adapter2
        val layout = LinearLayoutManager(this)
        rv_dados.layoutManager = layout
    }

    private fun update() {
        val df = DecimalFormat ("#0.00")
        val ativoDao = AtivosDAO(this)
        AtivoList.clear()
        AtivoList = ativoDao.select()
        val compraDao = ComprasDAO(this)
        total = compraDao.selectTotal()
        qtdBTC = compraDao.selectQtd("Bitcoin")
        qtdLTC = compraDao.selectQtd("Litecoin")
        qtdBCH = compraDao.selectQtd("BCash")
        qtdXRP = compraDao.selectQtd("XRP")
        qtdETH = compraDao.selectQtd("Ethereum")
        txt_total.text = getResources().getString(R.string.qtd) + " = "  + df.format(total)


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
            if (MoedaAtivosHTTP.hasConnection(this)) {
                if (asyncTask?.status != AsyncTask.Status.RUNNING) {
                    asyncTask = StatesTask()
                    asyncTask?.execute()
                }
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    inner class StatesTask : AsyncTask<Void, Void, ArrayList<MoedaAtivos>?>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }


        @SuppressLint("WrongThread")
        @RequiresApi(Build.VERSION_CODES.O)
        override fun doInBackground(vararg params: Void?): ArrayList<MoedaAtivos>? {
            return MoedaAtivosHTTP.loadMoeda()
        }


        private fun update(result: ArrayList<MoedaAtivos>?) {
            val df = DecimalFormat("#0.00")
            if (result != null) {

                valorBTC = result[0].buy.toDouble()
                valorBTC = valorBTC * qtdBTC
                valorLTC = result[1].buy.toDouble()
                valorLTC = valorLTC * qtdLTC
                valorXRP = result[2].buy.toDouble()
                valorXRP = valorXRP * qtdXRP
                valorBCH = result[3].buy.toDouble()
                valorBCH = valorBCH * qtdBCH
                valorETH = valorETH * qtdETH

                var valorTotal =valorBTC + valorLTC + valorBCH + valorXRP + valorETH
                var variacao = (valorTotal / total - 1) *100

                txt_totalMoedas.text = getResources().getString(R.string.qtd_total) + " = " + df.format(valorTotal)
                txt_variacao.text = resources.getString(R.string.variacao) + " = " + df.format(variacao) + "%"
            }

            asyncTask = null
        }

        override fun onPostExecute(result: ArrayList<MoedaAtivos>?) {
            super.onPostExecute(result)
            update(result as ArrayList<MoedaAtivos>)
//            txt_totalMoedas.text ="valor" + valorBTC.toString()

        }
    }
}
