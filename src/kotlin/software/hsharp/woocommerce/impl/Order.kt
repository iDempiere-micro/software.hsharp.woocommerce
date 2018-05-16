package software.hsharp.woocommerce.impl

import software.hsharp.woocommerce.IOrder

data class Order(val map: Map<String, Any?>) : IOrder
{
    override val id : Int by map
    override val number : String by map
    override val status : String by map
    override val currency : String by map
    override val total : String by map

    constructor(
            id : Int,
            number : String,
            status : String,
            currency : String,
            total : String
    ) : this(
            mapOf(
                    "id" to id,
                    "number" to number,
                    "status" to status,
                    "currency" to currency,
                    "total" to total
            )
    )
}
