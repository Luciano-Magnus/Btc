package com.magnus.btc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_save.*

class SaveAtivosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)
        val cliente = intent.getParcelableExtra<Ativos_parce>("cliente")

        FABBack.setOnClickListener(View.OnClickListener {

            var ativoDao = AtivosDao(this)
            ativoDao.delete(cliente)
            onBackPressed()
        })


        FABSave.setOnClickListener(View.OnClickListener {

            var ativo = Ativos_parce(null,edtCodigo.text.toString(),edtnome.text.toString(), edtQtd.text.toString().toInt())
            var ativoDao = AtivosDao(this)
            ativoDao.insert(ativo)
            //  clienteDao.getAll()
            onBackPressed()
        })
    }



}
