package com.magnus.btc

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ativos_item.view.*
import kotlinx.android.synthetic.main.ativos_item.view.txt_preco
import kotlinx.android.synthetic.main.ativos_item.view.txt_quantidade
import kotlinx.android.synthetic.main.ativos_item.view.txtmoeda
import kotlinx.android.synthetic.main.compras_item.view.*

class CompraAdapter (private val ativos: List<Compra>):
    RecyclerView.Adapter<CompraAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        Log.v("LOG", "onCreate")
        val v= LayoutInflater.from(parent.context).inflate(R.layout.compras_item,parent,false)
        val vh = VH(v)


        vh.itemView.setOnClickListener{

        }
        return vh
    }

    override fun getItemCount(): Int {
        return ativos.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Log.v("LOG", "ViewHolder")


        var ativo = ativos[position]
        holder.txtQuantidade.text= "Quantidade = "+ativo.qtd.toString()
        holder.txtMoeda.text= ativo.nome.toString()
        holder.txtPreco.text = "Preço = "+ativo.valor.toString()
        holder.txtData.text = ativo.data
    }

    class VH(itenView: View): RecyclerView.ViewHolder(itenView){

        var txtQuantidade:TextView=itenView.txt_quantidade
        var txtMoeda:TextView = itenView.txtmoeda
        var txtPreco:TextView = itemView.txt_preco
        var txtData:TextView = itenView.txt_data
    }
}