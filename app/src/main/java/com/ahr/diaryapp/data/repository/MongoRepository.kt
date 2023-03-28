package com.ahr.diaryapp.data.repository

import com.ahr.diaryapp.model.Diary
import com.ahr.diaryapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

typealias DiariesResponse = RequestState<Map<LocalDate, List<Diary>>>
typealias DiaryResponse = RequestState<Diary>

interface MongoRepository {
    fun configureRealm()
    fun getAllDiaries(): Flow<DiariesResponse>
    fun getSelectedDiary(diaryId: ObjectId): Flow<DiaryResponse>
    suspend fun insertDiary(diary: Diary): DiaryResponse
    suspend fun updateUpdate(diary: Diary): DiaryResponse
}