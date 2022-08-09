package ru.netology

import org.junit.Assert.*
import org.junit.Test


internal class ChatServiceTest {

    @Test
    fun addMessageIfFirst() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
        )
        val resulted = service.addMessage(1, 2, 1, message11)
        var result = false
        if (resulted.toString().contains(" First message from 1 to 2", false)) {
            result = true
        }
        assertTrue(result)
    }

    @Test
    fun addMessageIfNotFirst() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
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
        val result1 = service.addMessage(1, 2, 1, message11)
        val result2 = service.addMessage(1, 2, 1, message32)
        var resulted = false
        if (result1.toString().contains(" First message from 1 to 2", false) &&
            result2.toString().contains(" First reply from 1 to 2, chat 2", false)
        ) {
            resulted = true
        }
        assertTrue(resulted)
    }

    @Test
    fun editMessageExisting() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"

        )
        service.addMessage(1, 2, 1, message11)
        val result = service.editMessage(1, 11, "Edited")
        assertTrue(result?:true)
    }

    @Test(expected = NoSuchElementException ::class)
    fun editMessageNonExisting() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
        )
        service.addMessage(1, 2, 1, message11)
        val result = service.editMessage(1, 111, "Edited")
        assertFalse(result?:true)
    }

    @Test
    fun deleteMessageExisting() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
        )
        service.addMessage(1, 2, 1, message11)
        val result = service.deleteMessage(1, 1, 11)
        assertTrue(result?:true)
    }

    @Test
    fun deleteMessageNonExisting() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
        )
        service.addMessage(1, 2, 1, message11)
        val result = service.deleteMessage(2, 2, 11)
        var resulted = false
        if (result.toString().contains("deleted=true", false)) {
            resulted = true
        }
        assertFalse(resulted)
    }


    @Test
    fun deleteChatExisting() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
        )
        service.addMessage(1, 2, 1, message11)
        val result = service.deleteChat(1, 1)
        assertTrue(result)
    }
    @Test(expected = NoSuchChatException::class)
    fun deleteChatNonExisting() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
        )
        service.addMessage(1, 2, 1, message11)
        val result = service.deleteChat(1, 4)
        assertTrue(result)

    }

    @Test
    fun getMessagesFromExistingChat() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
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
        service.addMessage(1, 2, 1, message11)
        service.addMessage(1, 2, 1, message32)
        val result = service.getMessages(1, 32, 2)
        var resulted = false
        if (result.toString().contains("read=true", false)) {
            resulted = true
        }
        assertTrue(resulted)

    }

    @Test(expected = NoSuchChatException::class)
    fun getMessagesFromNonExistingChat() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
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
        service.addMessage(1, 2, 1, message11)
        service.addMessage(1, 2, 1, message32)
        val result = service.getMessages(4, 32, 2)
        var resulted = false
        if (result.toString().contains("read=true", false)) {
            resulted = true
        }
        assertFalse(resulted)
    }
    @Test
    fun getChatsWithAtLeastOneMessage() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
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
        service.addMessage(1, 2, 1, message11)
        service.addMessage(1, 2, 1, message32)
        val result = service.getChats(1)
        var resulted = false
        if (result.toString().contains(" First reply from 1 to 2, chat 2", false)) {
            resulted = true
        }
        assertTrue(resulted)

    }
    @Test
    fun getChatsWithNoMessages() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
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
        service.addMessage(1, 2, 1, message11)
        service.addMessage(1, 2, 1, message32)
        val result = service.getChats(2)
        var resulted = false
        if (result.toString().contains("has no chats with at least one message", false)) {
            resulted = true
        }
        assertFalse(resulted)
    }

    @Test
    fun getUnreadChatsCountExisting() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
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
        service.addMessage(1, 2, 1, message11)
        service.addMessage(1, 2, 1, message32)
        val result = service.getUnreadChatsCount(1)
        var resulted = false
        if (result.toString().contains(" First reply from 1 to 2, chat 2", false)) {
            resulted = true
        }
        assertTrue(resulted)
    }
    @Test
    fun getUnreadChatsCountNonExisting() {
        val service = ChatService()
        val message11 = Message(
            id = 11,
            chatId = 1,
            ownerId = 1,
            mateId = 2,
            read = false,
            deleted = false,
            text = " First message from 1 to 2"
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
        service.addMessage(1, 2, 1, message11)
        service.addMessage(1, 2, 1, message32)
        val result = service.getUnreadChatsCount(2)
        var resulted = false
        if (result.toString().contains(" First reply from 1 to 2, chat 2", false)) {
            resulted = true
        }
        assertFalse(resulted)
    }

}