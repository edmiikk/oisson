import org.web3j.eth2.api.beacon.BeaconResource
import org.web3j.eth2.api.beacon.blocks.BlocksResource
import org.web3j.eth2.api.schema.BeaconResponse
import org.web3j.eth2.api.schema.SignedBeaconBlock

class Node {
    fun blockString(beacon: BeaconResource, blockId: String): String {
        val bsr: BlocksResource = beacon.blocks
        val sbb: BeaconResponse<SignedBeaconBlock> = bsr.findById(blockId)
        return sbb.toString()
    }

    fun sBBlock(beacon: BeaconResource, blockId: String): SignedBeaconBlock{
        val bsr: BlocksResource = beacon.blocks
        val sbb: BeaconResponse<SignedBeaconBlock> = bsr.findById(blockId)
        return sbb.data
    }
}