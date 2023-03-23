package com.ahr.diaryapp.model

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PersistedName
import org.mongodb.kbson.ObjectId


class Diary : RealmObject {
    @PersistedName("_id")
    var id: ObjectId = ObjectId.invoke()
    var ownerId: String = ""
    var mood: String = Mood.Neutral.name
    var title: String = ""
    var description: String = ""
    var images: String = ""
    var date: RealmInstant = RealmInstant.now()
}