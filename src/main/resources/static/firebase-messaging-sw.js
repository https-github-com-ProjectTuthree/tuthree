importScripts('https://www.gstatic.com/firebasejs/5.9.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/5.9.2/firebase-messaging.js');

// Initialize Firebase
let firebaseConfig = {

    "type": "service_account",
    "project_id": "project-tuthree",
    "private_key_id": "21e23c6c8a0adbf357f70bd4c3412f6835bc23f5",
    "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDVkOBhNNX4Fs/j\nH3uFfheNO3+xoyEVvisWZlWNYqBHZQ5yzSLy/66zBLHxtupGrfNoiZawKGJkKoL5\nv9Leb2hqKy6X96LTnjrBWADa97xIj1G3stS01W3ALap3AhzbiRFWod5eg2Vq62AZ\nRG/utILxRB2XlNsx8pOZRBB4mdGng7HGwcoaOL5mPugNXSyQA1qJ0ot7qvUO4B6I\nFzQboqw6bWkY8II7yaT/1Tuyft/VnjgESkI5lgjOnJUcqBGvumlZX7dK94YurFNx\nHjJwVmUUWpncDWKvXOXgBEcmQlRXxwG+fwPJSUa3niMg4ozfyoIvHhvmsX5s/JIA\nk3cSBJ75AgMBAAECggEAE2vMF9Ll9VxftcwGX9aeXivPui/3QYKsaOpP895I7I0q\nUfLJL5dkQ+fUpcJITjqh5LsEXsshXXIHPJ5+1tcDRApev5eXsU5VkgEGfOMj9mZA\nHGgxEyvIgf41HwLsbGNr1fi839PrnFxAvjN8yVSVhsq2tTLmdwWT+g9YPxtf1y+G\nUPRLJG0d0bg3kob+OlHOu4aaR8n0JR3t2HpvVDxdufQ+lNQFBzU3ETCbPQgxlvMg\ncbCk6aQdwZiao3Z9pzYcIX90PwJ+bll0mtWwT/kHd6l3sqcOuHYevakizce+Y35b\n6VJx+/05+PTZBT44mw3upGKkL7UqOZSLUkxIGjLjrQKBgQD6vD2nXG2a9/Bh+m9+\nEtx/myoIbv6dUHbh6SdKTyhV+w+uP7SLOtX2lK0IMTu4ecKU9CndXg2LTLxmBWgy\n1dq0W2+SVnz7lq3Xf3pWL2tZvhaBMkYHRpBwGJryLu82CUt40+dFgSG8DX8PvzOB\n/8PIWOky7YwVBuIBlAtcBlZbnQKBgQDaDNd8QrICkRZ8w4NNwz3l8lPFNx++ZF0l\niV9eKrDeowXHQmeftbIAmGGJyNP1wCXukGAG8jDKqqKWX3C0hWnTA0LEqZ6dgCX2\nK41TAYjMZmM5HwNRE/LBoAb0xbs2TuFHBBI0fxAqRtxK7dMAjdohYF6SufdKt/QP\nM350sXRYDQKBgEW1B777p00qGR5kLZLzU3H2hV0K3qaFtEdiNpYmI6Sjw9xBwcPj\ne5CuDNz2MxA6aapXpzvv5fwQPWBvqGRZqA/0S7R3bw/S+EmtGynSzRmKC1+nxmhH\naeiALrOd8MspEl5pKybbm7FYXzbVhVBi9t9AOLT77bH60+J0ciQHbwLRAoGAfsHM\nWs4i0V8qpWi64e/IAe63SARdjb6mb3XGXOWQYDyA1W8C8lMboUD0Irb+kPQcmOyR\nBjiwqIA9eBEPYcHU3PgeTlkUfhbK+qoiw8g2kCxNLh2bPfzMuEahBJYV2OJOD4yM\nULw0137N/Tj32nCcB58QG1fELEl3tYaH0qp+KdkCgYEAvyUX4TMBZ8KFhsfMxbDb\nZyH87Lgm9TFzkOCCy//dV7SLX6P9Wf2rzG3riAm80b1064zT9Gjow4ngO/vOr443\nfE2MvEjGIG54zNOH8C1GWNsB3fwvxxA9cuNIQkrXphXs7CkFMSyOYOOmpzUPerwx\nqTjkx0Q/VpbJLYzD0CHgC1k=\n-----END PRIVATE KEY-----\n",
    "client_email": "firebase-adminsdk-b508m@project-tuthree.iam.gserviceaccount.com",
    "client_id": "108332579071285894015",
    "auth_uri": "https://accounts.google.com/o/oauth2/auth",
    "token_uri": "https://oauth2.googleapis.com/token",
    "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
    "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-b508m%40project-tuthree.iam.gserviceaccount.com"

};
firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();