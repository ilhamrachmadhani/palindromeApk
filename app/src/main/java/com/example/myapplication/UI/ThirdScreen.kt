package com.example.myapplication.UI
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.adapter.UserAdapter
import com.example.myapplication.data.retofit.APIConfig
import kotlinx.coroutines.launch

class ThirdScreen : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private var currentPage = 1
    private var totalPages = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = UserAdapter(mutableListOf()) { user ->
            val resultIntent = Intent()
            resultIntent.putExtra("selectedUserName", "${user.first_name} ${user.last_name}")
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load more when scroll bottom
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = rv.layoutManager as LinearLayoutManager
                if (!isLoading && currentPage < totalPages &&
                    layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    currentPage++
                    loadUsers()
                }
            }
        })

        swipeRefresh.setOnRefreshListener {
            currentPage = 1
            adapter.clearUsers()
            loadUsers {
                swipeRefresh.isRefreshing = false
            }
        }

        loadUsers()
    }

    private fun loadUsers(onComplete: (() -> Unit)? = null) {
        isLoading = true
        lifecycleScope.launch {
            try {
                val response = APIConfig.apiService.getUsers(currentPage)
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    totalPages = userResponse?.total_pages ?: 1
                    userResponse?.data?.let { adapter.addUsers(it) }
                }
            } catch (e: Exception) {
                Toast.makeText(this@ThirdScreen, "Failed to load users", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
                onComplete?.invoke()
            }
        }
    }
}
