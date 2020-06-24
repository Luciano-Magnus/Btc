package com.magnus.btc

import android.os.Parcel
import android.os.Parcelable

 data class Ativos_parce(var id: Int?, val nome: String?, val codigo: String?, var qtd: Int?):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()

    )


     override fun writeToParcel(parcel: Parcel, flags: Int) {
         this!!.id?.let { parcel.writeInt(it) }
        parcel.writeString(nome)
         parcel.writeString(codigo)
         if (qtd != null) {
             parcel.writeInt(qtd!!)
         }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ativos_parce> {
        override fun createFromParcel(parcel: Parcel): Ativos_parce {
            return Ativos_parce(parcel)
        }

        override fun newArray(size: Int): Array<Ativos_parce?> {
            return arrayOfNulls(size)
        }
    }
}