import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.web3j.eth2.api.BeaconNodeApi
import org.web3j.eth2.api.BeaconNodeClientFactory
import org.web3j.eth2.api.BeaconNodeService
import org.web3j.eth2.api.beacon.BeaconResource
import org.web3j.eth2.api.schema.Attestation
import org.web3j.eth2.api.schema.Deposit
import org.web3j.eth2.api.schema.Root

class BlockTest {

    internal class PingBeaconNodeTest {

        @Test
        fun ping() {
            val service = BeaconNodeService("http://localhost:5051/")
            val client: BeaconNodeApi = BeaconNodeClientFactory.build(service)
            val beacon: BeaconResource = client.beacon
            val node = Node()
            println(node.blockString(beacon,"75856"))
            val sbb = node.sBBlock(beacon,"0")
            println(sbb.toString())
            val bb = sbb.message
            val bbBody = bb.body
            val ats: List<Attestation> = bbBody.attestations

            for (att in ats) {
                val attData = att.data
                val root: Root = attData.beaconBlockRoot

            }
            val deps: List<Deposit> = bbBody.deposits
            for (dep in deps) {
                val depData = dep.data
            }
        }
    }
}