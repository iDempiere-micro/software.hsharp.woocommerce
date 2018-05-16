package software.hsharp.woocommerce

interface IOrder {
    val id : Int
    val number : String
    val status : String
    val currency : String
    val total : String
}