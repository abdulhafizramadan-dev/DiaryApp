package com.ahr.diaryapp.data.repository

import com.ahr.diaryapp.model.Diary
import com.ahr.diaryapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

typealias DiariesResponse = RequestState<Map<LocalDate, List<Diary>>>

interface MongoRepository {
    fun configureRealm()
    fun getAllDiaries(): Flow<DiariesResponse>
}