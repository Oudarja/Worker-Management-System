import React, { useEffect, useState } from "react";
import UserService from "../service/UserService";
import { useNavigate } from "react-router-dom";

/*
The RegistrationPage is implemented as a React functional component.
This is the structure of a modern React component that uses hooks like
useState and useNavigate for state management and navigation
*/

/*
useState: A React hook to manage state in functional components.
UserService: A service class for making API calls related to user registration
(uses axios in the service methods).
useNavigate: A React Router hook for programmatically navigating between pages.
*/

const RegistrationPage = () => {
  // Set custom title when the component mounts
  useEffect(() => {
    document.title = "Registration"; // Set the desired title here
  }, []);

  const navigate = useNavigate();

  /*
formData: Holds the form input values such as name, email, password, role, and city.
setFormData: A function to update the formData state.

The initial state is an object with empty values for all fields.
*/
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    role: "",
    city: "",
  });

  //Purpose: This function updates the respective field in formData when a user types in the input.

  /*
Destructuring: Extracts the name (field name) and value (user input) from the input event object (e).
Update State: Copies the existing formData values and updates only the changed field using 
the spread operator (...formData).
*/

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    //e.preventDefault() stops the page from reloading after form submission.
    e.preventDefault();
    try {
      // Call the register method from UserService

      const token = localStorage.getItem("token");
      //Call to API: UserService.register is called to handle the registration process
      // via an API endpoint.
      await UserService.register(formData, token);

      // Clear the form fields after successful registration
      setFormData({
        name: "",
        email: "",
        password: "",
        role: "",
        city: "",
      });
      alert("User registered successfully");
      navigate("/admin/user-management");
    } catch (error) {
      console.error("Error registering user:", error);
      alert("An error occurred while registering user");
    }
  };

  return (
    <div>
      <h2>Registration</h2>

      {/* The form uses controlled components where the value of each field is tied to formData:
   Field Input: The value of each input field is bound to a key in the formData state.
   Dynamic Change: Updates the formData state dynamically as the user types. */}

      {/* value={formData.name}: The name field's value comes from the formData state. */}
      {/* onChange={handleInputChange}: Updates the formData state on every keystroke. */}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Name:</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Password:</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Role:</label>
          <input
            type="text"
            name="role"
            value={formData.role}
            onChange={handleInputChange}
            placeholder="Enter your role"
            required
          />
        </div>
        <div className="form-group">
          <label>City:</label>
          <input
            type="text"
            name="city"
            value={formData.city}
            onChange={handleInputChange}
            placeholder="Enter your city"
            required
          />
        </div>
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default RegistrationPage;
