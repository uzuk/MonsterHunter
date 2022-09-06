package com.example.monsterhunter

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.generateViewId
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_armor.view.*

class ArmorsAdapter( private val armors: List<Armor>, private val context: Context ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_armor, parent, false)
        return ArmorItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val rows = armors
        val item = rows[position]
        (holder as ArmorItemViewHolder).bindData(item, context)
    }

    override fun getItemCount(): Int {
        return armors.size
    }

    internal class ArmorItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bindData(armor: Armor, context: Context) {
            itemView.armorName.text = armor.name
            when(armor.type) {
                "chest" -> itemView.typeIcon.setImageResource(R.drawable.ic_chest)
                "deco" -> itemView.typeIcon.setImageResource(R.drawable.ic_deco)
                "gloves" -> itemView.typeIcon.setImageResource(R.drawable.ic_gloves)
                "head" -> itemView.typeIcon.setImageResource(R.drawable.ic_head)
                "legs" -> itemView.typeIcon.setImageResource(R.drawable.ic_legs)
                "shield" -> itemView.typeIcon.setImageResource(R.drawable.ic_shield)
                "waist" -> itemView.typeIcon.setImageResource(R.drawable.ic_waist)
            }
            itemView.rank.text = armor.rank
            itemView.defenceBase.text = armor.defense?.base.toString() + "+"
            if (!armor.slots.isNullOrEmpty()) {
                armor.slots.forEach {
                    if (it != null) {
                        val slotItem = TextView(context)
                        slotItem.id = generateViewId()
                        slotItem.layoutParams = LinearLayout.LayoutParams(48, 48)
                        slotItem.gravity = Gravity.CENTER
                        slotItem.text = it.rank
                        slotItem.textSize = 14f
                        slotItem.setBackgroundResource(R.drawable.small_circle)
                        itemView.constraint.addView(slotItem, armor.slots.indexOf(it))
                        itemView.slotFlow.addView(slotItem)
                    }
                }
            }
          //  itemView.slots.text = armor.slots?.size.toString()
        }
    }
}