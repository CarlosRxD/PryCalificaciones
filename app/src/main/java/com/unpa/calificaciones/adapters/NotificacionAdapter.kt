package com.unpa.calificaciones.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unpa.calificaciones.R
import com.unpa.calificaciones.view_holders.NotificationViewHolder
import java.text.SimpleDateFormat
import java.util.Locale

data class NotificationItem(
    val iconResId: Int,
    val title: String,
    val message: String,
    val timestamp: Long
)

class NotificationAdapter(
    private val items: List<NotificationItem>,
    private val onClick: ((NotificationItem) -> Unit)? = null
) : RecyclerView.Adapter<NotificationViewHolder>() {

    private val dateFormatter = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notificacion_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = items[position]
        holder.icon.setImageResource(item.iconResId)
        holder.title.text = item.title
        holder.message.text = item.message
        holder.date.text = dateFormatter.format(item.timestamp)
        holder.itemView.setOnClickListener {
            onClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int = items.size
}