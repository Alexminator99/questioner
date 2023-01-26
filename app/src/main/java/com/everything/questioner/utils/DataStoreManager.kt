package com.everything.questioner.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.everything.questioner.data.DataConstants.QUESTIONER_DATASTORE
import com.everything.questioner.data.models.ProfessionalData
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = QUESTIONER_DATASTORE)

    companion object {
        //#region Professional Data
        val NAME = stringPreferencesKey("NAME")
        val SCIENTIFIC_CATEGORY = stringPreferencesKey("SCIENTIFIC_CATEGORY")
        val SEX = stringPreferencesKey("SEX")
        val SPECIALIST = stringPreferencesKey("SPECIALIST")
        val EXPERIENCE = stringPreferencesKey("EXPERIENCE")
        val GRADUATED_OF = stringPreferencesKey("GRADUATED_OF")
        val ORGANIZATION = stringPreferencesKey("ORGANIZATION")
        val PROFESSION = stringPreferencesKey("PROFESSION")
        val DOCENT_CATEGORY = stringPreferencesKey("DOCENT_CATEGORY")
        //#endregion

        //#region Behavior Information
        val BEHAVIOR_INFORMATION = stringPreferencesKey("BEHAVIOR_INFORMATION")
        //#endregion
    }

    suspend fun saveToDataStore(professionalData: ProfessionalData) {
        context.dataStore.edit {
            it[NAME] = professionalData.name
            it[SCIENTIFIC_CATEGORY] = professionalData.scientificCategory
            it[SEX] = professionalData.sex
            it[SPECIALIST] = professionalData.specialist
            it[EXPERIENCE] = professionalData.experience
            it[GRADUATED_OF] = professionalData.graduatedOf
            it[ORGANIZATION] = professionalData.organization
            it[PROFESSION] = professionalData.profession
            it[DOCENT_CATEGORY] = professionalData.docentCategory
        }
    }

    fun getProfessionalData() = context.dataStore.data.map {
        val professionalData =
            if (it[NAME] != null)
                ProfessionalData(
                    name = it[NAME] ?: "",
                    scientificCategory = it[SCIENTIFIC_CATEGORY] ?: "",
                    sex = it[SEX] ?: "",
                    specialist = it[SPECIALIST] ?: "",
                    experience = it[EXPERIENCE] ?: "",
                    graduatedOf = it[GRADUATED_OF] ?: "",
                    organization = it[ORGANIZATION] ?: "",
                    profession = it[PROFESSION] ?: "",
                    docentCategory = it[DOCENT_CATEGORY] ?: "",
                )
            else null
        professionalData
    }

    suspend fun saveBehaviorInformation(behaviorInformation: String) {
        context.dataStore.edit {
            it[BEHAVIOR_INFORMATION] = behaviorInformation
        }
    }

    fun getBehaviorInformation() = context.dataStore.data.map {
        it[BEHAVIOR_INFORMATION] ?: ""
    }
}