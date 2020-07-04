package com.magnus.btc

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ativos_item.view.*

class AtivosAdapter (private val ativos: List<Ativo>):
    RecyclerView.Adapter<AtivosAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        Log.v("LOG", "onCreate")
        val v= LayoutInflater.from(parent.context).inflate(R.layout.ativos_item,parent,false)
        val vh = VH(v)


        vh.itemView.setOnClickListener{
            val ativo= ativos[vh.adapterPosition]
            val it = Intent(parent.context, ComprasActivity::class.java)
           it.putExtra("ativos",ativo)
           parent.context.startActivity(it)

        }
        return vh
    }

    override fun getItemCount(): Int {
        return ativos.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Log.v("LOG", "ViewHolder")


        var ativo = ativos[position]
        holder.txtCodigo.text= ativo.codigo.toString()
        holder.txtMoeda.text= ativo.nome.toString()

    }

    class VH(itenView: View): RecyclerView.ViewHolder(itenView){

        var txtCodigo:TextView=itenView.txt_quantidade
        var txtMoeda:TextView = itenView.txtmoeda
    }
}