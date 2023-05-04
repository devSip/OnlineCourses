import React, { useState } from 'react';
import './Admin.css';

const Admin = () => {
  const [users, setUsers] = useState([]);
  const [editableUser, setEditableUser] = useState(null);
  const [courses, setCourses] = useState([]);
  const [editableCourse, setEditableCourse] = useState(null);
  const [professors, setProfessors] = useState([]);
  const [editableProfessor, setEditableProfessor] = useState(null);
  const [registrations, setRegistrations] = useState([]);
  const [editableRegistration, setEditableRegistration] = useState(null);
  const [showUsers, setShowUsers] = useState(false);
  const [showCourses, setShowCourses] = useState(false);
  const [showProfessors, setShowProfessors] = useState(false);
  const [showRegistrations, setShowRegistrations] = useState(false);

  const handleGetUsers = async () => {
    setShowUsers(!showUsers);
    try {
      const token = localStorage.getItem('token');
      const response = await fetch('/students', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      const data = await response.json();
      setUsers(data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleDeleteUser = async (userId) => {
    const token = localStorage.getItem('token');
    try {
      await fetch(`/students/${userId}/delete`, {
        method: 'DELETE', 
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      setUsers(users.filter((user) => user.id !== userId));
    } catch (error) {
      console.error(error);
    }
  };

  const handleEditUser = (user) => {
    setEditableUser(user);
  };

  const handleSaveUser = async (user) => {
    const token = localStorage.getItem('token');
    try {
      const modifiedUser = {
        firstname: user.firstname,
        lastname: user.lastname,
        email: user.email,
        password: user.password,
      };
      await fetch(`/students/${user.id}/update`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(modifiedUser),
      });
      const response = await fetch('/students', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      const data = await response.json();
      setUsers(data);
      setEditableUser(null);
    } catch (error) {
      console.error(error);
    }
  };

  const handleGetCourses = async () => {
    setShowCourses(!showCourses);
    try {
      const response = await fetch("/courses");
      const data = await response.json();
      setCourses(data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleDeleteCourse = async (courseId) => {
    const token = localStorage.getItem('token');
    try {
      await fetch(`/courses/${courseId}/delete`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      setCourses(courses.filter((course) => course.id !== courseId));
    } catch (error) {
      console.error(error);
    }
  };

  const handleEditCourse = (course) => {
    setEditableCourse(course);
  };

  const handleSaveCourse = async (course) => {
    try {
      const token = localStorage.getItem('token');
      const modifiedCourse = {
        description: course.description,
        professorName: course.professorName,
        subject: course.subject
      };
      await fetch(`/courses/${course.id}/update`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(modifiedCourse),
      });
      const response = await fetch('/courses');
      const data = await response.json();
      setCourses(data);
      setEditableCourse(null);
    } catch (error) {
      console.log(error);
    }
  };

  const handleCreateCourse = async (event) => {
    event.preventDefault();
    try {
      const token = localStorage.getItem('token');
      const response = await fetch("/courses", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          subject: event.target.elements.subject.value,
          description: event.target.elements.description.value,
          professorName: event.target.elements.professorName.value
        }),
      });
      const data = await response.json();
      setCourses([...courses, data]);
    } catch (error) {
      console.error(error);
    }
  };

  const handleGetProfessors = async () => {
    setShowProfessors(!showProfessors);
    try {
      const response = await fetch("/professors");
      const data = await response.json();
      setProfessors(data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleEditProfessor = (professor) => {
    setEditableProfessor(professor);
  };

  const handleSaveProfessor = async (professor) => {
    try {
      const token = localStorage.getItem('token');
      const modifiedProfessor = {
        email: professor.email,
        fullName: professor.fullName
      };
      await fetch(`/professors/${professor.id}/update`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(modifiedProfessor),
      });
      const response = await fetch('/professors');
      const data = await response.json();
      setProfessors(data);
      setEditableProfessor(null);
    } catch (error) {
      console.log(error);
    }
  };

  const handleDeleteProfessor = async (professorId) => {
    try {
      const token = localStorage.getItem('token');
      await fetch(`/professors/${professorId}/delete`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
      });
      setProfessors(professors.filter((professor) => professor.id !== professorId));
    } catch (error) {
      console.error(error);
    }
  };

  const handleCreateProfessor = async (event) => {
    event.preventDefault();
    try {
      const token = localStorage.getItem('token');
      const response = await fetch("/professors", {
        method: 'POST',
        headers: {
          "Content-Type": "application/json",
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          email: event.target.elements.email.value,
          fullName: event.target.elements.fullName.value
        }),
      });
      const data = await response.json();
      setProfessors([...professors, data]);
    } catch (error) {
      console.error(error);
    }
  };

  const handleGetRegistrations = async () => {
    setShowRegistrations(!showRegistrations);
    try {
      const token = localStorage.getItem('token');
      const response = await fetch("/api/registrations", {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      const data = await response.json();
      setRegistrations(data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleEditRegistration = (registration) => {
    setEditableRegistration(registration);
  };

  const handleSaveRegistration = async (registration) => {
    try {
      const token = localStorage.getItem('token');
      const modifiedRegistration = {
        course: {
        id: registration.courseId,
      },
      user: {
        id: registration.userId,
      }
    };
      await fetch(`/api/registrations/${registration.id}/update`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(modifiedRegistration),
      });
      const response = await fetch('/api/registrations', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      const data = await response.json();
      setRegistrations(data);
      setEditableRegistration(null);
    } catch (error) {
      console.log(error);
    }
  };

  const handleDeleteRegistration = async (registrationId) => {
    try {
      const token = localStorage.getItem('token');
      await fetch(`/api/registrations/${registrationId}/delete`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`
        },
      });
      setRegistrations(registrations.filter((registration) => registration.id !== registrationId));
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className='body'>
      <h1 className='title'>Admin Panel</h1>
      <button className="buttonStudents" onClick={handleGetUsers}>Get Students</button>
      <button className="buttonCourses" onClick={handleGetCourses}>Get Courses</button>
      <button className="buttonProfessors" onClick={handleGetProfessors}>Get Professors</button>
      <button className="buttonRegistrations" onClick={handleGetRegistrations}>Get Registrations</button>
      {showRegistrations && (
        <div className="show">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Course ID</th>
              <th>User ID</th>
            </tr>
          </thead>
          <tbody>
            {registrations.map((registration) => (
              <tr key={registration.id}>
                <td>{registration.id}</td>
                <td>
                  {editableRegistration?.id === registration.id ? (
                    <input
                      type="text"
                      value={editableRegistration.courseId}
                      onChange={(e) =>
                        setEditableRegistration({ ...editableRegistration, courseId: e.target.value })
                      }
                    />
                  ) : (
                    registration.course.id
                  )}
                </td>
                <td>
                  {editableRegistration?.id === registration.id ? (
                    <input
                      type="text"
                      value={editableRegistration.userId}
                      onChange={(e) =>
                        setEditableRegistration({ ...editableRegistration, userId: e.target.value })
                      }
                    />
                  ) : (
                    registration.user.id
                  )}
                </td>
                <td>
                  {editableRegistration?.id === registration.id ? (
                    <>
                      <button className="buttonSave" onClick={() => handleSaveRegistration(editableRegistration)}>Save</button>
                      <button className="buttonCancel" onClick={() => setEditableRegistration(null)}>Cancel</button>
                    </>
                  ) : (
                    <>
                      <button className="buttonEdit" onClick={() => handleEditRegistration(registration)}>Edit</button>
                      <button className="buttonDelete" onClick={() => handleDeleteRegistration(registration.id)}>Delete</button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        </div>
      )}
      
      {showProfessors && (
        <div className="show">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Full Name</th>
              <th>Email</th>
            </tr>
          </thead>
          <tbody>
            {professors.map((professor) => (
              <tr key={professor.id}>
                <td>{professor.id}</td>
                <td>
                  {editableProfessor?.id === professor.id ? (
                    <input
                      type="text"
                      value={editableProfessor.email}
                      onChange={(e) =>
                        setEditableProfessor({ ...editableProfessor, email: e.target.value })
                      }
                    />
                  ) : (
                    professor.email
                  )}
                </td>
                <td>
                  {editableProfessor?.id === professor.id ? (
                    <input
                      type="text"
                      value={editableProfessor.fullName}
                      onChange={(e) =>
                        setEditableProfessor({ ...editableProfessor, fullName: e.target.value })
                      }
                    />
                  ) : (
                    professor.fullName
                  )}
                </td>
                <td>
                  {editableProfessor?.id === professor.id ? (
                    <>
                      <button className="buttonSave" onClick={() => handleSaveProfessor(editableProfessor)}>Save</button>
                      <button className="buttonCancel" onClick={() => setEditableProfessor(null)}>Cancel</button>
                    </>
                  ) : (
                    <>
                      <button className="buttonEdit" onClick={() => handleEditProfessor(professor)}>Edit</button>
                      <button className="buttonDelete" onClick={() => handleDeleteProfessor(professor.id)}>Delete</button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        </div>
      )}

      {showProfessors && (
        <div>
          <h2>Create a New Professor</h2>
          <form onSubmit={handleCreateProfessor}>
            <label htmlFor="email">Email:</label>
            <input type="text" id="email" name="email" />
            <label className="professorNameInputTitle" htmlFor="fullName">Professor Name:</label>
            <input type="text" id="fullName" name="fullName" />
            <button className="buttonCreateProfessor" type="submit">Create Professor</button>
          </form>
        </div>
      )}

      {showCourses && (
        <div className="show">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Subject</th>
              <th>Description</th>
              <th>Professor Name</th>
              <th>Edit/Delete</th>
            </tr>
          </thead>
          <tbody>
            {courses.map((course) => (
              <tr key={course.id}>
                <td>{course.id}</td>
                <td>
                  {editableCourse?.id === course.id ? (
                    <input
                      type="text"
                      value={editableCourse.subject}
                      onChange={(e) =>
                        setEditableCourse({ ...editableCourse, subject: e.target.value })
                      }
                    />
                  ) : (
                    course.subject
                  )}
                </td>
                <td>
                  {editableCourse?.id === course.id ? (
                    <input
                      type="text"
                      value={editableCourse.description}
                      onChange={(e) =>
                        setEditableCourse({ ...editableCourse, description: e.target.value })
                      }
                    />
                  ) : (
                    course.description
                  )}
                </td>
                <td>
                  {editableCourse?.id === course.id ? (
                    <input
                      type="text"
                      value={editableCourse.professorName}
                      onChange={(e) =>
                        setEditableCourse({ ...editableCourse, professorName: e.target.value })
                      }
                    />
                  ) : (
                    course.professorName
                  )}
                </td>
                <td>
                  {editableCourse?.id === course.id ? (
                    <>
                      <button className="buttonSave" onClick={() => handleSaveCourse(editableCourse)}>Save</button>
                      <button className="buttonCancel" onClick={() => setEditableCourse(null)}>Cancel</button>
                    </>
                  ) : (
                    <>
                      <button className="buttonEdit" onClick={() => handleEditCourse(course)}>Edit</button>
                      <button className="buttonDelete" onClick={() => handleDeleteCourse(course.id)}>Delete</button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        </div>
      )}
      {showCourses && (
        <div>
          <h2>Create a New Course</h2>
          <form onSubmit={handleCreateCourse}>
            <label htmlFor="subject">Subject:</label>
            <input type="text" id="subject" name="subject" />
            <label className='professorNameInputTitle' htmlFor="description">Description:</label>
            <input type="text" id="description" name="description" />
            <label className='professorNameInputTitle' htmlFor="professorName">Professor Name:</label>
            <input type="text" id="professorName" name="professorName" />
            <button className="buttonCreateProfessor" type="submit">Create Course</button>
          </form>
        </div>
      )}

      {showUsers && (
        <div className="show">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
              <th>Edit/Delete</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td className="space">
                  {editableUser?.id === user.id ? (
                    <input
                      type="text"
                      value={editableUser.firstname}
                      onChange={(e) =>
                        setEditableUser({ ...editableUser, firstname: e.target.value })
                      }
                    />
                  ) : (
                    user.firstname
                  )}
                </td>
                <td>
                  {editableUser?.id === user.id ? (
                    <input
                      type="text"
                      value={editableUser.lastname}
                      onChange={(e) =>
                        setEditableUser({ ...editableUser, lastname: e.target.value })
                      }
                    />
                  ) : (
                    user.lastname
                  )}
                </td>
                <td>
                  {editableUser?.id === user.id ? (
                    <input
                      type="text"
                      value={editableUser.email}
                      onChange={(e) =>
                        setEditableUser({ ...editableUser, email: e.target.value })
                      }
                    />
                  ) : (
                    user.email
                  )}
                </td>
                <td>
                  {editableUser?.id === user.id ? (
                    <>
                      <button className="buttonSave" onClick={() => handleSaveUser(editableUser)}>Save</button>
                      <button className="buttonCancel" onClick={() => setEditableUser(null)}>Cancel</button>
                    </>
                  ) : (
                    <>
                      <button className="buttonEdit" onClick={() => handleEditUser(user)}>Edit</button>
                      <button className="buttonDelete" onClick={() => handleDeleteUser(user.id)}>Delete</button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        </div>
      )}
    </div>
  );
};

export default Admin;
