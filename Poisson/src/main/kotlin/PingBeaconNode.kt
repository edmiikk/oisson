import org.web3j.eth2.api.BeaconNodeApi
import org.web3j.eth2.api.BeaconNodeClientFactory
import org.web3j.eth2.api.BeaconNodeService
import org.web3j.eth2.api.beacon.BeaconResource
import org.web3j.eth2.api.schema.BeaconResponse
import org.web3j.eth2.api.schema.Genesis
import org.web3j.eth2.api.schema.NamedBlockId
import org.web3j.eth2.api.validator.BlockResource

class PingBeaconNode {
    fun ping(url: String): String {
        // test the beacon node APIa
        val service = BeaconNodeService(url)
        val client: BeaconNodeApi = BeaconNodeClientFactory.build(service)
        val beacon: BeaconResource = client.beacon
        //var blocks: BlockResource = beacon.blocks
        //blocks.
            //.getBlocks().findById(NamedBlockId.HEAD);
        // try the genesis
        val genesis: BeaconResponse<Genesis> = beacon.genesis
        return genesis.toString()
    }
}