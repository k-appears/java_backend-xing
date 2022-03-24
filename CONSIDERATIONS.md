Assumptions
===========

* Gender `0` is [Non-binary](https://github.com/keremk/artist-info/blob/master/data/artists.json#L1042)
  , `1` is [Female](https://github.com/keremk/artist-info/blob/master/data/artists.json#L364), `2`
  is [Male](https://github.com/keremk/artist-info/blob/master/data/artists.json#L346)
* If other Genre appears is considered an error in the API
* No movie with negative revenue. See [data](https://github.com/keremk/movie-info/blob/master/data/indexed_movies.json)

Considerations
=============

* To minimize the use of Dependencies as mentioned in [rubic.md](rubric.md)
  * Not use DI
  * Not use Lombok
* To reduce the use of 3rd party bloated dependencies
  * Use [Spark](http://sparkjava.com/) microframework
* Integration tests need to run previously `docker-compose up` manually
