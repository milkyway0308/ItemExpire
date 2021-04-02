package skywolf46.itemexpire.util

import org.bukkit.inventory.ItemStack
import skywolf46.extrautility.util.itemMeta
import skywolf46.itemexpire.ItemExpire
import skywolf46.itemexpire.enums.ItemExpireMessages
import skywolf46.itemexpire.exceptions.ItemAlreadyUnwrappedException
import skywolf46.itemexpire.exceptions.NotItemExpireItemException
import skywolf46.messagereplacer.MessageReplacer
import skywolf46.placeholders.util.MessageParameters
import skywolf46.refnbt.util.item.getTag
import java.util.*

object ExpireUtil {

    fun wrap(item: ItemStack, time: Long) {
        item.getTag()["ExWrp"] = 0.toByte()
        item.getTag()["ExChk"] = time
        item.itemMeta {
            lore = (lore ?: mutableListOf()).also {
                var removed = 0
                for (i in it.indices)
                    if (it[i].startsWith("§7§0§f"))
                        it.removeAt(i - (removed++))
                it.addAll(it.size,
                    MessageReplacer.get(ItemExpireMessages::class.java)[ItemExpireMessages.TEXT_WRAPPED_ITEM]
                        .parse(
                            MessageParameters.of()
                                .add("lifeTime",
                                    TimeString.toDHMS(item.getTag().getLong("ExChk"))))
                        .also { lt ->
                            for (x in lt.indices)
                                lt[x] = "§7§0§f${lt[x]}"
                        }
                )
            }
        }

    }

    fun removeExpire(item: ItemStack) {
        item.getTag().remove("ExChk")
        item.getTag().remove("ExWrp")
    }

    fun isExpireItem(item: ItemStack): Boolean {
        return item.getTag().has("ExChk")
    }

    fun isWrapped(item: ItemStack): Boolean {
        return item.getTag().has("ExWrp") && item.getTag().getByte("ExWrp") == 0.toByte()
    }

    fun isExpired(item: ItemStack): Boolean {
        return isExpireItem(item) && !isWrapped(item) && item.getTag().getLong("ExChk") <= System.currentTimeMillis()
    }

    fun unwrap(item: ItemStack) {
        if (!isExpireItem(item))
            throw NotItemExpireItemException()
        if (!isWrapped(item))
            throw ItemAlreadyUnwrappedException()
        item.getTag()["ExWrp"] = 1.toByte()
        item.getTag()["ExChk"] = System.currentTimeMillis() + item.getTag().getLong("ExChk")

        item.itemMeta {
            lore = (lore ?: mutableListOf()).also {
                for (i in it.indices) {
                    if (it[i].startsWith("§7§0§f")) {
                        for (x in i until it.size) {
                            if (it[i].startsWith("§7§0§f"))
                                it.removeAt(i)
                        }
                        it.addAll(i,
                            MessageReplacer.get(ItemExpireMessages::class.java)[ItemExpireMessages.TEXT_UNWRAPPED_ITEM]
                                .parse(
                                    MessageParameters.of()
                                        .add("expire",
                                            ItemExpire.timeFormat.format(Date(item.getTag().getLong("ExChk")))))
                                .also { lt ->
                                    for (x in lt.indices)
                                        lt[x] = "§7§0§f${lt[x]}"
                                }
                        )

                        return@also
                    }
                }
                // Lore not found! Force adding lore at last.
                it.addAll(it.size,
                    MessageReplacer.get(ItemExpireMessages::class.java)[ItemExpireMessages.TEXT_UNWRAPPED_ITEM]
                        .parse(
                            MessageParameters.of()
                                .add("expire", ItemExpire.timeFormat.format(Date(item.getTag().getLong("ExChk"))))

                        ).also { lt ->
                            for (i in lt.indices)
                                lt[i] = "§7§0§f${lt[i]}"
                        }
                )

            }
        }
    }
}