package com.magnus.btc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private  var AtivoList = mutableListOf<Ativos_parce>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_add.setOnClickListener(View.OnClickListener {

            val it = Intent(this, SaveAtivosActivity::class.java)
            startActivity(it)
        })
        initRecyclerView()

    }

//    fun soma(): Int{
//        val ativoDao = AtivosDao(this)
//        val soma = ativoDao.soma()
//        return soma
//    }



    private fun initRecyclerView() {
        Log.v("LOG", "Inicia RecyclerView")
        val adapter2 = AtivosAdapter(AtivoList)
        rv_dados.adapter = adapter2
        val layout = LinearLayoutManager(this)
        rv_dados.layoutManager = layout
    }

    private fun update(){
        val ativoDao = AtivosDao(this)
        AtivoList.clear()
        AtivoList = ativoDao.select()
       // val soma = ativoDao.soma()
        //Log.v("LOG", "" + soma.toString())

        if (AtivoList.isEmpty()){
            rv_dados.setVisibility(View.GONE)
            txtMsg.setVisibility(View.VISIBLE)
            txtMsg.text = "Nenhum ativo adicionado"
        }else{
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
}
