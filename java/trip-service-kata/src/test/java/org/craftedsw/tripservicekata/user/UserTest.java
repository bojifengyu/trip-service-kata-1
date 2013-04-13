package org.craftedsw.tripservicekata.user;
import static org.craftedsw.tripservicekata.trip.UserBuilder.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private static final User BOB = new User();
	private static final User ANOTHER_USER = new User();
	private User user;

	@Before
	public void setUp() {
		user = aUser().friendsWith(BOB).build();

	}
	@Test public void
	should_inform_when_user_is_not_friends_with() {
		assertThat(user.isFriendsWith(ANOTHER_USER), is(false));
	}
	
	@Test public void
	should_inform_when_user_is_friends_with() {
		user.addFriend(ANOTHER_USER);
		assertThat(user.isFriendsWith(ANOTHER_USER), is(true));
	}
}
