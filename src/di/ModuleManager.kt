package di

import business.usecase.*
import context.AppSettings
import data.db.*
import data.repository.AppRepository
import data.repository.AppRepositoryImpl
import module.*
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy
import ui.WebRoute
import ui.film.DeleteFilmRoute
import ui.film.FilmListRoute
import ui.film.FilmRoute
import ui.film.InsertFilmRoute
import ui.order.*
import ui.user.DeleteUserRoute
import ui.user.InsertUserRoute
import ui.user.UserListRoute
import ui.user.UserRoute


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

    private val appModuleModule = module {
        single<BootstrapDbModule>() bind AppModule::class
        single<StatusModule>() bind AppModule::class
        single<ContentNegotiationModule>() bind AppModule::class
        single<LocationModule>() bind AppModule::class
        single { RoutingModule(getAll()) } bind AppModule::class

    }

    private val useCaseModule = module {
        single<DeleteFilmUseCase>()
        single<DeleteOrderUseCase>()
        single<DeleteUserUseCase>()
        single<GetAdminUsersUseCase>()
        single<GetAllFilmsUseCase>()
        single<GetAllUsersUseCase>()
        single<GetFilmOrdersUseCase>()
        single<GetFilmUseCase>()
        single<GetUserOrdersUseCase>()
        single<GetUserUseCase>()
        single<InsertOrderUseCase>()
        single<InsertFilmUseCase>()
        single<InsertUserUseCase>()
        single<GetOrderUseCase>()
    }

    private val routeModule = module {
        single<DeleteFilmRoute>() bind WebRoute::class
        single<FilmListRoute>() bind WebRoute::class
        single<FilmRoute>() bind WebRoute::class
        single<InsertFilmRoute>() bind WebRoute::class
        single<DeleteOrderRoute>() bind WebRoute::class
        single<InsertOrderRoute>() bind WebRoute::class
        single<OrderListByFilmRoute>() bind WebRoute::class
        single<OrderListByUserRoute>() bind WebRoute::class
        single<OrderRoute>() bind WebRoute::class
        single<DeleteUserRoute>() bind WebRoute::class
        single<InsertUserRoute>() bind WebRoute::class
        single<UserListRoute>() bind WebRoute::class
        single<UserRoute>() bind WebRoute::class
    }



    fun loadModules() {
        loadKoinModules(listOf(appModuleModule, dbModule, repoModule, routeModule, useCaseModule ))
    }

}

