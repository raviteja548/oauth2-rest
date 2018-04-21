/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oauth.security.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oauth.security.model.User;
import com.oauth.security.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
		}
		 /*List<SimpleGrantedAuthority> authList = new ArrayList<>();
		  * authList.add(new SimpleGrantedAuthority("ROLE_USER"));
			UserDetails userDetails = (UserDetails)new org.springframework.security.core.userdetails.User(user.getLogin()
					,user.getPassword(), authList);*/
		/*UserRepositoryUserDetails details = new UserRepositoryUserDetails(user);
		return details;*/
		UserDetails x = null;
		if(username.equals("roy"))
		x = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), true, true, true, true,
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		if(username.equals("craig"))
			x = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), true, true, true, true,
					AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
		if(username.equals("greg"))
			x = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), true, true, true, true,
					AuthorityUtils.createAuthorityList("ROLE_USER","ROLE_ADMIN"));
		return x;
	}

	private final static class UserRepositoryUserDetails extends User implements UserDetails {

		private static final long serialVersionUID = 1L;

		private UserRepositoryUserDetails(User user) {
			super(user);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return getRoles();
		}

		@Override
		public String getUsername() {
			return getLogin();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

	}

}
