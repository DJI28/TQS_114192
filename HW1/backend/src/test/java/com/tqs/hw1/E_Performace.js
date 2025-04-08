import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    scenarios: {
        get_meals: {
            executor: 'constant-vus',
            vus: 10,
            duration: '30s',
            exec: 'getMeals',
        },
        post_reservation: {
            executor: 'constant-vus',
            vus: 10,
            duration: '30s',
            exec: 'postReservation',
            startTime: '10s'
        },
    },
};

// Função para GET /api/meals
export function getMeals() {
    const res = http.get('http://localhost:8080/api/meals');

    check(res, {
        'GET status is 200': (r) => r.status === 200,
        'GET body is not empty': (r) => r.body.length > 0,
    });

    sleep(1);
}

// Função para POST /api/reservations
export function postReservation() {
    const payload = JSON.stringify({
        restaurantId: 1,
        date: '2025-04-09',
        type: 'ALMOCO',
    });

    const headers = { 'Content-Type': 'application/json' };

    const res = http.post('http://localhost:8080/api/reservations', payload, { headers });

    check(res, {
        'POST status is 200': (r) => r.status === 200,
        'received token': (r) => !!r.json('token'),
    });

    sleep(1);
}
