package world.anhgelus.msmp.basicmazeworldgenerator.events

import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*
import kotlin.math.floor
import kotlin.math.sqrt

class MobListener: Listener {
    fun onMobSpawn(event: EntitySpawnEvent) {
        if (event.entity.type != EntityType.ZOMBIE) {
            event.isCancelled = true
        }
        val zombie = event.entity as Zombie
        val loc = event.location
        val dist = sqrt(loc.x * loc.x + loc.z * loc.z)

        if (dist < 75) {
            event.isCancelled = true
            return
        } else if (dist < 200) {
            return
        }
         val rand = Random()
        rand.setSeed(loc.world!!.seed)
        val chance = rand.nextInt(10)
        if (chance == 0) {
            zombie.setBaby()
        } else {
            zombie.setAdult()
        }
        var boost = 0
        val isBoosted = rand.nextInt(4)
        if (isBoosted == 0) {
            boost = rand.nextInt(2)+1
        }
        val amp = floor((dist / 400)+boost).toInt()
        val speed = PotionEffect(PotionEffectType.SPEED, -1, amp, false, false, false)
        val strength = PotionEffect(PotionEffectType.INCREASE_DAMAGE, -1, amp, false, false, false)
        zombie.addPotionEffect(speed)
        zombie.addPotionEffect(strength)
    }
}