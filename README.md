# SpringBootMicroServices

* A movie catalog service which provides a list of movies with description, name and rating for a user.
* Movie Catalog service interacts with 2 microservices inorder to achieve that.
* The first microservice is UserRating Service, which gives a list of all rated movies (movieId, rating) by the user.
* The second microservice is movie info service, which gives movie information based on the movieId.
* Movie Catalog calls these 2 microservices , collates the data and then send back a response.
* Movie Info service calls the movie db apis https://developers.themoviedb.org/3 to get movie information from movieId. This is an external call.

# Service Discovery
* Service discovery uses spring cloud eureka technology (Eureka- orginally developed by netflix).
* The discovery server is spring cloud eureka server.
* The 3 microservices are eureka client.
* Client Side service discovery, which means the eureka clients talk to eureka server to find the actual address and then uses that to make calls.
* Here upon calling movie catalog service url, movie catalog service in order to call the other 2 web services contacts the discovery server to get the actual addresses and make a call.
* Eureka also helps in load balancing, if we have multiple nodes off microservices running and one of them are down, automatically the one which is up is picked up, if both of them are up requests are distributed over the running instance.

# Circuit Breaking
* Uses Hystrix (Originally developed by netflix, comes with spring cloud) for Circuit breaking.
* Uses hystrix to configure the following :
     -- @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"), --> timeout configuration, which means when a slow responding web service call should timeout. 
		 --	@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"), --> Hystrix checks last 5 request result to take a decision.
		 -- @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"), --> The circuit breaks, if in the last 5 calls, 50% of the calls failed. 
		 -- @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000") --> Every 5 second after circuit breaking the circuit breaker awakes.
* Incase of any error (timeouts, node downtime) there is an automatic fallback response which is configured.
* Hystrix Dashboard , hosted at http://localhost:8082/hystrix/monitor --> monitors the calls. 

# Notes
* No db calls/setup has been made in any microservices. The intent was only to achieve microservices based communication with important concepts revolving around it.
* User Rating Service: http://localhost:8083/ratingsdata/users/1234
* Movie Info Service: http://localhost:8081/movies/2002
* Movie Catalog Service: http://localhost:8082/catalog/rohit
* All the microservices uses rest template to communicate with each other.
* There is also Webclient.builder code (which is commented, can be used upon uncommenting) . WebClient is built upon spring reactive framework where the calls aren't synchronous.  
* This is based on java brains tutorial on microservices by koushik kothagal.
