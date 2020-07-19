package com.example.stackviewdemo

import com.example.stackviewdemo.model.ViewStack
import com.example.stackviewdemo.utils.cashify
import com.example.stackviewdemo.utils.toRounderAmount
import com.example.stackviewdemo.view.MockStackView
import org.junit.Assert.*
import org.junit.Test

class NumUtilTest {
    @Test
    fun testCashify() {
        assertEquals("500", 500.cashify())
        assertEquals("5,000", 5000.cashify())
        assertEquals("5,00,000", 500000.cashify())
        assertEquals("5,00,00,000", 50000000.cashify())
        assertEquals("50,00,00,000", 500000000.cashify())
        assertEquals("0", 0.cashify())
        assertEquals("1", 1.cashify())
        assertEquals("999", 999.cashify())
        assertEquals("1,000", 1000.cashify())
        assertEquals("99,999", 99999.cashify())
        assertEquals("1,00,000", 100000.cashify())
        assertEquals("99,99,999", 9999999.cashify())
        assertEquals("1,00,00,000", 10000000.cashify())
    }

    @Test
    fun testToRoundAmount(){
        assertEquals(0, 0.toRounderAmount())
        assertEquals(0, 1.toRounderAmount())
        assertEquals(0, 9.toRounderAmount())
        assertEquals(0, 10.toRounderAmount())
        assertEquals(0, 99.toRounderAmount())
        assertEquals(100, 100.toRounderAmount())
        assertEquals(900, 999.toRounderAmount())
        assertEquals(1000, 1000.toRounderAmount())
    }

    @Test
    fun testStack() {
        val viewStack: ViewStack = ViewStack()
        asserStackIsEmpty(viewStack)

        val mockView = MockStackView(1)
        viewStack.push(mockView)
        assertEquals(1, viewStack.size())
        assertFalse(viewStack.isEmpty())
        assertEquals(mockView, viewStack.peek())
        assertEquals(mockView, viewStack.pop())
        asserStackIsEmpty(viewStack)


        for(i in 1 .. 5) {
            viewStack.push(MockStackView(i))
        }
        assertEquals(5, viewStack.size())
        assertFalse(viewStack.isEmpty())

        for(i in 5 downTo 1) {
            val view = viewStack.pop()
            assertTrue(view is MockStackView)
            assertEquals(i, (view as MockStackView).getMockTag())
        }
        asserStackIsEmpty(viewStack)
    }

    private fun asserStackIsEmpty(viewStack: ViewStack) {
        assertEquals(0, viewStack.size())
        assertTrue(viewStack.isEmpty())

        try {
            viewStack.pop()
            fail("Pop should throw an exception")
        } catch (e: Exception) {
            assertTrue(e is ArrayIndexOutOfBoundsException)
        }
    }
}