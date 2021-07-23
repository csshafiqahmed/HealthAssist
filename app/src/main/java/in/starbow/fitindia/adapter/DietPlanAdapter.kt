package `in`.starbow.fitindia.adapter

import `in`.starbow.fitindia.DietDetailActivity
import `in`.starbow.fitindia.R
import `in`.starbow.fitindia.helper.AppPrefs
import `in`.starbow.fitindia.model.Diet
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import java.util.*

class DietPlanAdapter(private val context: Context, arrayList: ArrayList<Diet>) :
    RecyclerView.Adapter<DietPlanAdapter.DietPlanViewHolder>() {
    private val arrayList: ArrayList<Diet>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietPlanViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.listitem_diet_plan, parent, false)
        return DietPlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: DietPlanViewHolder, position: Int) {
        val diet: Diet = arrayList[position]
        if (diet.type == AppPrefs.getDisease()) {
            holder.img_diet.setImageResource(diet.image)
            holder.tv_title.text = (position + 1).toString() + ". " + diet.title
            holder.tv_desc.setText(diet.desc)
            holder.itemView.visibility = View.VISIBLE
        } else {
            holder.itemView.visibility = View.GONE
        }
        holder.itemView.setOnClickListener { view: View? ->
            val intent = Intent(context, DietDetailActivity::class.java)
            intent.putExtra("diet", diet)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class DietPlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_title: TextView
        var tv_desc: TextView
        var img_diet: ShapeableImageView

        init {
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_desc = itemView.findViewById(R.id.tv_desc)
            img_diet = itemView.findViewById(R.id.img_diet)
        }
    }

    init {
        this.arrayList = arrayList
        val it: MutableIterator<Diet> = arrayList.iterator()
        while (it.hasNext()) {
            val diet: Diet = it.next()
            if (diet.type != AppPrefs.getDisease()) {
                it.remove()
            }
        }
    }
}