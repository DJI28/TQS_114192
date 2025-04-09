import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = 'http://localhost:8080';

export let options = {
    stages: [
        { duration: '30s', target: 50 },
        { duration: '1m', target: 100 },
        { duration: '30s', target: 0 },
    ],
};

export default function () {
    let payload = JSON.stringify({
        restaurantId: 6,
        date: '2025-04-10',
        type: 'LUNCH',
    });

    let params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    let res = http.post(`${BASE_URL}/api/reservations`, payload, params);

    check(res, {
        'is status 200': (r) => r.status === 200,
        'token is returned': (r) => r.body.includes('token'),
    });

    let restaurantsRes = http.get(`${BASE_URL}/api/restaurants`);

    check(restaurantsRes, {
        'restaurants GET status is 200': (r) => r.status === 200,
        'restaurants list is not empty': (r) => JSON.parse(r.body).length > 0,
    });

    let mealsRes = http.get(`${BASE_URL}/api/meals`);

    check(mealsRes, {
        'meals GET status is 200': (r) => r.status === 200,
        'meals list is not empty': (r) => JSON.parse(r.body).length > 0,
    });

    sleep(1);
}