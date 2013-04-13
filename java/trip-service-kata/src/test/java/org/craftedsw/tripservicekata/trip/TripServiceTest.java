package org.craftedsw.tripservicekata.trip;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.craftedsw.tripservicekata.trip.UserBuilder.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

	private static final User UNUSED_USER = null;
	private static final User GUEST = null;
	private static final User REGISTERED_USER = new User();
	private static final User ANOTHER_USER = new User();
	private static final Trip TO_BRAZIL = new Trip();
	private static final Trip TO_LONDON = new Trip();

	@Mock private TripDAO  tripDAO;
	@InjectMocks @Spy private TripService realTripService = new TripService();
	private TripService tripService;

	@Before
	public void initialize() {
		tripService = new TestableTripService();
	}

	@Test(expected = UserNotLoggedInException.class)
	public void should_throw_exception_user_not_logged_in() {
		realTripService.getTripsByUser(UNUSED_USER, GUEST);
	}

	@Test
	public void should_not_return_any_trips_when_users_are_not_friends() {
		User friend = aUser()
				.friendsWith(ANOTHER_USER)
				.withTrips(TO_BRAZIL).build();

		List<Trip> tripList = realTripService.getTripsByUser(friend, REGISTERED_USER);

		assertThat(tripList.size(), is(0));
	}

	@Test
	public void should_return_trips_when_users_are_friends() {
		User friend = aUser()
				.friendsWith(ANOTHER_USER, REGISTERED_USER)
				.withTrips(TO_BRAZIL, TO_LONDON).build();

		List<Trip> tripList = tripService.getTripsByUser(friend, REGISTERED_USER);

		assertThat(tripList.size(), is(2));
	}

	private class TestableTripService extends TripService {

		@Override
		protected List<Trip> tripsBy(User user) {
			return user.trips();

		}
	}
}
