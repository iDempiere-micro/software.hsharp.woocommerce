package software.hsharp.woocommerce

import software.hsharp.woocommerce.impl.*

class WooCommerceAPI(config : IConfig, apiVersion : ApiVersionType) : IWooCommerce, WooCommerceBase(config, apiVersion) {
    override fun getOrder(id: Int): IOrder {
        val order : SingleOrder =
                get<SingleOrder>(
                        EndpointBaseType.ORDERS.value,
                        id,
                        {it as SingleOrder }
                )
        return order
    }

    override fun getOrders(): Array<IOrder> {
        val orders : Array<IOrder> =
                getAll(
                        EndpointBaseType.ORDERS.value,
                        mapOf(),
                        { MappedOrder(it as Map<String, Any?>) }
                ).map { it as IOrder }.toTypedArray()
        return orders
    }

}