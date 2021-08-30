package kittoku.mvc.unit.arp

import kittoku.mvc.debug.assertAlways
import kittoku.mvc.unit.DataUnit
import kittoku.mvc.unit.ethernet.ETHER_TYPE_IPv4
import java.nio.ByteBuffer


internal class ARPPacket : DataUnit {
    internal var opcode: Short = 0

    internal val senderMac = ByteArray(6)
    internal val senderIp = ByteArray(4)
    internal val targetMac = ByteArray(6)
    internal val targetIp = ByteArray(4)

    override val length = 28

    override fun write(buffer: ByteBuffer) {
        buffer.putShort(ARP_HARDWARE_TYPE_ETHERNET)
        buffer.putShort(ETHER_TYPE_IPv4)
        buffer.put(ARP_MAC_ADDRESS_SIZE)
        buffer.put(ARP_IP_ADDRESS_SIZE)
        buffer.putShort(opcode)

        buffer.put(senderMac)
        buffer.put(senderIp)
        buffer.put(targetMac)
        buffer.put(targetIp)
    }

    override fun read(buffer: ByteBuffer) {
        assertAlways(buffer.short == ARP_HARDWARE_TYPE_ETHERNET)
        assertAlways(buffer.short == ETHER_TYPE_IPv4)
        assertAlways(buffer.get() == ARP_MAC_ADDRESS_SIZE)
        assertAlways(buffer.get() == ARP_IP_ADDRESS_SIZE)

        opcode = buffer.short

        buffer.get(senderMac)
        buffer.get(senderIp)
        buffer.get(targetMac)
        buffer.get(targetIp)
    }
}
