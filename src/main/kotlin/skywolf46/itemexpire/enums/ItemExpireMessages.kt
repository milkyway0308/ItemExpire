package skywolf46.itemexpire.enums

enum class ItemExpireMessages(vararg msg: String) {
    ITEM_EXPIRED("&cItem <item> is expired."),
    ITEM_UNWRAP("&cItem <item> has been unwrapped. (Expires on <time>)"),
    TEXT_WRAPPED_ITEM(
        "§eItem lifetime: &c<lifeTime>",
        "§5&oItem is glowing with mysterious magical effect."
    ),
    TEXT_UNWRAPPED_ITEM(
        "§eItem expires on : &4<expire>",
        "§5&oItem is slowly disappearing."
    ),
    ERROR_CANNOT_INTERACT_WITH_OFFHAND("&cIteraction failed; Cannot interact when wrapped item is in offhand"),
    TIME_FORMAT("yyyy-MM-dd HH:mm:ss")

    ;

    val messages = listOf(*msg)

    fun get() = messages
}