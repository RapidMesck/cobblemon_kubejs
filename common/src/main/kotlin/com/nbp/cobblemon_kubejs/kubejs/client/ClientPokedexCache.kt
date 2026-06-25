package com.nbp.cobblemon_kubejs.kubejs.client

object ClientPokedexCache {
    @Volatile
    private var progressBySpecies: Map<String, String> = emptyMap()

    @Volatile
    var isReady: Boolean = false
        private set

    fun replace(progress: Map<String, String>) {
        progressBySpecies = progress.toMap()
        isReady = true
    }

    fun update(speciesId: String, progress: String) {
        progressBySpecies = progressBySpecies.toMutableMap().also {
            if (progress == "none") {
                it.remove(speciesId)
            } else {
                it[speciesId] = progress
            }
        }
        isReady = true
    }

    fun getProgress(speciesId: String): String {
        return progressBySpecies[speciesId] ?: "none"
    }

    fun snapshot(): Map<String, String> = progressBySpecies

    fun clear() {
        progressBySpecies = emptyMap()
        isReady = false
    }
}
