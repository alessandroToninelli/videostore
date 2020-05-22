package model

import data.db.OrderEntity
import java.time.LocalDateTime

data class Order(val id: Int, val film : Film, val user: User, val date: LocalDateTime)

fun OrderEntity.toOrder() = Order(this.id.value, this.film.toFilm(), this.user.toUser(), this.date)

fun List<OrderEntity>.toOrders() = this.map { it.toOrder() }