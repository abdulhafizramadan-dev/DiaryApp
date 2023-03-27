package com.ahr.diaryapp.data.repository

import com.ahr.diaryapp.model.Diary
import com.ahr.diaryapp.util.RequestState
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

typealias DiariesResponse = RequestState<Map<LocalDate, List<Diary>>>

interface MongoRepository {
    fun configureRealm()
    fun getAllDiaries(): Flow<DiariesResponse>
    fun getSelectedDiary(diaryId: ObjectId): RequestState<Diary>
    suspend fun insertNewDiary(diary: Diary): RequestState<Diary>
}