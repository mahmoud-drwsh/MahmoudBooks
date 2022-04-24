package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.data.local.VolumeRoomDatabase
import com.mahmoud_darwish.data.remote.GoogleBooksApi
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.repository.IBooksRepository
import com.mahmoud_darwish.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    val service: GoogleBooksApi,
    val local: VolumeRoomDatabase
) : IBooksRepository {
    override fun searchByTitle(title: String): Flow<Resource<List<Volume>>> = flow {

    }

    override fun getVolumeById(id: String): Flow<Resource<Volume>> = flow {

    }
}