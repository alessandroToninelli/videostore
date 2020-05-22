package di

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import context.AppSettings
import data.db.*
import data.repository.AppRepository
import data.repository.AppRepositoryImpl
import org.jetbrains.exposed.sql.Database
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy
import javax.sql.DataSource


object ModuleManager : KoinComponent {

    private val dbModule = module {
        single { AppSettings.dataSource }
        singleBy<UserDao, UserDaoImpl>()
        singleBy<FilmDao, FilmDaoImpl>()
        singleBy<OrderDao, OrderDaoImpl>()
    }

    private val repoModule = module {
        singleBy<AppRepository, AppRepositoryImpl>()
    }

    fun loadModules() {
        loadKoinModules(listOf(dbModule, repoModule))
    }

  }