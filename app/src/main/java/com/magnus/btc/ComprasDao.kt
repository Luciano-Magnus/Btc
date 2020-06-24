package com.magnus.btc

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.myapplication.*

class ComprasDao(context: Context) {
    val banco = DbHelper(context)


    fun insert(compra: ComprasParce): String {
        val db = banco.writableDatabase
        val contextValues = ContentValues()

        contextValues.put(ID_COMPRA, compra.id)
        contextValues.put(NOME_COMPRA, compra.nome)
        contextValues.put(DATA_COMPRA, compra.data)
        contextValues.put(QTD_COMPRA, compra.qtd)
        contextValues.put(VALOR_COMPRA, compra.valor)

        val resp_id = db.insert(TABELA_COMPRA, null, contextValues)
        val msg = if (resp_id != -1L) {
            "Inserido"
        } else {
            "Nao inserido"
        }
        db.close()
        return msg
    }

    fun selectNome(nome:String): ArrayList<ComprasParce> {
        Log.v("LOG", "GetAll")
        val db = banco.writableDatabase
        val sql = "SELECT * from " + TABELA_COMPRA
        Log.v("LOG", "" + sql)
        val cursor = db.rawQuery(sql, null)
        val compra = ArrayList<ComprasParce>()
        while (cursor.moveToNext()) {
            val compras = compraFromCursor(cursor)
            compra.add(compras)
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${compra.size}")
        return compra
    }


    private fun compraFromCursor(cursor: Cursor): ComprasParce {
        val id = cursor.getInt(cursor.getColumnIndex(ID_COMPRA))
        val nome = cursor.getString(cursor.getColumnIndex(NOME_COMPRA))
        val data = cursor.getString(cursor.getColumnIndex(DATA_COMPRA))
        val qtd = cursor.getInt(cursor.getColumnIndex(QTD_COMPRA))
        val valor = cursor.getInt(cursor.getColumnIndex(VALOR_COMPRA))

        return ComprasParce(id, nome, data, qtd, valor)

    }
}
