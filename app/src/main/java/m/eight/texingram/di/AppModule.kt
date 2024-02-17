package m.eight.texingram.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import m.eight.texingram.data.remote.ChatSocketService
import m.eight.texingram.data.remote.ChatSocketServiceImpl
import m.eight.texingram.data.remote.MessageService
import m.eight.texingram.data.remote.MessageServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(CIO) {
        install(WebSockets)
        install(ContentNegotiation)
    }

    @Provides
    @Singleton
    fun provideMessageService(client: HttpClient): MessageService = MessageServiceImpl(client)

    @Provides
    @Singleton
    fun provideChatSocketService(client: HttpClient): ChatSocketService = ChatSocketServiceImpl(client)
}