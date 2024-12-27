import React, { useEffect, useState } from "react";
import UserService from "../service/UserService";
import { Link } from "react-router-dom";

/*
This code defines a React functional component called ProfilePage.
It fetches and displays user profile information, allowing an admin
user to update their profile.
*/

const ProfilePage = () => {
  // Set custom title when the component mounts
  useEffect(() => {
    document.title = "Profile-page"; // Set the desired title here
  }, []);

  //Initializes profileInfo as an empty object.
  //Once the profile data is fetched, this state
  //is updated with the user's data.
  const [profileInfo, setProfileInfo] = useState({});

  /*
   Runs the fetchProfileInfo function when the component mounts
   ([] ensures this is called only once).as there has not been passed
    any dependency so it will runs for once. If there is passed any dependency 
    function or variable the each time that function called or variable
    changed the fetchprofileInfo function will be called.If there is not passed
    any things then it will be called continously
  */

  useEffect(() => {
    fetchProfileInfo();
  }, []);

  const fetchProfileInfo = async () => {
    try {
      //Retrieves the token from localStorage for authentication.
      const token = localStorage.getItem("token");
      //Calls UserService.getYourProfile(token) to fetch the user's profile.
      const response = await UserService.getYourProfile(token);
      //If successful, it updates profileInfo with the ourUsers data from the
      //  API response.
      setProfileInfo(response.ourUsers);
    } catch (error) {
      console.error("Error fetching profile information:", error);
    }
  };

  return (
    <div className="profile-page-container">
      <h2>Profile Information</h2>
      {/* //Display user's name, email, and city (retrieved from profileInfo state).
      //Values are conditionally rendered—only if they exist. */}
      <p>Name: {profileInfo.name}</p>
      <p>Email: {profileInfo.email}</p>
      <p>City: {profileInfo.city}</p>

      {/* If the user's role (profileInfo.role) is "ADMIN," display a button to update
       the profile.The button includes a Link navigating to /update-user/:id where id 
       is derived from the profile data. */}

      {profileInfo.role === "ADMIN" && (
        <button>
          <Link to={`/update-user/${profileInfo.id}`}>Update This Profile</Link>
        </button>
      )}
    </div>
  );
};

export default ProfilePage;
