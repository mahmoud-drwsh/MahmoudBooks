package com.mahmoud_darwish.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This will represent an entry in the favorites table which will contain just one column containing the ID of the book.
 * An Inner join is used to get the favorite books with the ID saved in the favorites table.
 * */
@Entity
data class Favorite(
    @PrimaryKey val id: String
)