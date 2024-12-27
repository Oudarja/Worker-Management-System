// App.js
import React from "react";
//React Components like Navbar, Footer, LoginPage, ProfilePage, etc.,
//  to construct the app UI.
/*
BrowserRouter: Enables routing in a React single-page app (SPA).
Routes and Route: Define routes to render specific components for specific paths.
Navigate: Handles programmatic redirection.
*/
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Navbar from "./components/common/Navbar";
import LoginPage from "./components/auth/LoginPage";
import ProfilePage from "./components/userspage/ProfilePage";
import UserService from "./components/service/UserService";
import RegistrationPage from "./components/auth/RegistrationPage";
import UserManagementPage from "./components/userspage/UserManagementPage";
import UpdateUser from "./components/userspage/UpdateUser";
import Footer from "./components/common/Footer";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        {/* Navbar: A top navigation bar for linking different parts of the app. */}
        <Navbar />
        <div className="content">
          {/* Routes: Dynamically renders components based on the URL path. */}
          <Routes>
            {/* LoginPage: Rendered for both / and /login paths.
              ProfilePage: Rendered for /profile, typically protected 
              for authenticated users. */}
            <Route exact path="/" element={<LoginPage />} />
            <Route exact path="/login" element={<LoginPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            {/* Check if user is authenticated and admin before rendering admin-only routes */}
            {/* If admin-only logic evaluates to true, additional routes become available */}
            {UserService.adminOnly() && (
              <>
                <Route path="/register" element={<RegistrationPage />} />
                <Route
                  path="/admin/user-management"
                  element={<UserManagementPage />}
                />
                <Route path="/update-user/:userId" element={<UpdateUser />} />
              </>
            )}

            {/* Redirects any undefined routes (*) to the login page (/login). */}
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </div>
        {/* //FooterComponent: A footer displayed on all pages. */}
        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;
