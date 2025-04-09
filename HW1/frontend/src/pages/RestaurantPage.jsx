import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

function RestaurantPage() {
  const { id } = useParams();
  const [meals, setMeals] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:8080/api/meals?restaurantId=${id}`)
      .then(res => res.json())
      .then(data => setMeals(data));
  }, [id]);

  const handleReserve = (mealId, date, type) => {
    fetch('http://localhost:8080/api/reservations', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        restaurantId: parseInt(id),
        date,
        type
      })
    })
      .then(res => res.json())
      .then(data => {
        alert(`Reservation created! Token: ${data.token}`);
      })
      .catch(err => {
        alert("Error while creating reservation.");
        console.error(err);
      });
  };

  return (
    <div id="restaurant-page" style={{ padding: '20px', width: '100%' }}>
      <h2 id="restaurant-meals-title" style={{ textAlign: 'center', fontSize: '28px', marginBottom: '20px' }}>
        Available Meals
      </h2>

      <table id="meals-table" style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ backgroundColor: '#34495e', color: 'white' }}>
            <th style={{ padding: '10px' }}>Name</th>
            <th style={{ padding: '10px' }}>Date</th>
            <th style={{ padding: '10px' }}>Type</th>
            <th style={{ padding: '10px' }}>Weather</th> {/* Column for Weather */}
            <th style={{ padding: '10px' }}>Action</th>
          </tr>
        </thead>
        <tbody>
          {meals.map(meal => (
            <tr key={meal.id} id={`meal-${meal.id}`} style={{ backgroundColor: '#ecf0f1', textAlign: 'center' }}>
              <td style={{ padding: '10px' }}>{meal.name}</td>
              <td style={{ padding: '10px' }}>{meal.date}</td>
              <td style={{ padding: '10px' }}>{meal.type}</td>
              {/* Displaying weather information */}
              <td style={{ padding: '10px' }}>
                {meal.weather ? (
                  <span>{meal.weather.description}, {meal.weather.temperature}°C</span>
                ) : (
                  <span>Loading weather...</span>
                )}
              </td>
              <td style={{ padding: '10px' }}>
                <button
                  id={`reserve-btn-${meal.id}`}
                  onClick={() => handleReserve(meal.id, meal.date, meal.type)}
                  style={{
                    backgroundColor: '#2ecc71',
                    color: 'white',
                    border: 'none',
                    padding: '8px 12px',
                    borderRadius: '5px',
                    cursor: 'pointer'
                  }}
                >
                  Reserve
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default RestaurantPage;