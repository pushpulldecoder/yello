package repo.data

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.stringLiteral


object AudioTable : Table() {
	val path: Column<String> = varchar("id", 1024)
	val title: Column<String> = varchar("title", 128)
	val artist = reference("artist", Artists)
	override val primaryKey = PrimaryKey(path, name = "exact_path") // PK_StarWarsFilms_Id is optional here
}

object Artists : StringIdTable() {
	val name = varchar("name", 256)
	override val primaryKey = PrimaryKey(name)
}

class Artist(id: EntityID<String>) : Entity<String>(id) {
	companion object : StringEntityClass<Artist>(Artists)
}

/*
 * Base class for entities with string id
 */
abstract class StringEntityClass<out E: Entity<String>>(table: IdTable<String>, entityType: Class<E>? = null) : EntityClass<String, E>(table, entityType)

/*
 * Base class for table objects with string id
 */
open class StringIdTable(name: String = "", columnLength: Int = 10) : IdTable<String>(name) {
	override val id: Column<EntityID<String>> = varchar(name, columnLength).entityId()
	override val primaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }
}


