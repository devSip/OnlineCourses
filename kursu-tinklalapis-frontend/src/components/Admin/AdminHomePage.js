/* eslint-disable */
import React from 'react';
import logo from '../assets/images/logo.png';
import '../HomePage/Homepage.css';

function Header(props) {
    const handleAdminButtonClick = () => {
      window.location.href = '/admin';
    }

    const handleLogoutClick = () => {
      props.onLogout(); 
    };

    return (
      <header>
        <div className="header-buttons">
            <button className="button-primary" onClick={handleAdminButtonClick}>
              Admin
            </button>
            <button className="button-secondary" onClick={handleLogoutClick}>
              Atsijungti
            </button>
        </div>
        <img src={logo} className="App-logo" alt="logo" />
      </header>
    );
  }

function LoggedInHomePage(props) {
    const handleButtonClick = (course) => {
      window.location.href = `/${course}`;
  };

  return (
    <div className="Homepage">
      <Header onLogout={props.onLogout} />
      <div className="Homepage-title">
        <h2>Atrakinkite neribotą prieigą prie šimtų kursų.</h2>
        <h2 className='title-2'>Mokykitės bet kur.</h2>
        <h2 className='title-3'>Pasiruošę pradėti mokytis? Registruokites.</h2>
      </div>
      <div className="button-row">
        <button className="math-button" onClick={() => handleButtonClick('matematika')}>
        Tikslieji Mokslai</button>
        <button className="english-button" onClick={() => handleButtonClick('anglu')}>
        Socialiniai Mokslai</button>
      </div>
      <div className="button-row">
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

export default LoggedInHomePage;