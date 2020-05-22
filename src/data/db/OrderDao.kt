package data.db


import model.Order
import model.toOrders
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

interface OrderDao{
    suspend fun insert(userId: Int, filmId: Int): DbResponse<Int>
    suspend fun getOrdersByUser(userId: Int): DbResponse<List<Order>>
    suspend fun getOrdersByFilm(filmId : Int): DbResponse<List<Order>>
    suspend fun delete(orderId: Int): DbResponse<Unit?>
}


class OrderDaoImpl : OrderDao{

    init {
        transaction { SchemaUtils.create(OrderTable) }
    }

    override suspend fun insert(userId: Int, filmId: Int): DbResponse<Int> {
        return dbQuery {
            OrderEntity.new {
                this.date = LocalDateTime.now()
                this.user = UserEntity[userId]
                this.film = FilmEntity[filmId]
            }.id.value

        }
    }

    override suspend fun getOrdersByUser(userId: Int): DbResponse<List<Order>> {
        return dbQuery {
//            val result = FilmTable.innerJoin(OrderTable).select { OrderTable.client eq user.id }.map { it[OrderTable.date] }
//            val query = FilmTable.innerJoin(OrderTable).select { OrderTable.client eq user.id }
//            val query = FilmTable.innerJoin(OrderTable).slice(FilmTable.columns).select { OrderTable.client eq user.id
            UserEntity[userId].orders.toList().toOrders()
        }

    }

    override suspend fun getOrdersByFilm(filmId: Int): DbResponse<List<Order>> {
        return dbQuery {
            FilmEntity[filmId].orders.toList().toOrders()
        }
    }

    override suspend fun delete(orderId: Int): DbResponse<Unit?> {
       return  dbQuery {
            OrderEntity.findById(orderId)?.delete()
        }
    }
}