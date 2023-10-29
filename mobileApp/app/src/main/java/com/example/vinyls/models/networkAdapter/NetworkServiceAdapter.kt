package com.example.vinyls.models.networkAdapter
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinyls.models.Album
import com.example.vinyls.models.Candidato
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL = "http://candidatos.us-east-2.elasticbeanstalk.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }


    suspend fun getCandidatos() = suspendCoroutine<List<Candidato>>{ cont ->
        requestQueue.add(getRequest("candidato",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Candidato>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Candidato(id = item.getInt("id"),
                        names = item.getString("names"),
                        lastNames = item.getString("lastNames"),
                        password = item.getString("password"),
                        confirmPassword = item.getString("confirmPassword"),
                        mail = item.getString("mail"))
                    )
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun registro(body: JSONObject) = suspendCoroutine<Candidato> { cont ->
        requestQueue.add(postRequest("candidato", body,
            {  response ->
                val candidato = Candidato(id = response.getInt("id"),
                    names = response.getString("names"),
                    lastNames = response.getString("lastNames"),
                    mail = response.getString("mail"))
                cont.resume(candidato)
            },{
                cont.resumeWithException(it)
            }))
    }











    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont ->
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                var item:JSONObject? = null
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description"))
                    )
                }
                cont.resume(list)
            },
            {
                cont.resumeWithException(it)
            }))
    }

    suspend fun createAlbum(body: JSONObject) = suspendCoroutine<Album> { cont ->
        requestQueue.add(postRequest("albums", body,
            {  response ->
                val album = Album(albumId = response.getInt("id"),
                                name = response.getString("name"),
                                cover = response.getString("cover"),
                                recordLabel = response.getString("recordLabel"),
                                releaseDate = response.getString("releaseDate"),
                                genre = response.getString("genre"),
                                description = response.getString("description"))
                cont.resume(album)
            },{
                cont.resumeWithException(it)
            }))
    }


    fun getAlbumById(albumId:Int, onComplete:(resp: Album)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONObject(response)
                val album = Album(albumId = resp.getInt("id"),name = resp.getString("name"), cover = resp.getString("cover"), recordLabel = resp.getString("recordLabel"), releaseDate = resp.getString("releaseDate"), genre = resp.getString("genre"), description = resp.getString("description"))
                onComplete(album)
            },
            {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL +path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL +path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL +path, body, responseListener, errorListener)
    }
}