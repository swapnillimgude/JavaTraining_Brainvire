package com.example.demo.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Entiy.Hotel;
import com.example.demo.Entiy.UserEntity;
import com.example.demo.Entiy.UserRating;
import com.example.demo.Exceptions.ResourceNotFoundExceptions;
import com.example.demo.External.Services.HotelServices;
import com.example.demo.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HotelServices hotelServices;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public UserEntity saveUser(UserEntity userEntity) {
		// TODO Auto-generated method stub
		// random uuid generation
		String randomUUID = UUID.randomUUID().toString();
		userEntity.setUserId(randomUUID);
		UserEntity save = this.userRepository.save(userEntity);
		return save;
	}

	@Override
	public List<UserEntity> getAllUsers() {
		// TODO Auto-generated method stub
		List<UserEntity> getAll = this.userRepository.findAll();
		return getAll;
	}

	@Override
	public UserEntity getbyUserName(String userName) {
		// TODO Auto-generated method stub
		UserEntity user = this.userRepository.getByUserName(userName);
		return user;
	}

	@Override
	public UserEntity updateUser(UserEntity userEntity, String userId) {
		// TODO Auto-generated method stub
		userEntity.setUserId(userId);
		UserEntity update = this.userRepository.save(userEntity);
		return update;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		this.userRepository.deleteById(userId);

	}

	@Override
	public UserEntity getUser(String userId) {
		// TODO Auto-generated method stub

		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundExceptions("User Not Found Exception"));
		logger.info("reached here....1");
		// Rest Template
		UserRating[] rating = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/" + user.getUserId(),
				UserRating[].class);
		List<UserRating> ratingList = Arrays.stream(rating).toList();
		logger.info("rating is :{} " + rating);

		List<UserRating> finalRatingList = ratingList.stream().map(ratings -> {
			logger.info("" + rating.toString());
			ResponseEntity<Hotel> hotel = restTemplate
					.getForEntity("http://HOTEL-SERVICE/hotels/" + ratings.getHotelId(), Hotel.class);
			Hotel hotelData = hotel.getBody();
			ratings.setHotel(hotelData);
			return ratings;
		}).collect(Collectors.toList());

		//////////////////
		// call the thied party API from the REST Template
//		ResponseEntity<Rating> reating = restTemplate.getForEntity("http://localhost:8083/ratings/"+ user.getUserId(),Rating.class);
//		logger.info("reating is : " + reating.getBody());
		//////////////

		user.setRatings(finalRatingList);

		// feign Client
		// Hotel hotel = hotelServices.getUserRating(1L);
		// logger.info("Hotel is : " + hotel.toString());

		return user;
	}

}

/*
 * @Override public UserEntity getUser(String userId) { // TODO Auto-generated
 * method stub
 * 
 * UserEntity getSingleUser = this.userRepository.findById(userId)
 * .orElseThrow(() -> new ResourceNotFoundExceptions("No user Found on Server" +
 * userId)); //LOG 1 logger.info("Hello i am reached here...");UserRating[]
 * ratingforUser = restTemplate
 * .getForObject("http://RATING-SERVICE/ratings/user/" +
 * getSingleUser.getUserId(), UserRating[].class); // UserRating[] ratingforUser
 * = restTemplate // .getForObject("http://localhost:8082/ratings/user/"+
 * getSingleUser.getUserId(), UserRating[].class);
 * 
 * UserRating[] ratingforUser = restTemplate
 * .getForObject("http://RATING-SERVICE/ratings/user/" +
 * getSingleUser.getUserId(), UserRating[].class); // UserRating[] ratingforUser
 * = restTemplate // .getForObject("http://localhost:8082/ratings/user/"+
 * getSingleUser.getUserId(), UserRating[].class);
 * 
 * // logger.info("{} ", ratingforUser); logger.info("Useer id : {}",
 * getSingleUser.getUserId());
 * 
 * List<UserRating> ratings = Arrays.stream(ratingforUser).toList();
 * 
 * List<UserRating> ratinglist = ratings.stream().map(rating -> {
 * 
 * // ResponseEntity<Hotel> forEntity = restTemplate //
 * .getForEntity("http://HOTEL-SERVICE/hotel/" + rating.getHotelId(),
 * Hotel.class); // Hotel hotelData = forEntity.getBody(); // use of Feign
 * Client Hotel hotel = hotelServices.getHotel(rating.getHotelId());
 * 
 * // logger.info("Response Status Code {}", forEntity.getStatusCode());
 * rating.setHotel(hotel); return rating; }).collect(Collectors.toList());
 * getSingleUser.setRatings(ratinglist); return getSingleUser; }
 * 
 */