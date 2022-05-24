package waslim.binar.andlima.latihan_noteapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*
import waslim.binar.andlima.latihan_noteapp.R
import waslim.binar.andlima.latihan_noteapp.local.note.Note

class AdapterNote(val listNote : List<Note>, private val OnClick : (Note) -> Unit) : RecyclerView.Adapter<AdapterNote.ViewHolder> () {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNote.ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: AdapterNote.ViewHolder, position: Int) {
        holder.itemView.judul.text = listNote[position].judul
        holder.itemView.time.text = listNote[position].waktu

        holder.itemView.cardNote.setOnClickListener {
            OnClick(listNote[position])
        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }


}