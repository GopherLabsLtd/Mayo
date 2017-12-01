package it.gopher.mayo_demo

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.gson.JsonSyntaxException
import it.gopher.mayo_demo.models.GitHubRepository
import com.miguelcatalan.materialsearchview.MaterialSearchView
import it.gopher.mayo_demo.providers.FileProvider
import it.gopher.mayo_demo.providers.GitHubApi
import it.gopher.mayo_demo.providers.GitHubProvider
import it.gopher.mayo_demo.providers.HttpBinProvider
import java.util.*

/**
 * Main Activity for the Application.
 *
 * This Activity contains a [RecyclerView] that display all of a user's repositories.
 * To Obtain all the user's reposiory, the [GitHubApi] is used with to fetch all the data using a search.
 * @author Alex Nguyen
 */
class MainActivity : AppCompatActivity(), MaterialSearchView.OnQueryTextListener, MainActivityView {

    private var recyclerView: RecyclerView? = null
    private var emptyView: View? = null
    private var searchView: MaterialSearchView? = null
    private var adapter: RepositoryAdapter? = null
    private val presenter = MainActivityPresenter(GitHubProvider, FileProvider, HttpBinProvider, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))

        searchView = findViewById(R.id.search_view)

        searchView?.setOnQueryTextListener(this)

        emptyView = findViewById(R.id.empty_view)

        adapter = RepositoryAdapter(this, ArrayList())

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview);
        recyclerView?.layoutManager = LinearLayoutManager(this);
        recyclerView?.itemAnimator = DefaultItemAnimator();
        recyclerView?.adapter = adapter
        presenter.searchUsernameRepo("GopherLabsLtd")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        searchView?.setMenuItem(menu?.findItem(R.id.action_search))
        menu?.findItem(R.id.action_download)?.setOnMenuItemClickListener({
            presenter.downloadRandomImage()
            true
        })
        menu?.findItem(R.id.action_zen)?.setOnMenuItemClickListener({
            presenter.showGitHubZen()
            true
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter.searchUsernameRepo(query ?: "")
        return false
    }

    override fun displayError(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayImage(image: Bitmap) {
        val view = View.inflate(this@MainActivity, R.layout.alert_dialog, null)
        val imageView = view.findViewById<ImageView>(R.id.selectedImage)
        imageView.setImageBitmap(image)
        val alertDialog = AlertDialog.Builder(this@MainActivity)
                .setView(view)
                .create()
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Close", { dialogInterface, i ->
        })
        alertDialog.show()
    }

    override fun reloadRepositories(repos: Array<GitHubRepository>?) {
        val reposList = repos.let { it } ?: return
        try {
            adapter?.updateList(reposList.toList())
        } catch (exception: JsonSyntaxException) {
            displayError(exception.toString())
        } finally {
            if (reposList.isEmpty()) {
                recyclerView?.visibility = View.GONE
                emptyView?.visibility = View.VISIBLE
            } else {
                recyclerView?.visibility = View.VISIBLE
                emptyView?.visibility = View.GONE
            }
            adapter?.updateList(reposList.toList())
        }
    }
}

fun List<Char>.random() = this[Random().nextInt(this.size)]
fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start