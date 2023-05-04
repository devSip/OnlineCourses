/* eslint-disable no-unused-vars */
import React, { useState } from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import HomePage from './components/HomePage/HomePage';
import RegistrationForm from './components/Auth/RegistrationForm';
import LoginForm from './components/Auth/LoginForm';
import LoggedInHomePage from './components/HomePage/LoggedInHomePage.js';
import ProfilePage from './components/ProfilePage/ProfilePage';
import Matematika from './components/SubjectPages/Matematika';
import Anglu from './components/SubjectPages/Anglu';
import Istorija from './components/SubjectPages/Istorija';
import Biologija from './components/SubjectPages/Biologija';
import Admin from './components/Admin/Admin';
import AdminHomePage from './components/Admin/AdminHomePage.js'

function App() {
  const [loggedInUser, setLoggedInUser] = useState(
    JSON.parse(sessionStorage.getItem('loggedInUser')) || {}
  );
  const [isLoggedIn, setIsLoggedIn] = useState(
    sessionStorage.getItem('isLoggedIn') === 'true'
  );
  const [role, setRole] = useState(
    localStorage.getItem('role')
  );

  function handleLogin(user) {
    setIsLoggedIn(true);
    setLoggedInUser(user);
    sessionStorage.setItem('isLoggedIn', 'true');
    sessionStorage.setItem('loggedInUser', JSON.stringify(user));
  }

  function handleLogout() {
    console.log('Logging out...');
    setIsLoggedIn(false);
    setLoggedInUser({});
    localStorage.removeItem('role');
    sessionStorage.removeItem('isLoggedIn');
    sessionStorage.removeItem('loggedInUser');
  }

  return (
    <div className="App">
      <Router>
        <Routes>
          {isLoggedIn ? (
            <Route
              path="/"
              element={
                role === "ADMIN" ? (
                  <AdminHomePage onLogout={handleLogout} username={loggedInUser.username} />
                ) : (
                  <LoggedInHomePage onLogout={handleLogout} username={loggedInUser.username} />
                )
              }
            />
          ) : (
            <Route path="/" element={<HomePage onLogin={handleLogin} />} />
          )}
          <Route path="/registration" element={<RegistrationForm />} />
          <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />
          <Route
            path="/profile"
            element={
              isLoggedIn ? (
                <ProfilePage username={loggedInUser.username} />
              ) : (
                <Navigate to="/" />
              )
            }
          />
          <Route path="/matematika" element={<Matematika isLoggedIn={isLoggedIn} />} />
          <Route path="/anglu" element={<Anglu isLoggedIn={isLoggedIn} />} />
          <Route path="/istorija" element={<Istorija isLoggedIn={isLoggedIn} />} />
          <Route path="/biologija" element={<Biologija isLoggedIn={isLoggedIn} />} />
          <Route
            path="/admin"
            element={
              role === 'ADMIN' ? (
                <Admin />
              ) : (
                <Navigate to="/" />
              )
            }
          />
        </Routes>
      </Router>
    </div>
  );
}

export default App;


