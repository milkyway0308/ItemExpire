package skywolf46.itemexpire.util

import org.bukkit.inventory.ItemStack


fun ItemStack.isExpired(): Boolean {
    return ExpireUtil.isExpired(this)
}

fun ItemStack.isWrapped(): Boolean {
    return ExpireUtil.isWrapped(this)
}

fun ItemStack.isExpireItem(): Boolean {
    return ExpireUtil.isExpireItem(this)
}

fun ItemStack.unwrap() {
    ExpireUtil.unwrap(this)
}


fun ItemStack.removeExpire() {
    ExpireUtil.removeExpire(this)
}


fun ItemStack.wrap(time: Long) {
    ExpireUtil.wrap(this, time)
}