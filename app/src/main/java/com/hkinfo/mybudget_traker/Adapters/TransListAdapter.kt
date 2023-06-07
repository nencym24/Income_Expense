package com.hkinfo.mybudget_traker.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hkinfo.mybudget_traker.Models.TransModel
import com.hkinfo.mybudget_traker.R
import com.hkinfo.mybudget_traker.databinding.TransItemBinding
import java.util.ArrayList
import kotlin.coroutines.coroutineContext

class TransListAdapter(update: (TransModel) -> Unit,delete: (Int)-> Unit) :
    RecyclerView.Adapter<TransListAdapter.TransHolder>() {

    lateinit var list: ArrayList<TransModel>
    var update = update
    var delete = delete
    lateinit var context: Context

    class TransHolder(itemView: TransItemBinding) : ViewHolder(itemView.root) {
        var binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransHolder {
        context = parent.context
        var binding = TransItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TransHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TransHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.apply {
            list.get(position).apply {

                txtTitle.text = title
                txtCategory.text = category
                txtAmount.text = amt.toString()

                if (isexpense == 0) {
                    txtAmount.setTextColor(Color.parseColor("#479C5E"))
                    round.setImageResource(R.drawable.arrow_up)
                    roundBack.setImageResource(R.drawable.income)
                } else {
                    roundBack.setImageResource(R.drawable.expense)
                    round.setImageResource(R.drawable.arrow_down)
                    txtAmount.setTextColor(Color.parseColor("#DD4545"))
                }

            }
        }
        holder.itemView.setOnLongClickListener(object : OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {

                var popupMenu = PopupMenu(context,holder.itemView)
                popupMenu.menuInflater.inflate(R.menu.trans_item,popupMenu.menu)

                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {

                        if (p0?.itemId == R.id.update){
                            update.invoke(list.get(position))
                        }
                        if (p0?.itemId == R.id.delete){
                            delete.invoke(list.get(position).id)
                        }
                        return true
                    }
                })
                popupMenu.show()
                return true
            }
        })
    }
    fun setTrans(list: ArrayList<TransModel>) {
        this.list = list
    }
    fun updateData(transaction: ArrayList<TransModel>) {
        this.list = transaction
        notifyDataSetChanged()
    }
}