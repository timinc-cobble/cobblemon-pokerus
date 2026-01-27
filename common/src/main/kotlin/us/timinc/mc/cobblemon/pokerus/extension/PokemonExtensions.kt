package us.timinc.mc.cobblemon.pokerus.extension

import com.cobblemon.mod.common.api.mark.Marks
import com.cobblemon.mod.common.pokemon.Pokemon
import us.timinc.mc.cobblemon.pokerus.Pokerus
import us.timinc.mc.cobblemon.pokerus.Pokerus.PokemonProperties.HAD_POKERUS
import us.timinc.mc.cobblemon.pokerus.Pokerus.PokemonProperties.POKERUS
import us.timinc.mc.cobblemon.pokerus.Pokerus.PokemonProperties.POKERUS_X
import us.timinc.mc.cobblemon.pokerus.Pokerus.PokemonProperties.POKERUS_Y
import kotlin.random.Random

fun Pokemon.infectWithPokerus(spreadFrom: Pair<Int, Int>? = null) {
    if (hasEverHadPokerus()) {
        Pokerus.debugger.debug("Attempted to infect a Pokémon that has already had Pokérus.", true)
        return
    }

    val pokerusMark = Marks.getByIdentifier(Pokerus.DataKeys.Marks.POKERUS) ?: run {
        Pokerus.debugger.debug("Cannot find the Pokérus marker.", true)
        return
    }
    exchangeMark(pokerusMark, true)

    val x = spreadFrom?.let { (x, _) -> x } ?: Random.nextInt(1, 16)
    val y = spreadFrom?.let { (_, y) -> y } ?: ((x and 0x03) + 1)

    POKERUS_X.pokemonApplicator(this, x.toFloat())
    POKERUS_Y.pokemonApplicator(this, y.toFloat())
    this.updateAspects()

    getOwnerPlayer()?.sendSystemMessage(Pokerus.Chat.pokerusGained(this))
}

fun Pokemon.hasPokerus() = POKERUS.pokemonMatcher(this, true)
fun Pokemon.hadPokerus() = HAD_POKERUS.pokemonMatcher(this, true)
fun Pokemon.neverHadPokerus() = !hasPokerus() && !hadPokerus()
fun Pokemon.hasEverHadPokerus() = hasPokerus() || hadPokerus()

fun Pokemon.getPokerus(): Pair<Int, Int>? {
    val x = POKERUS_X.getValue(this)?.toInt() ?: if (hasPokerus()) Random.nextInt(1, 16) else return null
    val y = POKERUS_Y.getValue(this)?.toInt() ?: if (hasPokerus()) ((x and 0x03) + 1) else return null

    return x to y
}

fun Pokemon.curePokerus() {
    if (!hasPokerus()) return
    getOwnerPlayer()
        ?.sendSystemMessage(Pokerus.Chat.pokerusCured(this))
    POKERUS.pokemonApplicator(this, false)
    HAD_POKERUS.pokemonApplicator(this, true)
    updateAspects()

    val pokerusCuredMark = Marks.getByIdentifier(Pokerus.DataKeys.Marks.POKERUS_CURED) ?: return
    exchangeMark(pokerusCuredMark, true)
}

fun Pokemon.tickPokerus() {
    val (_, y) = getPokerus() ?: return
    if (y == 0) {
        curePokerus()
        return
    }
    POKERUS_Y.pokemonApplicator(this, y - 1F)
}