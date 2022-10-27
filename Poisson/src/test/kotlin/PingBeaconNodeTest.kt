import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PingBeaconNodeTest {
    private val pbn = PingBeaconNode()
    @Test
    fun ping() {
        val genesisNode: String = pbn.ping("http://localhost:5051/")
        assertTrue(genesisNode != null)
        println(genesisNode)
    }

    @Test
    fun ping2() {
        val genesisNode: String = pbn.ping("")
        assertTrue(genesisNode != null)
        println(genesisNode)
    }
}