// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();


// Delete all requests and games each morning
export const scheduledFunctionCrontab =
functions.pubsub.schedule('5 4 * * *').onRun(() => {
    // This will be run every day at 4:05 AM UTC
    admin.database().ref('Request').remove();
    admin.database().ref('Requests').remove();
    admin.database().ref('Games').remove();
});
