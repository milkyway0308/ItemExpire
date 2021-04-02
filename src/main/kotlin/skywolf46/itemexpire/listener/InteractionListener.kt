package skywolf46.itemexpire.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import skywolf46.extrautility.events.interaction.PlayerRightClickEvent
import skywolf46.extrautility.util.getItemName
import skywolf46.itemexpire.ItemExpire
import skywolf46.itemexpire.enums.ItemExpireMessages
import skywolf46.itemexpire.util.isWrapped
import skywolf46.itemexpire.util.unwrap
import skywolf46.messagereplacer.MessageReplacer
import skywolf46.placeholders.util.MessageParameters
import skywolf46.refnbt.util.item.getTag
import java.util.*

class InteractionListener : Listener {
    @EventHandler
    fun PlayerRightClickEvent.onEvent() {
        if (!isOffHanded) {
            if (player.inventory.itemInMainHand?.isWrapped() == true) {
                player.inventory.itemInMainHand.unwrap()
                MessageReplacer.get(ItemExpireMessages::class.java)
                    .get(ItemExpireMessages.ITEM_UNWRAP)
                    .sendParameterTo(
                        player,
                        MessageParameters.of()
                            .add("item", player.inventory.itemInMainHand.getItemName())
                            .add("time", ItemExpire.timeFormat.format(Date(player.inventory.itemInMainHand!!.getTag().getLong("ExChk"))))
                    )

            }
        } else {
            if (player.inventory.itemInOffHand?.isWrapped() == true) {
                isCancelled = true
                MessageReplacer.get(ItemExpireMessages::class.java)
                    .get(ItemExpireMessages.ERROR_CANNOT_INTERACT_WITH_OFFHAND)
                    .sendTo(player)
            }
        }
    }
}