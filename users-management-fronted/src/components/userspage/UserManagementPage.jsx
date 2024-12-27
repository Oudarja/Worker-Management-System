/*
This React component, UserManagementPage, manages a user interface for 
viewing, updating, and deleting users within the application. It is designed 
to interact with a backend API through the UserService class, making use of 
hooks like useState and useEffect for state management and lifecycle handling.
*/

//For managing state and performing side effects like fetching data after component mounting.
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
//A service class that interacts with the backend (e.g., API calls for fetching,
//  updating, or deleting users).
import UserService from "../service/UserService";

const UserManagementPage = () => {
  // Set custom title when the component mounts
  useEffect(() => {
    document.title = "Manage-user"; // Set the desired title here
  }, []);

  //users: Holds the list of users fetched from the backend.
  //setUsers: A function to update the users state.

  const [users, setUsers] = useState([]);
  /*
Runs once when the component mounts, ensuring the user list
is fetched from the backend.
Dependency array [] means the fetchUsers function is called
only when the component is first rendered.
*/
  useEffect(() => {
    // Fetch users data when the component mounts
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const token = localStorage.getItem("token"); // Retrieve the token from localStorage
      const response = await UserService.getAllUsers(token);
      //   console.log(response);
      setUsers(response.ourUsersList); // Assuming the list of users is under the key 'ourUsersList'
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  const deleteUser = async (userId) => {
    try {
      // Prompt for confirmation before deleting the user
      const confirmDelete = window.confirm(
        "Are you sure you want to delete this user?"
      );

      const token = localStorage.getItem("token"); // Retrieve the token from localStorage
      if (confirmDelete) {
        await UserService.deleteUser(userId, token);
        // After deleting the user, fetch the updated list of users
        fetchUsers();
      }
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  return (
    <div className="user-management-container">
      <h2>Users Management Page</h2>
      <button type="submit">
        <Link to="/register">Add User</Link>
      </button>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {/* Each table row consists with  */}
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>
                <button
                  className="delete-button"
                  onClick={() => deleteUser(user.id)}
                >
                  Delete
                </button>

                <button>
                  <Link to={`/update-user/${user.id}`}>Update</Link>
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserManagementPage;
