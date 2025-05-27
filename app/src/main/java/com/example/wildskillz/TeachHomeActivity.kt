package com.example.wildskillz

import android.os.Bundle
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

class TeachHomeActivity : AppCompatActivity() {
    private lateinit var adapter: ContentAdapterTeach
    private lateinit var fullContentList: List<LearningContent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_teach_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra("username")

        val profileTextView = findViewById<TextView>(R.id.tvUsername)
        profileTextView.text = "$username"

        // sample data
        fullContentList = listOf(
            LearningContent.Workshop(
                name = "Weekend Foraging",
                author = "$username",
                thumbnailResId = R.drawable.workshop1,
                price = "FREE",
                time = "Saturday 18.6.2025, 15:45",
                location = "Forest Edge Park",
                capacity = 25
            ),
            LearningContent.Video(
                name = "How to build a Tent part 1",
                author = "$username",
                thumbnailResId = R.drawable.video5,
                price = "FREE",
                duration = "17 min",
                views = "722 views"
            ),
            LearningContent.Workshop(
                name = "Camping on Easter Night",
                author = "$username",
                thumbnailResId = R.drawable.workshop2,
                price = "13,00€",
                time = "Friday 18.6.2025, 15:45",
                location = "National Park, Riga",
                capacity = 9
            ),
            LearningContent.Video(
                name = "How to build a Tent part 2",
                author = "$username",
                thumbnailResId = R.drawable.video2,
                price = "FREE",
                duration = "12 min",
                views = "452 views"
            ),
            LearningContent.Video(
                name = "How to build a Tent part 3",
                author = "$username",
                thumbnailResId = R.drawable.video3,
                price = "FREE",
                duration = "11 min",
                views = "700 views"
            ),
            LearningContent.Video(
                name = "How to build a Tent part 4",
                author = "$username",
                thumbnailResId = R.drawable.video4,
                price = "2,00€",
                duration = "45 min",
                views = "91 views"
            )
        )

        adapter = ContentAdapterTeach(fullContentList) { content ->
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
            layoutManager = LinearLayoutManager(this@TeachHomeActivity)
            adapter = this@TeachHomeActivity.adapter
        }

        setupFilterButtons()
    }

    private fun updateFilterButtonStyle(button: MaterialButton, isChecked: Boolean) {
        val primaryColor = ContextCompat.getColor(this, R.color.teach_primary)
        val whiteColor = ContextCompat.getColor(this, R.color.white)
        val offColor = ContextCompat.getColor(this, R.color.teach_light)

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
            applyFilter()
        }

        // Initial state (both selected)
        btnVideos.isChecked = true
        btnWorkshops.isChecked = false
        updateFilterButtonStyle(btnVideos, true)
        updateFilterButtonStyle(btnWorkshops, false)
    }
}