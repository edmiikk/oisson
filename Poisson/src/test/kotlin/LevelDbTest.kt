import org.iq80.leveldb.DB
import org.iq80.leveldb.DBException
import org.iq80.leveldb.Options
import org.iq80.leveldb.impl.Iq80DBFactory.bytes
import org.iq80.leveldb.impl.Iq80DBFactory.factory
import java.io.File
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class LevelDbTest {



    @Test
    fun createDB() {
        val dbPath = "./leveldb/poissonTest"
        val options = Options().createIfMissing(true)
        var ldb: DB
        try {
            ldb = factory.open(File(dbPath), options)
            ldb.close()
        } catch (e: DBException) {
            println(e.message)
            // if LOCK is there destroy
        }
        ldb = factory.open(File(dbPath), options)
        ldb.use {
            var value = ldb.get(bytes("ping"))
            if (value != null && value.isNotEmpty()) {
                ldb.delete(bytes("ping"))
            }
            ldb.put(bytes("ping"), bytes("ping"))
            value = ldb.get(bytes("ping"))
            assertTrue(value.isNotEmpty())
            assertEquals("ping", String(value))
            try {
                value = ldb.get(bytes("xyzzy"))
                if (value == null) {
                    println("No xyzzy key")
                } else {
                    println("xyzzy is " + String(value))
                }
            } catch (e: DBException) {
                println(e.message)
                if (e.message!!.contains("does not exist")) {
                    println("Missing key xyzzy!")
                }
            }
        }
    }

    @Test
    fun intHandling() {
        val ldb = factory.open(File("./leveldb/poissonTest"), Options().createIfMissing(true))
        ldb.use {
            var valCount: Long = 68L;
            val valCountStore = BigInteger.valueOf(valCount).toByteArray()
            val valCountKey = "bucketT000001"  // T for test
            valCount = try {
                val value = ldb.get(bytes(valCountKey))
                if (value == null) {
                    valCount
                } else {
                    BigInteger(value).toLong() + 1 // woops there goes another one
                }
            } catch (e: DBException) {
                println(e.message)
                if (e.message!!.contains("does not exist")) {
                    println("Missing key $valCountKey!")
                }
                valCount
            }
            ldb.put(bytes(valCountKey), BigInteger.valueOf(valCount).toByteArray())
            assertEquals(69L, valCount)
        }
    }

}
