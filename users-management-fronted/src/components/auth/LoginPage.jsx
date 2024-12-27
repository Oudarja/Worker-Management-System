import React, { useEffect, useState } from "react";

import { useNavigate } from "react-router-dom";

import UserService from "../service/UserService";

const LoginPage = () => {
  // Simpler code since only two fields are managed, making a
  // separate handleChange function unnecessary.
  //Unlike registration page here no handleInputChange function
  //has been used because only 2 fields so seperate separate states
  // are maintained
  /*
  Why Not Use handleChange for Login?
  The login form only has two fields (email and password), so separate
  onChange handlers suffice. Managing state in this way is more straightforward
  for small forms. Using a general-purpose handleChange function would add complexity
  without significant benefits for a form with just two fields.
  */

  const [email, setEmail] = useState("");

  const [password, setPassword] = useState("");

  const [error, setError] = useState("");

  const navigate = useNavigate();

  // Set custom title when the component mounts
  useEffect(() => {
    //This will show the page title as Login
    document.title = "Login"; // Set the desired title here
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const userData = await UserService.login(email, password);
      if (userData.token) {
        localStorage.setItem("token", userData.token);
        localStorage.setItem("role", userData.role);
        navigate("/profile");
        // Auto-reload the page after navigation
        window.location.reload();
      } else {
        setError(userData.message);
      }
    } catch (error) {
      console.log(error);
      setError(error.message);
      setTimeout(() => {
        setError("");
      }, 5000);
    }
  };

  return (
    <div className="auth-container">
      <h2>Login</h2>
      {error && <p className="error-message">{error}</p>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default LoginPage;
