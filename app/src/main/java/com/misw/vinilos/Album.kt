package com.misw.vinilos

import com.squareup.moshi.Json

data class Album(
    @Json val id: Int? = null,
    @Json val name: String,
    @Json val cover: String,
    @Json val releaseDate: String,
    @Json val description: String,
    @Json val genre: String,
    @Json val recordLabel: String,
 //   val tracks: List<Track> = emptyList(),
 //   val performers: List<Performer> = emptyList(),
 //   val comments: List<Comment> = emptyList()
)


/*{
    "name": "Buscando América",
    "cover": "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
    "releaseDate": "1984-08-01T00:00:00-05:00",
    "description": "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.",
    "genre": "Salsa",
    "recordLabel": "Elektra"
}
{
        "id": 1,
        "name": "Buscando América",
        "cover": "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
        "releaseDate": "1984-08-01T05:00:00.000Z",
        "description": "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.",
        "genre": "Salsa",
        "recordLabel": "Elektra",
        "tracks": [],
        "performers": [],
        "comments": []
    }

*/


