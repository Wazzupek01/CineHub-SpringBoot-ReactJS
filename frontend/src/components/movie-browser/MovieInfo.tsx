import { genCommaDividedList } from '../../helpers/genCommaDividedList';
import RateMovie from '../rating/RateMovie';

import classes from "./MovieInfo.module.css";

function MovieInfo(props: any) {
  
  const movie = props.movie;

  const addReviewHandler = () => {
    window.location.reload();
  }

  let genreString: string = genCommaDividedList(movie.genres);
  let castString: string = genCommaDividedList(movie.cast);


  return (
    <div className={classes.MovieInfo_container}>
      <h1>{movie.title}</h1>
        <RateMovie rating={movie.rating} readOnly={false} movieId={movie.id} onAddReview={addReviewHandler} />
        <h2>Mean rating: {movie.rating.toFixed(2)}</h2>
        <h2>{movie.releaseYear}</h2>
        {movie.directors.map((dir: string) => (
          <h3 key={dir}>{dir}</h3>
        ))}
        <h3>{genreString}</h3>
        <h3>{movie.runtime} minutes</h3>
        <p style={{width: "50%"}}>{movie.plot}</p>
        <h2>Cast:</h2>
        <p>{castString}</p>
    </div>
  )
}

export default MovieInfo