import { useState } from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [showCheckinModal, setShowCheckinModal] = useState(false);
  const [tokenInput, setTokenInput] = useState('');
  const [toast, setToast] = useState(null);

  const showToast = (message, success = true) => {
    setToast({ message, success });
    setTimeout(() => setToast(null), 3000);
  };

  const cancelReservation = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/reservations/${tokenInput}`, {
        method: 'DELETE'
      });

      if (response.ok) {
        showToast("Reservation cancelled successfully", true);
      } else {
        showToast(`Error cancelling`, false);
      }
    } catch (err) {
      showToast(`Error: ${err.message}`, false);
    }

    setShowCancelModal(false);
    setTokenInput('');
  };

  const checkIn = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/reservations/checkin/${tokenInput}`, {
        method: 'POST'
      });

      if (response.ok) {
        showToast("Check-in successful", true);
      } else {
        showToast(`Check-in not avaliable`, false);
      }
    } catch (err) {
      showToast(`Error: ${err.message}`, false);
    }

    setShowCheckinModal(false);
    setTokenInput('');
  };

  return (
    <>
      <nav id="navbar" style={{
        backgroundColor: '#2c3e50',
        color: 'white',
        padding: '10px 30px',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        width: '100%'
      }}>
        <Link to="/" id="navbar-title" style={{ textDecoration: 'none', color: 'white', fontSize: '24px', fontWeight: 'bold' }}>
          MealBooking - Moliceiro University
        </Link>
        <div>
          <button id="cancel-btn" onClick={() => setShowCancelModal(true)} style={{ marginRight: '10px' }}>
            Cancel Reservation
          </button>
          <button id="checkin-btn" onClick={() => setShowCheckinModal(true)}>
            Check-In
          </button>
        </div>
      </nav>

      {showCancelModal && (
        <div id="cancel-modal" style={modalStyle}>
          <div style={modalBoxStyle}>
            <h3>Cancel Reservation</h3>
            <input id="cancel-token-input" placeholder="Enter token" value={tokenInput} onChange={e => setTokenInput(e.target.value)} />
            <button onClick={cancelReservation}>Confirm</button>
            <button onClick={() => setShowCancelModal(false)}>Close</button>
          </div>
        </div>
      )}

      {showCheckinModal && (
        <div id="checkin-modal" style={modalStyle}>
          <div style={modalBoxStyle}>
            <h3>Check-In</h3>
            <input id="checkin-token-input" placeholder="Enter token" value={tokenInput} onChange={e => setTokenInput(e.target.value)} />
            <button onClick={checkIn}>Confirm</button>
            <button onClick={() => setShowCheckinModal(false)}>Close</button>
          </div>
        </div>
      )}

    {toast && (
      <div
        id={toast.success ? "success-toast" : "error-toast"}
        style={{
          position: 'fixed',
          top: '20px',
          right: '20px',
          padding: '12px 20px',
          backgroundColor: toast.success ? '#27ae60' : '#c0392b',
          color: 'white',
          borderRadius: '6px',
          boxShadow: '0 2px 10px rgba(0,0,0,0.2)',
          zIndex: 2000,
          maxWidth: '300px'
        }}
      >
        {toast.message}
      </div>
    )}
    </>
  );
};

const modalStyle = {
  position: 'fixed',
  top: 0, left: 0, right: 0, bottom: 0,
  backgroundColor: 'rgba(0, 0, 0, 0.5)',
  display: 'flex', justifyContent: 'center', alignItems: 'center',
  zIndex: 1000
};

const modalBoxStyle = {
  backgroundColor: 'white',
  padding: '20px',
  borderRadius: '8px',
  minWidth: '300px',
  display: 'flex',
  flexDirection: 'column',
  gap: '10px'
};

export default Navbar;
