import org.compiere.order.MOrder
import org.compiere.order.X_I_Order
import org.compiere.process.ProcessInfo
import org.idempiere.common.db.CConnection
import org.idempiere.common.db.Database
import org.idempiere.common.util.CLogger
import org.idempiere.common.util.DB
import org.idempiere.common.util.Env
import org.idempiere.common.util.Ini
import org.idempiere.process.ImportOrder
import org.junit.Assert
import org.junit.Test
import pg.org.compiere.db.DB_PostgreSQL
import software.hsharp.woocommerce.IOrder
import software.hsharp.woocommerce.WooCommerceAPI
import software.hsharp.woocommerce.impl.*
import org.compiere.process.ProcessInfoParameter
import org.idempiere.common.util.Env.getAD_User_ID
import org.idempiere.common.util.Env.getAD_Client_ID
import software.hsharp.woocommerce.IProduct


class TestGetSimpleOrder {
    @Test
    fun getSimpleOrder() {
        val config = Secrets()
        val wooCommerce = WooCommerceAPI(config, ApiVersionType.V2)
        val order : IOrder = wooCommerce.getOrder(1290)
        println("Order:$order")
    }
    @Test
    fun getAllOrders() {
        val config = Secrets()
        val wooCommerce = WooCommerceAPI(config, ApiVersionType.V2)
        val orders : Array<IOrder> = wooCommerce.getOrders()
        orders.forEach { println("Order:$it") }
    }
    @Test
    fun getAllProducts() {
        val config = Secrets()
        val wooCommerce = WooCommerceAPI(config, ApiVersionType.V2)
        val products: Array<IProduct> = wooCommerce.getProducts()
        products.forEach { println("Product:$it") }
    }
    @Test
    fun createNewOrder() {
        Ini.getIni().isClient = false
        CLogger.getCLogger(TestGetSimpleOrder::class.java)
        Ini.getIni().properties
        val db = Database()
        db.setDatabase(DB_PostgreSQL())
        DB.setDBTarget(CConnection.get(null))
        DB.isConnected()

        val ctx = Env.getCtx()
        val AD_CLIENT_ID = 11
        val AD_CLIENT_ID_s = AD_CLIENT_ID.toString()
        ctx.setProperty(Env.AD_CLIENT_ID, AD_CLIENT_ID_s )
        Env.setContext(ctx, Env.AD_CLIENT_ID, AD_CLIENT_ID_s )

        val newOrder = MOrder( ctx, 0, null )
        newOrder.aD_Org_ID = 11
        newOrder.m_Warehouse_ID = 50000
        newOrder.c_BPartner_ID = 114
        newOrder.save()
        val id = newOrder._ID
        println( "id:${id}" )
        Assert.assertTrue( id > 0 )
    }

    @Test
    fun createNewImportOrderAndProcess() {
        println( org.compiere.impl.X_C_BPartner::class.java )

        Ini.getIni().isClient = false
        CLogger.getCLogger(TestGetSimpleOrder::class.java)
        Ini.getIni().properties
        val db = Database()
        db.setDatabase(DB_PostgreSQL())
        DB.setDBTarget(CConnection.get(null))
        DB.isConnected()

        val ctx = Env.getCtx()
        val AD_CLIENT_ID = 11
        val AD_CLIENT_ID_s = AD_CLIENT_ID.toString()
        ctx.setProperty(Env.AD_CLIENT_ID, AD_CLIENT_ID_s )
        Env.setContext(ctx, Env.AD_CLIENT_ID, AD_CLIENT_ID_s )
        val AD_ORG_ID = 11
        val AD_ORG_ID_s = AD_ORG_ID.toString()
        ctx.setProperty(Env.AD_ORG_ID, AD_ORG_ID_s )
        Env.setContext(ctx, Env.AD_ORG_ID, AD_ORG_ID_s )

        val newOrder = X_I_Order( ctx, 0, null )
        newOrder.bPartnerValue = "TreeFarm"
        newOrder.productValue = "Oak"
        newOrder.qtyOrdered = 1.toBigDecimal()
        newOrder.docTypeName = "Standard Order"
        newOrder.c_Currency_ID = 100 // 100 = USD, 148= CZK
        newOrder.aD_Org_ID = AD_ORG_ID
        newOrder.setIsSOTrx( true )
        newOrder.salesRep_ID = 103
        newOrder.save()
        val id = newOrder._ID
        println( "id:${id}" )
        Assert.assertTrue( id > 0 )

        val importOrder = ImportOrder()
        val pinfo = ProcessInfo("Import Test Order", 206);

        val parameters : Array<ProcessInfoParameter> = arrayOf(
                ProcessInfoParameter( "AD_Client_ID", AD_CLIENT_ID.toBigDecimal(), null, null, null ),
                ProcessInfoParameter( "AD_Org_ID", AD_ORG_ID.toBigDecimal(), null, null, null )
        )

        pinfo.aD_Client_ID = AD_CLIENT_ID
        pinfo.parameter = parameters

        importOrder.startProcess(ctx, pinfo, null)

        println( "pinfo:$pinfo" )
    }

}