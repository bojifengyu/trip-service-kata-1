package org.craftedsw.tripservicekata.trip;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

import java.util.List;

public class TripServiceTest {
	
	private static final User UNUSED_USER = null;
	private static final User GUEST = null;
	private static final User REGISTERED_USER = new User();
	private static final User ANOTHER_USER = new User();
	private static final Trip TO_BRAZIL = new Trip();
	private static final Trip TO_LONDON = new Trip();
	
	private User loggedInUser;
	private TripService tripService;

	@Before 
	public void initialize() {
		tripService = new TestableTripService();
		loggedInUser = REGISTERED_USER;

	}
	
	@Test(expected=UserNotLoggedInException.class) public void
	should_throw_exception_user_not_logged_in() {
		
		loggedInUser = GUEST;
		
		tripService.getTripsByUser(UNUSED_USER);
	}
	
	@Test public void
	should_not_return_any_trips_when_users_are_not_friends() {
		
		User friend = new User();
		friend.addFriend(ANOTHER_USER);
		friend.addTrip(TO_BRAZIL);
		
		List<Trip> tripList = tripService.getTripsByUser(friend);
		assertThat(tripList.size(), is(0));
		
	}
	
	@Test public void
	should_return_trips_when_users_are_friends() {
		
		User friend = UserBuilder.aUser().friendsWith(ANOTHER_USER, loggedInUser)
							.withTrips(TO_BRAZIL, TO_LONDON).build();
		
		List<Trip> tripList = tripService.getTripsByUser(friend);
		assertThat(tripList.size(), is(2));
		
	}
	
	public static class UserBuilder {

		private User[] friends = new User[] {};
		private Trip[] trips = new Trip[] {};

		public static UserBuilder aUser() {
			return 	new UserBuilder();
		}

		public UserBuilder withTrips(Trip... trips) {
			this.trips  = trips;
			return this;
		}


		public UserBuilder friendsWith(User... friends) {
			this.friends = friends;
			return this;
		}
		
		public User build() {
			User user = new User();
			addFriendsTo(user);
			addTripsTo(user);
			return user;
		}

		private void addTripsTo(User user) {
			for (Trip trip : trips) {
				user.addTrip(trip);
			}
			
		}

		private void addFriendsTo(User user) {
			for (User friend : friends) {
				user.addFriend(friend);
			}
		}

	}
	private class TestableTripService extends TripService {

		@Override
		protected User getLoggedInUser() {
			return loggedInUser;
		}
		
		@Override
		protected List<Trip> tripsBy(User user) {
			return user.trips();
			
		}
	}
}
