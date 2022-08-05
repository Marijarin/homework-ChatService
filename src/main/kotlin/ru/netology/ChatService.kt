package ru.netology

class ChatService {
    val allChats = mutableMapOf<Int, Chat>()


    fun addMessage(owner: Int, mate: Int, chat: Int, message: Message): List<Message>? {
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
        allChats[chat]?.messages?.plusAssign(message)

        return allChats[chat]?.messages?.takeLast(1)
    }

    fun editMessage(chat: Int, id: Int, messageText: String): Boolean {
        var edited = false
        allChats[chat]?.messages?.onEachIndexed { index, _ ->
            if (allChats[chat]?.messages?.get(index)?.id == id) {
                allChats[chat]?.messages?.get(index)?.text = messageText
                edited = true
            }
        }
        return edited
    }

    fun deleteMessage(owner: Int, chat: Int, messageId: Int): Message? {
        val neededMessage =
            allChats[chat]?.messages?.find { message: Message -> message.id == messageId && message.ownerId == owner }
        allChats[chat]?.messages?.forEach {
            if (it == neededMessage) {
                it.deleted = true
            }
        }
        return allChats[chat]?.messages?.find { message: Message -> message.id == messageId && message.ownerId == owner }
    }

    fun deleteChat(owner: Int, chat: Int): Boolean? {
        if (allChats[chat]?.ownerId == owner) {
            allChats[chat] = allChats[chat]!!.copy(deleted = true)
            allChats[chat]?.messages?.onEach { it.deleted = true }
        }
        return allChats[chat]?.deleted
    }

    fun getMessages(chat: Int, messageId: Int, howMany: Int): List<Message> {
        val fromChat = allChats[chat] ?: throw NoSuchChatException("no chat with $chat")
        val fromNeededMessage = fromChat.messages.slice(fromChat.messages.indexOfFirst {
            it.id == messageId
        } until fromChat.messages.size).filter { !it.deleted }
        if (howMany < fromNeededMessage.size) {
            return fromNeededMessage.onEach { it.read = true }
        } else {
            return fromNeededMessage.takeLast(howMany).onEach { it.read = true }
        }
    }

    fun getChats(owner: Int): String {

        val chatsWithAtLeastOneMsg = allChats.values.filter {
            it.ownerId == owner && it.messages.size >= 1 && it.messages.any { message -> !message.deleted }
        }

        if (chatsWithAtLeastOneMsg.isEmpty()) {
            return "User $owner has no chats with at least one message"
        }

        return chatsWithAtLeastOneMsg.toString()
    }

    fun getUnreadChatsCount(owner: Int): List<Chat> {
        return allChats.values.filter { it.ownerId == owner }.filter { !it.read && !it.deleted }
    }

}

class NoSuchChatException(message: String) : RuntimeException(message)








