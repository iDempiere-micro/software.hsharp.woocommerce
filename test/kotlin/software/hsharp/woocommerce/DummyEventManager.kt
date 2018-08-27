package software.hsharp.woocommerce

import org.osgi.service.event.EventHandler

class DummyEventManager : org.idempiere.orm.EventManager() {
    override fun unregister(eventHandler: EventHandler?): Boolean {
        return true
    }

    override fun register(topics: Array<out String>?, filter: String?, eventHandler: EventHandler?): Boolean {
        return true
    }

    companion object {
        fun setup() {
            org.idempiere.orm.EventManager.instance = DummyEventManager()
        }
    }
}