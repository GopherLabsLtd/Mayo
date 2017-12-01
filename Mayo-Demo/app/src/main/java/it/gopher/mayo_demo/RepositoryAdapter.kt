package it.gopher.mayo_demo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.gopher.mayo_demo.models.GitHubRepository

/**
 * This adapter binds data to the rows needed to display repositories.
 *
 * This adapters main functionality is to bind [GitHubRepository] to [RepositoryViewHolder].
 *
 * @author Chamu Rajasekera
 * @param context the context used to grab resources from the app.
 * @param list the list of [GitHubRepository] to display.
 */
class RepositoryAdapter(var context: Context, var list: List<GitHubRepository>) :
        RecyclerView.Adapter<RepositoryViewHolder>() {

    fun updateList(newList: List<GitHubRepository>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder?, position: Int) {
        val repo = list[position]
        holder?.nameTextView?.setText(repo.name)
        holder?.itemView?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val unwrappedContext = holder.itemView?.context.let { it } ?: return
                displayRepo(repo.url, unwrappedContext)
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(RepositoryViewHolder.getResourceId(), parent, false);
        return RepositoryViewHolder(view)
    }

    private fun displayRepo(url: String, context: Context) {
        val uri = Uri.parse(url)
        val intents = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intents)
    }
}