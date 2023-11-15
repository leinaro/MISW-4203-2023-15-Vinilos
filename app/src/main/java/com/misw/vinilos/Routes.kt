package com.misw.vinilos

sealed class Routes(val path:String){

    object Albums : Routes("albums")
    object AlbumsCreate : Routes("albums-create")
    object Artists : Routes("artists")
    object ArtistDetail : Routes("musician/{musicianId}")
    object Collectors : Routes("collectors")
    object CollectorDetail : Routes("collector-detail/{collectorId}")
    object AlbumDetail : Routes("album/{albumId}")
}

