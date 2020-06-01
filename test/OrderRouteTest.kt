package com.example

import data.db.FilmTable
import data.db.OrderTable
import data.db.UserTable
import io.ktor.application.Application
import io.ktor.http.*
import io.ktor.server.testing.*
import mainModule
import model.Order
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Test
import util.fromJson
import vo.BoolResult
import vo.ErrorResponse
import vo.Status
import kotlin.test.assertEquals

class OrderRouteTest {

    @After
    fun tearDown(){
        transaction {
            SchemaUtils.drop(OrderTable)
            SchemaUtils.drop(FilmTable)
            SchemaUtils.drop(UserTable)
        }
    }

    @Test
    fun testInsertOrder() = withTestApplication(Application::mainModule) {
        val filmId = insertFilm("Star Wars", "nolan", 30000).response.content
        val userId = insertUser("Alessando", "toninelli", "MyEmail").response.content
        val request = insertOrder(filmId, userId)
        assertEquals(HttpStatusCode.OK, request.response.status())
        val id = request.response.content?.toInt()
        assertEquals(true, id is Int)
    }

    @Test
    fun testInsertOrderBadRequest() = withTestApplication(Application::mainModule) {
        val request = insertOrder(null, null)
        assertEquals(HttpStatusCode.BadRequest, request.response.status())

    }

    @Test
    fun testGetOrderById() = withTestApplication(Application::mainModule) {
        val filmId = insertFilm("Star Wars", "nolan", 30000).response.content
        val userId = insertUser("Alessando", "toninelli", "MyEmail").response.content
        val orderId = insertOrder(filmId, userId).response.content
        val request = handleRequest(HttpMethod.Get, "/order?id=$orderId")
        assertEquals(HttpStatusCode.OK, request.response.status())
        val order = request.response.content?.fromJson<Order>()
        assertEquals(filmId?.toInt(), order?.film?.id)
        assertEquals(userId?.toInt(), order?.user?.id)
    }


    @Test
    fun testGetOrderByIdInvalidParam() = withTestApplication(Application::mainModule) {
        val filmId = insertFilm("Star Wars", "nolan", 30000).response.content
        val userId = insertUser("Alessando", "toninelli", "MyEmail").response.content
        val orderId = insertOrder(filmId, userId).response.content
        val request = handleRequest(HttpMethod.Get, "/order?id=a")
        assertEquals(HttpStatusCode.BadRequest, request.response.status())
        val error = request.response.content?.fromJson<ErrorResponse>()
        assertEquals(ErrorResponse.Type.INVALID_PARAM, error?.errType)
    }

    @Test
    fun testGetOrderByIdMissingParam() = withTestApplication(Application::mainModule) {
        val filmId = insertFilm("Star Wars", "nolan", 30000).response.content
        val userId = insertUser("Alessando", "toninelli", "MyEmail").response.content
        val orderId = insertOrder(filmId, userId).response.content
        val request = handleRequest(HttpMethod.Get, "/order")
        assertEquals(HttpStatusCode.BadRequest, request.response.status())
        val error = request.response.content?.fromJson<ErrorResponse>()
        assertEquals(ErrorResponse.Type.MISSING_ID, error?.errType)
    }

    @Test
    fun testGetOrdersByUser() = withTestApplication(Application::mainModule) {
        val filmId = insertFilm("Star Wars", "nolan", 30000).response.content
        val userId = insertUser("Alessando", "toninelli", "MyEmail").response.content
        insertOrder(filmId, userId).response.content
        val request = handleRequest(HttpMethod.Get, "/orders/user?id=$userId")
        assertEquals(HttpStatusCode.OK, request.response.status())
        val list = request.response.content?.fromJson<List<Order>>()
        assertEquals(1, list?.size)
        list?.forEach { assertEquals(it.user.id, userId?.toInt()) }
        Unit
    }

    @Test
    fun testGetOrdersByFilm() = withTestApplication(Application::mainModule) {
        val filmId = insertFilm("Star Wars", "nolan", 30000).response.content
        val userId = insertUser("Alessando", "toninelli", "MyEmail").response.content
        insertOrder(filmId, userId).response.content
        val request = handleRequest(HttpMethod.Get, "/orders/film?id=$filmId")
        assertEquals(HttpStatusCode.OK, request.response.status())
        val list = request.response.content?.fromJson<List<Order>>()
        assertEquals(1, list?.size)
        list?.forEach { assertEquals(it.film.id, userId?.toInt()) }
        Unit
    }

    @Test
    fun deleteOrderById() = withTestApplication(Application::mainModule) {
        val filmId = insertFilm("Star Wars", "nolan", 30000).response.content
        val userId = insertUser("Alessando", "toninelli", "MyEmail").response.content
        val orderId = insertOrder(filmId, userId).response.content
        val request = handleRequest(HttpMethod.Delete, "/order?id=$orderId")
        println(request.response.content)
        assertEquals(HttpStatusCode.OK, request.response.status())
        val boolResult = request.response.content?.fromJson<BoolResult>()
        assertEquals(Status.SUCCESS, boolResult?.status)
    }

    @Test
    fun deleteUserByIdFail() = withTestApplication(Application::mainModule) {
        val filmId = insertFilm("Star Wars", "nolan", 30000).response.content
        val userId = insertUser("Alessando", "toninelli", "MyEmail").response.content
        val orderId = insertOrder(filmId, userId).response.content
        val request = handleRequest(HttpMethod.Delete, "/user?id=200")
        println(request.response.content)
        assertEquals(HttpStatusCode.OK, request.response.status())
        val boolResult = request.response.content?.fromJson<BoolResult>()
        assertEquals(Status.ERROR, boolResult?.status)
    }

}

fun TestApplicationEngine.insertOrder(filmId: String?, userId: String?): TestApplicationCall{
    return handleRequest(HttpMethod.Post, "/order") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(listOf(
            "filmId" to filmId,
            "userId" to userId).formUrlEncode())
    }
}