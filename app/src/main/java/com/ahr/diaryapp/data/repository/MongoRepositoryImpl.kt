package com.ahr.diaryapp.data.repository

import android.content.Context
import com.ahr.diaryapp.R
import com.ahr.diaryapp.model.Diary
import com.ahr.diaryapp.util.RequestState
import com.ahr.diaryapp.util.UserNotAuthenticateException
import com.ahr.diaryapp.util.toInstant
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import java.time.ZoneId

class MongoRepositoryImpl(private val context: Context) : MongoRepository {

    private val app get() = App.create(context.getString(R.string.app_id))
    private val user get() = app.currentUser
    private lateinit var realm: Realm

    init {
        configureRealm()
    }

    override fun configureRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(user = user!!, schema = setOf(Diary::class))
                .initialSubscriptions { sub ->
                    add(sub.query<Diary>(query = "ownerId == $0", user?.id), name = "user diary subscriptions")
                }
                .build()
            realm = Realm.open(config)
        }
    }

    override fun getAllDiaries(): Flow<DiariesResponse> {
        return if (user != null) {
            try {
                realm.query<Diary>(query = "ownerId == $0", user?.id)
                    .sort(property = "date", Sort.DESCENDING)
                    .asFlow()
                    .map { result ->
                        RequestState.Success(
                            result.list.groupBy {  diary ->
                                diary.date.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                        )
                    }
            } catch (exception: Exception) {
                flow { emit(RequestState.Error(exception)) }
            }
        } else {
            flow { emit(RequestState.Error(UserNotAuthenticateException())) }
        }
    }

    override fun getSelectedDiary(diaryId: ObjectId): RequestState<Diary> {
        return if (user != null) {
            try {
                val diary = realm.query<Diary>(query = "_id == $0", diaryId).find().first()
                RequestState.Success(diary)
            } catch (exception: Exception) {
                RequestState.Error(exception)
            }
        } else {
            RequestState.Error(UserNotAuthenticateException())
        }
    }

    override suspend fun insertNewDiary(diary: Diary): RequestState<Diary> {
        return if (user != null) {
            try {
                val diaryResult = realm.write {
                    copyToRealm(diary.apply {
                        ownerId = user!!.id
                    })
                }
                RequestState.Success(diaryResult)
            } catch (exception: Exception) {
                RequestState.Error(exception)
            }
        } else {
            RequestState.Error(UserNotAuthenticateException())
        }
    }
}
