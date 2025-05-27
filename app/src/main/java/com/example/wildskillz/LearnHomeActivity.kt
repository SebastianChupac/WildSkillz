package com.example.wildskillz

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wildskillz.model.LearningContent.LearningContent
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class LearnHomeActivity : AppCompatActivity() {
    private lateinit var adapter: ContentAdapter
    private lateinit var fullContentList: List<LearningContent>
    private var currentSearchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_learn_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get username to display in top bar - profile
        val username = intent.getStringExtra("username")

        val profileTextView = findViewById<TextView>(R.id.tvUsername)
        profileTextView.text = "$username"

        // sample data
        fullContentList = listOf(
            LearningContent.Video(
                name = "Fire Starting 101",
                author = "John Doe",
                thumbnailResId = R.drawable.video1,
                price = "3,99€",
                duration = "10 min",
                views = "2.3k views"
            ),
            LearningContent.Workshop(
                name = "Weekend Foraging",
                author = "Jane Smith",
                thumbnailResId = R.drawable.workshop1,
                price = "FREE",
                time = "Saturday 18.6.2025, 15:45",
                location = "Forest Edge Park",
                capacity = 25
            ),
            LearningContent.Video(
                name = "How to build a Tent part 1",
                author = "Gregor Wile",
                thumbnailResId = R.drawable.video5,
                price = "FREE",
                duration = "17 min",
                views = "722 views"
            ),
            LearningContent.Workshop(
                name = "Camping on Easter Night",
                author = "Tamara Banks and Edward Jackson",
                thumbnailResId = R.drawable.workshop2,
                price = "13,00€",
                time = "Friday 18.6.2025, 15:45",
                location = "National Park, Riga",
                capacity = 9
            ),
            LearningContent.Video(
                name = "How to build a Tent part 2",
                author = "Gregor Wile",
                thumbnailResId = R.drawable.video2,
                price = "FREE",
                duration = "12 min",
                views = "452 views"
            ),
            LearningContent.Video(
                name = "How to build a Tent part 3",
                author = "Gregor Wile",
                thumbnailResId = R.drawable.video3,
                price = "FREE",
                duration = "11 min",
                views = "700 views"
            ),
            LearningContent.Video(
                name = "How to build a Tent part 4",
                author = "Gregor Wile",
                thumbnailResId = R.drawable.video4,
                price = "2,00€",
                duration = "45 min",
                views = "91 views"
            ),
            LearningContent.Workshop(
                name = "Hands on Survival skills workshop",
                author = "RIga Survivalist & Army school",
                thumbnailResId = R.drawable.workshop3,
                price = "54,50€",
                time = "Monday 27.6.2025, 14:00",
                location = "White Lake, Latvia",
                capacity = 12
            )
        )

        adapter = ContentAdapter(fullContentList) { content ->
            when (content) {
                is LearningContent.Video -> {
                    // Handle video click
                }
                is LearningContent.Workshop -> {
                    // Handle workshop click
                }
            }
        }

        findViewById<RecyclerView>(R.id.rvContent).apply {
            layoutManager = LinearLayoutManager(this@LearnHomeActivity)
            adapter = this@LearnHomeActivity.adapter
        }

        setupFilterButtons()
        setupSearch()

        val btnClear = findViewById<Button>(R.id.btnClear)
        val searchEditText = findViewById<EditText>(R.id.etSearch)  // if not already done

        btnClear.setOnClickListener {
            // 1. Clear search
            currentSearchQuery = ""
            searchEditText.setText("")

            // 2. Enable both filters
            val btnVideos = findViewById<MaterialButton>(R.id.btnFilterVideos)
            val btnWorkshops = findViewById<MaterialButton>(R.id.btnFilterWorkshops)

            btnVideos.isChecked = true
            btnWorkshops.isChecked = true

            // 3. Update styles and apply filter
            updateFilterButtonStyle(btnVideos, true)
            updateFilterButtonStyle(btnWorkshops, true)
            applyCombinedFilters()
        }

    }

    private fun updateFilterButtonStyle(button: MaterialButton, isChecked: Boolean) {
        val primaryColor = ContextCompat.getColor(this, R.color.learn_primary)
        val whiteColor = ContextCompat.getColor(this, R.color.white)
        val offColor = ContextCompat.getColor(this, R.color.learn_light)

        if (isChecked) {
            button.setBackgroundColor(primaryColor)
            button.setTextColor(whiteColor)
        } else {
            button.setBackgroundColor(offColor)
            button.setTextColor(primaryColor)
        }
    }

    private fun setupFilterButtons() {
        val btnVideos = findViewById<MaterialButton>(R.id.btnFilterVideos)
        val btnWorkshops = findViewById<MaterialButton>(R.id.btnFilterWorkshops)
        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.filterButtons)

        val applyFilter = {
            val showVideos = btnVideos.isChecked
            val showWorkshops = btnWorkshops.isChecked

            val filteredList = fullContentList.filter {
                (showVideos && it is LearningContent.Video) ||
                        (showWorkshops && it is LearningContent.Workshop)
            }

            adapter.updateList(filteredList)
        }

        toggleGroup.addOnButtonCheckedListener { _, _, _ ->
            updateFilterButtonStyle(btnVideos, btnVideos.isChecked)
            updateFilterButtonStyle(btnWorkshops, btnWorkshops.isChecked)
            applyCombinedFilters()
        }

        // Initial state (both selected)
        btnVideos.isChecked = true
        btnWorkshops.isChecked = false
        updateFilterButtonStyle(btnVideos, true)
        updateFilterButtonStyle(btnWorkshops, false)
    }

    private fun setupSearch() {
        val searchEditText = findViewById<EditText>(R.id.etSearch)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentSearchQuery = s.toString().trim()
                applyCombinedFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    private fun applyCombinedFilters() {
        val showVideos = findViewById<MaterialButton>(R.id.btnFilterVideos).isChecked
        val showWorkshops = findViewById<MaterialButton>(R.id.btnFilterWorkshops).isChecked
        val query = currentSearchQuery.lowercase()

        val filteredList = fullContentList.filter { content ->
            val matchesType = (showVideos && content is LearningContent.Video) ||
                    (showWorkshops && content is LearningContent.Workshop)
            val matchesQuery = content.name.lowercase().contains(query)
            matchesType && matchesQuery
        }

        adapter.updateList(filteredList)
    }

}