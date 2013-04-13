package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.DependendClassCallDuringUnitTestException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

public class TripDAOTest {

	@Test(expected = DependendClassCallDuringUnitTestException.class) public void
	should_throw_exception_when_asked_for_user_trips() {
		TripDAO td = new TripDAO();
		td.tripsBy(new User());
	}
}
