package ro.ubb.flaviu.client.dataimport android.util.Logimport com.google.gson.GsonBuilderimport okhttp3.*import retrofit2.Retrofitimport retrofit2.converter.gson.GsonConverterFactoryobject Api {    private const val URL = "http://192.168.119.144:3000/"    //private const val URL = "http://192.168.145.36:3000/"    //private const val URL = "http://172.30.115.18:3000/"    private val client: OkHttpClient = OkHttpClient.Builder().build()    private var gson = GsonBuilder()        .setLenient()        .create()    val retrofit: Retrofit = Retrofit.Builder()        .baseUrl(URL)        .addConverterFactory(GsonConverterFactory.create(gson))        .client(client)        .build()    fun registerWS(messageHandler: (String) -> Unit) {        val client = OkHttpClient()        val request: Request = Request.Builder().url(URL).build()        val webSocketListenerCoinPrice: WebSocketListener = object : WebSocketListener() {            override fun onOpen(webSocket: WebSocket, response: Response) {            }            override fun onMessage(webSocket: WebSocket, text: String) {                messageHandler(text)                Log.e("anthrax", "MESSAGE: $text")            }            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {                webSocket.close(1000, null)                webSocket.cancel()            }        }        client.newWebSocket(request, webSocketListenerCoinPrice)        client.dispatcher.executorService.shutdown()    }}