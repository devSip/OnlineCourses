/* eslint-disable */
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import logo from '../assets/images/logo.png';
import './Homepage.css';

function Header() {
  return (
    <header>
      <div className="header-buttons">
        <Link to="/login">
          <button className="button-primary">Prisijungti</button>
        </Link>
        <Link to="/registration">
          <button className="button-secondary">Registruotis</button>
        </Link>
      </div>
      <img src={logo} className="App-logo" alt="logo" />
    </header>
  );
}

function Homepage() {

  const handleButtonClick = (course) => {
    window.location.href = `/${course}`;
  };

  return (
    <div className="Homepage">
      <Header />
      <div className="Homepage-title">
        <h2>Atrakinkite neribotą prieigą prie šimtų kursų.</h2>
        <h2 className='title-2'>Mokykitės bet kur.</h2>
        <h2 className='title-3'>Pasiruošę pradėti mokytis? Registruokites.</h2>
      </div>
      <div class="button-row">
        <button className="math-button" onClick={() => handleButtonClick('matematika')}>
        Tikslieji Mokslai</button>
        <button className="english-button" onClick={() => handleButtonClick('anglu')}>
        Socialiniai Mokslai</button>
      </div>
      <div class="button-row">
        <button className="history-button" onClick={() => handleButtonClick('istorija')}>
        Humanitariniai Mokslai</button>
        <button className="biology-button" onClick={() => handleButtonClick('biologija')}>
        Gamtos Mokslai</button>
      </div>
      <footer className="footer">
        &copy; 2023 
      </footer>
    </div>
  );
}

export default Homepage;