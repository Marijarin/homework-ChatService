package ru.netology

class ChatService {
    val allChats = mutableMapOf<Int, Chat>()


    fun addMessage(owner: Int, mate: Int, chat: Int, message: Message): Message? {
        allChats.getOrPut(chat) {
            Chat(
                chatId = chat,
                ownerId = owner,
                mateId = mate,
                read = false,
                deleted = false,
                messages = mutableListOf<Message>()
            )
        }
            .messages.plusAssign(message)

        return allChats[chat]?.messages?.last()
    }

    fun editMessage(chat: Int, id: Int, messageText: String): Boolean? {
        val msg = allChats.getOrElse(chat) { throw NoSuchChatException("No chat with id $chat")}
            .messages
            .find { it.id == id } ?: throw NoSuchElementException ("No message with id $chat")
            msg.apply { text = messageText }
        return allChats
            .getOrElse(chat) { throw NoSuchChatException("No chat with id $chat")}
            .messages
            .any { it.id == id && it.text == messageText }
    }

    fun deleteMessage(owner: Int, chat: Int, messageId: Int): Boolean? {
           allChats[chat]?.messages
            ?.find { it.id == messageId && it.ownerId == owner }
            ?.apply { deleted = true }
        return allChats[chat]?.messages?.any { it.id == messageId && it.ownerId == owner && it.deleted }
    }

    fun deleteChat(owner: Int, chat: Int): Boolean {
        if (allChats[chat]?.ownerId == owner) {
            allChats[chat]?.let { allChats[chat] = it.copy(deleted = true) }
        } else throw NoSuchChatException("No chat by owner $owner and  id $chat")
        return allChats.containsValue(allChats[chat]?.copy(chatId = chat, ownerId = owner, deleted = true))
    }

    fun getMessages(chat: Int, messageId: Int, howMany: Int): List<Message> {
        val index1 = allChats
            .getOrElse(chat) { throw NoSuchChatException("No chat with id $chat")}
            .messages
            .indexOfFirst { it.id == messageId }
        val index2 =
            allChats
                .getOrElse(chat) { throw NoSuchChatException("No chat with id $chat")}
                .messages.size.minus(1)
        return if ((index1 + howMany) <= index2) {
            allChats
            .getOrElse(chat) { throw NoSuchChatException("No chat with id $chat")}.messages
                .slice(index1..(index1 + howMany))
                .onEach { it.read = true }
                .filter { !it.deleted }
        } else {
            allChats
                .getOrElse(chat) { throw NoSuchChatException("No chat with id $chat")}.messages
                .slice(index1..index2)
                .onEach { it.read = true }
                .filter { !it.deleted }
                .also {
                    println(
                        "Number of chosen messages is less than $howMany," +
                                "\n here they are:"
                    )
                }
        }


    }

    fun getChats(owner: Int): List<Chat> {

        return allChats.values
            .filter { it.ownerId == owner && !it.deleted }
            .filter {
                it.messages.size >= 1 && it.messages.any { message -> !message.deleted }}
            .map{chat: Chat -> chat.copy(messages = chat.messages.filter{!it.deleted}.toMutableList()) }
            }


    fun getUnreadChatsCount(owner: Int): List<Chat> {
        return allChats.values
            .filter { it.ownerId == owner }
            .filter { !it.read && !it.deleted }
            .map{chat: Chat -> chat.copy(messages = chat.messages.filter{!it.deleted}.toMutableList()) }
    }

}

class NoSuchChatException(message: String) : RuntimeException(message)








