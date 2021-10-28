importScripts('https://www.gstatic.com/firebasejs/5.9.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/5.9.2/firebase-messaging.js');

// Initialize Firebase
let firebaseConfig = {
    apiKey: "AIzaSyDI2_kLWmvIaFihYdqfY0bc81liemBR7vc",
    authDomain: "project-tuthree.firebaseapp.com",
    projectId: "project-tuthree",
    storageBucket: "project-tuthree.appspot.com",
    messagingSenderId: "154599911586",
    appId: "1:154599911586:web:9d73a0ff84b0f3ff65e7fb",
    measurementId: "G-1B70CDPDM4"
};
firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();
