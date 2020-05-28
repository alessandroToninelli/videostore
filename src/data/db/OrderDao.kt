package data.db


import model.Order
import model.toOrder
import model.toOrders
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import vo.DbResult
import vo.dbQuery
import java.time.LocalDateTime

interface OrderDao{
    suspend fun insert(userId: Int, filmId: Int): DbResult<Int>
    suspend fun getOrdersByUser(userId: Int): DbResult<List<Order>>
    suspend fun getOrdersByFilm(filmId : Int): DbResult<List<Order>>
    suspend fun getOrderById(id: Int): DbResult<Order?>
    suspend fun delete(orderId: Int): DbResult<Boolean>
}


class OrderDaoImpl : OrderDao{

    init {
        transaction { SchemaUtils.create(OrderTable) }
    }

    override suspend fun insert(userId: Int, filmId: Int): DbResult<Int> {
        return dbQuery {
            OrderEntity.new {
                this.date = LocalDateTime.now()
                this.user = UserEntity[userId]
                this.film = FilmEntity[filmId]
            }.id.value

        }
    }

    override suspend fun getOrdersByUser(userId: Int): DbResult<List<Order>> {
        return dbQuery {
//            val result = FilmTable.innerJoin(OrderTable).select { OrderTable.client eq user.id }.map { it[OrderTable.date] }
//            val query = FilmTable.innerJoin(OrderTable).select { OrderTable.client eq user.id }
//            val query = FilmTable.innerJoin(OrderTable).slice(FilmTable.columns).select { OrderTable.client eq user.id
            UserEntity[userId].orders.toList().toOrders()
        }

    }

    override suspend fun getOrdersByFilm(filmId: Int): DbResult<List<Order>> {
        return dbQuery {
            FilmEntity[filmId].orders.toList().toOrders()
        }
    }

    override suspend fun getOrderById(id: Int): DbResult<Order?> {
        return dbQuery {
            OrderEntity.findById(id)?.toOrder()
        }
    }

    override suspend fun delete(orderId: Int): DbResult<Boolean> {
       return dbQuery {
           OrderEntity.findById(orderId)?.delete() != null
       }
    }
}