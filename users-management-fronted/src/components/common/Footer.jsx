import React from "react";

const Footer = () => {
  return (
    <div>
      <footer className="footer">
        <span>
          {/* &copy; is an HTML entity for the copyright symbol (©) */}
          {/* The new Date() object is a JavaScript built-in function that returns the current date and time.
              Calling .getFullYear() extracts the current year as a 4-digit number (e.g., 2024).
              The {} in JSX allows embedding JavaScript expressions inside HTML-like syntax. */}
          Oudarja002 | All Right Reserved&copy; {new Date().getFullYear()}
        </span>
      </footer>
    </div>
  );
};

export default Footer;
