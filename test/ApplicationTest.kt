package com.example

import business.service.AppService
import com.google.gson.Gson
import data.repository.AppRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders
import io.ktor.server.testing.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import model.Film
import org.junit.Test
import org.koin.ktor.ext.get
import mainModule
import vo.ErrorResponse
import vo.Status
import vo.case
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testFilm() {
        withTestApplication({ mainModule() }) {
            val request = handleRequest(HttpMethod.Post, "/film") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody(
                    listOf(
                        "title" to "nome Film",
                        "director" to "direttore film",
                        "durationTimeMillis" to "29845987"
                    ).formUrlEncode()
                )
            }

            println(request.response.content)
        }
    }


    @Test
    fun testInsertNewFilm() {
        withTestApplication(Application::mainModule) {
            val request = handleRequest(HttpMethod.Post, "/film") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody(
                    listOf(
                        "title" to "titolo",
                        "director" to "direttore film",
                        "duration" to "29845987"
                    ).formUrlEncode()
                )
            }
            assertEquals(HttpStatusCode.OK, request.response.status())
        }
    }

    @Test
    fun testFilmList() {
        withTestApplication(Application::mainModule) {
            val request = handleRequest(HttpMethod.Get, "/films")
            assertEquals(HttpStatusCode.OK, request.response.status())
        }
    }

}
