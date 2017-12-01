package it.gopher.mayo_demo

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import it.gopher.mayo_demo.models.GitHubRepository

/**
 * A Generic View Holder that displays a Single [GitHubRepository].
 * @author Eliad Moosavi
 */
class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * The Textview to display the name.
     */
    val nameTextView: TextView by lazy {
        itemView.findViewById<TextView>(R.id.repository_name)
    }

    /**
     * Retrieves the resource ID for this particular view holder.
     *
     * @return layout resource Id.
     */
    companion object {
        @JvmStatic
        fun getResourceId(): Int {
            return R.layout.repository_list_item;
        }
    }
}