import React, { useState, useEffect } from "react";
import axios from "axios";

const ProfilePage = () => {
  const [user, setUser] = useState({});
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios
      .get(`/students/${userId}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
      .then((response) => {
        setUser(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [userId]);

  const handleDelete = (registrationId) => {
    const token = localStorage.getItem('token');
    axios
      .delete(`/api/registrations/${registrationId}/delete`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
      .then(() => {
        setUser((prevUser) => ({
          ...prevUser,
          registrations: prevUser.registrations.filter(
            (registration) => registration.id !== registrationId
          ),
        }));
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <div>
      <h1>Profile Page</h1>

      {user && (
        <div>
          <h2>Vardas Pavardė </h2>
          <h2>{user.firstname} {user.lastname}</h2>
          <h2>{user.email}</h2>

          <h3>Registrations:</h3>
          <ul>
            {user.registrations &&
              user.registrations.map((registration) => (
                <li key={registration.id}>
                  {registration.course.description}
                  <button onClick={() => handleDelete(registration.id)}>
                    Delete
                  </button>
                </li>
              ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default ProfilePage;