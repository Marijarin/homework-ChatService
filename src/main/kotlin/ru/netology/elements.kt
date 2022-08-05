package ru.netology

import java.time.LocalDateTime


data class Message(
    val id: Int,
    val chatId: Int,
    val ownerId: Int,
    val mateId: Int,
    val date: Int = LocalDateTime.now().nano,
    var read: Boolean,
    var deleted: Boolean,
    var text: String
)


data class Chat(
    val chatId: Int,
    val ownerId: Int,
    val mateId: Int,
    val date: Int = LocalDateTime.now().nano,
    val read: Boolean,
    var deleted: Boolean,
    val messages: MutableList<Message>
)