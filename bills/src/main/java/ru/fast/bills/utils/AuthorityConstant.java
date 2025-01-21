package ru.fast.bills.utils;

public class AuthorityConstant {

    public final static String SUPER_ADMIN = "hasAuthority('SUPER_ADMIN')";
    public final static String MOBILE_APP = "hasAuthority('MOBILE_APP')";
    public final static String STAFF_APP = "hasAuthority('STAFF_APP')";
    public final static String SUPER_ADMIN_OR_STAFF_APP = "hasAnyAuthority('SUPER_ADMIN', 'STAFF_APP')";
}
