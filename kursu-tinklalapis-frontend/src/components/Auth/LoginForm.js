/* eslint-disable */
import React, { useState } from 'react';
import './RegistrationLoginForm.css';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/images/logo.png';

function Login(props) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [emailError, setEmailError] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    let hasError = false;

    if (!email.trim()) {
      setEmailError('El. pastas negali būti tuščias');
      hasError = true;
    } else {
      setEmailError('');
    }

    if (!password.trim()) {
      setPasswordError('Slaptažodis negali būti tuščias');
      hasError = true;
    } else if (password.length < 4) {
      setPasswordError('Slaptažodis turi būti bent 8 simbolių ilgio');
      hasError = true;
    } else {
      setPasswordError('');
    }

    if (!hasError) {
      try {
        const response = await fetch('/user/authenticate', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ email, password }),
        });

        if (response.ok) {
          const data = await response.json();
          localStorage.setItem('token', data.access_token);
          localStorage.setItem('userId', data.userId);
          localStorage.setItem('role', data.userRole);
          props.onLogin({ email });
          navigate('/');
        } else {
          setEmailError('Vartotojas nerastas');
        }
      } catch (error) {
        console.error(error);
        alert('Įvyko klaida');
      }
    }
  };

  const inputErrorStyle = {
    borderBottom: '1px solid red',
    outlineColor: 'red',
  };

  return (
    <div className="RegistrationForm">
      <img src={logo} className="App-logo" alt="logo" />
      <form className="containerLogin" onSubmit={handleLogin}>
      <h2 className='registracija-title'>Prisijunkite</h2>
      {emailError ==='Vartotojas nerastas'}
        <div>
          <label htmlFor="username"></label>
          <input 
            type="text" 
            id="email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)}
            onFocus={(event) => event.target.placeholder = ''}
            onBlur={(event) => event.target.placeholder = 'El. pastas'}
            style={emailError ? inputErrorStyle : null}
            placeholder="El. pastas"
          />
          {emailError && <span className="error">{emailError}</span>}
        </div>
        <div>
          <label htmlFor="password"></label>
          <input 
            type="password" 
            id="password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)}
            onFocus={(event) => event.target.placeholder = ''}
            onBlur={(event) => event.target.placeholder = 'Slaptažodis'}
            style={passwordError ? inputErrorStyle : null}
            placeholder="Slaptažodis"
          />
          {passwordError && <span className="error">{passwordError}</span>}
        </div>
        <button type="submit">Prisijungti</button>
      </form>
    </div>
  );
  }
export default Login;