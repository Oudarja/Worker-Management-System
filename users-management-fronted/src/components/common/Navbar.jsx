import React from "react";
import UserService from "../service/UserService";
//Link: A component from react-router-dom for navigation without reloading the page.
import { Link, useNavigate } from "react-router-dom";

const Navbar = () => {
  //UserService provides utility methods for authentication (isAuthenticated, isAdmin, logout
  const isAuthenticated = UserService.isAuthenticated();
  const isAdmin = UserService.isAdmin();
  const navigate = useNavigate();
  const handleLogout = () => {
    //A confirmation dialog (window.confirm) appears to confirm the logout action
    const confirmDelete = window.confirm(
      "Are you sure you want to logout this user?"
    );
    //If confirmed, the logout method of UserService clears the user's token and role
    //  from localStorage, effectively logging the user out.
    if (confirmDelete) {
      UserService.logout();
      navigate("/");
      window.location.reload();
    }
  };

  return (
    <nav>
      <ul>
        {!isAuthenticated && (
          <li>
            <Link to="/">User Management Portal</Link>
          </li>
        )}
        {isAuthenticated && (
          <li>
            <Link to="/profile">Profile</Link>
          </li>
        )}
        {isAdmin && (
          <li>
            <Link to="/admin/user-management">User Management</Link>
          </li>
        )}
        {isAuthenticated && (
          <li>
            <Link to="/" onClick={handleLogout}>
              Logout
            </Link>
          </li>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
