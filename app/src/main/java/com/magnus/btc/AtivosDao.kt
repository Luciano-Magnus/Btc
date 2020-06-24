package com.magnus.btc

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.myapplication.*
import java.lang.Integer.parseInt


class AtivosDao(context: Context) {
    val banco = DbHelper(context)

    fun insert(ativo: Ativos_parce): String {
        val db = banco.writableDatabase
        val contextValues = ContentValues()

        contextValues.put(ID_COLUMN, ativo.id)
        contextValues.put(NOME_COLUMN, ativo.nome)
        contextValues.put(CODIGO_COLUMN, ativo.codigo)
       // var soma = soma(ativo)
        contextValues.put(QTD_COLUMN, ativo.qtd)

        val resp_id = db.insert(NOME_TABELA, null, contextValues)
        val msg = if (resp_id != -1L) {
            "Inserido"
        } else {
            "Erro ao inserir"
        }
        db.close()
        return msg
    }

//    fun soma(ativo: Ativos_parce) :String{
//
//        val sum = "SELECT SUM("+ QTD_COLUMN+") FROM " + NOME_TABELA
//        return sum
//    }

    fun update(ativo: Ativos_parce): Boolean {

        val db = banco.writableDatabase
        val contextValues = ContentValues()

        return true
    }

    fun delete(ativo: Ativos_parce): Int {
        val db = banco.writableDatabase
        return db.delete(NOME_TABELA, "id_ativos =?", arrayOf(ativo.id.toString()))
    }

    fun select(): ArrayList<Ativos_parce> {
        Log.v("LOG", "GetAll")
        val db = banco.writableDatabase
        val sql = "SELECT * from " + NOME_TABELA
        val cursor = db.rawQuery(sql, null)
        val ativo = ArrayList<Ativos_parce>()
        while (cursor.moveToNext()) {
            val moeda = ativoFromCursor(cursor)
            ativo.add(moeda)
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${ativo.size}")
        return ativo
    }

    fun soma(): Int {
        val db= banco.writableDatabase
        var sum = 0

        val sql = "SELECT * from " + NOME_TABELA
        val cursor = db.rawQuery(sql, null)
        val ativo = ArrayList<Ativos_parce>()
        while (cursor.moveToNext()) {
            val moeda = ativoFromCursor(cursor)
            sum +=  moeda.qtd!!
//            ativo.add(moeda)
        }
        cursor.close()
        db.close()
        Log.v("LOG", "Get ${ativo.size}")
        return sum
    }


    private fun ativoFromCursor(cursor: Cursor): Ativos_parce {
        val id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN))
        val codigo = cursor.getString(cursor.getColumnIndex(CODIGO_COLUMN))
        val nome = cursor.getString(cursor.getColumnIndex(NOME_COLUMN))
        val qtd = cursor.getInt(cursor.getColumnIndex(QTD_COLUMN))

        return Ativos_parce(id, nome, codigo, qtd)

    }
}