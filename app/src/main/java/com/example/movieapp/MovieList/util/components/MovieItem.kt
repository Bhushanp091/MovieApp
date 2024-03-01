import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.jetmovieapp.movieLIist.domain.model.Movie
import com.example.movieapp.MovieList.data.remote.MovieListApi
import com.example.movieapp.MovieList.util.Screen

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onMovieClick:(Int)->Unit,
) {

    val imageUrl = "${MovieListApi.IMAGE_BASE_URL}${movie.poster_path}"

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )
    val imageState = imagePainter.state

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable {
              onMovieClick(movie.id)
            }
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {

        if (imageState is AsyncImagePainter.State.Success) {


            val imageBitmap = imageState.result.drawable.toBitmap()

            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

        }

        if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center),
                imageVector = Icons.Rounded.ImageNotSupported,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = movie.title
            )
        }


        if (imageState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center)
                    .scale(0.5f)
            )
        }
    }
}