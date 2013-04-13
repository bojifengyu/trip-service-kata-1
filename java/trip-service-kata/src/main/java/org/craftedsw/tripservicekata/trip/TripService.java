package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

//	@Autowired private TripDAO tripDAO;
	
	public List<Trip> getTripsByUser(User user, User loggedInUser) throws UserNotLoggedInException {
		if (loggedInUser == null) {
			throw new UserNotLoggedInException();
		}
		return user.isFriendsWith(loggedInUser)
				? tripsBy(user)
				: noTrips();
	}

	private ArrayList<Trip> noTrips() {
		return new ArrayList<Trip>();
	}

	protected List<Trip> tripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}
}
