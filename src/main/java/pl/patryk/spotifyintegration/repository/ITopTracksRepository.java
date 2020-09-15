package pl.patryk.spotifyintegration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.patryk.spotifyintegration.model.TopTracks;

public interface ITopTracksRepository extends MongoRepository<TopTracks, String> {


}
