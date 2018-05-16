import org.junit.Test
import software.hsharp.woocommerce.IOrder
import software.hsharp.woocommerce.WooCommerceAPI
import software.hsharp.woocommerce.impl.*

class TestGetSimpleOrder {
    @Test
    fun getSimpleOrder() {
        val config = Config("https://vinokutnahora.cz/", "ck_0e580f4ff4b5fc394a8b7774ac0a02b67f6ea8f5", "cs_167a4e956e98ddc577e372812998c02db9b383c3")
        val wooCommerce = WooCommerceAPI(config, ApiVersionType.V2)
        val order : IOrder = wooCommerce.getOrder(1290)
        println("Order:$order")
    }
    @Test
    fun getAllOrders() {
        val config = Config("https://vinokutnahora.cz/", "ck_0e580f4ff4b5fc394a8b7774ac0a02b67f6ea8f5", "cs_167a4e956e98ddc577e372812998c02db9b383c3")
        val wooCommerce = WooCommerceAPI(config, ApiVersionType.V2)
        val orders : Array<IOrder> = wooCommerce.getOrders()
        orders.forEach { println("Order:$it") }
    }
}