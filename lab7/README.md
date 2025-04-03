# Lab 7

## Lab7_1

### Metrics
- API call took 52.87ms (How long did the API call take?)
- 1 request was made (How many requests were made?)
- 0 requests failed (How many requests failed? (i.e., whose HTTP status code was not 200))

## Lab7_2

### Metrics
- API calls took on average 154.75ms, minumum 6.47ms and maximum 707.32ms (How long did the API calls take on average, minimum and maximum?)
- 1963 requests were made (How many requests were made?)
- 0 requests failed (How many requests failed? (i.e., whose HTTP status code was not 200))

## Metrics 20VUs over 30sec
THRESHOLDS 

    checks
    ✓ 'rate>0.99' rate=100.00%

    http_req_duration
    ✓ 'p(95)<1100' p(95)=547.46ms

    http_req_failed
    ✓ 'rate<0.01' rate=0.00%


  █ TOTAL RESULTS 

    checks_total.......................: 9412    104.469157/s
    checks_succeeded...................: 100.00% 9412 out of 9412
    checks_failed......................: 0.00%   0 out of 9412

    ✓ status is 200
    ✓ body size is < 1KB

    HTTP
    http_req_duration.......................................................: avg=258.4ms  min=4.99ms med=236.58ms max=1.11s p(90)=473.68ms p(95)=547.46ms
      { expected_response:true }............................................: avg=258.4ms  min=4.99ms med=236.58ms max=1.11s p(90)=473.68ms p(95)=547.46ms
    http_req_failed.........................................................: 0.00%  0 out of 4706
    http_reqs...............................................................: 4706   52.234578/s

    EXECUTION
    iteration_duration......................................................: avg=258.75ms min=5.26ms med=236.91ms max=1.11s p(90)=473.93ms p(95)=547.85ms
    iterations..............................................................: 4706   52.234578/s
    vus.....................................................................: 1      min=1         max=20
    vus_max.................................................................: 20     min=20        max=20

    NETWORK
    data_received...........................................................: 3.4 MB 38 kB/s
    data_sent...............................................................: 1.7 MB 19 kB/s

PHOTO HERE

## Metrics 120+VUs over 30sec

  █ THRESHOLDS 

    checks
    ✗ 'rate>0.99' rate=76.33%

    http_req_duration
    ✗ 'p(95)<1100' p(95)=2.49s

    http_req_failed
    ✗ 'rate<0.01' rate=47.33%


  █ TOTAL RESULTS 

    checks_total.......................: 9958   110.61814/s
    checks_succeeded...................: 76.33% 7601 out of 9958
    checks_failed......................: 23.66% 2357 out of 9958

    ✗ status is 200
      ↳  52% — ✓ 2622 / ✗ 2357
    ✓ body size is < 1KB

    HTTP
    http_req_duration.......................................................: avg=1.46s min=31.05ms med=1.46s max=4.02s p(90)=2.21s p(95)=2.49s
      { expected_response:true }............................................: avg=1.15s min=31.05ms med=1.11s max=3.57s p(90)=1.88s p(95)=2.15s
    http_req_failed.........................................................: 47.33% 2357 out of 4979
    http_reqs...............................................................: 4979   55.30907/s

    EXECUTION
    iteration_duration......................................................: avg=1.46s min=31.38ms med=1.46s max=4.02s p(90)=2.21s p(95)=2.49s
    iterations..............................................................: 4979   55.30907/s
    vus.....................................................................: 1      min=1            max=120
    vus_max.................................................................: 120    min=120          max=120

    NETWORK
    data_received...........................................................: 2.2 MB 24 kB/s
    data_sent...............................................................: 1.8 MB 20 kB/s




running (1m30.0s), 000/120 VUs, 4979 complete and 0 interrupted iterations
default ✓ [======================================] 000/120 VUs  1m30s
ERRO[0090] thresholds on metrics 'checks, http_req_duration, http_req_failed' have been crossed 

PHOTO HERE

## Prometheus

  █ THRESHOLDS 

    checks
    ✗ 'rate>0.99' rate=77.11%

    http_req_duration
    ✗ 'p(95)<1100' p(95)=2.57s

    http_req_failed
    ✗ 'rate<0.01' rate=45.77%


  █ TOTAL RESULTS 

    checks_total.......................: 9766   108.375838/s
    checks_succeeded...................: 77.11% 7531 out of 9766
    checks_failed......................: 22.88% 2235 out of 9766

    ✗ status is 200
      ↳  54% — ✓ 2648 / ✗ 2235
    ✓ body size is < 1KB

    HTTP
    http_req_duration.......................................................: avg=1.49s min=37ms    med=1.48s max=4.21s p(90)=2.28s p(95)=2.57s
      { expected_response:true }............................................: avg=1.19s min=37ms    med=1.13s max=4.21s p(90)=1.96s p(95)=2.23s
    http_req_failed.........................................................: 45.77% 2235 out of 4883
    http_reqs...............................................................: 4883   54.187919/s

    EXECUTION
    iteration_duration......................................................: avg=1.49s min=37.39ms med=1.48s max=4.21s p(90)=2.28s p(95)=2.57s
    iterations..............................................................: 4883   54.187919/s
    vus.....................................................................: 1      min=1            max=120
    vus_max.................................................................: 120    min=120          max=120

    NETWORK
    data_received...........................................................: 2.2 MB 24 kB/s
    data_sent...............................................................: 1.8 MB 20 kB/s




running (1m30.1s), 000/120 VUs, 4883 complete and 0 interrupted iterations
default ✓ [======================================] 000/120 VUs  1m30s
ERRO[0090] thresholds on metrics 'checks, http_req_duration, http_req_failed' have been crossed 

