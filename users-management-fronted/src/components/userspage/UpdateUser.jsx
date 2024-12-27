/*
This line imports the React library along with the useState and 
useEffect hooks, which are necessary for managing state and side 
effects in functional components.
*/

import React, { useEffect, useState } from "react";
//This imports useParams to access URL parameters and useNavigate
// //to programmatically navigate between routes.
import { useNavigate, useParams } from "react-router-dom";
//This imports a service module that handles API calls related to user data.
import UserService from "../service/UserService";

const UpdateUser = () => {
  // Set custom title when the component mounts
  useEffect(() => {
    document.title = "User-update"; // Set the desired title here
  }, []);

  //This initializes the navigate function, which will be used to navigate
  //  to different routes.
  const navigate = useNavigate();
  const { userId } = useParams();
  /*
This initializes the userData state with an object containing empty strings
for name, email, role, and city. setUserData is the function used to update
this state.
*/
  const [userData, setUserData] = useState({
    name: "",
    email: "",
    role: "",
    city: "",
  });

  //This hook runs when the component mounts or when userId changes.
  //  It calls fetchUserDataById to fetch the user's data based on the userId.
  useEffect(() => {
    fetchUserDataById(userId); // Pass the userId to fetchUserDataById
  }, [userId]); //when ever there is a change in userId, run this

  const fetchUserDataById = async (userId) => {
    try {
      //This retrieves the authentication token from local storage, which
      // is needed for making secure API calls.
      const token = localStorage.getItem("token");

      const response = await UserService.getUserById(userId, token); // Pass userId to getUserById
      //This destructures the response to extract name, email, role, and city
      //  from the ourUsers object.
      const { name, email, role, city } = response.ourUsers;
      //This updates the userData state with the fetched user information.
      setUserData({ name, email, role, city });
    } catch (error) {
      console.error("Error fetching user data:", error);
    }
  };

  const handleInputChange = (e) => {
    //This destructures the name and value from the event
    //  target (the input field that changed).
    const { name, value } = e.target;

    setUserData((prevUserData) => ({
      ...prevUserData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    //This prevents the default form submission behavior, which
    // would refresh the page.
    e.preventDefault();
    try {
      const confirmUpdate = window.confirm(
        "Are you sure you want to update this user's info?"
      );
      if (confirmUpdate) {
        //This retrieves the authentication token again for the
        //  update request.
        const token = localStorage.getItem("token");
        //This calls the updateUser method from UserService, passing in
        //  the userId, userData, and token, and waits for the response.
        const res = await UserService.updateUser(userId, userData, token);
        console.log(res);
        // Redirect to profile page or display a success message
        navigate("/admin/user-management");
      }
    } catch (error) {
      console.error("Error updating user profile:", error);
      alert(error);
    }
  };

  return (
    <div className="auth-container">
      <h2>Update User</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Name:</label>
          <input
            type="text"
            name="name"
            value={userData.name}
            onChange={handleInputChange}
          />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input
            type="email"
            name="email"
            value={userData.email}
            onChange={handleInputChange}
          />
        </div>
        <div className="form-group">
          <label>Role:</label>
          <input
            type="text"
            name="role"
            value={userData.role}
            onChange={handleInputChange}
          />
        </div>
        <div className="form-group">
          <label>City:</label>
          <input
            type="text"
            name="city"
            value={userData.city}
            onChange={handleInputChange}
          />
        </div>
        <button type="submit">Update</button>
      </form>
    </div>
  );
};

export default UpdateUser;
