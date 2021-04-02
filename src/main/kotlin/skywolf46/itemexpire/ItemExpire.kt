package skywolf46.itemexpire

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import skywolf46.commandannotation.CommandAnnotation
import skywolf46.extrautility.util.getItemName
import skywolf46.extrautility.util.register
import skywolf46.extrautility.util.schedule
import skywolf46.extrautility.util.scheduleRepeat
import skywolf46.itemexpire.enums.ItemExpireMessages
import skywolf46.itemexpire.listener.InteractionListener
import skywolf46.itemexpire.util.isExpired
import skywolf46.itemexpire.util.isWrapped
import skywolf46.itemexpire.util.unwrap
import skywolf46.messagereplacer.MessageReplacer
import skywolf46.placeholders.util.MessageParameters
import skywolf46.refnbt.util.item.getTag
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ItemExpire : JavaPlugin() {
    companion object {
        lateinit var timeFormat: SimpleDateFormat
            private set
    }

    override fun onEnable() {
        CommandAnnotation.init(this)
        with(File(dataFolder, "messages.yml")) {
            MessageReplacer.register(ItemExpireMessages::class.java, this)
                .registerIfNotExists(ItemExpireMessages.values())
                .save(this)
            timeFormat =
                SimpleDateFormat(MessageReplacer.get(ItemExpireMessages::class.java)[ItemExpireMessages.TIME_FORMAT].parseSingle())
        }
        InteractionListener().register()

        scheduleRepeat(4L) {
            for (p in Bukkit.getOnlinePlayers()) {
                val armor = p.equipment.armorContents
                for (count in armor.indices) {
                    val item = armor[count]
                    if (item?.isWrapped() == true) {
                        item.unwrap()
                        MessageReplacer.get(ItemExpireMessages::class.java)
                            .get(ItemExpireMessages.ITEM_UNWRAP)
                            .sendParameterTo(
                                p,
                                MessageParameters.of()
                                    .add("item", item.getItemName())
                                    .add("time", timeFormat.format(Date(item.getTag().getLong("ExChk"))))
                            )
                    }
                }

                for (count in 0 until p.inventory.size) {
                    val item = p.inventory.getItem(count)
                    if (item?.isExpired() == true) {
                        p.inventory.setItem(count, ItemStack(Material.AIR))
                        MessageReplacer.get(ItemExpireMessages::class.java)
                            .get(ItemExpireMessages.ITEM_EXPIRED)
                            .sendParameterTo(
                                p,
                                MessageParameters.of()
                                    .add("item", item.getItemName())
                            )

                    }
                }
            }
        }
    }
}