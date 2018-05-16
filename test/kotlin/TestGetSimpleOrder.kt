import org.junit.Test
import software.hsharp.woocommerce.IOrder
import software.hsharp.woocommerce.WooCommerceAPI
import software.hsharp.woocommerce.impl.*

class TestGetSimpleOrder {
    @Test
    fun getSimpleOrder() {
        val config = Config("https://testsite.cz/", "ck_XXX", "cs_YYY")
        val wooCommerce = WooCommerceAPI(config, ApiVersionType.V2)
        val order : IOrder = wooCommerce.getOrder(1290)
        println("Order:$order")
    }
    @Test
    fun getAllOrders() {
        val config = Config("https://testsite.cz/", "ck_XXX", "cs_YYY")
        val wooCommerce = WooCommerceAPI(config, ApiVersionType.V2)
        val orders : Array<IOrder> = wooCommerce.getOrders()
        orders.forEach { println("Order:$it") }
    }
}
