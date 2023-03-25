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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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
                    add(sub.query<Diary>(query = "ownerId == $0", user?.id))
                }
                .build()
            realm = Realm.open(config)
        }
    }

    override fun getAllDiaries(): Flow<DiariesResponse> = flow {
        emit(RequestState.Loading)
        if (user != null) {
            val diariesResponse = realm.query<Diary>(query = "ownerId == $0", user?.id)
                .sort(property = "date", Sort.DESCENDING)
                .find()
                .groupBy {  diary ->
                    diary.date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                }
            emit(RequestState.Success(diariesResponse))
        } else {
            emit(RequestState.Error(UserNotAuthenticateException()))
        }
    }.catch { exception ->
        emit(RequestState.Error(exception))
    }
}
