package com.ticketing.server.user.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserGrade {
	ADMIN(ROLES.ADMIN, null),
	STAFF(ROLES.STAFF, ROLES.ADMIN),
	GUEST(ROLES.GUEST, ROLES.STAFF);

	private final String roleName;
	private final String parentName;

	public static class ROLES {

		public static final String ADMIN = "ROLE_ADMIN";
		public static final String STAFF = "ROLE_STAFF";
		public static final String GUEST = "ROLE_GUEST";

		private ROLES() {
		}
	}

	public static String getRoleHierarchy() {
		StringBuilder sb = new StringBuilder();

		for (UserGrade grade : UserGrade.values()) {
			if (grade.parentName != null) {
				sb.append(grade.parentName);
				sb.append(" > ");
				sb.append(grade.roleName);
				sb.append("\n");
			}
		}

		return sb.toString();
	}
}
