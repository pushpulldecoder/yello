package repo

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import repo.data.Artist
import repo.data.Artists
import repo.data.AudioTable
import java.io.File
import java.sql.Connection


object Repo  {
	init {
		println("repo init")
//		File("cache/yello").apply {
//			mkdirs()
//			println(absolutePath)
//		}
//		Database.connect("jdbc:sqlite:cache/yello/data.db", "org.sqlite.JDBC")
//// For both: set SQLite compatible isolation level, see
//// https://github.com/JetBrains/Exposed/wiki/FAQ
//		TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
//		// or Connection.TRANSACTION_READ_UNCOMMITTED
//
//		transaction {
//			// print sql to std-out
//			addLogger(StdOutSqlLogger)
//
//			SchemaUtils.create (AudioTable)
//
//			val artist = Artist.new {
//				this.
//			}
//
//
//			// 'select *' SQL: SELECT Cities.id, Cities.name FROM Cities
//
//			Artist.all().forEach {
//				println(it.name)
//			}
//		}

	}
}
