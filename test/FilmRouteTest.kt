package com.example

import data.db.FilmTable
import data.db.OrderTable
import io.ktor.application.Application
import io.ktor.http.*
import io.ktor.server.testing.*
import mainModule
import model.Film
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Test
import util.fromJson
import vo.BoolResult
import vo.ErrorResponse
import vo.Status
import kotlin.test.assertEquals

class FilmRouteTest {

    @After
    fun tearDown(){
        transaction {
            SchemaUtils.drop(OrderTable)
            SchemaUtils.drop(FilmTable)
        }
    }

    @Test
    fun testInsertNewFilm() {
        withTestApplication(Application::mainModule) {
            val request = insertFilm("titolo", "direttore", 400000)
            assertEquals(HttpStatusCode.OK, request.response.status())
            val result = request.response.content?.toInt()
            assertEquals(HttpStatusCode.OK, request.response.status())
            assertEquals(true, result is Int)
        }
    }

    @Test
    fun testInsertNewFilmBadRequest() {
        withTestApplication(Application::mainModule) {
            val request = insertFilm(null, "direttore", 400000)
            assertEquals(HttpStatusCode.BadRequest, request.response.status())
        }
    }

    @Test
    fun testFilmList() {
        withTestApplication(Application::mainModule) {
            insertFilm("Star Wars", "Nolan", 70000)
            val request = handleRequest(HttpMethod.Get, "/films")
            assertEquals(HttpStatusCode.OK, request.response.status())
            val list = request.response.content?.fromJson<List<Film>>()
            assertEquals(1, list?.size)
        }
    }

    @Test
    fun testGetFilmById() {
        withTestApplication(Application::mainModule) {
            insertFilm("film", "direttore", 3)
            val request = handleRequest(HttpMethod.Get, "/film?id=1")
            assertEquals(HttpStatusCode.OK, request.response.status())
            assertEquals(1, request.response.content?.fromJson<Film>()?.id)
            assertEquals("bubba", request.response.content?.fromJson<Film>()?.title)
        }
    }

    @Test
    fun testGetFilmByIdFailInvalidParam() {
        withTestApplication(Application::mainModule) {
            insertFilm("film", "direttore", 3)
            val request = handleRequest(HttpMethod.Get, "/film?id=a")
            assertEquals(HttpStatusCode.BadRequest, request.response.status())
            assertEquals(ErrorResponse.Type.INVALID_PARAM, request.response.content?.fromJson<ErrorResponse>()?.errType)
        }
    }

    @Test
    fun testGetFilmByIdFailNoId() {
        withTestApplication(Application::mainModule) {
            insertFilm("film", "direttore", 3)
            val request = handleRequest(HttpMethod.Get, "/film?id=9999999")
            assertEquals("null", request.response.content)
            assertEquals(HttpStatusCode.NoContent, request.response.status())
        }
    }

    @Test
    fun testGetFilmByIdFailMissingParam() {
        withTestApplication(Application::mainModule) {
            insertFilm("film", "direttore", 3)
            val request = handleRequest(HttpMethod.Get, "/film")
            assertEquals(HttpStatusCode.BadRequest, request.response.status())
            assertEquals(ErrorResponse.Type.MISSING_ID, request.response.content?.fromJson<ErrorResponse>()?.errType)
        }
    }

    @Test
    fun testDeleteFilmById() {
        withTestApplication(Application::mainModule) {
            insertFilm("titolo", "dir", 40000)
            val request = handleRequest(HttpMethod.Delete, "/film?id=1")
            println(request.response.content)
            assertEquals(HttpStatusCode.OK, request.response.status())
            assertEquals(Status.SUCCESS, request.response.content?.fromJson<BoolResult>()?.status)
        }
    }

    @Test
    fun testDeleteFilmByIdFail() {
        withTestApplication(Application::mainModule) {
            insertFilm("titolo", "dir", 40000)
            val request = handleRequest(HttpMethod.Delete, "/film?id=100")
            println(request.response.content)
            assertEquals(HttpStatusCode.OK, request.response.status())
            assertEquals(Status.ERROR, request.response.content?.fromJson<BoolResult>()?.status)
        }
    }

}

fun TestApplicationEngine.insertFilm(title: String?, director: String?, duration: Long?): TestApplicationCall {
    return handleRequest(HttpMethod.Post, "/film") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(
            listOf(
                "title" to title,
                "director" to director,
                "duration" to "$duration"
            ).formUrlEncode()
        )
    }
}