package com.misw.vinilos

sealed class Routes(val path:String){

    object Albums : Routes("albums")
    object AlbumsCreate : Routes("albums-create")
    object Artists : Routes("artists")
    object Collectors : Routes("collectors")
    object CollectorDetail : Routes("collector-detail/{collectorId}")
}

