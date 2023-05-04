/* eslint-disable */
import React, { useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus, faTimes } from '@fortawesome/free-solid-svg-icons';
import './Subject.css';

function Istorija({ isLoggedIn, user }) {
  const [courses, setCourses] = useState([]);
  const [expandedCourseId, setExpandedCourseId] = useState(null);
  const [buttonText, setButtonText] = useState('Dalyvauti');
  const [buttonDisabled, setButtonDisabled] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    fetch('/courses', {
      headers: {
        'Authorization': `Bearer ${token}`
      },
    })
      .then(response => response.json())
      .then(data => setCourses(data.filter(course => course.subject === 'Humanitariniai mokslai')))
      .catch(error => console.error(error));
  }, []);

  const toggleCourse = (id) => {
    if (id === expandedCourseId) {
      setExpandedCourseId(null);
    } else {
      setExpandedCourseId(id);
    }
  };

  const handleButtonClick = (courseId) => {
    if (!isLoggedIn) {
      alert('Please log in or register to participate.');
      return;
    }

    const userId = parseInt(localStorage.getItem('userId'));

    const registration = { courseId: parseInt(courseId), userId: userId };
    setButtonDisabled(true);
    const token = localStorage.getItem('token');
    var registrationJson = JSON.stringify(registration);
    fetch('/api/registrations', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: registrationJson,
    })
      .then(() => {
        window.location.href = '/profile';
      })
      .catch(error => {
        console.error(error);
      });
  };

  return (
    <div className="Matematika">
      <h2 className='humanitariniaiMokslai'>Humanitariniai Mokslai</h2>
      {courses.map(course => {
        return (
          <div key={course.id} className="course-container">
            <div className="course-header" onClick={() => toggleCourse(course.id)}>
              <h3 className="description">{course.description}</h3>
              <FontAwesomeIcon className='icon' icon={expandedCourseId === course.id ? faTimes : faPlus} />
            </div>
            {expandedCourseId === course.id && (
              <div className="course-description">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.<br></br><br></br>
                Kurso profesorius - {course.professorName}
                <div className="button-container">
                  <button className="courseButton" onClick={() => handleButtonClick(course.id)} disabled={buttonDisabled}>
                    {buttonText}
                  </button>
                </div>
              </div>
            )}
          </div>
        );
      })}
    </div>
  );
}

export default Istorija;