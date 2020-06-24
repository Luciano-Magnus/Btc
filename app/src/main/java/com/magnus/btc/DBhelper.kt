package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcel
import android.os.Parcelable
import android.util.Log


private val DB_NAME = "Portfolio.db"
private val DB_VERSION =4
class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    override fun onCreate(db: SQLiteDatabase) {
        val sql="CREATE TABLE $NOME_TABELA($ID_COLUMN  INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, $NOME_COLUMN TEXT,$CODIGO_COLUMN TEXT, $QTD_COLUMN INTEGER);"+
                " CREATE TABLE $TABELA_COMPRA($ID_COMPRA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$NOME_COMPRA TEXT, $DATA_COMPRA TEXT, $QTD_COMPRA INTEGER, $VALOR_COMPRA INTEGER)"
            db.execSQL(sql)

        Log.e("LOG","Criando")
    }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

          db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA)

      onCreate(db)
  }

}

