package com.example

import com.google.gson.Gson
import data.repository.AppRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders
import io.ktor.server.testing.*
import kotlinx.coroutines.*
import model.Film
import org.junit.Test
import org.koin.ktor.ext.get
import mainModule

class ApplicationTest {

    @Test
    fun testFilm() {
        withTestApplication({ mainModule() }) {
            handleRequest(HttpMethod.Post, "/film") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(Film(2, "iusdhf", "osuhdv", 2000)))
            }
        }
    }


    @Test
    fun testQuery()  {
        withTestApplication(Application::mainModule) {
            val repo = this.application.get<AppRepository>()

            runBlocking {
                println(repo.getOrderByUser(3))
            }
        }
    }


}
