package com.ttulka.ecommerce.billing.payment.rest

import com.ttulka.ecommerce.billing.payment.*
import com.ttulka.ecommerce.common.primitives.Money
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(PaymentController::class)
internal class PaymentControllerTest(
        @Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var findPayments: FindPayments

    @Test
    fun all_payments() {
        Mockito.`when`(findPayments.all()).thenReturn(
                testPayments(PaymentId("TEST123"), ReferenceId("TEST-REF1"), Money(123.5f)))

        mockMvc.perform(get("/payment"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", Matchers.hasSize<Any>(1)))
                .andExpect(jsonPath("$[0].id", Matchers.`is`("TEST123")))
                .andExpect(jsonPath("$[0].referenceId", Matchers.`is`("TEST-REF1")))
                .andExpect(jsonPath("$[0].total", Matchers.`is`(123.5)))
    }

    private fun testPayments(id: PaymentId, referenceId: ReferenceId, total: Money): Payments {
        return object : Payments {
            override fun range(start: Int, limit: Int): Payments {
                return this
            }

            override fun range(limit: Int): Payments {
                return this
            }

            override fun iterator(): Iterator<Payment> {
                return listOf(object : Payment {
                    override fun id(): PaymentId {
                        return id
                    }

                    override fun referenceId(): ReferenceId {
                        return referenceId
                    }

                    override fun total(): Money {
                        return total
                    }

                    override fun request() {}
                    override fun collect() {}
                    override val isRequested: Boolean
                        get() = false

                    override val isCollected: Boolean
                        get() = false
                }).iterator()
            }
        }
    }
}
