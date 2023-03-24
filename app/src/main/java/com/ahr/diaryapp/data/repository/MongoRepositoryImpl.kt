package com.ahr.diaryapp.data.repository

import com.ahr.diaryapp.model.Diary
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration

object Mongo : MongoRepository {

    private val app get() = App.create("")
    private val user get() = app.currentUser
    private lateinit var realm: Realm

    override fun configureRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(user = user!!, schema = setOf(Diary::class))
                .initialSubscriptions { sub ->
                    add(sub.query(query = "owner_id == $0", user!!.id))
                }
        }
    }
}