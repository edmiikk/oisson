import org.junit.jupiter.api.Test
import org.web3j.eth2.api.BeaconNodeApi
import org.web3j.eth2.api.BeaconNodeClientFactory
import org.web3j.eth2.api.BeaconNodeService
import org.web3j.eth2.api.beacon.BeaconResource
import org.web3j.eth2.api.beacon.blocks.BlocksResource
import org.web3j.eth2.api.schema.*
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountProposalsTest {
    @Test
    fun countProposals() {
        val service = BeaconNodeService("http://localhost:5051/")
        val client: BeaconNodeApi = BeaconNodeClientFactory.build(service)
        val beacon: BeaconResource = client.beacon
        val node = Node()
        val bsr: BlocksResource = beacon.blocks
        // walk the chain blocks and count proposals for a given
        // 7200 blocks a day * 30 = 216000 blocks
        // every 30 days chances of a block reward , adjusted by total number of active validators
        // for that 30 days period at day 15.
        // active validators
        // 1. track highest validator attesting a block

        // try first 30 days
        // build a histogram of block proposers
        // for each calculate the probability of a near proposal
        val proposers: HashMap<Uint64,Uint64>
        val thirtyDayCount: UInt = 216000u
        var blockCount: UInt = 1u // genesis does not count
        var maxValidatorProposer = 0u
        var validatorDepositCount = 0u
        var validatorExitCount = 0u
        while (blockCount < thirtyDayCount) {
            //val aBlock: BeaconResponse<SignedBeaconBlock>  = bsr.findById(blockCount.toString()) // should be the slot
            if (blockCount == 1u) {
                // test the root of block 1
                val block1 = bsr.findById("0x4a089c5e390bb98e66b27358f157df825128ea953cee9d191229c0bcf423a4f6")
                assertTrue ( block1.data.signature.equals("0x8c47b38f346c9bea9f84c4f68815b2d83db39f6eb1dde99e77bedd95b06dd027c10b0ccfb0f4055332609563df512a87062bc66b3626ae63d53af09315a2bb287346d956ec7bba5eef2841c49bf8043925acbdf61cbdc053d3237ef3e3ea3bec") )
                // assume that stateRoot is the same as Block Root in beaconcha.in explorer
                //assertEquals("0x7e0477efe0f6d59d6609df9457de1feda041832845f1f02d63c1df471e4ebcbd", aBlock.data.message.stateRoot)
            }
            var block : SignedBeaconBlock =
            try {
                bsr.findById("$blockCount").data
            }
            catch (e: Exception) {
                println("Cannot find block $blockCount : $e.message")
                // construct a dummy SignedBeaconBlock
                SignedBeaconBlock(BeaconBlock("empty","","","",
                    BeaconBlockBody("", Eth1Data("","",""),"graffiti",
                        List(0) {
                            return@List ProposerSlashing(
                                SignedBeaconBlockHeader(BeaconBlockHeader("","","","",""),"sig"),
                                SignedBeaconBlockHeader(BeaconBlockHeader("","","","",""),"sig"))
                                },
                        List(0){AttesterSlashing(
                            IndexedAttestation(List(0){""},AttestationData("","","",Checkpoint("",""),Checkpoint("","")),""),
                            IndexedAttestation(List(0){""},AttestationData("","","",Checkpoint("",""),Checkpoint("","")),"")
                        )},
                        List(0){Attestation("","",AttestationData("","","",Checkpoint("",""),Checkpoint("","")))},
                        List(0){
                            Deposit(List(0){""},DepositData("","","",""))
                        },
                        List(0){SignedVoluntaryExit(VoluntaryExit("",""),"")})
                    ),
                "empty")
            }
            if (!block.signature.equals("empty")) {
                val vi = block.message.proposerIndex
                val viUInt = vi.toUInt()
                if (viUInt > maxValidatorProposer) {
                    maxValidatorProposer = viUInt
                    println("maxValidatorProposer is $maxValidatorProposer")
                }
                //val body = block.data.message.body
                // look at attestations
                //for (att in body.attestations) {
                //    val committeeIndex = att.data.index.toUInt()
                //    val bbr = att.data.beaconBlockRoot
                //}
                    /*
                for (deposit in body.deposits) {
                    val data = deposit.data
                }
                for (attSlash in body.attesterSlashings) {
                    attSlash.attestation1.attestingIndices
                }
                for (propSlash in body.proposerSlashings) {
                    propSlash
                }
                     */
                //val slot = block.data.message.slot.toUInt() // same as block count
            }
            blockCount++
        }
        println("maxValidatorProposer is $maxValidatorProposer")
        //println("maxValidatorAttester is $maxValidatorAttester")
    }

    private fun getValBlockKey(blockNumber: UInt): String {
        return "valblock$blockNumber"
    }

}