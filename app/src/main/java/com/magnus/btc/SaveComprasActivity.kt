package com.magnus.btc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_save_compras.*

class SaveComprasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_compras)
        FABSave.setOnClickListener({
            var compra = ComprasParce(null , "BTC", "23/06/2020", edtQtd.text.toString().toInt(), 40000)
            var compraDao = ComprasDao(this)
            compraDao.insert(compra)
            //  clienteDao.getAll()
            onBackPressed()
        })

    }
}
