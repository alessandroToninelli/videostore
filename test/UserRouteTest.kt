package com.example

import data.db.OrderTable
import data.db.UserTable
import io.ktor.application.Application
import io.ktor.http.*
import io.ktor.server.testing.*
import mainModule
import model.User
import model.UserType
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Before
import org.junit.Test
import util.fromJson
import vo.BoolResult
import vo.Status
import kotlin.test.assertEquals

class UserRouteTest{

    @After
    fun tearDown(){
        transaction {
            SchemaUtils.drop(OrderTable)
            SchemaUtils.drop(UserTable)
        }
    }

    @Test
    fun insertUserTest() = withTestApplication(Application::mainModule){
        val request = insertUser("alessandro", "toninelli", "myEmail")
        assertEquals(HttpStatusCode.OK, request.response.status())
        val result = request.response.content?.toInt()
        assertEquals(HttpStatusCode.OK, request.response.status())
        assertEquals(true, result is Int)
    }

    @Test
    fun insertUserTestBadRequest() = withTestApplication(Application::mainModule){
        val request = insertUser(null, "toninelli", "myEmail")
        assertEquals(HttpStatusCode.BadRequest, request.response.status())
    }

    @Test
    fun getUserById() = withTestApplication(Application::mainModule) {
        insertUser("alessandro", "toninelli", "myEmail")
        val request = handleRequest(HttpMethod.Get, "/user?id=1")
        println(request.response.content)
        assertEquals(HttpStatusCode.OK, request.response.status())
        val user = request.response.content?.fromJson<User>()
        assertEquals("alessandro", user?.name)
    }

    @Test
    fun getUserList() = withTestApplication(Application::mainModule) {
        insertUser("alessandro", "toninelli", "myEmail")
        val request = handleRequest(HttpMethod.Get, "/users?type=u")
        assertEquals(HttpStatusCode.OK, request.response.status())
        val list = request.response.content?.fromJson<List<User>>()
        assertEquals(1, list?.size)
    }

    @Test
    fun getAdminUserList() = withTestApplication(Application::mainModule) {
        insertUser("alessandro", "toninelli", "myEmail")
        val request = handleRequest(HttpMethod.Get, "/users?type=a")
        assertEquals(HttpStatusCode.OK, request.response.status())
        val list = request.response.content?.fromJson<List<User>>()
        assertEquals(1, list?.size)
        list?.forEach {
            assertEquals(UserType.ADMIN, it.type)
        }
        Unit
    }

    @Test
    fun deleteUserById() = withTestApplication(Application::mainModule) {
        insertUser("alessandro", "toninelli", "myEmail")
        val request = handleRequest(HttpMethod.Delete, "/user?id=1")
        println(request.response.content)
        assertEquals(HttpStatusCode.OK, request.response.status())
        val boolResult = request.response.content?.fromJson<BoolResult>()
        assertEquals(Status.SUCCESS, boolResult?.status)
    }

    @Test
    fun deleteUserByIdFail() = withTestApplication(Application::mainModule) {
        insertUser("alessandro", "toninelli", "myEmail")
        val request = handleRequest(HttpMethod.Delete, "/user?id=100")
        println(request.response.content)
        assertEquals(HttpStatusCode.OK, request.response.status())
        val boolResult = request.response.content?.fromJson<BoolResult>()
        assertEquals(Status.ERROR, boolResult?.status)
    }

}


fun TestApplicationEngine.insertUser(name: String?, surname: String?, email: String?): TestApplicationCall {
    return handleRequest(HttpMethod.Post, "/user") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(
            listOf(
                "name" to name,
                "surname" to surname,
                "email" to email
            ).formUrlEncode()
        )
    }
}