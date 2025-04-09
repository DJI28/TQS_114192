import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
  const [restaurants, setRestaurants] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetch('http://localhost:8080/api/restaurants')
      .then((res) => res.json())
      .then((data) => setRestaurants(data))
      .catch((err) => console.error("Error:", err));
  }, []);

  const handleClick = (id) => {
    navigate(`/restaurant/${id}`);
  };

  return (
    <div id="homepage" style={{ width: '100%', padding: '20px', boxSizing: 'border-box' }}>
      <h2 id="restaurants-title" style={{ textAlign: 'center', fontSize: '28px' }}>
        Avaliable Restaurants
      </h2>

      <ul id="restaurant-list" style={{ listStyle: 'none', padding: 0 }}>
        {restaurants.map((r) => (
          <li
            key={r.id}
            id={`restaurant-${r.id}`}
            onClick={() => handleClick(r.id)}
            style={{
              margin: '10px 0',
              padding: '10px',
              backgroundColor: '#ecf0f1',
              borderRadius: '8px',
              cursor: 'pointer',
              textAlign: 'center'
            }}
          >
            {r.name}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default HomePage;