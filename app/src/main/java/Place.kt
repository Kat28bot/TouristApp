import android.location.Location
import android.widget.Toast
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng

abstract class Place {
    protected var index = 0
    protected var wspolrzednaLat = 0.0
    protected var wspolrzednaLng = 0.0
    protected var typ //1- ciekawostka 2 - pytanie, 3- wybor,  *4-dotarcieNaMiejsce
            = 0
    protected var nazwa: String? = null
    var info:String?=null
    var ciekawostka:String?=null
    var zagadka:String?=null
    var odp:String?=null
    var radius:Double=0.0

public fun isInPlace(location: Location):Boolean{
    val distance = FloatArray(2)

    Location.distanceBetween(
        location.latitude, location.longitude,
        wspolrzednaLat, wspolrzednaLng, distance
    )
    if (distance[0] > radius) {
       return false

    } else {
        return true

    }
}

    public fun showInfo(){

    }
    abstract fun showInfoCD()

}