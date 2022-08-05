package ru.netology


fun main() {

    val message11 = Message(
        id = 11,
        chatId = 1,
        ownerId = 1,
        mateId = 2,
        read = false,
        deleted = false,
        text = " First message from 1 to 2"
    )
    val message21 = Message(
        id = 21,
        chatId = 1,
        ownerId = 1,
        mateId = 2,
        read = false,
        deleted = false,
        text = " Second message from 1 to 2"
    )
    val message31 = Message(
        id = 31,
        chatId = 1,
        ownerId = 2,
        mateId = 1,
        read = false,
        deleted = false,
        text = " First reply from 2 to 1"
    )
    val message41 = Message(
        id = 41,
        chatId = 1,
        ownerId = 1,
        mateId = 2,
        read = false,
        deleted = false,
        text = " Second message from 2 to 1"
    )
    val message12 = Message(
        id = 12,
        chatId = 2,
        ownerId = 2,
        mateId = 1,
        read = false,
        deleted = false,
        text = " First message from 2 to 1, chat 2"
    )
    val message22 = Message(
        id = 22,
        chatId = 2,
        ownerId = 2,
        mateId = 1,
        read = false,
        deleted = false,
        text = " Second message from 2 to 1, chat 2"
    )
    val message32 = Message(
        id = 32,
        chatId = 2,
        ownerId = 1,
        mateId = 2,
        read = false,
        deleted = false,
        text = " First reply from 1 to 2, chat 2"
    )
    val message42 = Message(
        id = 42,
        chatId = 2,
        ownerId = 1,
        mateId = 2,
        read = false,
        deleted = false,
        text = " Second reply from 1 to 2, chat 2"
    )

    val service = ChatService()

    println(service.addMessage(1, 2, 1, message11))
    println(service.addMessage(1, 2, 1, message21))
    println(service.addMessage(2, 1, 1, message31))
    println(service.addMessage(2, 1, 1, message41))

    println(service.addMessage(2, 1, 2, message12))
    println(service.addMessage(2, 1, 2, message22))
    println(service.addMessage(1, 2, 2, message32))
    println(service.addMessage(1, 2, 2, message42))

    println(service.editMessage(1, 11, "Edited"))
    println(service.editMessage(1, 111, "Edited"))
    println(service.deleteMessage(2, 2, 12))
    println(service.deleteMessage(1, 1, 21))
    println(service.deleteChat(2, 2))
    println(service.getChats(1))
    println(service.getChats(2))
    println(service.getMessages(1, 21, 2))
    println(service.getUnreadChatsCount(1))
    println(service.getUnreadChatsCount(2))


}